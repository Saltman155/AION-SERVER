//package com.superywd.aion.model.templates.npc;
//
//import com.superywd.aion.model.templates.VisibleObjectTemplate;
//import com.superywd.aion.model.templates.stats.NpcStatsTemplate;
//import com.superywd.aion.templates.BoundRadius;
//
//import javax.xml.bind.annotation.*;
//
///**
// * 该类存储从npc_templates.xml加载的每一个节点信息
// * @author: saltman155
// * @date: 2019/5/5 0:45
// */
//
//@XmlAccessorType(XmlAccessType.NONE)
//@XmlRootElement(name = "npc_template")
//public class NpcTemplate extends VisibleObjectTemplate {
//
//    /**NPC的唯一id*/
//    private int npcId;
//
//    @XmlID
//    @XmlAttribute(name = "npc_id", required = true)
//    private void setNpcId(String uid) { npcId = Integer.parseInt(uid);}
//
//    /**NPC等级*/
//    @XmlAttribute(name = "level", required = true)
//    private byte level;
//
//    /**NPC名称id*/
//    @XmlAttribute(name = "name_id", required = true)
//    private int nameId;
//
//    /**NPC名称下面的说明id（控制实际显示）*/
//    @XmlAttribute(name = "title_id")
//    private int titleId;
//
//    /**NPC名称（实际显示由客户端控制）*/
//    @XmlAttribute(name = "name")
//    private String name;
//
//    /**NPC高度（默认为1）*/
//    @XmlAttribute(name = "height")
//    private float height = 1;
//
//    /**NPC类型*/
//    @XmlAttribute(name = "npc_type", required = true)
//    private NpcType npcType;
//
//    /**NPC各种属性，如生命值，经验，攻击力...*/
//    @XmlElement(name = "stats")
//    private NpcStatsTemplate statsTemplate;
//
////    /**NPC的装备（不影响属性，只做外形展示）*/
////    @XmlElement(name = "equipment")
////    private NpcEquippedGear equipment;
//
////    @XmlElement(name = "kisk_stats")
////    private KiskStatsTemplate kiskStatsTemplate;
//
//    @XmlElement(name = "ammo_speed")
//    private int ammoSpeed = 0;
//
////    /**NPC的强度，一共有6种（和血条旁的点有关）*/
////    @XmlAttribute(name = "rank")
////    private NpcRank rank;
//
////    /**NPC的类别 一共有5种（和血条样式有关）*/
////    @XmlAttribute(name = "rating")
////    private NpcRating rating;
//
//    /**NPC仇恨范围*/
//    @XmlAttribute(name = "srange")
//    private int aggroRange;
//
//    /**NPC攻击范围*/
//    @XmlAttribute(name = "arange")
//    private int attackRange;
//
//    /**npc攻击速度（字面翻译）*/
//    @XmlAttribute(name = "arate")
//    private int attackRate;
//
//    /**npc攻击延迟（字面翻译）*/
//    @XmlAttribute(name = "adelay")
//    private int attackDelay;
//
//    /**npc血条尺寸（字面翻译）*/
//    @XmlAttribute(name = "hpgauge")
//    private int hpGauge;
//
////    /**NPC阵营（关系npc之间的仇恨关系）*/
////    @XmlAttribute(name = "tribe")
////    private TribeClass tribe;
//
////    /**NPC采用的AI*/
////    @XmlAttribute(name = "ai")
////    private String ai = AiNames.DUMMY_NPC.getName();
//
////    /**NPC的种族（默认为none，因为有很多怪物）*/
////    @XmlAttribute(name = "race")
////    private Race race = Race.NONE;
//
//    /**不知道是啥*/
//    @XmlAttribute(name = "state")
//    private int state;
//
//    /**npc的体积（碰撞检测用）*/
//    @XmlElement(name = "bound_radius")
//    private BoundRadius boundRadius;
//
////    /**npc类型*/
////    @XmlAttribute(name = "type")
////    private NpcTemplateType npcTemplateType;
////
////    /**npc的欧比斯类型*/
////    @XmlAttribute(name = "abyss_type")
////    private AbyssNpcType abyssNpcType;
////
////    /**与NPC的对话属性（如对话距离，延迟）*/
////    @XmlElement(name = "talk_info")
////    private TalkInfo talkInfo;
////
////    /**npc的掉落信息，不从npc_template中读取*/
////    @XmlTransient
////    private NpcDrop npcDrop;
//
//
//    @Override
//    public int getTemplateId() {
//        return npcId;
//    }
//
//    @Override
//    public String getName() {
//        return name;
//    }
//
//    @Override
//    public int getNameId() {
//        return nameId;
//    }
//
//    @Override
//    public int getState() {
//        return state;
//    }
//
//    @Override
//    public BoundRadius getBoundRadius() {
//        return boundRadius;
//    }
//
//    public float getHeight() {
//        return height;
//    }
//
//    public NpcType getNpcType() {
//        return npcType;
//    }
//
//    public NpcEquippedGear getEquipment() {
//        return equipment;
//    }
//
//    public byte getLevel() {
//        return level;
//    }
//
//    public NpcStatsTemplate getStatsTemplate() {
//        return statsTemplate;
//    }
//
//    public KiskStatsTemplate getKiskStatsTemplate() {
//        return kiskStatsTemplate;
//    }
//
//    public TribeClass getTribe() {
//        return tribe;
//    }
//
//    public String getAi() {
//        //todo: 这里原版做了一些处理
//        return ai;
//    }
//
//    public NpcRank getRank() {
//        return rank;
//    }
//
//    public final NpcRating getRating() {
//        return rating;
//    }
//
//    public int getAggroRange() {
//        return aggroRange;
//    }
//
//    public int getAttackRange() {
//        return attackRange;
//    }
//
//    public void setAttackRange(int value) {
//        this.attackRange = value;
//    }
//
//    public int getAttackRate() {
//        return attackRate;
//    }
//
//    public int getAttackDelay() {
//        return attackDelay;
//    }
//
//    public int getHpGauge() {
//        return hpGauge;
//    }
//
//    public Race getRace() {
//        return race;
//    }
//
//    public NpcTemplateType getNpcTemplateType() {
//        return npcTemplateType != null ? npcTemplateType : NpcTemplateType.NONE;
//    }
//
//    public AbyssNpcType getAbyssNpcType() {
//        return abyssNpcType != null ? abyssNpcType : AbyssNpcType.NONE;
//    }
//
//    public NpcDrop getNpcDrop() {
//        return npcDrop;
//    }
//
//    public void setNpcDrop(NpcDrop npcDrop) {
//        this.npcDrop = npcDrop;
//    }
//
//
//    /**获取NPC的最短喊叫距离*/
//    public int getMinimumShoutRange() {
//        if (aggroRange < 10)
//            return 10;
//        return aggroRange;
//    }
//
//    /**获取NPC的最短交谈距离*/
//    public final int getTalkDistance() {
//        //缺省值是2
//        if (talkInfo == null)
//            return 2;
//        return talkInfo.getDistance();
//    }
//
//    /**获取NPC的交谈延迟时间*/
//    public int getTalkDelay() {
//        //缺省值是0
//        if (talkInfo == null)
//            return 0;
//        return talkInfo.getDelay();
//    }
//
//    /**判断该npc是否是可交谈的（存在talkInfo表示可交谈）*/
//    public boolean canInteract() {
//        return talkInfo != null;
//    }
//
//
//}
