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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 *     定义状态封装对象
 * </p>
 * <p>
 *     主要包含两个属性：
 *     <pre>
 *          code: 状态码，默认success：200，fail：500
 *          desc: 描述，可选项
 *     </pre>
 * </p>
 * <p>
 *     可以如下方式生成：
 *     <pre>
 *         {@code MyStatus.of(200, "xxx")} 或者 {@code MyStatus.of(200)}
 *     </pre>
 *
 *     如果你定义了/已存在一个可以大体描述的定义：{@link MyStatusz}，建议这样使用：
 *     {@code MyStatusz.XXX}
 * </p>
 *
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/11/22
 */
@Getter
@ToString
@Schema(description = "接口响应状态信息")
public class MyStatus implements Serializable {

    private static final long serialVersionUID = -7260329278483121972L;

    @Schema(name = "code", description = "一般情况下等同于http code，但是优先级更高。" +
            "通常情况下【只有】200表示响应成功，500表示服务异常，之外还有其他自定义code，参考具体接口")
    private Integer code;
    @Schema(name = "desc", description = "概要描述信息，作为参考使用")
    private String desc;

    public MyStatus() {
    }

    private MyStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static MyStatus of(int code, String desc){
           return new MyStatus(code, desc);
    }

    public static MyStatus of(int code){
        return new MyStatus(code, null);
    }
}
