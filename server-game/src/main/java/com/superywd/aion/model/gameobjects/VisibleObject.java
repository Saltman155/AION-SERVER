package com.superywd.aion.model.gameobjects;

import com.superywd.aion.model.templates.VisibleObjectTemplate;
import com.superywd.aion.word.KnownList;

/**
 * 此类表示可见对象，它代表在游戏中，可以在某个位置生成的，
 * 能够被其他该类型的对象选中的（如玩家，npc，怪物）。它被其他对象
 * 持有的信息会保存在{@link com.superywd.aion.word.KnownList }中
 *
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
    private KnownList knownlist;



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
