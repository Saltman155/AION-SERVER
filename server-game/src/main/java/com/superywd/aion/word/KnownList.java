package com.superywd.aion.word;

import com.superywd.aion.model.gameobjects.VisibleObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 该类主要用于维护一个具有察觉能力的对象其察觉到的其他对象
 * @author: saltman155
 * @date: 2019/5/1 22:57
 */
public class KnownList {

    private static final Logger log = LoggerFactory.getLogger(KnownList.class);

    /**该察觉列表所属的对象*/
    protected final VisibleObject owner;
    /**察觉的对象列表*/
    protected final Map<Integer, VisibleObject> knownObjects = new HashMap<>();
    /**察觉的对象列表*/
    protected final Map<Integer, VisibleObject> visibleObjects = new HashMap<>();
    /**察觉的玩家列表*/
    protected final Map<Integer, Player> knownPlayers = new HashMap<>();

    /**修改同步锁*/
    private ReentrantLock lock = new ReentrantLock();

    public KnownList(VisibleObject owner) {
        this.owner = owner;
    }

    /**
     * 更新所有的察觉列表
     */
    public void doUpdate(){

    }

    public void clear() {
        for (VisibleObject object : knownObjects.values()) {
            object.getKnownList().del(owner, false);
        }
        knownObjects.clear();
        if (knownPlayers != null) {
            knownPlayers.clear();
        }
        visualObjects.clear();
        if (visualPlayers != null) {
            visualPlayers.clear();
        }
    }
}
