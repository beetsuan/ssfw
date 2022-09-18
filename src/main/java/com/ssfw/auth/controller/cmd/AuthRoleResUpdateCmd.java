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
 * 角色权限 UpdateCommand
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-18 17:14:53
 */
@Setter
@Getter
public class AuthRoleResUpdateCmd implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    @Min(0)
    @Max(Integer.MAX_VALUE)
    private Long roleId;

    /**
     * 资源id
     */
    @NotNull
    @Min(0)
    @Max(Integer.MAX_VALUE)
    private Long resId;

    /**
     * 授权日期
     */
    @NotNull
    private LocalDateTime authDate;

}
