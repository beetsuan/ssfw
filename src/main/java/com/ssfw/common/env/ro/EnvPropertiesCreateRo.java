package com.ssfw.common.env.ro;


import com.ssfw.common.env.entity.EnvPropertiesEntity;
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
 * 环境配置Request Object
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-17 16:00:34
 */
@Setter
@Getter
public class EnvPropertiesCreateRo{

    private static final long serialVersionUID = 6401036929986058160L;

    /**
     * 配置说明
     */
    @Length(max = 255)
    private String propTitle;

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

    public EnvPropertiesEntity toEntity(){
        EnvPropertiesEntity entity = new EnvPropertiesEntity();
        BeanUtils.copyProperties(this, entity);
        return entity;
    }
}
