package com.catalog.com.imagemanipulation;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.catalog.com.dto.ProductDTO;
import com.catalog.com.imagemanipulation.service.ImageManipulationService;
import com.catalog.com.models.Product;
import com.catalog.com.services.ProductServiceImpl;

@RestController
@CrossOrigin
@RequestMapping("/imaged/api/products")
public class ProductControllerImaged {

	@Autowired
	private ImageManipulationService fileStorageService;

	@Autowired
	private ProductServiceImpl service;

	@GetMapping("/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> loadImage(@PathVariable String fileName, HttpServletRequest request) {
		return fileStorageService.downloadFile(fileName, request);
	}
	
	// save a new product
	@PostMapping("/category/{categoryid}")
	public ResponseEntity<ProductDTO> saveProduct(

			@RequestPart MultipartFile file,

			@RequestParam() String name, @RequestParam() long quantity, @RequestParam() long price,
			@PathVariable("categoryid") int categoryid) {

		Product product = new Product();
		product.setName(name);
		product.setQuantity(quantity);
		product.setPrice(price);

		// save the product (without the image)
		ProductDTO result = service.addProduct(product, categoryid);
		// save the corresponding image and retrieve
		String image = fileStorageService.storeFile(file, result.getId());

		String fileDownloadUri = fileStorageService.getDownloadUrlFor(image);

		product.setImage(fileDownloadUri);
		result = service.editProduct(product, product.getId(), categoryid);

		ResponseEntity<ProductDTO> response = new ResponseEntity<ProductDTO>(result, HttpStatus.CREATED);

		return response;
	}

	// modify an existing product
	@PutMapping("/{productid}/category/{categoryid}")
	public ResponseEntity<ProductDTO> editProduct(@RequestBody Product product,
			@PathVariable("productid") int productid, @PathVariable("categoryid") int categoryid) {

		ResponseEntity<ProductDTO> response = new ResponseEntity<ProductDTO>(
				service.editProduct(product, productid, categoryid), HttpStatus.NO_CONTENT);

		return response;
	}

	// delete a product
	@DeleteMapping("/{productid}")
	public ResponseEntity<Object> deleteProduct(@PathVariable("productid") int productid) {
		service.deleteProduct(productid);

		ResponseEntity<Object> response = new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		return response;
	}

	// view all products
	@GetMapping("/")
	public ResponseEntity<List<ProductDTO>> getAllProducts() {
		ResponseEntity<List<ProductDTO>> response = new ResponseEntity<List<ProductDTO>>(service.retrieveAllProducts(),
				HttpStatus.OK);

		return response;
	}

	// view a single product
	@GetMapping("/{productid}")
	public ResponseEntity<ProductDTO> getSingleProduct(@PathVariable("productid") int productid) {
		ResponseEntity<ProductDTO> response = new ResponseEntity<ProductDTO>(service.retrieveProduct(productid),
				HttpStatus.OK);

		return response;
	}

}
