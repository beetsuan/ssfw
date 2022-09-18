package com.ssfw.auth.controller.cmd;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
/**
 * 用户 CreateCommand
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-18 17:18:02
 */
@Setter
@Getter
public class UserCreateCmd implements Serializable {

    private static final long serialVersionUID = 1L;

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

    /**
     * 分组id
     */
    @Min(0)
    @Max(Integer.MAX_VALUE)
    private Long groupId;

}
