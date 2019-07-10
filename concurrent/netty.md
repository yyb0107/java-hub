1. 为什么都说Netty是高性能的RPC框架？
将线程池和多路复用的Selector运用到了极致。

1. 服务端的Socket在哪里开始初始化？
在`ServerBootStrap`中传入的channel为`NioServerSocketChannel`，在`bind`的时候，会对`NioServerSocketChannel`调用默认的空构造进行初始化.
```java
    public NioServerSocketChannel() {
        this(newSocket(DEFAULT_SELECTOR_PROVIDER));
    }

    private static java.nio.channels.ServerSocketChannel newSocket(SelectorProvider provider) {
        try {
            return provider.openServerSocketChannel();
        } catch (IOException var2) {
            throw new ChannelException("Failed to open a server socket.", var2);
        }
    }

    public NioServerSocketChannel(java.nio.channels.ServerSocketChannel channel) {
        super((Channel)null, channel, 16);
	// 这里的this.javaChannel().socket()将通过ServerSocketAdaptor.create(ServerScoketChannelImpl)创建一个serverSocket
        this.config = new NioServerSocketChannel.NioServerSocketChannelConfig(this, this.javaChannel().socket());
    }
```


1. 服务端的Socket在哪里开始accept链接？
首先在执行doRegister()方法
_**AbstractNioChannel.java**_
```java
    protected void doRegister() throws Exception {
        boolean selected = false;

        while(true) {
            try {
                // 先注册ops=0在selector上
                this.selectionKey = this.javaChannel().register(this.eventLoop().selector, 0, this);
                return;
            } catch (CancelledKeyException var3) {
                if (selected) {
                    throw var3;
                }

                this.eventLoop().selectNow();
                selected = true;
            }
        }
    }
    // 最后会执行doBeginRead()方法
    ```java
    protected void doBeginRead() throws Exception {
        SelectionKey selectionKey = this.selectionKey;
        if (selectionKey.isValid()) {
            this.readPending = true;
            int interestOps = selectionKey.interestOps();
            // this.readInterestOp在初始化NioServerSocketChannel的时候传的是16，OP_ACCEPT
            if ((interestOps & this.readInterestOp) == 0) {
                selectionKey.interestOps(interestOps | this.readInterestOp);
            }
        }
    }
    ```
```

---


1. 了解Netty服务端的线程池分配规则，线程何时启动。
Netty服务端有两个线程池，通过方法group(parent,child)方式进行传入，其中parent是boss线程，每个boss线程对应bind动作。
启动时间，触发register时，会调用如下方法
_**AbstractChannel.java**_
```java
        eventLoop.execute(new Runnable() {
                            public void run() {
                                AbstractUnsafe.this.register0(promise);
                            }
                        });
```
然后 _**SingleThreadEventExecutor.java**_
```java
    public void execute(Runnable task) {
        if (task == null) {
            throw new NullPointerException("task");
        } else {
            boolean inEventLoop = this.inEventLoop();
            if (inEventLoop) {
                this.addTask(task);
            } else {
                this.startThread();
                this.addTask(task);
                if (this.isShutdown() && this.removeTask(task)) {
                    reject();
                }
            }

            if (!this.addTaskWakesUp && this.wakesUpForTask(task)) {
                this.wakeup(inEventLoop);
            }

        }
    }
```
child是work线程，用于当Server收到Accept请求后
```java
    	ch.eventLoop().execute(new Runnable() {
                    public void run() {
			// currentChildGroup 就是传过去的child
                        pipeline.addLast(new ChannelHandler[]{new ServerBootstrap.ServerBootstrapAcceptor(currentChildGroup, currentChildHandler, currentChildOptions, currentChildAttrs)});
                    }
                });
```
当server收到可读事件后，会调用下面的方法
```java
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
            final Channel child = (Channel)msg;
            child.pipeline().addLast(new ChannelHandler[]{this.childHandler});
            Entry[] arr$ = this.childOptions;
            int len$ = arr$.length;

            int i$;
            Entry e;
            for(i$ = 0; i$ < len$; ++i$) {
                e = arr$[i$];

                try {
                    if (!child.config().setOption((ChannelOption)e.getKey(), e.getValue())) {
                        ServerBootstrap.logger.warn("Unknown channel option: " + e);
                    }
                } catch (Throwable var10) {
                    ServerBootstrap.logger.warn("Failed to set a channel option: " + child, var10);
                }
            }

            arr$ = this.childAttrs;
            len$ = arr$.length;

            for(i$ = 0; i$ < len$; ++i$) {
                e = arr$[i$];
                child.attr((AttributeKey)e.getKey()).set(e.getValue());
            }

            try {
		// 这里就会触发work线程的启动
                this.childGroup.register(child).addListener(new ChannelFutureListener() {
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (!future.isSuccess()) {
                            ServerBootstrap.ServerBootstrapAcceptor.forceClose(child, future.cause());
                        }

                    }
                });
            } catch (Throwable var9) {
                forceClose(child, var9);
            }

        }
```
2. 了解Netty是如何解决JDK空轮训Bug的？
通过计时的方式，如果判定为空轮询，将会创建一个新的selector，并将原selector的元数据赋给新selector。原来的selector将废弃

3. Netty是如何实现异步串行无锁化编程的？
通过EventLoop,EventLoop和channel绑定，不同的channel分别有不同的eventloop进行维护。

