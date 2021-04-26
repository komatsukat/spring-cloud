package com.asuka.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述：网关过滤器
 *
 * @author chenpeng
 * @date 2021-04-23 1:35 PM
 */
@Component
public class AuthFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre"; // 表示在路由前进行过滤
    }

    @Override
    public int filterOrder() {
        return 0; // 表示该过滤器对执行顺序
    }

    @Override
    public boolean shouldFilter() {
        return true; // true表示过滤器需要执行
    }

    /**
     * http://localhost:6060/api-asuka/feign/getUser 不带token，不给路由，直接返回，提示非法访问
     * http://localhost:6060/api-asuka/feign/getUser?token=123 带token了，就给路由
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {

        // 定义一个RuntimeException，让自定义errorFilter来处理其他filter的异常
        //int a = 10 / 0;

        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String token = request.getParameter("token");
        if (token == null) { // 表示参数中没有token对话，不于路由，直接返回
            context.setSendZuulResponse(false); // 设置为false，表示不对该请求路由
            context.setResponseStatusCode(401);
            context.addZuulResponseHeader("content-type", "text/html;charset=utf-8");
            context.setResponseBody("非法访问");
        }
        return null; // run返回值没有任何意义，就直接返回null
    }
}
