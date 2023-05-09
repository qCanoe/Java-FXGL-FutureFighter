package com.canoe.login.jdbc;

import java.sql.*;


public class Conn {

    private String host;

    private int port;

    private String user;

    private String password;

    private String database;


    //注册驱动
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (
                ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection conn;

    public Conn() {
        this.host = "localhost";
        this.port = 3306;
        this.user = "root";
        this.password = "123456";
        this.database = "mydatabase";
    }

    //获取连接url
    private String getUrl() {
        return "jdbc:mysql://" + host + ":" + port + "/" + database + "?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8";
    }
    //原note有疑问

    //连接数据库
    public Connection getConn() throws SQLException {
        if (conn == null) {
            conn = DriverManager.getConnection(getUrl(), user, password);
        }
        return conn;
    }

    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                System.out.println("关闭连接发生异常" + e.getMessage());
            }
        }
    }

    //执行sql
    public ResultSet excuseSql(String sql) throws SQLException {
        if (conn == null) {
            getConn();
        }

        Statement statement = null;
        statement = conn.createStatement();
        boolean execute = statement.execute(sql);
        if (!execute) {
            System.err.println("执行sql失败");
            return null;
        }
        return statement.getResultSet();
    }

    // Define a method for logging in with a username and password
    public Boolean login(String username, String password) throws SQLException {
        String sql = "select * from users where username = '" + username + "'";
        ResultSet resultSet = excuseSql(sql);
        while (resultSet.next()) {
            String passwordInDB = resultSet.getString("password");
            if (password.equals(passwordInDB)) {
                return true;
            }
        }
        return false;
    }



     /*
     方法：连接数据库
     方法：关闭连接
     方法：执行sql语句
     方法：通过用户名、密码查询用户记录
     conn.excuse(sql)
     判断是否存在该用户名，密码
     如果存在返回tru
     如果不存在返回false
     */

    public static void main(String[] args) {

    }
}
