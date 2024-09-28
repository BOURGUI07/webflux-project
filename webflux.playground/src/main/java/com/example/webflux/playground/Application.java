package com.example.webflux.playground;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication(scanBasePackages = "com.example.webflux.playground.sec05")
@EnableR2dbcRepositories(basePackages = "com.example.webflux.playground.sec05")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
