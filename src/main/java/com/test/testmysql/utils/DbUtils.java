package com.test.testmysql.utils;




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *  数据库连接工具类
 */
public class DbUtils {

    public static Connection getConnQuery() throws ClassNotFoundException, SQLException {
//    String url = "jdbc:mysql://localhost:3306/low_log_schema?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true";
       // String url = "jdbc:mysql://192.168.0.213:3306/low_log_scheam?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true";
      String url = "jdbc:mysql://172.29.0.3:8066/all_low_log_schema?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true";
        String username = "root";
       String password = "nd2wW8Myi19";
     //  String password = "root";
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }

    public static Connection getConnInsert() throws ClassNotFoundException, SQLException {
      String url = "jdbc:mysql://localhost:3306/all_low_log_schema?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true";
    //    String url = "jdbc:mysql://192.168.0.213:3306/low_log_scheam?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true";
        String username = "root";
       String password = "kqJEkQftzaxehRc3";
    //    String password="JSNyk&29Mlt";
     //   String password = "root123";
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }
}
