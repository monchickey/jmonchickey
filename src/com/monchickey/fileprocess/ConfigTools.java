package com.monchickey.fileprocess;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * 配置文件读取工具类
 * @author monchickey
 *
 */

public class ConfigTools {
    
    /**
     * 读取指定配置文件的指定配置项的值
     * @param configFileName 配置文件在文件系统的路径 类型:.properties
     * @param key 配置项名称
     * @param charset 取决于properties文件的编码 是utf8的就写UTF-8
     * @return
     *      返回配置项对应的值 如果出现异常返回null
     */
    public static String getConfig(String configFileName, String key, String charset) {
        Properties pro = new Properties();
        String configValue = null;
        try {
            FileInputStream in = new FileInputStream(configFileName);
            pro.load(in);
            configValue = pro.getProperty(key);
            // 编码转换
            configValue = new String(configValue.getBytes("ISO-8859-1"), charset);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return configValue;
    }
}
