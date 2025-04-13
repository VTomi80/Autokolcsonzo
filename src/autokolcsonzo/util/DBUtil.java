package autokolcsonzo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    //postgreSQL adatbázis kapcsolati sztring paraméterek deifinálása.
    //ezt az osztályt importálom, és használom a DAO osztályokban, hogy elérjem a postgreSQL adatbázist.

    private static final String URL = "jdbc:postgresql://localhost:5432/autokolcsonzo";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres"; // ha trust módban van, üres lehet

    public static Connection getConnection() throws SQLException { //kapcsoalti string átadása a getConnection metódusnak
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
