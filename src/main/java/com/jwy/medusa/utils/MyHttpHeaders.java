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
package com.jwy.medusa.utils;

/**
 * <p>
 *     自定义HTTP的header
 * </p>
 *
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/12/19
 */
public interface MyHttpHeaders {

    /**请求源的头信息*/
    String REQUEST_ORIGIN = "X-MY-ORIGIN";

    /**SaaS - tenant的头信息, 请以json格式存储*/
    String REQUEST_SAAS_TENANT = "X-MY-TENANT";

    /**灰度路由的头信息*/
    String REQUEST_FEATURE = "X-MY-FEATURE";


}
