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
package com.jwy.medusa.exception;

import com.jwy.medusa.mvc.MyStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 *     一个描述{@code Exception}的dto
 * </p>
 * <p>
 *     通过这个对象就可以生成一个对应的exception并且设置对应的信息
 * </p>
 *
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/12/15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDesc {
    private String fullName;
    private String message;
    private MyStatus myStatus;
}
