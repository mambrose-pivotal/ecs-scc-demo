package com.emc.ecs.config;

public class S3Configuration {
    private String awsAccessKey;
    private String awsSecretKey;
    private String bucket;

	public S3Configuration(String id, String awsAccessKey, String awsSecretKey, String bucket) {
        super();
        this.awsAccessKey = awsAccessKey;
        this.awsSecretKey = awsSecretKey;
        this.bucket = bucket;
    }

    public String getAwsAccessKey() {
        return awsAccessKey;
    }

    public String getAwsSecretKey() {
        return awsSecretKey;
    }

    public String getBucket() {
        return bucket;
    }

}
