package com.catalog.com.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catalog.com.dto.ProductDTO;
import com.catalog.com.exceptions.product.ProductNotDeletedException;
import com.catalog.com.exceptions.product.ProductNotFoundException;
import com.catalog.com.exceptions.product.UncaughtProductException;
import com.catalog.com.imagemanipulation.properties.ImageUploadProperties;
import com.catalog.com.imagemanipulation.service.ImageManipulationService;
import com.catalog.com.models.Product;
import com.catalog.com.repositories.CategoryRepository;
import com.catalog.com.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	private ProductRepository repository;
	private CategoryRepository catRepo;
	private ImageManipulationService images;
	private ImageUploadProperties properties;

	@Autowired
	public ProductServiceImpl(ProductRepository repository, CategoryRepository catRepo,
			ImageManipulationService images, ImageUploadProperties properties) {
		this.repository = repository;
		this.catRepo = catRepo;
		this.images = images;
		this.properties = properties;
	}

	@Override
	public ProductDTO addProduct(Product product, int categoryid) {

		product.setCategory(catRepo.findById(categoryid).get());
		return repository.save(product).toD();
	}

	@Override
	public ProductDTO editProduct(Product product, int productid, int categoryid) {
		// verify that the given product exists
		if (!repository.existsById(productid))
			throw new ProductNotFoundException("Product id: " + productid); // if not exists, throw new product not
																			// found exception

		// verify that the given category exists
		if (!catRepo.existsById(categoryid))
			throw new UncaughtProductException("Category not found, id: " + categoryid); // if not exists, throw
																							// category not found
																							// exception

		product.setCategory(catRepo.findById(categoryid).get()); // if all is well, set the productid and category id
		product.setId(productid);

		return repository.save(product).toD(); // effect the modification
	}

	@Override
	public void deleteProduct(int productid) {

		// verify that the product exists
		if (!repository.existsById(productid))
			throw new ProductNotFoundException("Product id: " + productid); // throw a product not found exception

		String productImageUrl = repository.findById(productid).get().getImage();
		repository.deleteById(productid); // attempt to delete the product

		if (repository.existsById(productid)) // verify whether the product was deleted
			throw new ProductNotDeletedException("Product id:" + productid); // throw a product not deleted exception if
																				// it was not
		// the product was successfully deleted, then delete its image
		images.delete(this.getImage(productImageUrl, productid));
	}

	@Override
	public List<ProductDTO> retrieveAllProducts() {
		List<ProductDTO> products = new ArrayList<ProductDTO>();

		// for each retrieved product, add to a listof products to be returned
		repository.findAll().forEach(product -> {
			products.add(product.toD());
		});
		return products;
	}

	@Override
	public ProductDTO retrieveProduct(int productid) {

		Optional<Product> result = repository.findById(productid);

		return result.get().toD();
	}

	// helper method
	// filename generator
	public String getImage(String imageUrl, int productid) {

		String fileName = properties.getNamescheme() + productid;
		String fileExtension = imageUrl.substring(imageUrl.lastIndexOf(".") + 1);

		return fileName.concat(".").concat(fileExtension);
	}
}
