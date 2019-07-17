### redo log
    WAL(Write-Ahead Logging),关键点是先写日志，再写磁盘
    redo log 是 InnoDB 引擎特有的
    redo log 是 crash-safe,数据库异常重启，数据不丢失
    redo log 是物理日志，记录的是“在某个数据页上做了什么修改”
    redo log 是循环写的，空间固定会用完
### binlog
    binlog 是 MySQL 的 Server层实现的，所有引擎都可以使用
    binlog 是逻辑日志，记录的是这个语句的原始逻辑，比如“给id = 2 的这一行的 c 字段加 1”
    binlog 是追加写的
### 总结
    redolog 用于保证 crash-safe 能力。innodb_flush_log_attrx_commit 这个参数设置成1的时候,表示每次事务的 redolog 都直接持久化到磁盎。这个参数建议设置成 1,这样可以保证 MySQL 异常重启之后数据不丢失。
    sync_binlog这个参数设置成1的时候,表示每次事务的binlog都持久化到磁盘。这个参数也建议设置成1,这样可以保证 MySQL 异常重启之后 binlog 不丢失。


