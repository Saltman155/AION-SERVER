package com.saltman155.aion.game.templates;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * 三维信息类
 * 用于读取配置文件中对三维碰撞方面的设置以及碰撞的计算
 * @author: saltman155
 * @date: 2019/5/1 22:35
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BoundRadius")
public class BoundRadius {

    public static final BoundRadius DEFAULT = new BoundRadius(0f, 0f, 0f);

    /**正面*/
    @XmlAttribute
    private double front;
    /**侧面*/
    @XmlAttribute
    private double side;
    /**上面*/
    @XmlAttribute
    private double upper;

    /**碰撞信息*/
    private double collision;

    public BoundRadius() { }

    public BoundRadius(float front, float side, float upper) {
        this.front = front;
        this.side = side;
        this.upper = upper;
        calculateCollision(front, side);
    }

    protected void calculateCollision(float front, float side) {
        this.collision = (float) Math.sqrt(side * front);
    }

    public double getFront() {
        return front;
    }

    public double getSide() {
        return side;
    }

    public double getUpper() {
        return upper;
    }

    public double getCollision() {
        return collision;
    }
}
