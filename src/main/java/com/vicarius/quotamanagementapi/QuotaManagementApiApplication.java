package com.vicarius.quotamanagementapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class QuotaManagementApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuotaManagementApiApplication.class, args);
	}

}
