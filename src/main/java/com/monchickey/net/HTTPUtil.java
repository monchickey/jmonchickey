package com.monchickey.net;

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

public class HTTPUtil {
    
    /**
     * 简单HTTP get方法获取指定uri网络资源
     * @param uri  资源uri
     * @param charset  字符串编码
     * @return  请求成功: 返回资源内容, 请求失败: null
     */
    public static String simpleGet(String uri, String charset) {
        try {
            URL url = new URL(uri);
            //获取字节输入流
            InputStream is = url.openStream();
            //转换为字符流
            InputStreamReader isr = new InputStreamReader(is, charset);
            //添加缓冲
            BufferedReader br = new BufferedReader(isr);

            StringBuffer buffer = new StringBuffer();
            int b;

            while((b = br.read()) != -1) {
                buffer.append((char) b);
            }
            
            br.close();
            isr.close();
            is.close();
            
            return buffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    
    /**
     * 向指定URI发起HTTP GET方法的请求
     * 
     * @param uri
     *            发送请求的URL
     * @param params
     *            请求参数字符串，例如: name1=value1&name2=value2
     * @param headers
     *             设置请求的HTTP header头
     * @param charset
     *            请求响应的编码
     * @return 成功: 资源响应头和内容, 失败: null
     */
    public static HTTPResponse get(String uri, String params, Map<String, String> headers, String charset) {
        StringBuffer buffer = new StringBuffer();
        BufferedReader in = null;
        try {
            String urlNameString = uri + "?" + params;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            if(headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            connection.connect();

            // 获取所有响应头字段
            Map<String, List<String>> responseHeaders = connection.getHeaderFields();

            // 读取正文响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
            String line;
            while ((line = in.readLine()) != null) {
                buffer.append(line);
                buffer.append("\r\n");
            }

            HTTPResponse response = new HTTPResponse(responseHeaders, buffer.toString());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
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
            if (line != null) {
                result += line;
            }
            while ((line = in.readLine()) != null) {
                result += ("\n" + line);
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
    
}

