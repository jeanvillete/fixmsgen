package org.fixmsgen.arguments;

import java.util.Objects;
import java.util.Random;

class RandomInteger implements SpecialArgumentValue.SpecialArgumentImplementer {

    @Override
    public String specialValue(String rawArgumentValue) {
        if (Objects.nonNull(rawArgumentValue) && rawArgumentValue.length() > 0) {
            throw new IllegalArgumentException(
                    "No argument can be supplied to special argument value [" + identifier() + "]"
            );
        }

        int randomInteger = new Random()
                .ints(1, Integer.MAX_VALUE)
                .findFirst()
                .getAsInt();

        return String.valueOf(randomInteger);
    }

    @Override
    public String identifier() {
        return "randint";
    }

}