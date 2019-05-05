package com.superywd.aion.commons.callbacks.enhancer;

import com.superywd.aion.commons.callbacks.Callback;
import com.superywd.aion.commons.callbacks.CallbackResult;
import com.superywd.aion.commons.callbacks.EnhancedObject;
import com.superywd.aion.commons.callbacks.metadata.ObjectCallback;
import com.superywd.aion.commons.callbacks.util.CallbackUtil;
import com.superywd.aion.commons.callbacks.util.ObjectCallbackHelper;
import javassist.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 面向指定的类的代理增强
 * @author: saltman155
 * @date: 2018/10/19 00:23
 */

public class ObjectCallbackEnhancer extends CallbackClassFileTransformer{

    private static final Logger logger = LoggerFactory.getLogger(ObjectCallbackEnhancer.class);

    private static final String FIELD_NAME_CALLBACKS = "$$$callbacks";

    private static final String FIELD_NAME_CALLBACKS_LOCK = "$$$callbackLock";

    @Override
    protected byte[] transformClass(ClassLoader loader, byte[] clazzBytes) throws Exception {
        ClassPool classPool = new ClassPool();
        classPool.appendClassPath(new LoaderClassPath(loader));
        //这里把传进来的类字节码构造新的CtClass类
        CtClass clazz = classPool.makeClass(new ByteArrayInputStream(clazzBytes));
        Set<CtMethod> methodsToEnhance = new HashSet<>();
        for(CtMethod method : clazz.getDeclaredMethods()){
            if(!isEnhanceable(method)){
                continue;
            }
            methodsToEnhance.add(method);
        }
        if (!methodsToEnhance.isEmpty()) {
            CtClass eo = classPool.get(EnhancedObject.class.getName());
            //检查一下需要被代理的类是不是已经实现了EnhancedObject接口
            for (CtClass i : clazz.getInterfaces()) {
                if (i.equals(eo)) {
                    throw new RuntimeException("这个类的方法需要被代理，但是却已经实现了EnhancedObject接口？");
                }
            }
            logger.debug("需要被代理的类: " + clazz.getName());
            //生成代理类
            writeEnhancedObjectImpl(clazz);
            //生成代理方法
            for (CtMethod method : methodsToEnhance) {
                logger.debug("代理名为: " + method.getLongName()+" 的方法...");
                enhanceMethod(method);
            }
            return clazz.toBytecode();
        }
        else{
            logger.trace("类 " + clazz.getName() + " 不需要被代理");
            return null;
        }

    }

    /**
     * 负责方法增强，编写服务调用方法。
     * @param method                        需要被增强的方法
     * @throws NotFoundException
     * @throws CannotCompileException
     * @throws ClassNotFoundException
     */
    protected void enhanceMethod(CtMethod method) throws NotFoundException, CannotCompileException, ClassNotFoundException {
        ClassPool classPool = method.getDeclaringClass().getClassPool();
        //addLocalVariable 新设置一个局部变量到这个方法里
        method.addLocalVariable("___cbr", classPool.get(CallbackResult.class.getName()));
        //获取这个方法上的增强注解里配置的代理方法类
        CtClass listenerClazz = classPool.get(((ObjectCallback) method.getAnnotation(ObjectCallback.class)).value().getName());
        String listenerFieldName = "$$$"+listenerClazz.getSimpleName();
        CtClass clazz = method.getDeclaringClass();
        try	{
            //先试探一下看存不存在这个成员变量
            clazz.getField(listenerFieldName);
        }	catch(NotFoundException e) {
            //不存在的话，直接暴力塞一个进去
            clazz.addField(CtField.make("Class "+listenerFieldName+" = Class.forName(\""+listenerClazz.getName()+"\");", clazz));
        }
        //获取参数的长度
        int paramLength = method.getParameterTypes().length;
        //在方法前插入代码
        method.insertBefore(writeBeforeMethod(method, paramLength, listenerFieldName));
        //在方法后插入代码
        method.insertAfter(writeAfterMethod(method, paramLength, listenerFieldName));
    }

