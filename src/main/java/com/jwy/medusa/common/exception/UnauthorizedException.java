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
 *     未授权异常
 * </p>
 *
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2024/2/26
 */
public class UnauthorizedException extends MyServiceException {

    public UnauthorizedException() {
        this(MyStatusz.UNAUTHORIZED);
    }

    public UnauthorizedException(MyStatus status) {
        super(status);
    }

    public UnauthorizedException(MyStatus status, Throwable cause) {
        super(status, cause);
    }
}
