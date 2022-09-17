package com.ssfw.common.dict.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ssfw.common.env.entity.EnvPropertiesEntity;
import com.ssfw.common.framework.controller.BaseController;
import com.ssfw.common.framework.response.ResponseVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.ssfw.common.dict.entity.DictTypeEntity;
import com.ssfw.common.dict.ro.DictTypeCreateRo;
import com.ssfw.common.dict.ro.DictTypeUpdateRo;
import com.ssfw.common.dict.service.DictTypeService;
import com.ssfw.common.dict.vo.DictTypeVo;
import javax.validation.Valid;

/**
 * 数据字典Controller控制器
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-17 15:50:42
 */
@RestController
@RequestMapping("/do/common/dict/type")
@Slf4j
public class DictTypeController extends BaseController<DictTypeEntity> {

    /** 数据字典Service */
    private final DictTypeService service;

    public DictTypeController(DictTypeService service) {
        super(service);
        this.service = service;
    }


    /**
	 * 根据ID获取数据
	 */
    @GetMapping(value = "/get/{id}", produces= PRODUCE_UTF8_JSON)
    public ResponseVo get(@PathVariable Integer id){
        return ResponseVo.of(new DictTypeVo().of(service.getById(id)));
    }

    /**
	 * 查询数据字典
     * @param id 字典id
     * @param name 字典名称
     * @param status 字典状态
     * @param parentId 字典父类id
	 * @return json
	 */
    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST},produces= PRODUCE_UTF8_JSON)
    public ResponseVo list(String id,String name,String status,String parentId){

        DictTypeEntity entity = new DictTypeEntity();
        LambdaQueryWrapper<DictTypeEntity> wrapper = new LambdaQueryWrapper<>(entity);
        if (StringUtils.isNotEmpty(id)){
            wrapper.like(DictTypeEntity::getDictTypeId,id);
        }
        if (StringUtils.isNotEmpty(name)){
            wrapper.like(DictTypeEntity::getDictTypeName,name);
        }
        if (StringUtils.isNotEmpty(status)){
            wrapper.eq(DictTypeEntity::getDictStatus,status);
        }
        if (StringUtils.isNotEmpty(parentId)){
            wrapper.eq(DictTypeEntity::getParentId,parentId);
        }
        return super.pageQuery(wrapper,new DictTypeVo());
    }


    /**
     *  新增数据字典
     * @param vo DictTypeVo
     * @return json
     */
    @PostMapping(value = "/create")
    public ResponseVo create(@RequestBody @Valid DictTypeCreateRo vo){

        return service.save(vo.toEntity()) ? ResponseVo.success(vo) : ResponseVo.failureToAdd();
    }


    /**
     *  修改数据字典
     * @param vo DictTypeUpdateRo
     * @return json
     */
    @PostMapping(value = "/update")
    public ResponseVo update(@RequestBody @Valid DictTypeUpdateRo vo){

        return service.updateById(vo.toEntity()) ? ResponseVo.success(vo) : ResponseVo.failureToUpdate();
    }

    /**
	 * 删除数据字典
     * @param id 数据字典ID
	 * @return json
	 */
    @PostMapping(value = "/delete/{id}", produces= PRODUCE_UTF8_JSON)
    public ResponseVo delete(@PathVariable String id) {

        return service.removeById(new DictTypeEntity(id))? ResponseVo.success() : ResponseVo.failureToUpdate();
    }


}
