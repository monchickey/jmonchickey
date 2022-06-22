package com.monchickey.net;

import java.io.*;
import java.net.HttpURLConnection;
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
     * @return 成功: 资源响应头和内容的HTTPResponse对象, 失败: null
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
     * @param body
     *            请求正文
     * @param headers
     *            设置请求的HTTP header头
     * @param charset
     *            返回资源响应的读取编码方式
     * @return 成功: 资源响应头和内容的HTTPResponse对象, 失败: null
     */
    public static HTTPResponse post(String url, String body, Map<String, String> headers, String charset) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuffer buffer = new StringBuffer();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            if(headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    conn.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(body);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));

            String line;
            while ((line = in.readLine()) != null) {
                buffer.append(line);
                buffer.append("\r\n");
            }

            HTTPResponse response = new HTTPResponse(conn.getHeaderFields(), buffer.toString());
            return response;
        } catch (Exception e) {
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
    }

    /**
     * 通过HTTP/HTTPS方式下载图片
     * @param imageUrl 图片URL
     * @param savePath 保存图片文件的目录
     * @param saveFilename 保存的文件名
     * @param timeout 图片下载超时时间 单位: s
     * @return
     *     0 下载成功, -1 文件异常,-2 打开服务器连接失败,-3 io流读取异常,-4 图片保存错误
     */
    public static int downloadImage(String imageUrl, String savePath, String saveFilename, int timeout) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(savePath + File.separator + saveFilename);
            byte[] imageStream = getImageUrl(imageUrl, timeout);
            if(imageStream.length == 1) {
                return -2;
            }
            if(imageStream.length == 2 || imageStream.length == 3) {
                return -3;
            }

            os.write(imageStream, 0, imageStream.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -4;
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * 获取远程图片的字节流, 通常都是以GET方式请求
     * @param urlString 图片URL
     * @param timeout  连接和读取的超时时间, 默认设置为一样的值, 单位: s
     * @return  成功: 图片字节数组 失败: 数组长度为1,2,3 (正常图片大小一定不会小于10字节)
     *          长度为1: URL打开失败, 长度为2: IO异常, 长度为3: 其他异常
     */
    public static byte[] getImageUrl(String urlString, int timeout) {
        // 构造URL
        URL url;
        InputStream is = null;
        ByteArrayOutputStream outputStream = null;
        try {
            url = new URL(urlString);
            // 打开连接
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            //设置连接超时
            con.setConnectTimeout(timeout*1000);
            con.setRequestMethod("GET");
            // 设置读取超时时间
            con.setReadTimeout(timeout*1000);
            is = con.getInputStream();
            outputStream = new ByteArrayOutputStream();
            readInputStream(is, outputStream);
            byte[] data = outputStream.toByteArray();
            return data;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return new byte[1];
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[2];
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[3];
        } finally {
            try {
                if(is != null) {
                    is.close();
                }
                if(outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 远程输入字节流读取  缓冲区大小: 4k
     * @param inputStream 图片输入流
     * @return 从输入流中读取到字节数组
     * @throws IOException
     */
    private static void readInputStream(InputStream inputStream, ByteArrayOutputStream outputStream) throws IOException {
        byte[] buffer = new byte[4096];
        int len;
        while((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
    }
}

