## 启动

1. 修改配置文件

```txt
###  持久化配置开始

# 是否开启aof
appendonly yes
 
# 文件名称
appendfilename "appendonly.aof"
 
# 同步方式，上文已提到
appendfsync everysec
 
# aof重写操作是否同步，yes则不进行同步，no则同步
no-appendfsync-on-rewrite no
 
# 重写触发配置
auto-aof-rewrite-percentage 100 # 当前AOF文件大小是上次日志重写时的AOF文件大小两倍时，发生BGREWRITEAOF操作。
auto-aof-rewrite-min-size 64mb # 当前AOF文件执行BGREWRITEAOF命令的最小值，避免刚开始启动Reids时由于文件尺寸较小导致频繁的BGREWRITEAOF。
 
# 加载aof时如果有错如何处理，忽略最后一条可能存在问题的指令
aof-load-truncated yes
# Redis4.0新增RDB-AOF混合持久化格式。
aof-use-rdb-preamble no

###  持久化配置结束
```

2. 指定配置文件启动

```bash
redis-server /绝对路径/to/redis.conf
```

