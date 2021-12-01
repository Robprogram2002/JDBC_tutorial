package Data;

import java.sql.*;

public class MakeConnection {
    private static final String URI = "jdbc:postgresql://localhost:5432/java_tutorial";
    private static final String USER = "postgres";
    private static final String PASSWORD = "passwordSegura20";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URI,USER,PASSWORD);
    }

    public static void close(ResultSet set) throws SQLException {
        set.close();
    }

    public static void close(Statement statement) throws SQLException {
        statement.close();
    }

    public static void close(Connection connection) throws SQLException {
        connection.close();
    }

    public static void close(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.close();
    }
}
