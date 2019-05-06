package com.bingo.customize.spring.framework.context;

import com.bingo.customize.spring.framework.beans.config.BGBeanDefinition;
import com.bingo.customize.spring.framework.beans.support.BGBeanDefinitionReader;
import com.bingo.customize.spring.framework.beans.support.BGBeanWrapper;
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
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getBean(String beanName) {
        BGBeanDefinition bgBeanDefinition = getBeanDfinitionMap().get(beanName);
        if(bgBeanDefinition == null) return null;

        //1.instantiateBean
        if(getFactoryBeanInstanceCache().isEmpty()){
            getBeanDfinitionMap().forEach((key,bd)->{
                getFactoryBeanInstanceCache().putIfAbsent(key, instantiateBean(key, bd));
            });
        }
        BGBeanWrapper bgBeanWrapper = null;
        if (!getFactoryBeanInstanceCache().containsKey(beanName)) {
            //bgBeanWrapper = instantiateBean(beanName, bgBeanDefinition);
            getFactoryBeanInstanceCache().putIfAbsent(beanName, instantiateBean(beanName, bgBeanDefinition));
        }
        bgBeanWrapper = getFactoryBeanInstanceCache().get(beanName);

        if(bgBeanDefinition.isSingleton()){
            addSingletonObjects(beanName,bgBeanWrapper.getWrappedInstance());
        }

        //2.populateBean
        populateBean(beanName,bgBeanDefinition,bgBeanWrapper);
        return getFactoryBeanInstanceCache().get(beanName).getWrappedInstance();
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

        //对非lazy bean 开始初始化
        doBeanAutoWired();
    }

    private void doBeanAutoWired() {
        getBeanDfinitionMap().forEach((key,definition)->{
            if(!definition.isInitLazy()){
                getBean(key);
            }
        });
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
