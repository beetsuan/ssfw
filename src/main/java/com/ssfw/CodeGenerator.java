package com.ssfw;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author a
 * @date 2022/9/14
 * @since 2.7.3
 */
@Service
public class CodeGenerator {


    @Autowired
    private  DataSourceProperties dataSourceProperties;


    public void gen() {
        FastAutoGenerator.create(dataSourceProperties.getUrl(), dataSourceProperties.getUsername(), dataSourceProperties.getPassword())
                .globalConfig(builder -> {
                    builder.author("ssfw") // 设置作者
                            .fileOverride()
                            .enableSpringdoc()
                            .outputDir("D:/mapper/"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.ssfw") // 设置父包名
                            .moduleName("auth") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D:/mapper/")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("auth_user") // 设置需要生成的表名
                            .addTablePrefix("auth", "t_"); // 设置过滤表前缀

                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
