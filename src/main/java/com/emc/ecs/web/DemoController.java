package com.emc.ecs.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.emc.ecs.config.S3Connector;
import com.emc.ecs.data.DemoFile;
import com.emc.ecs.data.DemoRepository;

@Controller
public class DemoController {
	
	Log log = LogFactory.getLog(DemoController.class);

	@Autowired
	DemoRepository repository;
	
	@Autowired
	S3Connector s3;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		Iterable<DemoFile> images = repository.findAll();
		model.addAttribute("images", images);
		
		return "index";
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteFile(@PathVariable String id) {
		DemoFile demoFile = repository.findOne(id);
		repository.delete(demoFile);
		log.info(demoFile.getId() + " deleted from DB.");
		s3.getClient().deleteObject(s3.getBucket(), demoFile.getObjectKey());
		log.info(demoFile.getObjectKey() + " deleted from S3 bucket.");
		return "redirect:/";
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String handleFileUpload(@RequestParam("file") MultipartFile file) {
		String id = UUID.randomUUID().toString();
		File uploadedFile = new File(file.getOriginalFilename());
		
		try {
			byte[] bytes = file.getBytes();
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
			stream.write(bytes);
			stream.close();
		} catch (IOException e) {
			throw new RuntimeException("Failed to upload file!", e);
		}
		String fileName = file.getOriginalFilename();
		String key = id + "/" + fileName;
		PutObjectRequest putObjectRequest = new PutObjectRequest(s3.getBucket(), key, uploadedFile);
		putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
		s3.getClient().putObject(putObjectRequest);
		String url = Arrays
				.asList("http://s3.amazonws.com", s3.getBucket(), id, fileName).stream()
				.collect(Collectors.joining("/"));
		DemoFile demoFile = new DemoFile(id, key, url, uploadedFile);
		log.info(demoFile.getObjectKey() + " put to S3.");
		repository.save(demoFile);
		log.info(demoFile.getObjectKey() + " record saved to MySQL.");
		uploadedFile.delete();
		log.info(demoFile.getFile().getAbsolutePath() + " is deleted.");
		return "redirect:/";
	}
}