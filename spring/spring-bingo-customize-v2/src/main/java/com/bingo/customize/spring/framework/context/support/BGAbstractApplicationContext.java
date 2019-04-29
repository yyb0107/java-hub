package com.bingo.customize.spring.framework.context.support;

import com.bingo.customize.spring.framework.beans.BGBeanFactory;
import com.bingo.customize.spring.framework.beans.support.BGDefaultListableBeanFactory;

/**
 * @author Bingo
 * @title: BGAbstractApplicationContext
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/28  22:53
 */
public abstract class BGAbstractApplicationContext extends BGDefaultListableBeanFactory {
    public void refresh() throws Exception{} ;
}
