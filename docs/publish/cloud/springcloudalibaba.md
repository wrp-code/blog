## Nacos

> Naming Configuration Service 服务注册+配置中心
>
> 等价于：Eureka + Config + Bus

### 作为注册中心

#### 示例代码

##### pom

```xml
<dependency>
  <groupId>com.alibaba.cloud</groupId>
  <artifactId>spring-cloud-alibaba-nacos-discovery</artifactId>
</dependency>
```

##### yml

```yaml
server:
  port: 9001

spring:
  application:
    name: payment-provider

  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
```

##### 主启动类

```java
@SpringBootApplication
@EnableDiscoveryClient
public class Payment9001Application {
    public static void main(String[] args) {
        SpringApplication.run(Payment9001Application.class, args);
    }
}
```

##### 业务类

```java
@RestController
public class PaymentController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/provider/payment")
    public String payment() {
        return "payment, port:" + port;
    }
}
```



---

支持CP 、AP(默认)



### 作为配置中心

#### 概念

> bootstrap.yml优先级高于application.yml

#### dataId

> spring.profiles.active控制加载dev\test\prod等环境配置。

公式`${prefix}-${spring.profiles.active}.${file-extension}`

- `prefix` 默认为 `spring.application.name`的值。可以配置`spring.cloud.nacos.config.prefix`来指定
- `spring.profiles.active`当前环境对应的值。不建议省略
- `file-exetension`数据格式。通过`spring.cloud.nacos.config.file-extension`配置。目前支持`properties`和`yaml`类型

#### namespace命名空间

> 默认public

```yaml
spring:
  application:
    name: nacos-config-client
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848
      config:
        server-addr: localhost:8848
        file-extension: yml # 指定yml格式的配置,需与nacos配置中的后缀一致
        group: TEST_GROUP # 设置分组，默认为DEFAULT_GROUP
        namespace: 20b759a8-899e-4b15-b961-b73ba4007245 # 命名空间
```



#### group 分组

> 默认 DEFAULT_GROUP

```yaml
spring:
  application:
    name: nacos-config-client
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848
      config:
        server-addr: localhost:8848
        file-extension: yml # 指定yml格式的配置,需与nacos配置中的后缀一致
        group: TEST_GROUP # 设置分组，默认为DEFAULT_GROUP
```



#### 示例代码

##### pom

```xml
<dependency>
  <groupId>com.alibaba.cloud</groupId>
  <artifactId>spring-cloud-alibaba-nacos-discovery</artifactId>
</dependency>
<dependency>
  <groupId>com.alibaba.cloud</groupId>
  <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
</dependency>
```

##### yml

```yaml
### bootstrap.yaml
server:
  port: 3377

spring:
  application:
    name: nacos-config-client
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848
      config:
        server-addr: localhost:8848
        file-extension: yaml # 指定yaml格式的配置
```

```yaml
### application.yml
spring:
  profiles:
    active: dev # 开发环境
```



##### 主启动类

```java
@SpringBootApplication
@EnableDiscoveryClient
public class Payment9001Application {
    public static void main(String[] args) {
        SpringApplication.run(Payment9001Application.class, args);
    }
}
```

##### 业务类

```java
@RestController
@RefreshScope// 配置自动更新
public class PaymentController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/provider/payment")
    public String payment() {
        return "payment, port:" + port;
    }
}
```

### 集群

> Nacos使用了嵌入式数据库实现数据的存储。集群时存在数据一致性问题，可以使用mysql集中存储数据支持集群化部署

#### 架构

![nacos](C:\Users\wrp\Pictures\Saved Pictures\springcloudalibaba\nacos集群架构.png)

#### Nacos支持三种部署模式

- 单机模式-用于测试和单机测试
- 集群模式-用于生产环境，确保高可用
- 多集群模式-用于多数据中心场景

#### nacos + mysql存储

> nacos自带derby内嵌式数据库

整合步骤：

1. mysql（5.7+）创建数据库nacos_config
2. 执行..\nacos\conf\nacos-mysql.sql
3. 配置..\nacos\conf\application.properties

