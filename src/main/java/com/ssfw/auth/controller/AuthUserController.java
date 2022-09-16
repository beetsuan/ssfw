package com.ssfw.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ssfw.auth.contant.UserConstants;
import com.ssfw.auth.entity.UserEntity;
import com.ssfw.auth.mapper.AuthUserMapper;
import com.ssfw.auth.service.AuthUserService;
import com.ssfw.auth.util.LoginUserUtil;
import com.ssfw.auth.ro.UserCreateRo;
import com.ssfw.auth.vo.UserVo;
import com.ssfw.auth.ro.UserUpdateRo;
import com.ssfw.common.framework.controller.BaseController;
import com.ssfw.common.framework.response.ResponseVo;
import com.ssfw.common.util.LocalDateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 用户 前端控制器
 *
 * @author ssfw
 * @since 2022-09-14
 */
@RestController
@RequestMapping("/do/auth/user")
public class AuthUserController extends BaseController<UserEntity> {

    private final AuthUserService service;

    private final AuthUserMapper authUserMapper;

    public AuthUserController(AuthUserService authUserService, AuthUserMapper authUserMapper) {

        super(authUserService);
        this.service = authUserService;
        this.authUserMapper = authUserMapper;
    }

    /**
     * 获取用户
     * @param id 用户id
     * @return ResponseVo
     */
    @GetMapping(value = "/get/{id}", produces= PRODUCE_UTF8_JSON)
    public ResponseVo get(@PathVariable Integer id){
        return ResponseVo.of(new UserVo().of(service.getById(id)));
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
        return super.pageQuery(wrapper,new UserVo());
    }

    /**
     * 查询用户 xml
     * @param vo 用户模板
     * @return
     */
    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseVo query(UserVo vo){
        vo.setTenantId(1);
        return ResponseVo.success(authUserMapper.queryAll(vo));
    }


    /**
     * 新增用户
     * @param vo 用户信息
     * @return
     */
    @PostMapping(value = "/add")
    public ResponseVo add(UserCreateRo vo){

        request.getSession(true).setAttribute(UserConstants.SESSION_TENANT_ID,1);
        UserEntity user = vo.toEntity();
        user.setUseStatus(UserEntity.STATUS_VALID);

        return service.save(user) ? ResponseVo.success(vo) : ResponseVo.failureToAdd();
    }

    /**
     * 修改用户
     * @param vo
     * @return
     */
    @PostMapping(value = "/update")
    public ResponseVo update(UserUpdateRo vo){

        request.getSession(true).setAttribute(UserConstants.SESSION_TENANT_ID,1);
        UserEntity user1 = service.getById(1);
        UserEntity user = vo.toEntity();
        if (service.updateById(user1)) {
            return ResponseVo.success(new UserVo().of(user1));
        }else{
            return ResponseVo.failureToUpdate();
        }
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
        user.setUpdateUser(LoginUserUtil.getLoginNickname());
        user.setUpdateDate(LocalDateUtil.nowLocalDateTime());
        service.updateById(user);
        if (service.updateById(user)) {
            return ResponseVo.success(new UserVo().of(user));
        }else{
            return ResponseVo.failure("修改失败");
        }
    }

}
