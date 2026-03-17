package config;

/**
 * Singleton que representa la configuración global de la aplicación.
 * Solo existe una instancia durante toda la ejecución.
 */
public class ApplicationConfig {

    private static ApplicationConfig instance;

    private String systemName;
    private String version;

    // Constructor privado
    private ApplicationConfig() {
        systemName = "Electronic Components Manager";
        version = "2.0";
    }

    // Método que devuelve la única instancia
    public static ApplicationConfig getInstance() {

        if (instance == null) {
            instance = new ApplicationConfig();
        }

        return instance;
    }

    public String getSystemName() {
        return systemName;
    }

    public String getVersion() {
        return version;
    }

}