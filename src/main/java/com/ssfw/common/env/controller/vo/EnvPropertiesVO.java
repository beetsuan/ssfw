package com.ssfw.common.env.controller.vo;


import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 环境配置 ValueObject
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-18 15:05:09
 */
@Data
@Accessors(chain = true)
public class EnvPropertiesVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 配置键
     */
    private String key;
    /**
     * 配置说明
     */
    private String title;
    /**
     * 配置值1
     */
    private String value01;
    /**
     * 配置值2
     */
    private String value02;
    /**
     * 配置值3
     */
    private String value03;
    /**
     * 配置值4
     */
    private String value04;
    /**
     * 配置值5
     */
    private String value05;
    /**
     * 创建者ID
     */
    private Long creatorId;
    /**
     * 创建者
     */
    private String creatorName;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新者ID
     */
    private Long updaterId;
    /**
     * 更新者
     */
    private String updaterName;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


}
