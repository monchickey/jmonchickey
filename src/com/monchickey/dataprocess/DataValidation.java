package com.monchickey.dataprocess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 数据验证类，用户对用户输入的表单数据正确性进行一定范围的匹配或者验证，保证数据的准确性
 * @author monchickey
 *
 */

public class DataValidation {
    
    /**
     * 判断字符串中是否包含指定字符串
     * @param text
     * @param query_str
     * @return
     */
    public boolean isContain(String text, String query_str) {
        return text.contains(query_str);
    }
    
    /**
     * 验证电子邮箱
     * @param email
     * @return
     */
    public boolean isEmail(String email) {
        String reg = "^[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}$";
        //编译正则表达式
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(email);
        boolean rs = matcher.matches();
        return rs;
    }
    
    /**
     * 验证一个数字字符串是否为正整数
     * @param number
     * @return
     */
    public boolean isPosInteger(String number) {
        Pattern p = Pattern.compile("^[1-9][0-9]{0,}$");
        
        Matcher m = p.matcher(number);
        if(m.find()) {
            return true;
        }
        return false;
    }
    
    /**
     * 日期类型字符串验证，有日期越界判断能力，比正则更强大
     * @param dateStr 格式必须为2016-05-30这样的格式
     * @return
     */
    public boolean isDate(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //设置严格匹配，默认是宽松匹配
        format.setLenient(false);
        try {
            format.parse(dateStr);
            //到这里转换正常
            return true;
        } catch (ParseException e) {
            // 转换异常
            return false;
        }
    }
    
    /**
     * 用户名校验
     * 规则:4-16个字符 首字符为字母然后包括大小写、数字、下划线
     * @param username
     * @return
     */
    public boolean isUsername(String username) {
        Matcher m = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]{3,15}$").matcher(username);
        if(m.find()) {
            return true;
        }
        return false;
    }
    
    /**
     * 验证密码
     * 规则:6-18位 包含尽可能多的字符，因为密码要哈希，所以可以包含特殊字符
     * @param password
     * @return
     */
    public boolean isPassword(String password) {
        Matcher m = Pattern.compile("^[a-zA-Z0-9_~`.,;\':\"-]{6,18}$").matcher(password);
        if(m.find()) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 匹配一个或多个汉字
     * @param chinese
     * @return
     */
    public boolean isChinese(String chinese) {
        Matcher m = Pattern.compile("^[\u0391-\uFFE5]+$").matcher(chinese);
        return m.matches();
    }
    
    public static void main(String[] args) {
        DataValidation dv = new DataValidation();
        if(dv.isChinese("曾")) {
            System.out.println("匹配成功！");
        } else {
            System.out.println("匹配失败");
        }
        System.out.println("测试".length());
    }
    
}
