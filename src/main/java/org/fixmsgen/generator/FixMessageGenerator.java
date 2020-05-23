package org.fixmsgen.generator;

import org.fixmsgen.arguments.ConsoleParameters;
import org.fixmsgen.generator.exception.IOExceptionOnReadingDefaultsFileContent;
import org.fixmsgen.generator.exception.InvalidSuppliedImplementation;
import org.fixmsgen.generator.exception.MandatoryParameterNotProvided;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import static java.util.Collections.unmodifiableMap;

public class FixMessageGenerator {

    interface Implementation {
        void generateFixContent();
    }

    static final String ARGUMENT_DEFAULTS = "-defaults";
    static final String ARGUMENT_IMPLEMENTATION = "-i";

    static final Map<String, Implementation> IMPLEMENTATIONS = unmodifiableMap(
            new HashMap<String, Implementation>(){{
            }}
    );

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

    public void generateFixMessages() throws MandatoryParameterNotProvided, InvalidSuppliedImplementation {
        String implementation = parameters.getRequiredValue(ARGUMENT_IMPLEMENTATION);

        Optional.ofNullable(IMPLEMENTATIONS.get(implementation))
                .orElseThrow(() ->
                        new InvalidSuppliedImplementation("No implementation with value [" + implementation + "] is available.")
                )
                .generateFixContent();
    }
}
