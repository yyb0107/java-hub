#### resultType（属性）和resultMap（标签引用）的区别？
resultType对应一个java类全路径名，映射的时候要求查询出来的列名和当前类的属性名对应，否则映射失败。

resultMap是mybatis mapper 文件中创建的一个标签引用，使用property和column完成类和表字段的映射。

#### collection和association的区别？

collection: 一对`多`中的`多`

association：一对`一`中的`一`

#### 3、Statement和PreparedStatement的区别？
jdbc处理sql语句的步骤如下
1. 解析sql语句
2. 编译sql语句
3. 优化sql语句的占位参数
4. 执行优化后的sql语句

PreparedStatement会先完成步骤1-3，这样使得如果这条语句要重复使用，将会减少后续步骤1到步骤3的消耗，提高速度。
但它的第一次执行会消耗比Statement更长的时间。
