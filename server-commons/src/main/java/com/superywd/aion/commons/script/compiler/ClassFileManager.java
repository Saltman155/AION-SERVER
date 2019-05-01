package com.superywd.aion.commons.script.compiler;

import com.superywd.library.script.classloader.ScriptClassLoader;
import com.superywd.library.script.classloader.impl.ScriptClassLoaderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *这个类用于改写编译的细节。将提供给javac的编译结果容器改成我们自己定义的容器
 * @author 迷宫的中心
 * @date 2019/4/18 17:32
 */
public class ClassFileManager extends ForwardingJavaFileManager<JavaFileManager> {

    private static final Logger logger = LoggerFactory.getLogger(ClassFileManager.class);

    /**这个map（缓存表）存储相关的classloader对脚本编译的结果*/
    private final Map<String, BinaryClass> compiledClasses = new HashMap<>();
    /**相关的ScriptClassLoader*/
    protected ScriptClassLoaderImpl classLoader;
    /**相关的ScriptClassLoader的父级classLoader*/
    protected ScriptClassLoader parentClassLoader;

    public ClassFileManager(JavaCompiler compiler, DiagnosticListener<? super JavaFileObject> listener) {
        super(compiler.getStandardFileManager(listener, null, null));
    }

    /**
     * 该方法会被javac内部的编译逻辑调用，通过调用此方法来生成一个用于存放编译结果字节码的对象
     * 我们将其做一些侵入性的重写，创建一个自定义的BinaryClass，然后放入缓存中，然后返回
     * @param location      未使用
     * @param className     类名
     * @param kind          未使用
     * @param sibling       未使用
     * @return              自定义的字节码类关联对象（继承自 {@link com.sun.tools.javac.file.BaseFileObject}）
     */
    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) {
        BinaryClass binaryClass = new BinaryClass(className);
        compiledClasses.put(className, binaryClass);
        return binaryClass;
    }

    /**
     * 获取此类文件管理器的类加载器
     * 如果没有类加载器，则创建一个类加载器
     * @param location      未使用
     * @return              对应的类加载器
     */
    @Override
    public ClassLoader getClassLoader(Location location) {
        if(classLoader == null){
            if (parentClassLoader != null) {
                classLoader = new ScriptClassLoaderImpl(this, parentClassLoader);
            } else {
                classLoader = new ScriptClassLoaderImpl(this);
            }
        }
        return classLoader;
    }

    /**
     * 获取文件对象的二进制名称，这里对于BinaryClass类型的统一返回创建时传入的name
     * @param location      位置
     * @param file          编译结果对象
     * @return              二进制名称
     */
    @Override
    public String inferBinaryName(Location location, JavaFileObject file) {
        if (file instanceof BinaryClass) {
            return ((BinaryClass) file).inferBinaryName(null);
        }
        return super.inferBinaryName(location, file);
    }

    /**
     * 添加依赖文件到这个类文件管理器中
     * @param file                  依赖文件（.jar）
     * @throws IOException
     */
    public void addLibrary(File file) throws IOException {
        ScriptClassLoaderImpl classLoader = (ScriptClassLoaderImpl) getClassLoader(null);
        classLoader.addJarFile(file);
    }

    /**
     * 添加一系列依赖文件到类文件管理器中
     * @param files                 依赖文件迭代器
     * @throws IOException
     */
    public void addLibraries(Iterable<File> files) throws IOException {
        for (File f : files) {
            addLibrary(f);
        }
    }

    /**
     * 获取类文件管理器缓存中维护的类（已经编译的）
     * @return                      已经编译的类表
     */
    public Map<String, BinaryClass> getCompiledClasses() {
        return compiledClasses;
    }

    public void setParentClassLoader(ScriptClassLoader classLoader) {
        this.parentClassLoader = classLoader;
    }

}
