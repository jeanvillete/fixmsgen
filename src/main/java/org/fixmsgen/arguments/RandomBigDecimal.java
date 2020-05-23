package org.fixmsgen.arguments;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Random;

public class RandomBigDecimal implements SpecialArgumentValue.SpecialArgumentImplementer {

    private final IllegalArgumentException argumentValidationException = new IllegalArgumentException(
            "Special argument value [" + identifier() +  "] must receive a numeric argument, " +
                    "e.g; :" + identifier() + ":2"
    );

    @Override
    public String specialValue(String rawArgumentValue) {
        if (Objects.isNull(rawArgumentValue) || rawArgumentValue.length() == 0) {
            throw argumentValidationException;
        }

        Integer bigDecimalScale;
        try {
            bigDecimalScale = Integer.parseInt(rawArgumentValue);
        } catch (NumberFormatException e) {
            throw argumentValidationException;
        }

        int randomIntegerPart = new Random()
                .ints(1, Integer.MAX_VALUE)
                .findFirst()
                .getAsInt();
        int randomDecimalPart = new Random()
                .ints(1, Integer.MAX_VALUE)
                .findFirst()
                .getAsInt();

        return new BigDecimal(randomIntegerPart + "." + randomDecimalPart)
                .setScale(bigDecimalScale, RoundingMode.UP)
                .toString();
    }

    @Override
    public String identifier() {
        return "randbigdec";
    }
}
