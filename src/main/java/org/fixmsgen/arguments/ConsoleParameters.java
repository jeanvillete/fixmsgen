package org.fixmsgen.arguments;

import org.fixmsgen.generator.exception.MandatoryParameterNotProvided;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ConsoleParameters {

	public static class ReadOnly extends ConsoleParameters {

		private ConsoleParameters consoleParameters;

		public static ReadOnly of(ConsoleParameters consoleParameters) {
			ReadOnly readOnly = new ReadOnly();
			readOnly.consoleParameters = consoleParameters;
			return readOnly;
		}

		@Override
		public void addIfAbsent(String key, String value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public String getValue(String key) {
			return consoleParameters.getValue(key);
		}

		@Override
		public String getRequiredValue(String key) throws MandatoryParameterNotProvided {
			return consoleParameters.getRequiredValue(key);
		}

		@Override
		public boolean containsArgument(String key) {
			return consoleParameters.containsArgument(key);
		}

		@Override
		public Integer size() {
			return consoleParameters.size();
		}
	}

	private Map<String, String> mappedParams = new HashMap<>();

	public ConsoleParameters(String[] args) {
		this.build(args);
	}

	private ConsoleParameters(){
	}

	private void build(String[] args) {
		if (args == null) {
			throw new IllegalArgumentException("List of arguments cannot be null.");
		}

		for (int index=0 ; index < args.length ; index++) {
			String arg = args[index];
			if (arg.startsWith("-")) {
				if (index+1 < args.length && !args[index+1].startsWith("-")) {
					this.mappedParams.put(arg, args[++index]);
				} else {
					this.mappedParams.put(arg, arg);
				}
			} else {
				this.mappedParams.put(arg, arg);
			}
		}
	}

	public void addIfAbsent(String key, String value) {
		if (!mappedParams.containsKey(key)) {
			mappedParams.put(key, value);
		}
	}

	public String getValue(String key) {
		return this.mappedParams.get(key);
	}

	public String getRequiredValue(String key) throws MandatoryParameterNotProvided {
		return Optional.ofNullable(this.mappedParams.get(key))
                .orElseThrow(() ->
                        new MandatoryParameterNotProvided("Mandatory parameter with key [" + key + "] was not provided.")
                );
	}

	public boolean containsArgument(String key) {
		return this.mappedParams.containsKey(key);
	}

	public Integer size() {
		return this.mappedParams.size();
	}
}
