package com.bingo.customize.spring.framework.context;

import com.bingo.customize.spring.framework.aop.framework.BGAdvisedConfig;
import com.bingo.customize.spring.framework.aop.framework.BGAdvisedSupport;
import com.bingo.customize.spring.framework.beans.config.BGBeanDefinition;
import com.bingo.customize.spring.framework.beans.support.BGBeanDefinitionReader;
import com.bingo.customize.spring.framework.beans.support.BGBeanWrapper;
import com.bingo.customize.spring.framework.context.support.BGAbstractApplicationContext;

import java.util.List;
import java.util.Properties;

/**
 * @author Bingo
 * @title: BGApplicationContext
 * @projectName java-hub
 */
public class BGApplicationContext extends BGAbstractApplicationContext {
    private String[] configLocation;
    private List<BGBeanDefinition> bgBeanDefinitions;
    private BGBeanDefinitionReader reader;

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
        if (!getFactoryBeanInstanceCache().containsKey(beanName)) {
            populateBean(beanName, bgBeanDefinition, bgBeanWrapper);
        }
        return getFactoryBeanInstanceCache().get(beanName).getWrappedInstance();
    }

    @Override
    public void refresh() throws Exception {
        //定位
        if(configLocation == null || configLocation.length == 0) return;
        reader =new BGBeanDefinitionReader(configLocation[0]);

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
    public Properties getConfig(){
        return reader.getConfig();
    }

    @Override
    protected BGAdvisedSupport instantionAopConfig(BGBeanDefinition bd) {
//        if(this.getFactoryBeanInstanceCache().get(bd.getFactoryBeanName())!=null){
//            return (BGAdvisedSupport)this.getFactoryBeanInstanceCache().get(bd.getFactoryBeanName()).getWrappedInstance();
//        }
        BGAdvisedConfig config = new BGAdvisedConfig();
        config.setPointCut(reader.getConfig().getProperty("pointCut"));
        config.setAspectAfter(reader.getConfig().getProperty("aspectAfter"));
        config.setAspectBefore(reader.getConfig().getProperty("aspectBefore"));
        config.setAspectAfterThrow(reader.getConfig().getProperty("aspectAfterThrow"));
        config.setAspectAfterThrowingName(reader.getConfig().getProperty("aspectAfterThrowName"));
        config.setAspectClass(reader.getConfig().getProperty("aspectClass"));
        config.setAspectAround(reader.getConfig().getProperty("aspectAround"));

        BGAdvisedSupport support = new BGAdvisedSupport();
        support.setConfig(config);
        BGBeanWrapper beanWrapper = new BGBeanWrapper();
        beanWrapper.setWrappedInstance(support);
//        this.getFactoryBeanInstanceCache().putIfAbsent(bd.getFactoryBeanName(),beanWrapper);
        return support;
    }
}
