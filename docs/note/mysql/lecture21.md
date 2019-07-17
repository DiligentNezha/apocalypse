### 加锁规则，两个“原则”、两个“优化”和一个“bug” 
***
    1.原则1：加锁的基本单位是 next-key lock。  
    2.原则2：查找过程中访问到的对象才会加锁。  
    3.优化1：索引上的等值查询，给唯一索引加锁的时候，next-key lock退化为行锁。  
    4.优化2：索引上的等值查询，向右遍历时且最后一个值不满足等值条件的时候，next-key lock退化为间隙锁。  
    5.一个bug：唯一索引上的范围查询会访问到不满足条件的第一个值为止。
#### 案例
```mysql
CREATE TABLE t_21 (
   id int(11) NOT NULL,
   c int(11) DEFAULT NULL,
   d int(11) DEFAULT NULL,
   PRIMARY KEY (id),
   KEY c (c)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
insert into t_21 values(0,0,0),(5,5,5),(10,10,10),(15,15,15),(20,20,20),(25,25,25);
```
    1.案例一  
      等值查询间隙锁
|session A|session B|session C|
|:--:|:--:|:--:|
|begin;<br>update t_21 set d=d+1 where id = 7;| | |
| |insert into t_21 values(8,8,8);<br>(<font color=red>blocked</font>)| |
| | |update t_21 set d=d+1 where id = 10;<br>(<font color=green>Query OK</font>)|

    由于表t中没有id=7的记录,所以用我们上面提到的加锁规则判断一下的话:
    1.根据原则1,加锁单位是next-keylock,session A加锁范围就是(5,101];  
    2.同时根据优化2,这是一个等值查询(id=7),而id=10不满足审询条件,next-key lock退化成间隙锁,因此最终加锁的范围是(5,10)。
    所以,session B要往这个间隙里面插入id=8的记录会被铖住,但是session C修改id=10这行是可以的。  
***
    2.案例二  
        非唯一索引等值锁  
|session A|session B|session C|
|:--:|:--:|:--:|
|begin:<br>select id from t_21 where c = 5 lock in share mode;| | |
| |update t_21 set d = d + 1 where id = 5;<br>(<font color=green>Query OK</font>)| |
| | |insert into t_21 values(7, 7, 7);<br>(<font color=red>blocked</font>)|

    这里session A要给索引c 上 c=5的这一行加上读锁。
    1.根据原则1,加锁单位是next-key lock,因此会给(0,5]加上next-key lock。
    2.要注意c是普通索引,因此仅访问c=5这一条记录是不能马上停下来的,需要向右遍历,查到c=10才放弃。
      根据原则2,访问到的都要加锁,因此要给(5,10]加next-key lock。
    3.但是同时这个符合优化2:等值判断,向右遍历,最后一个值不满足c=5这个等值条件,因此退化成间隙锁(5,10)。
    4.根据原则2,只有访问到的对象才会加锁,这个查询使用覆盖索引,并不需要访问主键索引,所以主键索引上没有加任何锁,
      这就是为什么session B的update语句可以执行完成。但session C要插入一个(7,7.7)的记录,
      就会被session A的间隙锁(5.10)锁住。需要注意,在这个例子中,lock in share mode只锁覆盖索引,
      但是如果是for update就不一样了,执行for update时,系统会认为你接下来要更新数据,
      因此会顺便给主键索引上满足条件的行加上行锁,这个例子说明,锁是加在索引上的;
      同时,它给我们的指导是,如果你要用lock in share mode来给行加读锁避免数据被更新的话,
      就必须得绕过覆盖索引的优化,在查询字段中加入索引中不存在的字段。
      比如′将session A改成select d from t_21 where c=5 lock in share mode。
*** 
    3.案例三  
    等值查询间隙锁 
***
    4.案例四  
    等值查询间隙锁  
*** 
    5.案例五  
    等值查询间隙锁  
***
    6.案例六  
    等值查询间隙锁
*** 
    7.案例七  
    等值查询间隙锁
***
    8.案例八  
    等值查询间隙锁  
