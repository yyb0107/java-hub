#### 跟踪update()流程，绘制每一步的时序图（4个）
`1.创建SqlSessionFactory`

![创建SqlSessionFactory](imgs/1-Mybatis-SqlSessionFactoryBuilder.jpg)

`2.获取SqlSession`

![获取SqlSession](imgs/2-Mybatis-SqlSession.jpg)

`3.getMapper`

![getMapper](imgs/3-Mybatis-GetMapper.jpg)

`4.执行Update语句`

![执行Update语句](imgs/4-Mybatis-UpdateStatement.jpg)

#### 总结：MyBatis里面用到了哪些设计模式？

设计模式|具体体现
---|---
简单工厂模式 | SqlSessionFactoryBuilder 
工厂方法模式 |SqlSessionFactory.openSession() 
代理模式|sqlSession.getMapper()
装饰器模式|executor = new CachingExecutor((Executor)executor)


