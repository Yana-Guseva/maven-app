package dogapp;

import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RequiredArgsConstructor
public class JdbcConnectionHolder {

    private final DataSource dataSource;

    private final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

    public Connection getConnection() {
        Connection connection = connectionHolder.get();
        try {
            if (connection == null) {
                connection = dataSource.getConnection();
                connectionHolder.set(connection);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public void closeConnection() {
        Connection connection = connectionHolder.get();
        try {
            if (connection != null) {
                connection.close();
                connectionHolder.remove();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
