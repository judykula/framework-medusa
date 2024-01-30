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
package com.jwy.medusa.common.exception;

import com.jwy.medusa.mvc.MyStatus;

import java.util.Optional;

/**
 * <p>
 *     定义自定义异常信息
 * </p>
 *
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/12/5
 */
public interface MyServiceExceptionDefinition {

    Optional<MyStatus> status();
}
