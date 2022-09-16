package com.ssfw.auth.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssfw.auth.entity.UserEntity;
import com.ssfw.common.framework.vo.ValueObjectOfEntity;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author ssfw
 * @since 2022-09-14
 */
@Data
public class UserVo implements ValueObjectOfEntity<UserVo, UserEntity> {

    /**
     * 用户id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;

    /**
     * 登录名称
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 账号状态 valid有效 suspended挂起
     */
    private String useStatus;

    /**
     * 分组id
     */
    private Long groupId;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 租户id
     */
    private Integer tenantId;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    private LocalDateTime updateDate;

    @Override
    public UserVo of(UserEntity user){
        UserVo vo = new UserVo();
        BeanUtils.copyProperties(user, vo);
        return vo;
    }

    @Override
    public List<UserVo> of(Collection<UserEntity> users){

        final List<UserVo> list = new ArrayList<>(users.size());
        users.forEach(user -> list.add(of(user)));
        return list;
    }
}
