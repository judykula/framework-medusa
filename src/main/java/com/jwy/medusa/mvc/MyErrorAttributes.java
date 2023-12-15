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

import com.jwy.medusa.exception.ExceptionDesc;
import com.jwy.medusa.exception.MyServiceException;
import com.jwy.medusa.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *     扩展{@link org.springframework.boot.web.reactive.error.ErrorAttributes}的消息体
 *     以符合服务之间的异常消息传递以及统一输出
 * </p>
 *
 * @see DefaultErrorAttributes
 * @see MyMvcConfiguration
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/12/4
 */
@Slf4j
public class MyErrorAttributes extends DefaultErrorAttributes {

    @Autowired
    private JsonUtils jsonUtils;

    /**
     * 自定义出现异常时的key，要区别于系统自定义的数据，比如status 、message这些
     *
     * 这个key需要与{@link MyStatus}同步，不管正确还是错误的结果请求端都可以用同一种方式反序列化成功
     *
     * @see MyResponse
     */
    private final String My_Error_Key = "mystatus";
    /**
     * 错误信息表示
     */
    private final String My_Error_Msg = "服务繁忙，请稍后再试";

    /**
     * 重写这个方法，扩展{@code ErrorAttributes}
     *
     * @param request
     * @param options
     * @return
     */
    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {

        Map<String, Object> errorAttributes = super.getErrorAttributes(request, options);
        Throwable throwble = super.getError(request);//这个会throw exception，但是先不处理吧
        int statusCode = (int) errorAttributes.get("status");

        ExceptionDesc.ExceptionDescBuilder exceptionDescBuilder = ExceptionDesc.builder();
        exceptionDescBuilder.fullName(throwble.getClass().getName());
        exceptionDescBuilder.message(throwble.getMessage());

        /*虽然看到了super.getError()方法不会返回null，这里还是按照interface介绍，处理null问题*/
        if(Objects.isNull(throwble)){
            errorAttributes.put(My_Error_Key, MyStatus.of(statusCode, My_Error_Msg));
        }
        /*处理业务异常信息，将code与msg载入异常机制*/
        else if(throwble instanceof MyServiceException) {
            MyServiceException myException = (MyServiceException) throwble;
            MyStatus myStatus = myException.status().get();
            errorAttributes.put(My_Error_Key, myStatus);
            exceptionDescBuilder.myStatus(myStatus);
        }
        /*支持请求参数验证：@Validation*/
        else if(throwble instanceof MethodArgumentNotValidException || throwble instanceof WebExchangeBindException){
            String errorMsg = "param error";
            if(null == errorAttributes.get("errors")) {
                log.warn("Response validation information failed, set property 「server.error.includeBindingErrors=ALWAYS」to enable");
            }else {
                FieldError fieldError = ((List<FieldError>) errorAttributes.get("errors")).get(0);
                errorMsg = fieldError.getDefaultMessage();
            }
            errorAttributes.put(My_Error_Key, MyStatus.of(statusCode, errorMsg));
            log.warn("【MEA090】fail with {}: {}", throwble.getClass().getSimpleName(), errorMsg);
        }
        /*default 默认处理*/
        else{
            errorAttributes.put(My_Error_Key, MyStatus.of(statusCode, My_Error_Msg));
        }

        errorAttributes.put("ts", System.currentTimeMillis());
        errorAttributes.put("exception_desc", jsonUtils.toString(exceptionDescBuilder.build()));

        return errorAttributes;
    }
}
