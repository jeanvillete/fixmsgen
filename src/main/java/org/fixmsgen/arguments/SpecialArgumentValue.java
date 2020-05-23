package org.fixmsgen.arguments;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpecialArgumentValue {

    @FunctionalInterface
    interface SpecialArgumentImplementer {

        String specialValue(String rawArgumentValue);

        default String identifier() {
            throw new IllegalStateException(
                    "Concrete Special Argument Implementer did not override identifier method, and it is mandatory."
            );
        }
    }

    private static class SpecialArgument {
        String identifier;
        String argument;

        SpecialArgument(String identifier, String argument) {
            this.identifier = identifier;
            this.argument = argument;
        }
    }

    private final static String SPECIAL_ARGUMENT_PATTERN = ":(\\w+):([\\w\\d,:-]*)";

    private final Map<String, SpecialArgumentImplementer> implementers;

    public SpecialArgumentValue() {
        RandomString randomString = new RandomString();
        RandomInteger randomInteger = new RandomInteger();
        RandomBigDecimal randomBigDecimal = new RandomBigDecimal();

        this.implementers = Collections.unmodifiableMap(
                new HashMap<String, SpecialArgumentImplementer>(){{
                    put(randomString.identifier(), randomString);
                    put(randomInteger.identifier(), randomInteger);
                    put(randomBigDecimal.identifier(), randomBigDecimal);
                }}
        );
    }

    public String checkAndApply(String rawArgumentValue) {
        return tryToParseSpecialArgument(rawArgumentValue)
                .map(specialArgument ->
                    Optional.ofNullable(implementers.get(specialArgument.identifier))
                            .map(specialArgumentImplementer -> specialArgumentImplementer.specialValue(specialArgument.argument))
                            .orElse(rawArgumentValue)
                )
                .orElse(rawArgumentValue);
    }

    private Optional<SpecialArgument> tryToParseSpecialArgument(String rawArgumentValue) {
        Matcher matcher = Pattern.compile(SPECIAL_ARGUMENT_PATTERN).matcher(rawArgumentValue);
        if (matcher.matches()) {
            return Optional.of(
                    new SpecialArgument(
                            matcher.group(1),
                            matcher.group(2)
                    )
            );
        }
        return Optional.empty();
    }

}
