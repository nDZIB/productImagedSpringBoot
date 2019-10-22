package com.catalog.com.imagemanipulation.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.catalog.com.imagemanipulation.properties.ImageUploadProperties;

@Service
public class ImageManipulationService {

	private final Path fileStorageLocation;
	private final ImageUploadProperties fileStorageProperties;

	@Autowired
	public ImageManipulationService(ImageUploadProperties fileStorageProperties) {
		this.fileStorageProperties = fileStorageProperties;
		
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
				.toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String storeFile(MultipartFile file, int id) {
		// Normalize file name
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		String fName = fileStorageProperties.getNamescheme()+id+"."+fileName
				.substring(fileName.lastIndexOf(".")+1);

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				System.out.println("file contains ...");
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fName);
			
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			//return targetLocation.toUri().toString();
			return fName;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				System.out.println("file not found");
			}
		} catch (MalformedURLException ex) {
			System.out.println("Malformed url exception");
		}
		return null;
	}
	
	public ResponseEntity < Resource > downloadFile(String fileName, 
    		HttpServletRequest request) {
        // Load file as Resource
        Resource resource = this.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Can't determine file type");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
    }

	public String getDownloadUrlFor(String image) {
		return  ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/imaged/api/products/downloadFile/")
				.path(image).toUriString();
	}

	public void delete(String image) {
		Path path = fileStorageLocation.resolve(image); //obtain the absolute path of the file	
		try {
			Files.deleteIfExists(path);
		} catch (IOException e) {
			System.out.println("Could not delete the file: it was not found");
			e.printStackTrace();
		}
	}
	
}
