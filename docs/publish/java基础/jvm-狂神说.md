## JVM探究

### JVM的位置

![image-20220130102925406](C:\Users\wrp\Pictures\Saved Pictures\jvm位置.png)

### JVM的体系结构

![架构简图](C:\Users\wrp\Pictures\Saved Pictures\jvm架构简图.png)

### 类加载器

类的继承结构：(组合方式实现，而不是使用继承)

![jvm](C:\Users\wrp\Pictures\Saved Pictures\jvm\jvm.png)

作用：加载Class文件

![类加载器作用](C:\Users\wrp\Pictures\Saved Pictures\ClassLoader作用.png)

类加载器的类型（虚拟机自带的加载器优先级最高，应用程序加载器最低）：

- 虚拟机自带的加载器
- 启动类（根）加载器 rt.jar
- 扩展类加载器 ext
- 应用程序（系统类）加载器 app

```java
// User类由AppClassLoader加载
// 父类是ExtClassLoader
// 父类的父类是null ，实际是BootClassLoader ，由C/C++ 写的，获取不到，故返回null
public class User {
    public static void main(String[] args) {
        User user = new User();
        ClassLoader classLoader = user.getClass().getClassLoader();
        System.out.println(classLoader);// sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println(classLoader.getParent());// sun.misc.Launcher$ExtClassLoader@355da254
        System.out.println(classLoader.getParent().getParent());// null
    }
}
```



### 双亲委派机制

作用：保证安全

加载顺序：App(委派父类Ext加载) -> Ext （委派父类Boot加载）-> Boot（if无法执行，抛出异常，通知子加载器） -> Ext（if无法加载，抛出异常，通知子加载器） -> App (app无法加载的话，抛出异常)-> ClassNotFound



```java
package java.lang;

/**
 * 错误: 在类 java.lang.String 中找不到 main 方法, 请将 main 方法定义为:
 *  public static void main(String[] args)
 *否则 JavaFX 应用程序类必须扩展javafx.application.Application
 */
public class String {
    public String toString() {
        return "hello";
    }

    public static void main(String[] args) {
        String str = new String();
        System.out.println(str.toString());
    }
}

```



### 沙箱安全机制(了解)



### Native

作用：说明Java的作用范围达不到了，会去调用底层c / c++的 库

步骤：

- 进入本地方法栈（内存区域中专门开辟的一块标记区域，登记native方法）
- 调用本地方法接口 JNI（作用：扩展Java的使用，融合不同的编程语言为Java所用！）
- 通过JNI加载本地方法库中的方法

### PC寄存器（了解）

每个线程都有一个程序计数器，是线程私有的。

### 方法区

方法区是被所有线程共享。

== 静态变量、常量、类信息（构造方法、接口定义）、运行时的常量池存在方法区中，但是 实例变量存在堆内存中，和方法区无关 ==

static/ final/ Class/ 常量池。

### 栈



### 三种JVM

### 堆

![堆](C:\Users\wrp\Pictures\Saved Pictures\堆.png)

Heap 一个JVM只有一个堆，堆内存的大小可以调节

类加载器读取了类文件后，类、方法、常量、变量~，保存我们所有的引用类型的真实对象。

堆内存中分为三个区域：

- 新生区（又分伊甸园区、幸存0区、幸存1区）
- 养老区
- 永久区 Perm（JDK8以后，改为元空间）

#### 永久区

这个区域存储的是Java运行时的环境，这个区域不存在垃圾回收，关闭JVM会释放内存。一个启动类，加载了大量的三方包，Tomcat部署了太多的应用，大量动态生成的反射类。不断的被加载，直到内存满，就会出现OOM;

- jdk1.6之前：永久代，常量池在方法区
- jdk1.7：永久代，但是慢慢退化了，`去永久代`，常量池在堆中
- jdk1.8之后：无永久代，常量池在元空间

GC垃圾回收主要是在伊甸园区和养老区

## 堆内存调优

MAT、Jprofiler作用：

- 分析Dump内存，快速定位内存泄漏；
- 获得堆中的数据
- 获得大的对象~
- 。。。

>-Xms1m -Xmx8m -XX:+HeapDumpOnOutOfMemoryError 在内存溢出时，dump出内存信息的文件。用Jprofiler查看

- -Xms 设置初始化内存分配大小1/64
- -Xmx 设置最大分配内存，默认1/4
- -XX:+PrintGCDetails 打印出GC信息
- -XX:+HeapDumpOnXXX XXX错误时，dump出内存信息的文件，分析原因。

## GC，常用算法

JVM在进行GC时，并不是对这三个区域统一回收。大部分时候，回收都是新生代~

- 新生代
- 幸存区（from to）
- 老年区

GC两种分类：

- 轻GC（普通GC，清理新生代、幸存区）
- 重GC（全局GC，清理除永久代外的所有区域）

---

算法：

- 引用计数法（计数器统计对象的使用情况，效率低，不用）
- 复制算法（通过幸存0区和幸存1区 from to 不停的GC处理；好处：没有内存碎片；坏处：浪费了内存空间、多了一半空间永远是空to；使用最佳场景：`对象存活度较低`的时候，新生区~）
- 标记清除算法 （扫描标记不被清除的对象，扫描清除没有被标记的对象；优点：不需要额外空间；缺点：二次扫描，效率低，内存碎片；）
- 标记压缩算法（扫描标记不被清除的对象，扫描压缩内存；优点：不需要额外空间；缺点：二次扫描，效率低；）



### 常用结论

- 一旦Eden区被GC后，就会是空的！
- 当一个对象经历`15`次普通GC后，都还没有死，将进入老年区
- -XX:maxTenuringThreshold=20 可以调整进入老年区的时间



## JMM 

Java Memory Model Java内存模型

作用：在虚拟机基础上进行的规范从而实现平台一致性，以达到Java程序能够“一次编写，到处运行”。

- 有序性
- 可见性 
- 原子性 volitile



![jmm](C:\Users\wrp\Pictures\Saved Pictures\JMM.png)



## 总结

内存效率：复制算法 > 标记清除算法 > 标记清除压缩算法（时间复杂度）

内存整齐度：复制算法 = 标记压缩算法 > 标记清除算法

内存利用率：标记压缩算法 = 标记清除算法 > 复制算法

> 没有最好的算法，只有最合适的算法 --> GC:分代收集算法

年轻代：

- 存活率低
- 复制算法

老年代：

- 区域大：存活率高
- 标记清除 + 标记压缩混合实现



多看面试题和《深入理解JVM》