package com.ssfw.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ssfw.auth.contant.UserConstants;
import com.ssfw.auth.util.LoginUserUtil;
import com.ssfw.common.framework.controller.BaseController;
import com.ssfw.common.framework.response.ResponseVo;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.ssfw.auth.entity.AuthGroupEntity;
import com.ssfw.auth.ro.AuthGroupCreateRo;
import com.ssfw.auth.ro.AuthGroupUpdateRo;
import com.ssfw.auth.service.AuthGroupService;
import com.ssfw.auth.vo.AuthGroupVo;
import javax.validation.Valid;

/**
 * 用户小组 前端控制器
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-15 17:19:00
 */
@RestController
@RequestMapping("/do/auth/group")
@Slf4j
public class AuthGroupController extends BaseController<AuthGroupEntity> {

    /** 用户小组Service */
    private final AuthGroupService service;

    public AuthGroupController(AuthGroupService service) {
        super(service);
        this.service = service;
    }


    /**
     * 获取用户小组
     * @param id 用户小组ID
     * @return ResponseVo
     */
    @GetMapping(value = "/get/{id}", produces= PRODUCE_UTF8_JSON)
    public ResponseVo get(@PathVariable Integer id){
        return ResponseVo.of(new AuthGroupVo().of(service.getById(id)));
    }

    /**
	 * 查询用户小组
	 * @param nickname 参数一
	 * @return json
	 */
    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST},produces= PRODUCE_UTF8_JSON)
    public ResponseVo list(String nickname){

        AuthGroupEntity entity = new AuthGroupEntity();
        LambdaQueryWrapper<AuthGroupEntity> wrapper = new LambdaQueryWrapper<>(entity);

        return super.pageQuery(wrapper,new AuthGroupVo());
    }


    /**
     *  新增用户小组
     * @param vo AuthGroupVo
     * @return json
     */
    @PostMapping(value = "/create")
    public ResponseVo create(@RequestBody @Valid AuthGroupCreateRo vo){

        request.getSession(true).setAttribute(UserConstants.SESSION_TENANT_ID,1);
        return service.save(vo.toEntity().setTenantId(1)) ? ResponseVo.success(vo) : ResponseVo.failureToAdd();
    }


    /**
     *  修改用户小组
     * @param vo AuthGroupUpdateRo
     * @return json
     */
    @PostMapping(value = "/update")
    public ResponseVo update(@RequestBody @Valid AuthGroupUpdateRo vo){

        request.getSession(true).setAttribute(UserConstants.SESSION_TENANT_ID,1);
        return service.updateById(vo.toEntity()) ? ResponseVo.success(vo) : ResponseVo.failureToUpdate();
    }

    /**
	 * 删除用户小组
     * @param id 用户小组ID
	 * @return json
	 */
    @PostMapping(value = "/delete/{id}", produces= PRODUCE_UTF8_JSON)
    public ResponseVo delete(@PathVariable Long id) {

        return service.removeById(new AuthGroupEntity(id))? ResponseVo.success() : ResponseVo.failureToUpdate();
    }


}
