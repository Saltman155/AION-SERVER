package com.superywd.aion.model.engine.ai3.callback;

import com.superywd.aion.commons.callbacks.Callback;
import com.superywd.aion.commons.callbacks.CallbackResult;
import com.superywd.aion.model.engine.ai3.AbstractAI;
import com.superywd.aion.model.engine.ai3.event.AIEventType;

/**
 * 该类是类{@link com.superywd.aion.model.engine.ai3.AbstractAI}
 * 在调用方法 handleGeneralEvent 时（处理一般事件）的代理方法类
 * @author: saltman155
 * @date: 2019/5/6 21:25
 */
public abstract class OnHandleAIGeneralEvent implements Callback<AbstractAI> {

    @Override
    public CallbackResult beforeCall(AbstractAI obj, Object[] args) {
        //继续执行
        return CallbackResult.newContinue();
    }

    @Override
    public CallbackResult afterCall(AbstractAI obj, Object[] args, Object methodResult) {
        AIEventType eventType = (AIEventType) args[0];
        onAIHandleGeneralEvent(obj, eventType);
        return CallbackResult.newContinue();
    }

    @Override
    public Class<? extends Callback> getBaseClass() {
        return OnHandleAIGeneralEvent.class;
    }

    protected abstract void onAIHandleGeneralEvent(AbstractAI obj, AIEventType eventType);

}
