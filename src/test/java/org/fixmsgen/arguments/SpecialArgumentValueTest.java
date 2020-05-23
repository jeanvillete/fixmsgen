package org.fixmsgen.arguments;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
}
