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
package com.jwy.medusa.saas;

import lombok.Data;

/**
 * <p>
 *     saas"多租户"系统的元数据存储dto
 * </p>
 *
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/12/20
 */
@Data
public class Tenant {

    /**"租户"ID*/
    private String tenantId;
    /**隔离级别*/
    private String isolation;

    public Tenant() {
    }

    public Tenant(String tenantId) {
        this.tenantId = tenantId;
    }

}
