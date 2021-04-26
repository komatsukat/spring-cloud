package com.asuka.controller;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：自定义全局error错误页面的形式，处理filter中的异常，代替默认的error filter
 *
 * @author chenpeng
 * @date 2021-04-23 2:42 PM
 */
@RestController
public class ErrorHandlerController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error"; // 返回处理异常的页面路径
    }

    @RequestMapping("/error")
    public String error() {
        RequestContext context = RequestContext.getCurrentContext();
        ZuulException exception = (ZuulException) context.getThrowable();
        return "{code: " + exception.nStatusCode + ", msg: " + exception.getMessage() + "}";
    }
}
