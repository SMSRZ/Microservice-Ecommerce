package com.smsrz.catalogservice;

import org.springframework.boot.SpringApplication;

public class TestCatalogServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(CatalogServiceApplication::main).with(ContainersConfig.class).run(args);
	}

}
