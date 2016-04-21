package com.emc.ecs.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.service.PooledServiceConnectorConfig;
import org.springframework.cloud.service.relational.DataSourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;

@Configuration
@Profile("cloud")
public class CloudConfig extends AbstractCloudConfig {
	
	@Autowired
	private S3Configuration s3Configuration;

	@Bean
	public DataSource dataSource() {
        // Default pool size to 4 connections to support ClearDB Spark (free)
    	PooledServiceConnectorConfig.PoolConfig poolConfig =
                new PooledServiceConnectorConfig.PoolConfig(4, 200);
        DataSourceConfig config = new DataSourceConfig(poolConfig, new DataSourceConfig.ConnectionConfig(""));
		return connectionFactory().dataSource(config);
	}
	
	@Bean
    public S3Connector s3() {
		S3ClientOptions options = new S3ClientOptions();
		options.setPathStyleAccess(true);
		AWSCredentials awsCredentials = new BasicAWSCredentials(
				s3Configuration.getAwsAccessKey(), s3Configuration.getAwsSecretKey());
		AmazonS3 amazonS3 = new AmazonS3Client(awsCredentials);
		amazonS3.setS3ClientOptions(options);
		return new S3Connector(amazonS3, s3Configuration.getBucket());
    }

}