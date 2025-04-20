## 1. psql

### 连接数据库

```bash
psql -h 127.0.0.1 -p 5432 -U postgres -d postgres -E
```

- -h 主机IP
- -p 端口
- -U 用户名
- -d 数据库名称
- -E 获取元命令对应的SQL代码（如\d \db的元命令会转换为SQL）

### 常用命令

1. \q 断开psql客户端
2. \d 查看当前数据库所有表、视图、序列
3. \d 表名 查看改表的定义
4. \db 查看表空间的信息
5. \dn 查看所有的模式信息
6. \du or \dg 查看数据库中的所有角色或用户
7. \x 可以设置结果的输出模式
8. \? 查询PostgreSQL支持的所有命令
9. \timing 显示SQL的执行时间（第一次使用\timing 打开；第二次使用关闭）

## 2. 表空间

> 表空间为表、索引等对象指定了一个存储的目录

### 创建表空间

```sql
CREATE TABLESPACE tablespace_name [OWNER user_name] LOCATION 'directory' [WITH tablespace_option]
```

- postgresql不区分大小写
- tablespace_name 表空间名称 不能以 **pg-**开头，他是系统预留名
- user_name 表空间所属用户名 如果省略则为执行SQL的用户
- directory 表空间对应的路径（空目录）postgreSQL的数据目录的**pg_tblspc**，存储了表空间对应的路径的符号链接
- tablespace_option 设置或重置表空间参数
  - seq_page_const 在磁盘上按顺序扫描获取一个页面的代价，默认值1.0
  - random_page_const 在磁盘上随机扫描获取一个页面的代价，默认值4.0
  - effective_io_concurrency 可以执行并发异步磁盘I/O的数量

---

Demo：创建一个tbs_test表空间，所属用户为执行SQL的用户，表空间目录为D:\temp\tbs。

```sql
CREATE TABLESPACE tbs_test LOCATION 'D:\temp\tbs';
```

- 在$PGDATA/pg_tblspc目录下产生一个符号链接
- pg_tblspc下的19009目录名 是表空间tbs_test的对象标识符（OID）

---

在执行CREATE DATABASE、CREATE TABLE、CREATE_INDEX或ADD CONSTRAINT命令时可以指定表空间，以便将这些对象存储在指定的表空间中。

### 修改表空间

```sql
ALTER TABLESPACE name RENAME TO new_name;
ALTER TABLESPACE name OWNER TO user_name;
ALTER TABLESPACE name SET tablespace_option;
ALTER TABLESPACE name RESET tablespace_option;
```

- RENAME TO 重命名表空间
- OWNER TO 修改表空间所属用户
- SET和 RESET设置表空间参数

### 删除表空间

```sql
DROP TABLESPACE [IF EEXISTS] name;
```

- 只用所属用户和超级管理员可以删除表空间
- 只有表空间被清空之后，才能够被删除

## 3. 数据库

> 数据库是一个或多个模式的集合，模式包含表、函数等。

### 创建数据库

```sql
CREATE DATABASE name
[OWNER user_name]
[TEMPLATE template]
[ENCODING encoding]
[LC_COLLATE lc_collate]
[LC_CTYPE lc_ctype]
[TABLESPACE tablespace_name]
[ALLOW_CONNECTIONS allowconn]
[CONNECTION LIMIT connlimit]
[IS_TEMPLATE istemplate]
```

- name 数据库的名称
- user_name 数据库的所属用户
- template 数据库使用的模板库，默认 **template1**
- encoding 字符集，默认使用模板库的字符集
- lc_collate collation顺序。会影响ORDER BY字符串类型列的顺序；影响 text类型列的索引顺序。默认使用模板库的collation顺序
- lc_ctype 字符分类，会影响字符的分类，如大小写和数字
- tablespace_name 指定数据库的表空间
- allowconn 是否荀彧连接该数据库 默认true，false时，任何用户都不能连接该数据库
- connlimit 允许并发连接该数据库的个数 默认-1,没有限制
- istemplate 是否是模板库，默认false 。只有数据库拥有者或超级管理员可以用其复制新的数据库。

### 修改数据库

```sql
ALTER DATABASE name [options]
```

