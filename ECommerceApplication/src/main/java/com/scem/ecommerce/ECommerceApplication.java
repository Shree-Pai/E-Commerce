package com.scem.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.scem.ecommerce")
public class ECommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}

}
