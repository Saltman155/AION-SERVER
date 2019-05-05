package com.superywd.aion.model.templates.stats;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 该类用于描述NPC的战斗属性
 * @author: saltman155
 * @date: 2019/5/5 1:54
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "npc_stats_template")
public class NpcStatsTemplate {

    @XmlAttribute(name = "run_speed_fight")
    private float runSpeedFight;
    @XmlAttribute(name = "pdef")
    private int pdef;
    @XmlAttribute(name = "mdef")
    private int mdef;
    @XmlAttribute(name = "mresist")
    private int mresist;
    @XmlAttribute(name = "crit")
    private int crit;
    @XmlAttribute(name = "accuracy")
    private int accuracy;
    @XmlAttribute(name = "power")
    private int power;
    @XmlAttribute(name = "maxXp")
    private int maxXp;
}
