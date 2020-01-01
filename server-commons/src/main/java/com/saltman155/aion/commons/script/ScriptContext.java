package com.saltman155.aion.commons.script;


import com.saltman155.aion.commons.script.compiler.CompilationResult;
import com.saltman155.aion.commons.script.classlistener.ClassListener;

import java.io.File;
import java.util.Collection;

/**
 * 表示一类脚本的上下文环境
 * @author: saltman155
 * @date: 2019/4/18 13:52
 */
public interface ScriptContext {

    /**
     * 初始化脚本上下文，执行编译，以及编译后的切面方法
     */
    void init();

    /**
     * 安全关闭
     * 卸载所有的已加载的类，保存数据
     */
    void shutdown();

    /**
     * 重新载入脚本上下文
     */
    void reload();

    /**
     * 获取脚本文件读取的根路径
     * @return                  根路径地址
     */
    public File getRoot();

    /**
     * 设置依赖库文件
     * @param files             依赖库文件
     */
    void setLibraries(Iterable<File> files);

    /**
     * 获取依赖库文件
     */
    Iterable<File> getLibraries();

    /**
     * 设置编译器名称
     * @param className         编译器类名
     */
    void setCompilerClassName(String className);

    /**
     * 设置类装卸载监听器
     * @param listener          监听器
     */
    void setClassListener(ClassListener listener);

    /**
     * 获取类装卸在监听器
     */
    ClassListener getClassListener();

    /**
     * 获取脚本编译结果
     * @return                  编译结果
     */
    CompilationResult getCompilationResult();


    /**
     * 获取子级脚本上下文对象
     * @return                  子级脚本上下文对象
     */
    Collection<ScriptContext> getChildScriptContexts();


    /**
     * 添加子级脚本上下文对象
     * 需要注意的是，在一个脚本上下文中，不允许加载多个子级脚本上下文
     * 如果因为错误的配置而导致重复读取，将会打印警告，多余的子级脚本对象将会被忽略
     * @param context           子级脚本上下文对象
     */
    void addChildScriptContext(ScriptContext context);


    /**
     * 脚本上下文对象是否已经初始化
     * @return                  是否已经初始化
     */
    boolean isInitialized();



}
