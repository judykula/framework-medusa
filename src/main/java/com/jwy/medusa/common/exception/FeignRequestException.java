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

/**
 * <p>
 *     feign请求时抛出的异常
 * </p>
 *
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/12/18
 */
public class FeignRequestException extends Exception{
    public FeignRequestException() {
        super();
    }

    public FeignRequestException(String message) {
        super(message);
    }

    public FeignRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public FeignRequestException(Throwable cause) {
        super(cause);
    }

    protected FeignRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
