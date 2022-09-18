package com.ssfw.auth.controller.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssfw.common.framework.vo.GmtVO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户小组 ValueObject
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-18 17:08:01
 */
@Data
@Accessors(chain = true)
public class AuthGroupVO extends GmtVO implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 小组id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long groupId;
    /**
     * 小组名称
     */
    private String groupName;
    /**
     * 小组编号
     */
    private String groupCode;

}
