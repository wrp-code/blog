## 集合和数组的区别

- 长度（集合可变，数组固定）
- 内容（集合存引用；数组存基本类型或引用类型）
- 元素（集合可以存储不同类型（一般还是存一种），数组只能存一种）

## Collection的方法

```java
boolean add(E e) 
添加元素  
boolean addAll(Collection<? extends E> c) 
将所有元素添加到此集合
boolean remove(Object o) 
移除元素
boolean removeAll(Collection<?> c) 
移除指定集合中的所有元素  
boolean contains(Object o) 
判断是否包含指定的元素  
boolean containsAll(Collection<?> c) 
判断是否包含指定 集合中的所有元素 
Iterator<E> iterator() 
返回此集合中的元素的迭代器。  
boolean retainAll(Collection<?> c) 
仅保留指定集合中的元素
int size() 
集合的元素个数
boolean isEmpty() 
判断是否为空
void clear() 
删除所有元素
Object[] toArray() 
集合转数组，返回Object[]  
<T> T[] toArray(T[] a) 
集合转数组。参数指定返回类型：toArray(new String[0]); 

default Spliterator<E> spliterator() 
创建一个Spliterator在这个集合中的元素。  
default Stream<E> stream() 
返回以此集合作为源的顺序 Stream 。  
default boolean removeIf(Predicate<? super E> filter) 
删除满足给定谓词的此集合的所有元素。  
default Stream<E> parallelStream() 
返回可能并行的 Stream与此集合作为其来源。 
```

