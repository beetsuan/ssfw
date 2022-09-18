package com.ssfw.common.framework.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author beets
 */
@Data
@Accessors(chain = true)
public class GmtVO implements Serializable {

    private static final long serialVersionUID = 1L;

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
