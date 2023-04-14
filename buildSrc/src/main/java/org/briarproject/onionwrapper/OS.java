package org.briarproject.onionwrapper;

public enum OS {
	Linux("linux"),
	Windows("windows"),
	MacOS("macos");

	private String id;

	OS(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
