



## 1.并发和并行的区别



## 2.volatile

> volatile是Java虚拟机提供的轻量级的同步机制。

1. 保证可见性
2. 不保证原子性
3. 禁止指令重排

```java
// 验证volatile可见性
public class VolatileDemo {
    public static void main(String[] args) {
        MyData myData = new MyData();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.add60();
            System.out.println("data:" + myData.data);
        }, "aaa").start();

        while (myData.data == 0) {
					// 当data != 0 时,退出循环
        }

        System.out.println(Thread.currentThread().getName() + "\t data: " + myData.data);
    }
}

class MyData {
    volatile int data = 0;

    public void add60() {
        this.data = 60;
    }
}

```



## 3.JMM Java内存模型

> 是一组规则或规范，定义了程序中各个数据的访问方式。

![jmm](C:\Users\wrp\Pictures\Saved Pictures\jmm内存模型.png)

1. ==可见性== 线程a拷贝共享变量到本地内存修改后，把数据回写到主内存的同时，通知线程b重新读取修改了的共享变量
2. ==原子性==
3. ==有序性==

JMM关于同步的规范：

- 线程解锁前，必须把共享变量的值刷新回主内存
- 线程加锁前，必须读取主内存的最新值到自己的工作内存
- 加锁解锁是同一把锁

