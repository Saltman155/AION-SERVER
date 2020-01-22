package com.aionstar.login.exception;

/**
 * @author saltman155
 * @date 2020/1/18 23:26
 */

public class MSAlreadyRegisterException extends Exception {

    private byte id;
    private String name;

    public MSAlreadyRegisterException(byte id,String name){
        super();
        this.id = id;
        this.name = name;
    }

    @Override
    public String getMessage() {
        return String.format("主服务器 %s : %s 已经注册！",id,name);
    }
}
