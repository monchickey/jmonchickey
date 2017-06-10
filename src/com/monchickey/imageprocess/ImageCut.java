package com.monchickey.imageprocess;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * 图片处理工具类 之 图片裁剪类
 * @author monchickey
 *
 */
public class ImageCut {
    
    /**
     * 裁剪 jpg 类型的图片
     * @param inputFile 图片文件路径
     * @param x 开始裁减位置x轴坐标
     * @param y 开始裁减位置y轴坐标
     * @param width 裁剪的宽度
     * @param height 裁剪的高度
     * @param outputFile 裁剪后图片输出文件
     */
    public static void cutJPG(String inputFile, int x, int y, int width, int height, String outputFile) {
        Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("jpg");
        ImageReader reader = readers.next();
        try {
            ImageInputStream imageStream = ImageIO.createImageInputStream(new FileInputStream(inputFile));
            reader.setInput(imageStream, true);
            ImageReadParam param = reader.getDefaultReadParam();
            
            System.out.println(reader.getWidth(0));
            System.out.println(reader.getHeight(0));
            
            Rectangle rect = new Rectangle(x, y, width, height);
            param.setSourceRegion(rect);
            BufferedImage bi = reader.read(0, param);
            ImageIO.write(bi, "jpg", new FileOutputStream(outputFile));
            imageStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * 图片截取方法，将截取后的图片放到文件系统
     * @param inputFile
     * @param outputFile
     * @param type
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public static void cutImage(String inputFile, String outputFile, String type, int x, int y, int width, int height) {
        
        Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(type);
        ImageReader reader = readers.next();
        try {
            ImageInputStream imageStream = ImageIO.createImageInputStream(new FileInputStream(inputFile));
            reader.setInput(imageStream, true);
            ImageReadParam param = reader.getDefaultReadParam();
            
            //长和宽
            //System.out.println(reader.getWidth(0));
            //System.out.println(reader.getHeight(0));
            
            Rectangle rect = new Rectangle(x, y, width, height);
            param.setSourceRegion(rect);
            BufferedImage bi = reader.read(0, param);
            ImageIO.write(bi, type, new FileOutputStream(outputFile));
            imageStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    
    public static void main(String[] args) {
        ImageCut.cutImage("images/test.png", "images/test1.png", "png", 350, 99, 164, 245);
    }
}
