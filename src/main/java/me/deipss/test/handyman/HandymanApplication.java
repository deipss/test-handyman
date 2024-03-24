package me.deipss.test.handyman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class},scanBasePackages = {"me.deipss.test.handyman"})
@EnableConfigurationProperties
@EnableAspectJAutoProxy(exposeProxy = true)
public class HandymanApplication {
    public static void main(String[] args) {
        SpringApplication.run(HandymanApplication.class, args);
    }
}
