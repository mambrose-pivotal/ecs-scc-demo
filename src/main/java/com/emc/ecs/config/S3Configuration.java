package com.emc.ecs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "s3")
public class S3Configuration {

    private String awsAccessKey;
    private String awsSecretKey;
	private String bucket;

	public S3Configuration() {
		super();
	}

	public void setAwsAccessKey(String awsAccessKey) {
		this.awsAccessKey = awsAccessKey;
	}

	public S3Configuration(String awsAccessKey, String awsSecretKey, String bucket) {
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

	public void setAwsSecretKey(String awsSecretKey) {
		this.awsSecretKey = awsSecretKey;
	}

	public void setBucket(String bucket) {
		this.bucket = bucket;
	}
}
