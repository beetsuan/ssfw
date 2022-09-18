package com.ssfw.common.env.assembler;

import com.ssfw.common.framework.assembler.AssemblerFacade;
import com.ssfw.common.env.controller.cmd.EnvPropertiesCreateCmd;
import com.ssfw.common.env.controller.cmd.EnvPropertiesUpdateCmd;
import com.ssfw.common.env.controller.vo.EnvPropertiesVO;
import com.ssfw.common.env.entity.EnvPropertiesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 *  环境配置 对象转换
 * @author beets
 */
@Mapper(componentModel = "spring")
public interface EnvPropertiesAssembler extends AssemblerFacade<EnvPropertiesEntity> {

    EnvPropertiesAssembler INSTANCE = Mappers.getMapper(EnvPropertiesAssembler.class);

    /**
     * Entity 转换为 VO
     * @param entity Entity
     * @return VO
     */
    @Override
    @Mapping(source = "propKey", target = "key")
    @Mapping(source = "propTitle", target = "title")
    EnvPropertiesVO entityToVO(EnvPropertiesEntity entity);

    /**
     * Entity 转换为 VO
     * @param entities Iterable
     * @return List
     */
    @Override
    List<EnvPropertiesVO> entityToVO(Iterable<EnvPropertiesEntity> entities);

    /**
     * Command 转换为 Entity
     * @param command CreateCmd
     * @return Entity
     */
    @Mapping(source = "key", target = "propKey")
    @Mapping(source = "title", target = "propTitle")
    EnvPropertiesEntity cmdToEntity(EnvPropertiesCreateCmd command);

    /**
     * Command 转换为 Entity
     * @param command UpdateCmd
     * @return Entity
     */
    @Mapping(source = "key", target = "propKey")
    @Mapping(source = "title", target = "propTitle")
    EnvPropertiesEntity cmdToEntity(EnvPropertiesUpdateCmd command);
}
