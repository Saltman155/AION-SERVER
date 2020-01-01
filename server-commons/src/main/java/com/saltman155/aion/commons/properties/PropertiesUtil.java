package com.saltman155.aion.commons.properties;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * 配置文件载入工具类
 * @author: saltman155
 * @date: 2019/1/24 00:36
 */

public class PropertiesUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    private static final String PROPERTIES_FILE_REGEX = "^.+\\.properties$";

    /**
     * 根据配置文件路径，读取配置文件信息并返回
     * @param filePath          配置文件路径
     * @return                  配置类
     * @throws IOException      当文件不存在或读取错误
     */
    public static Properties loadProperties(String filePath) throws IOException{
        return loadProperties(new File(filePath));
    }

    /**
     * 根据文件对象，读取配置文件信息并返回
     * @param file              配置文件路径
     * @return                  配置类
     * @throws IOException      当文件不存在或读取错误
     */
    public static Properties loadProperties(File file) throws IOException{
        FileInputStream inputStream = new FileInputStream(file);
        Properties properties = new Properties();
        properties.load(inputStream);
        inputStream.close();
        return properties;
    }

    /**
     * 根据配置文件目录的路径，读取所有配置文件信息并返回
     * @param dirPath           配置文件目录路径
     * @return                  所有的文件的配置类
     * @throws IOException      io错误
     */
    public static List<Properties> loadAllFromDirectory(String dirPath) throws IOException{
        List<Properties> result = new ArrayList<>();
        return loadAllFromDirectory(new File(dirPath), true,result);
    }

    /**
     * 根据配置文件目录对象，读取所有配置文件信息并返回
     * @param dir               配置文件目录对象
     * @return                  所有的文件的配置类
     * @throws IOException      io错误
     */
    public static List<Properties> loadAllFromDirectory(File dir) throws IOException{
        List<Properties> result = new ArrayList<>();
        return loadAllFromDirectory(dir, false,result);
    }

    /**
     * 从目录或单个文件中读取配置文件到指定容器中
     * @param file              配置文件目录对象
     * @param recursive         是否解析子目录
     * @param result            装载配置文件类的容器
     * @throws IOException      io错误
     */
    public static List<Properties> loadAllFromDirectory(File file, boolean recursive, List<Properties> result) throws IOException {
        if(!file.exists() || result == null){
            return result;
        }
        if(file.isFile() && file.getName().matches(PROPERTIES_FILE_REGEX)){
            result.add(loadProperties(file));
            return result;
        }
        if(file.isDirectory() && recursive && file.listFiles() != null){
            File[] children = file.listFiles();
            assert children != null;
            for(File item: children){
                loadAllFromDirectory(item,true,result);
            }
        }
        return result;
    }

    /**
     * 批量覆盖配置文件类
     * @param initialProperties     被批量覆盖的配置文件集合
     * @param properties            新的来源集合
     * @return                      覆盖完成的配置文件集合
     */
    public static List<Properties> overrideProperties(List<Properties> initialProperties, List<Properties> properties){
        if(properties != null){
            for(Properties item :properties){
                overrideProperties(initialProperties,item);
            }
        }
        return initialProperties;
    }


    /**
     * 批量覆盖配置文件类
     * @param initialProperties         被批量覆盖的配置文件集合
     * @param properties                新的来源
     * @return                          覆盖完成的配置文件集合
     */
    public static List<Properties> overrideProperties(List<Properties> initialProperties, Properties properties){
        if(properties != null){
            for(Properties item :initialProperties){
                item.putAll(properties);
            }
        }
        return initialProperties;
    }
}
