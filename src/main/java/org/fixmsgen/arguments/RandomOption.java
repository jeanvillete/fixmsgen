package org.fixmsgen.arguments;

import java.util.Objects;
import java.util.Random;

class RandomOption implements SpecialArgumentValue.SpecialArgumentImplementer {

    private final static String COMMA_AS_SEPARATOR = ",";

    private final IllegalArgumentException argumentValidationException = new IllegalArgumentException(
            "Special argument value [" + identifier() +  "] must receive a list of options non empty and " +
                    "split by commas, e.g; :" + identifier() + ":VAL_A,VAL_B,VAL_C"
    );

    @Override
    public String specialValue(String rawArgumentValue) {
        if (Objects.isNull(rawArgumentValue) || rawArgumentValue.length() == 0) {
            throw argumentValidationException;
        }
        if (rawArgumentValue.startsWith(COMMA_AS_SEPARATOR)) {
            throw argumentValidationException;
        }
        if (rawArgumentValue.endsWith(COMMA_AS_SEPARATOR)) {
            throw argumentValidationException;
        }

        String[] options = rawArgumentValue.split(COMMA_AS_SEPARATOR);
        for (String option : options) {
            if (option.length() == 0) {
                throw argumentValidationException;
            }
        }

        int randomIndex = new Random()
                .ints(0, options.length)
                .findFirst()
                .getAsInt();

        return options[randomIndex];
    }

    @Override
    public String identifier() {
        return "randopt";
    }
}
