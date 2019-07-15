package com.bingo.springboot.demo1;

import com.bingo.springboot.demo1.entity.DumpEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Bingo
 * @title: ApplicationConfig
 * @projectName java-hub
 * @description: TODO
 * @date 2019/7/14  23:37
 */
@Configuration
public class ApplicationConfig {
    @Bean
    public DumpEntity dumpEntity(){
        return new DumpEntity();
    }
}
