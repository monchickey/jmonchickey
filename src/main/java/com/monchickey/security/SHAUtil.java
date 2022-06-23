package com.monchickey.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAUtil {
    /**
     * SHA1 单向hash
     * @param src  原始字节数组
     * @return 计算正常返回: 长度为20的字节数组, 异常返回: null
     */
    public static byte[] SHA1(byte[] src) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA");
            return sha.digest(src);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
