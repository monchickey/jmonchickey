package com.monchickey.fileio;

import java.io.*;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件压缩解压工具
 * @author monchickey
 *
 */

public class FileCompress {
    
    /**
     * 对指定的单层目录进行压缩
     * @param dir 待压缩的目录
     * @param zipFileUri 压缩后的输出文件
     * @param zipDesc 压缩文件注释(只能为英文)
     * @return 压缩成功返回true，压缩失败返回false
     */
    public static boolean singleDirZip(String dir, String zipFileUri, String zipDesc) {
        File fileDir = new File(dir);
        File zipFile = new File(zipFileUri);
        InputStream input;
        ZipOutputStream zipOut;
        try {
            zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
            zipOut.setComment(zipDesc);
            if(fileDir.isDirectory()) {
                //是目录就进行压缩
                File[] files = fileDir.listFiles();
                if(files != null && files.length > 0) {
                    for (File file : files) {
                        input = new FileInputStream(file);
                        zipOut.putNextEntry(new ZipEntry(fileDir.getName() + File.separator + file.getName()));
                        int temp;
                        while ((temp = input.read()) != -1) {
                            zipOut.write(temp);
                        }
                        input.close();
                    }
                }
            }
            zipOut.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 压缩文件或目录
     * @param srcFile 源文件或源目录
     * @return 压缩成功: true
     *         压缩失败: false
     */
    private static boolean zipCompress(File srcFile) {
        String basePath = srcFile.getPath();
        String destPath = basePath + ".zip";
        return zipCompress(srcFile, destPath);
    }
    
    /**
     * 重载方法 - 压缩 传入原路径和目标路径
     * @param srcFile  源文件或目录
     * @param destFile 目标文件或目录
     * @return 压缩成功: true
     *         压缩失败: false
     */
    private static boolean zipCompress(File srcFile, File destFile) {
        try {
            //输出做CRC32校验
            CheckedOutputStream cos = new CheckedOutputStream(new FileOutputStream(destFile), new CRC32());
            ZipOutputStream zos = new ZipOutputStream(cos);
            //传入第三个参数：压缩文件相对路径
            boolean status = zipCompress(srcFile, zos, "");
            zos.flush();
            zos.close();
            return status;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 重载-压缩 传入源文件、压缩路径
     * @param srcFile  源文件或源目录
     * @param destPath  压缩文件路径
     * @return 压缩成功: true
     *        压缩失败: false
     */
    private static boolean zipCompress(File srcFile, String destPath) {
        return zipCompress(srcFile, new File(destPath));
    }
    
    /**
     * 
     * @param srcFile 源文件或源目录
     * @param zos  zip输出流
     * @param basePath  输出路径
     * @return 压缩成功: true
     *        压缩失败: false
     */
    private static boolean zipCompress(File srcFile, ZipOutputStream zos, String basePath) {
        if(srcFile.isDirectory()) {
            //压缩目录
            return zipCompressDir(srcFile, zos, basePath);
        } else {
            //压缩文件
            return zipCompressFile(srcFile, zos, basePath);
        }
    }
    
    /**
     * 压缩文件或目录函数入口，该方法对外提供开放调用
     * 默认压缩后的文件和源文件或源目录在同一层级，文件名为源文件或源目录后拼上.zip
     * @param srcPath 传入文件或目录的路径
     * @return 压缩成功: true
     *         压缩失败: false
     */
    public static boolean zipCompress(String srcPath) {
        File srcFile = new File(srcPath);
        return zipCompress(srcFile);
    }
    
    /**
     * 重载方法 - 自定义 - 压缩文件传入输入路径和输出路径
     * @param srcPath 源文件或源目录
     * @param destPath 压缩文件路径
     * @return 压缩成功: true
     *        压缩失败: false
     */
    public static boolean zipCompress(String srcPath, String destPath) {
        File srcFile = new File(srcPath);
        return zipCompress(srcFile, destPath);
    }
    
    /**
     * 目录压缩
     * @param dir  源目录
     * @param zos  zip输出流
     * @param basePath  压缩文件输出路径
     * @return 压缩成功: true
     *        压缩失败: false
     */
    private static boolean zipCompressDir(File dir, ZipOutputStream zos, String basePath) {
        File[] files = dir.listFiles();
        //构建空目录
        try {
            if(files.length < 1) {
                ZipEntry entry = new ZipEntry(basePath + dir.getName() + "/");
                zos.putNextEntry(entry);
                zos.closeEntry();
            }
            for(File file:files) {
                //递归压缩
                zipCompress(file, zos, basePath + dir.getName() + "/");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    
    /**
     * 单个文件压缩
     * @param file  源文件
     * @param zos  zip输出流
     * @param dir 压缩文件输出路径
     * @return 压缩成功: true
     *         压缩失败: false
     */
    private static boolean zipCompressFile(File file, ZipOutputStream zos, String dir) {
        ZipEntry entry = new ZipEntry(dir + file.getName());
        try {
            zos.putNextEntry(entry);
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            int count;
            byte[] data = new byte[1024];
            while((count = bis.read(data, 0, 1024)) != -1) {
                zos.write(data, 0, count);
            }
            bis.close();
            zos.closeEntry();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
    }
    
}
