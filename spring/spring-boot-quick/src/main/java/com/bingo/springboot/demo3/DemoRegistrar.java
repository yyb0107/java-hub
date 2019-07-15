package com.bingo.springboot.demo3;

import com.bingo.springboot.demo1.entity.DumpEntity2;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author Bingo
 * @title: DemoRegistrar
 * @projectName java-hub
 * @description: TODO
 * @date 2019/7/15  23:39
 */
public class DemoRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(DumpEntity2.class);
        beanDefinitionRegistry.registerBeanDefinition("dumpEnity2",beanDefinition);

    }
}
