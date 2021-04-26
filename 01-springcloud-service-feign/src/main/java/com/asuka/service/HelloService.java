package com.asuka.service;

import com.asuka.fallback.HelloFallBack;
import com.asuka.fallback.MyFallbackFactory;
import com.asuka.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 描述：声明远程接口
 *
 * @author chenpeng
 * @date 2021-04-23 12:20 PM
 */
// 声明远程服务名，表明该接口是调用该服务里的接口方法的
@FeignClient(name = "01-springcloud-service-provider",
        /*fallback = HelloFallBack.class*/ //  这种获取不到错误信息
        fallbackFactory = MyFallbackFactory.class)  // 这种能获取到错误信息
public interface HelloService {

    // @FeignClient声明后，通过@RequestMapping调用远程服务时，已经做好了负载均衡，默认为轮询
    @RequestMapping("/service/user")
    public User user();

    // 注意，一定要和远程服务的接口访问路径以及接口方法名一样
    @RequestMapping("/service/hello")
    public String hello();
}
