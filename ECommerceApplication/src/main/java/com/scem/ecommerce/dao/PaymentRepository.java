package com.scem.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.scem.ecommerce.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

}