    /**
     * 组装前置代理方法的代码
     * @param method                        需要被代理的方法
     * @param paramLength                   被代理的方法的参数长度
     * @param listenerFieldName             包含代理方法的代理类之前被声明的名字
     * @return
     * @throws NotFoundException
     */
    protected String writeBeforeMethod(CtMethod method, int paramLength, String listenerFieldName) throws NotFoundException {
        StringBuilder sb = new StringBuilder();
        //组装插入的代码
        sb.append('{');
        sb.append("___cbr = ");
        sb.append(ObjectCallbackHelper.class.getName()).append(".beforeCall((");
        sb.append(EnhancedObject.class.getName());
        sb.append(")this, " + listenerFieldName + ", ");
        if(paramLength > 0){
            sb.append("new Object[]{");
            //这里参数的插入用到了javassist
            for (int i = 1; i <= paramLength; i++) {
                sb.append("($w)$").append(i);
                if (i < paramLength) {
                    sb.append(',');
                }
            }
            sb.append("}");
        }
        else{
            sb.append("null");
        }
        sb.append(");");
        //如果代理方法返回的是截断被代理方法的信号，就把方法直接返回
        sb.append("if(___cbr.isBlockingCaller()){");
        CtClass returnType = method.getReturnType();
        if (returnType.equals(CtClass.voidType)) {
            sb.append("return");
        }
        else if (returnType.equals(CtClass.booleanType)) {
            sb.append("return false");
        }
        else if (returnType.equals(CtClass.charType)) {
            sb.append("return 'a'");
        }
        else if (returnType.equals(CtClass.byteType) || returnType.equals(CtClass.shortType)
                || returnType.equals(CtClass.intType) || returnType.equals(CtClass.floatType)
                || returnType.equals(CtClass.longType) || returnType.equals(CtClass.doubleType)) {
            sb.append("return 0");
        }else {
            //这里我比较疑惑，如果被代理的方法返回一个类，那不就翻皮水了，找不到返回值了
            throw new RuntimeException("其他类型的返回就TMD不管了");
        }
        sb.append(";}}");
        logger.info(sb.toString());
        return sb.toString();
    }

    /**
     * 组装后置代理方法的代码
     * @param method
     * @param paramLength
     * @param listenerFieldName
     * @return
     * @throws NotFoundException
     */
    protected String writeAfterMethod(CtMethod method, int paramLength, String listenerFieldName) throws NotFoundException {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        // workaround for javassist bug, $r is not available in "insertBefore"
        if (!method.getReturnType().equals(CtClass.voidType)) {
            sb.append("if(___cbr.isBlockingCaller()){");
            sb.append("$_ = ($r)($w)___cbr.getResult();");
            sb.append("}");
        }
        sb.append("___cbr = ").append(ObjectCallbackHelper.class.getName()).append(".afterCall((");
        sb.append(EnhancedObject.class.getName()).append(")this, " + listenerFieldName + ", ");
        if (paramLength > 0) {
            sb.append("new Object[]{");
            for (int i = 1; i <= paramLength; i++) {
                sb.append("($w)$").append(i);
                if (i < paramLength) {
                    sb.append(',');
                }
            }
            sb.append("}");
        }
        else {
            sb.append("null");
        }
        sb.append(", ($w)$_);");
        //如果代理方法返回的是截断被代理方法的信号，就把方法在这里直接返回，不要继续执行了
        sb.append("if(___cbr.isBlockingCaller()){");
        if (method.getReturnType().equals(CtClass.voidType)) {
            sb.append("return;");
        }
        else {
            sb.append("return ($r)($w)___cbr.getResult();");
        }
        sb.append("}");
        sb.append("else {return $_;}");
        sb.append("}");
        logger.info(sb.toString());
        return sb.toString();
    }

    /**
     * 让这个类实现被代理接口
     * @param clazz     需要代理
     * @throws NotFoundException
     * @throws CannotCompileException
     */
    protected void writeEnhancedObjectImpl(CtClass clazz) throws NotFoundException, CannotCompileException {
        ClassPool classPool = clazz.getClassPool();
        //别bb了，先给你加个接口！
        clazz.addInterface(classPool.get(EnhancedObject.class.getName()));
        writeEnhancedObjectFields(clazz);
        writeEnhancedObjectMethods(clazz);
    }


