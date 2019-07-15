package com.bingo.springboot.demo2;

import com.bingo.springboot.demo1.ApplicationConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Bingo
 * @title: ApplicationConfig
 * @projectName java-hub
 * @description: TODO
 * @date 2019/7/14  23:37
 */
@Import(ApplicationConfig.class)
@Configuration
public class ApplicationConfigDemo2 {
}
