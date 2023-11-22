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

import lombok.Getter;

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
 *         {@code Status.of(200, "xxx")} 或者 {@code Status.of(200)}
 *     </pre>
 * </p>
 *
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/11/22
 */
@Getter
public class Status {

    /**
     * 设置默认 SUCCESS 选项
     */
    public static Status SUCCESS = new Status(200, "SUCCESS");
    /**
     * 设置默认 FAIL 选项
     */
    public static Status FAIL = new Status(500, "FAIL");

    private Integer code;
    private String desc;

    public Status() {
    }

    private Status(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static Status of(int code, String desc){
           return new Status(code, desc);
    }

    public static Status of(int code){
        return new Status(code, null);
    }
}
