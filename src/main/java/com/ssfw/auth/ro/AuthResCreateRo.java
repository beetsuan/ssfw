package com.ssfw.auth.ro;


import com.ssfw.auth.entity.AuthResEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
/**
 * 系统资源Request Object
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-16 09:53:38
 */
@Setter
@Getter
public class AuthResCreateRo {

    private static final long serialVersionUID = 4919995830323102514L;

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

    public AuthResEntity toEntity(){
        AuthResEntity entity = new AuthResEntity();
        BeanUtils.copyProperties(this, entity);
        return entity;
    }
}
