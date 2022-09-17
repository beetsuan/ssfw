package com.ssfw.common.dict.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ssfw.common.dict.entity.DictTypeEntity;
import com.ssfw.common.framework.controller.BaseController;
import com.ssfw.common.framework.response.ResponseVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.ssfw.common.dict.entity.DictEntryEntity;
import com.ssfw.common.dict.ro.DictEntryCreateRo;
import com.ssfw.common.dict.ro.DictEntryUpdateRo;
import com.ssfw.common.dict.service.DictEntryService;
import com.ssfw.common.dict.vo.DictEntryVo;
import javax.validation.Valid;

/**
 * 数据字典项Controller控制器
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-17 15:48:49
 */
@RestController
@RequestMapping("/do/common/dict/entry")
@Slf4j
public class DictEntryController extends BaseController<DictEntryEntity> {

    /** 数据字典项Service */
    private final DictEntryService service;

    public DictEntryController(DictEntryService service) {
        super(service);
        this.service = service;
    }


    /**
     * @param typeId 数据字典ID
     * @param id 数据字典项ID
	 * 根据ID获取数据
	 */
    @GetMapping(value = "/get/{typeId}/{id}", produces= PRODUCE_UTF8_JSON)
    public ResponseVo get(@PathVariable String typeId,@PathVariable String id){
        return ResponseVo.of(new DictEntryVo().of(service.get(typeId, id)));
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

        DictEntryEntity entity = new DictEntryEntity();
        LambdaQueryWrapper<DictEntryEntity> wrapper = new LambdaQueryWrapper<>(entity);
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
        return super.pageQuery(wrapper,new DictEntryVo());
    }


    /**
     *  新增数据字典项
     * @param vo DictEntryVo
     * @return json
     */
    @PostMapping(value = "/create")
    public ResponseVo create(@RequestBody @Valid DictEntryCreateRo vo){

        return service.save(vo.toEntity()) ? ResponseVo.success(vo) : ResponseVo.failureToAdd();
    }


    /**
     *  修改数据字典项
     * @param vo DictEntryUpdateRo
     * @return json
     */
    @PostMapping(value = "/update")
    public ResponseVo update(@RequestBody @Valid DictEntryUpdateRo vo){

        return service.updateById(vo.toEntity()) ? ResponseVo.success(vo) : ResponseVo.failureToUpdate();
    }

    /**
	 * 删除数据字典项
     * @param typeId 数据字典ID
     * @param id 数据字典项ID
	 * @return json
	 */
    @PostMapping(value = "/delete/{typeId}/{id}", produces= PRODUCE_UTF8_JSON)
    public ResponseVo delete(@PathVariable String typeId,@PathVariable String id) {

        return service.removeById(new DictEntryEntity(typeId, id))? ResponseVo.success() : ResponseVo.failureToUpdate();
    }


}
