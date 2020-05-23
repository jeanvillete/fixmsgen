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
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableMap;
import static java.util.stream.Collectors.joining;

public class FixMessageGenerator {

    interface Implementation {
        Stream<String> generateFixContent();
    }

    private static final String ARGUMENT_DEFAULTS = "-defaults";
    private static final String ARGUMENT_IMPLEMENTATION = "-i";

    private final Map<String, Implementation> implementations;
    private final ConsoleParameters parameters;

    public FixMessageGenerator(String[] args) throws IOExceptionOnReadingDefaultsFileContent {
        this.parameters = parseConsoleParameters(args);

        this.implementations = unmodifiableMap(
                new HashMap<String, Implementation>(){{
                    put(
                            "fix-4.4-dc-created",
                            new Fix44DropCopyCreated(getParameters())
                    );
                }}
        );
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

    public String generateFixMessage() throws MandatoryParameterNotProvided, InvalidSuppliedImplementation {
        String implementation = parameters.getRequiredValue(ARGUMENT_IMPLEMENTATION);

        return Optional.ofNullable(implementations.get(implementation))
                .orElseThrow(() ->
                        new InvalidSuppliedImplementation("No implementation with value [" + implementation + "] is available.")
                )
                .generateFixContent()
                .collect(joining("\u0001"));
    }

    ConsoleParameters.ReadOnly getParameters() {
        return ConsoleParameters.ReadOnly.of(parameters);
    }
}
