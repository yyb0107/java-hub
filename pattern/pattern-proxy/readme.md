# 代理模式

---

## 什么是代理模式
由于某些原因需要给某对象提供一个代理以控制对该对象的访问。这时，访问对象不适合或者不能直接引用目标对象，代理对象作为访问对象和目标对象之间的中介。

## 为什么用代理模式
上面说的某些原因包括：
目标对象的功能需要增强；
目标对象需要进行保护；

## 如何使用代理模式
### 静态代理
代理对象持有目标对象的一个引用，在这个基础上对目标对象的进行功能上的增强，在平时的代码中我们会有意无意的用到。
```java
public class JobHunter {
    Person person;

    public JobHunter(Person person) {
        this.person = person;
    }

    public void findJob() {
        before();
        person.findJob("static");
        after();
    }

    private void after() {
        System.out.println("找到合适的工作了。");
    }

    private void before() {
        System.out.println("根据需求搜集信息……");
    }
}
```
### 动态代理
动态代理主要有`JDK动态代理`和`CGLIB动态代理`
它们的共性就是都会对根据目标对象动态生成一个代理类的class文件,通过内部编译并重新初始化的一个新的对象，这个就是我们的代理对象。
```java
public class JobHunter implements MethodInterceptor {
    public Object getInstance(Class clazz) {
        //这里就是生成代理对象的代码
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object obj = methodProxy.invokeSuper(o,objects);
        after();
        return obj;
    }

    private void after() {
        System.out.println("这里是CGLib,找到合适的工作了。");
    }

    private void before() {
        System.out.println("这里是CGLib,根据需求搜集信息……");
    }
}
```

--|jdk代理|CGlib代理
----|----|----
要求|目标对象必须实现一个接口，jdk根据接口重写方法|目标对象在继承关系上没有限制，cglib根据当前类来重写方法
效率|JDK Proxy生成代理的逻辑简单，执行效率相对要低，每次都要反射动态调用|CGLib 生成代理逻辑更复杂，效率,调用效率更高，生成一个包含了所有的逻辑的FastClass，不再需要反射调用
> cglib不能代理final的方法·

---
## 引申
> 为什么JDK动态代理中要求目标类实现的接口数量不能超过65535个？
首先目标类是一个类文件，那先看看类文件的结构
       ` Class文件是一组以8字节为基础单位的二进制流`，
       ` 各个数据项目严格按照顺序紧凑排列在class文件中`，
       ` 中间没有任何分隔符，这使得class文件中存储的内容几乎是全部程序运行的程序`
       `(摘自《深入理解java虚拟机》周志明著)`
```
    ClassFile 结构体，如下：
       ClassFile {
           u4             magic;
           u2             minor_version;
           u2             major_version;
           u2             constant_pool_count;
           cp_info        constant_pool[constant_pool_count-1];
           u2             access_flags;
           u2             this_class;
           u2             super_class;
           u2             interfaces_count;
           u2             interfaces[interfaces_count];
           u2             fields_count;
           field_info     fields[fields_count];
           u2             methods_count;
           method_info    methods[methods_count];
           u2             attributes_count;
           attribute_info attributes[attributes_count];
       }
```
>各项的含义描述：
     1，无符号数，以`u1、u2、u4、u8`分别代表`1个字节、2个字节、4个字节、8个字节`的无符号数
2，表是由多个无符号数或者其它表作为数据项构成的复合数据类型，所以表都以`_info`结尾，由多个无符号数或其它表构成的复合数据类型

综上，我的理解是如果目标类实现的接口数量超过了65535个，那我类文件能记下来的只有`2^16-1`个，类似windows的寻址一样吧。





