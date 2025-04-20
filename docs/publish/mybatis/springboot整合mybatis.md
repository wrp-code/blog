## 一、springboot与mybatis-spring-boot版本兼容

- master(2.2.x) : MyBatis 3.5+, MyBatis-Spring 2.0+(2.0.6+ recommended), Java 8+ and Spring Boot 2.5+
- 2.1.x : MyBatis 3.5+, MyBatis-Spring 2.0+(2.0.6+ recommended), Java 8+ and Spring Boot 2.1-2.4
- ~~2.0.x : (EOL) MyBatis 3.5+, MyBatis-Spring 2.0+, Java 8+ and Spring Boot 2.0/2.1.~~
- ~~1.3.x : (EOL) MyBatis 3.4+, MyBatis-Spring 1.3+, Java 6+ and Spring Boot 1.5~~

## 二、springboot整合mybatis

### 1.pom.xml

```xml
<dependency>
  <groupId>org.mybatis.spring.boot</groupId>
	<artifactId>mybatis-spring-boot-starter</artifactId>
	<version>2.1.1</version>
</dependency>
<!--mysql数据库驱动-->
<dependency>
  <groupId>mysql</groupId>
  <artifactId>mysql-connector-java</artifactId>
  <scope>runtime</scope>
</dependency>
```

### 2.application.yml

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/test?characterEncoding=utf-8&useSSL=false
    username: root
    password: 123456

#mybatis的相关配置
mybatis:
  #mapper配置文件
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: com.wrp.entity
  #开启驼峰命名
  configuration:
    map-underscore-to-camel-case: true

# 显示sql
logging:
  level:
    com:
      wrp:
        mapper: debug
```

### 3.实体类

```java
@Data
public class User implements Serializable {
    private Long id;
    private String username;
    private String password;
}
```

### 4.mapper

```java
public interface UserMapper {
    List<User> findAll();

    User findById(Long id);

    void add(User user);

    void update(User user);

    void delete(Long id);
}
```

### 5.mapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.wrp.mapper.UserMapper">
    <select id="findAll" resultType="User">
        SELECT * FROM sys_user
    </select>

    <select id="findById" parameterType="long" resultType="user">
        select * from sys_user where id = #{id}
    </select>

    <insert id="add" parameterType="user" >
        insert into sys_user values (null, #{username}, #{password})
    </insert>

    <update id="update" parameterType="user">
        update sys_user
        <trim prefix="set" suffixOverrides=",">
        <if test="username != null and username != ''">username = #{username},</if>
        <if test="password != null and password != ''">password = #{password},</if>
        </trim>
        where id = #{id}
    </update>

    <delete id="delete" parameterType="long">
        delete from sys_user where id = #{id}
    </delete>
</mapper>
```

### 6.service

```java
public interface UserService {

    List<User> findAll();

    User findById(Long id);

    void add(User user);

    void update(User user);

    void delete(Long id);
}

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public User findById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public void add(User user) {
        userMapper.add(user);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public void delete(Long id) {
        userMapper.delete(id);
    }
}
```

### 7.controller

```java
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @GetMapping("/list")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User findAll(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @PostMapping("/add")
    public String add(@RequestBody User user) {
        userService.add(user);
        return "添加成功";
    }

    @PutMapping("/update")
    public String update(@RequestBody User user) {
        userService.update(user);
        return "修改成功";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "删除成功";
    }

}
```

### 8.扫描mapper

> 添加了@MapperScan，则在mapper类上可省略@Mapper

```java
@SpringBootApplication
@MapperScan("com.wrp.mapper")
public class MybatisWithSpringbootApplication {
   public static void main(String[] args) {
      SpringApplication.run(MybatisWithSpringbootApplication.class, args);
   }
}
```

