### 简单说明

rpc下四个Module

**rpc-api**：服务接口

**rpc-client**：模拟客户端发送socket请求

**rpc-registry**：服务注册中心，由于存放注册的服务，当收到请求能找到注册的服务，则向prc-srever发送请求，否则直接返回失败

**rpc-server**：真正的服务提供者，但不接受客户端的请求，而是接受注册中心发过来的请求。启动的时候会向注册中心发送可以处理的服务信息，完成在注册中心的注册。


`rpc-client` `rpc-registry` `rpc-server`都需要依赖rpc-api

启动顺序

`rpc-registry`->`rpc-registry`->`rpc-client`