package com.emc.ecs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "s3")
public class S3Configuration {

    private String accessKey;
    private String secretKey;
	private String bucket;
	private String endpoint;

	public S3Configuration() {
		super();
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public S3Configuration(String accessKey, String secretKey, String bucket, String endpoint) {
        super();
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.endpoint = endpoint;
        this.bucket = bucket;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getBucket() {
        return bucket;
    }

    public String getEndpoint() {
        return endpoint;
    }

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public void setBucket(String bucket) {
		this.bucket = bucket;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
}
