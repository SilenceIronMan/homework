
# 一、准备工作

**1. 搭建数据库**

```sql
create database stockdb;
use stockdb;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS stock;
DROP TABLE IF EXISTS stock_order;

# 用户
create table `user`(
                       uid int primary key auto_increment,
                       uname varchar(100),
                       upwd varchar(50)
);

# 商品
create table stock(
                      id int primary key auto_increment,
                      `name` varchar(50),
                      `count` int,	#库存数量
                          sale int,		#已售
                          `version` int	#版本号（乐观锁）
);

# 订单
create table stock_order(
                            id int primary key auto_increment,
                            sid int,
                            `name` varchar(50),
                            `create_time` timestamp
);

insert `user` values('0','yinshiyu','123456');
insert stock value('0','iPhone 13 Pro Max',15,0,0);
```

**2. 启动redis**

```
CMD 进入控制台
执行 redis-windows\redis-server
启动redis
```

**3. 运行程序**


变更数据库链接 账号密码
执行
mvn clean package  -DskipTests
重新打包代码

1. java -jar kill-0.0.1-SNAPSHOT.jar

2. 在 redis 中设置 商品秒杀时间

   ```set kill1 1 EX 60```  设置商品1的秒杀时间为60s
   (商品id为1 key 为 kill1)

3. /ms/stock/md5/{id}/{uid} 返回md5

   http://localhost:8989/ms/stock/md5/1/1 
   获取商品1 用户1 的md5 鉴权码
   返回 0eb74c0e1017822cd4e6868da3f97727

   

4. /ms/stock/killtokenMD5limit/{id}/{uid}/{md5}

   http://localhost:8989/ms/stock/killtokenMD5limit/1/1/0eb74c0e1017822cd4e6868da3f97727
   进行商品的抢购
   返回 抢购结果

