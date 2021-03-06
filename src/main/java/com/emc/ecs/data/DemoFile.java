package com.emc.ecs.data;

import java.io.File;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class DemoFile {
	
	@Id
	private String id;
	@Transient
	private File file;
	private String objectKey;
	private String url;
	
	public DemoFile() {
	}
	
	public DemoFile(String id, String objectKey, String url) {
		this.id = id;
		this.objectKey = objectKey;
		this.url = url;
	}

	public String getId() {
		return id;
	}
	
	public String getObjectKey() {
		return objectKey;
	}
	
	public String getUrl() {
		return url;
	}
	
}