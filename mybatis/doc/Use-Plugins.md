自己编写插件：

1、当我们传入RowBounds做翻页查询的时候，使用limit物理分页，代替原来的逻辑分页



`MyBatisTest.java —— testSelectByRowBounds()`


2、在未启用日志组件的情况下，输出执行的SQL，并且统计SQL的执行时间（先实现查询的拦截）

（自己思考，自己动手）