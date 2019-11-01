package com.saltman155.aion.game.model.engine.ai;


import com.saltman155.aion.game.model.engine.ai.event.AIEventType;
import com.saltman155.aion.game.model.engine.ai.event.AIState;
import com.saltman155.aion.game.model.engine.ai.event.AISubState;
import com.saltman155.aion.game.model.engine.ai.poll.AIAnswer;
import com.saltman155.aion.game.model.engine.ai.poll.AIQuestion;
import com.saltman155.aion.game.model.entity.Player;
import com.saltman155.aion.game.model.gameobjects.Creature;

/**
 * AI顶层接口
 * @author: saltman155
 * @date: 2019/5/1 20:44
 */
public interface AI {

    /**
     * 处理一般事件
     * @param event     事件类型
     */
    void onGeneralEvent(AIEventType event);

    /**
     * 处理活动对象发出的事件
     * @param event     事件类型
     * @param creature  创建者
     */
    void onCreatureEvent(AIEventType event, Creature creature);

    /**
     * 处理自定义事件
     * @param eventId   事件ID
     * @param args      参数
     */
    void onCustomEvent(int eventId, Object... args);

    /**
     * 对话框处理
     * 如果已经处理过，则返回true
     * @param player                发起用户
     * @param dialogId              对话id
     * @param questId
     * @param extendedRewardIndex
     * @return
     */
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

    /**
     * 轮询
     * @param question  查看状态
     * @return
     */
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
