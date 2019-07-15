1. 基于你对springboot的理解描述一下什么是springboot。
    > springboot是一个基于框架的框架。
    
    > 基于约定优于配置的原则，是对个spring原有生态的一个整合。

1. 约定优于配置指的是什么？
    > springboot约定了pom文件中依赖的整合和版本统一。
    
    > springboot提供了个各种start，结合自动装备，只需要在依赖中声明，不用额外配置。
    
1. @SpringBootApplication由哪几个注解组成，这几个注解分别表示什么作用
    > @SpringBootConfiguration：和@Configuration一样
    
    > @EnableAutoConfiguration：这里包含了两个注解`@ConfigurationPackage`和`@Import({AutoConfigurationImportSelector.class})`.`@ConfigurationPackage`表示Springboot将以`@SpringBootApplication`所在的包为根路径进行扫描。`@Import({AutoConfigurationImportSelector.class})`表示将扫描Classpath下所有的`META-INF/spring.factories`文件和`META-INF/spring-autoconfigure-metadata.properties`，这两类文件将定义springboot启动时根据当前classpath下的路径和容器中bean的情况加载不同的配置类信息。
     
    > @ComponentScan：包扫描配置，这里主要设置的是需要exclude的信息。
	```java
	@ComponentScan(
    	excludeFilters = {@Filter(
    	type = FilterType.CUSTOM,
    	classes = {TypeExcludeFilter.class}
	), @Filter(
    	type = FilterType.CUSTOM,
    	classes = {AutoConfigurationExcludeFilter.class}
	)}
	)
	```

1. springboot自动装配的实现原理
	> 基于`AutoConfigurationImportSelector.class`和`Registrar.class`

1. spring中的spi机制的原理是什么？
	> `AutoConfigurationImportSelector.class`会扫描classpath下`META-INF/spring.factories`文件和`META-INF/spring-autoconfigure-metadata.properties`，使得我们可以在自己的包下添加这两个文件来进行Configuration的配置