package com.superywd.aion.model.engine.ai3;

import com.superywd.aion.commons.script.ScriptManager;
import com.superywd.aion.commons.script.classlistener.AggregatedClassListener;
import com.superywd.aion.commons.script.classlistener.ClassListener;
import com.superywd.aion.commons.script.classlistener.OnClassLoadUnloadListener;
import com.superywd.aion.commons.script.classlistener.ScheduledTaskClassListener;
import com.superywd.aion.commons.utils.ClassUtil;
import com.superywd.aion.model.engine.GameEngine;
import com.superywd.aion.model.engine.ai3.metadata.AIName;
import com.superywd.aion.model.gameobjects.Creature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * 基于AI2改良的第三代AI引擎
 * @author: saltman155
 * @date: 2019/5/1 20:39
 */
public class AI3Engine implements GameEngine {

    private static final Logger logger = LoggerFactory.getLogger(AI3Engine.class);

    /**AI脚本配置文件*/
    private static final String INSTANCE_DESCRIPTOR_FILE = "./data/scripts/system/ai_handlers.xml";
    /**脚本编译器*/
    private static ScriptManager scriptManager = new ScriptManager();
    /**被载入的AI脚本*/
    private final Map<String, Class<? extends AbstractAI>> aiMap = new HashMap<String, Class<? extends AbstractAI>>();

    @Override
    public void load(CountDownLatch progressLatch) {
        logger.info("游戏AI控制引擎载入...");
        //声明载入的监听器
        AggregatedClassListener acl = new AggregatedClassListener();
        //装卸载注解监听器
        acl.addClassListener(new OnClassLoadUnloadListener());
        //定时任务注解监听器
        acl.addClassListener(new ScheduledTaskClassListener());
        //AI3类装载监听器
        acl.addClassListener(new AI3HandlerClassListener());
        scriptManager.setGlobalClassListener(acl);
        try{
            scriptManager.load(new File(INSTANCE_DESCRIPTOR_FILE));
            logger.info("本次共计载入 " + aiMap.size() + " 个AI逻辑处理脚本!");
            //验证脚本
            validateScripts();
        }catch (Exception e){
            logger.error("AI3游戏引擎启动失败！");
            logger.error(e.getMessage(),e);
            throw new Error(e);
        }finally {
            //计数同步锁自减
            if (progressLatch != null) {
                progressLatch.countDown();
            }
        }
    }

    @Override
    public void shutdown() {

    }

    /**
     * 验证所有载入的脚本
     */
    private void validateScripts() {
        //TODO:从所有NPC的AI配置中查询是否有不存在的AI被配置了
    }

    /**
     * 为指定的生物设置其对应的AI
     * @param name      AI名称
     * @param owner     指定的生物
     * @return          绑定结果
     */
    public final AI3 setupAI(String name, Creature owner) {
        AbstractAI aiInstance = null;
        try{
            aiInstance = aiMap.get(name).newInstance();
            aiInstance.
        } catch (Exception e) {
            logger.error("AI设置失败！");
            logger.error(e.getMessage(),e);
        }
        return aiInstance;
    }

    /**
     * 加载脚本类文件到引擎
     * @param clazz     脚本类文件
     */
    private void registerAI(Class<? extends AbstractAI> clazz) {
        AIName nameAnnotation = clazz.getAnnotation(AIName.class);
        if (nameAnnotation != null) {
            aiMap.put(nameAnnotation.name(), clazz);
        }
    }


    private static class SingletonHolder {
        private static final AI3Engine instance = new AI3Engine();
    }

    /**单例对象获取方法*/
    public static AI3Engine getInstance(){
        return SingletonHolder.instance;
    }

    /**
     * 该类作为在逻辑脚本被编译时，将编译结果存入aiMap中的监听器
     */
    private static class AI3HandlerClassListener implements ClassListener{

        @Override
        @SuppressWarnings("unchecked")
        public void postLoad(Class<?>[] classes) {
            for(Class<?> clazz : classes){
                logger.debug("载入AI脚本类 "+ clazz.getName());
                if(isValidClass(clazz)){
                    continue;
                }
                if(ClassUtil.isSubclass(clazz,AbstractAI.class)){
                    Class<? extends AbstractAI> tmp = (Class<? extends AbstractAI>) clazz;
                    AI3Engine.getInstance().registerAI(tmp);
                }
            }
        }

        @Override
        public void preUnload(Class<?>[] classes) {
            for(Class<?> clazz : classes){
                logger.debug("类 " + clazz.getName() + "被卸载...");
            }
        }

        /**
         * 判断类是否是可使用的
         * 抽象、接口、非公共的类将不会放置到ClassMap中
         * @param clazz     带判断的类
         * @return  是否可用
         */
        private boolean isValidClass(Class<?> clazz){
            final int modifiers = clazz.getModifiers();
            return !Modifier.isAbstract(modifiers)
                    && !Modifier.isInterface(modifiers)
                    && Modifier.isPublic(modifiers);
        }
    }

}



