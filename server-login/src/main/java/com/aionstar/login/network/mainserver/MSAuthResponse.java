package com.aionstar.login.network.mainserver;

/**
 * 响应主服务端的验证码
 * @author saltman155
 * @date 2020/1/18 21:27
 */

public enum MSAuthResponse {

    /**验证无误*/
    AUTHED((byte)0x00),
    /**验证失败*/
    NOT_AUTHED((byte)0x01),
    /**验证的id未释放*/
    ALREADY_REGISTER((byte)0x02);

    private byte code;

    MSAuthResponse(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }
}
