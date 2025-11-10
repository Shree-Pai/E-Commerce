package com.scem.ecommerce.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.scem.ecommerce.dao.ProductRepository;
import com.scem.ecommerce.entity.Product;
import com.scem.ecommerce.exception.ProductNotFoundException;
import com.scem.ecommerce.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
public class ProductServiceImpl  implements ProductService{
	
	private ProductRepository productRepo;
	
	
	
	public ProductServiceImpl(ProductRepository productRepo) {
		this.productRepo = productRepo;
	}

	@Override
	public Product addProduct(Product product) {
		
		return productRepo.save(product);
		
	}

	@Override
	public Optional<Product> getProductById(Long id) {
		
		return  productRepo.findById(id);
	}

	@Override
	public List<Product> getAllProducts() {
		
		return productRepo.findAll();
	}

	@Override
	public Product updateProduct(Long id, Product product) {
	    Product existingProduct = productRepo.findById(id)
	            .orElseThrow(() -> new ProductNotFoundException("Product not found"));

	    existingProduct.setName(product.getName());
	    existingProduct.setImageURL(product.getImageURL());
	    existingProduct.setDescription(product.getDescription());
	    existingProduct.setPrice(product.getPrice());
	    existingProduct.setStock(product.getStock());
	    existingProduct.setCategory(product.getCategory());
	    existingProduct.setRating(product.getRating());

	    return productRepo.save(existingProduct);
	}


	@Override
	public void deleteProduct(Long id) {
		
		productRepo.deleteById(id);
		
		
	}
	@Override
	public Page<Product> getAllProducts(Pageable pageable) {
	    return productRepo.findAll(pageable);
	}


}
