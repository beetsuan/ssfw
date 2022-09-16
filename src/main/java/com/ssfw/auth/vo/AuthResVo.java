package com.ssfw.auth.vo;

import com.ssfw.auth.entity.AuthResEntity;
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
 * 系统资源ValueObject
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-16 10:21:00
 */
@Setter
@Getter
public class AuthResVo implements ValueObjectOfEntity<AuthResVo,AuthResEntity> {

    private static final long serialVersionUID = 8099341008525017995L;

    /**
     * 资源id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long resId;
    /**
     * 资源名称
     */
    private String resName;
    /**
     * 资源编号
     */
    private String resCode;
    /**
     * 资源访问路径
     */
    private String resUrl;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 菜单图标
     */
    private String menuIcon;
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
    public AuthResVo of(AuthResEntity entity){
        AuthResVo vo = new AuthResVo();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    @Override
    public List<AuthResVo> of(Collection<AuthResEntity> entities){

        final List<AuthResVo> list = new ArrayList<>(entities.size());
        entities.forEach(user -> list.add(of(user)));
        return list;
    }

}
