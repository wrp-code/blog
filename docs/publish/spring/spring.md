## 1. Spring概念

> Spring是轻量级的开源的JavaEE框架



架构图：

![archi](C:\Users\wrp\Pictures\Saved Pictures\spring.png)

spring5.2.6[下载地址](https://repo.spring.io/release/org/springframework/spring/)

核心包：

![包](C:\Users\wrp\Pictures\Saved Pictures\test.png)

## 2. IOC容器

### IOC是什么

控制反转，把对象交给spring管理，可以降低耦合度

### 原理

- xml解析
- 工厂模式
- 反射

### 接口结构

![impl](C:\Users\wrp\Pictures\Saved Pictures\ClassPathXmlApplicationContext.png)

- BeanFactory ：IOC原始接口，加在配置文件时，不创建对象，懒加载
- ApplicationContext：BeanFactory子接口，功能更强，加在配置文件时，创建对象。

### Bean管理

- Spring创建对象
- Spring注入属性



### 基于xml进行Bean管理

#### 创建对象

```xml
<bean id="user" class="com.wrp.spring5.User"></bean>
```

- 使用<bean>标签创建对象
- id：对象的唯一标识
- class：类的全路径名
- 默认使用无参构造函数

bean标签其他属性

- lazy-init 是否懒加载
- scope 是否单例 singleton/ prototype
- init-method 对象创建后，调用的初始化方法
- destory-mehtod 对象在销毁前，调用的销毁方法

#### 属性注入

- setXXX方式注入
- construction方式注入
- p 命名空间（property）方式注入(了解)
- c命名空间（constructor）方式注入(了解)

```xml
<bean id="book" class="com.wrp.spring5.Book">
    <property name="name" value="三国演义"></property>
    <property name="value" value="50"></property>
</bean>
```

property标签其他属性

- ref 属性的引用

```xml
<bean id="user" class="com.wrp.spring5.User" >
	<constructor-arg name="name" value="zs"></constructor-arg>
    <constructor-arg name="age" value="18"></constructor-arg>
</bean>
```

constructor-arg标签其他属性

- 

```xml
xmlns:p="http://www.springframework.org/schema/p"
xmlns:c="http://www.springframework.org/schema/c"

<bean id="user" class="com.wrp.spring5.User" p:name="zs" p:age="18" ></bean>
<bean id="user1" class="com.wrp.spring5.User" c:name="zs" c:age="18" ></bean>
```





### 基于注解进行Bean管理



## 3. AOP



## 4. JdbcTemplate



## 5. 事务管理



## 6. Spring5新特性

