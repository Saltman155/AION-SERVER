package com.superywd.aion.model.engine.ai3;

import com.superywd.aion.model.engine.ai3.enums.AIEventType;
import com.superywd.aion.model.engine.ai3.enums.AIState;
import com.superywd.aion.model.gameobjects.Creature;
import com.superywd.aion.model.gameobjects.player.Player;

/**
 * @author: saltman155
 * @date: 2019/5/1 20:44
 */
public interface AI3 {

    /**一般事件*/
    void onGeneralEvent(AIEventType event);

    /**生物事件*/
    void onCreatureEvent(AIEventType event, Creature creature);

    /**自定义事件*/
    void onCustomEvent(int eventId, Object... args);

    /**对话框处理 处理完成返回true*/
    boolean onDialogSelect(Player player, int dialogId, int questId, int extendedRewardIndex);

    /**思考*/
    void think();

    /**判断该AI是否是可以*/
    boolean canThink();

    /**获取AI当前状态*/
    AIState getState();
}
