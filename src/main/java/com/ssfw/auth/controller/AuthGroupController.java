package com.ssfw.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ssfw.common.framework.controller.BaseController;
import com.ssfw.common.framework.response.ResponseVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.ssfw.auth.assembler.AuthGroupAssembler;
import com.ssfw.auth.controller.cmd.AuthGroupCreateCmd;
import com.ssfw.auth.controller.cmd.AuthGroupUpdateCmd;
import com.ssfw.auth.entity.AuthGroupEntity;
import com.ssfw.auth.service.AuthGroupService;
import javax.validation.Valid;

/**
 * 用户小组Controller控制器
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-18 17:08:01
 */
@RestController
@RequestMapping("/do/auth/group")
@Slf4j
public class AuthGroupController extends BaseController<AuthGroupEntity> {

    /** 用户小组Service */
    private final AuthGroupService service;
    /**
    * 对象转换
    */
    private final AuthGroupAssembler assembler;

    public AuthGroupController(AuthGroupService service) {
        super(service);
        this.service = service;
        this.assembler = AuthGroupAssembler.INSTANCE;
    }


    /**
	 * 根据ID获取数据
	 */
    @GetMapping(value = "/get/{id}", produces= PRODUCE_UTF8_JSON)
    public ResponseVo get(@PathVariable Integer id){
        return ResponseVo.ofData(assembler.entityToVO(service.getById(id)));
    }

    /**
	 * 查询用户小组
	 * @param name 参数一
	 * @return json
	 */
    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST},produces= PRODUCE_UTF8_JSON)
    public ResponseVo list(String name){

        LambdaQueryWrapper<AuthGroupEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(name)){
            wrapper.like(AuthGroupEntity::getGroupName, name);
        }
        return super.pageQuery(wrapper, assembler);
    }


    /**
     *  新增用户小组
     * @param command AuthGroupCreateCmd
     * @return json
     */
    @PostMapping(value = "/create")
    public ResponseVo create(@RequestBody @Valid AuthGroupCreateCmd command){

        AuthGroupEntity entity = assembler.cmdToEntity(command);
        return service.save(entity) ? ResponseVo.success(assembler.entityToVO(entity)) : ResponseVo.failureToAdd();

    }


    /**
     *  修改用户小组
     * @param command AuthGroupUpdateCmd
     * @return json
     */
    @PostMapping(value = "/update")
    public ResponseVo update(@RequestBody @Valid AuthGroupUpdateCmd command){

        AuthGroupEntity entity = assembler.cmdToEntity(command);
        return service.updateById(entity) ? ResponseVo.success(assembler.entityToVO(entity)) : ResponseVo.failureToUpdate();
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
