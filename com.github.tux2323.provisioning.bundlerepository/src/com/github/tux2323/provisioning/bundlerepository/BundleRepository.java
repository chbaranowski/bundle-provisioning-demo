package com.github.tux2323.provisioning.bundlerepository;

import java.io.Serializable;

public interface BundleRepository {
	
	String getBundleLocation(String bundleSymbolicName);
	
}
