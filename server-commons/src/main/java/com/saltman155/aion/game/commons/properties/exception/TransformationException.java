package com.saltman155.aion.game.commons.properties.exception;

/**
 * 描述在类型值注入时可能抛出的异常
 * @author: saltman155
 * @date: 2019/3/1 23:21
 */

public class TransformationException extends RuntimeException {

    private static final long serialVersionUID = -6641235751743285902L;


    public TransformationException() {
    }

    public TransformationException(String message) {
        super(message);
    }

    public TransformationException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransformationException(Throwable cause) {
        super(cause);
    }
}
