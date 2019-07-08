# 客户端(BootStrap)

## 配置阶段
创建一个线程池 `EventLoopGroup group = new NioEventLoopGroup();`

创建BootStrap `bootstrap = new Bootstrap();`

传入工作线程 `bootstrap.group(group)`

传入待初始化的SocketChannel封装类 `bootstrap.group(group).channel(NioSocketChannel.class)`

传入第一个handler类，用于registry,调用完毕后就销毁了`bootstrap.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>()


## 连接阶段

`ChannelFuture future = bootstrap.connect("127.0.0.1", 8080).sync()`

### 这个阶段分为两步

#### 初始化
##### BootStrap 初始化
`bootstrap.connect`->`bootstrap.doResolveAndConnect`->`this.initAndRegister()`

_**AbstractBootstrap.java**_
```java
    final ChannelFuture initAndRegister() {
        Channel channel = null;

        try {
            channel = this.channelFactory.newChannel();//利用反射初始化SocketChannel封装类
            this.init(channel);
        } catch (Throwable var3) {
            ......
        }
        ......
        return regFuture;
    }
```
this.init() 回到子类BootStrap的init方法

_**BootStrap.java**_
```java
void init(Channel channel) throws Exception {
        ChannelPipeline p = channel.pipeline();
        //this.config.handler() 返回的就是配置阶段传入的handler
        p.addLast(new ChannelHandler[]{this.config.handler()});
        Map<ChannelOption<?>, Object> options = this.options0();
        ...//后续的暂时可以忽略
    }
```

重点注意`p.addLast(handler[])`。首先要从channel中拿到ChannelPipeLine，channel就是那个SocketChannel的封装类，它在初始化的时候会new一个PipeLine，具体流程如下：

_**AbstractChannel.java**_
```java
protected AbstractChannel(Channel parent) {
        this.parent = parent;
        this.id = this.newId();
        this.unsafe = this.newUnsafe();
        //看见没，就是在这个初始化的，NioSocketChannel层层调用父类的方法，这里到头
        this.pipeline = this.newChannelPipeline();
    }
    
    //其实就是new了一个和当前channel绑定一起的DefaultPipeChannelPipeline
    protected DefaultChannelPipeline newChannelPipeline() {
            return new DefaultChannelPipeline(this);
        }
```

那这个初始化的DefaultChannelPipeLine做了些什么呢？
```java
//维护一张单链表
private DefaultChannelPipeline.PendingHandlerCallback pendingHandlerCallbackHead;

protected DefaultChannelPipeline(Channel channel) {
        this.channel = (Channel)ObjectUtil.checkNotNull(channel, "channel");
        this.succeededFuture = new SucceededChannelFuture(channel, (EventExecutor)null);
        this.voidPromise = new VoidChannelPromise(channel, true);
        this.tail = new DefaultChannelPipeline.TailContext(this);
        this.head = new DefaultChannelPipeline.HeadContext(this);
        // tail 和 prev 维护一张双向链表 head-tail
        this.head.next = this.tail;
        this.tail.prev = this.head;
    }
```
这里主要关注一下构造方法里面tail和head这两个变量和另一个全局变量`private DefaultChannelPipeline.PendingHandlerCallback pendingHandlerCallbackHead;`

好了，知道Pipeline怎么来的之后，我们再回到`p.addLast(handler[])`

通过层层调用，会调用DefaultChannelPipeLine的这个addLast方法
```java
public final ChannelPipeline addLast(EventExecutorGroup group, String name, ChannelHandler handler) {
        final AbstractChannelHandlerContext newCtx;
        synchronized(this) {
            checkMultiplicity(handler);
            // 把我们传入的handler封装成一个AbstractChannelHandlerContext
            newCtx = this.newContext(group, this.filterName(name, handler), handler);
            // 还记得pipeline初始化的时候有个tail和head吗，这个方法就是把newCtx插入到tail的前面一个
            // 于是有个 head-newCtx-tail这样一个双向结构
            this.addLast0(newCtx);
            //第一次调用的时候，肯定还没有registered，别忘了，我们现在是在走初始化阶段呢，所以this.registered=false
            //也就是说第一次会走这个判断
            if (!this.registered) {
                newCtx.setAddPending();//更新了一个值HANDLER_STATE_UPDATER 为 1，对我来说，好像不太重要，继续
                // 注意这个判断体马上就要结束了，handler总要给个地方安放吧
                // 所以这个方法挺关键的，做的一件事就是把newCtx放到pendingHandlerCallbackHead链表里
                this.callHandlerCallbackLater(newCtx, true);
                return this;
            }
            // 对于初始化阶段，后面的代码不看也罢
            EventExecutor executor = newCtx.executor();
            if (!executor.inEventLoop()) {
                newCtx.setAddPending();
                executor.execute(new Runnable() {
                    public void run() {
                        DefaultChannelPipeline.this.callHandlerAdded0(newCtx);
                    }
                });
                return this;
            }
        }

        this.callHandlerAdded0(newCtx);
        return this;
    }
