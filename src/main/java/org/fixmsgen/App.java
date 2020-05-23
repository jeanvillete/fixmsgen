package org.fixmsgen;

import org.fixmsgen.generator.FixMessageGenerator;
import org.fixmsgen.generator.exception.IOExceptionOnReadingDefaultsFileContent;
import org.fixmsgen.generator.exception.InvalidSuppliedImplementation;
import org.fixmsgen.generator.exception.MandatoryParameterNotProvided;

public class App {

    public static void main(String[] args) {
        try {
            String fixMessage = new FixMessageGenerator(args).generateFixMessage();

            System.out.println(fixMessage);
        } catch (MandatoryParameterNotProvided | IOExceptionOnReadingDefaultsFileContent | InvalidSuppliedImplementation e) {
            System.err.println(e);
            System.exit(1);
        }
    }

}