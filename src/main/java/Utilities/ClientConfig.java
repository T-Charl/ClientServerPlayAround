package Utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ClientConfig {


    private final Properties properties;

    public ClientConfig(String configFileName) throws IOException {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(configFileName)) {
            if (input == null) {
                throw new IOException("Unable to find " + configFileName);
            }
            properties.load(input);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
