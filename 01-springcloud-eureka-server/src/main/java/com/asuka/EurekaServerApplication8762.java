package com.asuka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 配置运行时参数，让加载application-eureka8761.properties配置文件（右键该文件设置）： --spring.profiles.active=eureka8762
 */
@SpringBootApplication
@EnableEurekaServer // 表示该应用为eureka注册中心服务器
public class EurekaServerApplication8762 {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication8762.class, args);
    }

}
