package com.archiving.archiving.system.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.archiving.archiving.system.exception.ResourceNotFounException;
import com.archiving.archiving.system.model.Customer;
import com.archiving.archiving.system.model.File;
import com.archiving.archiving.system.repository.FileRepository;

@Service
public class FileServiceImpl implements FileService{

	@Autowired
	private FileRepository fileRepository;
	@Autowired
	private CustomerService customerService;
	@Override
	public File store(Long customerId,MultipartFile multipartFile) throws IOException {
		Customer customer = customerService.getCustomerById(customerId);
		File file = new File();
		file.setCustomer(customer);
		file.setName(multipartFile.getOriginalFilename());
		file.setType(multipartFile.getContentType());
		file.setData(multipartFile.getBytes());
		
		return fileRepository.save(file);
	}
	@Override
	public File getFileById(Long id) {
		Optional<File> optionalFile = fileRepository.findById(id);
		if(optionalFile.isPresent())
			return optionalFile.get();
		
		return null;
	}
	@Override
	public List<File> getAllFile(){
		return fileRepository.findAll();
	}
	@Override
	public void updateFile(Long id, MultipartFile multipartFile) {
		File file = fileRepository.findById(id).orElseThrow(()-> new ResourceNotFounException("File not found with id"));
		try {
			file.setData(multipartFile.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		file.setName(multipartFile.getOriginalFilename());
		file.setType(multipartFile.getContentType());
		fileRepository.save(file);	
	}
	@Override
	public void deleteFile(Long id) {
		File file = fileRepository.findById(id).orElseThrow(()-> new ResourceNotFounException("File not found with id"));
		fileRepository.delete(file);
	}
	
}