package com.ssfw.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ssfw.common.framework.controller.BaseController;
import com.ssfw.common.framework.response.ResponseVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.ssfw.auth.assembler.AuthResAssembler;
import com.ssfw.auth.controller.cmd.AuthResCreateCmd;
import com.ssfw.auth.controller.cmd.AuthResUpdateCmd;
import com.ssfw.auth.entity.AuthResEntity;
import com.ssfw.auth.service.AuthResService;
import javax.validation.Valid;

/**
 * 系统资源Controller控制器
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-18 17:13:57
 */
@RestController
@RequestMapping("/do/auth/res")
@Slf4j
public class AuthResController extends BaseController<AuthResEntity> {

    /** 系统资源Service */
    private final AuthResService service;
    /**
    * 对象转换
    */
    private final AuthResAssembler assembler;

    public AuthResController(AuthResService service) {
        super(service);
        this.service = service;
        this.assembler = AuthResAssembler.INSTANCE;
    }


    /**
	 * 根据ID获取数据
	 */
    @GetMapping(value = "/get/{id}", produces= PRODUCE_UTF8_JSON)
    public ResponseVo get(@PathVariable Integer id){
        return ResponseVo.ofData(assembler.entityToVO(service.getById(id)));
    }

    /**
	 * 查询系统资源
	 * @param name 参数一
	 * @return json
	 */
    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST},produces= PRODUCE_UTF8_JSON)
    public ResponseVo list(String name){

        LambdaQueryWrapper<AuthResEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(name)){
            //wrapper.like(AuthResEntity::getName, name);
        }
        return super.pageQuery(wrapper, assembler);
    }


    /**
     *  新增系统资源
     * @param command AuthResCreateCmd
     * @return json
     */
    @PostMapping(value = "/create")
    public ResponseVo create(@RequestBody @Valid AuthResCreateCmd command){

        AuthResEntity entity = assembler.cmdToEntity(command);
        return service.save(entity) ? ResponseVo.success(assembler.entityToVO(entity)) : ResponseVo.failureToAdd();

    }


    /**
     *  修改系统资源
     * @param command AuthResUpdateCmd
     * @return json
     */
    @PostMapping(value = "/update")
    public ResponseVo update(@RequestBody @Valid AuthResUpdateCmd command){

        AuthResEntity entity = assembler.cmdToEntity(command);
        return service.updateById(entity) ? ResponseVo.success(assembler.entityToVO(entity)) : ResponseVo.failureToUpdate();
    }

    /**
	 * 删除系统资源
     * @param id 系统资源ID
	 * @return json
	 */
    @PostMapping(value = "/delete/{id}", produces= PRODUCE_UTF8_JSON)
    public ResponseVo delete(@PathVariable Long id) {

        return service.removeById(new AuthResEntity(id))? ResponseVo.success() : ResponseVo.failureToUpdate();
    }


}
