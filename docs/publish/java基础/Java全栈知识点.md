# 一、Java基础

## 1.1基础

### 1.1.1JDK、JRE、JVM之间的区别

- JDK Java开发工具包，提供给开发者使用；包含了JRE和各种Java工具和Java基础的类库
- JRE Java运行时环境，提供给用户使用；饱和了JVM、运行时类库和类加载器
- JVM Java虚拟机，所有Java应用在JVM上运行，是应用和操作系统之间的桥梁

工作方式：Java文件编译成class字节码文件，JVM加载执行。

![jvm](C:\Users\wrp\Pictures\Saved Pictures\JAVASE\Java全栈知识点\jvm.png)

### 1.1.2 数据类型

- 基本类型
  - byte			1字节		->		Byte
  - short          2字节		->		Short
  - int               4字节		->		Integer
  - long            8字节		->		Long
  - float            4字节		->		Float
  - double        8字节		->		Double
  - boolean                		->		Boolean
  - char             2字节		->		Character
- 枚举类型
- 引用类型

### 1.1.3 Java面向对象三大特性

- 封装 将数据和操作放在一起，尽量隐藏内部细节，只保留对外提供的接口
  - 减少耦合
  - 提高可重用性
  - 提高安全性
- 继承 is-a关系 猫继承动物
  - 遵循里氏替换原则（子类对象必须能够替换所有父类对象）
- 多态（条件：继承、重写、向上转型）
  - 编译时多态-重载
  - 运行时多态-定义的对象引用所指向的具体类型在运行期间才能确认

### 1.1.4 类图

各种关系的强弱顺序：

**泛化** **=** **实现** **>** **组合** **>** **聚合** **>** **关联** **>** **依赖** 

**1. 泛化关系（继承关系）**

![lt1](C:\Users\wrp\Pictures\Saved Pictures\JAVASE\Java全栈知识点\lt1.png)

**2. 实现关系**

![lt1](C:\Users\wrp\Pictures\Saved Pictures\JAVASE\Java全栈知识点\lt2.png)

**3. 聚合关系**

整体由部分组成，整体不存在了部分还是会存在：

- 成员变量

![lt1](C:\Users\wrp\Pictures\Saved Pictures\JAVASE\Java全栈知识点\lt3.png)

**4. 组合关系**

整体由部分组成，整体不存在了部分还是不存在了：

- 成员变量

![lt1](C:\Users\wrp\Pictures\Saved Pictures\JAVASE\Java全栈知识点\lt4.png)

**5. 关联关系**

一种静态关系，与运行过程的状态无关。表示不同类对象之间有关联：

- 成员变量

![lt1](C:\Users\wrp\Pictures\Saved Pictures\JAVASE\Java全栈知识点\lt5.png)

**6. 依赖关系**

依赖关系是在运行过程中起作用的：

- 局部变量、方法的参数或者对静态方法的调用

![lt1](C:\Users\wrp\Pictures\Saved Pictures\JAVASE\Java全栈知识点\lt6.png)

### 1.1.5 七大原则

- **单一职责原则** 一个类只做负责一个职责
- **开闭原则** 对扩展是开放的，而对修改是封闭
- **里氏替换原则** 当一个子类的实例能够替换任何其超类的实例时，它们之间才具有is-A关系
- **依赖倒置原则** 抽象不应该依赖于细节，细节应当依赖于抽象。
  - 面向接口、抽象类编程
- **接口隔离原则** 使用多个专门的接口，而不使用单一的总接口
- **迪米特原则** 最少知道原则，不要和陌生人说话，只与你的直接朋友通信；朋友有哪些：
  - 当前对象本身(this)；
  - 以参数形式传入到当前对象方法中的对象；
  - 当前对象的成员对象；
  - 如果当前对象的成员对象是一个集合，那么集合中的元素也都是朋友；
  - 当前对象所创建的对象

- **组合聚合复用原则 **尽量的使用组合和聚合，而不是继承关系

### 1.1.6 自动装箱、自动拆箱

​		装箱就是自动将基本数据类型转换为包装器类型；拆箱就是自动将包装器类型转换为基本数据类型。

