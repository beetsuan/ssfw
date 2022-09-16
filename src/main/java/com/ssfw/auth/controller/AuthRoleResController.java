package com.ssfw.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ssfw.common.framework.controller.BaseController;
import com.ssfw.common.framework.response.ResponseVo;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.ssfw.auth.entity.AuthRoleResEntity;
import com.ssfw.auth.ro.AuthRoleResCreateRo;
import com.ssfw.auth.ro.AuthRoleResUpdateRo;
import com.ssfw.auth.service.AuthRoleResService;
import com.ssfw.auth.vo.AuthRoleResVo;
import javax.validation.Valid;

/**
 * 角色权限 前端控制器
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-16 10:27:26
 */
@RestController
@RequestMapping("/do/auth/role/res")
@Slf4j
public class AuthRoleResController extends BaseController<AuthRoleResEntity> {

    /** 角色权限Service */
    private final AuthRoleResService service;

    public AuthRoleResController(AuthRoleResService service) {
        super(service);
        this.service = service;
    }


    /**
	 * 根据ID获取数据
	 */
    @GetMapping(value = "/get/{id}", produces= PRODUCE_UTF8_JSON)
    public ResponseVo get(@PathVariable Integer id){
        return ResponseVo.of(new AuthRoleResVo().of(service.getById(id)));
    }

    /**
	 * 查询角色权限
	 * @param nickname 参数一
	 * @return json
	 */
    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST},produces= PRODUCE_UTF8_JSON)
    public ResponseVo list(String nickname){

        AuthRoleResEntity entity = new AuthRoleResEntity();
        LambdaQueryWrapper<AuthRoleResEntity> wrapper = new LambdaQueryWrapper<>(entity);

        return super.pageQuery(wrapper,new AuthRoleResVo());
    }


    /**
     *  新增角色权限
     * @param vo AuthRoleResVo
     * @return json
     */
    @PostMapping(value = "/create")
    public ResponseVo create(@RequestBody @Valid AuthRoleResCreateRo vo){

        return service.save(vo.toEntity()) ? ResponseVo.success(vo) : ResponseVo.failureToAdd();
    }


    /**
     *  修改角色权限
     * @param vo AuthRoleResUpdateRo
     * @return json
     */
    @PostMapping(value = "/update")
    public ResponseVo update(@RequestBody @Valid AuthRoleResUpdateRo vo){

        return service.updateById(vo.toEntity()) ? ResponseVo.success(vo) : ResponseVo.failureToUpdate();
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
