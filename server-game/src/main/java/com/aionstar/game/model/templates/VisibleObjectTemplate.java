package com.aionstar.game.model.templates;

import com.aionstar.game.templates.BoundRadius;

/**
 * 该类是所有“可见对象”的模版类的基类，如npc、房子、玩家、宠物等模板类都应该继承它
 * @author: saltman155
 * @date: 2019/5/1 22:27
 */
public abstract class VisibleObjectTemplate {

    /**
     * 获取配置文件中设置的模版id
     *   如对于npc，它返回的是npc_templates.xml中配置中的npcId
     * @return  id
     */
    public abstract int getTemplateId();

    /**
     * 获取配置文件中关于名称的设置
     * @return  名称
     */
    public abstract String getName();

    /**
     * 获取名称id
     * @return  名称id
     */
    public abstract int getNameId();

    /**
     * 三维设置（碰撞检查用）
     * @return  三维大小设置（默认为0,0,0）
     */
    public BoundRadius getBoundRadius() {
        return BoundRadius.DEFAULT;
    }

    /**
     * 获取状态
     *  默认返回为0
     * @return  状态值
     */
    public int getState(){
        return 0;
    }
}