- ALLOW_CONNECTIONS allowconn
- CONNECTION LIMIT connlimit
- IS_TEMPLATE istemplate
- RENAME TO new_name
- OWNER TO username
- SET TABLESPACE new_tablespace
- SET configuration_parameter

### 删除数据库

```sql
DROP DATABASE IF EXISTS NAME
```

## 4. 数据表

### 创建表

```sql
CREATE TABLE(
);
```

1. 使用check约束
   1. 字段级check约束
   2. 表级check约束

```sql

```



2. 使用非空约束



3. 使用唯一约束



4. 使用主键约束



5. 使用默认约束



6. 使用外键约束



7. 设置表的属性值自增

- 方法1 使用序列

```sql
-- 第一步：创建序列
CREATE REQUENCE seq_test
STASRT WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

-- 第二步：
CREATE TABLE tb_test (
	a	int	NOT NULL DEFAULT nextval('seq_text'),
  b	text	NOT NULL,
  c	timestamp
);
```

- 方法2 使用serial数据类型

```sql
CREATE TABLE tb_test (
	a	serial	NOT NULL,
  b	text	NOT NULL,
  c	timestamp
);
```

8. 查看表结构

```sql
\d tb_name
```

### 修改数据表

1. 修改表名

```sql
ALTER TABLE tb_name RENAME TO new_name;
```

2. 修改字段名

```sql
ALTER TABLE tb_name RENAME COLUMN col_name to new_name;
```

3. 添加字段

```sql
```

4. 删除字段



5. 删除表的外键约束

```sql
ALTER TABLE tb_name DROP CONSTRAINT key_name;
```

### 删除数据表

```sql
DROP TABLE IF EXISTS tb_name;

-- 删除被键关联的主表:只会删除外键约束，不会删除子表或子表中的记录。
DROP TABLE IF EXISTS tb_name CASCADE;
```

### 数据操作

```sql
-- 插入数据
insert into department values(null, 'it', '2021');
insert into department(name, year) values('it', '2021');

-- 修改数据
UPDATE tb_name set col_name = '2021' where id = '1';

-- 删除数据
DELETE FROM tb_name WHERE col_name = value
```

### 数据查询

```sql
SELECT * FROM tb_name WHERE col_name = value;
```

## 5. 数据类型及运算符

### 数值

- 整型 smallint	integer	bigint
- 任意精度数字类型（精确） numeric(precision,scale)  decimal ，两者使用等价
  - precision 总共的位数
  - scale 小数部分的位数
- 浮点类型（不精确） real、 double precision、float和float(n) n指以二进制位表示的最低可接受精度
  - Infinity 正无穷大
  - -Infinity 负无穷大
  - NaN 非数字
- 序列类型（可作为自增整数时的类型） smallserial、serial、bigserial
- 货币类型 money
  - lc_monetary 指定货币符号对应的地区。默认'en_US.utf8'美元，可设置为'zh_CN.utf8'人民币

### 字符串

- 类型 char varchar text
- 连接运算符 `||`,返回的是text格式的结果
- 模式匹配运算符
  - LIKE
    - _ 表示匹配任何单个字符
    - % 表示零或更多个字符的序列
    - 转义字符串 `\`
    - ESCAPE 声明想要使用其他转义字符
  - SIMILAR TO
    - _ 匹配任何单个字符
    - % 匹配更多个字符的序列
    - | 选择
    - *零次或更多
    - +一次或更多
    - ?零次或一次
    - {m}重复前面的项m次
    - {m,}m次或更多次
    - {m,n}至少m次，最多n次
    - ()
    - []
  - POSIX 正则表达式
    - ~匹配正则，区分大小写
    - ~*匹配正则，不区分大小写
    - !~不匹配正则，区分大小写
    - !~*不匹配正则，不区分大小写

### 二进制

- 十六进制格式 十六进制以`\x`开头
- 二进制数据的转义格式 通常将他的数值转换成对应的三位八进制数，并加两个前导反斜线

### 日期和时间

- 类型 timestamp(p)、date、time、interval
- 日期类型输入
  - 配置datestyle参数指定顺序 MDY、DMY、YMD
  - 支持 ISO8601、SQL、Postgres
- 时间类型输入
  - 支持 ISO8601、SQL、Postgres
- 输出格式 支持 ISO8601、SQL、Postgres、German
- 时区
  - 一个完整的时区名字 如America/New_York，所有配置存储在 pg_timezone_names视图中
  - 一个时区的缩写 如PST，定义了到UTC的一个特定偏移。存储在 pg_timezone_abbrevs视图中
  - POSIX时区声明 STDoffset 或 DSToffset
    - STD 一个区域的缩写
    - DST 一个可选的下令时区域缩写
    - offset本地时间相对于UTC时间的偏移量
- 使用日期和时间类型的运算符
  - +
  - -
  - *
  - /

### 布尔

- 类型 true false unknown
- 运算符 and or not
- 比较谓词
  - between
  - not
  - is

### 位串

- bit(n), 不带n表示 bit(1)
- bit varying(n),不带n表示长度没有限制
- 支持使用位运算符

### 枚举

```sql
CREATE TYPE week AS ENUM('Mon','Tue','Wed','Thur','Fri','Sat','Sun');

