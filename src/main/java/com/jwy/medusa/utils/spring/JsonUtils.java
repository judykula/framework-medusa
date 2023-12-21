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
package com.jwy.medusa.utils.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwy.medusa.exception.MyJsonException;
import com.jwy.medusa.mvc.MyStatusz;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *     统一json的选型，jackson。避免系统中出现多种json序列化工具的依赖。<br/>
 *     强烈建议你看完下面的「提示」之后再决定如何使用。
 * </p>
 * <p>
 *     这里仅提供常用的Object to String, 或者 String to Object方法
 *     需要这样使用：
 *     <pre>
 *         ...
 *         {@code @Autowired}
 *         private JsonUtils jsonUtils;
 *         ...
 *
 *         jsonUtils.xxx
 *     </pre>
 *
 *     如果你想使用原生的jackson或者复杂的序列化方式，可以自己注入{@link ObjectMapper}：
 *     <pre>
 *          ...
 *          {@code @Autowired}
 *          private ObjectMapper objectMapper;
 *          ...
 *     </pre>
 * </p>
 * <p>
 *     ！通常不建议即引入{@link JsonUtils} 又引入{@link ObjectMapper}<br/>
 *     或者说如果你能领悟到我们统一化使用jackson的设计要求，强烈建议你直接使用{@link ObjectMapper}
 * </p>
 * <p>
 *     鉴于json使用的频繁需求，为了避免重复的写一样的代码，这个工具类将"集成"入{@link MyContextUtils}中
 *
 *     你最终可以如下使用：
 *     <pre>
 *         ...
 *         {@code @Autowired}
 *         private MyContextUtils myContextUtils;
 *         ...
 *
 *         myContextUtils.Json.xxx();
 *     </pre>
 * </p>
 *
 * @see MyContextUtils
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/12/6
 */
public class JsonUtils {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Object to String
     * {@code return objectMapper.writeValueAsString(obj);}
     *
     * @param obj
     * @return
     * @throws MyJsonException
     */
    public String toString(Object obj){
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new MyJsonException(MyStatusz.JsonError, e);
        }
    }

    /**
     * String to Object
     * {@code return objectMapper.readValue(str, t);}
     *
     * @param str
     * @param t
     * @param <T>
     * @return
     * @throws MyJsonException
     */
    public <T> T toObj(String str, Class<T> t){
        try {
            return objectMapper.readValue(str, t);
        } catch (JsonProcessingException e) {
            throw new MyJsonException(MyStatusz.JsonError, e);
        }
    }


}
