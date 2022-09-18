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
 * 系统资源 CreateCommand
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-18 17:13:57
 */
@Setter
@Getter
public class AuthResCreateCmd implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 资源名称
     */
    @Length(min = 1, max = 100)
    @NotBlank
    private String resName;

    /**
     * 资源编号
     */
    @Length(max = 100)
    private String resCode;

    /**
     * 资源访问路径
     */
    @Length(max = 1000)
    private String resUrl;

    /**
     * 菜单名称
     */
    @Length(max = 100)
    private String menuName;

    /**
     * 菜单图标
     */
    @Length(max = 2000)
    private String menuIcon;

}
