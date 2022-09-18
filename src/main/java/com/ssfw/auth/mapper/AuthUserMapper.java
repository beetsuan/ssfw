package com.ssfw.auth.mapper;

import com.ssfw.auth.controller.vo.UserVO;
import com.ssfw.auth.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author ssfw
 * @since 2022-09-14
 */
public interface AuthUserMapper extends BaseMapper<UserEntity> {

    /**
     * 查询所以用户
     * @param vo UserVO
     * @return UserVO
     */
    List<UserVO> queryAll(UserVO vo);
}
