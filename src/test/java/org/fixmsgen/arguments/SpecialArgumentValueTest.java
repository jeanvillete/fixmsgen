package org.fixmsgen.arguments;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.stream.IntStream;

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
}
