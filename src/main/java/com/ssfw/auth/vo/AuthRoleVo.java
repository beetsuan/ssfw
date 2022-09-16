package com.ssfw.auth.vo;

import com.ssfw.auth.entity.AuthRoleEntity;
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
 * 用户角色ValueObject
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-16 15:09:19
 */
@Setter
@Getter
public class AuthRoleVo implements ValueObjectOfEntity<AuthRoleVo,AuthRoleEntity> {

    private static final long serialVersionUID = 9207358069800964972L;

    /**
     * 角色id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long roleId;
    /**
     * 角色code
     */
    private String roleCode;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 描述
     */
    private String roleDesc;
    /**
     * 租户id
     */
    private Integer tenantId;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 创建时间
     */
    private LocalDateTime createDate;
    /**
     * 修改人
     */
    private String updateUser;
    /**
     * 修改时间
     */
    private LocalDateTime updateDate;

    @Override
    public AuthRoleVo of(AuthRoleEntity entity){
        AuthRoleVo vo = new AuthRoleVo();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    @Override
    public List<AuthRoleVo> of(Collection<AuthRoleEntity> entities){

        final List<AuthRoleVo> list = new ArrayList<>(entities.size());
        entities.forEach(user -> list.add(of(user)));
        return list;
    }

}
