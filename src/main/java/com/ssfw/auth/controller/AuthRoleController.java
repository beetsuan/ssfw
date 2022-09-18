package com.ssfw.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ssfw.common.framework.controller.BaseController;
import com.ssfw.common.framework.response.ResponseVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.ssfw.auth.assembler.AuthRoleAssembler;
import com.ssfw.auth.controller.cmd.AuthRoleCreateCmd;
import com.ssfw.auth.controller.cmd.AuthRoleUpdateCmd;
import com.ssfw.auth.entity.AuthRoleEntity;
import com.ssfw.auth.service.AuthRoleService;
import javax.validation.Valid;

/**
 * 用户角色Controller控制器
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-18 17:14:41
 */
@RestController
@RequestMapping("/do/auth/role")
@Slf4j
public class AuthRoleController extends BaseController<AuthRoleEntity> {

    /** 用户角色Service */
    private final AuthRoleService service;
    /**
    * 对象转换
    */
    private final AuthRoleAssembler assembler;

    public AuthRoleController(AuthRoleService service) {
        super(service);
        this.service = service;
        this.assembler = AuthRoleAssembler.INSTANCE;
    }


    /**
	 * 根据ID获取数据
	 */
    @GetMapping(value = "/get/{id}", produces= PRODUCE_UTF8_JSON)
    public ResponseVo get(@PathVariable Integer id){
        return ResponseVo.ofData(assembler.entityToVO(service.getById(id)));
    }

    /**
	 * 查询用户角色
	 * @param name 参数一
	 * @return json
	 */
    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST},produces= PRODUCE_UTF8_JSON)
    public ResponseVo list(String name){

        LambdaQueryWrapper<AuthRoleEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(name)){
            //wrapper.like(AuthRoleEntity::getName, name);
        }
        return super.pageQuery(wrapper, assembler);
    }


    /**
     *  新增用户角色
     * @param command AuthRoleCreateCmd
     * @return json
     */
    @PostMapping(value = "/create")
    public ResponseVo create(@RequestBody @Valid AuthRoleCreateCmd command){

        AuthRoleEntity entity = assembler.cmdToEntity(command);
        return service.save(entity) ? ResponseVo.success(assembler.entityToVO(entity)) : ResponseVo.failureToAdd();

    }


    /**
     *  修改用户角色
     * @param command AuthRoleUpdateCmd
     * @return json
     */
    @PostMapping(value = "/update")
    public ResponseVo update(@RequestBody @Valid AuthRoleUpdateCmd command){

        AuthRoleEntity entity = assembler.cmdToEntity(command);
        return service.updateById(entity) ? ResponseVo.success(assembler.entityToVO(entity)) : ResponseVo.failureToUpdate();
    }

    /**
	 * 删除用户角色
     * @param id 用户角色ID
	 * @return json
	 */
    @PostMapping(value = "/delete/{id}", produces= PRODUCE_UTF8_JSON)
    public ResponseVo delete(@PathVariable Long id) {

        return service.removeById(new AuthRoleEntity(id))? ResponseVo.success() : ResponseVo.failureToUpdate();
    }


}
