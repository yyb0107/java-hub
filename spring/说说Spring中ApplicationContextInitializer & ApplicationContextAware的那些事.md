`ApplicationContextInitializer`和`ApplicationContextAware`来自`spring-context`，他们都有一个操作ApplicationContext的方法
```java
public interface ApplicationContextInitializer<C extends ConfigurableApplicationContext> {
    void initialize(C var1);
}
```

```java
public interface ApplicationContextAware extends Aware {
    void setApplicationContext(ApplicationContext var1) throws BeansException;
}
```

接下来的文字主要想理一理这个接口在Spring boot中是怎么使用的,以及两者对ApplicationContext操作区别。

从Springboot启动开始->`(new SpringApplication(primarySources)).run(args);`->`new SpringApplication(primarySources)`->从spring.factories文件中获取需要加载的Initializer->`SpringApplication.initializers`
```java
    public SpringApplication(ResourceLoader resourceLoader, Class... primarySources) {
        this.sources = new LinkedHashSet();
        this.bannerMode = Mode.CONSOLE;
        this.logStartupInfo = true;
        this.addCommandLineProperties = true;
        this.addConversionService = true;
        this.headless = true;
        this.registerShutdownHook = true;
        this.additionalProfiles = new HashSet();
        this.isCustomEnvironment = false;
        this.resourceLoader = resourceLoader;
        Assert.notNull(primarySources, "PrimarySources must not be null");
        this.primarySources = new LinkedHashSet(Arrays.asList(primarySources));
        this.webApplicationType = WebApplicationType.deduceFromClasspath();
        // SpringApplication的实例构造方法中
        // 从spring.factories文件中获取需要加载的Initializer
        this.setInitializers(this.getSpringFactoriesInstances(ApplicationContextInitializer.class));
        this.setListeners(this.getSpringFactoriesInstances(ApplicationListener.class));
        this.mainApplicationClass = this.deduceMainApplicationClass();
    }
```

`SpringApplication.run(args)`部分代码
```java
......
    ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
    ConfigurableEnvironment environment = this.prepareEnvironment(listeners, applicationArguments);
    this.configureIgnoreBeanInfo(environment);
    Banner printedBanner = this.printBanner(environment);
    //创建ApplicationContext
    context = this.createApplicationContext();
    exceptionReporters = this.getSpringFactoriesInstances(SpringBootExceptionReporter.class, new Class[]{ConfigurableApplicationContext.class}, context);
    //prepare 从名字来开可以看出这是在为context实例做一些准备工作，ApplicationContextInitializers便是在这里进行触发
    this.prepareContext(context, environment, listeners, applicationArguments, printedBanner);
    this.refreshContext(context);
    this.afterRefresh(context, applicationArguments);
    stopWatch.stop();
    if (this.logStartupInfo) {
        (new StartupInfoLogger(this.mainApplicationClass)).logStarted(this.getApplicationLog(), stopWatch);
    }

    listeners.started(context);
    this.callRunners(context, applicationArguments);
 ......
```
详细来看看`this.prepareContext(context, environment, listeners, applicationArguments, printedBanner);`
```java
private void prepareContext(ConfigurableApplicationContext context, ConfigurableEnvironment environment, SpringApplicationRunListeners listeners, ApplicationArguments applicationArguments, Banner printedBanner) {
        context.setEnvironment(environment);
    this.postProcessApplicationContext(context);
    this.applyInitializers(context);
    ......
}
```
`this.applyInitializers(context)`
```java
protected void applyInitializers(ConfigurableApplicationContext context) {
    Iterator var2 = this.getInitializers().iterator();

    while(var2.hasNext()) {
        ApplicationContextInitializer initializer = (ApplicationContextInitializer)var2.next();
        Class<?> requiredType = GenericTypeResolver.resolveTypeArgument(initializer.getClass(), ApplicationContextInitializer.class);
        Assert.isInstanceOf(requiredType, context, "Unable to call initializer.");
        initializer.initialize(context);
    }

}
```
至此可以看到`ApplicationContextInitializer`在prepareContext阶段就已经开始触发了。那`ApplicationContextAware`

我们一般使用 implements `ApplicationContextAware`好让这个实现体可以获取到ApplicationContext,那么`ApplicationContextAware`的`setApplicationContext()`又是在什么时候触发的呢？

既然`ApplicationContextAware`是和Bean绑定在一起的，那看看Spring中Bean的初始化流程(也就是Spring的IOC和DI,详细展开的会影响篇幅，有时间将单独开篇，这里只做简单梳理)

先做个铺垫，看看createApplicationContext()做的事情
```java
protected ConfigurableApplicationContext createApplicationContext() {
    Class<?> contextClass = this.applicationContextClass;
    if (contextClass == null) {
        try {
            switch(this.webApplicationType) {
            case SERVLET:
                contextClass = Class.forName("org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext");
                break;
            case REACTIVE:
                contextClass = Class.forName("org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebServerApplicationContext");
                break;
            default:
                contextClass = Class.forName("org.springframework.context.annotation.AnnotationConfigApplicationContext");
            }
        } catch (ClassNotFoundException var3) {
            throw new IllegalStateException("Unable create a default ApplicationContext, please specify an ApplicationContextClass", var3);
        }
    }

    return (ConfigurableApplicationContext)BeanUtils.instantiateClass(contextClass);
}
```

不管创建的ApplicationContext是哪一个类型，一定是`GenericApplicationContext`的子类

`GenericApplicationContext`中有一个`private final DefaultListableBeanFactory beanFactory;`

我们通过`context.getBean(**)`操作其实最终是作用于`DefaultListableBeanFactory`, `GenericApplicationContext`相当于是一个静态代理。