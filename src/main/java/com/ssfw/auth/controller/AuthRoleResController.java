package com.ssfw.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ssfw.common.framework.controller.BaseController;
import com.ssfw.common.framework.response.ResponseVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.ssfw.auth.assembler.AuthRoleResAssembler;
import com.ssfw.auth.controller.cmd.AuthRoleResCreateCmd;
import com.ssfw.auth.controller.cmd.AuthRoleResUpdateCmd;
import com.ssfw.auth.entity.AuthRoleResEntity;
import com.ssfw.auth.service.AuthRoleResService;
import javax.validation.Valid;

/**
 * 角色权限Controller控制器
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-18 17:14:53
 */
@RestController
@RequestMapping("/do/auth/role_res")
@Slf4j
public class AuthRoleResController extends BaseController<AuthRoleResEntity> {

    /** 角色权限Service */
    private final AuthRoleResService service;
    /**
    * 对象转换
    */
    private final AuthRoleResAssembler assembler;

    public AuthRoleResController(AuthRoleResService service) {
        super(service);
        this.service = service;
        this.assembler = AuthRoleResAssembler.INSTANCE;
    }


    /**
	 * 根据ID获取数据
	 */
    @GetMapping(value = "/get/{id}", produces= PRODUCE_UTF8_JSON)
    public ResponseVo get(@PathVariable Integer id){
        return ResponseVo.ofData(assembler.entityToVO(service.getById(id)));
    }

    /**
	 * 查询角色权限
	 * @param name 参数一
	 * @return json
	 */
    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST},produces= PRODUCE_UTF8_JSON)
    public ResponseVo list(String name){

        LambdaQueryWrapper<AuthRoleResEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(name)){
            //wrapper.like(AuthRoleResEntity::getName, name);
        }
        return super.pageQuery(wrapper, assembler);
    }


    /**
     *  新增角色权限
     * @param command AuthRoleResCreateCmd
     * @return json
     */
    @PostMapping(value = "/create")
    public ResponseVo create(@RequestBody @Valid AuthRoleResCreateCmd command){

        AuthRoleResEntity entity = assembler.cmdToEntity(command);
        return service.save(entity) ? ResponseVo.success(assembler.entityToVO(entity)) : ResponseVo.failureToAdd();

    }


    /**
     *  修改角色权限
     * @param command AuthRoleResUpdateCmd
     * @return json
     */
    @PostMapping(value = "/update")
    public ResponseVo update(@RequestBody @Valid AuthRoleResUpdateCmd command){

        AuthRoleResEntity entity = assembler.cmdToEntity(command);
        return service.updateById(entity) ? ResponseVo.success(assembler.entityToVO(entity)) : ResponseVo.failureToUpdate();
    }

    /**
	 * 删除角色权限
     * @param id 角色权限ID
	 * @return json
	 */
    @PostMapping(value = "/delete/{id}", produces= PRODUCE_UTF8_JSON)
    public ResponseVo delete(@PathVariable Long id) {

        return service.removeById(new AuthRoleResEntity(id))? ResponseVo.success() : ResponseVo.failureToUpdate();
    }


}
