package com.example.noteworkshop;

import org.mybatis.spring.annotation.MapperScan; // 注意导入这个包
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.noteworkshop.mapper") // 告诉 Spring 扫描 mapper 包下的接口
public class NoteWorkshopApplication {
    public static void main(String[] args) {
        SpringApplication.run(NoteWorkshopApplication.class, args);
    }
}