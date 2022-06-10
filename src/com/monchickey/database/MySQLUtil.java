package com.monchickey.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * jdbc对mysql数据库的操作类
 * @author monchickey
 *
 */

public class MySQLUtil {
    
    /**
     * 获取数据库连接
     * @param hostName 主机名或ip
     * @param port mysql服务器端口
     * @param databaseName 数据库名
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public static Connection getConnection(String hostName, int port,String databaseName,String username, String password) {
        String url = "jdbc:mysql://" + hostName + ":" + port + "/" + databaseName + "?useUnicode=true&characterEncoding=UTF-8";
        Connection conn;
        try {
            // 加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
            return conn;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 用于查询时返回编译sql结果
     * @param conn
     * @param sql
     * @return
     */
    public static PreparedStatement getStatement(Connection conn, String sql) {
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            return pstmt;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 获取查询结果集
     * @param pstmt
     * @return
     */
    public static ResultSet getQueryResults(PreparedStatement pstmt) {
        ResultSet rs;
        try {
            rs = pstmt.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    /**
     * 销毁结果集对象
     * @param rs
     */
    public static void close(ResultSet rs) {
        try {
            if (rs!=null && !rs.isClosed()) {
                System.out.println(rs);
                rs.close();
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * 销毁语句对象
     * @param stmt
     */
    public static void close(PreparedStatement stmt) {
       try {
           if(stmt != null && !stmt.isClosed()) {
               stmt.close();
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }    
    }
    
    /**
     * 关闭数据库连接
     * @param conn
     */
    public static void close(Connection conn) {
        try {
            if(conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String args[]) {
        Connection conn1 = getConnection("127.0.0.1",3306,"tets","root","");
        if(conn1 != null) {
            System.out.println("获取连接成功,," + conn1);
        }
        PreparedStatement pstmt = getStatement(conn1, "select * from user");
        ResultSet rs = getQueryResults(pstmt);
        if(rs != null) {
            try {
                while(rs.next()) {
                    System.out.println(rs.getString("Host"));
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println(rs);
        Connection conn2 = getConnection("127.0.0.1",3306,"mysql","root","");
        close(rs);
        close(pstmt);
        close(conn1);
        close(conn2);
    }
    
}
