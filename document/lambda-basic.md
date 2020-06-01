##  Lambda基础语法

1. ### **函数式编程**

   Java API中有的接口只有一个方法，大多数回调接口都拥有这个特征：比如Callable接口和Comparator接口。由此定义了一个统一的规范：

   **函数式接口**，一般以注解@FunctionalInterface 进行标注。

   

   - **关于@FunctionalInterface的注解的说明：**

     1. 使用该注解后，接口中只能存在**一个抽象方法**的接口，否则编译报错。（lambda默认调用的方法）
     2. 接口中允许存在非抽象的静态方法和默认方法。（可以对接口进行扩展，使接口功能不那么单一）
     3. 接口中允许定义与Object类同名的抽象方法。（暂时不知道有啥用？）


   > 注意：@FunctionalInterface只是一个规范标记，代码编译时会去检查

   

   - **jdk默认提供的一些函数式接口:**

     为了方便使用lambda,jdk提供了一些通用的函数式接口,以下列出一些常用接口:

     - Predicate<T>	——接收T对象并返回boolean

       ```java
       // Predicate : 参数：T，返回值：boolean
               Predicate<Integer> predicate = (i) -> i > 5;
               // 可以利用默认方法，进行组合连接
               predicate.and(i -> i % 2 == 0)// 且操作
                       .or(i-> i / 3 == 3)// 或操作
                       .negate(); // 取反操作
               System.out.println(predicate.test(9));
       ```

       ```
       控制台：
       false
       ```

       

     - Consumer<T> ——接收T对象，不返回值

       ```java
       // Consumer : 参数：T，返回值：void ， 可以看做是消费者
       		Consumer<String> consumer = s -> System.out.println(s);
       		consumer = consumer.andThen(s -> System.out.println("after:" + s));// 后续操作
       		consumer.accept("Java");
       ```

       ```java
       控制台:
       Java
       after:Java
       ```

     

     - Function<T, R> ——接收T对象，返回R对象

       ```java
       // T ,R都可以自定义，扩展性较高
       Function<Integer, Boolean> function = (m) -> m < 0;
               Function<Boolean,Integer > function1 = m -> m ? 1 : 2 ;
               // andThen 和compose 使用时需要注意入参类型和返回值类型的对应
               function.andThen(function1);
               function.compose(function1);
       
       ```

       

       

     - Supplier——提供T对象（例如工厂），不接收值

       ```java
       // Supplier: 参数：无，返回值：T ，可以看做是生产者
               Supplier<Boolean> supplier = () -> true;
               
       ```

       

     - UnaryOperator——接收T对象，返回T对象

       ```java
       // 继承自Function，限定了入参和出参保持一致
       UnaryOperator<Integer> unaryOperator = (s) -> s++;
       ```

       同样的函数接口还有很多，可以参考package：java.util.function; 自行研究

   - ### 具体使用

1. #### **过程简化**

   - **参数类型可以省略** 

     参数类型，JVM编译时可以通过上下文去获取。

     ```java
     Consumer<String> consumer = ( String s) -> System.out.println(s);
     // 省略后
     Consumer<String> consumer = (s) -> System.out.println(s);
     ```

     

   - **（）可以省略**

     ​	只有一个参数的时候，（）可以省略，除非需要声明对象时，不能省略。

     ```
     Consumer<String> consumer = (s) -> System.out.println(s);
     // 省略后
     Consumer<String> consumer = s -> System.out.println(s);
     ```

   - **-> 可以省略**

     ```java
     Consumer<String> consumer = (s) -> System.out.println(s);
     // 省略后
     Consumer<String> consumer = System.out::println;//具体见下文
     ```

     



2. #### **方法引用**

- 静态方法引用：ClassName::methodName

- 实例上的实例方法引用：instanceReference::method

- 超类上的实例方法引用：super::methodName

- 类型上的实例方法引用：ClassName::methodName

- 构造方法引用： Class::new

- 数组构造方法引用：TypeName[]::new

  

  ```java
   /**
       *  测试方法引用
       */
      public void testReferance(){
          // 静态方法引用
          Consumer consumer = System.out::println;
          // 等价于：  consumer = s -> System.out.println(s);
  
          // 对象调用
          Object o = new Object();
          Supplier<Integer> supplier = o::hashCode;
          // 等价于： supplier = () -> o.hashCode();
          //或者是
          supplier = new Object()::hashCode;
          // 等价于： supplier = () -> new Object().hashCode();
  
          // 父类或本类方法引用
          supplier = super::hashCode;
          //等价于： supplier = () -> super.hashCode();
          supplier = this::hashCode;
          // 等价于： supplier = () -> this.hashCode();
  
          // 通过类型上的实例方法引用
          String[] arr = new String[2];
          Arrays.sort(arr,String::compareTo);
          // 构造方法引用
          Supplier<Person> supplier1 = Person::new;
          // 等价于：supplier1 = () -> new Person();
          // 数组构造方法引用
          HasParamHasReturn1 supplier2 = String[]::new;
          // 等价于：supplier2 = i -> new String[i];
  
      }
      public interface HasParamHasReturn1 {
          String[] count(Integer t1);
  
      }
  ```

  

  

   