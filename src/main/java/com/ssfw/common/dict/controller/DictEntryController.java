package com.ssfw.common.dict.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ssfw.common.framework.controller.BaseController;
import com.ssfw.common.framework.response.ResponseVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.ssfw.common.dict.assembler.DictEntryAssembler;
import com.ssfw.common.dict.controller.cmd.DictEntryCreateCmd;
import com.ssfw.common.dict.controller.cmd.DictEntryUpdateCmd;
import com.ssfw.common.dict.entity.DictEntryEntity;
import com.ssfw.common.dict.service.DictEntryService;
import javax.validation.Valid;

/**
 * 数据字典项Controller控制器
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-18 16:30:56
 */
@RestController
@RequestMapping("/do/common/dict/entry")
@Slf4j
public class DictEntryController extends BaseController<DictEntryEntity> {

    /** 数据字典项Service */
    private final DictEntryService service;
    /**
    * 对象转换
    */
    private final DictEntryAssembler assembler;

    public DictEntryController(DictEntryService service) {
        super(service);
        this.service = service;
        this.assembler = DictEntryAssembler.INSTANCE;
    }


    /**
     * @param typeId 数据字典ID
     * @param id 数据字典项ID
	 * 根据ID获取数据
	 */
    @GetMapping(value = "/get/{typeId}/{id}", produces= PRODUCE_UTF8_JSON)
    public ResponseVo get(@PathVariable String typeId,@PathVariable String id){
        return ResponseVo.ofData(assembler.entityToVO(service.get(typeId, id)));
    }

    /**
	 * 查询数据字典项
     * @param typeId 数据字典ID
     * @param id 数据字典项ID
     * @param name 字典项名称
     * @param status 字典项状态
	 * @return json
	 */
    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST},produces= PRODUCE_UTF8_JSON)
    public ResponseVo list(String typeId, String id,String name,String status){

        LambdaQueryWrapper<DictEntryEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(typeId)){
            wrapper.eq(DictEntryEntity::getDictTypeId,typeId);
        }
        if (StringUtils.isNotEmpty(id)){
            wrapper.like(DictEntryEntity::getDictId,id);
        }
        if (StringUtils.isNotEmpty(name)){
            wrapper.like(DictEntryEntity::getDictName,name);
        }
        if (StringUtils.isNotEmpty(status)){
            wrapper.eq(DictEntryEntity::getDictStatus,status);
        }
        return super.pageQuery(wrapper,assembler);
    }


    /**
     *  新增数据字典项
     * @param command DictEntryCreateCmd
     * @return json
     */
    @PostMapping(value = "/create")
    public ResponseVo create(@RequestBody @Valid DictEntryCreateCmd command){

        DictEntryEntity entity = assembler.cmdToEntity(command);
        return service.save(entity) ? ResponseVo.success(assembler.entityToVO(entity)) : ResponseVo.failureToAdd();

    }


    /**
     *  修改数据字典项
     * @param command DictEntryUpdateCmd
     * @return json
     */
    @PostMapping(value = "/update")
    public ResponseVo update(@RequestBody @Valid DictEntryUpdateCmd command){

        DictEntryEntity entity = assembler.cmdToEntity(command);
        return service.updateById(entity) ? ResponseVo.success(assembler.entityToVO(entity)) : ResponseVo.failureToUpdate();
    }

    /**
	 * 删除数据字典项
     * @param id 数据字典项ID
	 * @return json
	 */
    @PostMapping(value = "/delete/{typeId}/{id}", produces= PRODUCE_UTF8_JSON)
    public ResponseVo delete(@PathVariable String typeId,@PathVariable String id) {

        return service.removeById(new DictEntryEntity(typeId, id))? ResponseVo.success() : ResponseVo.failureToUpdate();
    }


}
