package com.ssfw.auth.vo;

import com.ssfw.auth.entity.AuthRoleResEntity;
import com.ssfw.common.framework.vo.ValueObjectOfEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.BeanUtils;

/**
 * 角色权限ValueObject
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-16 10:27:26
 */
@Setter
@Getter
public class AuthRoleResVo implements ValueObjectOfEntity<AuthRoleResVo,AuthRoleResEntity> {

    private static final long serialVersionUID = 9213506971326443267L;

    /**
     * 角色id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long roleId;
    /**
     * 资源id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long resId;
    /**
     * 授权日期
     */
    private LocalDateTime authDate;

    @Override
    public AuthRoleResVo of(AuthRoleResEntity entity){
        AuthRoleResVo vo = new AuthRoleResVo();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    @Override
    public List<AuthRoleResVo> of(Collection<AuthRoleResEntity> entities){

        final List<AuthRoleResVo> list = new ArrayList<>(entities.size());
        entities.forEach(user -> list.add(of(user)));
        return list;
    }

}
