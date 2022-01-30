package za.ac.cput.adp2.mymavendb.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection connectDerby() throws SQLException {
        String url = "jdbc:derby://localhost:1527/Parts";
        String user = "Sharfaa";
        String password = "Sharfaa";
        return DriverManager.getConnection(url, user, password);
    }

}
