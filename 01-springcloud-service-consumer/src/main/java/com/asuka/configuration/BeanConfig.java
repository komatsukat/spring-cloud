package com.asuka.configuration;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 描述：配置
 *
 * @author chenpeng
 * @date 2021-04-22 1:19 PM
 */
@Configuration
public class BeanConfig {

    @LoadBalanced // 开启Ribbon负载均衡模式调用，默认是轮询策略
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @Bean
    public IRule iRule() {
        return new RandomRule(); // 采用随机的负载均衡，覆盖掉默认的轮询策略
    }
}
