package com.saltman155.aion.game.commons.script;

import java.io.File;

/**
 * 脚本上下文实例工厂
 * @author: saltman155
 * @date: 2019/4/18 15:14
 */
public class ScriptContextFactory {

    /**
     * 创建一个脚本上下文实例
     * @param file      需要读取脚本文件的根目录
     * @param parent    父类脚本上下文
     * @return          上下文实例
     */
    public static ScriptContext getScriptContext(File file,ScriptContext parent){
        ScriptContext context;
        if(parent == null){
            context = new ScriptContextImpl(file);
        }else{
            context = new ScriptContextImpl(file,parent);
            parent.addChildScriptContext(context);
        }
        return context;
    }
}
