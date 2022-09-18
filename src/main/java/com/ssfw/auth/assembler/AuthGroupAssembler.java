package com.ssfw.auth.assembler;

import com.ssfw.common.framework.assembler.AssemblerFacade;
import com.ssfw.auth.controller.cmd.AuthGroupCreateCmd;
import com.ssfw.auth.controller.cmd.AuthGroupUpdateCmd;
import com.ssfw.auth.controller.vo.AuthGroupVO;
import com.ssfw.auth.entity.AuthGroupEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 *  用户小组 对象转换
 * @author beets
 */
@Mapper(componentModel = "spring")
public interface AuthGroupAssembler extends AssemblerFacade<AuthGroupEntity> {

        AuthGroupAssembler INSTANCE = Mappers.getMapper(AuthGroupAssembler.class);

    /**
     * Entity 转换为 VO
     * @param entity Entity
     * @return VO
     */
    @Override
    AuthGroupVO entityToVO(AuthGroupEntity entity);

    /**
     * Entity 转换为 VO
     * @param entities Iterable
     * @return List
     */
    @Override
    List<AuthGroupVO> entityToVO(Iterable<AuthGroupEntity> entities);

    /**
     * Command 转换为 Entity
     * @param command CreateCmd
     * @return Entity
     */
    AuthGroupEntity cmdToEntity(AuthGroupCreateCmd command);

    /**
     * Command 转换为 Entity
     * @param command UpdateCmd
     * @return Entity
     */
    AuthGroupEntity cmdToEntity(AuthGroupUpdateCmd command);
}
