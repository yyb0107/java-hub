# Springboot自定义starter出现的问题
---
## 背景
springboot提供了很多starter，达到开箱即用的效果。主要是因为starter都有一个META-INF/spring.factories文件，里面配置了我需要需要注入的bean信息。
什么时候加载这个spring.factories文件呢？
springboot项目，都会在启动类上加上@SpringBootApplication这样的注解
这个注解包含了三个部分
`@EnableAutoConfiguration`
`@SpringBootConfiguration`
`@ComponentScan`

`@SpringBootConfiguration`我理解的是和`@Configuration`的作用是一样的。
`@ComponentScan`就是扫描包下的entity
`@EnableAutoConfiguration`的用途之一就是加载spring.factories的地方。通过`SpringFactoriesLoader`，来解析classpath下所有的spring.factories文件

spring.factories上配置有需要加载的Configuration文件，这些文件会根据一些条件来帮我们初始化一些bean。

## 写一个简单的对象系列化字符串的starter
### 目的：
将一个对象转成Json或者Xml，具体转成哪一种格式由当前classpath下是否有Json或者Xml对应的jar包
### 实现
定义一个接口，IFormatterService
```java
public interface IFormatterService {
    <T> String output(T t) throws Exception;
}
```
两个实现`JsonFormatterService ` `XmlFormatterService `
```java
import com.alibaba.fastjson.JSON;

public class JsonFormatterService implements IFormatterService {
    @Override
    public <T> String output(T t) {
        return JSON.toJSON(t)+"";
    }
}
```

```java
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XmlFormatterService implements IFormatterService {
    @Override
    public <T> String output(T t) throws JsonProcessingException {
        XmlMapper mapper =  new XmlMapper();
        return mapper.writeValueAsString(t);
    }
}
```
然后开始定义一个Configuration完成这个bean的初始化
```java
@Configuration
public class FormatterConfiguration {

    @Bean
    @ConditionalOnClass(JSON.class)
    public IFormatterService jsonFormatterService() {
        return new JsonFormatterService();
    }

    @Bean("xmlFormatterService")
    @ConditionalOnMissingClass("com.alibaba.fastjson.JSON")
    public IFormatterService xmlFormatterService() {
        return new XmlFormatterService();
    }

}
```
把上面这工程打成一个jar包，这样，当别的工程引用这个jar包的时候，就可以直接注入IFormatterService了。
创建另外一个springboot项目。并在pom文件加入xml的依赖
```xml
    <dependency>
        <groupId>com.fasterxml.jackson.dataformat</groupId>
        <artifactId>jackson-dataformat-xml</artifactId>
    </dependency>
```

```java
@Service
public class FormatService {
    @Autowired
    IFormatterService formatterService;

    public <T> String format(T t) throws Exception {
        return formatterService.output(t);

    }
}
```
```java
@SpringBootApplication
public class ApplicationStart {
    public static void main(String[] args) throws Exception {
        ApplicationContext application =  SpringApplication.run(ApplicationStart.class,args);
 application.getBean(FormatService.class);
        String out = service.format(new User("bingo","430321",18,"hunan"));
        System.out.println("结果是 ："+out);
    }
}
```
### 结果
启动发现报错了
```java
java.lang.IllegalStateException: Cannot load configuration class: com.bingo.format.FormatterConfiguration
...
Caused by: java.lang.ArrayStoreException: sun.reflect.annotation.TypeNotPresentExceptionProxy
	at sun.reflect.annotation.AnnotationParser.parseClassArray(AnnotationParser.java:724) ~[na:1.8.0_121]
...
```
但是如果是json的依赖
```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.58</version>
</dependency>
```
运行正常
```java
结果是 ：{"address":"hunan","userName":"bingo","userId":"430321","userAge":18}
```
### 分析
主要关注`TypeNotPresentExceptionProxy`,查了下网上的资料说是没有找到对应的类。出现问题的地方只有`FormatterConfiguration`了,也就是注解上的Conditional有问题。


`@ConditionalOnClass`可以用在方法上，也可以用在类上
`@ConditionalOnClass`有两个属性，一个默认的`value`属于Class[],还有一个`name`属于String[]

```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional({OnClassCondition.class})
public @interface ConditionalOnClass {
    Class<?>[] value() default {};

    String[] name() default {};
}
```
上面的定义有一个`OnClassCondition.class`
层层跟进，会发现,如果ConditionalOnClass的value不为空，那么会调用下面的方法，将它转为对应String类型的className。
```java
if (classValuesAsString) {
    if (value instanceof Class) {
        return ((Class)value).getName();
    }

    if (value instanceof Class[]) {
        Class<?>[] clazzArray = (Class[])((Class[])value);
        String[] classNames = new String[clazzArray.length];

        for(i = 0; i < clazzArray.length; ++i) {
            classNames[i] = clazzArray[i].getName();
        }

        return classNames;
    }
}
```
然后根据className判断是否符合条件
```java
public static boolean isPresent(String className, ClassLoader classLoader) {
    if (classLoader == null) {
        classLoader = C lassUtils.getDefaultClassLoader();
    }

    try {
        forName(className, classLoader);
        return true;
    } catch (Throwable var3) {
        return false;
    }
}
```
看起来好像没有什么问题，但因为`@ConditionalOnClass`可以用在类上，也可以用在方法上，如果是用在方法上，那么这个方法一定是在一个Configuration类下面的，而Springboot对于Configuration类，会做一层CGLib的动态代理，为什么要做动态代理暂且不管。
```java
try {
    Class<?> configClass = beanDef.resolveBeanClass(this.beanClassLoader);
    if (configClass != null) {
        Class<?> enhancedClass = enhancer.enhance(configClass, this.beanClassLoader);
        if (configClass != enhancedClass) {
            if (this.logger.isTraceEnabled()) {
                this.logger.trace(String.format("Replacing bean definition '%s' existing class '%s' with enhanced class '%s'", entry.getKey(), configClass.getName(), enhancedClass.getName()));
            }

            beanDef.setBeanClass(enhancedClass);
        }
    }
} catch (Throwable var9) {
    throw new IllegalStateException("Cannot load configuration class: " + beanDef.getBeanClassName(), var9);
}
```
回来我们最开始的例子，在只有`jackson-dataformat-xml.jar`的环境下，JSON.class是不存在了，而cglib发现有这个JSON.class的声明，在代理层的内部或许违背了它代理的原则（纯属猜测）。

解决方案：
全用字符串的形式。
```java
@Configuration
public class FormatterConfiguration {

    @Bean
    @ConditionalOnClass(name="com.alibaba.fastjson.JSON")
    public IFormatterService jsonFormatterService() {
        return new JsonFormatterService();
    }

    @Primary
    @Bean("xmlFormatterService")
    @ConditionalOnMissingClass("com.alibaba.fastjson.JSON")
    public IFormatterService xmlFormatterService() {
        return new XmlFormatterService();
    }

}
```
输出结果
```java
结果是 ：<User><userName>bingo</userName><userId>430321</userId><userAge>18</userAge><address>hunan</address></User>
```

### 建议
如果需要定义自定义的starter或者使用`@ConditionalOnClass`的注解,尽量使用`@ConditionalOnClass(name="com.alibaba.fastjson.JSON")`字符串的形式。

