package com.catalog.com.imagemanipulation.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file")
public class ImageUploadProperties {
    private String uploadDir;
    private String namescheme;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

	public String getNamescheme() {
		return namescheme;
	}

	public void setNamescheme(String namescheme) {
		this.namescheme = namescheme;
	}
}