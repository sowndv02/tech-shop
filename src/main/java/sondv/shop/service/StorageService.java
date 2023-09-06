package sondv.shop.service;

import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	void init();

	void delete(String storedFileName);

	Path load(String fileName);

	Resource loadAsResource(String fileName);

	void store(MultipartFile file, String storedFilename);

	String getStorageFilename(MultipartFile file, String id);

}
