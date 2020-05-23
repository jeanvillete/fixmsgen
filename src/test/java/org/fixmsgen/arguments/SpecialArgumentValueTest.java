package org.fixmsgen.arguments;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

public class SpecialArgumentValueTest {

    private SpecialArgumentValue specialArgumentValue;

    @Before
    public void setUp() {
        specialArgumentValue = new SpecialArgumentValue();
    }

    @Test
    public void when_supplied_a_non_special_argument_pattern_retrieve_the_raw_argument_value() {
        // given
        String rawArgumentValue = "simple content value";

        // when
        String returnedArgumentValue = specialArgumentValue.checkAndApply(rawArgumentValue);

        // then
        assertThat(returnedArgumentValue).isNotNull().isNotBlank().isNotEmpty();
        assertThat(returnedArgumentValue).isEqualTo(rawArgumentValue);
    }

    @Test
    public void when_supplied_invalid_special_argument_pattern_retrieve_the_raw_argument_value() {
        // given
        String rawArgumentValueFakeInvalidImplementer = ":fake:arg";

        // when
        String returnedArgumentValue = specialArgumentValue.checkAndApply(rawArgumentValueFakeInvalidImplementer);

        // then
        assertThat(returnedArgumentValue).isNotNull().isNotBlank().isNotEmpty();
        assertThat(returnedArgumentValue).isEqualTo(rawArgumentValueFakeInvalidImplementer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throws_exception_when_random_string_without_argument() {
        specialArgumentValue.checkAndApply(":randstr:");

        failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throws_exception_when_random_string_with_argument_non_integer() {
        specialArgumentValue.checkAndApply(":randstr:abc");

        failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }

    @Test
    public void ensure_random_string_retrieves_valid_string_with_different_sizes() {
        IntStream.range(1, 50)
                .forEach(index -> {
                    String specialGeneratedValue = specialArgumentValue.checkAndApply(":randstr:" + index);

                    assertThat(specialGeneratedValue)
                            .isNotNull()
                            .isNotBlank()
                            .isNotEmpty()
                            .hasSize(index);
                });
    }

    @Test(expected = IllegalArgumentException.class)
    public void throws_exception_in_case_a_non_numeric_argument_is_provided_to_random_integer() {
        specialArgumentValue.checkAndApply(":randint:arg");

        failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throws_exception_in_case_a_numeric_argument_is_provided_to_random_integer() {
        specialArgumentValue.checkAndApply(":randint:123");

        failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }

    @Test
    public void ensure_random_integer_retrieves_valid_positive_values_for_different_invokes() {
        IntStream.range(1, 50)
                .forEach(index -> {
                    String specialGeneratedValue = specialArgumentValue.checkAndApply(":randint:");
                    assertThat(specialGeneratedValue)
                            .isNotNull()
                            .isNotBlank()
                            .isNotEmpty();

                    int generatedValueAsInteger = Integer.parseInt(specialGeneratedValue);
                    assertThat(generatedValueAsInteger).isGreaterThan(0);
                });
    }

    @Test(expected = IllegalArgumentException.class)
    public void throws_exception_when_random_big_decimal_does_not_receive_argument() {
        specialArgumentValue.checkAndApply(":randbigdec:");

        failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throws_exception_when_random_big_decimal_receives_non_numeric_argument() {
        specialArgumentValue.checkAndApply(":randbigdec:abc");

        failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }

    @Test
    public void ensure_random_big_decimal_retrieves_valid_positive_values_for_different_invokes() {
        IntStream.range(1, 50)
                .forEach(index -> {
                    String specialGeneratedValue = specialArgumentValue.checkAndApply(":randbigdec:" + index);
                    assertThat(specialGeneratedValue)
                            .isNotNull()
                            .isNotBlank()
                            .isNotEmpty();

                    BigDecimal generatedBigDecimalValue = new BigDecimal(specialGeneratedValue);
                    assertThat(generatedBigDecimalValue).isGreaterThan(BigDecimal.ZERO);
                });
    }

    @Test(expected = IllegalArgumentException.class)
    public void throws_exception_when_random_option_does_not_receive_argument() {
        specialArgumentValue.checkAndApply(":randopt:");

        failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throws_exception_when_random_option_receives_an_empty_option_on_the_beginning() {
        specialArgumentValue.checkAndApply(":randopt:,VAL_B,VAL_C");

        failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throws_exception_when_random_option_receives_an_empty_option_on_the_middle() {
        specialArgumentValue.checkAndApply(":randopt:VAL_A,,VAL_C");

        failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throws_exception_when_random_option_receives_an_empty_option_at_the_end() {
        specialArgumentValue.checkAndApply(":randopt:VAL_A,VAL_B,");

        failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }

    @Test
    public void ensure_random_option_retrieves_valid_option_from_list_passed_as_argument_for_different_invokes() {
        IntStream.range(1, 50)
                .forEach(index -> {
                    String specialGeneratedValue = specialArgumentValue.checkAndApply(":randopt:VAL_A,VAL_B,VAL_C");
                    assertThat(specialGeneratedValue)
                            .isNotNull()
                            .isNotBlank()
                            .isNotEmpty()
                            .isIn(
                                    "VAL_A",
                                    "VAL_B",
                                    "VAL_C"
                            );
                });
    }

    @Test(expected = IllegalArgumentException.class)
    public void throws_exception_when_now_does_not_receive_any_argument() {
        specialArgumentValue.checkAndApply(":now:");

        failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throws_exception_when_now_receives_invalid_date_time_format() {
        specialArgumentValue.checkAndApply(":now:abc");

        failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }

    @Test
    public void ensure_now_retrieves_valid_date_data() {
        String generatedDateValue = specialArgumentValue.checkAndApply(":now:yyyy-MM-dd");
        assertThat(generatedDateValue)
                .isNotNull()
                .isNotBlank()
                .isNotEmpty()
                .hasSize(10);

        LocalDate todayParsed = LocalDate.parse(generatedDateValue);
        LocalDate todayFromLocalDateNowFunction = LocalDate.now();

        assertThat(todayParsed).isBeforeOrEqualTo(todayFromLocalDateNowFunction);
    }

    @Test
    public void ensure_now_retrieves_valid_time_data() {
        String generatedTimeValue = specialArgumentValue.checkAndApply(":now:HH:mm:ss");
        assertThat(generatedTimeValue)
                .isNotNull()
                .isNotBlank()
                .isNotEmpty()
                .hasSize(8);

        LocalTime nowParsed = LocalTime.parse(generatedTimeValue);
        LocalTime nowFromLocalTimeNowFunction = LocalTime.now();

        assertThat(nowParsed).isBeforeOrEqualTo(nowFromLocalTimeNowFunction);
    }

    @Test
    public void ensure_now_retrieves_valid_date_time_data() {
        asList(
                "yyyyMMdd'T'HH:mm:ss.SSS",
                "yyyyMMdd'T'HH:mm:ss",
                "yyyyMMdd'T'HH:mm",
                "yyyyMMdd-HH:mm:ss.SSS",
                "yyyyMMdd-HH:mm:ss",
                "yyyyMMdd-HH:mm"
        ).forEach(dateTimePattern -> {
            String generatedTimeValue = specialArgumentValue.checkAndApply(":now:" + dateTimePattern);
            assertThat(generatedTimeValue)
                    .isNotNull()
                    .isNotBlank()
                    .isNotEmpty();

            LocalDateTime localDateTimeParsed = LocalDateTime.parse(generatedTimeValue, DateTimeFormatter.ofPattern(dateTimePattern));
            LocalDateTime localDateTimeFromNowFunction = LocalDateTime.now();

            assertThat(localDateTimeParsed).isBeforeOrEqualTo(localDateTimeFromNowFunction);
        });
    }
}
