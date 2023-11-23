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
 *     统一定义请求响应数据：{@code Response<T>}
 *     封装内容包括:
 *     <pre>
 *          {@link Status}：状态码
 *          T: data
 *          latency: 程序执行时间(optional)
 *          timestamp: 响应时的时间戳
 *     </pre>
 *     结构体[例]：
 *     <pre>
 *         {
 *             "status":
 *                  {
 *                      code: ..., //int
 *                      desc: "...", //string
 *                  },
 *             data: ..., //jsonObject
 *             latency: ..., //optional, millisecond
 *             timestamp: ... // current timestamp , millisecond
 *         }
 *     </pre>
 * </p>
 * <p>
 *     ！所有的接口返回的DTO都是此类，非特殊不允许自定义
 * </p>
 * <p>
 *     可以按照如下方式调用：
 *     <pre>
 *         自定义状态时：{@code Response.of(status, data)}
 *         返回正确结果时：{@code Response.ofSuccess(data)}
 *         返回错误结果时：{@code Response.ofFail(data)}
 *
 *         如果想返回程序执行时间，比如执行了500ms：
 *         {@code Response.of(status, data, 500)}
 *     </pre>
 * </p>
 *
 * @see Status
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/11/22
 */
@Getter
public class MyResponse<T> {

    private Status status;
    private T data;
    private Long latency;
    private long timestamp = System.currentTimeMillis();

    public MyResponse() {
    }

    private MyResponse(Status status, T data) {
        this.status = status;
        this.data = data;
    }

    private MyResponse(Status status, T data, long latency) {
        this.status = status;
        this.data = data;
        this.latency = latency;
    }

    public static <T> MyResponse<T> of(Status status, T data){
        return new MyResponse(status, data);
    }

    public static <T> MyResponse<T> of(Status status, T data, long latency){
        return new MyResponse(status, data, latency);
    }

    public static MyResponse ofSuccess(){
        return new MyResponse<>(Status.SUCCESS, null);
    }

    public static <T> MyResponse<T> ofSuccess(T t){
        return new MyResponse<>(Status.SUCCESS, t);
    }

    public static MyResponse ofFail(){
        return new MyResponse<>(Status.FAIL, null);
    }

    public static <T> MyResponse<T> ofFail(T t){
        return new MyResponse<>(Status.FAIL, t);
    }

}
