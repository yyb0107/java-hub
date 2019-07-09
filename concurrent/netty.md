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
