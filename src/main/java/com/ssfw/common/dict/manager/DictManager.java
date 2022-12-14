package com.ssfw.common.dict.manager;

/**
 * 数据字典管理器
 */

import com.ssfw.common.centext.SpringContextHolder;
import com.ssfw.common.dict.assembler.DictEntryAssembler;
import com.ssfw.common.dict.assembler.DictTypeAssembler;
import com.ssfw.common.dict.controller.cmd.DictEntryCreateCmd;
import com.ssfw.common.dict.controller.cmd.DictEntryUpdateCmd;
import com.ssfw.common.dict.controller.cmd.DictTypeCreateCmd;
import com.ssfw.common.dict.controller.cmd.DictTypeUpdateCmd;
import com.ssfw.common.dict.controller.vo.DictTypeVO;
import com.ssfw.common.dict.dto.Dict;
import com.ssfw.common.dict.entity.DictEntryEntity;
import com.ssfw.common.dict.entity.DictTypeEntity;
import com.ssfw.common.dict.service.DictEntryService;
import com.ssfw.common.dict.service.DictTypeService;
import com.ssfw.common.env.assembler.EnvPropertiesAssembler;
import com.ssfw.common.framework.dto.ResultDto;
import com.ssfw.common.util.StringUtil;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 数据字典管理器
 *
 * @author a
 * @date 2016/10/28 10:37
 * Description:
 */
public class DictManager implements Serializable {

    private static final long serialVersionUID = 6029410254956143884L;
    private final DictTypeService typeService;
    private final DictEntryService entryService;

    /**
     * 对象转换
     */
    private final DictEntryAssembler entryAssembler;
    /**
     * 对象转换
     */
    private final DictTypeAssembler typeAssembler;

    private DictManager() {
        this.typeService = SpringContextHolder.getBean(DictTypeService.class);
        this.entryService = SpringContextHolder.getBean(DictEntryService.class);


        this.typeAssembler = DictTypeAssembler.INSTANCE;
        this.entryAssembler = DictEntryAssembler.INSTANCE;
    }


    private volatile static DictManager instance;


    public static DictManager getInstance() {
        if (instance == null) {
            //双重检测同步延迟加载
            synchronized (DictManager.class){
                if (null == instance){
                    instance = new DictManager();
                }
            }
        }
        return instance;
    }

    /**
     * 获取指定的数据字典的所有字典项
     * @param dictTypeId 字典名称
     * @return 字典项合集
     */
    public List<Dict> getDictList(String dictTypeId){

        return entryService.lambdaQuery().eq(DictEntryEntity::getDictTypeId, dictTypeId).list()
                .stream().map(Dict::of).collect(Collectors.toList());
    }

    /**
     * 获取指定数据字典的指定字典项
     * @param dictTypeId 字典名称
     * @param dictId 字典键
     * @return 字典项
     */
    public Dict getDict(String dictTypeId,Number dictId){
        return getDict(dictTypeId,dictId+"");
    }

    /**
     * 获取指定数据字典的指定字典项
     * 不直接使用service.getEntry方法获取，因为getEntry有事务
     * @param dictTypeId 字典名称
     * @param dictId 字典键
     * @return 字典项
     */
    public Dict getDict(String dictTypeId,String dictId){

        if (StringUtil.isNull(dictTypeId)){
            return new Dict();
        }
        List<Dict> list = getDictList(dictTypeId);
        if (null==list) {
            return new Dict();
        }
        for (Dict dict : list) {
            if (dict.getId().equals(dictId)){
                return dict;
            }
        }
        return new Dict();
    }

    /**
     * 获取数据字典的指定字典项
     * 没有事务，适合循环体内使用
     * @param dictList 字典
     * @param dictId 字典键
     * @return 字典项
     */
    public Dict pickDict(List<Dict> dictList, Object dictId){

        if (null==dictList || null == dictId) {
            return new Dict();
        }
        String key;
        if (dictId instanceof String){
            key = dictId.toString();
        }else{
            key = dictId+"";
        }
        for (Dict dict : dictList) {
            if (dict.getId().equals(key)){
                return dict;
            }
        }
        return new Dict();
    }

    public boolean hasType(@NotNull String dictTypeId){
        return typeService.getById(dictTypeId) != null;
    }

    /**
     * 新增数据字典分类
     * @param type 字典分类
     * @return 失败信息
     */
    public ResultDto<DictTypeVO> addType(@NotNull DictTypeCreateCmd type){

        DictTypeEntity entity = typeAssembler.cmdToEntity(type);

        return typeService.save(entity) ? ResultDto.of(typeAssembler.entityToVO(entity)) : ResultDto.of("失败");
    }

    /**
     * 修改数据字典分类
     * @param type 字典分类
     * @return 失败信息
     */
    public ResultDto<DictTypeVO> updateType(@NotNull DictTypeUpdateCmd type){
        DictTypeEntity entity = typeAssembler.cmdToEntity(type);
        return typeService.updateById(entity) ? ResultDto.of(typeAssembler.entityToVO(entity)) : ResultDto.of("失败");
    }

    /**
     * 删除数据字典分类
     * @param typeId 字典ID
     * @return 失败信息
     */
    public ResultDto<Boolean> deleteType(@NotNull String typeId){
        return ResultDto.of(typeService.removeById(new DictTypeEntity(typeId)));
    }

    /**
     * 新增数据字典
     * @param dictTypeId 字典ID
     * @param dict 字典
     * @return 失败信息
     */
    public ResultDto<Dict> addDict(@NotNull String dictTypeId, @NotNull DictEntryCreateCmd dict){

        dict.setDictTypeId(dictTypeId);
        DictEntryEntity entity = entryAssembler.cmdToEntity(dict);

        return entryService.save(entity) ? ResultDto.of(entryAssembler.entityToDict(entity)) : ResultDto.of("失败");
    }

    /**
     * 新增数据字典
     * @param dictTypeId 字典ID
     * @param dict 字典
     * @return 失败信息
     */
    public ResultDto<Dict> updateDict(@NotNull String dictTypeId, @NotNull DictEntryUpdateCmd dict){

        dict.setDictTypeId(dictTypeId);
        DictEntryEntity entity = entryAssembler.cmdToEntity(dict);

        return entryService.updateById(entity) ? ResultDto.of(entryAssembler.entityToDict(entity)) : ResultDto.of("失败");
    }

    /**
     * 删除数据字典
     * @param dictTypeId 字典ID
     * @param dictId 字典ID
     * @return 失败信息
     */
    public ResultDto<Boolean> deleteDict(@NotNull String dictTypeId, @NotNull String dictId){

        DictEntryEntity entity = new DictEntryEntity(dictTypeId, dictId);
        return ResultDto.of(entryService.removeById(entity));
    }

    /**
     * 失效指定字典缓存
     * @param dictTypeId 字典ID
     */
    public void cacheEvict(String dictTypeId){

        typeService.cacheEvict(dictTypeId);
    }
}
