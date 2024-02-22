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
package com.jwy.medusa.common.utils;

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

    // —— —— —— —— —— —— —— —— —— —— —— —— —— —— —— ——

    /**请求时的终端id*/
    String REQUEST_CLIENT_ID = "X-MY-ClientId";
    /**请求时的token*/
    String REQUEST_ACCESS_TOKEN = "X-MY-AccessToken";
    /**请求时的设备ID（APP）*/
    String REQUEST_DEVICE_ID = "X-MY-DeviceId";

    /**REQUEST_CLIENT_ID的描述信息*/
    String REQUEST_CLIENT_ID_DESC = "客户端ID，不同的客户对应以下不同的值：" +
            "<br> IOS端: ios " +
            "<br> Android端: android " +
            "<br> PC端: pc" +
            "<br> 小程序: mini";

}
