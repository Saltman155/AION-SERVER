package com.aionstar.game.model;

/**
 * 角色性别
 * @author saltman155
 * @date 2020/1/31 15:54
 */

public enum Gender {

    MALE(0),

    FEMALE(1);

    private int genderId;

    Gender(int genderId) {
        this.genderId = genderId;
    }

    public int getGenderId() { return genderId; }
}
