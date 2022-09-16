package com.ssfw.auth.bizcheck;


import com.ssfw.common.framework.check.BaseBizCheck;
import com.ssfw.auth.entity.AuthGroupEntity;
import com.ssfw.auth.service.AuthGroupService;
import com.ssfw.common.util.StringUtil;

/**
 * 用户小组check
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-15 17:19:00
*/
public class AuthGroupBizCheck extends BaseBizCheck<AuthGroupEntity> {

    /** 用户小组Service */
    private final AuthGroupService  service;


    public AuthGroupBizCheck(AuthGroupService service) {
        super();
        this.service =service;
    }

    @Override
    public AuthGroupBizCheck addable(AuthGroupEntity entity){

        String groupCode = entity.getGroupCode();
        if (StringUtil.isNotNull(groupCode) && !service.query().eq("group_code", groupCode).list().isEmpty()) {
            addError("小组编号重复");
        }
        return this;
    }
    @Override
    public AuthGroupBizCheck deletable(AuthGroupEntity entity){

        if (needBizCheck()) {
            required(entity.getGroupId(),"用户小组ID");
        }
        return this;
    }

    @Override
    public AuthGroupBizCheck updatable(AuthGroupEntity entity){

        if (needBizCheck()) {
            Long groupId = entity.getGroupId();
            required(groupId,"用户小组ID").throwIfError();
            String groupCode = entity.getGroupCode();
            if (StringUtil.isNotNull(groupCode) &&
                    !service.query().eq("group_code", groupCode).ne("group_id", groupId).list().isEmpty()) {
                addError("小组编号重复");
            }
        }
        return this;
    }
}
