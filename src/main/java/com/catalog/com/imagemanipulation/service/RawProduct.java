package com.catalog.com.imagemanipulation.service;

import org.springframework.web.multipart.MultipartFile;

public class RawProduct {

	private int id;
	private MultipartFile file;
	
	public RawProduct() {
		
	}
	
	public RawProduct(int id, MultipartFile file) {
		this.setId(id);
		this.setFile(file);
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
