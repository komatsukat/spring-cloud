package com.asuka.fallback;

import com.asuka.model.User;
import com.asuka.service.HelloService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 描述：远程调用异常时，可以获取到异常信息
 *
 * @author chenpeng
 * @date 2021-04-23 12:49 PM
 */
@Component // 交给Spring管理
public class MyFallbackFactory implements FallbackFactory<HelloService> {

    @Override
    public HelloService create(Throwable throwable) {
        return new HelloService() {
            @Override
            public User user() {
                System.out.println(throwable.getMessage());
                return new User();
            }

            @Override
            public String hello() {
                System.out.println(throwable.getMessage());
                return "error";
            }
        };
    }
}
