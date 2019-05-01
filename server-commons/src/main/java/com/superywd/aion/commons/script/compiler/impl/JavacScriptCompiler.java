package com.superywd.aion.commons.script.compiler.impl;

import com.sun.tools.javac.api.JavacTool;
import com.superywd.aion.commons.script.classloader.ScriptClassLoader;
import com.superywd.aion.commons.script.compiler.ClassFileManager;
import com.superywd.aion.commons.script.compiler.CompilationResult;
import com.superywd.aion.commons.script.compiler.ScriptCompiler;
import com.superywd.aion.commons.script.compiler.source.JavaSourceFromFile;
import com.superywd.aion.commons.script.compiler.source.JavaSourceFromString;
import com.superywd.aion.commons.utils.ArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Javac实现的脚本编译器
 * @author saltman155
 * @date 2019/4/18 13:49
 */
public class JavacScriptCompiler implements ScriptCompiler {

    private static final Logger logger = LoggerFactory.getLogger(JavacScriptCompiler.class);

    /**jdk提供的编译接口*/
    protected final JavaCompiler javaCompiler;
    /**编译的依赖库*/
    protected Iterable<File> libraries;
    /**此编译器关联的classLoader*/
    protected ScriptClassLoader parentClassLoader;

    public JavacScriptCompiler() {
        this.javaCompiler = JavacTool.create();
    }

    @Override
    public void setParentClassLoader(ScriptClassLoader classLoader) {
        this.parentClassLoader = classLoader;
    }

    @Override
    public void setLibraries(Iterable<File> files) {
        this.libraries = files;
    }

    @Override
    public CompilationResult compile(String className, String sourceCode) {
        return compile(new String[] { className }, new String[] { sourceCode });
    }

    @Override
    public CompilationResult compile(String[] className, String[] sourceCode) throws IllegalArgumentException {
        if (className.length != sourceCode.length) {
            throw new IllegalArgumentException("错误的参数，类名称数组与源码数组长度不一致！");
        }
        List<JavaFileObject> compilationUnits = new ArrayList<>();
        for (int i = 0; i < className.length; i++) {
            //将类名与关联的源码包装成JavaFileObject类型
            JavaFileObject compilationUnit = new JavaSourceFromString(className[i], sourceCode[i]);
            compilationUnits.add(compilationUnit);
        }
        //编译
        return doCompilation(compilationUnits);
    }

    @Override
    public CompilationResult compile(Iterable<File> compilationUnits) {
        List<JavaFileObject> list = new ArrayList<JavaFileObject>();
        for (File f : compilationUnits) {
            list.add(new JavaSourceFromFile(f, JavaFileObject.Kind.SOURCE));
        }
        //编译
        return doCompilation(list);
    }

    @Override
    public String[] getSupportedFileTypes() {
        return new String[]{"java"};
    }

    /**
     * 编译细节
     * @param compilationUnits      待编译的文件
     * @return                      编译结果
     */
    protected CompilationResult doCompilation(Iterable<JavaFileObject> compilationUnits) {
        //编译参数
        List<String> options = Arrays.asList("-encoding", "UTF-8", "-g");
        //错误信息收集器
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        //使用自定义的classFileManager来完成编译结果的注入
        ClassFileManager manager = new ClassFileManager(javaCompiler, diagnostics);
        manager.setParentClassLoader(parentClassLoader);
        if(!ArrayUtil.isEmpty(libraries)){
            try {
                manager.addLibraries(libraries);
            } catch (IOException e) {
                logger.error(e.getMessage(),e);
            }
        }
        JavaCompiler.CompilationTask task = javaCompiler
                .getTask(null, manager, diagnostics, options, null, compilationUnits);
        if (!task.call()) {
            //编译失败处理（打印错误日志）
            for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
                System.err.printf("--------------------脚本编译失败--------------------%n");
                System.err.printf("错误代码: %s %n",diagnostic.getCode());
                System.err.printf("类型：%s %n",diagnostic.getKind());
                System.err.printf("位置：%s %n",diagnostic.getPosition());
                System.err.printf("起始位置：%s %n",diagnostic.getStartPosition());
                System.err.printf("结束位置：%s %n",diagnostic.getEndPosition());
                System.err.printf("Source：%s %n",diagnostic.getSource());
                System.err.printf("Message：%s %n",diagnostic.getMessage(null));
                System.err.printf("---------------------------------------------------%n");
            }
            throw new RuntimeException("编译脚本文件失败！");
        }
        ScriptClassLoader classLoader = (ScriptClassLoader) manager.getClassLoader(null);
        Class<?>[] compiledClasses = classNamesToClasses(manager.getCompiledClasses().keySet(), classLoader);
        return new CompilationResult(compiledClasses, classLoader);
    }

    /**
     * 从指定的classLoader中根据类名获取到Class对象
     * @param className         类名列表
     * @param classLoader       指定的类加载器
     * @return
     */
    protected Class<?>[] classNamesToClasses(Collection<String> className, ScriptClassLoader classLoader) {
        Class<?>[] classes = new Class<?>[className.size()];
        int i = 0;
        for (String item : className) {
            try {
                Class<?> clazz = classLoader.loadClass(item);
                classes[i++] = clazz;
            }
            catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return classes;
    }

}
