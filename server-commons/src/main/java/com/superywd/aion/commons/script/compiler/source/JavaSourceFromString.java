package com.superywd.aion.commons.script.compiler.source;

import javax.tools.SimpleJavaFileObject;
import java.io.IOException;
import java.net.URI;

/**
 * 表示来源于字符串的脚本源码封装类
 * @author 迷宫的中心
 * @date 2019/4/18 20:39
 */
public class JavaSourceFromString extends SimpleJavaFileObject {

    private final String code;

    public JavaSourceFromString(String className, String code) {
        //这里因为直接给的是字符串，所以用字符串协议
        super(URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension),
                Kind.SOURCE);
        this.code = code;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return this.code;
    }
}
