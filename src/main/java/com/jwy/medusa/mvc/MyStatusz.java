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

/**
 * <p>
 *      {@link MyStatus}的定义实例
 * </p>
 * <p>
 *     这里放一些框架类的基础Status信息设定：
 *     <ul>
 *         <li>SUCCESS: 响应成功</li>
 *         <li>FAIL: 响应失败</li>
 *     </ul>
 *     框架类（自定义）status code以<font color="red">4位</font>数数字表示，从1010开始，每次增加10，比如1010，1020，1030...
 *     这个"10"是留给"定义分裂"使用，比如在1010上要新加一个"分支"，可以为1011
 * </p>
 * <p></p>
 * <p>
 *     继承此类以扩展业务Status定义，所有业务code以<font color="red"> >=5位</font>数字表示，其中前两位代表不同的业务，后3位代表对应的状态，
 *     比如用户业务可以为：10010，权限业务为:11010，这仅是例子
 * </p>
 *
 * @see MyStatus
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/12/6
 */
public class MyStatusz {

    /**
     * 设置默认 SUCCESS 选项
     */
    public static MyStatus SUCCESS = MyStatus.of(200, "SUCCESS");
    /**
     * 设置默认 FAIL 选项
     */
    public static MyStatus FAIL = MyStatus.of(500, "FAIL");

    // —— —— —— —— —— —— —— —— —— —— —— —— —— —— —— —— —— —— —— —— ——

    /**
     * 处理json错误时的status
     */
    public static MyStatus JsonError =  MyStatus.of(1010, "process json error");
    /**
     * 生成id错误
     */
    public static MyStatus IdGenerateError =  MyStatus.of(1020, "id generate fail");

    /**
     * 设置默认 未授权访问
     */
    public static MyStatus UNAUTHORIZED = MyStatus.of(4010, "Unauthorized accessed");

}
