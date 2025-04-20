## Eureka 服务注册和发现

> 和Consul、Zookeeper、Nacos类似，Eureka是一个用于服务注册和发现的组件。包括三个部分，服务注册中心，服务提供者和服务消费者

![eureka](C:\Users\wrp\Pictures\Saved Pictures\eureka.png)

### 代码

#### Eureka Server
##### pom



##### yml



##### 主程序启动类



##### 业务类

#### Eureka Client
##### pom



##### yml



##### 主程序启动类



##### 业务类



### eureka的延时

- Eureka Client 注册延时 40s
- Eureka Server 响应缓存 30s `eureka.server.response-cache-update-interval-ms`配置
- Eureka Client 服务注册表缓存 30s
- LoadBalancer 服务注册表缓存 30s `ribbon.ServerListRefreshInterval`配置

### eureka的自我保护模式

>进入自我保护模式，Eureka Server保护服务注册表中的信息，不再删除服务注册表中的数据。
>
>默认是开启的

```yaml
eureka:
  server:
    enable-self-preservation: false # 关闭自我保护模式
```

作用：

- 自我保护模式是一种对网络异常的安全保护措施.让Eureka集群更加的健壮、稳定.

![zwbh](C:\Users\wrp\Pictures\Saved Pictures\eureka自我保护.png)

## Ribbon 负载均衡

### 代码

#### pom

```xml
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
</dependency>
```

#### yml

```yaml
server:
  port: 8764

spring:
  application:
    name: eureka-ribbon-client

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

#### 主程序启动类

```java
@SpringBootApplication
@EnableEurekaClient
public class EurekaRibbonClientApplication {
    public static void main(String[] args) {
     SpringApplication.run(EurekaRibbonClientApplication.class, args);
    }
}

```

#### config

```java
@Configuration
public class RibbonConfig {
    @Bean
    @LoadBalanced// 开启负载均衡
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

#### 业务类

- RestTemplate + Ribbon 实现服务调用
- LoadBalancerClient 获取服务的相关信息

```java
@RestController
public class ClientController {

    private final String url = "http://eureka-client";
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("/hello/{name}")
    public String hi(@PathVariable("name") String name) {
        return restTemplate.getForObject(url + "/hello/" + name, String.class);
    }

    @GetMapping("/instance")
    public String instance() {
        ServiceInstance choose = loadBalancerClient.choose("eureka-client");
        return choose.getHost() + ":" + choose.getPort();
    }
}
```

#### 其他配置

```yaml
ribbon:
  eureka:
  	enabled: false #禁止ribbon从eureka获取服务注册表
  	
stores:
	ribbon:
	  listOfServers: example.com,google.com # 自定义ribbon服务注册表
```

### 负载均衡策略

| 策略类                    | 命名               | 描述                                                         |
| :------------------ | :------------------------ | :----------------------------------------------------------- |
| RandomRule                | 随机策略           | 随机选择server                                               |
| RoundRobinRule            | 轮询策略           | 轮询选择， 轮询index，选择index对应位置的Server；            |
| RetryRule                 | 重试策略           | 对选定的负载均衡策略机上重试机制，在一个配置时间段内当选择Server不成功，根据轮询的方式重试； |
| BestAvailableRule         | 最低并发策略       | 逐个考察server，如果server断路器打开，则忽略，再选择其中并发链接最低的server |
| AvailabilityFilteringRule | 可用过滤策略       | 过滤掉一直失败并被标记为circuit tripped的server，过滤掉那些高并发链接的server（active connections超过配置的阈值）或者使用一个AvailabilityPredicate来包含过滤server的逻辑，其实就就是检查status里记录的各个Server的运行状态； |
| ResponseTimeWeightedRule  | 响应时间加权重策略 | 根据server的响应时间分配权重，响应时间越长，权重越低，被选择到的概率也就越低。响应时间越短，权重越高，被选中的概率越高，这个策略很贴切，综合了各种因素，比如：网络，磁盘，io等，都直接影响响应时间 |
| ZoneAvoidanceRule         | 区域权重策略       | 综合判断server所在区域的性能，和server的可用性，轮询选择server并且判断一个AWS Zone的运行性能是否可用，剔除不可用的Zone中的所有server |

### 源码

![ribbon](C:\Users\wrp\Pictures\Saved Pictures\ribbon.png)

```java
public interface ServiceInstanceChooser {
  // 通过服务id选择服务
    ServiceInstance choose(String serviceId);
}

```

## Feign 声明式调用



## OpenFeign



## Hystrix



## Zuul



## Config



## Sleuth



## Springboot-Admin



## Springboot-Security



## OAuth2



