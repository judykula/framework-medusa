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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

/**
 * <p>
 *     统一定义请求响应数据：{@code Response<T>}
 *     封装内容包括:
 *     <pre>
 *          {@link MyStatus}：状态码
 *          T: data
 *          latency: 程序执行时间(optional)
 *          timestamp: 响应时的时间戳
 *     </pre>
 *     结构体[例]：
 *     <pre>
 *         {
 *             "mystatus":
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
 * @see MyStatus
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/11/22
 */
@Getter
@ToString
@Schema(description = "接口统一响应数据")
public class MyResponse<T> {

    @Schema(name = "mystatus", description = "服务接口的响应状态信息，包括状态码和描述信息。状态码与http code同步，判断优先级最高")
    private MyStatus mystatus;
    @Schema(name = "data", description = "响应数据，JSON Object，具体对象取决于每个接口的具体响应")
    private T data;
    @Schema(name = "latency", description = "接口的执行时间(不包括网络耗时)，单位是millisecond", example = "100")
    private Long latency;
    @Schema(name = "ts", description = "接口响应时的时间戳, 单位是millisecond", example = "169232345346")
    private long ts = System.currentTimeMillis();

    public MyResponse() {
    }

    private MyResponse(MyStatus status, T data) {
        this.mystatus = status;
        this.data = data;
    }

    private MyResponse(MyStatus status, T data, long latency) {
        this.mystatus = status;
        this.data = data;
        this.latency = latency;
    }

    public static <T> MyResponse<T> of(MyStatus status, T data){
        return new MyResponse(status, data);
    }

    public static <T> MyResponse<T> of(MyStatus status, T data, long latency){
        return new MyResponse(status, data, latency);
    }

    public static MyResponse ofSuccess(){
        return new MyResponse<>(MyStatusz.SUCCESS, null);
    }

    public static <T> MyResponse<T> ofSuccess(T t){
        return new MyResponse<>(MyStatusz.SUCCESS, t);
    }

    public static MyResponse ofFail(){
        return new MyResponse<>(MyStatusz.FAIL, null);
    }

    public static <T> MyResponse<T> ofFail(T t){
        return new MyResponse<>(MyStatusz.FAIL, t);
    }

}
