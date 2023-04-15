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
    private ThreadLocalSingleton() {
    }

    private static final ThreadLocal<ThreadLocalSingleton> threadLocalInstance = ThreadLocal.withInitial(() -> new ThreadLocalSingleton());

    public static ThreadLocalSingleton getInstance() {
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

### 2.工厂方法模式（Factory Method）

#### 2.1 简单工厂模式

简单工厂模式不是23种设计模式之一，他可以理解为工厂模式的一种简单的特殊实现。

##### 2.1.1 基础版

```java

// 工厂类
public class CoffeeFactory {
    public Coffee create(String type) {
        if ("americano".equals(type)) {
            return new Americano();
        }
        if ("mocha".equals(type)) {
            return new Mocha();
        }
        if ("cappuccino".equals(type)) {
            return new Cappuccino();
        }
        return null;
    }
}

```

```java

// 产品基类
public interface Coffee {
}

// 产品具体类，实现产品基类接口
public class Cappuccino implements Coffee {
}
```

基础版是最基本的简单工厂的写法，传一个参数过来，判断是什么类型的产品，就返回对应的产品类型。但是这里有一个问题，就是参数是字符串的形式，这就很容易会写错，比如少写一个字母，或者小写写成了大写，就会无法得到自己想要的产品类了，同时如果新加了产品，还得在工厂类的创建方法中继续加if，于是就有了升级版的写法。

#### 2.1.2 升级版

```java

// 使用反射创建对象
// 加一个static变为静态工厂
public class Test {
    public static Coffee create(Class<? extends Coffee> clazz) throws Exception {
        if (clazz != null) {
            return clazz.newInstance();
        }
        return null;
    }
}

```

升级版就很好的解决基础版的问题，在创建的时候在传参的时候不仅会有代码提示，保证不会写错，同时在新增产品的时候只需要新增产品类即可，也不需要再在工厂类的方法里面新增代码了。

#### 2.1.3 总结

适用场景：

- 工厂类负责创建的对象较少。
- 客户端只需要传入工厂类的参数，对于如何创建的对象的逻辑不需要关心。

优点：

- 只需要传入一个正确的参数，就可以获取你所需要的对象，无须知道创建的细节。

缺点：

- 工厂类的职责相对过重，增加新的产品类型的时需要修改工厂类的判断逻辑，违背了开闭原则。
- 不易于扩展过于复杂的产品结构。

### 2.2 工厂方法模式

工厂方法模式是指定义一个创建对象的接口，让实现这个接口的类来决定实例化哪个类，工厂方法让类的实例化推迟到子类中进行。

工厂方法模式主要有以下几种角色：

- 抽象工厂（Abstract Factory）：提供了创建产品的接口，调用者通过它访问具体工厂的工厂方法来创建产品。
- 具体工厂（ConcreteFactory）：主要是实现抽象工厂中的抽象方法，完成具体产品的创建。
- 抽象产品（Product）：定义了产品的规范，描述了产品的主要特性和功能。
- 具体产品（ConcreteProduct）：实现了抽象产品角色所定义的接口，由具体工厂来创建，它和具体工厂之间一一对应。

```java


// 抽象工厂
public interface CoffeeFactory {
    Coffee create();
}

// 具体工厂
public class CappuccinoFactory implements CoffeeFactory {
    @Override
    public Coffee create() {
        return new Cappuccino();
    }
}

// 抽象产品
public interface Coffee {
}

// 具体产品
public class Cappuccino implements Coffee {

}

```

#### 2.2.2 总结

适用场景：

- 创建对象需要大量的重复代码。
- 客户端（应用层）不依赖于产品类实例如何被创建和实现等细节。
- 一个类通过其子类来指定创建哪个对象。

优点：

- 用户只需要关系所需产品对应的工厂，无须关心创建细节。
- 加入新产品符合开闭原则，提高了系统的可扩展性。

缺点：

- 类的数量容易过多，增加了代码结构的复杂度。
- 增加了系统的抽象性和理解难度。

### 3.抽象工厂模式（Abstract Factory）

抽象工厂模式是指提供一个创建一系列相关或相互依赖对象的接口，无须指定他们具体的类。

抽象工厂模式的主要角色如下：

- 抽象工厂（Abstract Factory）：提供了创建产品的接口，它包含多个创建产品的方法，可以创建多个不同等级的产品。
- 具体工厂（Concrete Factory）：主要是实现抽象工厂中的多个抽象方法，完成具体产品的创建。
- 抽象产品（Product）：定义了产品的规范，描述了产品的主要特性和功能，抽象工厂模式有多个抽象产品。
- 具体产品（ConcreteProduct）：实现了抽象产品角色所定义的接口，由具体工厂来创建，它同具体工厂之间是多对一的关系。

#### 3.1 代码实现

```java

// 咖啡店 抽象工厂
public interface CoffeeShopFactory {

    // 咖啡类
    Coffee createCoffee();

    // 甜点类
    Dessert createDessert();
}

// 美式风格工厂
public class AmericanFactory implements CoffeeShopFactory {
    @Override
    public Coffee createCoffee() {
        return new Americano();
    }

    @Override
    public Dessert createDessert() {
        return new Cheesecake();
    }
}

// 意式风格工厂
public class ItalyFactory implements CoffeeShopFactory {
    @Override
    public Coffee createCoffee() {
        return new Cappuccino();
    }

    @Override
    public Dessert createDessert() {
        return new Tiramisu();
    }
}
```

#### 3.2 总结

适用场景：

- 客户端（应用层）不依赖于产品类实例如何被创建和实现等细节。
- 强调一系列相关的产品对象（属于同一产品族）一起使用创建对象需要大量重复的代码。
- 提供一个产品类的库，所有的产品以同样的接口出现，从而使客户端不依赖于具体实现。

优点：

- 当一个产品族中的多个对象被设计成一起工作时，它能保证客户端始终只使用同一个产品族中的对象。

缺点：

- 当产品族中需要增加一个新的产品时，所有的工厂类都需要进行修改。

### 4.原型模式（Prototype）

原型模式是指原型实例指定创建对象的种类，并且通过拷贝这些原型创建新的对象。调用者不需要知道任何创建细节，不调用构造函数。

原型模式包含如下角色：

- 抽象原型类：规定了具体原型对象必须实现的的 clone() 方法。
- 具体原型类：实现抽象原型类的 clone() 方法，它是可被复制的对象。
- 访问类：使用具体原型类中的 clone() 方法来复制新的对象。

```java

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Cloneable {
    private String name;
    private String sex;
    private Integer age;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static void main(String[] args) throws Exception {
        Student stu1 = new Student("张三", "男", 18);
        Student stu2 = (Student) stu1.clone();
        stu2.setName("李四");
        System.out.println(stu1);// Student(name=张三, sex=男, age=18)
        System.out.println(stu2);// Student(name=李四, sex=男, age=18)
    }
}
```

可以看到，把一个学生复制过来，只是改了姓名而已，其他属性完全一样没有改变，需要注意的是，一定要在被拷贝的对象上实现Cloneable接口，否则会抛出CloneNotSupportedException异常。

#### 4.1 浅克隆

创建一个新对象，新对象的属性和原来对象完全相同，对于非基本类型属性，仍指向原有属性所指向的对象的内存地址。

```java

@Data
public class Clazz implements Cloneable {
    private String name;
    private Student student;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {
    private String name;
    private String sex;
    private Integer age;

    public static void main(String[] args) throws Exception {
        Clazz clazz1 = new Clazz();
        clazz1.setName("高三一班");
        Student stu1 = new Student("张三", "男", 18);
        clazz1.setStudent(stu1);
        System.out.println(clazz1); // Clazz(name=高三一班, student=Student(name=张三, sex=男, age=18))
        Clazz clazz2 = (Clazz) clazz1.clone();
        Student stu2 = clazz2.getStudent();
        stu2.setName("李四");
        System.out.println(clazz1); // Clazz(name=高三一班, student=Student(name=李四, sex=男, age=18))
        System.out.println(clazz2); // Clazz(name=高三一班, student=Student(name=李四, sex=男, age=18))
    }
}


```

可以看到，当修改了stu2的姓名时，stu1的姓名同样也被修改了，这说明stu1和stu2是同一个对象，这就是浅克隆的特点，对具体原型类中的引用类型的属性进行引用的复制。同时，这也可能是浅克隆所带来的弊端，因为结合该例子的原意，显然是想在班级中新增一名叫李四的学生，而非让所有的学生都改名叫李四，于是我们这里就要使用深克隆。

#### 4.2 深克隆

创建一个新对象，属性中引用的其他对象也会被克隆，不再指向原有对象地址。

```java

@Data
public class Clazz implements Cloneable, Serializable {
    private String name;
    private Student student;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    protected Object deepClone() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this);
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return ois.readObject();
    }
}

public class Test {
    public static void main(String[] args) throws Exception {
        Clazz clazz1 = new Clazz();
        clazz1.setName("高三一班");
        Student stu1 = new Student("张三", "男", 18);
        clazz1.setStudent(stu1);
        Clazz clazz3 = (Clazz) clazz1.deepClone();
        Student stu3 = clazz3.getStudent();
        stu3.setName("王五");
        System.out.println(clazz1); // Clazz(name=高三一班, student=Student(name=张三, sex=男, age=18))
        System.out.println(clazz3); // Clazz(name=高三一班, student=Student(name=王五, sex=男, age=18))
    }
}

```

可以看到，当修改了stu3的姓名时，stu1的姓名并没有被修改了，这说明stu3和stu1已经是不同的对象了，说明Clazz中的Student也被克隆了，不再指向原有对象地址，这就是深克隆。这里需要注意的是，Clazz类和Student类都需要实现Serializable接口，否则会抛出NotSerializableException异常。

#### 4.3 克隆破坏单例与解决办法

PS：对上面的代码，稍加改造

```java
public class Test {
    // Clazz类
    private static Clazz clazz = new Clazz();

    private Clazz() {
    }

    public static Clazz getInstance() {
        return clazz;
    }

    // 测试
    public static void main(String[] args) throws Exception {
        Clazz clazz1 = Clazz.getInstance();
        Clazz clazz2 = (Clazz) clazz1.clone();
        System.out.println(clazz1 == clazz2); // false
    }
}
```

可以看到clazz1和clazz2并不相等，也就是说他们并不是同一个对象，也就是单例被破坏了。

解决办法也很简单，首先第一个就是不实现Cloneable接口即可，但是不实现Cloneable接口进行clone则会抛出CloneNotSupportedException异常。第二个方法就是重写clone()方法即可，如下：

```java

@Data
public class Clazz implements Cloneable, Serializable {
    private String name;
    private Student student;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return this;
    }

    protected Object deepClone() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this);
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return ois.readObject();
    }
}
```

另外我们知道，单例就是只有一个实例对象，如果重写了clone(）方法保证单例的话，那么通过克隆出来的对象则不可以重新修改里面的属性，因为修改以后就会连同克隆对象一起被修改，所以是需要单例还是克隆，在实际应用中需要好好衡量。

#### 4.4 总结

适用场景：

- 类初始化消耗资源较多。
- new产生的一个对象需要非常繁琐的过程（数据准备、访问权限等）。
- 构造函数比较复杂。
- 循环体中生产大量对象时。

优点：

- 性能优良，Java自带的原型模式是基于内存二进制流的拷贝，比直接new一个对象性能上提升了许多。
- 可以使用深克隆方式保存对象的状态，使用原型模式将对象复制一份并将其状态保存起来，简化了创建的过程。

缺点：

- 必须配备克隆（或者可拷贝）方法。
- 当对已有类进行改造的时候，需要修改代码，违反了开闭原则。
- 深克隆、浅克隆需要运用得当。

### 5.建造者模式（Builder）

建造者模式是将一个复杂对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示。用户只需指定需要建造的类型就可以获得对象，建造过程及细节不需要了解。

建造者（Builder）模式包含如下角色：

- 抽象建造者类（Builder）：这个接口规定要实现复杂对象的那些部分的创建，并不涉及具体的部件对象的创建。
- 具体建造者类（ConcreteBuilder）：实现 Builder 接口，完成复杂产品的各个部件的具体创建方法。在构造过程完成后，提供产品的实例。
- 产品类（Product）：要创建的复杂对象。
- 指挥者类（Director）：调用具体建造者来创建复杂对象的各个部分，在指导者中不涉及具体产品的信息，只负责保证对象各部分完整创建或按某种顺序创建。

#### 5.1 常规写法

```java

//产品类 电脑
@Data
public class Computer {
    private String motherboard;
    private String cpu;
    private String memory;
    private String disk;
    private String gpu;
    private String power;
    private String heatSink;
    private String chassis;
}

// 抽象 builder类（接口） 组装电脑
public interface ComputerBuilder {
    Computer computer = new Computer();

    void buildMotherboard();

    void buildCpu();

    void buildMemory();

    void buildDisk();

    void buildGpu();

    void buildHeatSink();

    void buildPower();

    void buildChassis();

    Computer build();
}

// 具体 builder类 华硕ROG全家桶电脑（手动狗头）
public class AsusComputerBuilder implements ComputerBuilder {
    @Override
    public void buildMotherboard() {
        computer.setMotherboard("Extreme主板");
    }

    @Override
    public void buildCpu() {
        computer.setCpu("Inter 12900KS");
    }

    @Override
    public void buildMemory() {
        computer.setMemory("芝奇幻峰戟 16G*2");
    }

    @Override
    public void buildDisk() {
        computer.setDisk("三星980Pro 2T");
    }

    @Override
    public void buildGpu() {
        computer.setGpu("华硕3090Ti 水猛禽");
    }

    @Override
    public void buildHeatSink() {
        computer.setHeatSink("龙神二代一体式水冷");
    }

    @Override
    public void buildPower() {
        computer.setPower("雷神二代1200W");
    }

    @Override
    public void buildChassis() {
        computer.setChassis("太阳神机箱");
    }

    @Override
    public Computer build() {
        return computer;
    }
}

// 指挥者类 指挥该组装什么电脑
@AllArgsConstructor
public class ComputerDirector {
    private ComputerBuilder computerBuilder;

    public Computer construct() {
        computerBuilder.buildMotherboard();
        computerBuilder.buildCpu();
        computerBuilder.buildMemory();
        computerBuilder.buildDisk();
        computerBuilder.buildGpu();
        computerBuilder.buildHeatSink();
        computerBuilder.buildPower();
        computerBuilder.buildChassis();
        return computerBuilder.build();
    }

}

public class Test {
    // 测试
    public static void main(String[] args) {
        ComputerDirector computerDirector = new ComputerDirector(new AsusComputerBuilder());
        // Computer(motherboard=Extreme主板, cpu=Inter 12900KS, memory=芝奇幻峰戟 16G*2, disk=三星980Pro 2T, gpu=华硕3090Ti 水猛禽, power=雷神二代1200W, heatSink=龙神二代一体式水冷, chassis=太阳神机箱)
        System.out.println(computerDirector.construct());
    }
}
```

上面示例是建造者模式的常规用法，指挥者类ComputerDirector在建造者模式中具有很重要的作用，它用于指导具体构建者如何构建产品，控制调用先后次序，并向调用者返回完整的产品类，但是有些情况下需要简化系统结构，可以把指挥者类和抽象建造者进行结合，于是就有了下面的简化写法。

#### 5.2 简化写法

```java
// 把指挥者类和抽象建造者合在一起的简化建造者类
public class SimpleComputerBuilder {
    private Computer computer = new Computer();

    public void buildMotherBoard(String motherBoard) {
        computer.setMotherboard(motherBoard);
    }

    public void buildCpu(String cpu) {
        computer.setCpu(cpu);
    }

    public void buildMemory(String memory) {
        computer.setMemory(memory);
    }

    public void buildDisk(String disk) {
        computer.setDisk(disk);
    }

    public void buildGpu(String gpu) {
        computer.setGpu(gpu);
    }

    public void buildPower(String power) {
        computer.setPower(power);
    }

    public void buildHeatSink(String heatSink) {
        computer.setHeatSink(heatSink);
    }

    public void buildChassis(String chassis) {
        computer.setChassis(chassis);
    }

    public Computer build() {
        return computer;
    }

}

public class Test {
    // 测试
    public static void main(String[] args) {
        SimpleComputerBuilder simpleComputerBuilder = new SimpleComputerBuilder();
        simpleComputerBuilder.buildMotherBoard("Extreme主板");
        simpleComputerBuilder.buildCpu("Inter 12900K");
        simpleComputerBuilder.buildMemory("芝奇幻峰戟 16G*2");
        simpleComputerBuilder.buildDisk("三星980Pro 2T");
        simpleComputerBuilder.buildGpu("华硕3090Ti 水猛禽");
        simpleComputerBuilder.buildPower("雷神二代1200W");
        simpleComputerBuilder.buildHeatSink("龙神二代一体式水冷");
        simpleComputerBuilder.buildChassis("太阳神机箱");
        // Computer(motherboard=Extreme主板, cpu=Inter 12900K, memory=芝奇幻峰戟 16G*2, disk=三星980Pro 2T, gpu=华硕3090Ti 水猛禽, power=雷神二代1200W, heatSink=龙神二代一体式水冷, chassis=太阳神机箱)
        System.out.println(simpleComputerBuilder.build());
    }
}
```

可以看到，对比常规写法，这样写确实简化了系统结构，但同时也加重了建造者类的职责，也不是太符合单一职责原则，如果construct() 过于复杂，建议还是封装到 Director 中。

#### 5.3 链式写法

```java

// 链式写法建造者类
public class SimpleComputerBuilder {
    private Computer computer = new Computer();

    public SimpleComputerBuilder buildMotherBoard(String motherBoard) {
        computer.setMotherboard(motherBoard);
        return this;
    }

    public SimpleComputerBuilder buildCpu(String cpu) {
        computer.setCpu(cpu);
        return this;
    }

    public SimpleComputerBuilder buildMemory(String memory) {
        computer.setMemory(memory);
        return this;
    }

    public SimpleComputerBuilder buildDisk(String disk) {
        computer.setDisk(disk);
        return this;
    }

    public SimpleComputerBuilder buildGpu(String gpu) {
        computer.setGpu(gpu);
        return this;
    }

    public SimpleComputerBuilder buildPower(String power) {
        computer.setPower(power);
        return this;
    }

    public SimpleComputerBuilder buildHeatSink(String heatSink) {
        computer.setHeatSink(heatSink);
        return this;
    }

    public SimpleComputerBuilder buildChassis(String chassis) {
        computer.setChassis(chassis);
        return this;
    }

    public Computer build() {
        return computer;
    }
}

public class Test {
    // 测试
    public static void main(String[] args) {
        Computer asusComputer = new SimpleComputerBuilder().buildMotherBoard("Extreme主板")
                .buildCpu("Inter 12900K")
                .buildMemory("芝奇幻峰戟 16G*2")
                .buildDisk("三星980Pro 2T")
                .buildGpu("华硕3090Ti 水猛禽")
                .buildPower("雷神二代1200W")
                .buildHeatSink("龙神二代一体式水冷")
                .buildChassis("太阳神机箱").build();
        System.out.println(asusComputer);
    }
}
```

可以看到，其实链式写法与普通写法的区别并不大，只是在建造者类组装部件的时候，同时将建造者类返回即可，使用链式写法使用起来更方便，某种程度上也可以提高开发效率。从软件设计上，对程序员的要求比较高。比较常见的mybatis-plus中的条件构造器就是使用的这种链式写法。

#### 5.4 总结

适用场景：

- 适用于创建对象需要很多步骤，但是步骤顺序不一定固定。
- 如果一个对象有非常复杂的内部结构（属性），把复杂对象的创建和使用进行分离。

优点：

- 封装性好，创建和使用分离。
- 扩展性好，建造类之间独立、一定程度上解耦。

缺点：

- 产生多余的Builder对象。
- 产品内部发生变化，建造者都要修改，成本较大。

与工厂模式的区别：

- 建造者模式更注重方法的调用顺序，工厂模式更注重创建对象。
- 创建对象的力度不同，建造者模式创建复杂的对象，由各种复杂的部件组成，工厂模式创建出来的都一样。
- 关注点不同，工厂模式只需要把对象创建出来就可以了，而建造者模式中不仅要创建出这个对象，还要知道这个对象由哪些部件组成。
- 建造者模式根据建造过程中的顺序不一样，最终的对象部件组成也不一样。

与抽象工厂模式的区别：

- 抽象工厂模式实现对产品族的创建，一个产品族是这样的一系列产品：具有不同分类维度的产品组合，采用抽象工厂模式则是不需要关心构建过程，只关心什么产品由什么工厂生产即可。
- 建造者模式则是要求按照指定的蓝图建造产品，它的主要目的是通过组装零配件而产生一个新产品。
- 建造者模式所有函数加到一起才能生成一个对象，抽象工厂一个函数生成一个对象

## 二、结构型模式

### 1.代理模式（Proxy Pattern）

代理模式是指为其他对象提供一种代理，以控制对这个对象的访问。代理对象在访问对象和目标对象之间起到中介作用。

Java中的代理按照代理类生成时机不同又分为静态代理和动态代理。静态代理代理类在编译期就生成，而动态代理代理类则是在Java运行时动态生成。动态代理又有JDK代理和CGLib代理两种。

代理（Proxy）模式分为三种角色：

- 抽象角色（Subject）： 通过接口或抽象类声明真实角色和代理对象实现的业务方法。
- 真实角色（Real Subject）： 实现了抽象角色中的具体业务，是代理对象所代表的真实对象，是最终要引用的对象。
- 代理角色（Proxy） ： 提供了与真实角色相同的接口，其内部含有对真实角色的引用，它可以访问、控制或扩展真实角色的功能。

#### 1.1 静态代理

静态代理就是指我们在给一个类扩展功能的时候，我们需要去书写一个静态的类，相当于在之前的类上套了一层，这样我们就可以在不改变之前的类的前提下去对原有功能进行扩展，静态代理需要代理对象和目标对象实现一样的接口。

```java

// 火车站接口，有卖票功能
public interface TrainStation {
    void sellTickets();
}

// 广州火车站卖票
public class GuangzhouTrainStation implements TrainStation {
    @Override
    public void sellTickets() {
        System.out.println("广州火车站卖票啦");
    }
}

// 代售点卖票（代理类）
public class ProxyPoint implements TrainStation {
    // 目标对象（代理火车站售票）
    private GuangzhouTrainStation station = new GuangzhouTrainStation();

    @Override
    public void sellTickets() {
        System.out.println("代售加收5%手续费");
        station.sellTickets();
    }

    public static void main(String[] args) {
        ProxyPoint proxyPoint = new ProxyPoint();
        // 代售加收5%手续费
        // 广州火车站卖票啦
        proxyPoint.sellTickets();
    }
}

public class Test {
    // 测试
    public static void main(String[] args) {
        ProxyPoint proxyPoint = new ProxyPoint();
        // 代售加收5%手续费
        // 火车站卖票啦
        proxyPoint.sellTickets();
    }
}

```

可以从上面代码看到，我们访问的是ProxyPoint对象，也就是说ProxyPoint是作为访问对象和目标对象的中介的，同时也对sellTickets方法进行了增强（代理点收取加收5%手续费）。

静态代理的优点是实现简单，容易理解，只要确保目标对象和代理对象实现共同的接口或继承相同的父类就可以在不修改目标对象的前提下进行扩展。

而缺点也比较明显，那就是代理类和目标类必须有共同接口(父类)，并且需要为每一个目标类维护一个代理类，当需要代理的类很多时会创建出大量代理类。一旦接口或父类的方法有变动，目标对象和代理对象都需要作出调整。

#### 1.2 动态代理

代理类在代码运行时创建的代理称之为动态代理。动态代理中代理类并不是预先在Java代码中定义好的，而是运行时由JVM动态生成，并且可以代理多个目标对象。

##### 1.2.1 jdk动态代理

JDK动态代理是Java JDK自带的一个动态代理实现， 位于java.lang.reflect包下。

```java


// 火车站接口，有卖票功能
public interface TrainStation {
    void sellTickets();
}

// 广州火车站卖票
public class GuangzhouTrainStation implements TrainStation {
    @Override
    public void sellTickets() {
        System.out.println("广州火车站卖票啦");
    }
}

// 深圳火车站卖票
public class ShenzhenTrainStation implements TrainStation {
    @Override
    public void sellTickets() {
        System.out.println("深圳火车站卖票啦");
    }
}

// 代售点卖票（代理类）
public class ProxyPoint implements InvocationHandler {
    private TrainStation trainStation;

    public TrainStation getProxyObject(TrainStation trainStation) {
        this.trainStation = trainStation;
        Class<? extends TrainStation> clazz = trainStation.getClass();
        return (TrainStation) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代售火车票收取5%手续费");
        return method.invoke(this.trainStation, args);
    }
}

public class Test {
    // 测试
    public static void main(String[] args) {
        ProxyPoint proxy = new ProxyPoint();
        TrainStation guangzhouTrainStation = proxy.getProxyObject(new GuangzhouTrainStation());
        // 代售火车票收取5%手续费
        // 广州火车站卖票啦
        guangzhouTrainStation.sellTickets();
        TrainStation shenzhenTrainStation = proxy.getProxyObject(new ShenzhenTrainStation());
        // 代售火车票收取5%手续费
        // 深圳火车站卖票啦
        shenzhenTrainStation.sellTickets();
    }

}

```

优点：

- 使用简单、维护成本低。
- Java原生支持，不需要任何依赖。
- 解决了静态代理存在的多数问题。

缺点：

- 由于使用反射，性能会比较差。
- 只支持接口实现，不支持继承， 不满足所有业务场景。

##### 1.2.2 CGLIB动态代理

CGLIB是一个强大的、高性能的代码生成库。它可以在运行期扩展Java类和接口，其被广泛应用于AOP框架中（Spring、dynaop）中， 用以提供方法拦截。CGLIB比JDK动态代理更强的地方在于它不仅可以接管Java接口，
还可以接管普通类的方法。

```xml
        <!-- 先引入cglib包 -->
<dependency>
    <groupId>cglib</groupId>
    <artifactId>cglib</artifactId>
    <version>${cglib-version}</version>
</dependency>
```

```java

// 代售点卖票（代理类）
public class ProxyPoint implements MethodInterceptor {
    public TrainStation getProxyObject(Class<? extends TrainStation> trainStation) {
        //创建Enhancer对象，类似于JDK动态代理的Proxy类，下一步就是设置几个参数
        Enhancer enhancer = new Enhancer();
        //设置父类的字节码对象
        enhancer.setSuperclass(trainStation);
        //设置回调函数
        enhancer.setCallback(this);
        //创建代理对象并返回
        return (TrainStation) enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("代售火车票收取5%手续费");
        return methodProxy.invokeSuper(o, objects);
    }
}

public class Test {
    // 测试
    public static void main(String[] args) {
        ProxyPoint proxy = new ProxyPoint();
        TrainStation guangzhouTrainStation = proxy.getProxyObject(GuangzhouTrainStation.class);
        // 代售火车票收取5%手续费
        // 广州火车站卖票啦
        guangzhouTrainStation.sellTickets();
        TrainStation shenzhenTrainStation = proxy.getProxyObject(ShenzhenTrainStation.class);
        // 代售火车票收取5%手续费
        // 深圳火车站卖票啦
        shenzhenTrainStation.sellTickets();
    }
}
```

#### 1.3 总结

应用场景：

- 保护目标对象。
- 增强目标对象。

优点：

- 代理模式能将代理对象与真实被调用的目标对象分离。
- 一定程度上降低了系统的耦合程度，易于扩展。
- 代理可以起到保护目标对象的作用。
- 增强目标对象的职责。

缺点：

- 代理模式会造成系统设计中类的数目增加。
- 在客户端和目标对象之间增加了一个代理对象，请求处理速度变慢。
- 增加了系统的复杂度。

两种动态代理的对比：

JDK动态代理的特点：

- 需要实现InvocationHandler接口， 并重写invoke方法。
- 被代理类需要实现接口， 它不支持继承。
- JDK 动态代理类不需要事先定义好， 而是在运行期间动态生成。
- JDK 动态代理不需要实现和被代理类一样的接口， 所以可以绑定多个被代理类。
- 主要实现原理为反射， 它通过反射在运行期间动态生成代理类， 并且通过反射调用被代理类的实际业务方法。

cglib的特点：

- cglib动态代理中使用的是FastClass机制。
- cglib生成字节码的底层原理是使用ASM字节码框架。
- cglib动态代理需创建3份字节码，所以在第一次使用时会比较耗性能，但是后续使用较JDK动态代理方式更高效，适合单例bean场景。
- cglib由于是采用动态创建子类的方法，对于final方法，无法进行代理。

### 2.适配器模式（Adapter Class/Object）

适配器模式，它的功能是将一个类的接口变成客户端所期望的另一种接口，从而使原本因接口不匹配而导致无法在一起工作的两个类能够一起工作。适配器模式分为类适配器模式和对象适配器模式，前者类之间的耦合度比后者高，且要求程序员了解现有组件库中的相关组件的内部结构，所以应用相对较少些。

适配器模式（Adapter）包含以下主要角色：

- 目标（Target）接口：当前系统业务所期待的接口，它可以是抽象类或接口。
- 适配者（Adaptee）类：它是被访问和适配的现存组件库中的组件接口。
- 适配器（Adapter）类：它是一个转换器，通过继承或引用适配者的对象，把适配者接口转换成目标接口，让客户按目标接口的格式访问适配者。

#### 2.1 类适配器

类适配器是通过定义一个适配器类来实现当前系统的业务接口，同时又继承现有组件库中已经存在的组件来实现的

```java

// 适配者 220V电压
public class AC220 {
    public int output() {
        System.out.println("输出220V交流电");
        return 220;
    }
}

// 目标 5V
public interface DC5 {
    public int output5();
}

// 适配器类（电源适配器）
public class PowerAdapter extends AC220 implements DC5 {
    @Override
    public int output5() {
        int output220 = super.output();
        int output5 = output220 / 44;
        System.out.println(output220 + "V适配转换成" + output5 + "V");
        return output5;
    }
}

public class Test {
    // 测试
    public static void main(String[] args) {
        PowerAdapter powerAdapter = new PowerAdapter();
        // 输出220V交流电
        powerAdapter.output();
        // 输出220V交流电
        // 220V适配转换成5V
        powerAdapter.output5();
    }
}
```

通过上面代码例子可以看出，类适配器有一个很明显的缺点，就是违背了合成复用原则。结合上面的例子，假如我不是220V的电压了，是380V电压呢？那就要多建一个380V电压的适配器了。同理，由于Java是单继承的原因，如果不断的新增适配者，那么就要无限的新增适配器，于是就有了对象适配器。

#### 2.2 对象适配器

对象适配器的实现方式是通过现有组件库中已经实现的组件引入适配器类中，该类同时实现当前系统的业务接口。

```java

// 电源接口
public interface Power {
    int output();
}

// 适配者 220V电压
public class AC220 implements Power {
    @Override
    public int output() {
        System.out.println("输出220V交流电");
        return 220;
    }
}

// 目标 5V
public interface DC5 {
    public int output5();
}

@AllArgsConstructor
public class PowerAdapter implements DC5 {

    // 适配者
    private Power power;

    @Override
    public int output5() {
        int output220 = power.output();
        int output5 = output220 / 44;
        System.out.println(output220 + "V适配转换成" + output5 + "V");
        return output5;
    }
}

public class Test {
    // 测试
    public static void main(String[] args) {
        DC5 powerAdapter = new PowerAdapter(new AC220());
        // 输出220V交流电
        // 220V适配转换成5V
        powerAdapter.output5();
    }
}
```

可以看到，上面代码中，只实现了目标接口，并没有继承适配者，而是将适配者类实现适配者接口，在适配器中引入适配者接口，当我们需要使用不同的适配者通过适配器进行转换时，就无需再新建适配器类了，如上面例子，假如我需要380V的电源转换成5V的，那么客户端只需要调用适配器时传入380V电源的类即可，就无需再新建一个380V电源的适配器了（PS：上述逻辑代码中output220
/ 44请忽略，可以根据实际情况编写实际的通用逻辑代码）。

#### 2.3 接口适配器

接口适配器主要是解决类臃肿的问题，我们可以把所有相近的适配模式的方法都放到同一个接口里面，去实现所有方法，当客户端需要哪个方法直接调用哪个方法即可。如上面例子所示，我们只是转换成了5V电压，那假如我要转换成12V，24V，30V...呢？那按照上面的写法就需要新建12V，24V，30V...的接口，这样就会导致类过于多了。那么我们就可以把5V，12V，24V，30V...这些转换方法，通通都写到一个接口里去，这样当我们需要转换哪种就直接调用哪种即可。

```java

// 这里例子 输出不同直流电接口
public interface DC {
    int output5();

    int output12();

    int output24();

    int output30();
}

// 适配器类（电源适配器）
@AllArgsConstructor
public class PowerAdapter implements DC {
    private Power power;

    @Override
    public int output5() {
        // 具体实现逻辑
        return 5;
    }

    @Override
    public int output12() {
        // 具体实现逻辑
        return 12;
    }

    @Override
    public int output24() {
        // 具体实现逻辑
        return 24;
    }

    @Override
    public int output30() {
        // 具体实现逻辑
        return 30;
    }
}
```

#### 2.4 总结

适用场景：

- 已经存在的类，它的方法和需求不匹配（方法结构相同或相似）的情况。
- 使用第三方提供的组件，但组件接口定义和自己要求的接口定义不同。

优点：

- 能提高类的透明性和复用，现有的类复用但不需要改变。
- 目标类和适配器类解耦，提高程序的扩展性。
- 在很多业务场景中符合开闭原则。

缺点：

- 适配器编写过程需要全面考虑，可能会增加系统的复杂性。
- 增加代码阅读难度，降级代码可读性，过多使用适配器会使系统代码变得凌乱。

### 3.装饰模式（Decorator Pattern）

装饰模式，是指在不改变原有对象的基础上，将功能附加到对象上，提供了比继承更有弹性的替代方案（扩展原有对象的功能）

装饰（Decorator）模式中的角色：

- 抽象构件（Component）角色 ：定义一个抽象接口以规范准备接收附加责任的对象。
- 具体构件（Concrete Component）角色 ：实现抽象构件，通过装饰角色为其添加一些职责。
- 抽象装饰（Decorator）角色 ： 继承或实现抽象构件，并包含具体构件的实例，可以通过其子类扩展具体构件的功能。
- 具体装饰（ConcreteDecorator）角色 ：实现抽象装饰的相关方法，并给具体构件对象添加附加的责任。

#### 3.1 继承方式

举一个简单的例子，假如现在有一碟炒饭，每个人的口味不一样，有些人喜欢加鸡蛋，有些人喜欢加鸡蛋火腿，有些人喜欢加鸡蛋火腿胡萝卜等，那么就会发现，如果采用继承的方式去实现这个例子，那么没加一个配料，都需要创建新的配料类去继承上一个旧的配料类，那么久而久之，就会产生很多类了，而且还不利于扩展，代码如下：

```java

// 炒饭类
public class FriedRice {
    String getDesc() {
        return "炒饭";
    }

    Integer getPrice() {
        return 5;
    }
}

// 炒饭加鸡蛋类
public class FriedRiceAddEgg extends FriedRice {
    String getDesc() {
        return super.getDesc() + "+鸡蛋";
    }

    Integer getPrice() {
        return super.getPrice() + 2;
    }
}

// 炒饭加鸡蛋加火腿类
public class FriedRiceAddEggAndHam extends FriedRiceAddEgg {
    String getDesc() {
        return super.getDesc() + "+火腿";
    }

    Integer getPrice() {
        return super.getPrice() + 3;
    }
}

public class Test {
    // 测试方法
    public static void main(String[] args) {
        FriedRice friedRice = new FriedRice();
        System.out.println(friedRice.getDesc() + friedRice.getPrice() + "元");// 炒饭5元
        FriedRice friedRiceAddEgg = new FriedRiceAddEgg();
        System.out.println(friedRiceAddEgg.getDesc() + friedRiceAddEgg.getPrice() + "元"); // 炒饭+鸡蛋7元
        FriedRice friedRiceAddEggAndHam = new FriedRiceAddEggAndHam();
        System.out.println(friedRiceAddEggAndHam.getDesc() + friedRiceAddEggAndHam.getPrice() + "元");// 炒饭+鸡蛋+火腿10元
    }
}

```

可以从上面看到，如果我们只需要炒饭加火腿，那么我们还需要创建一个FriedRiceAddHam类去继承FriedRice类，所以继承的方式扩展性非常不好，且需要定义非常多的子类，下面就可以用装饰器模式去改进它。

#### 3.2 装饰器模式方式

```java

// 炒饭类
public class FriedRice {
    String getDesc() {
        return "炒饭";
    }

    Integer getPrice() {
        return 5;
    }
}

// 配料表
public abstract class Ingredients extends FriedRice {
    private FriedRice friedRice;

    public Ingredients(FriedRice friedRice) {
        this.friedRice = friedRice;
    }

    String getDesc() {
        return this.friedRice.getDesc();
    }

    Integer getPrice() {
        return this.friedRice.getPrice();
    }
}

// 鸡蛋配料
public class Egg extends Ingredients {
    public Egg(FriedRice friedRice) {
        super(friedRice);
    }

    String getDesc() {
        return super.getDesc() + "+鸡蛋";
    }

    Integer getPrice() {
        return super.getPrice() + 2;
    }
}

// 火腿配料
public class Ham extends Ingredients {
    public Ham(FriedRice friedRice) {
        super(friedRice);
    }

    String getDesc() {
        return super.getDesc() + "+火腿";
    }

    Integer getPrice() {
        return super.getPrice() + 3;
    }
}

public class Test {
    // 测试方法
    public static void main(String[] args) {
        FriedRice friedRice = new FriedRice();
        System.out.println(friedRice.getDesc() + friedRice.getPrice() + "元"); // 炒饭5元
        friedRice = new Egg(friedRice);
        System.out.println(friedRice.getDesc() + friedRice.getPrice() + "元"); // 炒饭+鸡蛋7元
        friedRice = new Egg(friedRice);
        System.out.println(friedRice.getDesc() + friedRice.getPrice() + "元");// 炒饭+鸡蛋+鸡蛋9元
        friedRice = new Ham(friedRice);
        System.out.println(friedRice.getDesc() + friedRice.getPrice() + "元");// 炒饭+鸡蛋+鸡蛋+火腿12元
    }
}
```

可以看到，使用装饰器模式的方法实现，与普通的继承方法实现，最大的区别就是一种配料只有一个类，而且在加配料的时候，也可以直接想加多少就加多少，不需要说一个鸡蛋一个类，两个鸡蛋也要创建一个类，这样可以带来比继承更加灵活的扩展功能，使用也更加方便。

#### 3.3 总结

装饰器模式与代理模式对比：

- 装饰器模式就是一种特殊的代理模式。
- 装饰器模式强调自身的功能扩展，用自己说了算的透明扩展，可动态定制的扩展；代理模式强调代理过程的控制。
- 获取目标对象构建的地方不同，装饰者是从外界传递进来的，可以通过构造方法传递；静态代理是在代理类内部创建，以此来隐藏目标对象。

适用场景：

- 用于扩展一个类的功能或者给一个类添加附加职责。
- 动态的给一个对象添加功能，这些功能同样也可以再动态的撤销。

优点：

- 装饰器是继承的有力补充，比继承灵活，不改变原有对象的情况下动态地给一个对象扩展功能，即插即用。
- 通过使用不同装饰类以及这些装饰类的排列组合，可实现不同效果。
- 装饰器完全遵守开闭原则。

缺点：

- 会出现更多的代码，更多的类，增加程序的复杂性。
- 动态装饰时，多层装饰会更复杂。

### 4.桥接模式（Bridge Pattern）

桥接模式也称为桥梁模式、接口模式或者柄体（Handle and Body）模式，是将抽象部分与他的具体实现部分分离，使它们都可以独立地变化，通过组合的方式建立两个类之间的联系，而不是继承。

桥接（Bridge）模式包含以下主要角色：

- 抽象化（Abstraction）角色 ：定义抽象类，并包含一个对实现化对象的引用。
- 扩展抽象化（Refined Abstraction）角色 ：是抽象化角色的子类，实现父类中的业务方法，并通过组合关系调用实现化角色中的业务方法。
- 实现化（Implementor）角色 ：定义实现化角色的接口，供扩展抽象化角色调用。
- 具体实现化（Concrete Implementor）角色 ：给出实现化角色接口的具体实现。

#### 4.1 代码实现

下面以一个多系统多视频格式文件播放为例子：

```java

// 视频接口
public interface Video {
    void decode(String fileName);
}

// MP4格式类
public class Mp4 implements Video {
    @Override
    public void decode(String fileName) {
        System.out.println("MP4视频文件：" + fileName);
    }
}

// RMVB格式类
public class Rmvb implements Video {
    @Override
    public void decode(String fileName) {
        System.out.println("rmvb文件：" + fileName);
    }
}

// 操作系统抽象类
@AllArgsConstructor
public abstract class OperatingSystem {
    Video video;

    public abstract void play(String fileName);

}

// iOS系统
public class Ios extends OperatingSystem {
    public Ios(Video video) {
        super(video);
    }

    @Override
    public void play(String fileName) {
        video.decode(fileName);
    }
}

// windows系统
public class Windows extends OperatingSystem {
    public Windows(Video video) {
        super(video);
    }

    @Override
    public void play(String fileName) {
        video.decode(fileName);
    }
}
```

#### 4.2 总结

适用场景：

- 在抽象和具体实现之间需要增加更多的灵活性的场景。
- 一个类存在两个（或多个）独立变化的维度，而这两个（或多个）维度都需要独立进行扩展。
- 不希望使用继承，或因为多层继承导致系统类的个数剧增。

优点：

- 分离抽象部分及其具体实现部分。
- 提高了系统的扩展性。
- 符合开闭原型。
- 符合合成复用原则。

缺点：

- 增加了系统的理解与设计难度。
- 需要正确地识别系统中两个独立变化的维度。

### 5.外观模式（Facade）

外观模式又称门面模式，提供了一个统一的接口，用来访问子系统中的一群接口。

特征：门面模式定义了一个高层接口，让子系统更容易使用。

外观（Facade）模式包含以下主要角色：

- 外观（Facade）角色：为多个子系统对外提供一个共同的接口。
- 子系统（Sub System）角色：实现系统的部分功能，客户可以通过外观角色访问它。

#### 5.1 代码实现

下面以一个智能音箱实现起床睡觉一键操作电器的场景，通过代码模拟一下这个场景：

```java

public class Light {
    public void on() {
        System.out.println("开灯");
    }

    public void off() {
        System.out.println("关灯");
    }
}

public class Tv {
    public void on() {
        System.out.println("开电视");
    }

    public void off() {
        System.out.println("关电视");
    }
}

public class Fan {
    public void on() {
        System.out.println("开风扇");
    }

    public void off() {
        System.out.println("关风扇");
    }
}

public class SmartSpeaker {
    private Light light;
    private Tv tv;
    private Fan fan;

    public SmartSpeaker() {
        light = new Light();
        tv = new Tv();
        fan = new Fan();
    }

    public void say(String order) {
        if (order.contains("起床")) {
            getUp();
        } else if (order.contains("睡觉")) {
            sleep();
        } else {
            System.out.println("我还听不懂你说的啥！");
        }
    }

    public void getUp() {
        System.out.println("起床");
        light.on();
        tv.on();
        fan.off();
    }

    public void sleep() {
        System.out.println("睡觉");
        light.off();
        tv.off();
        fan.on();
    }
}

public class Test {

    public static void main(String[] args) {
        SmartSpeaker smartSpeaker = new SmartSpeaker();
        //睡觉
        //关灯
        //关电视
        //开风扇
        smartSpeaker.say("我要睡觉了!");
        //起床
        //开灯
        //开电视
        //关风扇
        smartSpeaker.say("我起床了!");
        //我还听不懂你说的啥！
        smartSpeaker.say("Emmm");
    }
}
```

#### 5.2 总结

适用场景：

- 对分层结构系统构建时，使用外观模式定义子系统中每层的入口点可以简化子系统之间的依赖关系。
- 当一个复杂系统的子系统很多时，外观模式可以为系统设计一个简单的接口供外界访问。
- 当客户端与多个子系统之间存在很大的联系时，引入外观模式可将它们分离，从而提高子系统的独立性和可移植性。

优点：

- 简化了调用过程，无需深入了解子系统，以防给子系统带来风险。
- 减少系统依赖、松散耦合。
- 更好地划分访问层次，提高了安全性。
- 遵循迪米特法则，即最少知道原则。

缺点：

- 当增加子系统和扩展子系统行为时，可能容易带来未知风险。
- 不符合开闭原则。
- 某些情况下可能违背单一职责原则。

### 6.组合模式（Composite Pattern）

组合模式也称为整体-部分（Part-Whole）模式，它的宗旨是通过将单个对象（叶子结点）和组合对象（树枝节点）用相同的接口进行表示。

作用：使客户端对单个对象和组合对象保持一致的方式处理。

组合模式主要包含三种角色：

- 抽象根节点（Component）：定义系统各层次对象的共有方法和属性，可以预先定义一些默认行为和属性。
- 树枝节点（Composite）：定义树枝节点的行为，存储子节点，组合树枝节点和叶子节点形成一个树形结构。
- 叶子节点（Leaf）：叶子节点对象，其下再无分支，是系统层次遍历的最小单位。

#### 6.1 代码实现

下面以一个添加菜单的例子通过代码实现：

```java

// 菜单组件
public abstract class MenuComponent {
    String name;
    Integer level;

    public void add(MenuComponent menuComponent) {
        throw new UnsupportedOperationException("不支持添加操作!");
    }

    public void remove(MenuComponent menuComponent) {
        throw new UnsupportedOperationException("不支持删除操作!");
    }

    public MenuComponent getChild(Integer i) {
        throw new UnsupportedOperationException("不支持获取子菜单操作!");
    }

    public String getName() {
        throw new UnsupportedOperationException("不支持获取名字操作!");
    }

    public void print() {
        throw new UnsupportedOperationException("不支持打印操作!");
    }
}

// 菜单类
public class Menu extends MenuComponent {
    private List<MenuComponent> menuComponentList = new ArrayList<>();

    public Menu(String name, int level) {
        this.level = level;
        this.name = name;
    }

    @Override
    public void add(MenuComponent menuComponent) {
        menuComponentList.add(menuComponent);
    }

    @Override
    public void remove(MenuComponent menuComponent) {
        menuComponentList.remove(menuComponent);
    }

    @Override
    public MenuComponent getChild(Integer i) {
        return menuComponentList.get(i);
    }

    @Override
    public void print() {
        for (int i = 1; i < level; i++) {
            System.out.print("--");
        }
        System.out.println(name);
        for (MenuComponent menuComponent : menuComponentList) {
            menuComponent.print();
        }
    }
}

// 子菜单类
public class MenuItem extends MenuComponent {
    public MenuItem(String name, int level) {
        this.name = name;
        this.level = level;
    }

    @Override
    public void print() {
        for (int i = 1; i < level; i++) {
            System.out.print("--");
        }
        System.out.println(name);
    }
}

public class Test {

    // 测试方法
    public static void main(String[] args) {
        //创建一级菜单
        MenuComponent component = new Menu("系统管理", 1);

        MenuComponent menu1 = new Menu("用户管理", 2);
        menu1.add(new MenuItem("新增用户", 3));
        menu1.add(new MenuItem("修改用户", 3));
        menu1.add(new MenuItem("删除用户", 3));

        MenuComponent menu2 = new Menu("角色管理", 2);
        menu2.add(new MenuItem("新增角色", 3));
        menu2.add(new MenuItem("修改角色", 3));
        menu2.add(new MenuItem("删除角色", 3));
        menu2.add(new MenuItem("绑定用户", 3));

        //将二级菜单添加到一级菜单中
        component.add(menu1);
        component.add(menu2);

        //打印菜单名称(如果有子菜单一块打印)
        component.print();
    }
}
// 测试结果
/**
 系统管理
 --用户管理
 ----新增用户
 ----修改用户
 ----删除用户
 --角色管理
 ----新增角色
 ----修改角色
 ----删除角色
 ----绑定用户
 */

```

#### 6.2 总结

适用场景：

- 希望客户端可以忽略组合对象与单个对象的差异时。
- 对象层次具备整体和部分，呈树形结构（如树形菜单，操作系统目录结构，公司组织架构等）。

优点：

- 清楚地定义分层次的复杂对象，表示对象的全部或部分层次。
- 让客户端忽略了层次的差异，方便对整个层次结构进行控制。
- 简化客户端代码。
- 符合开闭原则。

缺点：

- 限制类型时会较为复杂。
- 使设计变得更加抽象。

分类：

- 透明组合模式
    - 透明组合模式中，抽象根节点角色中声明了所有用于管理成员对象的方法，比如在示例中MenuComponent声明了add() 、 remove() 、getChild()
      方法，这样做的好处是确保所有的构件类都有相同的接口。透明组合模式也是组合模式的标准形式。
    - 透明组合模式的缺点是不够安全，因为叶子对象和容器对象在本质上是有区别的，叶子对象不可能有下一个层次的对象，即不可能包含成员对象，因此为其提供 add()、remove()
      等方法是没有意义的，这在编译阶段不会出错，但在运行阶段如果调用这些方法可能会出错（如果没有提供相应的错误处理代码）
- 安全组合模式
  -
  在安全组合模式中，在抽象构件角色中没有声明任何用于管理成员对象的方法，而是在树枝节点Menu类中声明并实现这些方法。安全组合模式的缺点是不够透明，因为叶子构件和容器构件具有不同的方法，且容器构件中那些用于管理成员对象的方法没有在抽象构件类中定义，因此客户端不能完全针对抽象编程，必须有区别地对待叶子构件和容器构件。

### 7.享元模式（Flyweight Pattern）

享元模式又称为轻量级模式，是对象池的一种实现，类似于线程池，线程池可以避免不停的创建和销毁多个对象，消耗性能。提供了减少对象数量从而改善应用所需的对象结构的方式。宗旨：共享细粒度对象，将多个对同一对象的访问集中起来。

享元（Flyweight ）模式中存在以下两种状态：

- 内部状态，即不会随着环境的改变而改变的可共享部分。
- 外部状态，指随环境改变而改变的不可以共享的部分。享元模式的实现要领就是区分应用中的这两种状态，并将外部状态外部化。

享元模式的主要有以下角色：

- 抽象享元角色（Flyweight）：通常是一个接口或抽象类，在抽象享元类中声明了具体享元类公共的方法，这些方法可以向外界提供享元对象的内部数据（内部状态），同时也可以通过这些方法来设置外部数据（外部状态）。
- 具体享元（Concrete Flyweight）角色 ：它实现了抽象享元类，称为享元对象；在具体享元类中为内部状态提供了存储空间。通常我们可以结合单例模式来设计具体享元类，为每一个具体享元类提供唯一的享元对象。
- 非享元（Unsharable Flyweight)角色 ：并不是所有的抽象享元类的子类都需要被共享，不能被共享的子类可设计为非共享具体享元类；当需要一个非共享具体享元类的对象时可以直接通过实例化创建。
- 享元工厂（Flyweight Factory）角色 ：负责创建和管理享元角色。当客户对象请求一个享元对象时，享元工厂检査系统中是否存在符合要求的享元对象，如果存在则提供给客户；如果不存在的话，则创建一个新的享元对象。

#### 7.1 代码实现

下面通过查询火车票的例子来用代码进行模拟实现：

```java
// 抽象接口
public interface ITicket {
    void show(String seat);
}

public class TrainTicket implements ITicket {
    private String from;
    private String to;
    private Integer price;

    public TrainTicket(String from, String to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public void show(String seat) {
        this.price = new Random().nextInt(500);
        System.out.println(from + "->" + to + ":" + seat + "价格:" + this.price);
    }
}

// 工厂类
public class TicketFactory {
    private static Map<String, ITicket> pool = new ConcurrentHashMap<>();

    public static ITicket getTicket(String from, String to) {
        String key = from + "->" + to;
        if (pool.containsKey(key)) {
            System.out.println("使用缓存获取火车票:" + key);
            return pool.get(key);
        }
        System.out.println("使用数据库获取火车票:" + key);
        ITicket ticket = new TrainTicket(from, to);
        pool.put(key, ticket);
        return ticket;
    }
}

public class Test {
    // 测试
    public static void main(String[] args) {
        ITicket ticket = getTicket("北京", "上海");
        //使用数据库获取火车票:北京->上海
        //北京->上海:二等座价格:20
        ticket.show("二等座");
        ITicket ticket1 = getTicket("北京", "上海");
        //使用缓存获取火车票:北京->上海
        //北京->上海:商务座价格:69
        ticket1.show("商务座");
        ITicket ticket2 = getTicket("上海", "北京");
        //使用数据库获取火车票:上海->北京
        //上海->北京:一等座价格:406
        ticket2.show("一等座");
        System.out.println(ticket == ticket1);//true
        System.out.println(ticket == ticket2);//false
    }
}
```

可以看到ticket和ticket2是使用数据库查询的，而ticket1是使用缓存查询的，同时ticket == ticket1返回的是true，ticket ==
ticket2返回的是false，证明ticket和ticket1是共享的对象。

#### 7.2 总结

适用场景：

- 一个系统有大量相同或者相似的对象，造成内存的大量耗费。
- 对象的大部分状态都可以外部化，可以将这些外部状态传入对象中。
- 在使用享元模式时需要维护一个存储享元对象的享元池，而这需要耗费一定的系统资源，因此，应当在需要多次重复使用享元对象时才值得使用享元模式。

优点：

- 减少对象的创建，降低内存中对象的数量，降低系统的内存，提高效率。
- 减少内存之外的其他资源占用。

缺点：

- 关注内、外部状态。
- 关注线程安全问题。
- 使系统、程序的逻辑复杂化。

## 三、行为型模式

### 1.模板方法模式（Template method pattern）

模板方法模式通常又叫模板模式，是指定义一个算法的骨架，并允许之类为其中的一个或者多个步骤提供实现。模板方法模式使得子类可以在不改变算法结构的情况下，重新定义算法的某些步骤。

模板方法（Template Method）模式包含以下主要角色：

- 抽象类（Abstract Class）：负责给出一个算法的轮廓和骨架。它由一个模板方法和若干个基本方法构成。
    - 模板方法：定义了算法的骨架，按某种顺序调用其包含的基本方法。
    - 基本方法：是实现算法各个步骤的方法，是模板方法的组成部分。基本方法又可以分为三种：
        - 抽象方法(Abstract Method) ：一个抽象方法由抽象类声明、由其具体子类实现。
        - 具体方法(Concrete Method) ：一个具体方法由一个抽象类或具体类声明并实现，其子类可以进行覆盖也可以直接继承。
        - 钩子方法(Hook Method) ：在抽象类中已经实现，包括用于判断的逻辑方法和需要子类重写的空方法两种。一般钩子方法是用于判断的逻辑方法，这类方法名一般为isXxx，返回值类型为boolean类型。
    - 具体子类（Concrete Class）：实现抽象类中所定义的抽象方法和钩子方法，它们是一个顶级逻辑的组成步骤。

#### 1.1 代码实现

下面以一个简单的请假流程来通过代码来实现：

```java

public abstract class DayOffProcess {
    // 请假模板
    public final void dayOffProcess() {
        // 领取申请表
        this.pickUpForm();
        // 填写申请信息
        this.writeInfo();
        // 签名
        this.signUp();
        // 提交到不同部门审批
        this.summit();
        // 行政部备案
        this.filing();
    }

    private void filing() {
        System.out.println("行政部备案");
    }

    protected abstract void summit();

    protected abstract void signUp();

    private void writeInfo() {
        System.out.println("填写申请信息");
    }

    private void pickUpForm() {
        System.out.println("领取申请表");
    }
}

public class ZhangSan extends DayOffProcess {
    @Override
    protected void summit() {
        System.out.println("张三签名");
    }

    @Override
    protected void signUp() {
        System.out.println("提交到技术部审批");
    }
}

public class Lisi extends DayOffProcess {
    @Override
    protected void summit() {
        System.out.println("李四签名");
    }

    @Override
    protected void signUp() {
        System.out.println("提交到市场部审批");
    }
}

public class Test {

    // 测试方法
    public static void main(String[] args) {
        DayOffProcess zhangsan = new ZhangSan();
        //领取申请表
        //填写申请信息
        //提交到技术部审批
        //张三签名
        //行政部备案
        zhangsan.dayOffProcess();
        DayOffProcess lisi = new Lisi();
        //领取申请表
        //填写申请信息
        //提交到市场部审批
        //李四签名
        //行政部备案
        lisi.dayOffProcess();
    }
}
```

#### 1.2 总结

适用场景：

- 一次性实现一个算法不变的部分，并将可变的行为留给子类来实现。
- 各子类中公共的行为被提取出来并集中到一个公共的父类中，从而避免代码重复。

优点：

- 利用模板方法将相同处理逻辑的代码放到抽象父类中，可以提高代码的复用性。
- 将不同的代码不同的子类中，通过对子类的扩展增加新的行为，提高代码的扩展性。
- 把不变的行为写在父类上，去除子类的重复代码，提供了一个很好的代码复用平台，符合开闭原则。

缺点：

- 类数目的增加，每一个抽象类都需要一个子类来实现，这样导致类的个数增加。
- 类数量的增加，间接地增加了系统实现的复杂度。
- 继承关系自身缺点，如果父类添加新的抽象方法，所有子类都要改一遍。

### 2.策略模式（Strategy Pattern）

策略模式又叫政策模式（Policy Pattern），它是将定义的算法家族分别封装起来，让它们之间可以互相替换，从而让算法的变化不会影响到使用算法的用户。可以避免多重分支的if......else和switch语句。

策略模式的主要角色如下：

- 抽象策略（Strategy）类：这是一个抽象角色，通常由一个接口或抽象类实现。此角色给出所有的具体策略类所需的接口。
- 具体策略（Concrete Strategy）类：实现了抽象策略定义的接口，提供具体的算法实现或行为。
- 环境（Context）类：持有一个策略类的引用，最终给客户端调用。

#### 2.1 普通案例（会员卡打折）

```java

// 会员卡接口
public interface VipCard {
    public void discount();
}

public class GoldCard implements VipCard {
    @Override
    public void discount() {
        System.out.println("金卡打7折");
    }
}

public class SilverCard implements VipCard {
    @Override
    public void discount() {
        System.out.println("银卡打8折");
    }
}

public class CopperCard implements VipCard {
    @Override
    public void discount() {
        System.out.println("铜卡打9折");
    }
}

public class Normal implements VipCard {
    @Override
    public void discount() {
        System.out.println("普通会员没有折扣");
    }
}

// 会员卡容器类
public class VipCardFactory {
    private static Map<String, VipCard> map = new ConcurrentHashMap<>();

    static {
        map.put("gold", new GoldCard());
        map.put("silver", new SilverCard());
        map.put("copper", new CopperCard());
    }

    public static VipCard getVIPCard(String level) {
        return map.get(level) != null ? map.get(level) : new Normal();
    }

}

public class Test {
    // 测试方法
    public static void main(String[] args) {
        //金卡打7折
        VipCardFactory.getVIPCard("gold").discount();
        //银卡打8折
        VipCardFactory.getVIPCard("silver").discount();
        //普通会员没有折扣
        VipCardFactory.getVIPCard("other").discount();
    }
}
```
