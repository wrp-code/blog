# 基于Idea + MybatisX插件快速构建Java项目  JMeter集成测试 压力测试

## 1 环境

### 1.1 Idea

### 1.2 MybatisX插件

![image-20230621140553413](C:\Users\13456\AppData\Roaming\Typora\typora-user-images\image-20230621140553413.png)

### 1.3 创建一个表

```sql
CREATE TABLE IF NOT EXISTS public.sys_user
(
    id serial,
    username character varying  NOT NULL,
    password character varying  NOT NULL,
    age smallint  NOT NULL,
    hobby character varying NOT NULL,
    CONSTRAINT sys_user_pkey PRIMARY KEY (id)
)
```

## 2 创建项目

### 2.1 引入依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
</dependency>

<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.5.2</version>
</dependency>
```

### 2.2 添加数据源

![image-20230621141848294](C:\Users\13456\AppData\Roaming\Typora\typora-user-images\image-20230621141848294.png)



### 2.3 使用MybatisX插件创建 mvc

![image-20230621141933257](C:\Users\13456\AppData\Roaming\Typora\typora-user-images\image-20230621141933257.png)

![image-20230621142158994](C:\Users\13456\AppData\Roaming\Typora\typora-user-images\image-20230621142158994.png)

![image-20230621142237688](C:\Users\13456\AppData\Roaming\Typora\typora-user-images\image-20230621142237688.png)

![image-20230621142352517](C:\Users\13456\AppData\Roaming\Typora\typora-user-images\image-20230621142352517.png)

注：检查一下生成的代码是否合意

```java
// 注意@TableName(value ="sys_user")及属性的类型
@TableName(value ="sys_user")
@Data
public class SysUser implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private Integer age;

    private String hobby;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
```

mapper类，可以加上@Mapper ，或在主类加上@MapperScan("com.wrp.jmeter.mapper")

```java
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}

@MapperScan("com.wrp.jmeter.mapper")
@SpringBootApplication
public class JmeterDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JmeterDemoApplication.class, args);
    }

}

```



### 2.4 开发controller

```java
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class SysUserController {

    private SysUserService sysUserService;

    @PostMapping("/register")
    public R register(@RequestBody RegisterUser registerUser) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(registerUser, sysUser);
        boolean saved = sysUserService.save(sysUser);
        if(saved) {
            return R.success();
        } else {
            return R.error("注册失败");
        }
    }

    @PostMapping("/login")
    public R login(@RequestBody LoginUser loginUser) {
        SysUser sysUser = sysUserService.login(loginUser);
        if(sysUser != null) {
            return R.success(sysUser.getId());
        } else {
            return R.error("登录失败");
        }
    }

    @GetMapping("/detail")
    public R detail(@RequestParam("id") Integer id) {
        return R.success(sysUserService.getById(id));
    }
}

```

其他代码省略，easy

## 3 Jmeter测试

> 模拟测试场景： 登录成功后根据id查询用户详情

1. 添加线程组
2. 添加登录接口Http请求取样器

![image-20230621153000827](C:\Users\13456\AppData\Roaming\Typora\typora-user-images\image-20230621153000827.png)

3. 添加前置处理器 请求头管理 乱码问题

```tex
Content-Type: application/json;charset=UTF-8
```

4. 添加后置处理器 JSON提取器

- apply to 应用范围
  - Main sample and sub-samples 主取样器及子取样器
  - Main sample only 仅主取样器
  - Sub-samples only 仅子取样器
  - JMeter Variable Name to use Jmeter变量名
- Names of Created variables 变量名
- Json Path expressions json路径表达式 $代表结果
- Match NO.(0 for Random) 

0表示随机；
n取第几个匹配值；
-1匹配所有。
若只要获取到匹配的第一个值，则填写1

- compute concatenation var(suffix_ALL) 如果找到许多结果，则插件将使用’ ， '分隔符将它们连接起来，并将其存储在名为 _ALL的var中
- Default Values 默认值

5. 添加详情接口Http请求取样器

使用后置处理器的变量 ${变量名}

![image-20230621153923101](C:\Users\13456\AppData\Roaming\Typora\typora-user-images\image-20230621153923101.png)

6. 添加查看结果树和汇总报告监听器

7. 配置线程组参数

三个线程，每个线程跑100次

![image-20230621154038179](C:\Users\13456\AppData\Roaming\Typora\typora-user-images\image-20230621154038179.png)

8. 查看结果树及汇总报告

![image-20230621154134890](C:\Users\13456\AppData\Roaming\Typora\typora-user-images\image-20230621154134890.png)

## 4 常用指标

### 4.1 QPS 

> Queries Per Second，意思是每秒查询率

主要针对查询接口

### 4.2 TPS

> Transactions Per Second，意思是每秒事务数

一个事务中，可能会调用多个接口

### 4.3 提高TPS的方法

1. 优化数据库
   1. 连接数
   2. ==SQL== 
   3. 索引
2. 优化服务的配置
   1. 优化数据库连接池参数
   2. 优化tomcat参数
   3. 优化JVM参数 -Xms -Xmx等
   4. ==优化代码==
3. 更好的硬件设备
4. 单机 -> 集群