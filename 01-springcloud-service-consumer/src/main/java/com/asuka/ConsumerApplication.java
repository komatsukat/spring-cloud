package com.asuka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient // 能连接到eureka服务器，把自己注册上去，并能拉取到其他服务提供者注册的信息到本地，Ribbon以此为基础进行restful调用和负载均衡，如果要用其他注册中心，不能这个注解
@EnableCircuitBreaker // 开启断路器功能（先引包）
// @SpringCloudApplication // 可以用这一个注解代替上面三个注解
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

}
