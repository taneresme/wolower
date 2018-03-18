package com.wolower.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
public class WolowerUiApplication {
	
	public static void main(String[] args) {
        SpringApplication.run(WolowerUiApplication.class, args);
    }
	
}
