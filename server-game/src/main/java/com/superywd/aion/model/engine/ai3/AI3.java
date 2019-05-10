package com.superywd.aion.model.engine.ai3;

import com.superywd.aion.model.engine.ai3.event.AIEventType;
import com.superywd.aion.model.engine.ai3.event.AIState;
import com.superywd.aion.model.engine.ai3.event.AISubState;
import com.superywd.aion.model.engine.ai3.poll.AIAnswer;
import com.superywd.aion.model.engine.ai3.poll.AIQuestion;
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

    /**判断该AI是否能够思考*/
    boolean canThink();

    /**获取AI名称*/
    String getName();

    /**获取AI当前状态*/
    AIState getState();

    /**获取AI当前子状态*/
    AISubState getSubState();

    /**调查AI具体的状态*/
    boolean poll(AIQuestion question);

    /**询问AI*/
    AIAnswer ask(AIQuestion question);

    /**获取AI剩余时间*/
    boolean getRemainingTime();

    /**伤害修改*/
    int modifyDamage(int damage);

    /**所有者伤害修改*/
    int modifyOwnerDamage(int damage);

    /**生命值修改*/
    int modifyHealValue(int value);

}
