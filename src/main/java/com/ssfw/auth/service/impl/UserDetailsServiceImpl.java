package com.ssfw.auth.service.impl;

import com.ssfw.auth.entity.AuthRoleEntity;
import com.ssfw.auth.entity.UserEntity;
import com.ssfw.auth.mapper.AuthUserRoleMapper;
import com.ssfw.auth.service.AuthUserService;
import com.ssfw.auth.dto.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author a
 * @date 2022/9/16
 * @since 2.7.3
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AuthUserService authUserService;
    private final AuthUserRoleMapper authUserRoleMapper;

    public UserDetailsServiceImpl(AuthUserService authUserService, AuthUserRoleMapper authUserRoleMapper) {
        this.authUserService = authUserService;
        this.authUserRoleMapper = authUserRoleMapper;
    }

    /**
     * 重写loadUserByUsername方法获得用户
     * @param username 用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<UserEntity> list = authUserService.lambdaQuery().eq(UserEntity::getUsername, username).eq(UserEntity::getTenantId,1).list();
        if (list.isEmpty()){
            throw new UsernameNotFoundException("用户名不存在");
        }
        UserEntity user = list.get(0);
        List<AuthRoleEntity> entities = authUserRoleMapper.listByUserId(user.getUserId());
        return new CustomUserDetails(user, entities.stream().map(AuthRoleEntity::getRoleCode).collect(Collectors.toList()));
    }
}
