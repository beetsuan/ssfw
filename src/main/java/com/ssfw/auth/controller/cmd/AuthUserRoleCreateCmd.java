package com.ssfw.auth.controller.cmd;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
/**
 * 用户关联角色 CreateCommand
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-18 17:15:38
 */
@Setter
@Getter
public class AuthUserRoleCreateCmd implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    @NotNull
    @Min(0)
    @Max(Integer.MAX_VALUE)
    private Long roleId;

}
