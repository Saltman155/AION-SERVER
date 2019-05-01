package com.superywd.aion.commons.script.description;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

/**
 * 脚本配置描述文件的根元素解析结果
 * @author 迷宫的中心
 * @date 2019/4/18 14:12
 */

@XmlRootElement(name = "scriptList")
@XmlAccessorType(XmlAccessType.NONE)
public class ScriptList {


    @XmlElement(name = "scriptInfo", type = ScriptInfo.class)
    private Set<ScriptInfo> scriptInfos;

    public Set<ScriptInfo> getScriptInfos() {
        return scriptInfos;
    }

    public void setScriptInfos(Set<ScriptInfo> scriptInfos) {
        this.scriptInfos = scriptInfos;
    }

    @Override
    public String toString() {
        return "ScriptList{" +
                "scriptInfos=" + scriptInfos +
                '}';
    }
}
