package com.emc.ecs.connector.spring;

import com.amazonaws.services.s3.AmazonS3;

public class S3Connector {
	private AmazonS3 client;
	private String bucket;
	private String endpoint;
	private String baseUrl;
	
	public S3Connector(AmazonS3 client, String bucket, String endpoint, String baseUrl) {
		this.client = client;
		this.bucket = bucket;
		this.endpoint = endpoint;
		this.baseUrl = baseUrl;
	}
	
	public AmazonS3 getClient() {
		return client;
	}

	public String getBucket() {
		return bucket;
	}
	
	public String getEndpoint() {
		return endpoint;
	}
	
	public String getBaseUrl() {
		return baseUrl;
	}
}
