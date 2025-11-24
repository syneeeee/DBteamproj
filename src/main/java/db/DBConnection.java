package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String HOST = "aws-1-ap-northeast-2.pooler.supabase.com";
    private static final String PORT = "5432";
    private static final String DATABASE = "postgres";
    private static final String USER = "postgres.gtphipnfnieqtdxfumhb";
    private static final String PASSWORD = "Lullaby3042@!";

    private static final String URL =
            "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DATABASE +
                    "?sslmode=require";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
