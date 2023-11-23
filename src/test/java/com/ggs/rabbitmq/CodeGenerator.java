package com.ggs.rabbitmq;

import java.io.File;
import java.sql.Types;
import java.util.Collections;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * @Author starbug
 * @Description
 * @Datetime 2023/11/23 10:48
 */
public class CodeGenerator {

    public static void main(String[] args) {
        String absolutePath = new File("").getAbsolutePath().replaceAll("\\\\", "/");
        String url =
                "jdbc:mysql://8.134.177.106:3306/practice_demo?serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&rewriteBatchedStatements=true&useUnicode=true&characterEncoding=utf-8&characterSetResults=UTF-8&autoReconnect=true&allowMultiQueries=true&useSSL=false";
        String username = "root";
        String password = "starbug-docker-mysql";
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("lhh") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(absolutePath + "/src/main/java"); // 指定输出目录
                })
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    } else if (typeCode == Types.TIMESTAMP) {
                        return DbColumnType.LOCAL_DATE_TIME;
                    } else if (typeCode == Types.DATE) {
                        return DbColumnType.LOCAL_DATE;
                    }
                    return typeRegistry.getColumnType(metaInfo);

                }))
                .packageConfig(builder -> {
                    builder.parent("com.ggs.rabbitmq.demo") // 设置父包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, absolutePath + "/resources")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("a_device") // 设置需要生成的表名
                            .addTablePrefix("a_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

}
