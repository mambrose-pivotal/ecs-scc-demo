package com.emc.ecs.connector.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.service.AbstractServiceConnectorCreator;
import org.springframework.cloud.service.ServiceConnectorConfig;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.emc.ecs.connector.cloudfoundry.S3ServiceInfo;

public class S3ServiceConnectorCreator
		extends AbstractServiceConnectorCreator<S3Connector, S3ServiceInfo> {
	Log log = LogFactory.getLog(S3ServiceConnectorCreator.class);

	@Override
	public S3Connector create(S3ServiceInfo serviceInfo,
			ServiceConnectorConfig serviceConnectorConfig) {
		S3ClientOptions options = new S3ClientOptions();
		options.setPathStyleAccess(true);
		AWSCredentials awsCredentials = new BasicAWSCredentials(
				serviceInfo.getAwsAccessKey(), serviceInfo.getAwsSecretKey());
		AmazonS3 amazonS3 = new AmazonS3Client(awsCredentials);
		amazonS3.setEndpoint(serviceInfo.getEndpoint());
		amazonS3.setS3ClientOptions(options);
		log.info("Using S3 Bucket: " + serviceInfo.getBucket());
		return new S3Connector(amazonS3, serviceInfo.getBucket(),
				serviceInfo.getEndpoint(), serviceInfo.getBaseUrl());
	}
}