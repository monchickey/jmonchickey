package com.monchickey.tests;

import com.monchickey.os.OSUtil;

public class OSTest {
    public static void main(String[] args) {
        System.out.println(OSUtil.execCommand("ls"));
        String r = OSUtil.execCommand("ls /");
        System.out.println(r);

        System.out.println(OSUtil.execCommand("lscpu"));
        System.out.println(OSUtil.execCommand("pwd"));
    }
}
