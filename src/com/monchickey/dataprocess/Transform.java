package com.monchickey.dataprocess;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

import com.google.gson.Gson;

/**
 * 数据类型、进制转换工具类
 * @author monchickey
 *
 */

public class Transform {

    /**
     * 提取字符串中的整数
     * @param str 字符串
     * @return int[]
     */
    public static int[] stringExtractInteger(String str) {
        int[] ints = new int[str.length()];
        int j = 0;
        //遍历赋值
        for(int i = 0;i < str.length();i++){
            char c = str.charAt(i);
            if(c >= 48 && c <= 57){
                ints[j] = Integer.parseInt(String.valueOf(c));
                j++;
            }
        }
        //取出 缩短数组冗余空间
        int[] newInts = new int[j];
        for(int i = 0;i < j;i++) {
            newInts[i] = ints[i];
        }
        return newInts;
    }
    
    /**
     * 整数转成二进制字符串
     * @param n
     * @return
     */
    public static String integer2BinaryString(int n) {
        return Integer.toBinaryString(n);
    }
    
    /**
     * 整数转成16进制字符串
     * @param n
     * @return
     */
    public static String integer2HexString(int n) {
        return Integer.toHexString(n);
    }
    
    /**
     * 整数转换成8进制字符串
     * @param n
     * @return
     */
    public static String integer2OctalString(int n) {
        return Integer.toOctalString(n);
    }
    
    /**
     * 字符串按照指定的进制转换为十进制整数
     * 支持普通字符串、2、8、16等其他进制的字符串
     * 字符串中若有非数字字符则抛出异常，需要提前处理
     * @param s 具体的字符串实体
     * @param base 进制
     * @return 返回转换之后的整数
     */
    public static Integer string2Integer(String s, int base) {
        return Integer.parseInt(s, base);
    }
    
    
    /**
     * 整数转换为字节数组  小端
     * @param i 传入的普通整型数字 长度为4个字节
     * @return 字节数组
     */
    public static byte[] integer2Bytes(int i) {
        byte[] arr = new byte[4];
        for(int j = 0;j < arr.length;j++){
            arr[j] = (byte) ((i >> j*8) & 0xff);
        }
        return arr;
    }
    
    /**
     * 字节数组转换为整数 小端
     * @param b
     * @return 返回对应的整数
     */
    public static int bytes2Integer(byte[] b) {
        return b[0] & 0xff | b[1] & 0xff << 8 | b[2] & 0xff << 16 | b[3] & 0xff << 24;
    }
    
    /**
     * 字符串的编码转换
     * @param s
     * @param origin
     * @param dest
     * @return 正常返回新编码的字符串 异常返回: null
     */
    public static String stringCharsetConv(String s, String origin, String dest) {
        try {
            //按照原编码转换为字节数组
            byte[] bytes = s.getBytes(origin);
            //按照新编码打包为字符串
            return new String(bytes, dest);
        } catch (UnsupportedEncodingException e) {
            //异常返回空
            return null;
        }
    }
    

    /**
     * 将json字符串对象转换为Map
     * @param jsonString
     * @return
     */
    public static Map<String, Object> jsonToMap(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        Map<String, Object> result = new HashMap<String, Object>();
        Iterator<String> iterator = jsonObject.keys();

        while(iterator.hasNext()) {
            String key = iterator.next();
            Object value = jsonObject.get(key);
            result.put(key, value);
        }
        return result;
    }
    
    
    /**
     * 将map转换为json格式的字符串
     * @param maps
     * @return
     */
    public static String mapToJson(Map<String, Object> maps) {
        JSONObject jsonLoad = new JSONObject(maps);
        return jsonLoad.toString();
    }
    
    /**
     * json字符串转换为对象
     * @param json
     * @param classObj
     * @return
     */
    public static Object jsonToObject(String json, Class<?> classObj) {
        return new Gson().fromJson(json, classObj);
    }
    
    /**
     * 对象转换为json字符串
     * @param obj
     * @return
     */
    public static String objectToJson(Object obj) {
        return new Gson().toJson(obj);
    }
    
    
    /**
     * 时间戳转日期字符串
     * @param time 单位为毫秒
     * @return
     */
    public static String timeToString(long time, String dateFormat) {
        // String dateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(new Date(time));
    }
    
    /**
     * 日期字符串转时间戳
     * @param timeStr
     * @return 转换正常返回时间戳, 单位: ms 转换失败返回: 0
     */
    public static long stringToTime(String timeStr, String dateFormat) {
        //String dateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        try {
            return sdf.parse(timeStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    /**
     * 获取当前时间戳 单位: ms
     * @return
     */
    public static long now() {
        return System.currentTimeMillis();
    }
    
}
