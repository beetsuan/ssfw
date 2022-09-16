package com.ssfw.auth.bizcheck;


import com.ssfw.common.framework.check.BaseBizCheck;
import com.ssfw.auth.entity.AuthResEntity;
import com.ssfw.auth.service.AuthResService;
import com.ssfw.common.util.StringUtil;

/**
 * 系统资源check
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-15 17:01:05
*/
public class AuthResBizCheck extends BaseBizCheck<AuthResEntity> {

    /** 系统资源Service */
    private final AuthResService  service;


    public AuthResBizCheck(AuthResService service) {
        super();
        this.service =service;
    }

    @Override
    public AuthResBizCheck addable(AuthResEntity entity){

        String resCode = entity.getResCode();
        if (StringUtil.isNotNull(resCode) && !service.query().eq("res_code", resCode).list().isEmpty()) {
            addError("资源编号重复");
        }
        return this;
    }
    @Override
    public AuthResBizCheck deletable(AuthResEntity entity){

        if (needBizCheck()) {
            required(entity.getResId(),"系统资源ID");
        }
        return this;
    }

    @Override
    public AuthResBizCheck updatable(AuthResEntity entity){

        if (needBizCheck()) {
            Long resId = entity.getResId();
            required(resId,"系统资源ID").throwIfError();
            String resCode = entity.getResCode();
            if (StringUtil.isNotNull(resCode) &&
                    !service.query().eq("res_code", resCode).ne("res_id", resId).list().isEmpty()) {
                addError("资源编号重复");
            }
        }
        return this;
    }
}
