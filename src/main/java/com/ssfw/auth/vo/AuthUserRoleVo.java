package com.ssfw.auth.vo;

import com.ssfw.auth.entity.AuthUserRoleEntity;
import com.ssfw.common.framework.vo.ValueObjectOfEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.BeanUtils;

/**
 * 用户关联角色ValueObject
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-16 15:06:12
 */
@Setter
@Getter
public class AuthUserRoleVo implements ValueObjectOfEntity<AuthUserRoleVo,AuthUserRoleEntity> {

    private static final long serialVersionUID = 5128898565960357626L;

    /**
     * 用户id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;
    /**
     * 角色id
     */
    private Long roleId;

    @Override
    public AuthUserRoleVo of(AuthUserRoleEntity entity){
        AuthUserRoleVo vo = new AuthUserRoleVo();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    @Override
    public List<AuthUserRoleVo> of(Collection<AuthUserRoleEntity> entities){

        final List<AuthUserRoleVo> list = new ArrayList<>(entities.size());
        entities.forEach(user -> list.add(of(user)));
        return list;
    }

}
