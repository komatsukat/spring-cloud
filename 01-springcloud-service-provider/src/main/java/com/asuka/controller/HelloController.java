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
        try {
            Thread.sleep(2000); // 测试hystrix在服务发生超时时，进行熔断，不发生异常，但返回null
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("我是8080");
        return "hello, 我是hello，808080808080";
    }

    @GetMapping("/service/user")
    public User user() {
        User user = new User();
        user.setId(1);
        user.setName("张三");
        user.setAge(10);
        return user;
    }
    // 查询get，新增post，修改put，删除delete
    @GetMapping("/service/getUser")
    public User getUser(@RequestParam("name") String name,
                        @RequestParam("age") Integer age) {
        return new User(1, name, age);
    }

    @PostMapping("/service/addUser")
    public User addUser(@RequestParam("name") String name,
                        @RequestParam("age") Integer age) {
        return new User(3, name, age);
    }

    @PutMapping("service/updateUser")
    public void updateUser(@RequestParam("name") String name,
                           @RequestParam("age") Integer age) {
        System.out.println("put 请求：" + new User(5, name, age));
    }

    @DeleteMapping("service/deleteUser")
    public void deleteUser(@RequestParam("name") String name,
                           @RequestParam("age") Integer age) {
        System.out.println("delete 请求：" + new User(7, name, age));
    }
}
