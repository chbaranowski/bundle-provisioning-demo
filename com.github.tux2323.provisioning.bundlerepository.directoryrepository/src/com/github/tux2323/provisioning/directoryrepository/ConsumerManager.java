package com.github.tux2323.provisioning.directoryrepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.tux2323.provisioning.bundlerepository.BundleRepositoryConsumer;
import com.github.tux2323.provisioning.bundlerepository.RepositoryBundle;

public class ConsumerManager {

	private static final Logger logger = LoggerFactory.getLogger(ConsumerManager.class);
	
	private DirectoryBundleRepository directoryBundleRepository;
	
	private List<BundleRepositoryConsumer> consumers = new ArrayList<BundleRepositoryConsumer>();
	
	public void bind(BundleRepositoryConsumer consumer, Map params) {
		logger.info("Bind consumer service");
		consumers.add(consumer);
		String[] requiredBundels = consumer.getRequiredBundels();
		for (String symbolicName : requiredBundels) {
			RepositoryBundle repositoryBundle = directoryBundleRepository.getRepositoryBundle(symbolicName);
			consumer.bundleIsAvailable(repositoryBundle);
		}
	}
	
	public void unbind(BundleRepositoryConsumer consumer, Map params) {
		logger.info("Unbind consumer service");
		consumers.remove(consumers);
	}

	public void setDirectoryBundleRepository(DirectoryBundleRepository directoryBundleRepository) {
		this.directoryBundleRepository = directoryBundleRepository;
	}

	public DirectoryBundleRepository getDirectoryBundleRepository() {
		return directoryBundleRepository;
	}
	
	public void fireBundleUpdateEvent(RepositoryBundle bundle) {
		logger.info("Fire Bundle Update Event : " + bundle.getSymbolicName() + " Version: " + bundle.getVersion());
		for (BundleRepositoryConsumer consumer : consumers) {
			String[] requiredBundels = consumer.getRequiredBundels();
			for (String symbolicName : requiredBundels) {
				consumer.bundleUpdateIsAvailable(bundle);
			}
		}
	}
	
	public void fireNewBundleEvent(RepositoryBundle bundle) {
		logger.info("Fire New Bundle Event : " + bundle.getSymbolicName() + " Version: " + bundle.getVersion());
		for (BundleRepositoryConsumer consumer : consumers) {
			String[] requiredBundels = consumer.getRequiredBundels();
			for (String symbolicName : requiredBundels) {
				consumer.bundleIsAvailable(bundle);
			}
		}
	}
	
}
