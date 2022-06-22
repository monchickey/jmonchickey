package com.monchickey.os;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExecUtil {
    /**
     * 执行操作系统命令
     * @param command 要执行的命令
     * @return 命令执行的输出
     */
    public static String execCommand(String command) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            char[] cs = new char[1024];
            int n;
            while((n = input.read(cs)) != -1) {
                char[] tmp = new char[n];
                System.arraycopy(cs, 0, tmp, 0, n);
                stringBuffer.append(tmp);
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

}
