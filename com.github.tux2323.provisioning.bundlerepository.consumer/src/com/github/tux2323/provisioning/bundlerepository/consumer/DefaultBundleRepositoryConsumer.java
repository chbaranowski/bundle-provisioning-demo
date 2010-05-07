package com.github.tux2323.provisioning.bundlerepository.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.tux2323.provisioning.bundlerepository.BundleRepositoryConsumer;
import com.github.tux2323.provisioning.bundlerepository.RepositoryBundle;

public class DefaultBundleRepositoryConsumer implements BundleRepositoryConsumer {

	private static final Logger logger = LoggerFactory.getLogger(DefaultBundleRepositoryConsumer.class);

	public void bundleIsAvailable(RepositoryBundle bundle) {
		logger.info("Bundle " + bundle.getSymbolicName() + " is available!");
		
	}

	public void bundleUpdateIsAvailable(RepositoryBundle updateBundle) {
		logger.info("Bundle update for bundle with name: " + updateBundle.getSymbolicName() + " is available ");
	}

	public String[] getRequiredBundels() {
		return new String[]{"com.github.tux2323.sms.core"};
	}

}
