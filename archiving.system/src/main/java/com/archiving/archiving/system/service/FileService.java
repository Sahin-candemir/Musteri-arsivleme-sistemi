package com.archiving.archiving.system.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.archiving.archiving.system.model.File;

public interface FileService {

    File store(Long customerId,MultipartFile multipartFile) throws IOException;
	
	File getFileById(Long id);
	
	List<File> getAllFile();

	void updateFile(Long id, MultipartFile multipartFile);

	void deleteFile(Long id);
}
