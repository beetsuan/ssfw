package com.ssfw.auth.assembler;

import com.ssfw.common.framework.assembler.AssemblerFacade;
import com.ssfw.auth.controller.cmd.AuthUserRoleCreateCmd;
import com.ssfw.auth.controller.cmd.AuthUserRoleUpdateCmd;
import com.ssfw.auth.controller.vo.AuthUserRoleVO;
import com.ssfw.auth.entity.AuthUserRoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 *  用户关联角色 对象转换
 * @author beets
 */
@Mapper(componentModel = "spring")
public interface AuthUserRoleAssembler extends AssemblerFacade<AuthUserRoleEntity> {

        AuthUserRoleAssembler INSTANCE = Mappers.getMapper(AuthUserRoleAssembler.class);

    /**
     * Entity 转换为 VO
     * @param entity Entity
     * @return VO
     */
    @Override
    AuthUserRoleVO entityToVO(AuthUserRoleEntity entity);

    /**
     * Entity 转换为 VO
     * @param entities Iterable
     * @return List
     */
    @Override
    List<AuthUserRoleVO> entityToVO(Iterable<AuthUserRoleEntity> entities);

    /**
     * Command 转换为 Entity
     * @param command CreateCmd
     * @return Entity
     */
    AuthUserRoleEntity cmdToEntity(AuthUserRoleCreateCmd command);

    /**
     * Command 转换为 Entity
     * @param command UpdateCmd
     * @return Entity
     */
    AuthUserRoleEntity cmdToEntity(AuthUserRoleUpdateCmd command);
}
