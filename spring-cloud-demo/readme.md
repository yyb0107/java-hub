# spring cloud netflix eureka


> 本文演示的是使用NetFlix Eureka如何完成注册中心，服务注册和服务发现。

## 注册中心
依赖
```xml
 <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
```
启动类
```java
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(EurekaServerApp.class).web(WebApplicationType.SERVLET).run(args);
    }
}
```
配置文件
```yaml
server:
  port: 9090

eureka:
  instance:
    hostname: localhost
  client:
    registerWithEureka: false
    fetchRegistry: false
    serviceUrl:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
spring:
  application:
    name: eureka-server
```

## 服务提供者
提供一个服务，当收到name的时候响应name Hello World!字符串
依赖
```xml
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
```
服务实现
```java
@RestController
public class HelloController {

    @RequestMapping("/hello/{name}")
    public String hello(@PathVariable("name") String name){
        return name +"\tHello World!";
    }

}
```
配置文件
```
server:
  port: 8080
spring:
  application:
    name: service-provider-1
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:9090/eureka
management:
  endpoints:
    web:
      exposure:
        include: "*"

```

## 服务消费
依赖和`服务提供`的一样
消费实现
```java
@RestController
public class ConsumerController {
    @Autowired
    HelloService service;

    @RequestMapping("/say/{name}")
    public String sayHello(@PathVariable("name")String name) {
        return service.hello(name);
    }

}
```
```java
@Component
public class HelloService {
    @Autowired
    RestTemplate restTemplate;

    private static final String PROVIDE_URL_PREFIX ="http://service-provider-1/hello";

    public String hello(String name){
        ResponseEntity response = restTemplate.getForEntity(PROVIDE_URL_PREFIX+"/"+name,String.class);
        String resultValue = response.getBody().toString();
        return resultValue;
    }
}
```
```java
@SpringBootApplication
@EnableEurekaClient
public class EurekaConsumerApp {
    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(EurekaConsumerApp.class).run(args);
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
```