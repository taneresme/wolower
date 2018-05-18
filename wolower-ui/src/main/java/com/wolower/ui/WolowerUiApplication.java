package com.wolower.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = { "com.wolower" }, exclude = JpaRepositoriesAutoConfiguration.class)
public class WolowerUiApplication extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
        SpringApplication.run(WolowerUiApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WolowerUiApplication.class);
    }
}
