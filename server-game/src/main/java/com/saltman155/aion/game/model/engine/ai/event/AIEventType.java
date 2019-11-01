package com.saltman155.aion.game.model.engine.ai.event;

/**
 * 该类用于声明所有的AI事件&动作
 * @author: saltman155
 * @date: 2019/5/1 21:03
 */
public enum AIEventType {

    /**启用对象*/
    ACTIVATE,
    /**休眠对象*/
    DEACTIVATE,
    /**冻结对象*/
    FREEZE,
    /**解冻对象*/
    UNFREEZE,

    /**对象正在进行攻击*/
    ATTACK,
    /**对象完成了攻击*/
    ATTACK_COMPLETE,
    /**对象结束了攻击*/
    ATTACK_FINISH,
    /**对象需要被帮助*/
    CREATURE_NEEDS_SUPPORT,

    /**移动验证*/
    MOVE_VALIDATE,
    /**到达移动的地方*/
    MOVE_ARRIVED,

    /**对象察觉*/
    CREATURE_SEE,
    /**对象没有察觉*/
    CREATURE_NOT_SEE,
    /**对象在移动*/
    CREATURE_MOVED,
    /**对象仇恨*/
    CREATURE_AGGRO,

    /**对象刷新*/
    SPAWNED,
    /**对象重复刷新*/
    RESPAWNED,
    /**对象结束刷新*/
    DESPAWNED,
    /**对象死亡*/
    DIED,

    /**到达目标*/
    TARGET_REACHED,
    /**目标过远*/
    TARGET_TOOFAR,
    /**放弃当前目标*/
    TARGET_GIVEUP,
    /**更改当前目标*/
    TARGET_CHANGED,
    /**跟随当前目标*/
    TARGET_FOLLOW,
    /**停止跟随当前目标*/
    TARGET_STOP_FOLLOW,

    /**对象不在初始点*/
    NOT_AT_HOME,
    /**对象返回初始点*/
    BACK_HOME,

    /**对话开始*/
    DIALOG_START,
    /**对话结束*/
    DIALOG_FINISH,

    /**掉落注册*/
    DROP_REGISTERED

}
