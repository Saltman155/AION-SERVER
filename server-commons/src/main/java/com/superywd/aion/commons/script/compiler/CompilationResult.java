package com.superywd.aion.commons.script.compiler;

import com.superywd.aion.commons.script.classloader.ScriptClassLoader;

/**
 * 编译结果封装
 * @author 迷宫的中心
 * @date 2019/4/18 15:53
 */
public class CompilationResult {

    /**编译的类集合*/
    private final Class<?>[] compiledClasses;
    /**对应的classLoader*/
    private final ScriptClassLoader classLoader;

    public CompilationResult(Class<?>[] compiledClasses, ScriptClassLoader classLoader) {
        this.compiledClasses = compiledClasses;
        this.classLoader = classLoader;
    }

    public Class<?>[] getCompiledClasses() {
        return compiledClasses;
    }

    public ScriptClassLoader getClassLoader() {
        return classLoader;
    }
}
