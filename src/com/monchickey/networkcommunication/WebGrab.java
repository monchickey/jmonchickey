package com.monchickey.networkcommunication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * web抓取，请求类，用于抓取页面、发送get/post请求等
 * @author monchickey
 *
 */

public class WebGrab {
    
    /**
     * 获取指定uri网络资源的源代码内容
     * @param uri
     * @param code
     * @return
     */
    public String getUrl(String uri, String code) {
        try {
            URL url = new URL(uri);
            //获取字节输入流
            InputStream is = url.openStream();
            //转换为字符流
            InputStreamReader isr = new InputStreamReader(is, code);
            //添加缓冲
            BufferedReader br = new BufferedReader(isr);
            
            String data = "";
            int byteData;
            
            while((byteData = br.read()) != -1) {
                // Java中文字符为utf-16 一个中文字符底层占2个字节; 但是转换为字符串之后length()方法认为长度为1
                if(byteData == 13 && br.read() == 10) {
                    // 13为回车 文件每一行 换行符Java会认为是回车+换行 分别是13和10 所以长度是2 这里转为1
                    data += "\n";
                } else {
                    data += String.valueOf((char) byteData);
                }
            }
            
            br.close();
            isr.close();
            is.close();
            
            return data;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式
     * @param code
     *            请求响应的编码
     * @param isPrinWrite 
     *            是否开启后台打印响应头输出
     * @return URL 所代表远程资源的响应结果 返回结果如果最后一行是空行则不会返回
     */
    public String httpGet(String url, String param, String code, boolean isPrinWrite) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64)");
            // 建立实际的连接
            connection.connect();
            if(isPrinWrite) {
                // 获取所有响应头字段
                Map<String, List<String>> map = connection.getHeaderFields();
                // 后台遍历所有的响应头字段
                for (String key : map.keySet()) {
                    System.out.println(key + "--->" + map.get(key));
                }
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), code));
            String line = in.readLine();
            if(line != null) {
                result += line;
            }
            while ((line = in.readLine()) != null) {
                result += ("\n" + line);
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    
    
    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式
     * @param code
     *            返回资源响应的读取编码方式
     * @return 所代表远程资源的响应结果
     */
    public String httpPost(String url, String param, String code) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), code));
            String line = in.readLine();
            if(line != null) {
                result += line;
            }
            while ((line = in.readLine()) != null) {
                result += ("\n" + line);
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
            return null;
        } finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
    
    
    
    public static void main(String[] args) {
        WebGrab g = new WebGrab();
        String data = g.getUrl("http://127.0.0.1/test/image.jpg", "utf-8");
        System.out.println(data);
        System.out.println(data.length());
    }
    
}
