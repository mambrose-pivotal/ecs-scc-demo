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
		super();
	}
	
	public DemoFile(String id, String objectKey, String url, File file) {
		this.id = id;
		this.objectKey = objectKey;
		this.url = url;
		this.file = file;
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
	
	public File getFile() {
		return file;
	}

}