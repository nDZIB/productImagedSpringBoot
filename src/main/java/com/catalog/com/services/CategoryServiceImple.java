package com.catalog.com.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catalog.com.dto.CategoryDTO;
import com.catalog.com.models.Category;
import com.catalog.com.repositories.CategoryRepository;
@Service
public class CategoryServiceImple implements CategoryInterface {
	
	
    private CategoryRepository categoryrepos;
    
    @Autowired
    public CategoryServiceImple(CategoryRepository categoryrepos) {
		super();
		this.categoryrepos = categoryrepos;
	}


	@Override
    public List<CategoryDTO>getallcategroy() {
        Iterable<Category> categorys = categoryrepos.findAll();
        List<CategoryDTO> categorydtos = new ArrayList<>();
        for(Category category : categorys) {
            CategoryDTO categorytdo = new CategoryDTO();
            categorytdo.setId(category.getId());
            
            categorytdo.setName(category.getName());
            
            categorydtos.add(categorytdo);
        }
        return categorydtos;
    }
	@Override
	public CategoryDTO getparticularcategory(int categoryId) {
		Optional<Category> category= categoryrepos.findById(categoryId);
		CategoryDTO categorydto=new CategoryDTO();
		categorydto.setId(category.get().getId());
		categorydto.setName(category.get().getName());
		
		return categorydto;
		
	}
	@Override
	public void deletecategory(int categoryId) {
		categoryrepos.deleteById(categoryId);
		
	}
	@Override
	public void creatcategory(CategoryDTO categorydto) {
		Category category= new Category();
		category.setName(categorydto.getName());
		categoryrepos.save(category);
		
	}
	@Override
	public void updatecategory( int categoryId, CategoryDTO categorydto){
		
		Category category= categoryrepos.findById(categoryId).get();
		category.setName(categorydto.getName());
		Category categoryupdate=categoryrepos.save(category);
		
//		CategoryDTO categorydto=new CategoryDTO();
//		categorydto.setId(category.get().getId());
//		categorydto.setName(category.get().getName());
//		
	}


}
