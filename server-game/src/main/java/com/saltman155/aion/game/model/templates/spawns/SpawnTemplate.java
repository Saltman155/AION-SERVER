package com.saltman155.aion.game.model.templates.spawns;

/**
 * 该类表示一个可视对象 ${@link com.saltman155.aion.game.model.gameobjects.VisibleObject }的刷新模板
 * 具体的刷新模板类型，应该根据可视对象的具体类型而定
 * @author saltman155
 * @date 2019/10/26 17:12
 */

public class SpawnTemplate {

    private float x;
    private float y;
    private float z;
    private byte h;
    private int staticId;
    private int randomWalk;
    private String walkerId;
    private int walkerIdx;
}
