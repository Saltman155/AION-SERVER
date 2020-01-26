package com.aionstar.game.model.templates.world;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 游戏地图模版配置类
 * @author: saltman155
 * @date: 2019/5/1 22:13
 */

@XmlRootElement(name = "map")
@XmlAccessorType(XmlAccessType.NONE)
public class WorldMapTemplate {

    /**地图id*/
    @XmlAttribute(name = "id", required = true)
    protected Integer mapId;

    /**地图名称*/
    @XmlAttribute(name = "name")
    protected String name = "";

    /**多实例配置*/
    @XmlAttribute(name = "twin_count")
    protected int twinCount;

    /**最大用户数*/
    @XmlAttribute(name = "max_user")
    protected int maxUser;

    /**是否是监狱（不可离开）*/
    @XmlAttribute(name = "prison")
    protected boolean prison = false;

    @XmlAttribute(name = "death_level", required = true)
    protected int deathLevel = 0;



}
