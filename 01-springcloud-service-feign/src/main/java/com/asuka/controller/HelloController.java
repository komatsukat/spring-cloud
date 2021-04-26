package com.asuka.controller;

import com.asuka.model.User;
import com.asuka.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：hello feign
 *
 * @author chenpeng
 * @date 2021-04-23 12:24 PM
 */
@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;

    @RequestMapping("/feign/getUser")
    public User getUser() {
        return helloService.user();
    }

    @RequestMapping("/feign/hello")
    public String hello() {
        return helloService.hello();
    }
}
