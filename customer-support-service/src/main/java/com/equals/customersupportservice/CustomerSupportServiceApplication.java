package com.equals.customersupportservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableConfigurationProperties
@EnableTransactionManagement
@SpringBootApplication
public class CustomerSupportServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerSupportServiceApplication.class, args);
	}

}
