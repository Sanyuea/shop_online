package com.example.shop_online;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;

@SpringBootApplication
@MapperScan("com.example.shop_online.mapper")
@Async
public class ShopOnlineApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopOnlineApplication.class, args);
    }

}
