package com.ssfw.common.dict.assembler;

import com.ssfw.common.dict.dto.Dict;
import com.ssfw.common.framework.assembler.AssemblerFacade;
import com.ssfw.common.dict.controller.cmd.DictEntryCreateCmd;
import com.ssfw.common.dict.controller.cmd.DictEntryUpdateCmd;
import com.ssfw.common.dict.controller.vo.DictEntryVO;
import com.ssfw.common.dict.entity.DictEntryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 *  数据字典项 对象转换
 * @author beets
 */
@Mapper(componentModel = "spring")
public interface DictEntryAssembler extends AssemblerFacade<DictEntryEntity> {

        DictEntryAssembler INSTANCE = Mappers.getMapper(DictEntryAssembler.class);

    /**
     * Entity 转换为 VO
     * @param entity Entity
     * @return VO
     */
    @Override
    DictEntryVO entityToVO(DictEntryEntity entity);

    /**
     * Entity 转换为 VO
     * @param entities Iterable
     * @return List
     */
    @Override
    List<DictEntryVO> entityToVO(Iterable<DictEntryEntity> entities);

    /**
     * Command 转换为 Entity
     * @param command CreateCmd
     * @return Entity
     */
    DictEntryEntity cmdToEntity(DictEntryCreateCmd command);

    /**
     * Command 转换为 Entity
     * @param command UpdateCmd
     * @return Entity
     */
    DictEntryEntity cmdToEntity(DictEntryUpdateCmd command);

    /**
     * Dict 转换为 Entity
     * @param dict Dict
     * @return Entity
     */
    @Mapping(source = "id", target = "dictId")
    @Mapping(source = "text", target = "dictName")
    @Mapping(source = "enText", target = "dictEnName")
    @Mapping(source = "pid", target = "parentId")
    @Mapping(source = "no", target = "seqNo")
    @Mapping(source = "type", target = "dictTypeId")
    @Mapping(source = "status", target = "dictStatus")
    DictEntryEntity dtoToEntity(Dict dict);


    /**
     * Entity 转换为 Dict
     * @param entity DictEntryEntity
     * @return Dict
     */
    @Mapping(target = "id", source = "dictId")
    @Mapping(target = "text", source = "dictName")
    @Mapping(target = "enText", source = "dictEnName")
    @Mapping(target = "pid", source = "parentId")
    @Mapping(target = "no", source = "seqNo")
    @Mapping(target = "type", source = "dictTypeId")
    @Mapping(target = "status", source = "dictStatus")
    Dict entityToDict(DictEntryEntity entity);
}
