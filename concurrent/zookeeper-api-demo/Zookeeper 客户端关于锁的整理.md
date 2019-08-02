对于zookeeper，有很多可以基于java实现的客户端api

**curator** **ZkClient**

关于分布式锁的使用

curator 提供了LeaderSelector 和 LeaderLatch

LeaderSelector 需要传入LeaderSelectorListenerAdapter

LeaderLatch 需要传入LeaderLatchListener

它们的区别在于LeaderLatch需要主动调用LeaderLatch.close()方法
而LeaderSelector在重写方法takeLeadership()就自动释放了锁。

另外它们两个的start()都是异步的，获取锁后的具体操作在对应listener中的方法中去实现。