package com.ssfw.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ssfw.common.framework.controller.BaseController;
import com.ssfw.common.framework.response.ResponseVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.ssfw.auth.assembler.AuthUserRoleAssembler;
import com.ssfw.auth.controller.cmd.AuthUserRoleCreateCmd;
import com.ssfw.auth.controller.cmd.AuthUserRoleUpdateCmd;
import com.ssfw.auth.entity.AuthUserRoleEntity;
import com.ssfw.auth.service.AuthUserRoleService;
import javax.validation.Valid;

/**
 * 用户关联角色Controller控制器
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-18 17:15:38
 */
@RestController
@RequestMapping("/do/auth/grant")
@Slf4j
public class AuthUserRoleController extends BaseController<AuthUserRoleEntity> {

    /** 用户关联角色Service */
    private final AuthUserRoleService service;
    /**
    * 对象转换
    */
    private final AuthUserRoleAssembler assembler;

    public AuthUserRoleController(AuthUserRoleService service) {
        super(service);
        this.service = service;
        this.assembler = AuthUserRoleAssembler.INSTANCE;
    }


    /**
	 * 根据ID获取数据
	 */
    @GetMapping(value = "/get/{id}", produces= PRODUCE_UTF8_JSON)
    public ResponseVo get(@PathVariable Integer id){
        return ResponseVo.ofData(assembler.entityToVO(service.getById(id)));
    }

    /**
	 * 查询用户关联角色
	 * @param name 参数一
	 * @return json
	 */
    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST},produces= PRODUCE_UTF8_JSON)
    public ResponseVo list(String name){

        LambdaQueryWrapper<AuthUserRoleEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(name)){
            //wrapper.like(AuthUserRoleEntity::getName, name);
        }
        return super.pageQuery(wrapper, assembler);
    }


    /**
     *  新增用户关联角色
     * @param command AuthUserRoleCreateCmd
     * @return json
     */
    @PostMapping(value = "/create")
    public ResponseVo create(@RequestBody @Valid AuthUserRoleCreateCmd command){

        AuthUserRoleEntity entity = assembler.cmdToEntity(command);
        return service.save(entity) ? ResponseVo.success(assembler.entityToVO(entity)) : ResponseVo.failureToAdd();

    }


    /**
     *  修改用户关联角色
     * @param command AuthUserRoleUpdateCmd
     * @return json
     */
    @PostMapping(value = "/update")
    public ResponseVo update(@RequestBody @Valid AuthUserRoleUpdateCmd command){

        AuthUserRoleEntity entity = assembler.cmdToEntity(command);
        return service.updateById(entity) ? ResponseVo.success(assembler.entityToVO(entity)) : ResponseVo.failureToUpdate();
    }

    /**
	 * 删除用户关联角色
     * @param id 用户关联角色ID
	 * @return json
	 */
    @PostMapping(value = "/delete/{id}", produces= PRODUCE_UTF8_JSON)
    public ResponseVo delete(@PathVariable Long id) {

        return service.removeById(new AuthUserRoleEntity(id))? ResponseVo.success() : ResponseVo.failureToUpdate();
    }


}
