package framework.driver.producer;

import framework.properties.CommonProperties;
import framework.properties.PropertyLoader;

public class BaseLocalDriver {

    protected final static String browserVersion = PropertyLoader.getProperty(CommonProperties.BROWSER_VERSION);
}
