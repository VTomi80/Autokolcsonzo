package autokolcsonzo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String URL = "jdbc:postgresql://localhost:5433/autokolcsonzo";
    private static final String USER = "postgres";
    private static final String PASSWORD = ""; // ha trust módban van, üres lehet

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
