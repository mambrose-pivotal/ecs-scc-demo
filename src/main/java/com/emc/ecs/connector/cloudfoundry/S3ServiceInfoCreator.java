package com.emc.ecs.connector.cloudfoundry;

import java.util.Map;

import org.springframework.cloud.cloudfoundry.CloudFoundryServiceInfoCreator;
import org.springframework.cloud.cloudfoundry.Tags;

public class S3ServiceInfoCreator
		extends CloudFoundryServiceInfoCreator<S3ServiceInfo> {
    public S3ServiceInfoCreator() {
        super(new Tags("s3"));
	}

	@Override
	public S3ServiceInfo createServiceInfo(Map<String, Object> serviceData) {
		@SuppressWarnings("unchecked")
		Map<String, Object> credentials = (Map<String, Object>) serviceData
				.get("credentials");

		String id = (String) serviceData.get("name");
		String awsAccessKey = (String) credentials.get("accessKey");
		String awsSecretKey = (String) credentials.get("secretKey");
		String bucket = (String) credentials.get("bucket");
		
		String endpoint = credentials.containsKey("endpoint") ?
				(String) credentials.get("endpoint") :
				"https://s3.amazonaws.com";
				
		String baseUrl = credentials.containsKey("baseUrl") ?
				(String) credentials.get("baseUrl") :
				endpoint;
		return new S3ServiceInfo(id, awsAccessKey, awsSecretKey, bucket,
				endpoint, baseUrl);
	}
}