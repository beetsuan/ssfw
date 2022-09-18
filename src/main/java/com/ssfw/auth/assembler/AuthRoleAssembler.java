package com.ssfw.auth.assembler;

import com.ssfw.common.framework.assembler.AssemblerFacade;
import com.ssfw.auth.controller.cmd.AuthRoleCreateCmd;
import com.ssfw.auth.controller.cmd.AuthRoleUpdateCmd;
import com.ssfw.auth.controller.vo.AuthRoleVO;
import com.ssfw.auth.entity.AuthRoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 *  用户角色 对象转换
 * @author beets
 */
@Mapper(componentModel = "spring")
public interface AuthRoleAssembler extends AssemblerFacade<AuthRoleEntity> {

        AuthRoleAssembler INSTANCE = Mappers.getMapper(AuthRoleAssembler.class);

    /**
     * Entity 转换为 VO
     * @param entity Entity
     * @return VO
     */
    @Override
    AuthRoleVO entityToVO(AuthRoleEntity entity);

    /**
     * Entity 转换为 VO
     * @param entities Iterable
     * @return List
     */
    @Override
    List<AuthRoleVO> entityToVO(Iterable<AuthRoleEntity> entities);

    /**
     * Command 转换为 Entity
     * @param command CreateCmd
     * @return Entity
     */
    AuthRoleEntity cmdToEntity(AuthRoleCreateCmd command);

    /**
     * Command 转换为 Entity
     * @param command UpdateCmd
     * @return Entity
     */
    AuthRoleEntity cmdToEntity(AuthRoleUpdateCmd command);
}
