## 第 10 章面向对象编程(高级部分)
### 10.1 类变量
在Java中，类变量（class variables）是指属于类而不是类的实例的变量。它们也被称为静态变量（static variables），因为它们使用关键字static来声明。类变量与特定类的每个实例都共享相同的值。

```
public class MyClass {
    // 类变量，使用关键字 static 声明
    static int classVariable = 10;

    public static void main(String[] args) {
        // 使用类名直接访问类变量
        System.out.println("Class Variable: " + MyClass.classVariable);

        // 修改类变量的值
        MyClass.classVariable = 20;

        // 创建类的实例
        MyClass obj1 = new MyClass();
        MyClass obj2 = new MyClass();

        // 通过实例访问类变量，但通常建议使用类名访问
        System.out.println("Class Variable from obj1: " + obj1.classVariable);
        System.out.println("Class Variable from obj2: " + obj2.classVariable);
    }
}
```
在上面的例子中，classVariable 是一个类变量，而且它的值是共享的，即使创建了多个 MyClass 类的实例，它们都将共享相同的 classVariable 的值。在 main 方法中，我们可以通过类名
直接访问类变量，也可以通过类的实例访问，但通常建议使用类名来访问类变量，以明确表示这是一个类级别的变量。

- 类变量在类加载时就进行初始化，随着类消亡而销毁；
- 在Java中，类在第一次被使用时被加载。类加载是Java虚拟机（JVM）的一部分，它的任务是将类的字节码加载到内存中，以便在运行时创建类的实例。
  类加载的过程是由类加载器（Class Loader）来执行的。需要注意的是，类加载是一个懒加载（lazy loading）的过程，即只有在需要使用类时才会加载，这有助于提高程序的性能和资源利用率。
### 10.2 类方法
同类变量

#### 10.2.1 什么时候采用类方法
类方法（静态方法）是属于类而不是类的实例的方法，它使用 `static` 关键字进行声明。适合设计为类方法的场景通常包括以下情况：

1. **与类相关的操作：** 如果一个方法的操作与整个类的状态或数据密切相关，而不依赖于特定实例的状态，那么它可能适合设计为类方法。类方法不可以访问实例变量，但可以访问类变量。

2. **工具方法：** 如果一个方法不需要访问实例的状态，并且其行为不受实例变量的影响，那么它可能是一个适合设计为类方法的工具方法。例如，数学函数或其他通用的辅助方法。

3. **工厂方法：** 类方法可以用于创建类的实例，即工厂方法。如果一个方法的主要目的是根据输入参数创建并返回类的实例，而不是操作实例的状态，那么它可以是一个类方法。

4. **单例模式中的获取实例的方法：** 在单例模式中，类方法通常用于获取类的唯一实例。

5. **静态初始化块：** 静态初始化块是类方法的一种形式，用于在类加载时执行一些初始化操作。

#### 10.2.2 注意事项
- 类方法和普通方法都随着类的加载而加载，将结构信息存储在方法区，类方法中无this参数；
- 类方法可以通过类名调用，也可以通过对象名调用；
- 普通方法只能通过对象名调用；
- 类方法中不允许使用和对象有关的关键字，如this，super；
- 类方法中只能访问静态变量或静态方法；
- 普通成员方法，既可以访问静态成员，也可以访问非静态成员；
- 访问需遵循权限

 ### 10.3 理解main方法语法
 #### 10.3.1 深入理解main方法

`main` 方法是Java程序的入口点，它是程序执行的起始点。当你运行一个Java应用程序时，JVM（Java虚拟机）会首先查找并执行`main` 方法。`main` 方法有以下特征：

1. **方法签名：** `main` 方法的签名是固定的，必须是 `public static void main(String[] args)`。这意味着`main` 方法必须是公共的（`public`）、
   静态的（`static`），返回类型是`void`，而参数是一个字符串数组（`String[] args`）。

2. **公共静态方法：** 由于`main` 方法是程序入口，它必须是公共的（`public`）以便于被JVM调用，而且必须是静态的（`static`）以确保在没有创建类实例的情况下执行。

3. **参数：** `main` 方法的参数是一个字符串数组（`String[] args`）。这个数组用于传递命令行参数给程序。你可以通过 `args` 数组访问在命令行中传递给程序的参数。

