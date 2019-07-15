package com.bingo.springboot.demo3;

import com.bingo.springboot.demo1.entity.DumpEntity;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author Bingo
 * @title: DemoSelector
 * @projectName java-hub
 * @description: TODO
 * @date 2019/7/15  23:33
 */
public class DemoSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{DumpEntity.class.getName()};
    }
}
