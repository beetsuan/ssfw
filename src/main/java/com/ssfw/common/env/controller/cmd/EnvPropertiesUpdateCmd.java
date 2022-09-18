package com.ssfw.common.env.controller.cmd;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
/**
 * 环境配置 UpdateCommand
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-18 15:31:08
 */
@Setter
@Getter
public class EnvPropertiesUpdateCmd implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 配置键
     */
    @Length(max = 128)
    @NotBlank
    private String key;

    /**
     * 配置说明
     */
    @Length(min = 1, max = 255)
    @NotBlank
    private String title;

    /**
     * 配置值1
     */
    @Length(max = 1000)
    private String value01;

    /**
     * 配置值2
     */
    @Length(max = 1000)
    private String value02;

    /**
     * 配置值3
     */
    @Length(max = 1000)
    private String value03;

    /**
     * 配置值4
     */
    @Length(max = 1000)
    private String value04;

    /**
     * 配置值5
     */
    @Length(max = 1000)
    private String value05;

}
