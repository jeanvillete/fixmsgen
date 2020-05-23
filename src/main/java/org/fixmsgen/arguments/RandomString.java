package org.fixmsgen.arguments;

import java.util.Objects;
import java.util.Random;

class RandomString implements SpecialArgumentValue.SpecialArgumentImplementer {

    private final static int FROM_UPPERCASE_A = 65;
    private final static int TO_UPPERCASE_Z = 90;

    private final IllegalArgumentException argumentValidationException = new IllegalArgumentException(
            "Special argument value [" + identifier() +  "] must receive a numeric argument, " +
                    "e.g; :" + identifier() + ":5"
    );

    @Override
    public String specialValue(String specialArgument) {
        if (Objects.isNull(specialArgument) || specialArgument.length() == 0) {
            throw argumentValidationException;
        }

        Integer randomStringSize;
        try {
            randomStringSize = Integer.parseInt(specialArgument);
        } catch (NumberFormatException e) {
            throw argumentValidationException;
        }

        return new Random()
                .ints(FROM_UPPERCASE_A, TO_UPPERCASE_Z + 1)
                .limit(randomStringSize)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    @Override
    public String identifier() {
        return "randstr";
    }

}
