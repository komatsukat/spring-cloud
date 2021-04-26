package com.asuka.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：有些请求需要网关自己处理，那就要路由到网关自己的api上
 *
 * @author chenpeng
 * @date 2021-04-23 2:04 PM
 */
@RestController
public class ApiController {

    @RequestMapping("/api/local")
    public String hello() {
        System.out.println("网关自己处理。。。。。");
        return "zuul gateway local api";
    }
}
