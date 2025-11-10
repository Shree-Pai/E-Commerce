package com.scem.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scem.ecommerce.dto.PaymentRequest;
import com.scem.ecommerce.dto.OrderResponse;
import com.scem.ecommerce.entity.Order;
import com.scem.ecommerce.security.JwtAuthenticationFilter;
import com.scem.ecommerce.service.OrderService;
import com.scem.ecommerce.service.PaymentService;
import com.scem.ecommerce.util.JwtTokenUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SuppressWarnings("removal")
@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false) 
class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    private OrderService orderService;
    @MockBean
    private PaymentService paymentService;

    private ObjectMapper objectMapper;
    private Order order;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        order = new Order();
        order.setId(1L);
        order.setTotalAmount(500.0);
    }

    @Test
    void testGetAllOrders() throws Exception {
        when(orderService.getAllOrders()).thenReturn(Collections.singletonList(order));

        mockMvc.perform(get("/api/orders")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @SuppressWarnings("unused")
	@Test
    void testPlaceOrderAndPay() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(500.0);
        paymentRequest.setMethod(null); 
        paymentRequest.setTransactionId("TXN-1001");

        OrderResponse response = new OrderResponse(order, null);

        when(orderService.placeOrderForCurrentUser()).thenReturn(order);
        when(paymentService.createPaymentForOrder(anyLong(), any(), any(), any())).thenReturn(null);

        mockMvc.perform(post("/api/orders/place-and-pay")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.order.id").value(1L));
    }
}
