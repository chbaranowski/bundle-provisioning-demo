package com.github.tux2323.provisioning.bundlerepository.consumer;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.osgi.context.BundleContextAware;

import com.github.tux2323.provisioning.bundlerepository.BundleRepositoryConsumer;
import com.github.tux2323.provisioning.bundlerepository.RepositoryBundle;

public class DefaultBundleRepositoryConsumer implements BundleRepositoryConsumer, BundleContextAware {

	private static final Logger logger = LoggerFactory.getLogger(DefaultBundleRepositoryConsumer.class);

	private BundleContext bundleContext;
	
	public void bundleIsAvailable(RepositoryBundle bundle) {
		logger.info("Bundle " + bundle.getSymbolicName() + " is available!");
		installBundle(bundle);
	}

	private void installBundle(RepositoryBundle bundle) {
		try {
			logger.info("Install bundle : " + bundle.getSymbolicName() + " from location : " + bundle.getLocation());
			Bundle installedBundle = bundleContext.installBundle(bundle.getLocation());
			installedBundle.start();
			logger.info("Bundle with name : " + bundle.getSymbolicName() + " successful installed");
		} catch (BundleException e) {
			logger.error("Bundle not successful installed", e);
			throw new RuntimeException(e);
		}
	}

	public void bundleUpdateIsAvailable(RepositoryBundle updateBundle) {
		logger.info("Bundle update for bundle with name: " + updateBundle.getSymbolicName() + " is available ");
		Bundle bundle = findBundle(updateBundle.getSymbolicName());
		uninstallBundle(bundle);
		installBundle(updateBundle);
	}

	private void uninstallBundle(Bundle bundle) {
		if(bundle != null){
			logger.info("Uninstall Bundle with name : " + bundle.getSymbolicName());
			try {
				bundle.uninstall();
				logger.info("Uninstall bundle with name : " + bundle.getSymbolicName() + "sucessful");
			} catch (BundleException e) {
				logger.error("Uninstall bundle with name : " + bundle.getSymbolicName() + " was NOT successful!");
			}
		}
	}
	
	private Bundle findBundle(String name){
		Bundle[] bundles = bundleContext.getBundles();
		for (Bundle bundle : bundles) {
			String symbolicName = bundle.getSymbolicName();
			if(symbolicName.equals(name)){
				return bundle;
			}
		}
		return null;
	}

	public String[] getRequiredBundels() {
		return new String[]{"com.github.tux2323.osgi.smsservice"};
	}

	@Override
	public void setBundleContext(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

}
