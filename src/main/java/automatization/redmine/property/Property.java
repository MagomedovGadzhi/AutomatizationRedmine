package automatization.redmine.property;

import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.util.Properties;

public class Property {

    private static String propertiesName = System.getProperty("properties", "default.properties");
    private static Properties properties = new Properties();
    private static boolean isInitialized = false;

    @SneakyThrows
    private static void init() {
        properties.load(new FileInputStream("src/test/resources/" + propertiesName));
        isInitialized = true;
    }

    public static String getStringProperty(String key) {
        if (!isInitialized) {
            init();
        }
        if (System.getProperty(key) != null) {
            return System.getProperty(key);
        }
        return properties.getProperty(key);
    }

    public static Integer getIntegerProperty(String key) {
        if (!isInitialized) {
            init();
        }
        return Integer.parseInt(getStringProperty(key));
    }

    public static Boolean getBooleanProperty(String key) {
        if (!isInitialized) {
            init();
        }
        return Boolean.parseBoolean(getStringProperty(key));
    }
}