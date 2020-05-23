package org.fixmsgen;

import org.fixmsgen.generator.FixMessageGenerator;
import org.fixmsgen.generator.exception.IOExceptionOnReadingDefaultsFileContent;

public class App {

    public static void main(String[] args) {
        try {
            new FixMessageGenerator(args)
                    .generateFixMessages();
        } catch (IOExceptionOnReadingDefaultsFileContent e) {
            System.err.println(e);
            System.exit(1);
        }
    }

}