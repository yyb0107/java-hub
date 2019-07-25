# Zookeeper集群环境设置

---
## 情景
在virtualbox上安装了三台centos7虚拟机，ip分别是：
```
    192.168.1.110
    192.168.1.111
    192.168.1.112
```
三台机器上都有`apache-zookeeper-3.5.5-bin.tar.gz`在自创建了目录`/opt/zookeeper/`下

## 坏境准备
1. 下载zookeeper包
    ```sh
    wget http://mirror.bit.edu.cn/apache/zookeeper/zookeeper-3.5.5/apache-zookeeper-3.5.5-bin.tar.gz
    
    ```
1. 解压`apache-zookeeper-3.5.5-bin.tar.gz`
    ```sh
    tar -zxvf apache-zookeeper-3.5.5-bin.tar.gz

    ```
2. 设置环境变量
    ```sh
    vi /etc/profile
    ...
    export ZOOKEEPER_HOME=/opt/zookeeper/apache-zookeeper-3.5.5-bin
    export PATH=$PATH:$ZOOKEEPER/bin
    
    #运行source命令让当前设置立即生效
    source /etc/profile
    #可以用echo命令查看当前PATH，看是不是和预期一样
    echo $PATH
        
    ```
> 其他机器重复以上步骤。

## 集群
1. 打开zookeeper安装目录，`/opt/zookeeper/apache-zookeeper-3.5.5-bin`.结构目录如下
```sh
drwxr-xr-x. 2 2002 2002   232 Apr  9 19:13 bin
drwxr-xr-x. 2 2002 2002    77 Apr  2 21:05 conf #从这里开始我们的集群配置
drwxr-xr-x. 5 2002 2002  4096 May  3 20:07 docs
drwxr-xr-x. 2 root root  4096 Jul 24 22:06 lib
-rw-r--r--. 1 2002 2002 11358 Feb 15 20:55 LICENSE.txt
-rw-r--r--. 1 2002 2002   432 Apr  9 19:13 NOTICE.txt
-rw-r--r--. 1 2002 2002  1560 May  3 19:41 README.md
-rw-r--r--. 1 2002 2002  1347 Apr  2 21:05 README_packaging.txt
#-------------这是一条分隔线-------------------------
# 进入conf文件夹，有一个zoo_sample.cfg
cd conf 
# 复制一份名为zoo.cfg，因为zookeeper服务端启动时候会根据这个名字来加载
cp zoo_sample.cfg zoo.cfg
# 在zoo.cfg里面配置集群的信息
vi zoo.cfg
# 在zoo.cfg末端加入下面的配置
server.1=192.168.1.110:2888:3888
server.2=192.168.1.111:2888:3888
server.3=192.168.1.112:2888:3888
# 在zoo.cfg里dataDir指定的文件夹下，创建myid文件，文件内容为server后面的一个数字
#例如在192.168.1.110这台机器上，myid的内容为1.其他的以此类推。
```
>server.A=B：C：D：其中 A 是一个数字，表示这个是第几号服务器；B 是这个服务器的 ip 地址；C 表示的是这个服务器与集群中的 Leader 服务器交换信息的端口；D 表示的是万一集群中的 Leader 服务器挂了，需要一个端口来重新进行选举，选出一个新的 Leader，而这个端口就是用来执行选举时服务器相互通信的端口。如果是伪集群的配置方式，由于 B 都是一样，所以不同的 Zookeeper 实例通信端口号不能一样，所以要给它们分配不同的端口号。

完成上述配置后，就可以开始进入启动阶段

```sh
#zkServer.sh 需要传一个参数，有这些可以选 {start|start-foreground|stop|restart|status|print-cmd}
#我们先用start-foreground，可以在命令行看到启动的日志信息
zkServer.sh start

```
依次启动每个机器，如果不出意外会显示下面的信息
```sh
/usr/bin/java
ZooKeeper JMX enabled by default
Using config: /opt/zookeeper/apache-zookeeper-3.5.5-bin/bin/../conf/zoo.cfg
Starting zookeeper ... STARTED

```
通过`zkServer.sh status`查看状态信息
```sh
[root@localhost bin]# zkServer.sh status
/usr/bin/java
ZooKeeper JMX enabled by default
Using config: /opt/zookeeper/apache-zookeeper-3.5.5-bin/bin/../conf/zoo.cfg
Client port found: 2181. Client address: localhost.
Mode: follower

```

或者是
```sh
[root@localhost init.d]# zkServer.sh status
/usr/bin/java
ZooKeeper JMX enabled by default
Using config: /opt/zookeeper/apache-zookeeper-3.5.5-bin/bin/../conf/zoo.cfg
Client port found: 2181. Client address: localhost.
Mode: leader
```
看到上面的信息表示zk集群配置成功了。

不过一开始并没有这么顺利，查看状态
```sh
Using config: /opt/zookeeper/bin/../conf/zoo.cfg
Error contacting service. It is probably not running.
```
如果你也越到这样的提示，希望后面的步骤对你有帮助。
```sh
# 首先停掉服务
# 改用zkServer.sh start-foreground方式启动，可以看到具体错误日志
zkServer.sh start-foreground
```
启动之后，会看到这样的信息
```java
2019-07-25 23:11:14,266 [myid:2] - WARN  [QuorumPeer[myid=2](plain=/0:0:0:0:0:0:0:0:2181)(secure=disabled):QuorumCnxManager@677] - Cannot open channel to 1 at election address /192.168.1.110:3888
java.net.NoRouteToHostException: No route to host (Host unreachable)
	at java.net.PlainSocketImpl.socketConnect(Native Method)
	at java.net.AbstractPlainSocketImpl.doConnect(AbstractPlainSocketImpl.java:350)
	at java.net.AbstractPlainSocketImpl.connectToAddress(AbstractPlainSocketImpl.java:206)
...
```

```sh
#网络上的解释是说需要关闭防火墙
#可以通过`systemctl status firewalld.service`查看当前防火墙的状态
#关闭防火漆的命名
systemctl stop firewalld.service
#关闭之后再次启动zookeeper
```

> 网上也有说是和iptables有关，对我来说，关于linux网络设置还是有很多黑盒，暂且放下不提。


