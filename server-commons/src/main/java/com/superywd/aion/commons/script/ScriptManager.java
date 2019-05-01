package com.superywd.aion.commons.script;


import com.superywd.aion.commons.script.classlistener.ClassListener;
import com.superywd.aion.commons.script.compiler.ScriptCompiler;
import com.superywd.aion.commons.script.compiler.impl.JavacScriptCompiler;
import com.superywd.aion.commons.script.description.ScriptInfo;
import com.superywd.aion.commons.script.description.ScriptList;
import com.superywd.aion.commons.utils.ArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Set;


/**
 * 脚本引擎启动类
 * @author 迷宫的中心
 * @date 2019/4/18 13:38
 */
public class ScriptManager {

    private static final Logger logger = LoggerFactory.getLogger(ScriptManager.class);

    /**默认的脚本编译器实现*/
    public static final Class<? extends ScriptCompiler> DEFAULT_COMPILER_CLASS = JavacScriptCompiler.class;
    /**已经加载的所有类别的脚本的上下文环境*/
    private Set<ScriptContext> contexts = new HashSet<>();
    /**全局类加载监听器（用于编译结果类的装载与卸载）*/
    private ClassListener globalClassListener;

    /**
     * 读取脚本的配置，启动引擎以装载脚本
     * @param scriptDescriptor          脚本的配置xml文件
     */
    public synchronized void load(File scriptDescriptor) throws Exception {
        FileInputStream fin = new FileInputStream(scriptDescriptor);
        JAXBContext c = JAXBContext.newInstance(ScriptInfo.class, ScriptList.class);
        Unmarshaller u = c.createUnmarshaller();
        ScriptList list = (ScriptList) u.unmarshal(fin);
        fin.close();
        for (ScriptInfo si : list.getScriptInfos()) {
            ScriptContext context = createContext(si, null);
            if (context != null) {
                contexts.add(context);
                context.init();
            }
        }
    }

    private ScriptContext createContext(ScriptInfo scriptInfo,ScriptContext parent){
        //获取指定类别脚本的上下文实例
        ScriptContext context = ScriptContextFactory.getScriptContext(scriptInfo.getRoot(),parent);
        if (parent == null && contexts.contains(context)) {
            logger.warn("同一类别的脚本被重复加载！脚本文件根目录为： {}" + scriptInfo.getRoot().getAbsolutePath());
            return null;
        }
        context.setLibraries(scriptInfo.getLibraries());
        context.setCompilerClassName(scriptInfo.getCompilerClass());
        if (!ArrayUtil.isEmpty(scriptInfo.getChildScriptInfos())) {
            for (ScriptInfo child : scriptInfo.getChildScriptInfos()) {
                createContext(child, context);
            }
        }
        //对于顶层类型的脚本，设置class监听器为全局监听器
        if (parent == null && globalClassListener != null)
            context.setClassListener(globalClassListener);
        return context;
    }


}
