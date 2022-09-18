package com.ssfw.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ssfw.auth.assembler.AuthUserAssembler;
import com.ssfw.auth.contant.UserConstants;
import com.ssfw.auth.controller.cmd.UserCreateCmd;
import com.ssfw.auth.controller.cmd.UserUpdateCmd;
import com.ssfw.auth.entity.UserEntity;
import com.ssfw.auth.service.AuthUserService;
import com.ssfw.auth.util.LoginUserUtil;
import com.ssfw.common.framework.controller.BaseController;
import com.ssfw.common.framework.response.ResponseVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户 前端控制器
 *
 * @author ssfw
 * @since 2022-09-14
 */
@RestController
@RequestMapping("/do/auth/user")
public class AuthUserController extends BaseController<UserEntity> {

    /** 用户Service */
    private final AuthUserService service;

    /**
     * 对象转换
     */
    private final AuthUserAssembler assembler;

    public AuthUserController(AuthUserService service) {
        super(service);
        this.service = service;
        this.assembler = AuthUserAssembler.INSTANCE;
    }

    /**
     * 获取用户
     * @param id 用户id
     * @return ResponseVo
     */
    @GetMapping(value = "/get/{id}", produces= PRODUCE_UTF8_JSON)
    public ResponseVo get(@PathVariable Long id){
        return ResponseVo.ofData(assembler.entityToVO(service.getById(id)));
    }

    /**
     * 查询用户
     * @param username 用户名
     * @param nickname 用户昵称
     * @param useStatus 用户状态
     * @return
     */
    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseVo list(String username, String nickname, String useStatus){

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setUseStatus(useStatus);
        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>(user);
        if (StringUtils.isNotBlank(nickname)){
            wrapper.like(UserEntity::getNickname, nickname);
        }
        return super.pageQuery(wrapper, assembler);
    }


    /**
     *  新增用户
     * @param command AuthUserCreateCmd
     * @return json
     */
    @PostMapping(value = "/create")
    public ResponseVo create(@RequestBody @Valid UserCreateCmd command){

        request.getSession(true).setAttribute(UserConstants.SESSION_TENANT_ID,1);
        request.getSession(true).setAttribute(UserConstants.SESSION_USER_ID,1);
        request.getSession(true).setAttribute(UserConstants.SESSION_NICK_NAME,"dsf");


        UserEntity entity = assembler.cmdToEntity(command);
        entity.setUseStatus(UserEntity.STATUS_VALID);
        entity.setTenantId(LoginUserUtil.getTenantId());

        return service.save(entity) ? ResponseVo.success(assembler.entityToVO(entity)) : ResponseVo.failureToAdd();

    }

    /**
     *  修改用户
     * @param command AuthUserUpdateCmd
     * @return json
     */
    @PostMapping(value = "/update")
    public ResponseVo update(@RequestBody @Valid UserUpdateCmd command){

        UserEntity entity = assembler.cmdToEntity(command);
        return service.updateById(entity) ? ResponseVo.success(assembler.entityToVO(entity)) : ResponseVo.failureToUpdate();
    }

    /**
     * 修改用户密码
     * @param oldPw 原密码
     * @param newPw 新密码
     * @return
     */
    @PostMapping(value = "/updatePw")
    public ResponseVo updatePw(@PathVariable String oldPw,String newPw){
        return null;
    }

    /**
     * 调整用户状态
     * @param userId 用户ID
     * @param status 调整后的用户状态
     * @return
     */
    @PostMapping(value = "/updateStatus")
    public ResponseVo updateStatus(@RequestParam Long userId, @RequestParam String status){

        request.getSession(true).setAttribute(UserConstants.SESSION_TENANT_ID,1);
        UserEntity user = service.getById(userId);
        if (null == user){
            return ResponseVo.failure("目标用户id无效");
        }
        user.setUseStatus(status);
        service.updateById(user);
        if (service.updateById(user)) {
            return ResponseVo.success(assembler.entityToVO(user));
        }else{
            return ResponseVo.failure("修改失败");
        }
    }

}