```

##### NioEventLoopGroup的初始化
`EventLoopGroup group = new NioEventLoopGroup();`
一个空构造，好简单的样子，既然是线程池，总得有工作线程的数量吧
当我们点进入看他的层层实现，发现它一直在调用自己的重载构造和父类构造，可以追溯到`MultithreadEventExecutorGroup`.
先来看看它构造的架势。
_**NioEventLoopGroup.java**_
```java
public class NioEventLoopGroup extends MultithreadEventLoopGroup {
    // 1
    public NioEventLoopGroup() {
        this(0);
    }

    // 2
    public NioEventLoopGroup(int nThreads) {
        this(nThreads, (Executor)null);
    }

    public NioEventLoopGroup(int nThreads, ThreadFactory threadFactory) {
        this(nThreads, threadFactory, SelectorProvider.provider());
    }

    // 3
    public NioEventLoopGroup(int nThreads, Executor executor) {
        this(nThreads, executor, SelectorProvider.provider());
    }

    public NioEventLoopGroup(int nThreads, ThreadFactory threadFactory, SelectorProvider selectorProvider) {
        this(nThreads, threadFactory, selectorProvider, DefaultSelectStrategyFactory.INSTANCE);
    }

    public NioEventLoopGroup(int nThreads, ThreadFactory threadFactory, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory) {
        super(nThreads, threadFactory, new Object[]{selectorProvider, selectStrategyFactory, RejectedExecutionHandlers.reject()});
    }

    // 4
    public NioEventLoopGroup(int nThreads, Executor executor, SelectorProvider selectorProvider) {
        this(nThreads, executor, selectorProvider, DefaultSelectStrategyFactory.INSTANCE);
    }

    // 5
    public NioEventLoopGroup(int nThreads, Executor executor, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory) {
        super(nThreads, executor, new Object[]{selectorProvider, selectStrategyFactory, RejectedExecutionHandlers.reject()});
    }

    public NioEventLoopGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory) {
        super(nThreads, executor, chooserFactory, new Object[]{selectorProvider, selectStrategyFactory, RejectedExecutionHandlers.reject()});
    }

    public NioEventLoopGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory, RejectedExecutionHandler rejectedExecutionHandler) {
        super(nThreads, executor, chooserFactory, new Object[]{selectorProvider, selectStrategyFactory, rejectedExecutionHandler});
    }
    ......
}
```
**MultithreadEventLoopGroup.java**
```java
public abstract class MultithreadEventLoopGroup extends MultithreadEventExecutorGroup implements EventLoopGroup {
  
    private static final int DEFAULT_EVENT_LOOP_THREADS = Math.max(1, SystemPropertyUtil.getInt("io.netty.eventLoopThreads", Runtime.getRuntime().availableProcessors() * 2));
    // 6
    protected MultithreadEventLoopGroup(int nThreads, Executor executor, Object... args) {
        super(nThreads == 0 ? DEFAULT_EVENT_LOOP_THREADS : nThreads, executor, args);
    }

    protected MultithreadEventLoopGroup(int nThreads, ThreadFactory threadFactory, Object... args) {
        super(nThreads == 0 ? DEFAULT_EVENT_LOOP_THREADS : nThreads, threadFactory, args);
    }

    protected MultithreadEventLoopGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory, Object... args) {
        super(nThreads == 0 ? DEFAULT_EVENT_LOOP_THREADS : nThreads, executor, chooserFactory, args);
    }
```
最后是**MultithreadEventExecutorGroup.java**
```java
public abstract class MultithreadEventExecutorGroup extends AbstractEventExecutorGroup {
    private final EventExecutor[] children;
    private final Set<EventExecutor> readonlyChildren;
    private final AtomicInteger terminatedChildren;
    private final Promise<?> terminationFuture;
    private final EventExecutorChooser chooser;
    
