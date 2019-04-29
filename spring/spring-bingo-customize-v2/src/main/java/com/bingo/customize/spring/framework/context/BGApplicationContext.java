package com.bingo.customize.spring.framework.context;

import com.bingo.customize.spring.framework.beans.config.BGBeanDefinition;
import com.bingo.customize.spring.framework.beans.support.BGBeanDefinitionReader;
import com.bingo.customize.spring.framework.context.support.BGAbstractApplicationContext;

import java.util.List;

/**
 * @author Bingo
 * @title: BGApplicationContext
 * @projectName java-hub
 */
public class BGApplicationContext extends BGAbstractApplicationContext {
    private String[] configLocation;
    private List<BGBeanDefinition> bgBeanDefinitions;

    public BGApplicationContext(String... configLocation){
        this.configLocation = configLocation;
    }

    @Override
    public Object getBean(String beanName) {
        return null;
    }

    @Override
    public void refresh() throws Exception {
        //定位
        if(configLocation == null || configLocation.length == 0) return;
        BGBeanDefinitionReader reader =new BGBeanDefinitionReader(configLocation[0]);

        //加载
        bgBeanDefinitions = reader.loadBeanDefinitions();

        //注册
        registerBGBeanDeinition(bgBeanDefinitions);
    }

    private void registerBGBeanDeinition(List<BGBeanDefinition> bgBeanDefinitions) {
        for (BGBeanDefinition bgBeanDefinition : bgBeanDefinitions) {
            if(getBeanDfinitionMap().get(bgBeanDefinition.getFactoryBeanName())!=null){
                new RuntimeException(String.format("bean[%s] was existed",bgBeanDefinition.getFactoryBeanName()));
            }
            getBeanDfinitionMap().put(bgBeanDefinition.getFactoryBeanName(),bgBeanDefinition);
        }
    }
}
