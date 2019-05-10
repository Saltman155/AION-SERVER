package com.superywd.aion.model.gameobjects;

import com.superywd.aion.model.templates.VisibleObjectTemplate;
import com.superywd.aion.word.KnownList;
import com.superywd.aion.word.WorldPosition;

/**
 * 此类表示可见对象，它代表在游戏中，可以在某个位置生成的，
 * 能够被其他该类型的对象选中的（如玩家，npc，怪物）。它的
 * 成员变量knownList{@link com.superywd.aion.word.KnownList }中
 * 维护着所有观察到它的对象；它的成员变量target中维护着它观察
 * 到的对象。
 * @author: saltman155
 * @date: 2019/5/1 21:37
 */
public abstract class VisibleObject extends AionObject {

    /**关联的对象模版*/
    protected VisibleObjectTemplate objectTemplate;
    /**玩家可以看到该对象的最大距离*/
    public static final float visibilityDistance = 95;
    /**玩家在Z轴上（高度）可以看到该对象的最大距离*/
    public static final float visibilityZaxis = 95;
    /**对象在地图上的位置*/
    protected WorldPosition position;
    /**此对象的已知列表*/
    private KnownList knownList;
    /**此对象的控制器*/
    private final VisibleObjectController<? extends VisibleObject> controller;
    /**该可见对象观察的其他对象*/
    private VisibleObject target;
    /**此对象的刷新模版*/
    private SpawnTemplate spawn;

    public VisibleObject(int objId, VisibleObjectController<? extends VisibleObject> controller,
                         SpawnTemplate spawnTemplate, VisibleObjectTemplate objectTemplate, WorldPosition position) {
        super(objId);
        this.controller = controller;
        this.position = position;
        this.spawn = spawnTemplate;
        this.objectTemplate = objectTemplate;
    }



    @Override
    public String getName() {
        return null;
    }
}
