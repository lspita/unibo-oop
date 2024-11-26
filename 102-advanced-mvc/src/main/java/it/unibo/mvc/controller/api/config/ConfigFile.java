package it.unibo.mvc.controller.api.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

public interface ConfigFile {

    /**
     * Set read property on config builder.
     * 
     * @param key read key
     * @param value read value
     * @param builder builder to change
     */
    void setProperty(String key, String value, Configuration.Builder builder);

    /**
     * Get configuration from file
     * 
     * @param file config file
     * @return the configuration
     * @throws IOException if there is an error reading the file
     * @throws FileNotFoundException if the file is not present
     */
    Configuration getConfiguration(File file) throws FileNotFoundException, IOException;

    /**
     * Get configuration from file path
     * 
     * @param resourceName resource name
     * @return the configuration
     * @throws URISyntaxException if resource name is malformed
     * @throws IOException if there is an error reading the file
     * @throws FileNotFoundException if the file is not present
     */
    default Configuration getConfiguration(final String resourceName) throws URISyntaxException, FileNotFoundException, IOException  {
        final var file = new File(ClassLoader.getSystemResource(resourceName).toURI());
        return this.getConfiguration(file);
    }
}
