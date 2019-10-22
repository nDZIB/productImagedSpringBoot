package com.catalog.com.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.catalog.com.models.Product;
import com.catalog.com.models.ProductWithImage;
@Repository
public interface ProductWithImageRepository extends JpaRepository<ProductWithImage,Integer>{

}
