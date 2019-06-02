>这是一个基于Spring jdbc来写的一个简单orm框架，主要是为了更好的理解orm框架背后的设计和代码实现。

#### 理解ORM框架的顶层设计原理

* 基于Connection的包装

DataSource只有两个方法`Connection getConnection() throws SQLException;` 和`Connection getConnection(String username, String password) throws SQLException;`

orm框架的目的是将数据库表中的每一行数据和java对象的进行关联

所以首先要获取数据，那么就必须要有数据库的一个连接Connection.

通过connection，将Object对象到数据库关系表中的增删改查。

orm主要负责这个过程中事务如何整合，表数据和object如何关联