    protected MultithreadEventExecutorGroup(int nThreads, ThreadFactory threadFactory, Object... args) {
        this(nThreads, (Executor)(threadFactory == null ? null : new ThreadPerTaskExecutor(threadFactory)), args);
    }
    // 7
    protected MultithreadEventExecutorGroup(int nThreads, Executor executor, Object... args) {
        this(nThreads, executor, DefaultEventExecutorChooserFactory.INSTANCE, args);
    }

    // 8
    protected MultithreadEventExecutorGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory, Object... args) {
        this.terminatedChildren = new AtomicInteger();
        this.terminationFuture = new DefaultPromise(GlobalEventExecutor.INSTANCE);
        if (nThreads <= 0) {
            throw new IllegalArgumentException(String.format("nThreads: %d (expected: > 0)", nThreads));
        } else {
            if (executor == null) {
                // executor主要就是有一个execute()方法，
                executor = new ThreadPerTaskExecutor(this.newDefaultThreadFactory());
            }

            this.children = new EventExecutor[nThreads];

            int j;
            for(int i = 0; i < nThreads; ++i) {
                boolean success = false;
                boolean var18 = false;

                try {
                    var18 = true;
                    // 走的太远，都快忘了是从哪里出发，不要忘了，现在的group我们用的是EventLoopGroup group = new NioEventLoopGroup();
                    // NioEventLoopGroup的newChild方法返回如下
                    // return new NioEventLoop(this, executor, (SelectorProvider)args[0], ((SelectStrategyFactory)args[1]).newSelectStrategy(), (RejectedExecutionHandler)args[2]);
                    // 这说明childer[]数组放了一堆NioEventLoop对象，这个对象有些什么东西，我们先不管，继续往下看。
                    this.children[i] = this.newChild((Executor)executor, args);
                    success = true;
                    var18 = false;
                } catch (Exception var19) {
                    throw new IllegalStateException("failed to create a child event loop", var19);
                } finally {
                    if (var18) {
                        //... 失败我们不看
                    }
                }

                if (!success) {//... 失败我们不看
                }
            }
            // chooser是什么鬼？它是EventExecutorChooser接口的一个具体实例，通过一个factory创建出来，接口只有一个方法 EventExecutor next();这个就直接剧透了，netty中提供了EventExecutorChooser的两个实现类，GenericEventExecutorChooser和PowerOfTowEventExecutorChooser，它们都有一个EventExecutor[] 数组，next()方法就是在EventExecutor[]首尾遍历一个EventExecutor出来。
            this.chooser = chooserFactory.newChooser(this.children);
            // 后面的基本是围绕childer转，我们先放放，前面的就够让人头疼的，我们先捋捋
            FutureListener<Object> terminationListener = new FutureListener<Object>() {
                public void operationComplete(Future<Object> future) throws Exception {
                    if (MultithreadEventExecutorGroup.this.terminatedChildren.incrementAndGet() == MultithreadEventExecutorGroup.this.children.length) {
                        MultithreadEventExecutorGroup.this.terminationFuture.setSuccess((Object)null);
                    }

                }
            };
    
            EventExecutor[] arr$ = this.children;
            j = arr$.length;

            for(int i$ = 0; i$ < j; ++i$) {
                EventExecutor e = arr$[i$];
                e.terminationFuture().addListener(terminationListener);
            }

            Set<EventExecutor> childrenSet = new LinkedHashSet(this.children.length);
            Collections.addAll(childrenSet, this.children);
            this.readonlyChildren = Collections.unmodifiableSet(childrenSet);
        }
    }
}
```
单是构造方法的调用就是`1-2-3-4-5-6-7-8`一条龙。
**阅读下面的内容之前，要确保已经看了上面的注释内容。**
childern的内容好像很重要，目前我们只知道childer[]数组通过newChild方法放了一堆NioEventLoop对象，那来看看NioEventLoop是什么。
**NioEventLoopGroup.java**的`newChild`方法
```java
protected EventLoop newChild(Executor executor, Object... args) throws Exception {
        //在父类
        return new NioEventLoop(this, executor, (SelectorProvider)args[0], ((SelectStrategyFactory)args[1]).newSelectStrategy(), (RejectedExecutionHandler)args[2]);
    }
