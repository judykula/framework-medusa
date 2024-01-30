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

/**
 * <p>
 *     定义json exception
 * </p>
 *
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/12/6
 */
public class MyJsonException extends MyServiceException{

    public MyJsonException() {
        this(MyStatusz.JsonError);
    }

    public MyJsonException(MyStatus status) {
        super(status);
    }

    public MyJsonException(MyStatus status, Throwable cause) {
        super(status, cause);
    }
}
