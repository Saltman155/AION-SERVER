package com.aionstar.game.model;


/**
 * 这个枚举类型表示玩家的职业
 * @author saltman155
 * @date 2020/1/30 15:50
 */

public enum PlayerClass {

    //战士
    WARRIOR((byte)0x00,true),
    //剑星
    GLADIATOR((byte)0x01),
    //守护星
    TEMPLAR((byte)0x02),
    //侦查者
    SCOUT((byte)0x03,true),
    //杀星
    ASSASSIN((byte)0x04),
    //弓星
    RANGER((byte)0x05),
    //法师
    MAGE((byte)0x06,true),
    //魔道星
    SORCERER((byte)0x07),
    //精灵星
    SPIRIT_MASTER((byte)0x08),
    //祭司
    PRIEST((byte)0x09, true),
    //护法星
    CLERIC((byte)0x0a),
    //治愈星
    CHANTER((byte)0x0b),
    //所有的
    ALL((byte)0x0c);

    //类型Id
    private byte classId;
    //类型的掩码值，该值可能参与各种类型的操作
    private int idMask;
    //是否是初始职业
    private boolean startingClass;

    PlayerClass(byte classId){
        this(classId,false);
    }

    PlayerClass(byte classId,boolean startingClass){
        this.classId = classId;
        this.idMask = (int) Math.pow(2, classId);
        this.startingClass = startingClass;
    }


}