```
**NioEventLoop.java**
```java
public final class NioEventLoop extends SingleThreadEventLoop {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioEventLoop.class);
    private static final int CLEANUP_INTERVAL = 256;
    private static final boolean DISABLE_KEYSET_OPTIMIZATION = SystemPropertyUtil.getBoolean("io.netty.noKeySetOptimization", false);
    private static final int MIN_PREMATURE_SELECTOR_RETURNS = 3;
    private static final int SELECTOR_AUTO_REBUILD_THRESHOLD;
    private final IntSupplier selectNowSupplier = new IntSupplier() {
        public int get() throws Exception {
            return NioEventLoop.this.selectNow();
        }
    };
    private final Callable<Integer> pendingTasksCallable = new Callable<Integer>() {
        public Integer call() throws Exception {
            return NioEventLoop.super.pendingTasks();
        }
    };
    Selector selector;
    private SelectedSelectionKeySet selectedKeys;
    private final SelectorProvider provider;
    private final AtomicBoolean wakenUp = new AtomicBoolean();
    private final SelectStrategy selectStrategy;
    private volatile int ioRatio = 50;
    private int cancelledKeys;
    private boolean needsToSelectAgain;

    //最终落点在这里
    NioEventLoop(NioEventLoopGroup parent, Executor executor, SelectorProvider selectorProvider, SelectStrategy strategy, RejectedExecutionHandler rejectedExecutionHandler) {
        super(parent, executor, false, DEFAULT_MAX_PENDING_TASKS, rejectedExecutionHandler);
        if (selectorProvider == null) {
            throw new NullPointerException("selectorProvider");
        } else if (strategy == null) {
            throw new NullPointerException("selectStrategy");
        } else {
            this.provider = selectorProvider;
            this.selector = this.openSelector();
            this.selectStrategy = strategy;
        }
    }
```
到这里，可以看到我们在用nio编码时常用的对象了，所以NioEventLoop可以看做是`Selector`的封装，NioEventLoopGroup初始化结束


至此我们的初始化阶段已经完成了。在来回顾一下，这个阶段主要做了什么事情
> 1. 我们不是传了一个NioSocketChannel吗，那现在就要初始化它，它的初始化虽然有点曲折，但本质就是用反射newIntance一个新对象
> 1. NioSocketChannel可以看做是nio SocketChannel的封装，伴随着它的初始化，还初始化了DefaultChannelPipeLine
> 1. NioEventLoopGroup主要关注两个属性 `executor`对应`线程池`，`childern`对应`NioEventLoop`
> 1. `NioEventLoop`可以看成是对`selector`的封装

现在我们已经拥有了`nio SocketChannel`，`DefaultChannelPipeLine`上面有我们传入的`handler`.
`group`有`线程池`，`selector`
什么时候才开始用`nio SocketChannel`发送`connect`连接呢？我们继续。


#### 注册
继续回到 _**AbstractBootStrap.java**_ 
```java
final ChannelFuture initAndRegister() {
        Channel channel = null;

        try {
            channel = this.channelFactory.newChannel();
            this.init(channel);
        } catch (Throwable var3) {
           ......
        }
        // 看到现在，只是做了初始化的工作，接下来的就是和connect有关了

        // this.config().group() 就是那个线程池 EventLoopGroup，我们来看看register方法做了什么？
        ChannelFuture regFuture = this.config().group().register(channel);
        if (regFuture.cause() != null) {
            ......//报错了才走这个，我们先放弃
        }

        return regFuture;
    }
```

写到这里，提到了`netty的NioSocketChannel`和`nio SocketChannel`，避免混淆，说明一下，它们的关系是前者是对后者的封装,我得提醒一下我自己，现在传过去的channel是NioSocketChannle实例化的那个channel哦。

`NioEventLoopGroup.register(channel)`
会调用`MutithreadEventLoopGroup.register(channel)`
_**MutithreadEventLoopGroup.java**_
```java
public ChannelFuture register(Channel channel) {
        return this.next().register(channel);
    }
