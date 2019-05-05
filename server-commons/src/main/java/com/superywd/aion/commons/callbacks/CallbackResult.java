package com.superywd.aion.commons.callbacks;

/**
 *  这个类用来表示回调函数执行的结果
 * @author: saltman155
 * @date: 2018/10/21 19:10
 */

public class CallbackResult<T> {


    /**
     * 表示被代理的方法将继续执行，将来可能发生的其他回调方法也不会被屏蔽
     */
    public static final int CONTINUE = 0x00;

    /**
     * 表示被代理的方法会被继续执行，但是将来可能发生的其他回调方法不会被执行（被屏蔽）
     */
    public static final int BLOCK_CALLBACKS = 0x01;

    /**
     * 表示被代理的方法将被阻止（blocked）,但是将来可能发生的其他回调方法可以继续执行
     */
    public static final int BLOCK_CALLER = 0x02;

    /**
     * 表示被代理的方法和将来可能发生的其他回调方法都被阻止
     */
    public static final int BLOCK_ALL = 0x01 | 0x02;

    /**
     * 缓存继续执行的实例
     */
    private static final CallbackResult INSTANCE_CONTINUE = new CallbackResult(CONTINUE);

    /**
     * 缓存其他回调方法被阻止的实例
     */
    private static final CallbackResult INSTANCE_BLOCK_CALLBACKS = new CallbackResult(BLOCK_CALLBACKS);

    /**
     * 被代理的原方法的执行结果，仅在调用者被回调阻止时会利用到
     */
    private final T result;

    /**
     * 这个回调类表达的具体结果
     */
    private final int blockPolicy;


    /**
     * 用具体的结果（上面静态声明过的）创建新的回调结果
     * @param blockPolicy 指定的阻止策略
     */
    private CallbackResult(int blockPolicy) {
        this(null, blockPolicy);
    }


    /**
     * 用具体的结果（上面静态声明过的）和回调函数执行的结果创建新的回调结果
     * @param result
     * @param blockPolicy
     */
    private CallbackResult(T result, int blockPolicy) {
        this.result = result;
        this.blockPolicy = blockPolicy;
    }


    /**
     * 获取回调函数执行的结果
     * @return 回调函数执行的结果
     */
    public T getResult() {
        return result;
    }


    /**
     * 判断是否是阻止了其他回调函数的回调结果
     * @return
     */
    public boolean isBlockingCallbacks() {
        return (blockPolicy & BLOCK_CALLBACKS) != 0;
    }


    /**
     * 判断是否是阻止了被代理的方法执行的回调结果
     * @return
     */
    public boolean isBlockingCaller() {
        return (blockPolicy & BLOCK_CALLER) != 0;
    }


    /**
     * 继续执行的回调结果
     * @param <T>
     * @return
     */
    public static <T> CallbackResult<T> newContinue() {
        return INSTANCE_CONTINUE;
    }


    /**
     * 阻止了其他回调函数的回调结果
     * @param <T>
     * @return
     */
    public static <T> CallbackResult<T> newCallbackBlocker() {
        return INSTANCE_BLOCK_CALLBACKS;
    }


    /**
     * 阻止了所有执行的回调结果
     * @param result
     * @param <T>
     * @return
     */
    public static <T> CallbackResult<T> newFullBlocker(T result) {
        return new CallbackResult<T>(result, BLOCK_ALL);
    }



}
