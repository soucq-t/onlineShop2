package persistence.setup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSupplier {
    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection("jdbc:sqlserver://IFSQL-03.htl-stp.if;"+
                "database=chen_onlineShop");
    }
}