-- 创建表，定义字段类型为枚举类型
CREATE TABLE T1(
	NAME TEXT,
  CURRENT_WEEK week
)
```

- 排序 根据定义时的 顺序排列
- 注意枚举类型安全性（可以自定义运算符，或者添加显示格式）

### 几何

- point 点 (x,y)
- line 线 {A,B,C}线性方程
- lseg 有限线段((x1,y1),(x2,y2))
- box 矩形框((x1,y1),(x2,y2))
- path 封闭路径((x1,y1),...)
- path 开放路径[(x1,y1),...]
- polygon 多边形((x1,y1),...) 多边形和封闭路径的存储方式不同
- circle 圆<(x,y),r>
- 运算符 P76

### JSON

- json
- jsonb 建议使用（处理速度要快很多，建议使用）
- 运算符 P80
  - ->获取字段值
  - ->>
  - #>获取指定路径的JSON对象
  - #>>

### 范围

- int4range integer的范围类型
- int8range bigint的范围类型
- numrange
- tsrange
- tstzrange带时区的timestamp的范围类型
- daterange
- 边界 []包含上下边界 ()排除上下边界
- 自定义范围类型

```sql
CREATE TYPE floatrange AS RANGE (
	subtype = float8,
  subtype_diff = float8mi
);
SELECT '[1.23, 5.67]'::floatrange;
```

### 数组

- 定义数组

```sql
CREATE TABLE payment (
	pay_by_month float[],
  schedule	text[][],
  squares	integer[2][3],
  name	float array[12]
);
```

- 输入数组常量

```sql
-- 格式1
{{1,2,3},{2,3,4}}

-- 格式2
ARRAY[1,2,3]
```

- array_dims(array, level)返回数组的维度
- array_upper(array, level) 返回数组的上界
- array_lower(array, level)返回数组的下界
- array_length(array, level) 返回数组维度的长度

- 访问数组
  -  下标从1开始
  - 数组切片
- 修改数组
  - 替换数组的所有元素
  - 更新数组中的一个元素
-  查找数组中的内容

```sql
-- 任意一个数组元素为10000
SELECT * FROM payment WHERE 10000 = ANY(pay_by_month);

-- 所有数组元素为10000
SELECT * FROM payment WHERE 10000 = ALL(pay_by_month);

-- generate_subscripts()函数
SELECT * FROM (
	SELECT pay_by_month, generate_subscripts(pay_by_month, 1) AS s
  	FROM payment) AS foo
WHERE pay_by_month[s] = 10000;
```

- 运算符 P89

## 6. 函数

### 数学函数 P90

- 绝对值函数 abs()
- 三角函数 参数和返回值是一个双精度的弧度
- 对数函数
  - In(dp or numeric) 自然对数
  - log(dp or numeric) 以10为底的对数
  - log(b numeric, x numeric) 以b为底的对数
- 随机数函数 random() 0.0<=x<1的值； setseed(dp)-1.0~1.0设置种子值

### 字符串函数

 

### 数据类型格式化函数



### 序列函数



### 日期函数和时间函数



### 位串函数



### 枚举函数



### 几何函数



### JSON函数



### 范围函数



### 数组函数



### 其他函数



## 7. 查询和修改数据

- UNION 两个结果的并集，并且有去重功能
- INTERSECT 两个结果的交集，并且有去重功能
- EXCEPT query1中的行，但是不在query2中，过滤重复的行

