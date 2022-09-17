package com.ssfw.common.env.bizcheck;


import com.ssfw.common.framework.check.BaseBizCheck;
import com.ssfw.common.env.entity.EnvPropertiesEntity;
import com.ssfw.common.env.service.EnvPropertiesService;

/**
 * 环境配置 业务验证
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-17 16:00:34
*/
public class EnvPropertiesBizCheck extends BaseBizCheck<EnvPropertiesEntity> {

    /** 环境配置Service */
    private final EnvPropertiesService  service;


    public EnvPropertiesBizCheck(EnvPropertiesService service) {
        super();
        this.service =service;
    }

    @Override
    public EnvPropertiesBizCheck addable(EnvPropertiesEntity entity){
        return this;
    }
    @Override
    public EnvPropertiesBizCheck deletable(EnvPropertiesEntity entity){

        if (needBizCheck()) {
            required(entity.getKey(),"环境配置ID");
        }
        return this;
    }

    @Override
    public EnvPropertiesBizCheck updatable(EnvPropertiesEntity entity){

        if (needBizCheck()) {
            required(entity.getKey(),"环境配置ID");
        }
        return this;
    }
}
