package com.ssfw.auth.assembler;

import com.ssfw.common.framework.assembler.AssemblerFacade;
import com.ssfw.auth.controller.cmd.AuthResCreateCmd;
import com.ssfw.auth.controller.cmd.AuthResUpdateCmd;
import com.ssfw.auth.controller.vo.AuthResVO;
import com.ssfw.auth.entity.AuthResEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 *  系统资源 对象转换
 * @author beets
 */
@Mapper(componentModel = "spring")
public interface AuthResAssembler extends AssemblerFacade<AuthResEntity> {

        AuthResAssembler INSTANCE = Mappers.getMapper(AuthResAssembler.class);

    /**
     * Entity 转换为 VO
     * @param entity Entity
     * @return VO
     */
    @Override
    AuthResVO entityToVO(AuthResEntity entity);

    /**
     * Entity 转换为 VO
     * @param entities Iterable
     * @return List
     */
    @Override
    List<AuthResVO> entityToVO(Iterable<AuthResEntity> entities);

    /**
     * Command 转换为 Entity
     * @param command CreateCmd
     * @return Entity
     */
    AuthResEntity cmdToEntity(AuthResCreateCmd command);

    /**
     * Command 转换为 Entity
     * @param command UpdateCmd
     * @return Entity
     */
    AuthResEntity cmdToEntity(AuthResUpdateCmd command);
}
