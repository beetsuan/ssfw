package com.ssfw.auth.ro;

import com.ssfw.auth.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author a
 * @date 2022/9/15
 * @since 2.7.3
 */
@Setter
@Getter
public class UserUpdateRo {
    /**
     * 用户id
     */
    @Min(0)
    @Max(Integer.MAX_VALUE)
    private Long userId;

    /**
     * 登录名称
     */
    @Length(min = 1, max = 100)
    @NotBlank
    private String username;

    /**
     * 密码
     */
    @Length(min = 1, max = 100)
    @NotBlank
    private String password;

    /**
     * 用户昵称
     */
    @Length(min = 1, max = 100)
    @NotBlank
    private String nickname;

    public UserEntity toEntity(){
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(this, entity);
        return entity;
    }
}
