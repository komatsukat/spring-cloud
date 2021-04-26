package com.asuka.hystrix;

import com.netflix.hystrix.HystrixCommand;
import org.springframework.web.client.RestTemplate;

/**
 * 描述：自定义hystrixCommand，进行远程调用服务，及发生异常的处理
 *
 * @author chenpeng
 * @date 2021-04-23 9:58 AM
 */
public class MyHystrixCommand extends HystrixCommand<String> {

    private RestTemplate restTemplate;

    public MyHystrixCommand(Setter setter, RestTemplate restTemplate) {
        super(setter); // 服务命名，服务分组的一些信息
        this.restTemplate = restTemplate;
    }

    /**
     * 在该方法进行远程调用
     *
     * @return
     * @throws Exception
     */
    @Override
    protected String run() throws Exception {
        return restTemplate.getForEntity("http://01-SPRINGCLOUD-SERVICE-PROVIDER/service/hello", String.class).getBody();
    }

    /**
     * 调用者本身发生异常，或者服务发生异常、超时、不可用时，回调的方法。
     * 是熔断回调的方法，也是降级方法。
     *
     * @return
     */
    @Override
    public String getFallback() {
        Throwable throwable = getExecutionException(); // 调用父类方法获取异常信息
        System.out.println("服务熔断或降级后进行回调: " + throwable.getMessage());
        return "error";
    }
}
