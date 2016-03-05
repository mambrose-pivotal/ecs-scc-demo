package com.emc.ecs.web;

import java.io.IOException;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jclouds.blobstore.domain.Blob;
import org.jclouds.blobstore.domain.BlobAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.emc.ecs.data.DemoFile;
import com.emc.ecs.data.DemoRepository;
import com.orange.spring.cloud.connector.s3.core.jcloudswrappers.SpringCloudBlobStore;

@Controller
public class DemoController {
	
	private Log log = LogFactory.getLog(DemoController.class);

	@Autowired
	private DemoRepository repository;
	
	@Autowired
	private SpringCloudBlobStore blobStore;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("message", "Hello Boot!");
		
		Iterable<DemoFile> images = repository.findAll();
		model.addAttribute("images", images);
		
		return "index";
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteFile(@PathVariable String id) {
		DemoFile demoFile = repository.findOne(id);
		repository.delete(demoFile);
		log.info(demoFile.getId() + " deleted from DB.");
		blobStore.removeBlob(demoFile.getObjectKey());
		log.info(demoFile.getObjectKey() + " deleted from S3 bucket.");
		return "redirect:/";
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
		String id = UUID.randomUUID().toString();
		String fileName = file.getOriginalFilename();
		String key = id + "/" + fileName;
		Blob blob = blobStore.blobBuilder(key)
						.payload(file.getBytes())
						.contentDisposition(file.getOriginalFilename())
						.contentLength(file.getSize())
						.build();
		blobStore.putBlob(blob);
		blobStore.setBlobAccess(key, BlobAccess.PUBLIC_READ);
		String url = blobStore.blobMetadata(key).getPublicUri().toString();
		DemoFile demoFile = new DemoFile(id, key, url);
		log.info(demoFile.getObjectKey() + " put to S3. URL: " + url);
		repository.save(demoFile);
		log.info(demoFile.getObjectKey() + " record saved to MySQL.");
		return "redirect:/";
	}
}