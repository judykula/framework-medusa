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
public class MyStatus {
    private Integer code;
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
