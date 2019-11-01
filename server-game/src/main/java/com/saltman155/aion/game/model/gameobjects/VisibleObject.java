package com.saltman155.aion.game.model.gameobjects;

import com.saltman155.aion.game.model.templates.VisibleObjectTemplate;
import com.saltman155.aion.game.model.templates.spawns.SpawnTemplate;
import com.saltman155.aion.game.word.WorldPosition;
import com.saltman155.aion.game.word.knowlist.KnownList;

/**
 * 此类表示可见对象，它代表在游戏中，可以在某个位置生成的，
 * 能够被其他该类型的对象选中的（如玩家，npc，怪物）事物。
 * @author: saltman155
 * @date: 2019/5/1 21:37
 */
public abstract class VisibleObject extends AionObject {

    /**玩家可以看到该对象的最大距离*/
    public static final float visibilityDistance = 95;
    /**玩家在Z轴上（高度）可以看到该对象的最大距离*/
    public static final float visibilityZAxis = 95;

    /**此对象具体的对象模版（它的具体类型，由子类赋予）*/
    protected VisibleObjectTemplate objectTemplate;
    /**此对象的刷新模版*/
    private SpawnTemplate spawn;
    /**此对象在地图上的位置*/
    protected WorldPosition position;
    /**此对象观察&选中的其他对象*/
    private VisibleObject target;
    /**此对象已经察觉到的其他对象*/
    private KnownList knownList;

    public VisibleObject(Integer objectId, VisibleObjectTemplate objectTemplate, SpawnTemplate spawn, WorldPosition position) {
        super(objectId);
        this.objectTemplate = objectTemplate;
        this.spawn = spawn;
        this.position = position;
    }

    @Override
    public String getName() {
        return null;
    }
}
