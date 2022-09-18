package com.ssfw.auth.controller.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 角色权限 ValueObject
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-18 17:14:53
 */
@Data
@Accessors(chain = true)
public class AuthRoleResVO implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 资源id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long resId;
    /**
     * 授权日期
     */
    private LocalDateTime authDate;


}
