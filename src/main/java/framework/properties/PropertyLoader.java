package framework.properties;

import com.github.dockerjava.core.dockerfile.DockerfileStatement;
import framework.environment.Environment;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

@Slf4j
public class PropertyLoader {

    private static final Set<PropertiesFileConfig> propertiesFileConfigs;

    static {
        propertiesFileConfigs = new HashSet<>();
        String environment = Environment.getEnvironmentName();
        registerPropertiesFileConfig(new PropertiesFileConfig("properties/" + environment + ".properties"));
    }

    public static String getProperty(String propertyKey) {
        if (System.getProperty(propertyKey) != null) {
            log.debug("Found property, " + propertyKey + " as a system property of " + System.getProperty(propertyKey));
            return System.getProperty(propertyKey);
        }

        for (PropertiesFileConfig config : propertiesFileConfigs) {
            String property = getValueFromPropertiesFile(config, propertyKey);
            if (property != null) {
                log.debug("Found property, " + propertyKey + " in file: " + config.getFileName() + " with value of " + property);
                return property;
            }
        }
        return null;
    }

    public static void registerPropertiesFileConfig(PropertiesFileConfig propertiesFileConfig) {
        propertiesFileConfigs.add(propertiesFileConfig);
    }

    private static String getValueFromPropertiesFile(PropertiesFileConfig config, String propertyKey) {
        Properties properties = new Properties();
        InputStream inputStream;
        try {
            inputStream = PropertyLoader.class.getClassLoader().getResourceAsStream(config.getFileName());
            if (inputStream == null) {
                System.out.println("Unable to find file:  " + config.getFileName());
                return null;
            }
            log.debug("Loaded resource from: " + PropertyLoader.class.getClassLoader().getResource(config.getFileName()).getPath());
            properties.load(inputStream);

            return properties.getProperty(propertyKey);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
