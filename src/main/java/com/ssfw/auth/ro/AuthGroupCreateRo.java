package com.ssfw.auth.ro;


import com.ssfw.auth.entity.AuthGroupEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
/**
 * 用户小组Request Object
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-15 18:12:25
 */
@Setter
@Getter
public class AuthGroupCreateRo{

    private static final long serialVersionUID = 1130709825904388121L;

    /**
     * 小组名称
     */
    @Length(min = 1, max = 100)
    @NotBlank
    private String groupName;

    /**
     * 小组编号
     */
    @Length(min = 1, max = 100)
    @NotBlank
    private String groupCode;

    public AuthGroupEntity toEntity(){
        AuthGroupEntity entity = new AuthGroupEntity();
        BeanUtils.copyProperties(this, entity);
        return entity;
    }
}
