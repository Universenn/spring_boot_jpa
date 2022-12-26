package com.example.springbootjpa1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootJpa1Application {

    public static void main(String[] args) {
        Hello hello = new Hello();
        hello.setHello("hello");
        String data = hello.getHello();
        System.out.println("data = "+ data);

        SpringApplication.run(SpringBootJpa1Application.class, args);
    }

}