4. **程序执行的起点：** `main` 方法是程序的起始点。当你运行一个Java程序时，JVM会查找并执行 `main` 方法。其他的类和方法可以通过调用 `main` 方法来启动程序的执行。

5. **例子：** 下面是一个简单的Java程序，展示了 `main` 方法的使用：

    ```java
    public class HelloWorld {
        public static void main(String[] args) {
            // 程序的入口点
            System.out.println("Hello, World!");
            
            // 访问命令行参数
            if (args.length > 0) {
                System.out.println("Command line arguments:");
                for (String arg : args) {
                    System.out.println(arg);
                }
            }
        }
    }
    ```

    在上述示例中，`main` 方法打印了 "Hello, World!"，并且如果有命令行参数传递给程序，它还打印了这些参数。

- 在 main()方法中，我们可以直接调用 main 方法所在类的静态方法或静态属性。
-  但是，不能直接访问该类中的非静态成员，必须创建该类的一个实例对象后，才能通过这个对象去访问类中的非静态成员

### 10.4 代码块
在Java中，代码块是一组用花括号 `{}` 括起来的代码语句。根据其位置和声明方式，可以分为以下几种类型的代码块：

1. **普通代码块：** 也称为局部代码块，是最常见的一种代码块。它是在方法体或控制语句内部定义的，用于限定变量的作用域。普通代码块不带任何关键字，只是一组被花括号包围的语句。
   
    ```java
    public class Example {
        public void someMethod() {
            // 普通代码块
            {
                int x = 10;
                System.out.println(x);
            }

            // 这里 x 不可访问，因为它的作用域在上面的代码块内
        }
    }
    ```

2. **构造代码块：** 也称为实例初始化块，是定义在类中但不属于任何方法或构造方法的代码块。构造代码块在创建类的实例时被调用，每次创建实例时都会执行一次。调用静态成员或方法时不会执行
3. 它主要用于在每次对象创建时执行一些初始化操作。

    ```java
    public class Example {
        // 构造代码块
        {
            System.out.println("Constructor block executed");
        }

        public Example() {
            System.out.println("Constructor executed");
        }

        public static void main(String[] args) {
            new Example();
        }
    }
    ```

4. **静态代码块：** 也称为类初始化块，是定义在类中并带有 `static` 关键字的代码块。静态代码块在类加载时执行，且只执行一次。它通常用于进行类级别的初始化操作。

    ```java
    public class Example {
        // 静态代码块
        static {
            System.out.println("Static block executed");
        }

        public static void main(String[] args) {
            System.out.println("Main method executed");
        }
    }
    ```

总的来说，代码块是用于组织和限定作用域的代码片段。构造代码块和静态代码块在一些特定场景下提供了在对象创建和类加载时执行代码的机制。

### 10.5 类的调用顺序
在Java中，当创建一个对象时，涉及到类的初始化和对象的实例化。类的初始化涉及到静态成员和静态代码块的初始化，而对象的实例化则涉及到实例成员和实例代码块的初始化。
以下是在创建对象时，涉及的类的调用顺序：

1. **类加载：** 当程序首次访问一个类时，该类的字节码会被加载到内存中。这会触发类的初始化过程。在类初始化中，静态成员变量和静态代码块会被执行。这发生在类加载的过程中，且仅发生一次。

2. **实例化对象：** 当通过 `new` 关键字创建一个对象时，会触发对象的实例化过程。在这个过程中，首先会调用父类的构造方法，然后再调用子类的构造方法。

3. **父类构造方法调用：** 如果有父类，那么首先会调用父类的构造方法。这是一个递归的过程，直到达到继承链的最顶层。
   
4. **实例成员变量初始化：** 在构造方法中，实例成员变量会被初始化。如果这些变量有默认值，它们会先被赋予默认值，然后再根据构造方法中的赋值语句进行赋值。

5. **实例代码块执行：** 如果类中包含实例代码块，那么在构造方法执行之前，实例代码块会被执行。

6. **构造方法执行：** 最后，构造方法会被执行，完成对象的实例化过程。

综合起来，类的调用顺序可以总结为：

1. 父类的静态成员变量和静态代码块的初始化（仅在类加载时执行一次）。
2. 子类的静态成员变量和静态代码块的初始化（仅在类加载时执行一次）。
3. 父类的普通代码块和普通属性初始化。
4. 父类的构造方法。
5. 子类的普通代码块和普通属性初始化。
6. 子类构造方法的执行。

