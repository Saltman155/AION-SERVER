package com.superywd.aion.model.engine.ai3.poll;

/**
 * AI对相应调查{@link AIQuestion}的回应格式
 *
 * @author: saltman155
 * @date: 2019/5/5 20:32
 */
public interface AIAnswer {

    /**
     * 获取调查可行性
     * @return  true OR false
     */
    boolean isPositive();

    /**
     * 获取调查结果
     * @return  obj
     */
    Object getResult();
}
