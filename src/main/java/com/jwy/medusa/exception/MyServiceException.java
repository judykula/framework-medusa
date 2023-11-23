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

/**
 * <p>
 *     核心"业务逻辑"异常
 * </p>
 * <p>
 *      规定所有自定义的异常继承此类
 *
 *      规定"业务异常"在Service进行处理与抛出
 *
 *      规定所有业务异常都必须设定"code"，"message"可以忽略
 * </p>
 *
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/10/30
 */
public class MyServiceException extends RuntimeException{

    private int code;
    private String message;

    public MyServiceException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public MyServiceException(int code, String message, Throwable cause) {
        super(cause);
        this.code = code;
        this.message = message;
    }
}