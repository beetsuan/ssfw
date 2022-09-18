package com.ssfw.common.dict.assembler;

import com.ssfw.common.framework.assembler.AssemblerFacade;
import com.ssfw.common.dict.controller.cmd.DictTypeCreateCmd;
import com.ssfw.common.dict.controller.cmd.DictTypeUpdateCmd;
import com.ssfw.common.dict.controller.vo.DictTypeVO;
import com.ssfw.common.dict.entity.DictTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 *  数据字典 对象转换
 * @author beets
 */
@Mapper(componentModel = "spring")
public interface DictTypeAssembler extends AssemblerFacade<DictTypeEntity> {

        DictTypeAssembler INSTANCE = Mappers.getMapper(DictTypeAssembler.class);

    /**
     * Entity 转换为 VO
     * @param entity Entity
     * @return VO
     */
    @Override
    DictTypeVO entityToVO(DictTypeEntity entity);

    /**
     * Entity 转换为 VO
     * @param entities Iterable
     * @return List
     */
    @Override
    List<DictTypeVO> entityToVO(Iterable<DictTypeEntity> entities);

    /**
     * Command 转换为 Entity
     * @param command CreateCmd
     * @return Entity
     */
    DictTypeEntity cmdToEntity(DictTypeCreateCmd command);

    /**
     * Command 转换为 Entity
     * @param command UpdateCmd
     * @return Entity
     */
    DictTypeEntity cmdToEntity(DictTypeUpdateCmd command);
}
