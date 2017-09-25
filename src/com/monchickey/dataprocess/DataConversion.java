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

public class DataConversion {
    
    /**
     * 整数转成二进制字符串
     * 简单封装 无其他意义
     * @param n
     * @return
     */
    public String integer2BinaryString(int n) {
        return Integer.toBinaryString(n);
    }
    
    /**
     * 整数转成16进制字符串
     * @param n
     * @return
     */
    public String integer2HexString(int n) {
        return Integer.toHexString(n);
    }
    
    /**
     * 整数转换成8进制字符串
     * @param n
     * @return
     */
    public String integer2OctalString(int n) {
        return Integer.toOctalString(n);
    }
    
    /**
     * 进制字符串转换为10进制整数
     * 支持普通字符串、2、8、16等其他进制的字符串
     * 字符串中若有非数字字符则抛出异常，需要提前处理
     * @param s 具体的字符串实体
     * @param oca_number 进制数
     * @return 返回转换之后的整数
     */
    public Integer string2Integer(String s, int oca_number) {
        return Integer.parseInt(s, oca_number);
    }
    
    
    /**
     * 整数转换为字节数组
     * @param i 传入的普通整型数字 长度为4个字节
     * @return 字节数组
     */
    public byte[] int2Bytes(int i) {
        byte[] arr = new byte[4];
        for(int j = 0;j < arr.length;j++){
            arr[j] = (byte) ((i >> j*8) & 0xff);
        }
        return arr;
    }
    
    /**
     * 字节数组转换为整数
     * @param b
     * @return 返回对应的整数
     */
    public int bytes2Int(byte[] b) {
        int result = 0;
        for(int i = 0;i < b.length;i++){
            result += (int) ((b[i] & 0xff) << i*8);
        }
        return result;
    }
    
    /**
     * 字节数组转换为字符串
     * @param b
     * @return
     */
    public String bytes2String(byte[] b) {
        String s = new String(b);
        return s;
    }
    
    
    /**
     * 字符串转换字节数组
     * @param s
     * @return
     */
    public byte[] string2Bytes(String s) {
        byte[] b = s.getBytes();
        return b;
    }
    
    /**
     * 字符串的编码转换
     * @param s
     * @param old_code
     * @param new_code
     * @return 正常返回新编码的字符串 异常返回null
     */
    public String stringConv(String s, String old_code, String new_code) {
        try {
            //按照原编码转换为字节数组
            byte[] bytes = s.getBytes(old_code);
            //按照新编码打包为字符串
            String str = new String(bytes, new_code);
            return str;
        } catch (UnsupportedEncodingException e) {
            //e.printStackTrace();
            //异常返回空
            return null;
        }
    }
    

    /**
     * 将json字符串对象转换为Map
     * @param jsonString
     * @return
     */
    public Map<String, Object> jsonToMap(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        Map<String, Object> result = new HashMap<String, Object>();
        Iterator<String> iterator = jsonObject.keys();

        while(iterator.hasNext()) {
            String key  = (String) iterator.next();
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
    public String mapToJson(Map<String, Object> maps) {
        JSONObject jsonLoad = new JSONObject(maps);
        return jsonLoad.toString();
    }
    
    /**
     * json字符串转换为对象
     * @param json
     * @param classObj
     * @return
     */
    public Object jsonToObject(String json, Class<?> classObj) {
        return new Gson().fromJson(json, classObj);
    }
    
    /**
     * 对象转换为json字符串
     * @param obj
     * @return
     */
    public String objectToJson(Object obj) {
        return new Gson().toJson(obj);
    }
    
    
    /**
     * 时间戳转日期字符串
     * @param time
     * @return
     */
    public String timeToString(long time, String dateFormat) {
        // String dateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(new Date(time * 1000));
    }
    
    /**
     * 日期字符串转时间戳
     * @param timeStr
     * @return 转换正常返回时间戳 转换失败返回0
     */
    public long stringToTime(String timeStr, String dateFormat) {
        //String dateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        try {
            return (long) (sdf.parse(timeStr).getTime()/1000);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    /**
     * 获取当前时间戳
     * @return
     */
    public long newTime() {
        long time = System.currentTimeMillis()/1000;
        return time;
    }

    
    public static void main(String[] args) {
        DataConversion tc = new DataConversion();
        int a = tc.string2Integer("1000", 2);
        System.out.println(a);
        
//      System.out.println(tc.integer2BinaryString(113));
//      System.out.println(tc.integer2HexString(15));
//      System.out.println(tc.integer2OctalString(23));
//      System.out.println(tc.string2Integer("1272"));
//      
//      byte[] b = new byte[3];
//      b[0] = 001;
//      b[1] = 100;
//      System.out.println(tc.bytes2Int(b));
//      
//      System.out.println(tc.string2Bytes("abc")[2]);
        
//        String s = "曾智颖";
//        s = tc.stringConv(s, "utf-8", "gbk");
//        System.out.println(s);
        
//        Map<String, Object> r_m = tc.jsonToMap("{\"id\":3, \"name\":\"zengzy\"}");
//        
//        String name = (String) r_m.get("name");
//        int id = (int) r_m.get("id");
//        System.out.println(id + ":" + name);
        Map<String, Object> r_m = new HashMap<String, Object>();
        r_m.put("id", 1);
        r_m.put("name", "zzs");
        r_m.put("xq", "还");
        System.out.println(tc.mapToJson(r_m));
        
    }
    
}