```java
Integer x = 2;// 装箱 valueOf()
int y = x;// 拆箱 xxxValue()
```

- 装箱的过程会创建对应的对象，这个会消耗内存，所以装箱的过程会增加内存的消耗，影响性能（Integer|Short|Byte|Character都有缓存，可以适当优化性能， -128~127）
- 当 “==”运算符的两个操作数都是 包装器类型的引用，则是比较指向的是否是同一个对象，而如果其中有一个操作数是表达式（即包含算术运算）则比较的是数值（即会触发自动拆箱的过程）。

```java
Integer i1 = 100;
Integer i2 = 100;
Integer i3 = 200;
Integer i4 = 200;
System.out.println(i1 == i2);  //true 因为缓存-128 ~ 127，同一个对象
System.out.println(i3 == i4);  //false 创建了不同的对象

Boolean b1 = false;
Boolean b2 = false;
Boolean b3 = true;
Boolean b4 = true;
System.out.println(b1==b2);//true static final Boolean TRUE = new Boolean(true);
System.out.println(b3==b4);//true

Integer num1 = 400;
int num2 = 400;
Long num3 = 800L;
System.out.println(num1 == num2);//true 拆箱
System.out.println(num1.equals(num2));//true 装箱
System.out.println(num3 == (num1 + num2));//true 拆箱
System.out.println(num3.equals(num1 + num2));//false 变量类型不同

Integer integer = null;
int int1 = integer;// NullPointerException
```

### 1.1.7 基本数据类型的缓冲池

包装类在缓冲池内，值相同则对象相同；缓冲区外，对象都不同

- boolean values true and false

- all byte values

- short values between -128 and 127

- int values between -128 and 127

- char in the range \u0000 to \u007F

### 1.1.8 String

- 被final修饰，不可继承
- char数组存储数据，被final修饰，初始化后不可改变
- 可以缓存hash值，hashCode方法第二次调用时直接返回

- StringBuilder 线程不安全；StringBuffer 线程安全（sync实现）
- String.intern() 保证相同内容的字符串变量引用同一个内存对象

- 字符串常量池位置：
  - JDK7以前：在Perm Gen区(也就是方法区)中
  - JDK7及以后：在堆中
- switch，从JDK7开始，条件可以用string；不支持long型

### 1.1.9 继承

- 访问权限
  - private 自己可见
  - protected 子类可见
  - public 所有可见
  - default 包级可见

如果子类的方法重写了父类的方法，那么子类中该方法的访问级别不允许低于父类的访问级别。（保证里氏替换原则）

- 接口
  - JDK8，允许接口有默认实现
  - 成员变量都是 static final
  - 方法都是public

### 1.1.10 重写与重载

- 重写：子类实现了一个与父类在方法声明上完全相同的一个方法，用@Override注解，为了满足里氏替换原则，两个限制：
  - 子类方法的访问权限必须大于等于父类方法
  - 子类方法的返回类型必须是父类方法返回类型或其子类型

- 重载：方法名相同，参数不同

### 1.1.11 Object

```java
public final native Class<?> getClass()

public native int hashCode()

public boolean equals(Object obj)

protected native Object clone() throws CloneNotSupportedException

public String toString()

public final native void notify()

public final native void notifyAll()

public final native void wait(long timeout) throws InterruptedException

public final void wait(long timeout, int nanos) throws InterruptedException

public final void wait() throws InterruptedException

protected void finalize() throws Throwable {}
```

- equals 与 ==
  - 对于基本类型，== 判断两个值是否相等，基本类型没有 equals() 方法
  - 对于引用类型，== 判断两个变量是否引用同一个对象，而 equals() 判断引用的对象是否等价（值是否相同）
- hashCode
  - hash值相同的两个对象不一定相同，相反对象相同的hash值一定相同
  - 先比较hashCode再比较equals
  - 重写equals时，也要重写hashCode

- clone  不建议使用，可以使用拷贝构造函数或者拷贝工厂来拷贝一个对象
  - clone是Object的一个protected native方法
  - 一个类不重写clone，就不能调用clone方法
  - 如果要重写clone方法，需要实现Cloneable接口，否则调用时报CloneNotSupportedException
  - ==浅拷贝== 拷贝对象和原始对象的引用类型引用同一个对象
    - Object的clone方法默认实现的
  - ==深拷贝== 拷贝对象和原始对象的引用类型引用不同对象

