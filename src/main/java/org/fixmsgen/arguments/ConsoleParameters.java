package org.fixmsgen.arguments;

import java.util.HashMap;
import java.util.Map;

public class ConsoleParameters {
	
	private Map<String, String> mappedParams = new HashMap<>();

	public ConsoleParameters(String[] args) {
		this.build(args);
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

	public boolean containsArgument(String key) {
		return this.mappedParams.containsKey(key);
	}

	public String getValueOrDefault(String key, String defaultValue) {
		return this.mappedParams.getOrDefault(key, defaultValue);
	}

	public Integer size() {
		return this.mappedParams.size();
	}
}
