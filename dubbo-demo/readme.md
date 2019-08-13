# dubbo
## 是什么？
> 提供分布式服务治理的组件，提供一种生态可以实现多协议多注册中心多配置中心等等的支持。

## 为什么？
> 分布式服务模式的成熟，是的越来越多的原来的单体服务项分布式服务发展，在分布式的环境里，为了满足服务有高可用的特性，需要对集群的服务进行服务治理，实现负载均衡，容错熔断，宕机恢复等需求，bubbo就是可以提供这些问题的一种解决方案的生态。

## 怎么用
基于配置(dubbo默认将会开启20880的端口，zk默认监听2181端口),分为服务的提供者(Provider)和服务的消费者(Consumer)

## 基于配置文件
### provider
```xml
<!-- 这个主要用于在注册中心显示各服务之间的依赖关系 -->
<dubbo:application name="applicationName"/>

<dubbo:registry protocol="" address=""/>

<dubbo:service interface="" ref=""/>

<bean id="" class=""/>

```

### consumer
```xml
<dubbo:registry protocol="" address=""/>
<!-- 引用远程provider端的配置 -->
<dubbo:reference id="" interface=""/>

```

## 注解配置
`@Service`

`@Reference`