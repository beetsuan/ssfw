package com.ssfw.auth.controller.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssfw.common.framework.vo.GmtVO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 系统资源 ValueObject
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-18 17:13:57
 */
@Data
@Accessors(chain = true)
public class AuthResVO extends GmtVO implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 资源id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long resId;
    /**
     * 资源名称
     */
    private String resName;
    /**
     * 资源编号
     */
    private String resCode;
    /**
     * 资源访问路径
     */
    private String resUrl;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 菜单图标
     */
    private String menuIcon;

}
