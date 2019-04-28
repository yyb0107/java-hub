package com.bingo.customize.spring.framework.context;

import com.bingo.customize.spring.framework.beans.support.BGBeanDefinitionReader;
import com.bingo.customize.spring.framework.context.support.BGAbstractApplicationContext;

/**
 * @author Bingo
 * @title: BGApplicationContext
 * @projectName java-hub
 */
public class BGApplicationContext extends BGAbstractApplicationContext {
    private String[] configLocation;

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
        reader.loadBeanDefinitions();

        //注册
    }
}
