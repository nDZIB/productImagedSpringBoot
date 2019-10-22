package com.catalog.com.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.catalog.com.dto.CategoryDTO;
import com.catalog.com.services.CategoryServiceImple;

import java.util.List;
@RestController
@RequestMapping("/api")
@CrossOrigin
public class CategoryController {
	
	
	private CategoryServiceImple categoryservice;
	@Autowired
	public CategoryController(CategoryServiceImple categoryservice) {
		super();
		this.categoryservice = categoryservice;
	}
//	to get all the category
	
	@GetMapping("/category")
    public ResponseEntity<List<CategoryDTO>> getallcategory(){
        return ResponseEntity.ok(categoryservice.getallcategroy());
    }
//	to get a particular category
	@GetMapping("/category/{categoryId}")
    public ResponseEntity<CategoryDTO> getparticularcategory(@PathVariable int categoryId){
        return ResponseEntity.ok(categoryservice.getparticularcategory(categoryId));
    }
//	to delete a particular category
	@DeleteMapping("/category/{categoryId}")
    public void deletecategory(@PathVariable int categoryId){
        categoryservice.deletecategory(categoryId);
    }
//	to create a category
	@PostMapping("/category")
    public void creatcategory(@RequestBody CategoryDTO categorydto){
        categoryservice.creatcategory(categorydto);
    }
//	to update a particular categrory
	@PutMapping("/category/{categoryId}")
    public void updatecategory(@PathVariable int categoryId,@RequestBody CategoryDTO categorydto){
        categoryservice.updatecategory(categoryId,categorydto);
        
    }

}
