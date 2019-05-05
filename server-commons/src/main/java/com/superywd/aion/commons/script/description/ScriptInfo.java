package com.superywd.aion.commons.script.description;

import com.superywd.aion.commons.script.ScriptManager;

import javax.xml.bind.annotation.*;
import java.io.File;
import java.util.Set;

/**
 * 这个类表示脚本配置文件的 scriptInfo解析结果
 * @author: saltman155
 * @date: 2019/4/18 14:02
 */
@XmlRootElement(name = "scriptInfo")
@XmlAccessorType(XmlAccessType.NONE)
public class ScriptInfo {

    /**脚本文件的根目录*/
    @XmlAttribute(required = true)
    private File root;
    /**脚本文件依赖的库文件*/
    @XmlElement(name = "library")
    private Set<File> libraries;
    /**子脚本加载文件*/
    @XmlElement(name = "childScriptInfo",type = ScriptInfo.class)
    private Set<ScriptInfo> childScriptInfos;
    /**默认的脚本编译器*/
    @XmlElement(name = "compiler")
    private String compilerClass = ScriptManager.DEFAULT_COMPILER_CLASS.getName();

    public File getRoot() {
        return root;
    }

    public void setRoot(File root) {
        this.root = root;
    }

    public Set<File> getLibraries() {
        return libraries;
    }

    public void setLibraries(Set<File> libraries) {
        this.libraries = libraries;
    }

    public Set<ScriptInfo> getChildScriptInfos() {
        return childScriptInfos;
    }

    public void setChildScriptInfos(Set<ScriptInfo> childScriptInfos) {
        this.childScriptInfos = childScriptInfos;
    }

    public String getCompilerClass() {
        return compilerClass;
    }

    public void setCompilerClass(String compilerClass) {
        this.compilerClass = compilerClass;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof ScriptInfo)){
            return false;
        }
        return this.root.equals(((ScriptInfo) obj).root);
    }

    @Override
    public int hashCode() {
        return this.root.hashCode();
    }

    @Override
    public String toString() {
        return "ScriptInfo{" +
                "root=" + root +
                ", libraries=" + libraries +
                ", childScriptInfos=" + childScriptInfos +
                ", compilerClass='" + compilerClass + '\'' +
                '}';
    }
}
