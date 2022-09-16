package com.ssfw.common.framework.listener;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;


/**
 * 监听类初始化
 * @author a
 */
@Component
public class InitListener implements CommandLineRunner, Ordered {

    private Reflections interfaceReflection = null;
    private Reflections implReflection = null;


    private String[] basePackage = new String[]{"com.ssfw"};


    @SuppressWarnings("unchecked")
    public InitListener() {

        initAnnotationReflection();
        initImplReflection();

        Set<Class<?>> listenerSet = interfaceReflection.getTypesAnnotatedWith(Listener.class);

        for (Class<?> listener : listenerSet) {
            final Set<Class<?>> classSet = new LinkedHashSet<>();
            Set<Class<?>> subTypesOf = implReflection.getSubTypesOf((Class<Object>) listener);
            subTypesOf.forEach(aClass -> {
                //抽象类和内部类不予返回
                if (Modifier.isAbstract(aClass.getModifiers()) || Modifier.isInterface(aClass.getModifiers())){
                    return;
                }
                classSet.add(aClass);
            });
            if (classSet.isEmpty()){
                continue;
            }
            ListenerInvoker.putListener(listener.getName(),classSet);
        }
    }

    @Override
    public void run(String... args) {

    }

    /**
     * 初始化接口类Reflections
     * @return Reflections
     */
    private Reflections initAnnotationReflection(){

        Collection<URL> urlTotals = new ArrayList<>();
        for (String s : basePackage) {
            urlTotals.addAll(ClasspathHelper.forPackage(s));
        }

        interfaceReflection = new Reflections(
                new ConfigurationBuilder()
                        .forPackages(basePackage)
                        .setScanners(Scanners.TypesAnnotated, Scanners.SubTypes).setUrls(urlTotals)
        );

        return interfaceReflection;
    }

    /**
     * 初始化实现类Reflections
     * @return Reflections
     */
    private Reflections initImplReflection(){


        Collection<URL> urlTotals = new ArrayList<>();
        for (String s : basePackage) {
            urlTotals.addAll(ClasspathHelper.forPackage(s));
        }

        implReflection = new Reflections(
                new ConfigurationBuilder()
                        .forPackages(basePackage)
                        .setScanners(Scanners.SubTypes).setUrls(urlTotals)
        );
        return implReflection;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
