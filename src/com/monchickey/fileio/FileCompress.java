package com.monchickey.fileio;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
     * 对指定的单层目录进行压缩 传入参数:待压缩的目录、压缩文件输出目录、压缩文件注释(只能为英文)
     * @param dirUri
     * @param zipFileUri
     * @param zipDesc
     * @return 压缩成功返回true，压缩失败返回false
     */
    public boolean filesZipOne(String dirUri, String zipFileUri, String zipDesc) {
        File fileDir = new File(dirUri);
        File zipFile = new File(zipFileUri);
        InputStream input = null;
        ZipOutputStream zipOut;
        try {
            zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
            zipOut.setComment(zipDesc);
            if(fileDir.isDirectory()) {
                //是目录就进行压缩
                File[] files = fileDir.listFiles();
                if(files != null && files.length > 0) {
                    for(int i = 0;i < files.length;i++) {
                        input = new FileInputStream(files[i]);
                        zipOut.putNextEntry(new ZipEntry(fileDir.getName() + File.separator + files[i].getName()));
                        int temp = 0;
                        while((temp = input.read()) != -1) {
                            zipOut.write(temp);
                        }
                        input.close();
                    }
                } else {
                    
                }
            }
            zipOut.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 压缩 - private
     * @param srcFile
     * @return
     */
    private boolean filesZip(File srcFile) {
        String basePath = srcFile.getPath();
        String destPath = basePath + ".zip";
        if(filesZip(srcFile, destPath)) {
            return true;
        }
        return false;
    }
    
    /**
     * 重载方法 - 压缩 传入原路径和目标路径
     * @param srcFile
     * @param destFile
     * @return
     */
    private boolean filesZip(File srcFile, File destFile) {
        
        //输出做CRC32校验
        try {
            CheckedOutputStream cos = new CheckedOutputStream(new FileOutputStream(destFile), new CRC32());
            ZipOutputStream zos = new ZipOutputStream(cos);
            //传入第三个参数：压缩文件相对路径
            boolean status = false;
            if(filesZip(srcFile, zos, "")) {
                status = true;
            }
            zos.flush();
            zos.close();
            return status;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 重载-压缩 传入源文件、压缩路径
     * @param srcFile
     * @param destPath
     * @return
     */
    private boolean filesZip(File srcFile, String destPath) {
        if(filesZip(srcFile, new File(destPath))) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 
     * @param srcFile
     * @param zos
     * @param basePath
     * @return
     */
    private boolean filesZip(File srcFile, ZipOutputStream zos, String basePath) {
        if(srcFile.isDirectory()) {
            //压缩目录
            if(compressDir(srcFile, zos, basePath)) {
                return true;
            } else {
                return false;
            }
        } else {
            //压缩文件
            if(compressFile(srcFile, zos, basePath)) {
                return true;
            } else {
                return false;
            }
        }
    }
    
    /**
     * 主调函数入口 传入文件的路径，只有该方法对外提供开放调用，压缩文件和目录同目录压缩文件名默认为目录名
     * @param srcPath
     * @return
     */
    public boolean filesZip(String srcPath) {
        File srcFile = new File(srcPath);
        if(filesZip(srcFile)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 重载方法 - 自定义 - 压缩文件传入输入路径和输出路径
     * @param srcPath
     * @param destPath
     * @return
     */
    public boolean filesZip(String srcPath, String destPath) {
        File srcFile = new File(srcPath);
        if(filesZip(srcFile, destPath)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 目录压缩
     * @param dir
     * @param zos
     * @param basePath
     * @return
     */
    private boolean compressDir(File dir, ZipOutputStream zos, String basePath) {
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
                filesZip(file, zos, basePath + dir.getName() + "/");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    
    /**
     * 单个文件压缩
     * @param file
     * @param zos
     * @param dir
     * @return
     */
    private boolean compressFile(File file, ZipOutputStream zos, String dir) {
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
    
    public static void main(String[] args) {
        FileCompress fc = new FileCompress();
        if(fc.filesZip("C:\\Users\\Administrator\\Desktop\\ttt", "C:\\Users\\Administrator\\Desktop\\ttt.zip")) {
            System.out.println("ok..");
        }
    }
    
}
