主要测试springboot的自动装配的原理

> @EnableAutoConfiguration 其实就是@import了一些可以通过java代码控制装配逻辑的类，这些类主要是两类

> ImportSelector的实现类

> ImportBeanDefinitionRegistrar