```
注意这个`next()`方法，其实就是调用`this.chooser.next();`
`chooser`其实也是对`children`的封装，`children`是一个方法一堆`NioEventLoop的`数组，所以`this.next()`就是取一个`NioEventLoop`并调用它的`register(channel)`方法。

最终走到_**AbstractChannel.java**_
```java
private void register0(ChannelPromise promise) {
            try {
                if (!promise.setUncancellable() || !this.ensureOpen(promise)) {
                    return;
                }
                // 注意这里firstRegistration为true了
                boolean firstRegistration = this.neverRegistered;
                // 调用子类AbstractNioChannel.doRegistre方法
                // this.selectionKey = this.javaChannel().register(this.eventLoop().selector, 0, this);
                // 是不是很熟悉了，只是这里ops=0
                // 已知的只有四种
                // OP_READ = 1 << 0;
                // OP_WRITE = 1 << 2;
                // OP_CONNECT = 1 << 3;
                // OP_ACCEPT = 1 << 4;
                // 先不管，至少是设置了一个没人感兴趣的key
                AbstractChannel.this.doRegister();
                this.neverRegistered = false;
                // 这个关键了，到这里，NioSocketChannel.registered状态为true了
                AbstractChannel.this.registered = true;
                // 触发pipeLine有比必要处理的handler,还记得pipeLine有一个pendingHandlerCallbackHead属性吗，上面有传进去的那个handler，我们还没有处理的，应为当时NioSocketChannel.registered为false,现在要处理了，处理完毕后，将pendingHandlerCallbackHead置为null。
                // 最终会调用我们匿名初始化的那个handler的handlerAdded(ChannelHandlerContext ctx)->initChannel(ChannelHandlerContext ctx).
                // 做两件事
                // 1. initChannel(ctx.channel());这时，在initChannel(ctx)里我们写的代码就开始执行了。
                //2. this.remove(ctx); 删除这个handler ,原来的head-ctx-tail -> head-tail
                AbstractChannel.this.pipeline.invokeHandlerAddedIfNeeded();
                this.safeSetSuccess(promise);
                // 这个不知道干嘛用的?我猜只要是给重写channelActive(ChannelHandlerContext ctx)这个方法的子类的一个钩子方法吧。
                AbstractChannel.this.pipeline.fireChannelRegistered();
                //这里暂时忽略
                if (AbstractChannel.this.isActive()) {
                    if (firstRegistration) {
                        AbstractChannel.this.pipeline.fireChannelActive();
                    } else if (AbstractChannel.this.config().isAutoRead()) {
                        this.beginRead();
                    }
                }
            } catch (Throwable var3) {
                this.closeForcibly();
                AbstractChannel.this.closeFuture.setClosed();
                this.safeSetFailure(promise, var3);
            }

        }
```

代码的大部分有用的信息用注释做了说明，这个方法做的事情就和它的名字register一样，只是告诉程序现在bootstrap处于注册状态了`AbstractChannel.this.registered = true;`注册之前的`handler`赶紧处理了(这种注册之前传进来的`handler`都是放在`pipeLine`的`pendingHandlerCallbackHead`节点上，这是一个单向链表).
我们这里注册之前的handler是匿名类`ChannelInitializer`,在`initChannel`方法里，我们在pipeLine中传了很多`handler`，它们会一次加到`head`-`tail`的双向链表中
至此，注册完成。

#### connect阶段
回到`BootStrap.java`
```java
    private ChannelFuture doResolveAndConnect(final SocketAddress remoteAddress, final SocketAddress localAddress) {
        ChannelFuture regFuture = this.initAndRegister();
        final Channel channel = regFuture.channel();
        if (regFuture.isDone()) {
                //这里是真正开始connect方法
            return !regFuture.isSuccess() ? regFuture : this.doResolveAndConnect0(channel, remoteAddress, localAddress, channel.newPromise());
        } else {
        //...暂时忽略
        }
    }
```
最终
```java
    private static void doConnect(final SocketAddress remoteAddress, final SocketAddress localAddress, final ChannelPromise connectPromise) {
        final Channel channel = connectPromise.channel();
        channel.eventLoop().execute(new Runnable() {
            public void run() {
                if (localAddress == null) {
                    // 按照我们的逻辑，走这里
                    channel.connect(remoteAddress, connectPromise);
                } else {
                    channel.connect(remoteAddress, localAddress, connectPromise);
                }

                connectPromise.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        });
    }
```
上面代码做的事情最后是在selector上注册一个对读感兴趣的事件

```java
future.channel().writeAndFlush(new RPCRequest(method.getDeclaringClass().getSimpleName(),method.getName(), args, method.getParameterTypes())).sync();
```

`writeAndFlush`
写流程(选outboundHander,并调用他们的write方法）
tail->...->...->head|head的write方法会触发channelWritebilityChanged,直至传递到tail
Flush流程(选outboundHander,并调用他们的flush方法,最终head的flush方法实现真正将数据发送出去的逻辑）
tail->...->...->head



