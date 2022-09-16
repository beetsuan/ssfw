package com.ssfw.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ssfw.auth.entity.AuthRoleEntity;
import com.ssfw.auth.entity.AuthUserRoleEntity;
import com.ssfw.auth.vo.AuthRoleVo;
import com.ssfw.auth.vo.AuthUserRoleVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户关联角色Mapper接口
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-16 15:06:12
 */
@Repository
public interface AuthUserRoleMapper extends BaseMapper<AuthUserRoleEntity> {

    /**
     * 通过用户id查询用户的所有角色
     * @param userId 用户id
     * @return AuthRoleEntity集合
     */
    List<AuthRoleEntity> listByUserId(Long userId);
}

