package com.bingo.customize.spring.framework.aop.framework;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Bingo
 * @title: BGAdvisedConfig
 * @projectName java-hub
 */
@Getter
@Setter
public class BGAdvisedConfig {
    private String pointCut;
    private String aspectClass;
    private String aspectBefore;
    private String aspectAfter;
    private String aspectAround;
    private String aspectAfterThrow;
    private String aspectAfterThrowingName;
}
