package com.github.tux2323.provisioning.bundlerepository;

public interface BundleRepositoryConsumer {

	String[] getRequiredBundels();

	void bundleIsAvailable(RepositoryBundle bundle);
	
	void bundleUpdateIsAvailable(RepositoryBundle updateBundle);
	
}
