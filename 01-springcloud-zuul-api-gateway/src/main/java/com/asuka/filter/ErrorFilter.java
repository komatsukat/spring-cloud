package com.asuka.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 描述：自定义error过滤器，在禁用默认error过滤器后，采用自定义guolvq
 *
 * @author chenpeng
 * @date 2021-04-23 2:24 PM
 */
// @Component
public class ErrorFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "error"; // 覆盖默认的error过滤器
    }

    @Override
    public int filterOrder() {
        return 1; // 前面已经自定义了一个pre过滤器
    }

    @Override
    public boolean shouldFilter() {
        return true; // 该过滤器需要执行
    }

    @Override
    public Object run() {
        try {
            RequestContext context = RequestContext.getCurrentContext();
            ZuulException exception = (ZuulException) context.getThrowable();
            System.out.println("----error-----");
            HttpServletResponse response = context.getResponse();
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(exception.nStatusCode);

            PrintWriter printWriter = null;
            try {
                printWriter = response.getWriter();
                printWriter.write("{code:" + exception.nStatusCode + ", message: " + exception.getMessage() + "}");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (printWriter != null) {
                    printWriter.close();
                }
            }
        } catch (Exception var5) {
            ReflectionUtils.rethrowRuntimeException(var5);
        }
        return null;
    }
}
