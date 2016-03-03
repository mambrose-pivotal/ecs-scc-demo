package com.emc.ecs.config;

import javax.sql.DataSource;

import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.service.PooledServiceConnectorConfig;
import org.springframework.cloud.service.relational.DataSourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.emc.ecs.connector.spring.S3Connector;

@Configuration
@Profile("cloud")
public class CloudConfig extends AbstractCloudConfig {

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
        return connectionFactory().service(S3Connector.class);
    }

}