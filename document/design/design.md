# 设计模式

## 一、创建型模式

### 1、单例模式（Singleton Pattern）

单例模式（Singleton Pattern）是 Java 中最简单的设计模式之一。这种模式涉及到一个单一的类，该类负责创建自己的对象，同时确保只有单个对象被创建。这个类提供了一种访问其唯一的对象的方式，可以直接访问，不需要实例化该类的对象

#### 1.1 饿汉式

特点:类加载时就初始化,线程安全

```java
    public class Singleton {
    // 构造方法私有化
    private Singleton() {

    }

    // 饿汉式创建单例对象
    private static Singleton singleton = new Singleton();

    public static Singleton getInstance() {
        return singleton;
    }
}
```

#### 1.2 懒汉式

特点:第一次调用才初始化，避免内存浪费。

```java
    public class Singleton {
    /*
     * 懒汉式创建单例模式 由于懒汉式是非线程安全， 所以加上线程锁保证线程安全
     */
    private static Singleton singleton;

    public static synchronized Singleton getInstance() {
        if (singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }
}
```

#### 1.3 双重检验锁(double check lock)(DCL)

特点:安全且在多线程情况下能保持高性能

```java
public class Singleton {
    private volatile static Singleton singleton;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
```

#### 1.4 静态内部类

特点:效果类似DCL,只适用于静态域

```java
public class Singleton {
    private static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }

    private Singleton() {
    }

    public static final Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
```

#### 1.5 枚举

特点:自动支持序列化机制，绝对防止多次实例化

```java
public enum Singleton {
    INSTANCE;
}
```

#### 1.6 破坏单例的几种方式与解决方法

##### 1.6.1 反序列化

```java
public class Test {
    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance();
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D:/test.txt"));
        oos.writeObject(singleton);
        oos.flush();
        oos.close();

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("D:/test.txt"));
        Singleton singleton1 = (Singleton) ois.readObject();
        ois.close();
        System.out.println(singleton);//com.ruoyi.base.mapper.Singleton@50134894
        System.out.println(singleton1);//com.ruoyi.base.mapper.Singleton@5ccd43c2
    }
}

```

可以看到反序列化后，两个对象的地址不一样了，那么这就是违背了单例模式的原则了，解决方法只需要在单例类里加上一个readResolve()方法即可，原因就是在反序列化的过程中，会检测readResolve()
方法是否存在，如果存在的话就会反射调用readResolve()这个方法。

```java
public class Test {
    private Object readResolve() {
        return singleton;
    }
//com.ruoyi.base.mapper.Singleton@50134894
//com.ruoyi.base.mapper.Singleton@50134894
}

```

##### 1.6.2 反射

```java

public class Test {
    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance();
        Class<Singleton> singletonClass = Singleton.class;
        Constructor<Singleton> constructor = singletonClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        Singleton singleton1 = constructor.newInstance();
        System.out.println(singleton);//com.ruoyi.base.mapper.Singleton@32a1bec0
        System.out.println(singleton1);//com.ruoyi.base.mapper.Singleton@22927a81
    }
}
```

同样可以看到，两个对象的地址不一样，这同样是违背了单例模式的原则，解决办法为使用一个布尔类型的标记变量标记一下即可，代码如下：

```java

public class Test {
    private static boolean singletonFlag = false;

    private Singleton() {
        if (singleton != null || singletonFlag) {
            throw new RuntimeException("试图用反射破坏异常");
        }
        singletonFlag = true;
    }
}
```

但是这种方法假如使用了反编译，获得了这个标记变量，同样可以破坏单例，代码如下：

```java

public class Test {
    public static void main(String[] args) {
        Class<Singleton> singletonClass = Singleton.class;
        Constructor<Singleton> constructor = singletonClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        Singleton singleton = constructor.newInstance();
        System.out.println(singleton); // com.ruoyi.base.mapper.Singleton@32a1bec0

        Field singletonFlag = singletonClass.getDeclaredField("singletonFlag");
        singletonFlag.setAccessible(true);
        singletonFlag.setBoolean(singleton, false);
        Singleton singleton1 = constructor.newInstance();
        System.out.println(singleton1); // com.ruoyi.base.mapper.Singleton@5e8c92f4
    }
}
```

如果想使单例不被破坏，那么应该使用枚举的方式去实现单例模式，枚举是不可以被反射破坏单例的。

#### 1.7 容器式单例

当程序中的单例对象非常多的时候，则可以使用容器对所有单例对象进行管理，如下：

```java

public class ContainerSingleton {
    private ContainerSingleton() {
    }

    private static Map<String, Object> singletonMap = new ConcurrentHashMap<>();

    public static Object getInstance(Class clazz) throws Exception {
        String className = clazz.getName();
        // 当容器中不存在目标对象时则先生成对象再返回该对象
        if (!singletonMap.containsKey(className)) {
            Object instance = Class.forName(className).newInstance();
            singletonMap.put(className, instance);
            return instance;
        }
        // 否则就直接返回容器里的对象
        return singletonMap.get(className);
    }

    public static void main(String[] args) throws Exception {
        SafetyDangerLibrary instance1 = (SafetyDangerLibrary) ContainerSingleton.getInstance(SafetyDangerLibrary.class);
        SafetyDangerLibrary instance2 = (SafetyDangerLibrary) ContainerSingleton.getInstance(SafetyDangerLibrary.class);
        System.out.println(instance1 == instance2); // true
    }
}

```

#### 1.8 ThreadLocal单例

不保证整个应用全局唯一，但保证线程内部全局唯一，以空间换时间，且线程安全。


```java

public class ThreadLocalSingleton {
    private ThreadLocalSingleton(){}
    private static final ThreadLocal<ThreadLocalSingleton> threadLocalInstance = ThreadLocal.withInitial(() -> new ThreadLocalSingleton());
    public static ThreadLocalSingleton getInstance(){
        return threadLocalInstance.get();
    }
    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "-----" + ThreadLocalSingleton.getInstance());
            System.out.println(Thread.currentThread().getName() + "-----" + ThreadLocalSingleton.getInstance());
        }).start();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "-----" + ThreadLocalSingleton.getInstance());
            System.out.println(Thread.currentThread().getName() + "-----" + ThreadLocalSingleton.getInstance());
        }).start();
//        Thread-0-----com.ruoyi.library.domain.vo.ThreadLocalSingleton@53ac93b3
//        Thread-1-----com.ruoyi.library.domain.vo.ThreadLocalSingleton@7fe11afc
//        Thread-0-----com.ruoyi.library.domain.vo.ThreadLocalSingleton@53ac93b3
//        Thread-1-----com.ruoyi.library.domain.vo.ThreadLocalSingleton@7fe11afc
    }
}

```

可以看到上面线程0和1他们的对象是不一样的，但是线程内部，他们的对象是一样的，这就是线程内部保证唯一。

#### 1.9 总结

适用场景：

- 需要确保在任何情况下绝对只需要一个实例。如：ServletContext，ServletConfig，ApplicationContext，DBPool，ThreadPool等。

优点：

- 在内存中只有一个实例，减少了内存开销。
- 可以避免对资源的多重占用。
- 设置全局访问点，严格控制访问。

缺点：

- 没有接口，扩展困难。
- 如果要扩展单例对象，只有修改代码，没有其它途径。
