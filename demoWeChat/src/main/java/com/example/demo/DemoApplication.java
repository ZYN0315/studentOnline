package com.example.demo;


import com.example.demo.entity.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;
import util.wechatUrl;
import com.example.demo.entity.wechatContant;


@SpringBootApplication
@ServletComponentScan
@EnableScheduling
public class DemoApplication {

    @Autowired
    private wechatContant wechatContant;

    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);

    }

}
