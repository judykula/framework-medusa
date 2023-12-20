/*
 * easy come, easy go.
 *
 * contact : syiae.jwy@gmail.com
 *
 * · · · · ||   ..     __       ___      ____  ®
 * · · · · ||  ||  || _ ||   ||    ||   ||      ||
 * · · · · ||  ||  \\_ ||_.||    ||   \\_  ||
 * · · _//                                       ||
 * · · · · · · · · · · · · · · · · · ·· ·    ___//
 */
package com.jwy.medusa.mvc;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *     在这里处理一下请求对应的返回数据：对外的api请求不需要返回exception等敏感信息
 *     对内的rpc（比如"feign"）可以带异常信息等
 * </p>
 * <p>
 *     覆盖{@link ErrorMvcAutoConfiguration#basicErrorController(ErrorAttributes, ObjectProvider)}方法
 * </p>
 *
 * @see DefaultErrorWebExceptionHandler
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/12/1
 */
public class MyBasicErrorController extends BasicErrorController {

    public MyBasicErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
        super(errorAttributes, errorProperties);
    }

    public MyBasicErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties, List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorProperties, errorViewResolvers);
    }

    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {

        ResponseEntity<Map<String, Object>> errorRes = super.error(request);
        Map<String, Object> error = errorRes.getBody();

        error.remove("trace");
        error.remove("errors");
        error.remove("timestamp");

        if(!error.get("path").toString().startsWith("/feign")){
            error.remove("path");
            error.remove("error");
            error.remove("message");
            error.remove("exception");
            error.remove("exception_serialize");
        }

        return errorRes;
    }
}
