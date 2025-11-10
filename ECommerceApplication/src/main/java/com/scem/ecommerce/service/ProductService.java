package com.scem.ecommerce.service;

import java.util.Optional;
import java.util.List;
import com.scem.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ProductService {
	
	Product addProduct(Product product);
    Optional<Product> getProductById(Long id);
    List<Product> getAllProducts();
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
    Page<Product> getAllProducts(Pageable pageable);

}
