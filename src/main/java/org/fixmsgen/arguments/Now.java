package org.fixmsgen.arguments;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Now implements SpecialArgumentValue.SpecialArgumentImplementer {

    @Override
    public String specialValue(String rawArgumentValue) {
        if (Objects.isNull(rawArgumentValue) || rawArgumentValue.length() == 0) {
            throw new IllegalArgumentException(
                    "Special argument value [" + identifier() +  "] must receive as argument a data time pattern, " +
                            "e.g; :" + identifier() + ":YYYYMMdd-HH:mm:ss:SSS"
            );
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(rawArgumentValue);

        return LocalDateTime
                .now()
                .format(dateTimeFormatter);
    }

    @Override
    public String identifier() {
        return "now";
    }

}