    /**
     * 添加代理类必须的成员变量
     * @param clazz
     * @throws CannotCompileException
     * @throws NotFoundException
     */
    private void writeEnhancedObjectFields(CtClass clazz) throws CannotCompileException, NotFoundException {
        ClassPool classPool = clazz.getClassPool();
        //为被代理的类添加一个用来存放代理方法类的对象
        CtField cbField = new CtField(classPool.get(Map.class.getName()), FIELD_NAME_CALLBACKS, clazz);
        cbField.setModifiers(java.lang.reflect.Modifier.PRIVATE);
        clazz.addField(cbField, CtField.Initializer.byExpr("null;"));

        //为被代理的类添加一个用来存放代理方法类的同步锁的对象
        CtField cblField = new CtField(classPool.get(ReentrantReadWriteLock.class.getName()), FIELD_NAME_CALLBACKS_LOCK, clazz);
        cblField.setModifiers(java.lang.reflect.Modifier.PRIVATE);
        clazz.addField(cblField, CtField.Initializer.byExpr("new " + ReentrantReadWriteLock.class.getName() + "();"));
    }

    /**
     * 添加代理类必须的成员方法（从 {@link EnhancedObject} 接口实现的方法）
     * @param clazz
     * @throws NotFoundException
     * @throws CannotCompileException
     */
    private void writeEnhancedObjectMethods(CtClass clazz) throws NotFoundException, CannotCompileException {
        ClassPool classPool = clazz.getClassPool();
        CtClass callbackClass = classPool.get(Callback.class.getName());
        CtClass mapClass = classPool.get(Map.class.getName());
        CtClass reentrantReadWriteLockClass = classPool.get(ReentrantReadWriteLock.class.getName());

        CtMethod method = new CtMethod(CtClass.voidType, "addCallback", new CtClass[] { callbackClass }, clazz);
        method.setModifiers(java.lang.reflect.Modifier.PUBLIC);
        method.setBody("ObjectCallbackHelper.addCallback($1, this);");
        clazz.addMethod(method);

        method = new CtMethod(CtClass.voidType, "removeCallback", new CtClass[] { callbackClass }, clazz);
        method.setModifiers(java.lang.reflect.Modifier.PUBLIC);
        method.setBody("ObjectCallbackHelper.removeCallback($1, this);");
        clazz.addMethod(method);

        method = new CtMethod(mapClass, "getCallbacks", new CtClass[] {}, clazz);
        method.setModifiers(java.lang.reflect.Modifier.PUBLIC);
        method.setBody("return " + FIELD_NAME_CALLBACKS + ";");
        clazz.addMethod(method);

        method = new CtMethod(CtClass.voidType, "setCallbacks", new CtClass[] { mapClass }, clazz);
        method.setModifiers(java.lang.reflect.Modifier.PUBLIC);
        method.setBody("this." + FIELD_NAME_CALLBACKS + " = $1;");
        clazz.addMethod(method);

        method = new CtMethod(reentrantReadWriteLockClass, "getCallbackLock", new CtClass[] {}, clazz);
        method.setModifiers(java.lang.reflect.Modifier.PUBLIC);
        method.setBody("return " + FIELD_NAME_CALLBACKS_LOCK + ";");
        clazz.addMethod(method);
    }

    /**
     * 检查方法是否可增强 它应该标有
     * {@link ObjectCallback} 注解,并且不是本地方法，也不是抽象方法,或静态方法。
     * @param method 传入的方法
     * @return 能否被增强
     */
    protected boolean isEnhanceable(CtMethod method){
        //获取这个方法的修饰符标记
        int modifiers = method.getModifiers();
        if((Modifier.isAbstract(modifiers))  || Modifier.isNative(modifiers) || Modifier.isStatic(modifiers)){
            return false;
        }
        return CallbackUtil.isAnnotationPresent(method, ObjectCallback.class);

    }
}
