## 十二 异常
### 12.1 概述
在Java中，异常（Exception）是一种在程序执行期间可能发生的错误或异常情况的事件。异常处理是Java编程语言中一种强大而灵活的机制，它允许开发人员检测、抛出和捕获异常，
以便更有效地处理程序中的错误。

以下是异常的主要概念和用法：

1. **异常的分类：** 在Java中，异常分为两种主要类型：
   - **检查异常（Checked Exception）：** 编译器会检查这类异常，程序员必须显式地处理或声明它们。例如，`IOException`。
   - **运行时异常（Unchecked Exception）：** 运行时异常是在程序运行时可能发生的异常，通常是由于程序错误导致的。程序员可以选择处理或不处理这些异常。例如，`NullPointerException`。

2. **异常类层次结构：** Java中的异常是通过类来表示的，它们都继承自 `Throwable` 类。`Exception` 和 `Error` 是 `Throwable` 的两个主要子类，其中 `Exception` 用于表示可捕获的异常，
   而 `Error` 用于表示不可恢复的错误。

3. **抛出异常：** 在Java中，可以使用 `throw` 关键字手动抛出异常。通常情况下，异常是由Java运行时系统自动抛出的，但程序员也可以在代码中明确地抛出异常。

    ```java
    void myMethod() {
        if (someCondition) {
            throw new MyException("This is a custom exception");
        }
    }
    ```

4. **捕获异常：** 使用 `try-catch` 块可以捕获并处理异常。在 `try` 块中放置可能抛出异常的代码，而在 `catch` 块中处理异常。

    ```java
    try {
        // 可能抛出异常的代码
    } catch (SomeException e) {
        // 处理异常的代码
    }
    ```

5. **finally 块：** `finally` 块中的代码无论是否发生异常都会被执行。通常用于释放资源，例如关闭文件或网络连接。

    ```java
    try {
        // 可能抛出异常的代码
    } catch (SomeException e) {
        // 处理异常的代码
    } finally {
        // 无论是否发生异常，都会执行的代码
    }
    ```

6. **自定义异常：** 程序员可以定义自己的异常类，这些类通常继承自 `Exception` 或其子类。

    ```java
    class MyException extends Exception {
        public MyException(String message) {
            super(message);
        }
    }
    ```

异常处理是Java程序中非常重要的一部分，它可以帮助开发人员更好地处理错误情况，提高程序的健壮性。合理地使用异常机制可以使代码更清晰、更可读，并提高程序的可维护性。

12.2 异常体系图
![image](https://github.com/guangying23/java/assets/54796147/95b1f61a-3080-44bb-944c-c10df30fa6ce)
