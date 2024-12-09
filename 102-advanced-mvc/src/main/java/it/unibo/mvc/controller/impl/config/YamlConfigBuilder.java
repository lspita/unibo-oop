package it.unibo.mvc.controller.impl.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

import it.unibo.mvc.controller.api.config.FileConfigBuilder;
import it.unibo.mvc.controller.api.config.Configuration;
import it.unibo.mvc.controller.api.config.Configuration.Builder;

public class YamlConfigBuilder implements FileConfigBuilder {
    
    private static final String YAML_KEY_VAL_SEP = ": ";
    private static final String MAX_KEY = "maximum";
    private static final String MIN_KEY = "minimum";
    private static final String ATTEMPTS_KEY = "attempts";

    @Override
    public Configuration getConfiguration(final File file) throws FileNotFoundException, IOException {
        Objects.requireNonNull(file);
        var configBuilder = new Configuration.Builder();

        try (
            BufferedReader reader = new BufferedReader(new FileReader(file))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                final var splittedLine = line.split(YAML_KEY_VAL_SEP);
                if (splittedLine.length < 2) {
                    throw new IllegalArgumentException(line + " is not valid YAML syntax");
                }
                final String key = splittedLine[0].trim();
                final String value = splittedLine[1].trim();
                configBuilder = setConfigProperty(key, value, configBuilder);
            }
        }

        return configBuilder.build();
    }

    private Builder setConfigProperty(final String key, final String value, final Builder builder) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        Objects.requireNonNull(builder);
        
        return switch (key) {
            case MAX_KEY -> builder.setMax(Integer.parseInt(value));
            case MIN_KEY -> builder.setMin(Integer.parseInt(value));
            case ATTEMPTS_KEY -> builder.setAttempts(Integer.parseInt(value));
            default -> throw new IllegalArgumentException("Invalid prop key");
        };
    }
    
}
