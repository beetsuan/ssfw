package com.ssfw.auth.ro;


import com.ssfw.auth.entity.AuthRoleEntity;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.BeanUtils;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
/**
 * 用户角色Request Object
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-16 15:09:19
 */
@Setter
@Getter
public class AuthRoleCreateRo{

    private static final long serialVersionUID = 9207358069800964972L;

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

    public AuthRoleEntity toEntity(){
        AuthRoleEntity entity = new AuthRoleEntity();
        BeanUtils.copyProperties(this, entity);
        return entity;
    }
}
