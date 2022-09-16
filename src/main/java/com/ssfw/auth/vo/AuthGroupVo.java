package com.ssfw.auth.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssfw.auth.entity.AuthGroupEntity;
import com.ssfw.common.framework.vo.ValueObjectOfEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * 用户小组ValueObject
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-15 17:38:29
 */
@Setter
@Getter
public class AuthGroupVo implements ValueObjectOfEntity<AuthGroupVo,AuthGroupEntity> {

    private static final long serialVersionUID = 2030783296473550787L;

    /**
     * 小组id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long groupId;
    /**
     * 小组名称
     */
    private String groupName;
    /**
     * 小组编号
     */
    private String groupCode;
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
    public AuthGroupVo of(AuthGroupEntity entity){
        AuthGroupVo vo = new AuthGroupVo();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    @Override
    public List<AuthGroupVo> of(Collection<AuthGroupEntity> entities){

        final List<AuthGroupVo> list = new ArrayList<>(entities.size());
        entities.forEach(user -> list.add(of(user)));
        return list;
    }

}
