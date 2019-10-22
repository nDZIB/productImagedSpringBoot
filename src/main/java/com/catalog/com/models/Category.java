package com.catalog.com.models;
import java.util.List;

import javax.persistence.*;
@Entity
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	
//	@OneToMany(mappedBy="category")
//	private List<Product> product;
    public Category() {
		}
	public Category(int id, String name) {
		super();
		this.id = id;
		this.name = name;
		
	}
	
//	public List<Product> getProduct() {
//		return product;
//	}
//	public void setProduct(List<Product> product) {
//		this.product = product;
//	}
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
	
	
	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + "]";
	}
	
	

}
