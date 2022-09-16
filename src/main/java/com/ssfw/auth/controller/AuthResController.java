package com.ssfw.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ssfw.common.framework.controller.BaseController;
import com.ssfw.common.framework.response.ResponseVo;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.ssfw.auth.entity.AuthResEntity;
import com.ssfw.auth.ro.AuthResCreateRo;
import com.ssfw.auth.ro.AuthResUpdateRo;
import com.ssfw.auth.service.AuthResService;
import com.ssfw.auth.vo.AuthResVo;
import javax.validation.Valid;

/**
 * 系统资源 前端控制器
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-15 17:01:05
 */
@RestController
@RequestMapping("/do/auth/res")
@Slf4j
public class AuthResController extends BaseController<AuthResEntity> {

    /** 系统资源Service */
    private final AuthResService service;

    public AuthResController(AuthResService service) {
        super(service);
        this.service = service;
    }


    /**
     * 获取资源
     * @param id 资源id
     * @return ResponseVo
     */
    @GetMapping(value = "/get/{id}", produces= PRODUCE_UTF8_JSON)
    public ResponseVo get(@PathVariable Integer id){
        return ResponseVo.of(new AuthResVo().of(service.getById(id)));
    }

    /**
	 * 查询系统资源
	 * @param nickname 参数一
	 * @return json
	 */
    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST},produces= PRODUCE_UTF8_JSON)
    public ResponseVo list(String nickname){

        AuthResEntity entity = new AuthResEntity();
        LambdaQueryWrapper<AuthResEntity> wrapper = new LambdaQueryWrapper<>(entity);

        return super.pageQuery(wrapper,new AuthResVo());
    }


    /**
     *  新增系统资源
     * @param vo AuthResVo
     * @return json
     */
    @PostMapping(value = "/create")
    public ResponseVo create(@RequestBody @Valid AuthResCreateRo vo){

        return service.save(vo.toEntity())? ResponseVo.success(vo) : ResponseVo.failureToAdd();
    }


    /**
     *  修改系统资源
     * @param vo AuthResUpdateRo
     * @return json
     */
    @PostMapping(value = "/update")
    public ResponseVo update(@RequestBody @Valid AuthResUpdateRo vo){

        return service.updateById(vo.toEntity())? ResponseVo.success(vo) : ResponseVo.failureToUpdate();
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
