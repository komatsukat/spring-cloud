package com.asuka.fallback;

import com.asuka.model.User;
import com.asuka.service.HelloService;
import org.springframework.stereotype.Component;

/**
 * 描述：HelloService熔断降级处理类
 * 当调用远程接口超时、异常、不可用时，回调本地降级方法
 * 这种方式获取不到异常信息
 *
 * @author chenpeng
 * @date 2021-04-23 12:36 PM
 */
@Component // 要交给Spring管理
public class HelloFallBack implements HelloService {

    @Override
    public User user() {
        return new User();
    }

    @Override
    public String hello() {
        return "error";
    }
}
