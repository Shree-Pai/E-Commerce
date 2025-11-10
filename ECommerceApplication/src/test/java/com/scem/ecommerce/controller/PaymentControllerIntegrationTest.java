package com.scem.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scem.ecommerce.entity.Payment;
import com.scem.ecommerce.entity.enums.PaymentMethod;
import com.scem.ecommerce.entity.enums.PaymentStatus;
import com.scem.ecommerce.security.JwtAuthenticationFilter;
import com.scem.ecommerce.service.PaymentService;
import com.scem.ecommerce.util.JwtTokenUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("removal")
@WebMvcTest(PaymentController.class)
@AutoConfigureMockMvc(addFilters = false)
class PaymentControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    PaymentService paymentService;

    ObjectMapper mapper = new ObjectMapper();
    Payment payment;

    @BeforeEach
    void setup() {
        payment = new Payment();
        payment.setPaymentId(1L);
        payment.setPaymentMethod(PaymentMethod.UPI);
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setTransactionId("TXN-001");
        payment.setAmountPaid(100.0);
    }

    @Test
    void testGetPayment() throws Exception {
        Mockito.when(paymentService.getPayment(1L)).thenReturn(payment);

        mockMvc.perform(get("/api/payments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value("TXN-001"));
    }

    @Test
    void testCreatePayment() throws Exception {
        Mockito.when(paymentService.createPaymentForOrder(eq(1L), eq(PaymentMethod.UPI), eq(100.0), eq("TXN-001")))
                .thenReturn(payment);

        mockMvc.perform(post("/api/payments/order/1")
                .param("method", "UPI")
                .param("amount", "100")
                .param("transactionId", "TXN-001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amountPaid").value(100.0));
    }

    @Test
    void testUpdatePaymentStatus() throws Exception {
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        Mockito.when(paymentService.updatePaymentStatus(1L, PaymentStatus.SUCCESS)).thenReturn(payment);

        mockMvc.perform(put("/api/payments/1/status")
                .param("status", "SUCCESS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentStatus").value("SUCCESS"));
    }
}
