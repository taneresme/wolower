package com.wolower.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;

@SpringBootApplication(scanBasePackages = { "com.wolower" }, exclude = JpaRepositoriesAutoConfiguration.class)
public class WolowerUiApplication {
	
	public static void main(String[] args) {
        SpringApplication.run(WolowerUiApplication.class, args);
    }
	
}
