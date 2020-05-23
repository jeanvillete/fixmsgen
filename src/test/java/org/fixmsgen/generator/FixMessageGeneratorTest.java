package org.fixmsgen.generator;

import org.fixmsgen.generator.exception.IOExceptionOnReadingDefaultsFileContent;
import org.fixmsgen.generator.exception.InvalidSuppliedImplementation;
import org.fixmsgen.generator.exception.MandatoryParameterNotProvided;
import org.junit.Test;

import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

public class FixMessageGeneratorTest {

    @Test(expected = IllegalArgumentException.class)
    public void throws_exception_in_case_no_argument_is_provided() throws IOExceptionOnReadingDefaultsFileContent {
        new FixMessageGenerator(null);

        failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }

    @Test
    public void console_parameter_is_size_zero_in_case_no_argument_is_provided() throws IOExceptionOnReadingDefaultsFileContent {
        FixMessageGenerator fixMessageGenerator = new FixMessageGenerator(new String[]{});
        assertThat(fixMessageGenerator.getParameters().size()).isEqualTo(0);
    }

    @Test
    public void ensure_arguments_and_their_values() throws IOExceptionOnReadingDefaultsFileContent {
        FixMessageGenerator fixMessageGenerator = new FixMessageGenerator(
                new String[]{
                        "-11", "abc",
                        "-54", "2",
                        "-75", "20200531"
                }
        );

        assertThat(fixMessageGenerator.getParameters().getValue("-11")).isEqualTo("abc");
        assertThat(fixMessageGenerator.getParameters().getValue("-54")).isEqualTo("2");
        assertThat(fixMessageGenerator.getParameters().getValue("-75")).isEqualTo("20200531");
    }

    @Test(expected = IOExceptionOnReadingDefaultsFileContent.class)
    public void exception_in_case_argument_defaults_addresses_to_a_not_found_file() throws IOExceptionOnReadingDefaultsFileContent {
        FixMessageGenerator fixMessageGenerator = new FixMessageGenerator(
                new String[]{
                        "-defaults", "./sample-defaults.txt",
                        "-54", "2",
                        "-75", "20200531"
                }
        );

        failBecauseExceptionWasNotThrown(IOExceptionOnReadingDefaultsFileContent.class);
    }

    @Test
    public void load_file_content_for_argument_address() throws IOExceptionOnReadingDefaultsFileContent {
        URL resource = FixMessageGeneratorTest.class.getResource("/sample-defaults.txt");
        String fileAddressForArgumentDefaults = resource.getFile();

        FixMessageGenerator fixMessageGenerator = new FixMessageGenerator(
                new String[]{
                        "-defaults", fileAddressForArgumentDefaults,
                        "-54", "2", /* field provided on file, but overwritten here */
                        "-75", "20200531" /* field provided on file, but overwritten here */
                }
        );

        assertThat(fixMessageGenerator.getParameters().getValue("-11")).isEqualTo("123");
        assertThat(fixMessageGenerator.getParameters().getValue("-17")).isEqualTo("321");
        assertThat(fixMessageGenerator.getParameters().getValue("-54")).isEqualTo("2");
        assertThat(fixMessageGenerator.getParameters().getValue("-75")).isEqualTo("20200531");
    }

    @Test(expected = MandatoryParameterNotProvided.class)
    public void invoke_generate_fix_message_without_providing_parameter_for_implementation() throws IOExceptionOnReadingDefaultsFileContent, InvalidSuppliedImplementation, MandatoryParameterNotProvided {
        new FixMessageGenerator(
                new String[]{
                        "-54", "2",
                        "-75", "20200531"
                }
        ).generateFixMessage();
    }

    @Test(expected = InvalidSuppliedImplementation.class)
    public void look_for_invalid_implementation_value() throws IOExceptionOnReadingDefaultsFileContent, InvalidSuppliedImplementation, MandatoryParameterNotProvided {
        new FixMessageGenerator(
                new String[]{
                        "-i", "fake-impl-name"}
        ).generateFixMessage();
    }

    @Test
    public void check_valid_fix44_implementation_generated_content() throws IOExceptionOnReadingDefaultsFileContent, InvalidSuppliedImplementation, MandatoryParameterNotProvided {
        String fixContent = new FixMessageGenerator(
                new String[]{
                        "-i", "fix-4.4-dc-created"}
        ).generateFixMessage();

        assertThat(fixContent).isNotNull().isNotBlank().isNotEmpty();
    }
}