![clone](C:\Users\wrp\Pictures\Saved Pictures\JAVASE\Java全栈知识点\clone.jpg)

### 1.1.12 final

- 修饰成员变量 ==> 常量
- 修饰成员方法 ==> 子类不能重写
- 修饰类             ==> 不允许继承

### 1.1.13 static

- 修饰变量 ==> 静态变量，类变量，内存中只有一份
- 修饰方法 ==> 静态方法
  - 只能访问静态方法和静态变量
  - 方法中不能有this和super关键字
- 静态语句块 类初始化时执行一次
- 静态内部类 非静态内部类依赖于外部类的实例，而静态内部类不需要。
  - 静态内部类不能访问外部类的非静态的变量和方法

- 静态导包 在使用静态变量和方法时不用再指明 ClassName，从而简化代码，但可读性大大降低，不推荐  `import static com.xxx.ClassName.*`

**初始化顺序**

静态变量和静态语句块的初始化顺序取决于它们在代码中的顺序

- 父类(静态变量、静态语句块)
- 子类(静态变量、静态语句块)
- 父类(实例变量、普通语句块)
- 父类(构造函数)
- 子类(实例变量、普通语句块)
- 子类(构造函数

## 1.2 泛型

JDK5提供泛型，为了消灭 `ClassCastException`

### 1.2.1为什么引入泛型

1. 代码复用，适用于多种数据类型执行相同的代码
2. 编译期进行类型检查，约束参数类型，更安全

### 1.2.2 种类

- 泛型类 <T> <K,V>
- 泛型接口 
- 泛型方法 `public <? extends Number>T foo(T value)`

![fx](C:\Users\wrp\Pictures\Saved Pictures\JAVASE\Java全栈知识点\fx.png)

### 1.2.3 泛型的上下限

- List<? extends T> 元素是T及其子类，只能取元素，取出来的只能被T，及其父类接收
- List<? super T> 元素是T及其父类，只能放T及其子类，取出来用Object接收，丢失类信息
- List＜?> 任意泛型，但不知道是什么类型

使用原则：

- 频繁往外读取内容的，适合用上界Extends，使用 <? extends T>
- 经常往里插入的，适合用下界Super，使用<? super T>

总结：

- extends 可用于返回类型限定，不能用于参数类型限定（换句话说：? extends xxx 只能用于方法返回类型限定，jdk能够确定此类的最小继承边界为xxx，只要是这个类的子类都能接收，但是传入参数无法确定具体类型，只能接受null的传入）。
- super 可用于参数类型限定，不能用于返回类型限定（换句话说：? supper xxx 只能用于方法传参，因为jdk能够确定传入为xxx的子类，返回只能用Object类接收）。
- ? 既不能用于方法参数传入，也不能用于方法返回。

```java
public class Fx {
    public static void main(String[] args) {
        // <? extends Man>
        // 1.不能放入任何类型的数据
        // 2.取出时，只能由Man及其父类接收
        List<? extends Man> list2 = new ArrayList<>();
        list2.add(new Human());// 报错
        list2.add(new Man());// 报错
        list2.add(new Father());// 报错
        list2.add(new Son());// 报错

        Human human =  list2.get(0);
        Man man = list2.get(0);
        Father father = list2.get(0);// 报错
        Son son = list2.get(0);// 报错

        // <? super Man>
        // 1.放入时，只能放Man，及其子类
        // 2.取出时，只能用Object接收。可以强转，但可能出现转换错误
        List<? super Man> list = new ArrayList<>();
        list.add(new Human());// 报错
        list.add(new Man());
        list.add(new Father());
        list.add(new Son());

        Object object = list.get(0);
        Human human2 = list.get(0);// 报错
        Man man2 = list.get(0);// 报错
        Father father2 = list.get(0);// 报错

        // <?> 任意元素
        // 不能放入任何元素，取出时只能由Object接收
        List<?> list3 = new ArrayList<>();
        list3.add(0);
        list3.add("af");

        Object o = list3.get(0);
    }
}


class Human {}

class Man extends Human{}

class Father extends Man {}

class Son extends Father {}
```

### 1.2.4 伪泛型策略

> JDK5加入泛型，为了兼容之前的版本，采用"伪泛型"策略，即Java在语法上支持泛型，但是在编译阶段会进行所谓的“**类型擦除**”（Type Erasure），将所有的泛型表示（尖括号中的内容）都替换为具体的类型（其对应的原生态类型），就像完全没有泛型一样。



### 1.2.5 类型擦除

**泛型的类型擦除原则**是：

- 消除类型参数声明，即删除`<>`及其包围的部分。
- 根据类型参数的上下界推断并替换所有的类型参数为原生态类型：如果类型参数是无限制通配符或没有上下界限定则替换为Object，如果存在上下界限定则根据子类替换原则取类型参数的最左边限定类型（即父类）。
- 为了保证类型安全，必要时插入强制类型转换代码。
- 自动产生“桥接方法”以保证擦除类型后的代码仍然具有泛型的“多态性”。

==如何擦除==

- 当类定义中的类型参数没有任何限制时，在类型擦除中直接被替换为Object，即形如`<T>`和`<?>`的类型参数都被替换为Object。

![lxcc1](C:\Users\wrp\Pictures\Saved Pictures\JAVASE\Java全栈知识点\lxcc1.png)

- 当类定义中的类型参数存在限制（上下界）时，在类型擦除中替换为类型参数的上界或者下界，比如形如`<T extends Number>`和`<? extends Number>`的类型参数被替换为`Number`，`<? super Number>`被替换为Object。

![lxcc1](C:\Users\wrp\Pictures\Saved Pictures\JAVASE\Java全栈知识点\lxcc2.png)

### 1.2.6 Q&A

1. 如何证明类型的擦除？

- 通过反射添加其它类型元素

```java
public class Test {

    public static void main(String[] args) throws Exception {

        ArrayList<Integer> list = new ArrayList<Integer>();

        list.add(1);  //这样调用 add 方法只能存储整形，因为泛型类型的实例为 Integer

        list.getClass().getMethod("add", Object.class).invoke(list, "asd");

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

}
```

2. 泛型在编译期检查

Java编译器是通过先检查代码中泛型的类型，然后在进行类型擦除，再进行编译

## 1.3 注解

> JDK1.5版本开始引入的一个特性，用于对代码进行说明，可以对包、类、接口、字段、方法参数、局部变量等进行注解

### 1.3.1 Java内置注解

- @Override 标明重写某个方法
- @Deprecated 标明某个类或方法过时
- @SuppressWarnings 标明要忽略的警告

### 1.3.2 元注解

jdk5增加

- @Target 描述注解的使用范围（ElementType类中定义）

```java
public enum ElementType {
 
    TYPE, // 类、接口、枚举类
 
    FIELD, // 成员变量（包括：枚举常量）
 
    METHOD, // 成员方法
 
    PARAMETER, // 方法参数
 
    CONSTRUCTOR, // 构造方法
 
    LOCAL_VARIABLE, // 局部变量
 
    ANNOTATION_TYPE, // 注解类
 
    PACKAGE, // 可用于修饰：包
 
    TYPE_PARAMETER, // 类型参数，JDK 1.8 新增
 
    TYPE_USE // 使用类型的任何地方，JDK 1.8 新增
 
}
```



- @Retention 描述注解保留的时间范围

```java
public enum RetentionPolicy {
 
    SOURCE,    // 源文件保留
    CLASS,       // 编译期保留，默认值
    RUNTIME   // 运行期保留，可通过反射去获取注解信息
}
```



- @Documented 描述在使用 javadoc 工具为类生成帮助文档时是否要保留其注解信息
- @Inherited 如果某个类使用了被@Inherited修饰的Annotation，则其子类将自动具有该注解。

jdk8增加

- @Repeatable 表示注解可重复注释。参数为指向存储注解

```java
// JDK8前

public @interface Authority {
     String role();
}

public @interface Authorities {
    Authority[] value();
}

public class RepeatAnnotationUseOldVersion {

    @Authorities({@Authority(role="Admin"),@Authority(role="Manager")})
    public void doSomeThing(){
    }
}

// JDK8
@Repeatable(Authorities.class)
public @interface Authority {
     String role();
}

public @interface Authorities {
    Authority[] value();
}

public class RepeatAnnotationUseNewVersion {
    @Authority(role="Admin")
    @Authority(role="Manager")
    public void doSomeThing(){ }
}
```



- @Native 使用 @Native 注解修饰成员变量，则表示这个变量可以被本地代码引用，常常被代码生成工具使用，不常用

### 1.3.3 AnnotatedElement 接口

```java
/*
 * @since 1.5
 * @author Josh Bloch
 */
public interface AnnotatedElement {
    /**
			判断该程序元素上是否包含指定类型的注解，存在则返回true，否则返回false。注意：此方法会忽略注解对应的注解容器。
     * @since 1.5
     */
    default boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return getAnnotation(annotationClass) != null;
    }

   /**
返回该程序元素上存在的、指定类型的注解，如果该类型注解不存在，则返回null。
     * @since 1.5
     */
    <T extends Annotation> T getAnnotation(Class<T> annotationClass);

    /**
返回该程序元素上存在的所有注解，若没有注解，返回长度为0的数组
     * @since 1.5
     */
    Annotation[] getAnnotations();

    /**
返回该程序元素上存在的、指定类型的注解数组。没有注解对应类型的注解时，返回长度为0的数组。若程序元素为类，当前类上找不到注解，且该注解为可继承的，则会去父类上检测对应的注解。
     * @since 1.8
     */
    default <T extends Annotation> T[] getAnnotationsByType(Class<T> annotationClass) {}

    /**
返回直接存在于此元素上的所有注解。
     * @since 1.8
     */
    default <T extends Annotation> T getDeclaredAnnotation(Class<T> annotationClass) {}

    /**
返回直接存在于此元素上的所有注解。
     * @since 1.8
     */
    default <T extends Annotation> T[] getDeclaredAnnotationsByType(Class<T> annotationClass) {}

    /**
返回直接存在于此元素上的所有注解及注解对应的重复注解容器。
     * @since 1.5
     */
    Annotation[] getDeclaredAnnotations();
}
```

### 1.3.4 自定义注解

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyMethodAnnotation {

    public String title() default "";

    public String description() default "";

}
```

### 1.3.5 类型注解 JSR 308

>1. 在java 8之前，注解只能是在声明的地方所使用，比如类，方法，属性；
>2. java 8里面，注解可以应用在任何地方
>3. **类型注解只是语法而不是语义，并不会影响java的编译时间，加载时间，以及运行时间，也就是说，编译成class文件的时候并不包含类型注解**

- 类型注解的作用
  - 支持在Java的程序中做强类型检查。配合插件式的check framework，可以在编译的时候检测出runtime error，以提高代码质量

- 新增注解
  - `ElementType.TYPE_USE` ElementType.TYPE + ElementType.TYPE_PARAMETER
  - `ElementType.TYPE_PARAMETER` 类型参数声明

### 1.3.6 原理



### 1.3.7 应用场景

1. 继承实现到注解实现 - Junit3到Junit4
1. 自定义注解 + aop实现日志的操作

## 1.4 异常

### 1.4.1 结构

![error](C:\Users\wrp\Pictures\Saved Pictures\JAVASE\Java全栈知识点\error.png)

### 1.4.2 基础
1. 分类
- 运行时异常-不可查异常 RuntimeException类及其子类
- 编译异常-可查异常 RuntimeException以外的异常，必须在编译前解决

2.  throws规则

方法签名上抛出异常，表示本方法可能抛出的异常

- 抛出可查异常，否则编译不通过；
- 不可查异常不用抛出
- 抛出异常的方法的调用者，如果不能处理异常，应该继续抛出
- 方法重写后抛出的异常不能比父类的异常大

3.  throw

在方法中抛出异常

- 场景：改变异常的类型

4. 自定义异常 
   1. 继承Exception或RuntimeException
   2. 提供一个无参构造函数和一个带有详细描述信息的构造函数
5. 捕获异常的方法
   1. try-catch 可以捕获多个异常(多个catch或者一个catch中用 | 分割，异常大小依次增大)
   2. try-catch-finally
   3. try-finally
   4. try-with-resource JDK7引入

6. finally后的代码是否执行
   1. catch了异常，finally后面的代码继续执行
   2. 没有catch到异常，finally后的代码不执行
7. finally中不执行的情况
   1. 在前面的代码中用了System.exit()退出程序。
   2. finally语句块中发生了异常。
   3. 程序所在的线程死亡。
   4. 关闭CPU。

8. try-with-resource

finally可能抛出异常，从而覆盖了原始异常。**JAVA 7 **提供了更优雅的方式来实现**资源的自动释放**，自动释放的资源需要是实现了 ==AutoCloseable 接口==的类。

```java
private  static void tryWithResourceTest(){
    try (Scanner scanner = new Scanner(new FileInputStream("c:/abc"),"UTF-8")){
        // code
    } catch (IOException e){
        // handle exception
    }
}
```

try代码块退出 -> 自动调用`canner.close()` -> 如果抛出异常，被抑制，调用`addSusppressed`把异常添加到原来的异常，可以通过`getSusppressed`获取被抑制的异常列表

### 1.4.3 原理

1. 异常表

```java
//javap -c Main
 public static void simpleTryCatch();
    Code:
       0: invokestatic  #3                  // Method testNPE:()V
       3: goto          11
       6: astore_0
       7: aload_0
       8: invokevirtual #5                  // Method java/lang/Exception.printStackTrace:()V
      11: return
    Exception table:
       from    to  target type
           0     3     6   Class java/lang/Exception
```

2. JVM 如何处理异常

- 1.JVM会在当前出现异常的方法中，查找异常表，是否有合适的处理者来处理

- 2.如果当前方法异常表不为空，并且异常符合处理者的from和to节点，并且type也匹配，则JVM调用位于target的调用者来处理。

- 3.如果上一条未找到合理的处理者，则继续查找异常表中的剩余条目

- 4.如果当前方法的异常表无法处理，则向上查找（弹栈处理）刚刚调用该方法的调用处，并重复上面的操作。

- 5.如果所有的栈帧被弹出，仍然没有处理，则抛给当前的Thread，Thread则会终止。

- 6.如果当前Thread为最后一个非守护线程，且未处理异常，则会导致JVM终止运行。

3. 建立异常对象是普通对象的20倍，捕获异常是建立异常的4倍 (待测试)

```java
public class ExceptionTest {  
  
    private int testTimes;  
  
    public ExceptionTest(int testTimes) {  
        this.testTimes = testTimes;  
    }  
  
    public void newObject() {  
        long l = System.nanoTime();  
        for (int i = 0; i < testTimes; i++) {  
            new Object();  
        }  
        System.out.println("建立对象：" + (System.nanoTime() - l));  
    }  
  
    public void newException() {  
        long l = System.nanoTime();  
        for (int i = 0; i < testTimes; i++) {  
            new Exception();  
        }  
        System.out.println("建立异常对象：" + (System.nanoTime() - l));  
    }  
  
    public void catchException() {  
        long l = System.nanoTime();  
        for (int i = 0; i < testTimes; i++) {  
            try {  
                throw new Exception();  
            } catch (Exception e) {  
            }  
        }  
        System.out.println("建立、抛出并接住异常对象：" + (System.nanoTime() - l));  
    }  
  
    public static void main(String[] args) {  
        ExceptionTest test = new ExceptionTest(10000);  
        test.newObject();  
        test.newException();  
        test.catchException();  
    }  
}  
```

### 1.4.4 最佳实践

1. 只针对不正常的情况使用异常，不应用于正常的控制流
2. 在finally块中清理资源或者使用try-with-resource语句

3. 尽量使用标准的异常

4. 对异常进行文档说明
5. ==优先捕获最具体的异常==
6. 不要捕获Throwable类（Error等严重问题无法被处理）
7. 不要catch异常了，什么都不做（不好排查）
8. 不要记录并抛出异常（异常日志可能重复）
9. 包装异常时，不要抛弃原来的异常（用Throwable参数的构造器接收）
10. 不要使用异常控制程序的流程（影响性能）
11. 不要在finally中使用return（会覆盖掉原来的return）

## 1.5 反射

> 反射就是把java类中的各种成分映射成一个个的Java对象

![fs](C:\Users\wrp\Pictures\Saved Pictures\JAVASE\Java全栈知识点\fs.png)

### 1.5.2 Class类对象的获取

1. 类名.class
2. 对象.getClass()
3. Class.forName(全类名);



## 1.6 SPI

> SPI Service Provider Interface，是JDK中的一种 **服务提供发现机制**，可以用来启用框架扩展和替换组件

### 1.6.1 SPI的原理

- 使用到`ServiceLoader`类
- 核心思想：**解耦**

![yl](C:\Users\wrp\Pictures\Saved Pictures\JAVASE\Java全栈知识点\java-advanced-spi-8.jpg)

### 1.6.2 SPI的使用规范

![yl](C:\Users\wrp\Pictures\Saved Pictures\JAVASE\Java全栈知识点\java-advanced-spi-2.jpg)

### 1.6.3 SPI机制的实例

- 接口

```java
public interface Driver {

    String load();
}
```

- MySQLDriver

```java
public class MysqlDriver implements Driver {
    @Override
    public String load() {
        return "mysql Driver is loading";
    }
}
```

- OracleDriver

```java
public class OracleDriver implements Driver{
    @Override
    public String load() {
        return "oracle driver is loading";
    }
}
```

- META-INF/services/com.example.demo.spi.Driver文件

```tex
com.example.demo.spi.MysqlDriver
com.example.demo.spi.OracleDriver
```

- Test

```java
public class DriverTest {
    public static void main(String[] args) {
        ServiceLoader<Driver> loader = ServiceLoader.load(Driver.class);
        Iterator<Driver> iterator = loader.iterator();
        while (iterator.hasNext()) {
            Driver driver = iterator.next();
            System.out.println(driver.load());;
        }
    }
}
```

- 结果

```tex
mysql Driver is loading
oracle driver is loading
```

### 1.6.4 SPI 缺陷

- 不能按需加载，需要遍历所有的实现并实例化，浪费资源
- 线程安全问题

## 1.7 集合框架

### 1.7.1 常用类



### 1.7.2 ArrayList源码

**底层数据结构**

```java
transient Object[] elementData;// 存放数据
private int size;//实际大小
```

**构造函数**

```java
// 最好不要指定初始容量小于10
public ArrayList(int initialCapacity) {
  if (initialCapacity > 0) {
    this.elementData = new Object[initialCapacity];
  } else if (initialCapacity == 0) {
    this.elementData = EMPTY_ELEMENTDATA;
  } else {
    throw new IllegalArgumentException("Illegal Capacity: "+
                                       initialCapacity);
  }
}

public ArrayList() {
  this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
}


public ArrayList(Collection<? extends E> c) {
  Object[] a = c.toArray();
  if ((size = a.length) != 0) {
    if (c.getClass() == ArrayList.class) {
      elementData = a;
    } else {
      elementData = Arrays.copyOf(a, size, Object[].class);
    }
  } else {
    // replace with empty array.
    elementData = EMPTY_ELEMENTDATA;
  }
}
```

**扩容机制**

每次add前判断容量是否满足，否则扩容，每次扩到1.5倍

```java
// 确定容量
private void ensureCapacityInternal(int minCapacity) {
  ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
}

// 调用无参构造情况：确保初始容量大于等于10
private static int calculateCapacity(Object[] elementData, int minCapacity) {
  if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
    return Math.max(DEFAULT_CAPACITY, minCapacity);
  }
  return minCapacity;
}

