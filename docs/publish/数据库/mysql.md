## 一、七大JOIN

### 1.内连接

> 查询两张表的共有部分

```sql
SELECT * FROM TABLE_A A INNER JOIN TABLE_B B ON A.KEY = B.KEY;
```

### 2.左连接

> 查询左边表所有内容，右边只查询满足条件的记录

```sql
SELECT * FROM TABLE_A A LEFT JOIN TABLE_B B ON A.KEY = B.KEY;
```

### 3.右连接

> 查询右边所有的内容，左边只查询满足条件的记录

```sql
SELECT * FROM TABLE_A A RIGHT JOIN TABLE_B B ON A.KEY = B.KEY;
```

### 4.查询左边独有的数据

> 查询A表的独有数据

```sql
SELECT * FROM TABLE_A A LEFT JOIN TABLE_B B ON A.KEY = B.KEY WHERE B.KEY IS NULL;
```

### 5.查询右边独有的数据

> 查询B表的独有数据

```sql
SELECT * FROM TABLE_A A RIGHT JOIN TABLE_B B ON A.KEY = B.KEY WHERE A.KEY IS NULL;
```

### 6.全连接

> 查询两张表的全部信息

```sql
SELECT * FROM TABLE_A A LEFT JOIN TABLE_B B ON A.KEY = B.KEY
UNION
SELECT * FROM TABLE_A A RIGHT JOIN TABLE_B B ON A.KEY = B.KEY
```

- 使用`UNION`关键字，可以自动去重

### 7.查询左右表各自的独有数据

> 查询A和B各自独有的数据

```sql
SELECT * FROM TABLE_A A LEFT JOIN TABLE_B B ON A.KEY = B.KEY WHERE B.KEY IS NULL
UNION
SELECT * FROM TABLE_A A RIGHT JOIN TABLE_B B ON A.KEY = B.KEY WHERE A.KEY IS NULL
```

## 二、索引 index

### 1.MySQL的三层架构



