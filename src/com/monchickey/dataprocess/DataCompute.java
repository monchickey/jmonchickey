package com.monchickey.dataprocess;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 数据计算类 包括求值，计数，函数计算等
 * @author monchickey
 *
 */

public class DataCompute {
    
    /**
     * Sha1 单向加密算法
     * @param inPassword
     * @return String 40位sha码
     */
    public String shaEncrypt(String inPassword) {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA");
            byte[] byteArray = inPassword.getBytes();
            byte[] shaBytes = sha.digest(byteArray);
            StringBuffer hexValue = new StringBuffer();
            for(int i = 0;i < shaBytes.length;i++) {
                int val = ((int) shaBytes[i]) & 0xff;
                if(val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 转换日期为星期
     * @param y 年份,如:2016
     * @param m 月份,1-12
     * @param d 日期,1-31
     * @return 返回星期几 0-6 其中0为星期日
     * 原理利用蔡勒公式:w = y+[y/4]+[c/4]-2c+[26(m+1)/10]+d-1
     * 其中y为年取两位数,c为世纪数-1,m为月范围[3,14]即某年的1,2月份要按上一年的13,14月份为标准进行计算,同时c和y也按上一年的算
     * d代表日期
     * 最终算出来w如果大于0则除以7 余数是几就是星期几 余数是0则是星期天
     * 如果w是0则直接是星期天
     * 如果w小于0 则不断加7直到结果大于0 然后除以7进行求余运算即可
     * 此方法只适用于格里高利历即：1582年10月15日 星期五之后的时间(包括当天)
     * 1582年10月04日之前(含当天)使用的是儒略历   1582年10月04日后一天就是1582年10月15日
     */
    public int dateToWeek(int y, int m, int d) {
        // m,y,c预处理
        int c;
        if(m == 1 || m == 2) {
            // 按照上一年计算
            m += 12;
            y -= 1;
            // 由y计算蔡勒公式的c
            c = centuryNumber(y) - 1;
            // 转换y为后两位数
            y = y%100;
        } else {
            // 由y计算c
            c = centuryNumber(y) - 1;
            y = y%100;
        }
        // 由蔡勒公式计算星期数
        int w = y + y/4 + c/4 - 2*c + 26*(m + 1)/10 + d - 1;
        if(w == 0) {
            return 0;
        } else if(w > 0) {
            return w % 7;
        } else {
            do {
                w += 7;
            } while (w <= 0);
            return w % 7;
        }
    }
    
    /**
     * 由年份计算世纪数
     * @param y 正数大于公元元年
     * @return
     */
    public int centuryNumber(int y) {
        int c = y/100;
        return c + 1;
    }
    
    public static void main(String[] args) {
        DataCompute compute = new DataCompute();
        System.out.println(compute.dateToWeek(2017, 6, 10));
        System.out.println(compute.shaEncrypt("sc"));
    }
    
}
