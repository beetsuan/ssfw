package com.ssfw.auth.controller.cmd;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
/**
 * 用户角色 UpdateCommand
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-18 17:14:41
 */
@Setter
@Getter
public class AuthRoleUpdateCmd implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    @Min(0)
    @Max(Integer.MAX_VALUE)
    private Long roleId;

    /**
     * 角色code
     */
    @Length(min = 1, max = 100)
    @NotBlank
    private String roleCode;

    /**
     * 角色名称
     */
    @Length(min = 1, max = 100)
    @NotBlank
    private String roleName;

    /**
     * 描述
     */
    @Length(max = 100)
    private String roleDesc;

}
