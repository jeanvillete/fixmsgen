package org.fixmsgen.generator;

import org.fixmsgen.arguments.ConsoleParameters;
import org.fixmsgen.generator.exception.IOExceptionOnReadingDefaultsFileContent;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class FixMessageGenerator {

    static final String ARGUMENT_DEFAULTS = "-defaults";

    ConsoleParameters parameters;

    public FixMessageGenerator(String[] args) throws IOExceptionOnReadingDefaultsFileContent {
        this.parameters = parseConsoleParameters(args);
    }

    private ConsoleParameters parseConsoleParameters(String[] args) throws IOExceptionOnReadingDefaultsFileContent {
        ConsoleParameters parameters = new ConsoleParameters(args);
        if (parameters.containsArgument(ARGUMENT_DEFAULTS)) {
            try {
                String fileAddressWithDefaults = parameters.getValue(ARGUMENT_DEFAULTS);

                Properties defaultsFileContent = new Properties();
                defaultsFileContent.load(
                        new FileInputStream(fileAddressWithDefaults)
                );
                defaultsFileContent.stringPropertyNames()
                        .forEach(propertyName ->
                                parameters.addIfAbsent(
                                        propertyName,
                                        defaultsFileContent.getProperty(propertyName)
                                )
                        );
            } catch (IOException e) {
                throw new IOExceptionOnReadingDefaultsFileContent(e);
            }
        }

        return parameters;
    }

    public void generateFixMessages() {
    }
}
