package com.ssfw.common.dict.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ssfw.common.framework.controller.BaseController;
import com.ssfw.common.framework.response.ResponseVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.ssfw.common.dict.assembler.DictTypeAssembler;
import com.ssfw.common.dict.controller.cmd.DictTypeCreateCmd;
import com.ssfw.common.dict.controller.cmd.DictTypeUpdateCmd;
import com.ssfw.common.dict.entity.DictTypeEntity;
import com.ssfw.common.dict.service.DictTypeService;
import javax.validation.Valid;

/**
 * 数据字典Controller控制器
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-18 16:29:10
 */
@RestController
@RequestMapping("/do/common/dict/type")
@Slf4j
public class DictTypeController extends BaseController<DictTypeEntity> {

    /** 数据字典Service */
    private final DictTypeService service;
    /**
    * 对象转换
    */
    private final DictTypeAssembler assembler;

    public DictTypeController(DictTypeService service) {
        super(service);
        this.service = service;
        this.assembler = DictTypeAssembler.INSTANCE;
    }


    /**
	 * 根据ID获取数据
	 */
    @GetMapping(value = "/get/{id}", produces= PRODUCE_UTF8_JSON)
    public ResponseVo get(@PathVariable Integer id){
        return ResponseVo.ofData(assembler.entityToVO(service.getById(id)));
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

        LambdaQueryWrapper<DictTypeEntity> wrapper = new LambdaQueryWrapper<>();
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
        return super.pageQuery(wrapper, assembler);
    }


    /**
     *  新增数据字典
     * @param command DictTypeCreateCmd
     * @return json
     */
    @PostMapping(value = "/create")
    public ResponseVo create(@RequestBody @Valid DictTypeCreateCmd command){

        DictTypeEntity entity = assembler.cmdToEntity(command);
        return service.save(entity) ? ResponseVo.success(assembler.entityToVO(entity)) : ResponseVo.failureToAdd();

    }


    /**
     *  修改数据字典
     * @param command DictTypeUpdateCmd
     * @return json
     */
    @PostMapping(value = "/update")
    public ResponseVo update(@RequestBody @Valid DictTypeUpdateCmd command){

        DictTypeEntity entity = assembler.cmdToEntity(command);
        return service.updateById(entity) ? ResponseVo.success(assembler.entityToVO(entity)) : ResponseVo.failureToUpdate();
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
