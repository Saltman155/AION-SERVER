package com.superywd.aion.commons.script.compiler;

import com.superywd.library.script.classloader.ScriptClassLoader;

import java.io.File;

/**
 * 脚本引擎编译接口
 *      这个接口声明了一个脚本引擎的编译器应该具有的方法
 *      可以使用groovy来实现它，但目前使用的是原生的javac,因为其生成的字节码效率更高
 * @author 迷宫的中心
 * @date 2019/4/18 13:46
 */
public interface ScriptCompiler {

    /**
     * 为此编译器设置父级类加载器
     * @param classLoader       父级类加载器
     */
    void setParentClassLoader(ScriptClassLoader classLoader);

    /**
     * 设置依赖文件
     * @param files             依赖文件
     */
    void setLibraries(Iterable<File> files);

    /**
     * 编译单个文件
     * @param className         类名
     * @param sourceCode        代码
     * @return                  编译结果
     */
    CompilationResult compile(String className, String sourceCode);

    /**
     * 编译多个文件
     * @param className         类名
     * @param sourceCode        代码
     * @return                  编译结果
     * @throws IllegalArgumentException     当类名数量与代码数量不一致
     */
    CompilationResult compile(String[] className, String[] sourceCode) throws IllegalArgumentException;

    /**
     * 编译多个文件（File类形式）
     * @param compilationUnits  类文件
     * @return                  编译结果
     */
    CompilationResult compile(Iterable<File> compilationUnits);

    /**
     * 支持的文件类型（文件名后缀）
     * @return      默认的是 .java
     */
    String[] getSupportedFileTypes();
}
