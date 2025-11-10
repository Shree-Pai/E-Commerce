package com.scem.ecommerce.controller;

import com.scem.ecommerce.dto.CartItemRequest;
import com.scem.ecommerce.entity.Cart;
import com.scem.ecommerce.entity.CartItem;
import com.scem.ecommerce.entity.Product;
import com.scem.ecommerce.entity.User;
import com.scem.ecommerce.service.CartService;
import com.scem.ecommerce.util.AuthUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CartControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SuppressWarnings("removal")
	@MockBean
    private CartService cartService;

    @SuppressWarnings("removal")
	@MockBean
    private AuthUtil authUtil;

    private User testUser;
    private Cart testCart;
    private CartItem testCartItem;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUserId(1L);
        testUser.setEmail("user@example.com");
        
        Product product = new Product();
        product.setProductId(99L);
        testCartItem = new CartItem();
        testCartItem.setCartItemId(101L);
        testCartItem.setProduct(product);
        testCartItem.setQuantity(2);
        testCartItem.setProductPrice(149.99);


        testCart = new Cart();
        testCart.setCartId(1L);
        testCart.setUser(testUser);
        testCart.setCartItems(Collections.singletonList(testCartItem));

        Mockito.when(authUtil.getCurrentUser()).thenReturn(testUser);
    }

    @Test
    void testGetCartItems() throws Exception {
        Mockito.when(cartService.getCartItems(testUser.getUserId()))
                .thenReturn(Collections.singletonList(testCartItem));

        mockMvc.perform(get("/api/cart"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cartItemId").value(testCartItem.getCartItemId()))
                .andExpect(jsonPath("$[0].quantity").value(testCartItem.getQuantity()))
                .andExpect(jsonPath("$[0].productPrice").value(testCartItem.getProductPrice()));
    }


    @Test
    void testAddToCart() throws Exception {
        CartItemRequest request = new CartItemRequest();
        request.setProductId(99L);
        request.setQuantity(2);

        Mockito.when(cartService.addProductToCart(testUser.getUserId(), 99L, 2))
                .thenReturn(testCart);

        mockMvc.perform(post("/api/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartId").value(1L));
    }

    @Test
    void testRemoveFromCart() throws Exception {
        Mockito.when(cartService.removeProductFromCart(testUser.getUserId(), 99L))
                .thenReturn(testCart);

        mockMvc.perform(delete("/api/cart/remove/99"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartId").value(1L));
    }

    @Test
    void testUpdateQuantity() throws Exception {
        CartItemRequest request = new CartItemRequest();
        request.setProductId(99L);
        request.setQuantity(5);

        Mockito.when(cartService.updateProductQuantity(testUser.getUserId(), 99L, 5))
                .thenReturn(testCart);

        mockMvc.perform(put("/api/cart/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartId").value(1L));
    }

    @Test
    void testClearCart() throws Exception {
        Mockito.doNothing().when(cartService).clearCart(testUser.getUserId());

        mockMvc.perform(delete("/api/cart/clear"))
                .andExpect(status().isNoContent());
    }
}
