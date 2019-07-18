package com.bingo.format;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author Bingo
 * @title: FormatterConfiguration
 * @projectName java-hub
 * @description: TODO
 * @date 2019/7/17  23:10
 */
@Configuration
public class FormatterConfiguration {

    @Bean
    @ConditionalOnClass(name="com.alibaba.fastjson.JSON")
    public IFormatterService jsonFormatterService() {
        return new JsonFormatterService();
    }

    @Primary
    @Bean("xmlFormatterService")
    @ConditionalOnMissingClass("com.alibaba.fastjson.JSON")
    public IFormatterService xmlFormatterService() {
        return new XmlFormatterService();
    }

}
