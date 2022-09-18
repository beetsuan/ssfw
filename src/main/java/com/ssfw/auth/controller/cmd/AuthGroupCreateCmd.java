package com.ssfw.auth.controller.cmd;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
/**
 * 用户小组 CreateCommand
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-18 17:08:01
 */
@Setter
@Getter
public class AuthGroupCreateCmd implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 小组名称
     */
    @Length(min = 1, max = 100)
    @NotBlank
    private String groupName;

    /**
     * 小组编号
     */
    @Length(min = 1, max = 100)
    @NotBlank
    private String groupCode;

}
