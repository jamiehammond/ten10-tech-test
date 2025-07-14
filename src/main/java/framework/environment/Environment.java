package framework.environment;

public class Environment {

    private static final String DEFAULT_ENVIRONMENT_NAME = "local";

    public static String getEnvironmentName() {
        return DEFAULT_ENVIRONMENT_NAME;
    }
}
