package org.fixmsgen.generator;

import org.fixmsgen.arguments.ConsoleParameters;

import java.util.stream.Stream;

class Fix44DropCopyCreated implements FixMessageGenerator.Implementation {

    final ConsoleParameters parameters;

    public Fix44DropCopyCreated(ConsoleParameters.ReadOnly parameters) {
        this.parameters = parameters;
    }

    @Override
    public Stream<String> generateFixContent() {
        return Stream.of(
                fixVersion(),
                executionReport(),
                executionReportType()
        );
    }

    private String executionReport() {
        return "35=8";
    }

    private String executionReportType() {
        return "150=F";
    }

    private String fixVersion() {
        return "8=FIX.4.4";
    }

}
