package org.fixmsgen.arguments;

import org.junit.Before;
import org.junit.Test;

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

}
