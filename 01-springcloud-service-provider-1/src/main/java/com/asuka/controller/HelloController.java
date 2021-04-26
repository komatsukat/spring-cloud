package com.asuka.controller;

import com.asuka.model.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：hello
 *
 * @author chenpeng
 * @date 2021-04-22 1:14 PM
 */
@RestController
public class HelloController {

    @RequestMapping("/service/hello")
    public String hello() {
        int a = 10 / 0; // 测试hystrix在服务发生异常后，进行熔断
        System.out.println("调用了8081");
        return "hello, 我是hello，是8081";
    }

    @GetMapping("/service/user")
    public User user() {
        User user = new User();
        user.setId(2);
        user.setName("李四");
        user.setAge(12);
        return user;
    }

    @GetMapping("service/getUser")
    public User getUser(@RequestParam("name") String name,
                        @RequestParam("age") Integer age) {
        return new User(2, name, age);
    }

    @PostMapping("/service/addUser")
    public User addUser(@RequestParam("name") String name,
                        @RequestParam("age") Integer age) {
        return new User(4, name, age);
    }

    @PutMapping("service/updateUser")
    public void updateUser(@RequestParam("name") String name,
                           @RequestParam("age") Integer age) {
        System.out.println("put 请求：" + new User(6, name, age));
    }

    @DeleteMapping("service/deleteUser")
    public void deleteUser(@RequestParam("name") String name,
                           @RequestParam("age") Integer age) {
        System.out.println("delete 请求：" + new User(8, name, age));
    }
}
