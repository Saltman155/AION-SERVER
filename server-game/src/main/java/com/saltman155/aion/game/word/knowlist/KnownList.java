package com.saltman155.aion.game.word.knowlist;

import com.saltman155.aion.game.model.entity.Player;
import com.saltman155.aion.game.model.gameobjects.VisibleObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 该类主要用于维护一个具有察觉能力的对象其察觉到的其他对象
 * @author: saltman155
 * @date: 2019/5/1 22:57
 */
public class KnownList {

    private static final Logger log = LoggerFactory.getLogger(KnownList.class);

    /**该察觉列表所属的对象*/
    protected final VisibleObject owner;
    /**已察觉的对象列表*/
    protected final Map<Integer, VisibleObject> knownObjects = new ConcurrentHashMap<>();
    /**可见的对象列表*/
    protected final Map<Integer, VisibleObject> visibleObjects = new ConcurrentHashMap<>();
    /**已察觉的玩家列表*/
    protected final Map<Integer, Player> knownPlayers = new ConcurrentHashMap<>();
    /**可见的玩家列表*/
    protected final Map<Integer, Player> visiblePlayers = new ConcurrentHashMap<>();

    public KnownList(VisibleObject owner) {
        this.owner = owner;
    }

    /**
     * 更新所有列表
     */
    public void doUpdate(){

    }

    public void clear() {
//        for (VisibleObject object : knownObjects.values()) {
//            object.getKnownList().del(owner, false);
//        }
//        knownObjects.clear();
//        if (knownPlayers != null) {
//            knownPlayers.clear();
//        }
//        visualObjects.clear();
//        if (visualPlayers != null) {
//            visualPlayers.clear();
//        }
    }
}
