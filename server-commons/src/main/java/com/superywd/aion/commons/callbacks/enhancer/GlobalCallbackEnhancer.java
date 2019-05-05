package com.superywd.aion.commons.callbacks.enhancer;

import com.superywd.aion.commons.callbacks.CallbackResult;
import com.superywd.aion.commons.callbacks.metadata.GlobalCallback;
import com.superywd.aion.commons.callbacks.util.GlobalCallbackHelper;
import com.superywd.aion.commons.callbacks.util.CallbackUtil;
import javassist.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * 面向全体类的代理增强
 * @author: saltman155
 * @date: 2018/10/28 22:54
 */

public class GlobalCallbackEnhancer extends CallbackClassFileTransformer {

    private static final Logger logger = LoggerFactory.getLogger(GlobalCallbackEnhancer.class);

    @Override
    protected byte[] transformClass(ClassLoader loader, byte[] clazzBytes) throws Exception {
        ClassPool classPool = new ClassPool();
        classPool.appendClassPath(new LoaderClassPath(loader));
        CtClass clazz = classPool.makeClass(new ByteArrayInputStream(clazzBytes));

        Set<CtMethod> methodsToEnhance = new HashSet<CtMethod>();
        for (CtMethod method : clazz.getDeclaredMethods()) {
            if (!isEnhanceable(method)) {
                continue;
            }
            methodsToEnhance.add(method);
        }
        if (!methodsToEnhance.isEmpty()) {
            logger.debug("代理名为: " + clazz.getName()+"的类...");
            for (CtMethod method : methodsToEnhance) {
                logger.debug("代理名为: " + method.getLongName()+" 的方法...");
                enhanceMethod(method);
            }
            return clazz.toBytecode();
        }else{
            logger.trace("类："+clazz.getName() + " 不需要被代理");
            return null;
        }
    }

    /**
     * 代理方法的一些前置步骤，比如添加一个局部变量 ___globalCallbackResult 类型是在代理注解中声明的类型
     * @param method
     * @throws CannotCompileException
     * @throws NotFoundException
     * @throws ClassNotFoundException
     */
    protected void enhanceMethod(CtMethod method) throws CannotCompileException, NotFoundException, ClassNotFoundException {
        ClassPool classPool = method.getDeclaringClass().getClassPool();
        method.addLocalVariable("___globalCallbackResult", classPool.get(CallbackResult.class.getName()));
        //从方法注解上获取包含代理方法的类
        CtClass listenerClazz = classPool.get(((GlobalCallback) method.getAnnotation(GlobalCallback.class)).value().getName());
        boolean isStatic = Modifier.isStatic(method.getModifiers());
        String listenerFieldName = "$$$"+(isStatic? "Static" : "")+listenerClazz.getSimpleName();
        CtClass clazz = method.getDeclaringClass();
        try	{
            clazz.getField(listenerFieldName);
        }	catch(NotFoundException e) {
            clazz.addField(CtField.make((isStatic? "static " : "")+"Class "+listenerFieldName+" = Class.forName(\""+listenerClazz.getName()+"\");", clazz));
        }

        int paramLength = method.getParameterTypes().length;

        method.insertBefore(writeBeforeMethod(method, paramLength, listenerFieldName));
        method.insertAfter(writeAfterMethod(method, paramLength, listenerFieldName));
    }


    protected String writeBeforeMethod(CtMethod method, int paramLength, String listenerFieldName)
            throws NotFoundException {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("___globalCallbackResult = ");
        sb.append(GlobalCallbackHelper.class.getName()).append(".beforeCall(");
        // 检查代理的方法是静态的还是非静态的，静态方法传入的调用对象是本类的Class对象
        if (Modifier.isStatic(method.getModifiers())) {
            sb.append(method.getDeclaringClass().getName()).append(".class, ").append(listenerFieldName);
            sb.append(", ");
        }
        else {
            sb.append("this, ").append(listenerFieldName);
            sb.append(", ");
        }
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
        sb.append(");");
        sb.append("if(___globalCallbackResult.isBlockingCaller()){");
        // Fake return due to javassist bug
        // $r is not available in "insertBefore"
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
        }
        sb.append(";}}");
        return sb.toString();
    }

    /**
     * javassist技术中，可以动态的使用一些字符表示特定的对象，
     *  比如：
     *          $0, $1, $2, ... $0表示this，其他的表示实际的参数
     *          $args	        参数数组. 相当于new Object[]{$1,$2,....}，其中的基本类型会被转为包装类型
     *          $$	            所有的参数，如m($$)相当于m($1,$2...)，如果m无参数则m($$)相当于m()
     *          $cflow(...)	    表示一个指定的递归调用的深度
     *          $r	            用于类型装换，表示返回值的类型.
     *          $w	            将基础类型转换为一个包装类型.如Integer a=($w)5；表示将5转换为Integer。如果不是基本类型则什么都不做。
     *          $_	            返回值，如果方法为void，则返回值为null; 值在方法返回前获得，
     *                          如果希望发生异常是有返回值（默认值，如nul），需要将insertAfter方法的第二个参数asFinally设置为true
     *          $sig	        方法参数的类型数组，数组的顺序为参数的顺序
     *          $type	        返回类型的class， 如返回Integer则$type相当于java.lang.Integer.class， 注意其与$r的区别
     *          $class	        方法所在的类的class
     *
     * @param method                    被代理的方法
     * @param paramLength               被代理的方法的实参列表
     * @param listenerFieldName         之前步骤中声明的代理方法类变量的名称
     * @return
     * @throws NotFoundException
     */
    protected String writeAfterMethod(CtMethod method, int paramLength, String listenerFieldName)
            throws NotFoundException {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        // 如果前置代理方法返回的结果是中断其他后续代理方法的访问的，那就
        if (!method.getReturnType().equals(CtClass.voidType)) {
            sb.append("if(___globalCallbackResult.isBlockingCaller()){");
            sb.append("$_ = ($r)($w)___globalCallbackResult.getResult();");
            sb.append("}");
        }

        sb.append("___globalCallbackResult = ").append(GlobalCallbackHelper.class.getName()).append(".afterCall(");
        if (Modifier.isStatic(method.getModifiers())) {
            sb.append(method.getDeclaringClass().getName()).append(".class, ").append(listenerFieldName);
            sb.append(", ");
        }
        else {
            sb.append("this, ");
            sb.append(listenerFieldName).append(", ");
        }
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
        sb.append("if(___globalCallbackResult.isBlockingCaller()){");
        if (method.getReturnType().equals(CtClass.voidType)) {
            sb.append("return;");
        }
        else {
            sb.append("return ($r)($w)___globalCallbackResult.getResult();");
        }
        sb.append("}");
        sb.append("else {return $_;}");
        sb.append("}");
        return sb.toString();
    }



    /**
     * 查看方法是否可以被代理，抽象方法或本地方法不可被代理
     * 当加上 {@link GlobalCallback} 的方法可以被代理
     * @param method
     * @return
     */
    protected boolean isEnhanceable(CtMethod method) {
        int modifiers = method.getModifiers();
        if(Modifier.isAbstract(modifiers) || Modifier.isNative(modifiers)){
            return false;
        }
        return CallbackUtil.isAnnotationPresent(method, GlobalCallback.class);
    }
}
