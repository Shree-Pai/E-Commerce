package com.scem.ecommerce.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.scem.ecommerce.dao.ProductRepository;
import com.scem.ecommerce.entity.Product;
import com.scem.ecommerce.service.impl.ProductServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class) 
class ProductServiceTest {

    @Mock
    private ProductRepository productRepo;

    @InjectMocks
    private ProductServiceImpl productService; 

    private Product product;

    @BeforeEach
    void setup() {
        product = new Product();
        product.setProductId(1L);
        product.setName("Laptop");
        product.setPrice(1200.0);
       // product.setStock(10); 
    }

    @Test
    void testAddProduct() {
        when(productRepo.save(product)).thenReturn(product);

        Product saved = productService.addProduct(product);

        assertNotNull(saved);
        assertEquals("Laptop", saved.getName());
        verify(productRepo, times(1)).save(product);
    }

    @Test
    void testGetProductById() {
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));

        Optional<Product> found = productService.getProductById(1L);

        assertTrue(found.isPresent());
        assertEquals(1200.0, found.get().getPrice());
        verify(productRepo, times(1)).findById(1L);
    }

    @Test
    void testGetAllProducts_ListVersion() {
        List<Product> products = new ArrayList<>();
        products.add(product);
        when(productRepo.findAll()).thenReturn(products);

        List<Product> list = productService.getAllProducts();

        assertEquals(1, list.size());
        assertEquals("Laptop", list.get(0).getName());
        verify(productRepo, times(1)).findAll();
    }

    @Test
    void testGetAllProducts_PaginatedVersion() {
        Page<Product> page = new PageImpl<>(List.of(product));
        when(productRepo.findAll(any(Pageable.class))).thenReturn(page); // ✅ FIXED matcher type

        Page<Product> result = productService.getAllProducts(PageRequest.of(0, 5));

        assertEquals(1, result.getTotalElements());
        assertEquals("Laptop", result.getContent().get(0).getName());
        verify(productRepo, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(productRepo).deleteById(1L);

        productService.deleteProduct(1L);

        verify(productRepo, times(1)).deleteById(1L);
    }
}
