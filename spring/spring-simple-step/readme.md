> 列举SpringAOP、IOC、DI应用的代码片段

## SrpingAOP
```java
@Component
@Aspect
public class LoginAop {
    @Pointcut("execution(* com.bingo.java.spring.simple.step.UserService.login(*))")
    public void pointCut(){

    }
    /**
     * 前置通知
     * @param joinPoint
     */
    @Before("pointCut()")
    public void beforeExcution(JoinPoint joinPoint){

        System.out.println("aop 前置通知");

    }

    /**
     * 后置返回
     * @param result
     */
    @AfterReturning(returning = "result",pointcut = "pointCut()")
    public void afterReturning(Object result) throws Throwable{
        System.out.println("aop 后置返回 "+result);
    }

}
```
## IOC
```java
@Configuration
public class SpringStart {

    public static void main(String[] args) {
        FileSystemXmlApplicationContext applicationContext = new FileSystemXmlApplicationContext(SpringStart.class.getClassLoader().getResource("config.xml").getFile());
        UserService userService = (UserService)applicationContext.getBean("userService");
        userService.login(new User("uname","password"));

        applicationContext.start();
    }
}
```
## DI
```java
@Component
public class UserService {
    @Autowired
    UserDao userDao;

    public User login(User user) {
        User rs = userDao.login(user);
        if(rs!=null){
            System.out.println("用户登陆成功");
        }else{
            System.out.println("用户登陆失败");
        }
        return rs;
    }
}
```