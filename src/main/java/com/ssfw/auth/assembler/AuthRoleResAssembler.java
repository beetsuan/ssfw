package com.ssfw.auth.assembler;

import com.ssfw.common.framework.assembler.AssemblerFacade;
import com.ssfw.auth.controller.cmd.AuthRoleResCreateCmd;
import com.ssfw.auth.controller.cmd.AuthRoleResUpdateCmd;
import com.ssfw.auth.controller.vo.AuthRoleResVO;
import com.ssfw.auth.entity.AuthRoleResEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 *  角色权限 对象转换
 * @author beets
 */
@Mapper(componentModel = "spring")
public interface AuthRoleResAssembler extends AssemblerFacade<AuthRoleResEntity> {

        AuthRoleResAssembler INSTANCE = Mappers.getMapper(AuthRoleResAssembler.class);

    /**
     * Entity 转换为 VO
     * @param entity Entity
     * @return VO
     */
    @Override
    AuthRoleResVO entityToVO(AuthRoleResEntity entity);

    /**
     * Entity 转换为 VO
     * @param entities Iterable
     * @return List
     */
    @Override
    List<AuthRoleResVO> entityToVO(Iterable<AuthRoleResEntity> entities);

    /**
     * Command 转换为 Entity
     * @param command CreateCmd
     * @return Entity
     */
    AuthRoleResEntity cmdToEntity(AuthRoleResCreateCmd command);

    /**
     * Command 转换为 Entity
     * @param command UpdateCmd
     * @return Entity
     */
    AuthRoleResEntity cmdToEntity(AuthRoleResUpdateCmd command);
}
