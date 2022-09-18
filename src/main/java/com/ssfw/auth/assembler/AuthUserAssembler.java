package com.ssfw.auth.assembler;

import com.ssfw.common.framework.assembler.AssemblerFacade;
import com.ssfw.auth.controller.cmd.UserCreateCmd;
import com.ssfw.auth.controller.cmd.UserUpdateCmd;
import com.ssfw.auth.controller.vo.UserVO;
import com.ssfw.auth.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 *  用户 对象转换
 * @author beets
 */
@Mapper(componentModel = "spring")
public interface AuthUserAssembler extends AssemblerFacade<UserEntity> {

        AuthUserAssembler INSTANCE = Mappers.getMapper(AuthUserAssembler.class);

    /**
     * Entity 转换为 VO
     * @param entity Entity
     * @return VO
     */
    @Override
    UserVO entityToVO(UserEntity entity);

    /**
     * Entity 转换为 VO
     * @param entities Iterable
     * @return List
     */
    @Override
    List<UserVO> entityToVO(Iterable<UserEntity> entities);

    /**
     * Command 转换为 Entity
     * @param command CreateCmd
     * @return Entity
     */
    UserEntity cmdToEntity(UserCreateCmd command);

    /**
     * Command 转换为 Entity
     * @param command UpdateCmd
     * @return Entity
     */
    UserEntity cmdToEntity(UserUpdateCmd command);
}
