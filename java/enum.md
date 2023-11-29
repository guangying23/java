## 11 enum枚举和注解
### 11.1 枚举
在Java中，枚举（Enum）是一种特殊的数据类型，用于表示一组固定的常量。枚举可以帮助你在代码中创建更清晰、更具表达性的常量集合。

以下是枚举的主要特点和用法：

1. **定义枚举类型：** 枚举类型通过使用 `enum` 关键字来定义。

    ```java
    public enum Day {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
    }
    ```

    在这个例子中，`Day` 是一个枚举类型，包含了一周中的每一天。

2. **枚举常量：** 枚举类型中的每个值都是一个枚举常量。上面的例子中，`SUNDAY`、`MONDAY` 等就是枚举常量。

3. **访问枚举常量：** 你可以通过枚举类型名称和点运算符来访问枚举常量。

    ```java
    Day today = Day.MONDAY;
    ```

4. **switch 语句中的枚举：** 枚举常量很适合在 `switch` 语句中使用。

    ```java
    switch (today) {
        case MONDAY:
            System.out.println("It's Monday");
            break;
        case TUESDAY:
            System.out.println("It's Tuesday");
            break;
        // 其他的枚举常量
        default:
            System.out.println("It's not Monday or Tuesday");
    }
    ```

5. **枚举方法：** 枚举可以包含方法。你可以在枚举中定义方法，每个枚举常量可以提供自己的实现。

    ```java
    public enum Day {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;

        public boolean isWeekend() {
            return this == SATURDAY || this == SUNDAY;
        }
    }
    ```

    在这个例子中，`isWeekend` 方法用于判断一个给定的枚举常量是否代表周末。

6. **枚举实例的比较：** 枚举常量是单例的，可以直接使用 `==` 运算符进行比较。

    ```java
    if (today == Day.MONDAY) {
        System.out.println("It's Monday");
    }
    ```

枚举是一种非常方便和类型安全的方式来表示一组相关的常量。它们可以用于增强代码的可读性和可维护性，并防止无效的常量值。在需要一组有限且固定的常量时，使用枚举是一个很好的选择。

11.2 注解 
在Java中，注解（Annotation）是一种用于为程序元素（类、方法、字段等）提供元数据的标记。注解可以帮助开发人员在代码中添加信息，这些信息可以在运行时被读取和处理。注解是一种与程序代码关联的元数据形式，它可以在不影响程序语义的前提下提供有关程序的信息。

以下是注解的一些主要概念和用法：

1. **注解的定义：** 注解使用 `@` 符号加上注解名称的方式进行定义。注解可以有元素，这些元素可以有默认值。

    ```java
    // 定义一个简单的注解
    public @interface MyAnnotation {
        // 注解元素
        String value() default "Default value";
    }
    ```

2. **注解的使用：** 注解可以用于标记类、方法、字段等。在使用注解时，使用 `@` 符号加上注解名称并提供相应的值。

    ```java
    // 使用注解标记类
    @MyAnnotation(value = "Class Annotation")
    public class MyClass {

        // 使用注解标记方法
        @MyAnnotation(value = "Method Annotation")
        public void myMethod() {
            // 方法体
        }

        // 使用注解标记字段
        @MyAnnotation(value = "Field Annotation")
        private String myField;
    }
    ```

3. **内置注解：** Java提供了一些内置的注解，用于标记特定的情况，例如 `@Override` 表示方法覆盖，`@Deprecated` 表示已过时的元素等。

    ```java
    public class Example {

        @Override
        public String toString() {
            return "Overridden toString method";
        }

        @Deprecated
        public void oldMethod() {
            // 方法体
        }
    }
    ```

4. **元注解：** 元注解是用于注解其他注解的注解。Java中有几个元注解，例如 `@Retention`、`@Target`、`@Documented` 等，用于控制注解的行为。

    ```java
    // 元注解示例
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface MyMethodAnnotation {
        // 注解元素
        String value() default "Default value";
    }
    ```

5. **处理注解：** 注解的处理通常通过反射实现。在运行时，可以通过反射机制读取并处理包含注解的类、方法等。

    ```java
    // 处理注解的示例
    Class<?> myClass = MyClass.class;

    // 获取类上的注解
    MyAnnotation classAnnotation = myClass.getAnnotation(MyAnnotation.class);
    System.out.println(classAnnotation.value());

    // 获取方法上的注解
    Method myMethod = myClass.getMethod("myMethod");
    MyAnnotation methodAnnotation = myMethod.getAnnotation(MyAnnotation.class);
    System.out.println(methodAnnotation.value());
    ```

注解是Java中强大而灵活的特性，它们在很多框架和库中被广泛使用，用于提供配置信息、处理逻辑等。通过注解，开发人员可以更好地组织和描述代码，提高代码的可读性和可维护性。

