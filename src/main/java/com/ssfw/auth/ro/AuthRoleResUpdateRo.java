package com.ssfw.auth.ro;

import com.ssfw.auth.entity.AuthRoleResEntity;
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
 * 角色权限Request Object
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-16 10:27:26
 */
@Setter
@Getter
public class AuthRoleResUpdateRo{

    private static final long serialVersionUID = 9213506971326443267L;

    /**
     * 角色id
     */
    @Min(0)
    @Max(Long.MAX_VALUE)
    private Long roleId;

    /**
     * 资源id
     */
    @NotNull
    @Min(0)
    @Max(Long.MAX_VALUE)
    private Long resId;

    /**
     * 授权日期
     */
    @NotNull
    private LocalDateTime authDate;

    public AuthRoleResEntity toEntity(){
        AuthRoleResEntity entity = new AuthRoleResEntity();
        BeanUtils.copyProperties(this, entity);
        return entity;
    }
}
