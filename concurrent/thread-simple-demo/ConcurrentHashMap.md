1. ConcurrentHashMap1.8中是基于什么机制来保证线程安全性的

> 数组＋单向链表+红黑树 利用cas插入链表头结点和synchronize来维护每一个链表

2. ConcurrentHashMap通过get方法获取数据的时候，是否需要通过加锁来保证数据的可见性？为什么？
> 不需要，因为获取数据属于查询操作，不会修改集合的值，不存在可见性问题。

3. ConcurrentHashMap1.7和ConcurrentHashMap1.8有哪些区别？
> ConcurrentHashMap1.7 采用数组＋单向链表，ConcurrentHashMap1.8 采用数组＋单向链表+红黑树
> ConcurrentHashMap1.7 有segment分段锁 ConcurrentHashMap1.8 取消分段锁

4. ConcurrentHashMap1.8为什么要引入红黑树？
> 降低查询的深度，提高查询的速度