package com.saltman155.aion.commons.script.compiler.source;

import org.apache.commons.io.FileUtils;

import javax.tools.SimpleJavaFileObject;
import java.io.File;
import java.io.IOException;

/**
 * 表示来源于文件的脚本源码封装类
 * @author: saltman155
 * @date: 2019/4/18 20:35
 */
public class JavaSourceFromFile extends SimpleJavaFileObject {


    public JavaSourceFromFile(File file, Kind kind) {
        super(file.toURI(), kind);
    }

    /**
     * 获取源码内容（用utf-8编码）
     * @param ignoreEncodingErrors  忽略编码错误 未使用
     * @return                      源码
     * @throws IOException          读取错误
     */
    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return FileUtils.readFileToString(new File(this.toUri()), "UTF-8");
    }
}
