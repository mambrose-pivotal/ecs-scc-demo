package com.emc.ecs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.config.java.ServiceScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.orange.spring.cloud.connector.s3.core.jcloudswrappers.SpringCloudBlobStore;
import com.orange.spring.cloud.connector.s3.core.jcloudswrappers.SpringCloudBlobStoreContext;

@Configuration
@ServiceScan // Automatically creates dataSource bean based on bindings
@Profile("cloud")
public class CloudConfig extends AbstractCloudConfig {
	
	@Autowired
	// Requires ServiceScan, but doesn't auto-generate blobStore bean
	private SpringCloudBlobStoreContext springCloudBlobStoreContext;
	
	@Bean
	public SpringCloudBlobStore blobStore() {
		SpringCloudBlobStore blobStore = this.springCloudBlobStoreContext.getSpringCloudBlobStore();
		// Set bucket to public-read is erroring out ATM.
		// Manually set bucket group ACL to READ.
		// blobStore.setContainerAccess(ContainerAccess.PUBLIC_READ);
		return blobStore;
    }
}