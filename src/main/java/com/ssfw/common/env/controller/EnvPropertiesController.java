package com.ssfw.common.env.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ssfw.common.framework.controller.BaseController;
import com.ssfw.common.framework.response.ResponseVo;
import com.ssfw.common.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.ssfw.common.env.entity.EnvPropertiesEntity;
import com.ssfw.common.env.ro.EnvPropertiesCreateRo;
import com.ssfw.common.env.ro.EnvPropertiesUpdateRo;
import com.ssfw.common.env.service.EnvPropertiesService;
import com.ssfw.common.env.vo.EnvPropertiesVo;
import javax.validation.Valid;

/**
 * 环境配置Controller控制器
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-17 16:00:34
 */
@RestController
@RequestMapping("/do/common/env/config")
@Slf4j
public class EnvPropertiesController extends BaseController<EnvPropertiesEntity> {

    /** 环境配置Service */
    private final EnvPropertiesService service;

    public EnvPropertiesController(EnvPropertiesService service) {
        super(service);
        this.service = service;
    }


    /**
	 * 根据ID获取数据
	 */
    @GetMapping(value = "/get/{id}", produces= PRODUCE_UTF8_JSON)
    public ResponseVo get(@PathVariable Integer id){
        return ResponseVo.of(new EnvPropertiesVo().of(service.getById(id)));
    }

    /**
	 * 查询环境配置
	 * @param key 配置键
     * @param title 标题
	 * @return json
	 */
    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST},produces= PRODUCE_UTF8_JSON)
    public ResponseVo list(String key,String title){

        EnvPropertiesEntity entity = new EnvPropertiesEntity();
        LambdaQueryWrapper<EnvPropertiesEntity> wrapper = new LambdaQueryWrapper<>(entity);
        if (StringUtils.isNotEmpty(key)){
            wrapper.like(EnvPropertiesEntity::getKey,key);
        }
        if (StringUtils.isNotEmpty(title)){
            wrapper.like(EnvPropertiesEntity::getTitle,title);
        }
        return super.pageQuery(wrapper,new EnvPropertiesVo());
    }


    /**
     *  新增环境配置
     * @param vo EnvPropertiesVo
     * @return json
     */
    @PostMapping(value = "/create")
    public ResponseVo create(@RequestBody @Valid EnvPropertiesCreateRo vo){

        return service.save(vo.toEntity()) ? ResponseVo.success(vo) : ResponseVo.failureToAdd();
    }


    /**
     *  修改环境配置
     * @param vo EnvPropertiesUpdateRo
     * @return json
     */
    @PostMapping(value = "/update")
    public ResponseVo update(@RequestBody @Valid EnvPropertiesUpdateRo vo){

        return service.updateById(vo.toEntity()) ? ResponseVo.success(vo) : ResponseVo.failureToUpdate();
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