需要注意的是，如果子类没有显式调用父类的构造方法（使用 `super()`），Java 会默认调用父类的无参构造方法。如果父类没有无参构造方法，
或者你希望调用父类的特定构造方法，那么就需要显式调用 `super` 关键字。

### 10.6 设计模式概述
设计模式是在软件设计中经常遇到的一些重复性问题的解决方案。它们是在解决特定问题时经过反复验证、被广泛应用并被广泛接受的一种经验总结。设计模式提供了一套可复用的解决方案，
可以用于解决特定类型的问题。

以下是一些常见的设计模式：

1. **单例模式（Singleton Pattern）：** 确保一个类只有一个实例，并提供全局访问点。

2. **工厂模式（Factory Pattern）：** 定义一个用于创建对象的接口，让子类决定实例化哪个类。

3. **抽象工厂模式（Abstract Factory Pattern）：** 提供一个创建一系列相关或相互依赖对象的接口，而无需指定它们的具体类。

4. **建造者模式（Builder Pattern）：** 将一个复杂对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示。

5. **原型模式（Prototype Pattern）：** 用原型实例指定创建对象的种类，并通过拷贝这些原型创建新的对象。

6. **适配器模式（Adapter Pattern）：** 将一个类的接口转换成客户希望的另外一个接口，使得原本由于接口不匹配而不能一起工作的类可以一起工作。

7. **装饰器模式（Decorator Pattern）：** 动态地给一个对象添加一些额外的职责，而不改变其结构。

8. **代理模式（Proxy Pattern）：** 为其他对象提供一种代理以控制对这个对象的访问。

9. **观察者模式（Observer Pattern）：** 定义了一种一对多的依赖关系，当一个对象的状态发生改变时，所有依赖于它的对象都得到通知并自动更新。

10. **策略模式（Strategy Pattern）：** 定义了算法家族，分别封装起来，让它们之间可以互相替换，此模式让算法的变化独立于使用算法的客户。

11. **命令模式（Command Pattern）：** 将请求封装成对象，以便使用不同的请求、队列，或者日志请求参数化其他对象。

12. **状态模式（State Pattern）：** 允许对象在其内部状态改变时改变它的行为。

13. **访问者模式（Visitor Pattern）：** 表示一个作用于某对象结构中的各元素的操作，它使你可以在不改变各元素的类的前提下定义作用于这些元素的新操作。

这些设计模式提供了一种通用的解决方案，可以帮助解决软件设计中的一些常见问题，提高代码的可维护性、可扩展性和可重用性。选择合适的设计模式取决于具体的问题和需求。

### 10.7 单例模式
单例模式（Singleton Pattern）是设计模式中的一种创建型模式，其主要目的是确保一个类只有一个实例，并提供一个全局访问点。

单例模式的特点包括：

1. **单一实例：** 单例模式保证一个类只有一个实例存在。无论何时何地，该类的实例都是唯一的。

2. **全局访问点：** 单例模式提供了一个全局访问点，使得程序可以访问该唯一实例。

实现单例模式的方式有多种，其中比较常见的有以下几种：

1. **懒汉式单例：** 在首次使用时创建实例。如果没有特殊的同步措施，可能会导致在多线程环境中创建多个实例。

    ```java
    public class LazySingleton {
        private static LazySingleton instance;

        private LazySingleton() {}

        public static LazySingleton getInstance() {
            if (instance == null) {
                instance = new LazySingleton();
            }
            return instance;
        }
    }
    ```

2. **饿汉式单例：** 在类加载时就创建实例。在多线程环境中，由于在类加载时就创建了实例，因此不存在多个实例的问题。

    ```java
    public class EagerSingleton {
        private static final EagerSingleton instance = new EagerSingleton();

        private EagerSingleton() {}

        public static EagerSingleton getInstance() {
            return instance;
        }
    }
    ```

3. **双重检查锁定（Double-Checked Locking）：** 在懒汉式的基础上进行改进，使用双重检查锁定来确保在多线程环境中只创建一个实例。

    ```java
    public class DoubleCheckedSingleton {
        private static volatile DoubleCheckedSingleton instance;

        private DoubleCheckedSingleton() {}

        public static DoubleCheckedSingleton getInstance() {
            if (instance == null) {
                synchronized (DoubleCheckedSingleton.class) {
                    if (instance == null) {
                        instance = new DoubleCheckedSingleton();
                    }
                }
            }
            return instance;
        }
    }
    ```

