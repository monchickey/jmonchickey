package com.monchickey.fileprocess;

import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * 基于JSch类库使用linux自带的ssh服务传输文件
 * @author monchickey
 *
 */
public class JSchTransfer {
    
    private static JSch jsch;
    private static Session session;
    private static String host;
    private static int port;
    private static String username;
    private static String password;
    
    public JSchTransfer(String host, int port,String username, String password) {
        JSchTransfer.host = host;
        JSchTransfer.port = port;
        JSchTransfer.username = username;
        JSchTransfer.password = password;
    }
    
    public void uploadFile(String localFile, String serverFolder) {
        connect();
        // 打开sftp管道
        try {
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp cs = (ChannelSftp) channel;
            cs.cd("/");
            //上传文件
            cs.put(localFile, serverFolder, ChannelSftp.OVERWRITE);
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        } finally {
            session.disconnect();
        }
    }
    
    public void getFile(String serverFile, String localFolder) {
        connect();
        //打开sftp管道
        try {
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp cs = (ChannelSftp) channel;
            cs.get(serverFile, localFolder);    
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        } finally {
            session.disconnect();
        }
    }
    
    public static void connect() {
        jsch = new JSch();
        try {
            session = jsch.getSession(username, host, port);
            session.setPassword(password);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
        
    }
    
    public static void main(String[] args) {
        //uploadFile("/root/test/dhcp.png", "/root/test/");
        //getFile("/root/test/dhcp.png", "/root/test/");
    }
}
