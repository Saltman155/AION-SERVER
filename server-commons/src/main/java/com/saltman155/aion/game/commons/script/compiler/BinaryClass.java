package com.saltman155.aion.game.commons.script.compiler;

import com.sun.tools.javac.file.BaseFileObject;

import java.io.*;
import java.net.URI;

/**
 * 这个类实现了BaseFileObject,用于存储编译结果，脚本编译后的字节码会存储在这个类中，并维护一份引用在ClassFileManager的缓存表中
 * 当它通过缓存表被查询时，它对应的Class对象会被设置。
 * @author: saltman155
 * @date: 2019/4/18 17:36
 */
public class BinaryClass extends BaseFileObject {

    /**类名*/
    private final String name;
    /**字节码容器*/
    private final ByteArrayOutputStream byteData = new ByteArrayOutputStream();
    /**对应的类对象*/
    private Class<?> definedClass;

    protected BinaryClass(String name) {
        super(null);
        this.name = name;
    }

    @Override
    public String getShortName() {
        String[] tmp = name.split("\\.");
        return tmp[tmp.length-1];
    }

    @Override
    protected String inferBinaryName(Iterable<? extends File> iterable) {
        return name;
    }

    @Override
    public Kind getKind() {
        //Class类型
        return Kind.CLASS;
    }

    @Override
    public boolean isNameCompatible(String simpleName, Kind kind) {
        return Kind.CLASS.equals(kind);
    }

    @Override
    public URI toUri() {
        //不支持
        throw new UnsupportedOperationException();
    }

    @Override
    public String getName() {
        return name + ".class";
    }

    @Override
    public InputStream openInputStream() throws IOException {
        return new ByteArrayInputStream(byteData.toByteArray());
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        return byteData;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        //不支持
        throw new UnsupportedOperationException();
    }

    @Override
    public Writer openWriter() {
        //不支持
        throw new UnsupportedOperationException();
    }

    @Override
    public long getLastModified() {
        return 0;
    }

    @Override
    public boolean delete() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof BinaryClass){
            return this.name.equals(((BinaryClass) o).name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * 获取这个编译结果类对应的Class对象
     * @return              对应的Class对象
     */
    public Class<?> getDefinedClass() {
        return definedClass;
    }

    /**
     * 设置这个编译结果类对应的Class对象
     * @param definedClass  对饮的Class对象
     */
    public void setDefinedClass(Class<?> definedClass) {
        this.definedClass = definedClass;
    }

    /**
     * 获取编译结果的字节码
     * @return              编译结果（字节码）
     */
    public byte[] getBytes() {
        return byteData.toByteArray();
    }

}
