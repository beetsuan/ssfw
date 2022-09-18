package com.ssfw.auth.controller.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssfw.common.framework.vo.GmtVO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户 ValueObject
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-18 17:18:02
 */
@Data
@Accessors(chain = true)
public class UserVO extends GmtVO implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
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
     * 账号状态 valid有效 suspend挂起
     */
    private String useStatus;
    /**
     * 分组id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long groupId;

    /**
     * 小组名称
     */
    private String groupName;

}
