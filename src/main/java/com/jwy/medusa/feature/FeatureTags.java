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
package com.jwy.medusa.feature;

/**
 * <p>
 *     定义feature的命名, 匹配注册发现中心时的tag需要
 * <p>
 *     默认系统在启动时，注册到发现中心的tag：feature=normal，或者也可以不添加feature这个tag
 *     具体情况可以看{@link MyFeatureConfiguration}了解
 * </p>
 *
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/12/22
 */
public final class FeatureTags {

    /**
     * 发现中心的feature key命名
     */
    public static final String FEATURE_TAG_KEY = "feature";
    /**
     * 正常服务feature value，default
     */
    public static final String FEATURE_VALUE_NORMAL = "normal";
    public static final String FEATURE_VALUE_DEFAULT = FEATURE_VALUE_NORMAL;

    //其他分流feature可以根据 各个业务自定义

}
