package com.emc.ecs.connector.cloudfoundry;

import org.springframework.cloud.service.BaseServiceInfo;

public class S3ServiceInfo extends BaseServiceInfo {
    private String awsAccessKey;
    private String awsSecretKey;
    private String bucket;
	private String endpoint;
	private String baseUrl;

	public S3ServiceInfo(String id, String awsAccessKey, String awsSecretKey,
			String bucket, String endpoint, String baseUrl) {
        super(id);
        this.awsAccessKey = awsAccessKey;
        this.awsSecretKey = awsSecretKey;
        this.bucket = bucket;
        this.endpoint = endpoint;
        this.baseUrl = baseUrl;
    }

    @ServiceProperty
    public String getAwsAccessKey() {
        return awsAccessKey;
    }

    @ServiceProperty
    public String getAwsSecretKey() {
        return awsSecretKey;
    }

    @ServiceProperty
    public String getBucket() {
        return bucket;
    }

    @ServiceProperty
	public String getEndpoint() {
		return endpoint;
	}

    @ServiceProperty
	public String getBaseUrl() {
		return baseUrl;
	}
}
