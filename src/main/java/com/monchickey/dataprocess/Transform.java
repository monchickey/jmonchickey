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
     * 转换半精度float16为单精度浮点数
     * @param h  半精度浮点数, 由short类型表示
     * @return 单精度浮点数数值
     */
    public static float fromHalf(short h) {
        int v = h;
        switch(v) {
            case 0x0000 :
                return 0.0f;
            case 0x8000 :
                return -0.0f;
            case 0x7c00 :
                return Float.POSITIVE_INFINITY;
            case 0xfc00 :
                return Float.NEGATIVE_INFINITY;
            // NaN not support
            default :
                return Float.intBitsToFloat(((h & 0x8000 ) << 16 ) | (((h & 0x7c00) + 0x1C000) << 13 ) | (( h & 0x03FF ) << 13));
        }
    }


    public static short half(float v) {
        if(Float.isNaN(v))
            throw new UnsupportedOperationException("NaN to half conversion not supported!");
        if(v == Float.POSITIVE_INFINITY)
            return (short) 0x7c00;
        if(v == Float.NEGATIVE_INFINITY)
            return (short) 0xfc00;
        if(v == 0.0f)
            return (short) 0x0000;
        if(v == -0.0f)
            return (short) 0x8000;
        if(v > 65504.0f)
            return 0x7bff;  // max value supported by half float
        if(v < -65504.0f)
            return (short) (0x7bff | 0x8000);
        if(v > 0.0f && v < 5.96046E-8f)
            return 0x0001;
        if(v < 0.0f && v > -5.96046E-8f)
            return (short) 0x8001;

        final int f = Float.floatToIntBits(v);

        return (short) (((f >> 16) & 0x8000) | ((((f & 0x7f800000) - 0x38000000) >> 13) & 0x7c00) | ((f >> 13) & 0x03ff));
    }


    /**
     * 转换ipv4地址为对应的32位无符号数
     * 因为UInt32用java有符号int无法表示, 所以返回类型为long
     * @param ipv4
     * @return  正常返回对应的整数, ip错误返回-1.
     */
    public static long IPv4StringToNumber(String ipv4) {
        String[] nums = ipv4.split("\\.");
        if(nums.length != 4)
            return -1L;
        long number = 0L;
        for(int i = 0; i < nums.length; i++) {
            if(nums[i].isEmpty() || nums[i].length() > 3)
                return -1L;
            if(!Checking.isDigit(nums[i]))
                return -1L;
            int num = Integer.valueOf(nums[i]);
            if(num < 0 || num > 255)
                return -1L;
            number += (num << (24 - i*8)) & 0xffffffffL;
        }
        return number;
    }

    /**
     * 转换MAC字符串为对应的数字
     * @param mac  示例: 48-5F-99-B9-70-9D => 79575438160029
     * @return  转换成功返回原始数字, 类型: long, 转换失败返回: -1
     */
    public static long MACStringToNumber(String mac) {
        String macHex = mac
                .replace(":", "")
                .replace("-", "");

        return macHex.length() == 12 && Checking.isHex(macHex) ? Long.valueOf(macHex, 16) : -1L;
    }
    
}
