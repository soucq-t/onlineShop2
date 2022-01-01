package app;

import persistence.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Setup {
    public static Connection getConnection() throws SQLException {
        var properties = new Properties();
        properties.put("user", "sa");
        properties.put("password", "");
        var connection = DriverManager.getConnection("""
                jdbc:sqlserver://IFSQL-03.htl-stp.if;database=chen_onlineShop
                """, properties);
        return connection;
    }

}