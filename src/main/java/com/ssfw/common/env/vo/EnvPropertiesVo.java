package com.ssfw.common.env.vo;

import com.ssfw.common.env.entity.EnvPropertiesEntity;
import com.ssfw.common.framework.vo.ValueObjectOfEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * 环境配置ValueObject
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-17 16:00:34
 */
@Setter
@Getter
public class EnvPropertiesVo implements ValueObjectOfEntity<EnvPropertiesVo,EnvPropertiesEntity> {

    private static final long serialVersionUID = 6401036929986058160L;

    /**
     * 配置键
     */
    private String propKey;
    /**
     * 配置说明
     */
    private String propTitle;
    /**
     * 配置值1
     */
    private String value01;
    /**
     * 配置值2
     */
    private String value02;
    /**
     * 配置值3
     */
    private String value03;
    /**
     * 配置值4
     */
    private String value04;
    /**
     * 配置值5
     */
    private String value05;
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
    public EnvPropertiesVo of(EnvPropertiesEntity entity){
        EnvPropertiesVo vo = new EnvPropertiesVo();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    @Override
    public List<EnvPropertiesVo> of(Collection<EnvPropertiesEntity> entities){

        final List<EnvPropertiesVo> list = new ArrayList<>(entities.size());
        entities.forEach(user -> list.add(of(user)));
        return list;
    }

}
