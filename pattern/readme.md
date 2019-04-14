# 设计模式总结
设计模式|一句话总结|实例
----|----|----
工厂模式|获取对象，其他的不关心|BeanFactory
单例模式|全局唯一，一改全改|ApplicationContext
原型模式|深复制，浅复制|ArrayList、PrototypeBean
代理模式|实际的我已经不是原来的我|ProxyFactoryBean、JdkDynamicAopProxy、CglibAopProx
委派模式|任务下来，各司其职|DispatcherServlet
策略模式|只是一个方案，随时可以替换|InstantiationStrategy
模板模式|别怕，专心做自己的事|JdbcTemplate
适配器模式|接口不兼容？我来|HandlerAdapter
装饰器模式|包装，同宗同源|BufferedReader
观察者模式|你忙你的，有事再找你|ContextLoaderListener

> 列举SpringAOP、IOC、DI应用的代码片段
[点击这里](../spring/spring-simple-step/readme.md)