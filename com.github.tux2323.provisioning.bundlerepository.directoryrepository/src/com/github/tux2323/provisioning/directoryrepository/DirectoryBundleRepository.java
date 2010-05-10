package com.github.tux2323.provisioning.directoryrepository;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.github.tux2323.provisioning.bundlerepository.BundleRepository;
import com.github.tux2323.provisioning.bundlerepository.RepositoryBundle;

public class DirectoryBundleRepository extends TimerTask implements BundleRepository {

	private static final Logger logger = LoggerFactory
			.getLogger(DirectoryBundleRepository.class);

	private File bundleDirectory;

	private ConsumerManager consumerManager;

	private Map<String, RepositoryBundle> bundles = new HashMap<String, RepositoryBundle>();

	public DirectoryBundleRepository() {
		logger.debug("Create new directory bundle repository");
	}
	
	public void start(){
		run();
	}

	public void run() {
		logger.debug("Look for new bundles in the directory");
		Assert.notNull(bundleDirectory);
		List<RepositoryBundle> newBundles = new ArrayList<RepositoryBundle>();
		List<RepositoryBundle> updateBundles = new ArrayList<RepositoryBundle>();
		File[] listFiles = bundleDirectory.listFiles();
		for (File file : listFiles) {
			if (isBundle(file)) {
				String symbolicName = extractSymbolicName(file);
				if (!bundles.containsKey(symbolicName)) {
					RepositoryBundle bundle = createRepositoryBundle(file);
					bundles.put(symbolicName, bundle);
					newBundles.add(bundle);
				} else {
					RepositoryBundle actualBundle = bundles.get(symbolicName);
					String strVersion = extractVersion(file).replace(".", "");
					Long version = Long.valueOf(strVersion);
					String strActualVersion = actualBundle.getVersion().replace(".", "");
					Long actualVersion = Long.valueOf(strActualVersion);
					if(version > actualVersion){
						RepositoryBundle bundle = createRepositoryBundle(file);
						bundles.put(symbolicName, bundle);
						updateBundles.add(bundle);
					}
				}
			}
		}
		logger.debug("Advise listeners that a new bundle is available or updates for a bundle are available");
		Assert.notNull(getConsumerManager());
		for (RepositoryBundle newBundle : newBundles) {
			getConsumerManager().fireNewBundleEvent(newBundle);
		}
		for (RepositoryBundle updateBundle : updateBundles) {
			getConsumerManager().fireBundleUpdateEvent(updateBundle);
		}
	}

	private RepositoryBundle createRepositoryBundle(File file) {
		RepositoryBundle repositoryBundle = new RepositoryBundle();
		repositoryBundle.setLocation("file://" + file.getAbsolutePath());
		repositoryBundle.setSymbolicName(extractSymbolicName(file));
		repositoryBundle.setVersion(extractVersion(file));
		return repositoryBundle;
	}

	private String extractVersion(File file) {
		String version = file.getName()
				.substring(file.getName().indexOf('_') + 1,
						file.getName().indexOf(".jar"));
		return version;
	}

	private String extractSymbolicName(File file) {
		String fileName = file.getName();
		String symbolicName = fileName.substring(0, fileName.indexOf('_'));
		return symbolicName;
	}

	private boolean isBundle(File file) {
		return !file.isDirectory() && file.getName().endsWith("jar");
	}

	public void setBundleDirectory(File bundleDirectory) {
		this.bundleDirectory = bundleDirectory;
	}

	public File getBundleDirectory() {
		return bundleDirectory;
	}

	public String getBundleLocation(String bundleSymbolicName) {
		RepositoryBundle bundle = bundles.get(bundleSymbolicName);
		if(bundle != null)
			return bundle.getLocation();
		return null;
	}
	
	public RepositoryBundle getRepositoryBundle(String symbolicName){
		return bundles.get(symbolicName);
	}

	public void setConsumerManager(ConsumerManager consumerManager) {
		this.consumerManager = consumerManager;
	}

	public ConsumerManager getConsumerManager() {
		return consumerManager;
	}

}
