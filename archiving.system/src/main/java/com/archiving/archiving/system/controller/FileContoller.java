package com.archiving.archiving.system.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.archiving.archiving.system.model.File;
import com.archiving.archiving.system.service.FileService;

@RestController
@RequestMapping("/file")
public class FileContoller {

    @Autowired
	private FileService fileService;
	
    @GetMapping("/{id}")
    public File getFileById(@PathVariable Long id) {
    	return fileService.getFileById(id);
    }
    @GetMapping
    public List<File> getAllFile(){
    	return fileService.getAllFile();
    }
    @PostMapping("uploadFile/{customerId}")  
    public File uploadFile( @PathVariable Long customerId,@RequestParam("file") MultipartFile multipartFile) throws IOException {
    	return fileService.store(customerId,multipartFile);
    }
    @PostMapping("/uploadMultipleFiles/{customerId}")
    public List<File> uploadMultipleFiles(@PathVariable Long customerId,@RequestParam("file") MultipartFile[] multipartFiles){
    	return Arrays.asList(multipartFiles).stream().map(multipartFile->{
			try {
				return uploadFile(customerId, multipartFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());
    }
    
    
    @PutMapping("/{id}")
    public ResponseEntity<String> updateFile(@PathVariable Long id, @RequestParam("file") MultipartFile multipartFile){
    	fileService.updateFile(id,multipartFile);
    	return new ResponseEntity<>("File updated successfully",HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable Long id){
    	fileService.deleteFile(id);
    	return new ResponseEntity<>("File deleted successfully",HttpStatus.OK);
    }
    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
        // Load file from database
        File dbFile = fileService.getFileById(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }
}
