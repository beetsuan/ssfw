package com.ssfw.auth.ro;



import com.ssfw.auth.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * 用户Request Object
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-15 17:54:42
 */
@Setter
@Getter
public class UserCreateRo{

    private static final long serialVersionUID = 2620305485392680128L;

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
