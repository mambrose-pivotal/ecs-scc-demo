package com.emc.ecs.config;

import com.amazonaws.services.s3.AmazonS3;

public class S3Connector {
	private AmazonS3 client;
	private String bucket;
	
	public S3Connector(AmazonS3 client, String bucket) {
		this.client = client;
		this.bucket = bucket;
	}
	
	public AmazonS3 getClient() {
		return client;
	}

	public String getBucket() {
		return bucket;
	}
}