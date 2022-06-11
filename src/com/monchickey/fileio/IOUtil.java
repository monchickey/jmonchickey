package com.monchickey.fileio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * 常用的文件IO流操作类
 * @author monchickey
 *
 */

public class IOUtil {
    
    /**
     * 判断一个完整的路径是文件还是目录
     * @param uri 输入的路径
     * @return 如果是文件则返回字符串: file
     *         如果是目录则返回字符串: dir
     *         如果路径不存在则返回: null
     */
    public static String isFileOrDirectory(String uri) {
        final String FILE_MSG = "file";
        final String DIR_MSG = "dir";
        File f = new File(uri);
        if(f.exists()) {
            if(f.isDirectory()) {
                return DIR_MSG;
            } else {
                return FILE_MSG;
            }
        } else {
            return null;
        }
    }
    
    /**
     * 创建目录 如果多层则递归创建
     * @param path 要创建的目录完整路径
     */
    public static void createDirectorys(String path) {
        File fpath = new File(path);
        if(fpath.getParentFile().exists()) {
            fpath.mkdir();
        } else {
            createDirectorys(fpath.getParent());
            fpath.mkdir();
        }
    }
    
    /**
     * 向文件写入内容 ，从前面开始写不影响后面的内容
     * 如果文件不存在，会尝试重新建立
     * @param uri
     * @param text
     * @return
     */
    public int fileFront(String uri, String text) {
        final int PASS = 0; //成功
        final int ERROR_NOTFOUND = 1;   //文件不存在或错误
        final int ERROR_IO = 2; //文件写入出错
        File f = new File(uri);
        try {
            RandomAccessFile raf = new RandomAccessFile(f, "rw");
            //拆分为字节数组
            byte[] bytes = text.getBytes();
            //写入字节数组
            raf.write(bytes);
            //关闭资源
            raf.close();
            return PASS;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ERROR_NOTFOUND;
        } catch (IOException e) {
            e.printStackTrace();
            return ERROR_IO;
        }
    }
    
    /**
     * 文件内容追加 在文件最后添加内容，不影响前面的所有内容
     * @param uri
     * @param text
     * @return
     */
    public int fileAppend(String uri, String text) {
        final int PASS = 0; //成功
        final int ERROR_NOTFOUND = 1;   //文件不存在或错误
        final int ERROR_IO = 2; //文件写入出错
        File f = new File(uri);
        try {
            RandomAccessFile raf = new RandomAccessFile(f, "rw");
            //拆分为字节数组
            byte[] bytes = text.getBytes();
            //移动文件指针
            raf.seek(raf.length());
            //写入字节数组
            raf.write(bytes);
            //关闭资源
            raf.close();
            return PASS;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ERROR_NOTFOUND;
        } catch (IOException e) {
            e.printStackTrace();
            return ERROR_IO;
        }
    }
    
