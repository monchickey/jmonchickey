package com.monchickey.dataprocess;

import java.util.Base64;

public class Encoding {
    /**
     * 转换字节数组为16进制字符串
     * @param b  字节数组
     * @return  十六进制字符串
     */
    public static String hex(byte[] b) {
        StringBuffer hexValue = new StringBuffer();
        for(int i = 0;i < b.length;i++) {
            int val = b[i] & 0xff;
            if(val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * 转换16进制字符串为byte数组, 比如: e3a, 为: {0x0e, 0x3a}
     * 转换字符串高位对应byte数组低字节
     * @param hex 16进制字符串
     * @return  解码后的字节数组
     */
    public static byte[] hexDecode(String hex) {
        if((hex.length() & 1) != 0)
            hex = "0" + hex;
        byte[] bytes = new byte[hex.length()/2];
        for(int i = 0; i < hex.length() / 2; i++) {
            String hexUnit = hex.substring(i << 1, (i + 1) << 1);
            bytes[i] = (byte) Integer.parseInt(hexUnit, 16);
        }
        return bytes;
    }

    /**
     * base64编码
     * @param src  原数据
     * @return  编码后的字符串
     */
    public static String base64Encode(byte[] src) {
        return Base64.getEncoder().encodeToString(src);
    }

    /**
     * base64解码
     * @param src  base64编码字符串
     * @return  解码后的字节数组
     */
    public static byte[] base64Decode(String src) {
        return Base64.getDecoder().decode(src);
    }
}
