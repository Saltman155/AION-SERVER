package com.superywd.aion.commons.network.packet;

/**
 * 数据包类型
 *  表示是游戏服务端发出的数据包，还是游戏客户端发出的数据包
 * @author: saltman155
 * @date: 2019/3/25 22:17
 */

public enum PacketType {

    //服务端发出的类型
    SERVER("S"),
    //客户端发出的类型
    CLIENT("C");

    private final String name;

    PacketType(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

}
