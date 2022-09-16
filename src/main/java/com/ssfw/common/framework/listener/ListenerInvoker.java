package com.ssfw.common.framework.listener;

import com.ssfw.common.centext.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author a
 */
public class ListenerInvoker {

    private static final Logger log = LoggerFactory.getLogger(ListenerInvoker.class);

    private static final Map<String,Set<Class<?>>> LISTENER_MAP = new ConcurrentHashMap<>();


    static void putListener(String listenerName, Set<Class<?>> listenerImplSet){
        LISTENER_MAP.put(listenerName,listenerImplSet);
    }

    /**
     * 获取某个接口的实现类的实列
     * @param interfaceClass 接口类名
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> getListenerImplList(Class<T> interfaceClass) {

        List<T> implList = new ArrayList<>();
        final String interfaceName = interfaceClass.getName();
        try {
            if (!LISTENER_MAP.containsKey(interfaceName)){
                return implList;
            }
            //从listenerMap中，使用接口类全名称 来查找接口的实现类
            final Set<Class<?>> classSet = LISTENER_MAP.get(interfaceName);
            for (Class<?> aClass : classSet) {

                Object bean;
                try {
                    bean = SpringContextHolder.getBean(aClass);
                    implList.add((T) bean);
                } catch (NoSuchBeanDefinitionException e) {
                    //不是spring bean,使用构造函数new一个对象
                    boolean hasDefaultConstructor = false;
                    final Constructor<?>[] declaredConstructors = aClass.getDeclaredConstructors();
                    for (Constructor<?> constructor : declaredConstructors) {

                        if (0 == constructor.getParameterCount()){
                            hasDefaultConstructor = true;
                            break;
                        }
                    }
                    if (hasDefaultConstructor){
                        bean = aClass.getDeclaredConstructor().newInstance();
                        implList.add((T) bean);
                    }else{
                        log.error("无默认构造函数:{},调用失败.",aClass.getName());
                    }
                }
            }
        }catch (Exception e){
            log.error("获取接口的实现类出错:{}", interfaceName);
            throw new RuntimeException(e);
        }
        return implList;
    }
}
