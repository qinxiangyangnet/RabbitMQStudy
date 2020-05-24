package com.yueyang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.yueyang.mapper")
@SpringBootApplication
public class AppOrder {

	public static void main(String[] args) {
		SpringApplication.run(AppOrder.class, args);
	}

}
