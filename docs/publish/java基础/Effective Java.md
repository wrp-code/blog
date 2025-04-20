### 4.通过私有构造器强化不可实例化的能力

- 如果不显示指定构造器，则系统自动提供一个公有的无参构造器

```java
public class UtilityClass {
  private UtilityClass() {
    // 可以抛出异常
    throw new AssertionError();
  }
}
```

技巧：

1. 提供一个私有的构造器，防止外部实例化
2. 私有构造器内可以抛出异常，防止内部实例化

### 5.避免创建不必要的对象



### 6.消除过期的对象引用



### 7.避免使用终结方法





### 74.谨慎地实现Serializable接口

