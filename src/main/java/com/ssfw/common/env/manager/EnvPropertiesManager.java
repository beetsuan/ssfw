package com.ssfw.common.env.manager;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.ssfw.common.centext.SpringContextHolder;
import com.ssfw.common.env.entity.EnvPropertiesEntity;
import com.ssfw.common.env.service.EnvPropertiesService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * SP系统环境配置管理器
 */
@Slf4j
public class EnvPropertiesManager implements Serializable {

    private static final long serialVersionUID = 6029410254956143884L;


    private final EnvPropertiesService service;

    private volatile static EnvPropertiesManager instance;


    public static EnvPropertiesManager getInstance() {
        if (instance == null) {
            //双重检测同步延迟加载
            synchronized (EnvPropertiesManager.class){
                if (null == instance){
                    instance = new EnvPropertiesManager();
                }
            }
        }
        return instance;
    }

    private EnvPropertiesManager() {
        this.service = SpringContextHolder.getBean(EnvPropertiesService.class);
    }

    public EnvPropertiesEntity get(String key){
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return service.getFromCache(key.trim());
    }

    public Optional<EnvPropertiesEntity> optional(String key){

        if (StringUtils.isBlank(key)) {
            return Optional.of(EnvPropertiesEntity.off());
        }
        EnvPropertiesEntity value = this.get(key.trim());
        if(null == value){
            return Optional.of(EnvPropertiesEntity.off());
        }
        return Optional.of(value);
    }

    public Optional<EnvPropertiesEntity> optional(String key, Consumer<EnvPropertiesEntity> action){

        Optional<EnvPropertiesEntity> optional = optional(key);
        optional.ifPresent(action);
        return optional;
    }

    public List<EnvPropertiesEntity> get(String... key){

        LambdaQueryChainWrapper<EnvPropertiesEntity> wrapper = service.lambdaQuery();
        for (int i = 0; i < key.length; i++) {
            wrapper.eq(EnvPropertiesEntity::getKey, key[i]);
            if (i< key.length-1){
                wrapper.or();
            }
        }
        return wrapper.list();
    }

    public boolean isOn(String key){
        final EnvPropertiesEntity entity = get(key);
        if (null == entity){
            return false;
        }
        return entity.isOn();
    }

    public String getValue01(String key){

        final EnvPropertiesEntity entity = get(key);
        if (null == entity){
            return null;
        }
        return entity.getValue01();
    }

    public Object getValue01OrDefault(String key, String defaultValue) {
        final EnvPropertiesEntity entity = get(key);
        if (null == entity){
            return defaultValue;
        }
        return entity.getValue01();
    }

    public String getValue02(String key){

        final EnvPropertiesEntity entity = get(key);
        if (null == entity){
            return null;
        }
        return entity.getValue02();
    }

    public Object getValue02OrDefault(String key, String defaultValue) {
        final EnvPropertiesEntity entity = get(key);
        if (null == entity){
            return defaultValue;
        }
        return entity.getValue02();
    }

    public Integer getIntValue01(String key) {
        final String s = getValue01(key);
        if (null == s){
            return null;
        }
        return Integer.parseInt(s);
    }

    public String[] getArrayValue01(String key){

        final String s = getValue01(key);
        if (null == s){
            return new String[0];
        }
        return s.split(",");
    }

    public Integer getIntValue02(String key) {
        final String s = getValue02(key);
        if (null == s){
            return null;
        }
        return Integer.parseInt(s);
    }

    public String[] getArrayValue02(String key){

        final String s = getValue02(key);
        if (null == s){
            return new String[0];
        }
        return s.split(",");
    }

    public void save(EnvPropertiesEntity entity){
        String oldKey = entity.getKey();
        EnvPropertiesEntity dbModel = get(oldKey);
        if (null == dbModel){
            service.save(entity);
        }else {
            service.updateById(entity);
        }
    }

    public void delete(String key){
        service.removeById(new EnvPropertiesEntity(key));
    }

    public EnvPropertiesService getService() {
        return service;
    }
}
