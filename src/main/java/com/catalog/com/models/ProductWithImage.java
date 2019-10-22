package com.catalog.com.models;
import javax.persistence.*;

import com.catalog.com.dto.ProductDTO;


@Entity
public class ProductWithImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;	
	private String name;
	private long quantity;
	private long price;
	private String image;


	@ManyToOne
	@JoinColumn(nullable = false)
	private Category category; 

	
	public ProductWithImage(int id, String name, long quantity, long price, Category category, String image) {
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.category = category;
		this.setImage(image);
	}


	public ProductWithImage(String name, long quantity, long price,String image, Category category) {
		//this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.category = category;
		this.setImage(image);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}


	public ProductWithImage() {	
	}

	public ProductDTO toDTO() {
		return new ProductDTO(getId(), getName(), getQuantity(), getPrice());
	}
	public ProductDTO toD() {
		return new ProductDTO(getId(), getName(), getQuantity(), getPrice(), getCategory());
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}
	
}
