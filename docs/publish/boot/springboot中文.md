## 使用SpringBoot

> 本章将深入到关于怎样使用SpringBoot的更多细节中，这里有很多建议会让你的开发更容易。

### 1.构建系统

​	强烈建议使用一个支持依赖管理的构建系统如 Maven和 Gradle。

#### 1.1依赖管理

​	SpringBoot提供了一个依赖的版本列表，在`spring-boot-dependencies`中。如果需要可以重写版本号，但非常不建议你指定Spring Framework的版本。

#### 1.2Maven

#### 1.3Gradle

#### 1.4Ant

#### 1.5 启动器

​	启动器是一个方便的依赖集合，他的依赖是可传递的。其中，官方的启动器都是 `spring-boot-starter-*`,而第三方的启动器应该是`*-spring-boot-starter`。

| 名称                                     | 描述                                                  |
| ---------------------------------------- | ----------------------------------------------------- |
| `spring-boot-starter`                    | 核心启动了器，包括自动配置、日志和yaml                |
| `spring-boot-starter-activemq`           | ActiveMQ的JMS消息服务                                 |
| `spring-boot-starter-qmqp`               | Rabbit MQ 和Spring AMQP                               |
| `spring-boot-starter-artemis`            | Artemis的JMS                                          |
| `spring-boot-starter-batch`              | 使用Spring Batch                                      |
| `spring-boot-starter-cache`              | 使用Spring Framework的缓存                            |
| `spring-boot-starter-cassandra`          | Cassandra分布式数据库和Spring Data Cassandra          |
| `spring-boot-starter-cassandra-reactive` | Cassandra分布式数据库和Spring Data Cassandra Reactive |
|                                          |                                                       |
|                                          |                                                       |
|                                          |                                                       |
|                                          |                                                       |
|                                          |                                                       |
|                                          |                                                       |
|                                          |                                                       |
|                                          |                                                       |
|                                          |                                                       |
|                                          |                                                       |
|                                          |                                                       |
|                                          |                                                       |
|                                          |                                                       |
|                                          |                                                       |
|                                          |                                                       |
|                                          |                                                       |
|                                          |                                                       |
|                                          |                                                       |
|                                          |                                                       |
|                                          |                                                       |
|                                          |                                                       |
|                                          |                                                       |
|                                          |                                                       |
|                                          |                                                       |
|                                          |                                                       |
|                                          |                                                       |
|                                          |                                                       |
|                                          |                                                       |
|                                          |                                                       |
|                                          |                                                       |
|                                          |                                                       |
|                                          |                                                       |
|                                          |                                                       |
|                                          |                                                       |
|                                          |                                                       |



### 2.架构你的代码

#### 2.1使用默认包

默认包指的是没有`package`声明的类所处的位置，不推荐使用，应避免。推荐使用反向域名的名称作为包命名规范，如`com.baidu.*`

#### 2.2定位主程序启动类

​	强烈建议把主类（标识了`@SpringBootApplication`）放在根包路径下。他可以去扫描根路径下的其他类，如标注了@Entity的类。

​	其中@`SpringBootApplication`等价与`@EnableAutoCOnfiguration`和`@Component`注解

### 3.配置类

#### 3.1导入额外的配置类

#### 3.2导入xml配置

### 4.自动配置

#### 4.1逐步替代自动配置



#### 4.2禁用特定的自动配置类



### 5.SpringBean和依赖注入



### 6.使用@SpringBootApplicaiton注解



### 7.运行你的程序

#### 7.1ide运行

#### 7.2打了包运行

#### 7.3使用maven插件

#### 7.4使用gradle插件

#### 7.5热交换

### 8.开发者工具

#### 8.1属性默认值



#### 8.2自动重启

##### 8.2.1在条件评估中打印变化

##### 8.2.2不包括资源

##### 8.2.3观察附加路径

##### 8.2.4禁用重启

##### 8.2.5使用触发文件

##### 8.2.6定制化重启类加载器

##### 8.2.7已知的限制

#### 8.3实时重加载

#### 8.4全局设置

##### 8.4.1配置文件系统监视器



#### 8.5远程应用

##### 8.5.1运行远程客户端应用

##### 8.5.2远程更新



### 9.打包你的生产应用



### 10.Next



## SpringBoot 特征

### 1.SpringApplication

### 1.1 启动失败



### 1.2懒初始化

### 1.3定制化Banner

### 1.4定制化SpringApplication



### 1.5流利的Builder API



### 1.6Application的可能



### 1.7Application事件和监听器



### 1.8Web环境



### 1.9访问Application参数



### 1.10使用ApplicationRunner或commandlinrunner



### 1.11Application退出



### 1.12管理功能



### 2.外部化配置



### 3.多文件



### 4.日志



### 5.国际化



### 6.JSON



### 7.开发Web应用



### 8.优雅的关机



### 9.RSocket



### 10.安全



### 11.使用SQL数据库



### 12.使用NoSQL技术



### 13.缓存



### 14.发送消息



### 15.RestTemplate调用REST服务



### 16.WebClient调用REST服务



### 17.验证



### 18.发送邮件



### 19.JTA分布式事务



### 20.Hazelcast分布式缓存



### 21.Quartz任务调度



### 22.任务执行和调度



### 23.Spring集成



### 24.Spring Session会话



### 25.JMX监控和管理



### 26.测试



### 27.WebSockets



### 28.Web服务



### 29.自定义自动配置



### 30.Kotlin支持



### 31.容器镜像



### 32.Next



## SpringBoot监控