    /**
     * 向文件写入内容 会覆盖文件全部内容 如果文件不存在会尝试创建
     * 返回true写入成功 返回false则写入失败
     * @param uri
     * @param text
     * @return
     */
    public boolean fileWrite(String uri, String text) {
        try {
            OutputStream output = new FileOutputStream(uri);
            output.write(text.getBytes());
            output.close();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("打开文件失败...");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("写文件失败...");
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 文件内容读取
     * 将内容返回一个字符串
     * @param uri
     * @return
     */
    public String fileRead(String uri) {
        File f = new File(uri);
        try {
            RandomAccessFile raf = new RandomAccessFile(f, "r");
            //移动指针到头部
            raf.seek(0);
            //定义字节数组并读入
            byte[] buf = new byte[(int) raf.length()];
            raf.read(buf);
            raf.close();
            return (new String(buf));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    /**
     * 带缓冲的文件复制
     * 按字节复制
     * @param old_uri
     * @param new_uri
     * @return
     */
    public boolean bufferCopyFile(String old_uri, String new_uri) {
        try {
            //定义input的Buffer用于将文件内容读入到程序
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(old_uri)));
            //定义output的Buffer用于将文件内容输出到另一个文件
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(new_uri)));
            //循环读取 循环写入
            int c;
            while((c = bis.read()) != -1) {
                bos.write(c);
                bos.flush();
            }
            bis.close();
            bos.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /*
     * 基于字符流的文件读取
     */
    public String charFileRead(String uri) {
        try {
            FileInputStream fis = new FileInputStream(uri);
            InputStreamReader isr = new InputStreamReader(fis);
            String str = "";
            int c;
            while((c = isr.read()) != -1) {
                str += String.valueOf((char) c);
            }
            isr.close();
            return str;
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            return null;
        } catch (IOException e) {
            //e.printStackTrace();
            return null;
        }
    }
    
    
    /*
     * 基于字符流的文件批量读取
     */
    public String charFileReader(String uri) {
        try {
            FileInputStream fis = new FileInputStream(uri);
            InputStreamReader isr = new InputStreamReader(fis);
            //定义基准数组
            char[] buf = new char[8*1024];
            String str = "";
            int c;
            while((c = isr.read(buf, 0, buf.length)) != -1) {
                str += (new String(buf,0,c));
            }
            isr.close();
            return str;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }
    
    
    /*
     * 基于字符流的文本写入
     */
    public boolean charFileWrite(String uri, String text) {
        try {
            FileOutputStream fos = new FileOutputStream(uri);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            //转换为字符数组
            char[] ch = text.toCharArray();
            //写入
            for(int i = 0;i < ch.length;i++) {
                osw.write(ch[i]);
                osw.flush();
            }
            osw.close();
            return true;
            
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            return false;
        } catch (IOException e) {
            return false;
        }
    }
    
    
    /*
     * 基于字符的文件复制
     * 仅适用于复制非二进制文件即只能复制文本文件
     */
    public boolean charFileCopy(String old_uri, String new_uri) {
        try {
            FileReader fr = new FileReader(old_uri);
            FileWriter fw = new FileWriter(new_uri);
            char[] buffer = new char[2*1024];
            int c;
            while((c = fr.read(buffer, 0, buffer.length)) != -1) {
                fw.write(buffer, 0, c);
                fw.flush();
            }
            fr.close();
            fw.close();
            
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }
    
    
    /*
     * 基于行的文件读取
     * 每次读取一行内容
     */
    public String charLineFileReader(String uri) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(uri)));
            String str,line;
            str = "";
            while((line = br.readLine()) != null) {
                str += line;
                //添加换行
                str += "\n";
            }
            
            br.close();
            return str;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }
    
    
    /*
     * 基于行的文件复制
     * 每次复制一行
     */
    public boolean charLineFileCopy(String old_uri, String new_uri) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(old_uri)));
            PrintWriter pw = new PrintWriter(new_uri);
            String line;
            while((line = br.readLine()) != null) {
                //向文件写一行内容并且自动写入换行
                pw.println(line);
                pw.flush();
            }
            br.close();
            pw.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }
    
    
    /*
     * 得到指定目录下所有的文件和目录列表
     */
    public List<String> fileList(String uri) {
        File dir = new File(uri);
        if(!dir.isDirectory()) {
            return null;
        }
        List<String> filelist = new ArrayList<String>();
        //获取所有文件对象
        File[] files = dir.listFiles();
        if(files != null && files.length > 0) {
            //遍历文件对象
            for(File f:files) {
                filelist.add(f.getPath());
            }
        }
        return filelist;
    }
    
    
    /**
     * 递归遍历目录下所有文件
     * @param uri
     * @return List<String>
     */
    public List<String> fileListAll(String uri) {
        File dir = new File(uri);
        if(!dir.isDirectory()) {
            return null;
        }
        List<String> filelist = new ArrayList<String>();
        //获取所有文件对象
        File[] files = dir.listFiles();
        if(files != null && files.length > 0) {
            //遍历文件对象
            for(File f:files) {
                List<String> sonFilelist = new ArrayList<String>();
                filelist.add(f.getPath());
                //是目录再次循环
                if(f.isDirectory()) {
                    sonFilelist = fileListAll(uri + File.separator + f.getName());
                }
                filelist.addAll(sonFilelist);
            }
        }
        return filelist;
    }
    
    /**
     * 获取文件的十六进制内容 二进制安全
     * @param filename
     * @return 正常返回16进制字符串 异常返回null
     */
    public String getFileHexContent(String filename) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filename);
            int byteNumber;
            StringBuilder fileContent = new StringBuilder();
            while((byteNumber = inputStream.read()) != -1) {
                fileContent.append(Integer.toHexString(byteNumber));
            }
            return fileContent.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String args[]) throws IOException {
        // FileIO fio = new FileIO();
//      String url = "D:\\KuGou\\test1.txt";
//      if(fio.isFileorDirectory(url) == null) {
//          System.out.println("资源不存在");
//      } else if((fio.isFileorDirectory(url)).equals("file")) {
//          System.out.println("是文件");
//      } else {
//          System.out.println("是文件夹");
//      }
        
//      String state = fio.fileRead("D:\\KuGou\\test33.txt");
//      if(state == null) {
//          System.out.println("读取失败");
//      } else {
//          System.out.println(state);
//      }
        
//      System.out.println("正在复制，请稍后...");
//      String cmd = "cmd /c cls";
//      if(fio.bufferCopyFile("D:\\KuGou\\xihuanni.flac", "D:\\KuGou\\xihuanni1.flac")) {
//          Runtime.getRuntime().exec(cmd);
//          System.out.println("复制成功！");
//      } else {
//          Runtime.getRuntime().exec(cmd);
//          System.out.println("复制失败");
//      }
        
//      if(fio.charLineFileCopy("D:\\KuGou\\test.txt", "D:\\KuGou\\testsksksk.txt")) {
//          System.out.println("复制成功");
//      } else {
//          System.out.println("复制失败");
//      }
        
//      long start = System.currentTimeMillis();
//      ArrayList<String> filelist = (ArrayList<String>) fio.fileListAll("D:\\osimage\\");
//      if(filelist != null && filelist.size() > 0) {
//          for(String s:filelist) {
//              System.out.println(s);
//          }
//          
//          System.out.println("共" + filelist.size() +"个文件！");
//      } else {
//          System.out.println("没有文件");
//      }
//      long end = System.currentTimeMillis();
//      System.out.println((end-start)/1000);
        
    }
}
