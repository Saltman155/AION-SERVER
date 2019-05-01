package com.superywd.aion.commons.callbacks.enhancer;

import com.superywd.aion.commons.callbacks.util.ExitCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

/**
 * 增强类入口
 * @author: saltman155
 * @date: 2018/10/19 00:05
 */

public abstract class CallbackClassFileTransformer implements ClassFileTransformer {

    private static final Logger logger = LoggerFactory.getLogger(CallbackClassFileTransformer.class);

    private static final String ROOT_CLASS_LOADER_NAME = "sun.misc.Launcher$ExtClassLoader";

    /**
     *
     * @param loader
     * @param className
     * @param classBeingRedefined
     * @param protectionDomain
     * @param classfileBuffer
     * @return
     */
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {

        try {
            if(loader == null || ROOT_CLASS_LOADER_NAME.equals(loader.getClass().getName())){
                logger.trace("类 " + className.replace("/",".") +" 是核心类，不需要被代理...");
                return null;
            }
            return transformClass(loader,classfileBuffer);
        } catch (Exception e) {
            Error error=new Error("无法代理类 " + className,e);
            logger.error(error.getMessage(),e);

            //如果出现异常的它是从核心（非脚本）的服务器类
            //就停止服务端的启动
            if (ROOT_CLASS_LOADER_NAME.equals(loader.getClass().getName())) {
                Runtime.getRuntime().halt(ExitCode.CODE_ERROR);
            }
            throw error;
        }
    }


    /**
     * 具体的子类去实现相应的方法
     * @param loader
     * @param clazzBytes
     * @return
     * @throws Exception
     */
    protected abstract byte[] transformClass(ClassLoader loader, byte[] clazzBytes) throws Exception;
}
