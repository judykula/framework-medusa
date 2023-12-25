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
package com.jwy.medusa.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwy.medusa.exception.FeignRequestException;
import com.jwy.medusa.mvc.MyErrorAttributes;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * <p>
 *     在feign调用的时候以序列化形式exception，"deserialize"的时候在这里，在{@link MyErrorAttributes}进行serialize
 * </p>
 * <p>
 *     默认的情况下这个功能是关闭支持的，配置{@code my.exception.transfer.receive=true}来开启。需要注意的是，
 *     这个配置"必须配合"对应请求server的{@code my.exception.transfer.send=true}属性配置才可以，即服务端
 *     需要开启"exception transfer"的"发送"功能，请求端也需要开启"exception transfer"的"接收"功能。
 * </p>
 * <p>
 *     默认关闭此功能是考虑到，就设计上来说，不希望你知道调用的服务具体的异常信心，因为你可以通过无状态的code
 *     来确定你调用的服务是否正常以及该如何处理，这样可以更好的解耦。
 *
 *     那什么时候开启这个功能？有些服务之间是属于"组合"甚至"聚合"关系的时候，互相的功能本身就有绑定关系，又有
 *     必要根据具体的异常信息来针对处理，可以开启这个功能。
 * </p>
 * <p>
 *     最后请记住，这是针对feign的处理
 * </p>
 *
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/12/14
 */
@Slf4j
@ConditionalOnProperty(name = "my.exception.transfer.receive", matchIfMissing = false)
public class MyErrorDecoder implements ErrorDecoder {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {

        if(response.status()  < 400){
            return logException(new RuntimeException(methodKey+": "+ response.reason()));
        }

        try {
            Reader reader = response.body().asReader(StandardCharsets.UTF_8);
            String responseBody = Util.toString(reader);
            Map responseMap = objectMapper.readValue(responseBody, Map.class);
            if(null == responseMap){
                return null;
            }

            byte[] exceptionSerialize = (byte[]) responseMap.get("exception_serialize");
            Object deserialize = SerializationUtils.deserialize(exceptionSerialize);
            return logException((Exception) deserialize);

        } catch (Exception e) {
            return logException(new FeignRequestException(e));
        }
    }

    private Exception logException(Exception e) {
        log.error("【MED050】feign ErrorDecoder failed : ", e);
        return e;
    }

}
