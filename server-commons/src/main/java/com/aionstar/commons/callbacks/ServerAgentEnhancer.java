package com.aionstar.commons.callbacks;


import com.aionstar.commons.callbacks.enhancer.GlobalCallbackEnhancer;
import com.aionstar.commons.callbacks.enhancer.ObjectCallbackEnhancer;

import java.lang.instrument.Instrumentation;

/**
 *  服务端java探针技术入口类
 * @author: saltman155
 * @date: 2018/10/18 23:58
 */

public class ServerAgentEnhancer {

    public static void premain(String args, Instrumentation instrumentation){
        instrumentation.addTransformer(new ObjectCallbackEnhancer(),true);
        instrumentation.addTransformer(new GlobalCallbackEnhancer(),true);
    }
}
