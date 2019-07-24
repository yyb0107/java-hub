# 在virtualbox上安装centos

标签（空格分隔）： 工具使用

---

我在win10电脑上使用virtualbox安装centos系统，发现启动虚拟机的时候不能进入安装界面，只出现一个黑色的命令窗口。
后经网上的资料，原来需要电脑开启Virtualization Technology.
开启方式：启动时不停的按F2键进入BIOS系统，在BIOS界面将Virtualization Technology置为Enable.


网络设置

宿主主机为wifi情况下

网卡一：网络地址转换（NAT）
网卡二：桥接网卡
界面名称和你本机无线无卡的界面名称一致

启动虚拟机

先用ip address 看看虚拟机网卡情况

在/etc/sysconfig/network-scripts/下，可以看`ifcfg-网卡名`

我这里来显示的有两个，分别对应ip address看到的网卡
分别是`ifcfg-enp0s3`和`ifcfg-enp0s8`,对应virtualbox上的网卡一和网卡二
主要是设置`ifcfg-enp0s8`

需要设置的信息
```sh
[root@localhost ~]# vi /etc/sysconfig/network-scripts/ifcfg-enp0s8

TYPE=Ethernet
PROXY_METHOD=none
BROWSER_ONLY=no
#BOOTPROTO=dhcp #改为static
BOOTPROTO=static
#HWADDR=08:00:27:2e:04:b7
#新增内容开始
IPADDR=192.168.1.111
PREFIX=24
NETMASK=255.255.255.0
GATEWAY=192.168.1.1
DNS1=8.8.8.8
DNS2=223.5.5.5
#新增内容结束
DEFROUTE=yes
IPV4_FAILURE_FATAL=no
IPV6INIT=yes
IPV6_AUTOCONF=yes
IPV6_DEFROUTE=yes
IPV6_FAILURE_FATAL=no
IPV6_ADDR_GEN_MODE=stable-privacy
NAME=enp0s8
#UUID=ba3da5dd-c6e9-4719-a830-edb31aca5013
DEVICE=enp0s8 
ONBOOT=yes #默认为no，改为yes
```

## 环境安装
```sh
# jdk：
yum intall java-1.8.0-openjdk.x86_64 

#安装wget
yum search wget
yum install wget.x86_64

#下载zookeeper包,可以放在一个自定义的文件下/opt/zookeeper
wget http://mirror.bit.edu.cn/apache/zookeeper/zookeeper-3.5.5/apache-zookeeper-3.5.5-bin.tar.gz

tar -zxvf apache-zookeeper-3.5.5-bin.tar.gz
```



