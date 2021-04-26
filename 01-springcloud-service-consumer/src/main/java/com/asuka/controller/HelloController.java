package com.asuka.controller;

import com.asuka.hystrix.MyHystrixCommand;
import com.asuka.model.User;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 描述：hello
 *
 * @author chenpeng
 * @date 2021-04-22 1:18 PM
 */
@RestController // 可直接返回Json字符串 相当于@Controller和@ResponseBody
public class HelloController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/web/hello")
    @HystrixCommand(fallbackMethod = "error",
            // ignoreExceptions = RuntimeException.class, // 表示发生该异常时，忽略该异常，不回调熔断方法
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",
                    value = "1500")}) // 增加了服务熔断机制，扇出到链路调用超时或者调用异常时，会执行error方法
    public String hello() {

        // int a = 20 / 0; // 调用者自己发生异常时，也会进行熔断

        // return restTemplate.getForEntity("http://localhost:8080/service/hello", String.class).getBody();
        // 使用Ribbon负载均衡模式调用，就不需要关注机器是哪个，端口是什么
        // getForObject直接返回body
        return restTemplate.getForEntity("http://01-SPRINGCLOUD-SERVICE-PROVIDER/service/hello", String.class).getBody();
    }

    /**
     * 服务熔断就是当调用服务发生超时或者异常时，不再阻塞等待结果，而是返回一个错误码，然后回调本地fallbackMethod，进行异常处理，返回友好信息。
     * 服务降级就是在服务发生异常调用不了后，以后都将不再调用异常的服务，而是调用本地的fallbackMethod，返回友好信息，提高可用性。
     * 服务熔断后，可以通过Throwable参数传递异常信息。
     * <p>
     * 该方法是熔断后调用的方法，也是降级后调用的方法。
     *
     * @param throwable
     * @return
     */
    private String error(Throwable throwable) {
        System.out.println(throwable.getMessage());
        return "error";
    }

    /**
     * 通过自定义HystrixCommand调用远程服务，和上面的调用其实一样
     *
     * @return
     */
    @RequestMapping("/web/hystrix")
    public String hystrix() throws ExecutionException, InterruptedException {
        MyHystrixCommand hystrixCommand = new MyHystrixCommand(
                com.netflix.hystrix.HystrixCommand.Setter.withGroupKey(
                        HystrixCommandGroupKey.Factory.asKey("")),
                restTemplate);
        /**
         * 使用execute是同步调用，调用远程服务，如果服务正常就返回调用返回的结果返回，如果异常就返回熔断方法里的信息。
         */
        // String res = hystrixCommand.execute();

        /**
         * queue是异步调用
         */
        Future<String> future = hystrixCommand.queue();
        String res = future.get();
        return res;
    }

    // 返回pojo对象
    @RequestMapping("/web/user")
    public User user() {
        ResponseEntity<User> responseEntity = restTemplate.getForEntity("http://01-SPRINGCLOUD-SERVICE-PROVIDER/service/user", User.class);
        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getStatusCodeValue());
        System.out.println(responseEntity.getHeaders());
        System.out.println(responseEntity.getBody());
        return responseEntity.getBody();
    }

    // 两种传参形式：数组和map
    @RequestMapping("/web/getUser")
    public User getUser() {
        String[] params = {"张三", "14"};
        ResponseEntity<User> entiry = restTemplate.getForEntity("http://01-SPRINGCLOUD-SERVICE-PROVIDER/service/getUser?name={0}&age={1}", User.class, params);
        System.out.println(entiry.getBody());

        Map<String, String> maps = new HashMap<>();
        maps.put("name", "张三");
        maps.put("age", "14");
        return restTemplate.getForEntity("http://01-SPRINGCLOUD-SERVICE-PROVIDER/service/getUser?name={name}&age={age}", User.class, maps).getBody();
    }


    @RequestMapping("web/addUser")
    public User addUser() {
        // 注意，这里post传参不能用平常的HashMap
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("name", "张三");
        params.add("age", 55);

        // 用Post请求时，参数在请求体中才能正确解析到
        return restTemplate.postForObject("http://01-SPRINGCLOUD-SERVICE-PROVIDER/service/addUser", params, User.class);
    }

    @RequestMapping("web/updateUser")
    public String updateUser() {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("name", "李四啊");
        params.add("age", 23);

        restTemplate.put("http://01-SPRINGCLOUD-SERVICE-PROVIDER/service/updateUser", params);
        return "success";
    }

    @RequestMapping("web/deleteUser")
    public String deleteUser() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "李四啊");
        params.put("age", 23);

        restTemplate.delete("http://01-SPRINGCLOUD-SERVICE-PROVIDER/service/deleteUser?name={name}&age={age}", params);
        return "success";
    }
}
