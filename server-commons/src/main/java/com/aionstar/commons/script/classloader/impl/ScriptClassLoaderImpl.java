package com.aionstar.commons.script.classloader.impl;

import com.aionstar.commons.script.compiler.BinaryClass;
import com.aionstar.commons.script.compiler.ClassFileManager;
import com.aionstar.commons.script.classloader.ScriptClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Collections;
import java.util.Set;

/**
 * @author: saltman155
 * @date: 2019/4/18 17:13
 */
public class ScriptClassLoaderImpl extends ScriptClassLoader {

    private static final Logger logger = LoggerFactory.getLogger(ScriptClassLoaderImpl.class);

    /**类缓存管理器*/
    private final ClassFileManager classFileManager;

    public ScriptClassLoaderImpl(ClassFileManager classFileManager) {
        super(new URL[] {}, ScriptClassLoaderImpl.class.getClassLoader());
        this.classFileManager = classFileManager;
    }

    public ScriptClassLoaderImpl(ClassFileManager classFileManager, ClassLoader parent) {
        super(new URL[] {}, parent);
        this.classFileManager = classFileManager;
    }

    @Override
    public Set<String> getCompiledClasses() {
        Set<String> compiledClasses = classFileManager.getCompiledClasses().keySet();
        return Collections.unmodifiableSet(compiledClasses);
    }

    @Override
    public byte[] getByteCode(String className) {
        BinaryClass bc = getClassFileManager().getCompiledClasses().get(className);
        byte[] b = new byte[bc.getBytes().length];
        //做一个复制，以便修改时不会影响
        System.arraycopy(bc.getBytes(), 0, b, 0, b.length);
        return b;
    }

    @Override
    public Class<?> getDefinedClass(String name) {
        BinaryClass bc = classFileManager.getCompiledClasses().get(name);
        if (bc == null) {
            return null;
        }
        return bc.getDefinedClass();
    }

    @Override
    public void setDefinedClass(String name, Class<?> clazz) throws IllegalArgumentException {

    }

    public ClassFileManager getClassFileManager() {
        return classFileManager;
    }
}
