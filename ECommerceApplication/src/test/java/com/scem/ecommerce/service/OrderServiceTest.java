package com.scem.ecommerce.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.scem.ecommerce.dao.OrderRepository;
import com.scem.ecommerce.dao.UserRepository;
import com.scem.ecommerce.entity.Order;
import com.scem.ecommerce.entity.User;
import com.scem.ecommerce.service.impl.OrderServiceImpl;
import com.scem.ecommerce.util.AuthUtil; 

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepo;
    @Mock
    private UserRepository userRepo;
    @Mock
    private CartService cartService;
    @Mock
    private AuthUtil authUtil; 

    @InjectMocks
    private OrderServiceImpl orderService;

    private User user;
    private Order order;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUserId(1L);
        order = new Order();
        order.setUser(user);
    }

    @Test
    void testGetOrderById() {
        
        when(authUtil.getCurrentUser()).thenReturn(user);
        when(orderRepo.findById(1L)).thenReturn(Optional.of(order));

        
        Order o = orderService.getOrderByIdForCurrentUser(1L);
     
        assertNotNull(o);
        assertEquals(user.getUserId(), o.getUser().getUserId());
    }

    @Test
    void testGetAllOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        when(orderRepo.findAll()).thenReturn(orders);
        List<Order> all = orderService.getAllOrders();
        assertEquals(1, all.size());
    }
}