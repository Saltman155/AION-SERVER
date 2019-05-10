package com.superywd.aion.model.engine.ai3.poll;

/**
 * 回应格式接口的一个简单实现
 * @author: saltman155
 * @date: 2019/5/5 20:43
 */
public class SimpleAIAnswer implements AIAnswer {

    private final boolean answer;

    public SimpleAIAnswer(boolean answer) {
        this.answer = answer;
    }

    @Override
    public boolean isPositive() {
        return answer;
    }

    @Override
    public Object getResult() {
        return answer;
    }
}