```properties
spring.datasource.platform=mysql
db.num=1
db.url.0=jdbc:mysql://localhost:3306/nacos_config?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true
db.user=root
db.password=123456
```

#### 集群配置

..\nacos\conf\cluster.conf文件，

ip为`hostname -i `命令能够识别

```conf
172.21.38.28:3333
172.21.38.28:4444
172.21.38.28:5555
```

..\nacos\conf\startup.sh文件

![nacos1](C:\Users\wrp\Pictures\Saved Pictures\springcloudalibaba\nacos启动命令修改1.png)

![nacos1](C:\Users\wrp\Pictures\Saved Pictures\springcloudalibaba\nacos启动命令修改2.png)

nacos启动

```bash
./startup.sh -p 3333
./startup.sh -p 4444
./startup.sh -p 5555
```



## Sentinel

### 特征

![特征](C:\Users\wrp\Pictures\Saved Pictures\springcloudalibaba\sentinel特征.png)

### 安装

`java -jar sentinel-dashboard-1.7.0.jar`

### 流量控制

![llkz](C:\Users\wrp\Pictures\Saved Pictures\springcloudalibaba\流量控制.png)



- 资源名 唯一名称，默认请求路径
- 针对来源 sentinel可以针对调用者进行限流，填写微服务名，默认default
- 阈值类型/单机阈值
  - QPS：每秒请求数量超过阈值，进行限流
  - 线程数：调用该api的线程数到达阈值，进行限流
- 是否集群
- 流控模式
  - 直接 直接限流
  - 关联 关联的资源达到阈值时，限流
    - 关联资源 关联的资源
  - 链路 指定资源从入口资源进来的流量到达阈值，限流（Api级别的针对来源）
- 流控效果
  - 快速失败 直接失败，抛出异常
  - Warm up 阈值/coldFactor(默认3)开始，经过预热时长后才会达到阈值
    - 预热时长 
  - 排队等待 超过超时时长，限流
    - 超时时长 

### 熔断降级

![jj](C:\Users\wrp\Pictures\Saved Pictures\springcloudalibaba\服务降级.png)

- 资源名
- 降级策略
  - RT 平均响应时间，秒级。超出阈值且在时间窗口内通过的请求 >=5，降级。窗口期过后关闭断路器 RT最大4900.(5s)
  - 异常比例 QPS >= 5 且异常比例（秒级）超过阈值时，降级。窗口期过后关闭
  - 异常数 异常数（分钟）超过阈值（设置大于等于60s）时，降级。窗口期过后关闭

### 热点key限流

![sentinelhotkey](C:\Users\wrp\Pictures\Saved Pictures\springcloudalibaba\sentinel1.png)

### @SentinelResource

- value 资源名
- blockHandler 违规配置（限流/降级）处理方法（和fallback同时配置时，blockHandler生效）
- blockHandlerClass 限流处理类 和blockHandler结合使用
- fallback 处理程序错误，兜底办法
- fallbackClass 处理程序错误类
- exceptionsToIgnore 忽略异常，不走fallback

### 系统规则

> 系统全局性的配置。不建议使用

![xtgz](C:\Users\wrp\Pictures\Saved Pictures\springcloudalibaba\xt.png)

- LOAD 针对linux、unix机器。建议：cpu core * 2.5
- RT 平均处理时间 单位ms
- 线程数
- 入口QPS
- CPU使用率

### 持久化

添加pom: sentinel-datasource-nacos

yml配置：datasource

## Seata

> Seata 是一款开源的分布式事务解决方案，致力于提供高性能和简单易用的分布式事务服务。

### 核心概念（1+3）：

- 分布式事务处理过程的唯一ID XID：Transaction ID 

- 三组件模型：

  - TC (Transaction Coordinator) - 事务协调者（老师的助理）

    维护全局和分支事务的状态，驱动全局事务提交或回滚。

  - TM (Transaction Manager) - 事务管理器（老师）

    定义全局事务的范围：开始全局事务、提交或回滚全局事务。

  - RM (Resource Manager) - 资源管理器（同学）

    管理分支事务处理的资源，与TC交谈以注册分支事务和报告分支事务的状态，并驱动分支事务提交或回滚。

