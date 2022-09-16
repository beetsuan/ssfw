package com.ssfw.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ssfw.common.framework.controller.BaseController;
import com.ssfw.common.framework.response.ResponseVo;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.ssfw.auth.entity.AuthUserRoleEntity;
import com.ssfw.auth.ro.AuthUserRoleCreateRo;
import com.ssfw.auth.ro.AuthUserRoleUpdateRo;
import com.ssfw.auth.service.AuthUserRoleService;
import com.ssfw.auth.vo.AuthUserRoleVo;
import javax.validation.Valid;

/**
 * 用户关联角色 前端控制器
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-16 15:06:12
 */
@RestController
@RequestMapping("/do/auth/grant")
@Slf4j
public class AuthUserRoleController extends BaseController<AuthUserRoleEntity> {

    /** 用户关联角色Service */
    private final AuthUserRoleService service;

    public AuthUserRoleController(AuthUserRoleService service) {
        super(service);
        this.service = service;
    }


    /**
	 * 获取用户关联角色
     * @param id 用户关联角色ID
	 */
    @GetMapping(value = "/get/{id}", produces= PRODUCE_UTF8_JSON)
    public ResponseVo get(@PathVariable Integer id){
        return ResponseVo.of(new AuthUserRoleVo().of(service.getById(id)));
    }

    /**
	 * 查询用户关联角色
	 * @param nickname 参数一
	 * @return json
	 */
    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST},produces= PRODUCE_UTF8_JSON)
    public ResponseVo list(String nickname){

        AuthUserRoleEntity entity = new AuthUserRoleEntity();
        LambdaQueryWrapper<AuthUserRoleEntity> wrapper = new LambdaQueryWrapper<>(entity);

        return super.pageQuery(wrapper,new AuthUserRoleVo());
    }


    /**
     *  新增用户关联角色
     * @param vo AuthUserRoleVo
     * @return json
     */
    @PostMapping(value = "/create")
    public ResponseVo create(@RequestBody @Valid AuthUserRoleCreateRo vo){

        return service.save(vo.toEntity()) ? ResponseVo.success(vo) : ResponseVo.failureToAdd();
    }


    /**
     *  修改用户关联角色
     * @param vo AuthUserRoleUpdateRo
     * @return json
     */
    @PostMapping(value = "/update")
    public ResponseVo update(@RequestBody @Valid AuthUserRoleUpdateRo vo){

        return service.updateById(vo.toEntity()) ? ResponseVo.success(vo) : ResponseVo.failureToUpdate();
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