// 如果最小容量大于elementData的长度，则扩容
private void ensureExplicitCapacity(int minCapacity) {
  modCount++;

  if (minCapacity - elementData.length > 0)
    grow(minCapacity);
}

// 一般情况下，扩容到1.5倍
private void grow(int minCapacity) {
  int oldCapacity = elementData.length;
  int newCapacity = oldCapacity + (oldCapacity >> 1);
  if (newCapacity - minCapacity < 0)
    newCapacity = minCapacity;
  if (newCapacity - MAX_ARRAY_SIZE > 0)
    newCapacity = hugeCapacity(minCapacity);
  elementData = Arrays.copyOf(elementData, newCapacity);
}

// 确保容量不超过Integer.MAX_VALUE
private static int hugeCapacity(int minCapacity) {
  if (minCapacity < 0) // overflow
    throw new OutOfMemoryError();
  return (minCapacity > MAX_ARRAY_SIZE) ?
    Integer.MAX_VALUE :
  MAX_ARRAY_SIZE;
}
```

### 1.7.3 LinkedList



### 1.7.4 Stack & Queue

> 如果需要使用stack或queue时，官方==推荐使用ArrayDeque==，其效率高于Stack和LinkedList

**Queue**

> 是一个接口，集成自Collection接口。有两组api，一组抛出异常的实现；一组返回值的实现

|      | 抛出异常  | 返回指定值 |
| ---- | --------- | ---------- |
| 插入 | add(e)    | offer(e)   |
| 移除 | remove()  | poll()     |
| 查看 | element() | peek()     |

**Deque**

> 是一个接口，表示双端队列。有四组api

|      | 抛出异常  | 返回指定值 |
| ---- | --------- | ---------- |
| 插入 | add(e)    | offer(e)   |
| 移除 | remove()  | poll()     |
| 查看 | element() | peek()     |

1. 作为队列使用

| Queue Method | Equivalent Deque Method | 说明                                   |
| ------------ | ----------------------- | -------------------------------------- |
| add(e)       | addLast(e)              | 向队尾插入元素，失败则抛出异常         |
| offer(e)     | offerLast(e)            | 向队尾插入元素，失败则返回`false`      |
| remove()     | removeFirst()           | 获取并删除队首元素，失败则抛出异常     |
| poll()       | pollFirst()             | 获取并删除队首元素，失败则返回`null`   |
| element()    | getFirst()              | 获取但不删除队首元素，失败则抛出异常   |
| peek()       | peekFirst()             | 获取但不删除队首元素，失败则返回`null` |

2. 作为栈使用

| Queue Method | Equivalent Deque Method | 说明                                   |
| ------------ | ----------------------- | -------------------------------------- |
| push(e)      | addFirst(e)             | 向栈顶插入元素，失败则抛出异常         |
|              | offerFirst(e)           | 向栈顶插入元素，失败则返回`false`      |
| pop()        | removeFirst()           | 获取并删除队首元素，失败则抛出异常     |
|              | pollFirst()             | 获取并删除队首元素，失败则返回`null`   |
| peek()       | getFirst()              | 获取但不删除队首元素，失败则抛出异常   |
|              | peekFirst()             | 获取但不删除队首元素，失败则返回`null` |

**ArrayDeque**

> ArrayDeque和LinkedList是Deque的两个通用实现

特点：

- 循环数组实现
- 非线程安全的
- 不允许放入Null元素

技巧：

由于指针可能存在下标越界的情况，且当size = 8，head = 0时，再往Deque中添加头元素，可以使用`head = (head -1) & (elements.length -1)`来解决越界的问题

## 1.8多线程



## 1.9 IO/NIO

### 1.9.1 基础

1. 分类

- 字节流

![zjl](C:\Users\wrp\Pictures\Saved Pictures\JAVASE\Java全栈知识点\java-io-category-1.png)

- 字符流

![zjl](C:\Users\wrp\Pictures\Saved Pictures\JAVASE\Java全栈知识点\java-io-category-2.png)

2. 编码、解码

- GBK 编码中，中文字符占 2 个字节，英文字符占 1 个字节；
- UTF-8 编码中，中文字符占 3 个字节，英文字符占 1 个字节；
- UTF-16be 编码中，中文字符和英文字符都占 2 个字节。

Java中使用UTF-16be，为了让一个中文或者一个英文都能使用一个 char 来存储。

3. 操作分类

![zjl](C:\Users\wrp\Pictures\Saved Pictures\JAVASE\Java全栈知识点\java-io-category-3.png)

### 1.9.2装饰者模式



## 1.10 JDK新特性

## 1.11 JVM 



# 二、算法与数据结构

## 1.基础