4. **静态内部类：** 利用静态内部类的特性，在类加载时不会立即加载静态内部类，只有在第一次调用 `getInstance` 方法时才会加载并初始化内部类，从而实现懒加载和线程安全。

    ```java
    public class InnerClassSingleton {
        private InnerClassSingleton() {}

        private static class SingletonHolder {
            private static final InnerClassSingleton instance = new InnerClassSingleton();
        }

        public static InnerClassSingleton getInstance() {
            return SingletonHolder.instance;
        }
    }
    ```

选择何种方式实现单例模式取决于具体的需求和性能要求。每种方式都有其优缺点，需要根据实际情况进行选择。

### 10.8 final
在Java中，`final` 是一个关键字，它可以用来修饰类、方法、变量等，具体的使用方式如下：

1. **final 修饰类：** 如果一个类被声明为 `final`，则该类不能被继承，即不能有子类。

2. **final 修饰方法：** 如果一个方法被声明为 `final`，则该方法不能被子类重写。

3. **final 修饰变量：**
    - **final 修饰实例变量（成员变量）：** 如果一个实例变量被声明为 `final`，则该变量必须在创建对象时进行初始化，并且一旦初始化后，其值不能被修改。

    - **final 修饰静态变量（类变量）：** 如果一个静态变量被声明为 `final`，则该变量在类加载时进行初始化，并且一旦初始化后，其值不能被修改。

    - **final 修饰局部变量：** 如果一个局部变量被声明为 `final`，则该变量在初始化后不能被重新赋值。

`final` 关键字的使用有以下几个主要目的：

- **不可改变性：** 在变量声明为 `final` 时，其值一经赋值便不能再修改，提供了一种不可改变性的保证。

- **安全性：** 在一些情况下，使用 `final` 可以提高安全性。例如，通过声明方法为 `final`，可以防止子类重写该方法。

- **性能优化：** 有时候，编译器和运行时环境可以根据变量是否声明为 `final` 进行一些优化，因为它们知道这些变量的值在之后不会改变。

在设计程序时，合理使用 `final` 可以提高代码的可读性、稳定性和性能。

### 10.9 抽象类
在Java中，抽象类（Abstract Class）是一种用于模板设计的类，它本身不能被实例化，主要用于被继承。抽象类可以包含抽象方法和具体方法，而抽象方法则需要由子类提供具体的实现。

以下是抽象类的主要特点和用法：

1. **抽象方法：** 抽象类可以包含抽象方法，这是一种没有具体实现的方法。抽象方法以关键字 `abstract` 声明，不包含方法体。子类必须提供对抽象方法的具体实现。

    ```java
    abstract class Shape {
        abstract void draw(); // 抽象方法
    }

    class Circle extends Shape {
        @Override
        void draw() {
            System.out.println("Drawing a circle");
        }
    }
    ```

2. **构造方法：** 抽象类可以有构造方法，用于初始化抽象类的实例。当子类实例化时，它会首先调用父类的构造方法。

    ```java
    abstract class Animal {
        Animal() {
            System.out.println("Animal constructor");
        }

        abstract void makeSound();
    }

    class Dog extends Animal {
        @Override
        void makeSound() {
            System.out.println("Bark");
        }
    }
    ```

3. **成员变量和具体方法：** 抽象类可以包含成员变量和具体方法，不一定都是抽象的。子类可以继承这些成员变量和方法。

    ```java
    abstract class Vehicle {
        String brand; // 成员变量

        Vehicle(String brand) {
            this.brand = brand;
        }

        void start() {
            System.out.println("Starting the vehicle");
        }

        abstract void stop(); // 抽象方法
    }

    class Car extends Vehicle {
        Car(String brand) {
            super(brand);
        }

        @Override
        void stop() {
            System.out.println("Stopping the car");
        }
    }
    ```

4. **不能实例化：** 抽象类本身不能被实例化，因为它可能包含抽象方法，而抽象方法没有具体实现。只有具体的子类可以被实例化。

抽象类用于建模一些通用的行为和属性，并为子类提供一种共享的基础结构。通过继承抽象类，子类可以获得抽象类中定义的方法和属性，并且必须提供对抽象方法的具体实现。
这种机制有助于在大型项目中建立一致的代码结构，同时提供了一种方法来强制子类提供特定的行为。
