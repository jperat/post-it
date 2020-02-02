package com.jperat.postit.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.jperat.postit.dao.FileDao;
import com.jperat.postit.exception.StorageException;
import com.jperat.postit.exception.StorageFileNotFoundException;
import com.jperat.postit.model.File;

@Service
public class StorageServiceImpl implements StorageService {

	private final Path rootLocation;
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	@Autowired
	public StorageServiceImpl() {
		ClassLoader classLoader = getClass().getClassLoader();
		this.rootLocation = Paths.get(classLoader.getResource(".").getFile()+"../../ressources/uploads/");
	}

	@Autowired
	private FileDao fileDao;

	public void init() {
		try {
			Files.createDirectories(rootLocation);
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}

	public File store(MultipartFile file) {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		File file2 = new File();

		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + filename);
			}
			if (filename.contains("..")) {
				// This is a security check
				throw new StorageException(
						"Cannot store file with relative path outside current directory " + filename);
			}
			String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			do {
				filename = this.randomString()+'.'+extension;
			} while (fileDao.findByName(filename) != null);
			file2.setName(filename);
			file2.setType(file.getContentType());
			file2.setExtension(extension);
			file2.setOriginalName(file.getOriginalFilename());
	        		
			Files.copy(file.getInputStream(), this.rootLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
			fileDao.save(file2);
		} catch (IOException e) {
			throw new StorageException("Failed to store file " + filename, e);
		}
		return file2;
	}

	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation))
					.map(path -> this.rootLocation.relativize(path));
		} catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}
	}
	
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new StorageFileNotFoundException("Could not read file: " + filename);

			}
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}
	
	public boolean deleteUploadFile(String filename) {
		Path path = this.rootLocation.resolve(filename);
		try {
		    Files.delete(path);
		    return true;
		} catch (NoSuchFileException x) {
		    System.err.format("%s: no such" + " file or directory%n", path);
		    return true;
		} catch (DirectoryNotEmptyException x) {
		    System.err.format("%s not empty%n", path);
		    return false;
		} catch (IOException x) {
		    // File permission problems are caught here.
		    System.err.println(x);
		    return false;
		}
	}

	private String randomString() {
		StringBuilder stringBuilder = new StringBuilder(64);
		SecureRandom secureRandom = new SecureRandom();
		for (int i = 0; i < 64; i++)
			stringBuilder.append(AB.charAt(secureRandom.nextInt(AB.length())));
		return stringBuilder.toString();
	}

}
