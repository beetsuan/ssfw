package com.ssfw.common.env.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ssfw.common.framework.controller.BaseController;
import com.ssfw.common.framework.response.ResponseVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.ssfw.common.env.assembler.EnvPropertiesAssembler;
import com.ssfw.common.env.controller.cmd.EnvPropertiesCreateCmd;
import com.ssfw.common.env.controller.cmd.EnvPropertiesUpdateCmd;
import com.ssfw.common.env.entity.EnvPropertiesEntity;
import com.ssfw.common.env.service.EnvPropertiesService;
import javax.validation.Valid;

/**
 * 环境配置Controller控制器
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-18 15:05:09
 */
@RestController
@RequestMapping("/do/common/env/config")
@Slf4j
public class EnvPropertiesController extends BaseController<EnvPropertiesEntity> {

    /** 环境配置Service */
    private final EnvPropertiesService service;
    /**
    * 对象转换
    */
    private final EnvPropertiesAssembler assembler;

    public EnvPropertiesController(EnvPropertiesService service) {
        super(service);
        this.service = service;
        this.assembler = EnvPropertiesAssembler.INSTANCE;
    }


    /**
	 * 根据ID获取数据
	 */
    @GetMapping(value = "/get/{id}", produces= PRODUCE_UTF8_JSON)
    public ResponseVo get(@PathVariable Integer id){
        return ResponseVo.ofData(assembler.entityToVO(service.getById(id)));
    }

    /**
	 * 查询环境配置
	 * @param key 配置键
     * @param title 标题
	 * @return json
	 */
    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST},produces= PRODUCE_UTF8_JSON)
    public ResponseVo list(String key,String title){

        LambdaQueryWrapper<EnvPropertiesEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(key)){
            wrapper.like(EnvPropertiesEntity::getPropKey,key);
        }
        if (StringUtils.isNotEmpty(title)){
            wrapper.like(EnvPropertiesEntity::getPropTitle,title);
        }

        return super.pageQuery(wrapper, assembler);
    }


    /**
     *  新增环境配置
     * @param command EnvPropertiesCreateCmd
     * @return json
     */
    @PostMapping(value = "/create")
    public ResponseVo create(@RequestBody @Valid EnvPropertiesCreateCmd command){

        EnvPropertiesEntity entity = assembler.cmdToEntity(command);
        return service.save(entity) ? ResponseVo.success(assembler.entityToVO(entity)) : ResponseVo.failureToAdd();

    }


    /**
     *  修改环境配置
     * @param command EnvPropertiesUpdateCmd
     * @return json
     */
    @PostMapping(value = "/update")
    public ResponseVo update(@RequestBody @Valid EnvPropertiesUpdateCmd command){

        EnvPropertiesEntity entity = assembler.cmdToEntity(command);
        return service.updateById(entity) ? ResponseVo.success(assembler.entityToVO(entity)) : ResponseVo.failureToUpdate();
    }

    /**
	 * 删除环境配置
     * @param id 环境配置ID
	 * @return json
	 */
    @PostMapping(value = "/delete/{id}", produces= PRODUCE_UTF8_JSON)
    public ResponseVo delete(@PathVariable String id) {

        return service.removeById(new EnvPropertiesEntity(id))? ResponseVo.success() : ResponseVo.failureToUpdate();
    }


}
