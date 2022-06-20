package com.monchickey.fileio;

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
    
    private JSch jsch;
    private Session session;
    private String host;
    private int port;
    private String username;
    private String password;

    private boolean isConnected = false;
    
    public JSchTransfer(String host, int port,String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;

        this.jsch = new JSch();
    }
    
    public void uploadFile(String localFile, String serverFolder) {
        if(!isConnected)
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
        }
    }
    
    public void getFile(String serverFile, String localFolder) {
        if(!isConnected)
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
        }
    }
    
    public void connect() {
        if(isConnected) return;
        synchronized (session) {
            if(isConnected) return;
            try {
                this.session = jsch.getSession(username, host, port);
                this.session.setPassword(password);
                Properties config = new Properties();
                config.put("StrictHostKeyChecking", "no");
                this.session.setConfig(config);
                this.session.connect();
            } catch (JSchException e) {
                e.printStackTrace();
            }
            isConnected = true;
        }
    }

    public void close() {
        if(isConnected) {
            synchronized (session) {
                if(isConnected) {
                    this.session.disconnect();
                }
            }
        }
    }
}
