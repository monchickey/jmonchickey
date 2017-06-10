package com.monchickey.dataprocess;

/**
 * 数据抽取类，用于提取数据中包含的其他有用数据
 * @author monchickey
 *
 */

public class DataExtract {
    
    /**
     * 提取字符串中的整数
     * @param str
     * @return int[]
     */
    public int[] stringExtractInteger(String str) {
        int[] intlist = new int[str.length()];
        int j = 0;
        //遍历赋值
        for(int i = 0;i < str.length();i++){
            char c = str.charAt(i);
            if(c >= 48 && c <= 57){
                intlist[j] = Integer.parseInt(String.valueOf(c));
                j++;
            }
        }
        //取出 缩短数组冗余空间
        int[] newlist = new int[j];
        for(int i = 0;i < j;i++) {
            newlist[i] = intlist[i];
        }
        return newlist;
    }
    
    
    public static void main(String[] args) {
        String s = "ajshsh8272jshs2";
        int[] a = (new DataExtract()).stringExtractInteger(s);
        for(int i:a) {
            System.out.print(i+ " ");
        }
    }
    
}
