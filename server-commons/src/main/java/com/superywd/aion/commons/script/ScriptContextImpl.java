package com.superywd.aion.commons.script;

import com.superywd.aion.commons.script.classlistener.AggregatedClassListener;
import com.superywd.aion.commons.script.classlistener.ClassListener;
import com.superywd.aion.commons.script.classlistener.OnClassLoadUnloadListener;
import com.superywd.aion.commons.script.compiler.CompilationResult;
import com.superywd.aion.commons.script.compiler.ScriptCompiler;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 脚本上下文具体实现
 * @author 迷宫的中心
 * @date 2019/4/18 15:16
 */
public class ScriptContextImpl implements ScriptContext {

    private static final Logger logger = LoggerFactory.getLogger(ScriptContextImpl.class);

    /**父类型脚本上下文对象*/
    private final ScriptContext parentScriptContext;
    /**脚本文件根节点*/
    private final File root;
    /**依赖的包*/
    private Iterable<File> libraries;
    /**编译结果*/
    private CompilationResult compilationResult;
    /**子类型脚本上下文对象*/
    private Set<ScriptContext> childScriptContexts;
    /**类装载监听器*/
    private ClassListener classListener;
    /**编译器名称*/
    private String compilerClassName;

    {
        //默认注册加载卸载注解检查监听器
        AggregatedClassListener listener = new AggregatedClassListener();
        listener.addClassListener(new OnClassLoadUnloadListener());
        this.classListener = listener;
    }

    public ScriptContextImpl(File root){
        this(root, null);
    }

    public ScriptContextImpl(File root, ScriptContext parent){
        if(root == null){
            throw new NullPointerException("根路径不存在...");
        }
        if (!root.exists() || !root.isDirectory()) {
            throw new IllegalArgumentException(String.format("不是正确的根路径: %s",root.getAbsolutePath()));
        }
        this.root = root;
        this.parentScriptContext = parent;
    }


    @Override
    public void init() {
        if (compilationResult != null) {
            logger.error("脚本引擎需要编译器...");
            return;
        }
        ScriptCompiler scriptCompiler = instantiateCompiler();
        //获取到根路径下所有的文件
        Collection<File> files = FileUtils.listFiles(root, scriptCompiler.getSupportedFileTypes(), true);
        //设置父级类加载器
        if (parentScriptContext != null) {
            scriptCompiler.setParentClassLoader(parentScriptContext.getCompilationResult().getClassLoader());
        }
        scriptCompiler.setLibraries(libraries);
        //编译
        compilationResult = scriptCompiler.compile(files);
        //调用切面方法
        getClassListener().postLoad(compilationResult.getCompiledClasses());
        //处理子类脚本上下文的初始化
        if (childScriptContexts != null) {
            for (ScriptContext context : childScriptContexts) {
                context.init();
            }
        }
    }

    @Override
    public void shutdown() {
        synchronized (this) {
            if (compilationResult == null) {
                logger.error("关闭异常！脚本上下文没有有效的编译结果！");
                return;
            }
            //先关子级脚本的
            if (childScriptContexts != null) {
                for (ScriptContext child : childScriptContexts) {
                    child.shutdown();
                }
            }
            getClassListener().preUnload(compilationResult.getCompiledClasses());
            compilationResult = null;
        }
    }

    @Override
    public void reload() {
        shutdown();
        init();
    }

    @Override
    public File getRoot() {
        return root;
    }

    @Override
    public void setLibraries(Iterable<File> files) {
        this.libraries = files;
    }

    @Override
    public Iterable<File> getLibraries() {
        return libraries;
    }

    @Override
    public void setCompilerClassName(String className) {
        this.compilerClassName = className;
    }

    @Override
    public void setClassListener(ClassListener listener) {
        this.classListener = listener;
    }

    @Override
    public ClassListener getClassListener() {
        return classListener;
    }

    @Override
    public CompilationResult getCompilationResult() {
        return compilationResult;
    }

    @Override
    public Collection<ScriptContext> getChildScriptContexts() {
        return childScriptContexts;
    }

    @Override
    public void addChildScriptContext(ScriptContext context) {
        synchronized (this) {
            if (childScriptContexts == null) {
                childScriptContexts = new HashSet<>();
            }
            if (childScriptContexts.contains(context)) {
                logger.error("子级脚本对象被重复加载！子级脚本对象加载文件的根路径为" + context.getRoot().getAbsolutePath());
                return;
            }
            if (isInitialized()) {
                context.init();
            }
        }
        childScriptContexts.add(context);
    }

    @Override
    public boolean isInitialized() {
        synchronized (this) {
            return compilationResult != null;
        }
    }

    /**
     * 实例化编译器
     * @return                      编译器对象
     */
    protected ScriptCompiler instantiateCompiler() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        if (getParentScriptContext() != null) {
            classLoader = getParentScriptContext().getCompilationResult().getClassLoader();
        }
        ScriptCompiler compiler;
        try {
            compiler = (ScriptCompiler) Class.forName(getCompilerClassName(),true,classLoader).newInstance();
        } catch (Exception e) {
            logger.error("编译器 {} 加载失败!",getCompilerClassName());
            logger.error(e.getMessage(),e);
            throw new RuntimeException(e);
        }
        return compiler;
    }

    public ScriptContext getParentScriptContext() {
        return parentScriptContext;
    }

    public String getCompilerClassName() {
        return compilerClassName;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof ScriptContextImpl)){
            return false;
        }
        ScriptContextImpl another = (ScriptContextImpl) obj;
        //当存在父类上下文时，还需要再比较父类上下文是否也是一致的
        if (parentScriptContext == null) {
            return another.getRoot().equals(root);
        }
        return another.getRoot().equals(root) && parentScriptContext.equals(another.parentScriptContext);
    }

    @Override
    public int hashCode() {
        int result = parentScriptContext != null ? parentScriptContext.hashCode() : 0;
        result = 31 * result + root.hashCode();
        return result;
    }

    @Override
    public void finalize() throws Throwable {
        if (compilationResult != null) {
            logger.error("回收也要按基本法呀！你这都没执行销毁方法你就回收了？？？还好我有finalize方法做补充...");
            shutdown();
            logger.error("好了，程序逻辑有问题！得改改！");
        }
        super.finalize();
    }
}
