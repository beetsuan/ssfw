package com.ssfw.auth.ro;

import com.ssfw.auth.entity.AuthUserRoleEntity;
import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.BeanUtils;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
/**
 * 用户关联角色Request Object
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-16 15:06:12
 */
@Setter
@Getter
public class AuthUserRoleUpdateRo{

    private static final long serialVersionUID = 5128898565960357626L;

    /**
     * 用户id
     */
    @Min(0)
    @Max(Long.MAX_VALUE)
    private Long userId;

    /**
     * 角色id
     */
    @NotNull
    @Min(0)
    @Max(Long.MAX_VALUE)
    private Long roleId;

    public AuthUserRoleEntity toEntity(){
        AuthUserRoleEntity entity = new AuthUserRoleEntity();
        BeanUtils.copyProperties(this, entity);
        return entity;
    }
}
