package com.superywd.aion.dataholders;

import com.superywd.aion.model.templates.npc.NpcTemplate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 这是一个包含所有的NpcTemplate的实例的容器。
 * 每一类npc都会被 {@link NpcTemplate} 所定义，包括它的各种属性，id，种族，阵营，个体属性，装备...
 * @author: saltman155
 * @date: 2019/5/5 0:40
 */

@XmlRootElement(name = "npc_templates")
@XmlAccessorType(XmlAccessType.FIELD)
public class NpcData {

    @XmlElement(name = "npc_template")
    private List<NpcTemplate> npcList;

    /**包含所有NPC的id与具体对象映射表*/
    private Map<Integer,NpcTemplate> NpcMap = new HashMap<>();
}
