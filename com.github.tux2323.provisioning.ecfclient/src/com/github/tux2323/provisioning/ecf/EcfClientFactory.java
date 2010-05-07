package com.github.tux2323.provisioning.ecf;

import org.eclipse.ecf.core.ContainerCreateException;
import org.eclipse.ecf.core.IContainerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EcfClientFactory {
	
	private static final Logger logger = LoggerFactory.getLogger(EcfClientFactory.class);
	
	private IContainerFactory containerFactory;

	public void setContainerFactory(IContainerFactory containerFactory) {
		this.containerFactory = containerFactory;
	}

	public IContainerFactory getContainerFactory() {
		return containerFactory;
	}
	
	public void start()  {
		try {
			containerFactory.createContainer("ecf.generic.client");
		} catch (ContainerCreateException exp) {
			logger.error("Could not create the ECF client container", exp);
		}
	}
	
}
