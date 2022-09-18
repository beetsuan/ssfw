package com.ssfw.auth.controller.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssfw.common.framework.vo.GmtVO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户角色 ValueObject
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-18 17:14:41
 */
@Data
@Accessors(chain = true)
public class AuthRoleVO extends GmtVO implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long roleId;
    /**
     * 角色code
     */
    private String roleCode;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 描述
     */
    private String roleDesc;

}
