package com.asuka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：测试spring cloud config客户端能否连通配置中心服务端
 * 配置中心统一管理一份数据，多个微服务可以从这个配置中心读取到数据
 * @author chenpeng
 * @date 2021-04-23 5:24 PM
 */
@RestController
@RefreshScope
public class ConfigController {

    @Value("${url}") // 数据在远程配置中心，但读的是想相当于读本地一样
    private String url;

    @Autowired
    private Environment env;

    @RequestMapping("/cloud/url")
    public String url() {
        return url;
    }

    @RequestMapping("/cloud/url2")
    public String url2() {
        return env.getProperty("url");
    }

}
