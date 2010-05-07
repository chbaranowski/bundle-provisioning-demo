package com.github.tux2323.provisioning.bundlerepository;

import java.io.Serializable;

public class RepositoryBundle implements Serializable{

	private String symbolicName;
	
	private String version;
	
	private String location;

	public void setSymbolicName(String symbolicName) {
		this.symbolicName = symbolicName;
	}

	public String getSymbolicName() {
		return symbolicName;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getVersion() {
		return version;
	}
	
}
