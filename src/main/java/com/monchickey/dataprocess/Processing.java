package com.monchickey.dataprocess;

import java.util.Random;
import java.util.UUID;

/**
 * 数据计算类 包括求值，计数，函数计算等
 * @author monchickey
 *
 */

public class Processing {
    
    /**
     * 根据名字生成唯一uuid
     * @param name
     * @return 基于name的UUID字符串
     */
    public String getNameUUID(String name) {
        return UUID.nameUUIDFromBytes(name.getBytes()).toString().replace("-", "");
    }
    
    /**
     * 生成全局唯一uuid
     * @return random uuid
     */
    public String getRandomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 计算两个浮点数向量的余弦相似度
     * @param f1 向量1
     * @param f2 向量2
     * @return  余弦相似度
     */
    public static double cosine(float[] f1, float[] f2) {
        double dp = 0.0;
        double norm1 = 0.0f, norm2 = 0.0f;
        for(int i = 0; i < f1.length; i++) {
            dp += f1[i] * f2[i];
            norm1 += f1[i] * f1[i];
            norm2 += f2[i] * f2[i];
        }
        return dp / Math.sqrt(norm1 * norm2);
    }


    /**
     * 生成指定字节的随机值
     * @param numberBytes  生成的字节个数
     * @return  返回对应的字节数组
     */
    public static byte[] getRandBytes(int numberBytes) {
        byte[] bs = new byte[numberBytes];
        Random random = new Random();
        random.nextBytes(bs);
        return bs;
    }
    
}
