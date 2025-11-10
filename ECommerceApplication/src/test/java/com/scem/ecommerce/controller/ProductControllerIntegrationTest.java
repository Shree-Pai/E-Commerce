package com.scem.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scem.ecommerce.entity.Product;
import com.scem.ecommerce.security.JwtAuthenticationFilter;
import com.scem.ecommerce.service.ProductService;
import com.scem.ecommerce.util.JwtTokenUtil;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("removal")
@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    private ProductService productService;

    private Product product;
    private ObjectMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new ObjectMapper();
        product = new Product();
        product.setProductId(1L);
        product.setName("Test Product");
        product.setPrice(99.99);
    }

    @Test
    void testGetAllProducts_withPagination() throws Exception {
        Page<Product> page = new PageImpl<>(Collections.singletonList(product));
        Mockito.when(productService.getAllProducts(any(PageRequest.class))).thenReturn(page);

        mockMvc.perform(get("/api/products")
                .param("page", "0")
                .param("size", "5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // Because Page<Product> JSON looks like { "content": [ ... ], "totalElements": ... }
                .andExpect(jsonPath("$.content[0].name").value("Test Product"));
    }

    @Test
    void testAddProduct() throws Exception {
        Mockito.when(productService.addProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product"));
    }
}
