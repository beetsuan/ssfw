package com.ssfw;

import com.ssfw.common.centext.SpringContextHolder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 启动类
 * @author a
 * @date 2022/9/14
 * @since 2.7.3
 */
@SpringBootApplication
@MapperScan("com.ssfw.**.mapper")
public class BootApplication {

    public static void main(String[] args) {

        long start  = System.currentTimeMillis();
        SpringApplication.run(BootApplication.class, args);
        long end  = System.currentTimeMillis();
        System.out.println("--------------启动完成,耗时："+(end-start)+"ms---------------");

        //SpringContextHolder.optionalBean(CodeGenerator.class, CodeGenerator::gen);
    }

}
