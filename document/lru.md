## 用LinkedHashMap实现LRU
### LRU（Least Recently Used）：
  最近最久未使用策略，优先淘汰最久未使用的数据，也就是上次被访问时间距离现在最久的数据。该策略可以保证内存中的数据都是热点数据，也就是经常被访问的数据，从而保证缓存命中率。

- 应用场景
  - 底层的内存管理，页面置换算法
  - 一般的缓存服务，memcache\redis之类
  - 部分业务场景


- [实操代码](./common/src/main/java/com/roilka/roilka/question/common/collection/LRU.java)
- 代码展示
```java
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName LRU
 * @Description 最近最少使用算法简单实现
 * @Author 75six
 * @Date 2020/5/27 15:25
 **/
public class LRU<K, V> {
    private LinkedHashMap<K,V> map;
    private int cacheSize;

    public LRU(int cacheSize){
        this.cacheSize = cacheSize;
        map = new LinkedHashMap<K,V>(16,0.75f,true){

            @Override
            protected boolean removeEldestEntry(Map.Entry eldest){
                if (cacheSize + 1 == map.size()){
                    return true;
                }else {
                    return false;
                }
            }
        };
    }

    public synchronized V get(K key){
        return map.get(key);
    }
    public synchronized void put(K key,V value){
        map.put(key, value);
    }
    public synchronized void clear(){
        map.clear();
    }
    public synchronized int usedSize(){
        return map.size();
    }
    public synchronized int freeSize(){
        return cacheSize - map.size();
    }
    public void print(){
        map.forEach((k,v) -> {
            System.out.print(v+"- -");
        });
        System.out.println();
    }
}

```

### 原理分析
#### 为什么会用LinkedHashMap?
 - LinkedHashMap底层就是用的【**HashMap**】加【**双链表**】实现的，而且本身已经实现了按照访问顺序的存储。

 - LinkedHashMap提供了一个公开的构造方法，可以根据入参设置是否进入排序（accessOrder）,如下

   ```java
   // LinkedHashMap 源码 
   public LinkedHashMap(int initialCapacity,
                            float loadFactor,
                            boolean accessOrder) {
           super(initialCapacity, loadFactor);
           this.accessOrder = accessOrder;
       }
   ```

   如果accessOrder为true，当map被访问时，被访问的元素将会被移到链表的头部，如下

   ```java
   // LinkedHashMap 源码
   public V get(Object key) {
           Node<K,V> e;
           if ((e = getNode(hash(key), key)) == null)
               return null;
           if (accessOrder)
               afterNodeAccess(e);
           return e.value;
       }
   // 链表分解重组，将传入的元素设置到头部
   void afterNodeAccess(Node<K,V> e) { // move node to last
           LinkedHashMap.Entry<K,V> last;
           if (accessOrder && (last = tail) != e) {
               LinkedHashMap.Entry<K,V> p =
                   (LinkedHashMap.Entry<K,V>)e, b = p.before, a = p.after;
               p.after = null;
               if (b == null)
                   head = a;
               else
                   b.after = a;
               if (a != null)
                   a.before = b;
               else
                   last = b;
               if (last == null)
                   head = p;
               else {
                   p.before = last;
                   last.after = p;
               }
               tail = p;
               ++modCount;
           }
       }
   ```

   

 - 此外，LinkedHashMap中本身就实现了一个方法removeEldestEntry用于判断是否需要移除最不常读取的数，方法默认是直接返回false，不会移除元素，所以需要【重写该方法】，即当缓存满后就移除最不常用的数。

   ```java
   // LinkedHashMap 源码
   protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
           return false;
       }
   // 自定义重写方法
   @Override
   protected boolean removeEldestEntry(Map.Entry eldest){
       if (cacheSize + 1 == map.size()){
           return true;
       }else {
           return false;
       }
   }
   ```

   ### 总结一下：

   ​	1、新数据插入到链表头部；

   ​	2、每当缓存命中（即缓存数据被访问），将数据移动到链表头部；

   ​	3、当链表满的时候将链表尾部的数据丢弃；

   > 注意：当前方式只能解决，最新使用的热点数据，并不能处理点击最多的情况。
