package automatization.redmine.db.connection;

import automatization.redmine.property.Property;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class PostgresConnection implements DatabaseConnection {
    public final static DatabaseConnection INSTANCE = new PostgresConnection();

    private String host = Property.getStringProperty("db.host");
    private Integer port = Property.getIntegerProperty("db.port");
    private String database = Property.getStringProperty("db.database");
    private String user = Property.getStringProperty("db.user");
    private String password = Property.getStringProperty("db.password");
    private Connection connection;

    public PostgresConnection() {
        connect();
    }

    @SneakyThrows
    @Step("Подключение к БД")
    private void connect() {
        Class.forName("org.postgresql.Driver");

        String url = String.format("jdbc:postgresql://%s:%d/%s", host, port, database);
        Properties connectionProperties = new Properties();
        connectionProperties.setProperty("user", user);
        connectionProperties.setProperty("password", password);
        connection = DriverManager.getConnection(url, connectionProperties);
    }

    @Override
    @SneakyThrows
    @Step("Выполнение запроса к БД")
    public List<Map<String, Object>> executeQuery(String query, Object... parameters) {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i]);
            }
            Allure.addAttachment("SQL-запрос", statement.toString());
            ResultSet rs = statement.executeQuery();

            List<Map<String, Object>> result = new ArrayList<>();

            while (rs.next()) {
                Map<String, Object> oneLineResult = new HashMap<>();
                int columnCount = rs.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    String key = rs.getMetaData().getColumnName(i);
                    Object value = rs.getObject(i);
                    oneLineResult.put(key, value);
                }
                result.add(oneLineResult);
            }
            Allure.addAttachment("SQL-ответ", result.toString());
            return result;
        } catch (PSQLException exception) {
            if (exception.getMessage().equals("Запрос не вернул результатов.")) {
                return null;
            } else {
                throw exception;
            }
        }
    }
}
