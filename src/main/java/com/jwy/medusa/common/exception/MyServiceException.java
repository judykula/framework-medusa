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
import com.jwy.medusa.mvc.MyStatusz;

import java.util.Optional;

/**
 * <p>
 *     核心"业务逻辑"异常
 * </p>
 * <p>
 *      规定所有自定义的"业务"异常继承此类
 *
 *      规定"业务异常"在Service进行处理与抛出
 *
 *      规定所有业务异常都必须设定{@link MyStatus}状态
 * </p>
 *
 * @see MyStatus
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/10/30
 */
public class MyServiceException extends RuntimeException implements MyServiceExceptionDefinition{

    private MyStatus status;

    public MyServiceException() {
        this(MyStatusz.FAIL);
    }

    public MyServiceException(MyStatus status) {
        this.status = status;
    }

    public MyServiceException(MyStatus status, Throwable cause) {
        super(cause);
        this.status = status;
    }

    @Override
    public Optional<MyStatus> status() {
        return Optional.ofNullable(this.status);
    }
}
