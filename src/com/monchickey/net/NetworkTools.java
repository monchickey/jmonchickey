package com.monchickey.net;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 网络常用工具类
 * @author monchickey
 *
 */

public class NetworkTools {
    
    /**
     * 下载网络图片
     * @param imageUrl http类型的地址
     * @param savePath 保存文件系统的目录
     * @param saveFilename 保存的文件名
     * @return
     *     0 下载成功,-1 文件异常,-2 打开服务器连接失败,-3 io流读取异常,-4 图片保存错误
     * @throws InterruptedException
     */
    public static int downloadImage(String imageUrl, String savePath, String saveFilename) throws InterruptedException {
        OutputStream os = null;
        try {
            os = new FileOutputStream(savePath + "/" + saveFilename);
            byte[] imageStream = getImageUrl(imageUrl);
            // System.out.println("图片流返回完毕!");
            if(imageStream.length == 1) {
                System.out.println("图片服务器连接失败...");
                return -2;
            }
            if(imageStream.length == 2 || imageStream.length == 3) {
                System.out.println("io异常...");
                return -3;
            }
            
            os.write(imageStream, 0, imageStream.length);
        } catch (FileNotFoundException e) {
            System.out.println("初始化文件异常...");
            e.printStackTrace();
            return -1;
        } catch (IOException e) {
            System.out.println("保存图片异常...");
            e.printStackTrace();
            return -4;
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                System.out.println("关闭资源发生异常...");
                e.printStackTrace();
                Thread.sleep(10000);
            }
        }
        return 0;
    }
    
    /**
     * 获取远程图片的字节流
     * @param urlString
     * @return
     * 如果直接在web页面输出图片而不下载 可以在对应的请求中写入下面代码
     * OutputStream toClient = response.getOutputStream();
     * byte[] resultImage = getImageUrl("http://127.0.0.1/test/image.jpg");
     * if(resultImage.length != 1 && resultImage.length != 2) {
     *     System.out.println("get图片长度: " + resultImage.length);
     *     toClient.write(resultImage);
     *     toClient.flush();
     * }
     * toClient.close();
     */
    public static byte[] getImageUrl(String urlString) {
        // 构造URL  
        URL url;
        InputStream is = null;
        try {
            url = new URL(urlString);
            // 打开连接  
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            //设置请求超时为10s
            con.setConnectTimeout(10*1000);
            con.setRequestMethod("GET");
            // 设置read超时时间
            con.setReadTimeout(30*1000);
            // 输入流  
            System.out.println("获取图片输入流...");
            is = con.getInputStream();
            System.out.println("获取图片输入流 完毕...");
            byte[] data = readInputStream(is);
            return data;
        } catch (MalformedURLException e) {
            System.out.println("远程服务器连接异常...");
            e.printStackTrace();
            return new byte[1];
        } catch (IOException e) {
            System.out.println("io流出现异常...");
            e.printStackTrace();
            return new byte[2];
        } catch (Exception e) {
            System.out.println("捕获到未知异常！可能是打开url出现错误...");
            e.printStackTrace();
            return new byte[3];
        } finally {
            try {
                if(is != null) {
                    is.close();
                }
            } catch (IOException e) {
                System.out.println("关闭资源时异常！");
                e.printStackTrace();
            }
        }
          
    }
    
    /**
     * 远程输入字节流读取
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static byte[] readInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        // inputStream.close();
        // outputStream.close();
        return outputStream.toByteArray();
    }
    
}
