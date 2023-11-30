## 十三 常用类
### 13.1 包装类
在Java中，包装类（Wrapper Class）是一种将基本数据类型（如 `int`、`char`、`boolean` 等）封装成对象的类。每个基本数据类型都有对应的包装类，这些包装类提供了一些额外的方法和功能，
同时允许基本数据类型被当作对象处理。

以下是常见的基本数据类型及其对应的包装类：

- `byte`：`Byte`
- `short`：`Short`
- `int`：`Integer`
- `long`：`Long`
- `float`：`Float`
- `double`：`Double`
- `char`：`Character`
- `boolean`：`Boolean`

主要用法和概述如下：

1. **自动装箱和拆箱：** Java提供了自动装箱（Autoboxing）和自动拆箱（Unboxing）的机制，使得基本数据类型和包装类之间的转换更加方便。

    ```java
    // 自动装箱
    Integer intValue = 42;

    // 自动拆箱
    int primitiveValue = intValue;
    ```

2. **包装类的方法：** 包装类提供了许多方法，用于在基本数据类型和包装类之间进行转换，以及进行一些常见的操作。

    包装类提供了一些常用的方法，这些方法使得在基本数据类型和包装类之间进行转换、比较以及其他一些操作变得更加方便。以下是一些常用的包装类方法：

- 转换方法

**valueOf()：** 将指定的基本数据类型转换为包装类对象。

    ```java
    Integer intValue = Integer.valueOf(42);
    ```

**xxxValue()：** 将包装类对象转换为对应的基本数据类型。

    ```java
    int primitiveValue = intValue.intValue();
    ```

**parseXxx()：** 将字符串转换为对应的基本数据类型。

    ```java
    int parsedValue = Integer.parseInt("123");
    ```

- 比较方法

**equals()：** 比较两个包装类对象的值是否相等。

    ```java
    Integer a = 42;
    Integer b = 42;
    boolean isEqual = a.equals(b);
    ```

**compareTo()：** 比较两个包装类对象的大小关系。

    ```java
    Integer a = 42;
    Integer b = 30;
    int result = a.compareTo(b);
    ```

- 常量和极值

**MAX_VALUE 和 MIN_VALUE：** 表示基本数据类型的最大值和最小值。

    ```java
    int maxValue = Integer.MAX_VALUE;
    int minValue = Integer.MIN_VALUE;
    ```

- 其他方法

**toString()：** 将包装类对象转换为字符串。

    ```java
    Integer intValue = 42;
    String stringValue = intValue.toString();
    ```

**hashCode()：** 返回包装类对象的哈希码。

    ```java
    Integer intValue = 42;
    int hashCode = intValue.hashCode();
    ```

**getClass()：** 返回包装类对象的运行时类。

    ```java
    Integer intValue = 42;
    Class<?> clazz = intValue.getClass();
    ```

这些方法使得在基本数据类型和包装类之间进行转换和比较更加方便。需要注意的是，由于自动装箱和拆箱的存在，有些方法可能会导致性能开销，因此在性能敏感的场合需要谨慎使用。

3. **Null 值：** 包装类允许使用 `null` 表示空值，而基本数据类型无法表示空值。

    ```java
    Integer nullableValue = null;
    ```

4. **常量：** 包装类提供了一些常量，如 `Integer.MAX_VALUE`、`Integer.MIN_VALUE` 等，用于表示基本数据类型的取值范围。

    ```java
    int maxValue = Integer.MAX_VALUE;
    int minValue = Integer.MIN_VALUE;
    ```

5. **Java 5 中的泛型和集合：** 泛型和集合框架通常要求使用对象而不是基本数据类型。在这种情况下，包装类是不可或缺的，因为它们允许将基本数据类型转换为对象。

    ```java
    List<Integer> integerList = new ArrayList<>();
    integerList.add(42);

    int retrievedValue = integerList.get(0);
    ```

包装类在许多情况下都非常有用，特别是在需要将基本数据类型转换为对象的场景中，例如在集合框架中使用泛型。但是在性能敏感的场合，需要注意自动装箱和拆箱的开销，以确保不会影响程序的性能。

### 13.2 String
`String` 类是 Java 中的一个核心类，用于表示字符串。在 Java 中，字符串是不可变的，这意味着一旦创建了字符串对象，就不能修改其内容。`String` 类提供了许多方法来操作字符串数据。

以下是 `String` 类的一些重要特征和概述：

1. **不可变性：** 字符串一旦被创建，就不能被修改。任何对字符串的修改都将创建一个新的字符串对象。

2. **字符串池：** Java 中的字符串池是一个保存字符串对象的缓存区域，可以提高字符串的重复使用，减少内存消耗。由于字符串是不可变的，相同的字符串常常会被重复使用。

3. **构造方法：** `String` 类有多个构造方法，可以通过字面值、字符数组、字符串缓冲区等不同的方式创建字符串对象。

4. **常用方法：** `String` 类提供了许多方法来执行字符串操作，例如连接字符串、截取子串、查找字符或子串、转换大小写等。

   ```java
   String str1 = "Hello";
   String str2 = "World";
   
   // 连接字符串
   String result = str1.concat(" " + str2);
   
   // 获得字符串长度
   int length = result.length();
   
   // 转换为大写
   String upperCase = result.toUpperCase();
   
   // 截取子串
   String substring = result.substring(0, 5);
   ```

5. **不可变性的优点：** 不可变性使字符串在多线程环境下更加安全，也方便进行字符串共享和缓存。

6. **字符串比较：** 字符串比较时应使用 `equals()` 方法，而不是 `==`。`equals()` 方法比较的是字符串的内容，而 `==` 比较的是对象的引用。

```java
String str1 = "Hello";
String str2 = "Hello";

// 正确的字符串比较方式
if (str1.equals(str2)) {
    System.out.println("Strings are equal");
}
```

总体而言，`String` 类是 Java 编程中常用的类之一，具有广泛的应用。在字符串处理中，了解并熟练使用 `String` 类的方法是非常重要的。

### 13.3 Stringbuffer 
`StringBuffer` 类是 Java 中用于处理可变字符串的类，它与 `String` 类的主要区别在于可变性。与 `String` 类不同，`StringBuffer` 对象的内容可以被修改，而不会创建新的对象。
`StringBuffer` 主要用于需要频繁进行字符串拼接、插入和修改的情况，特别是在多线程环境下，`StringBuffer` 是线程安全的，而 `StringBuilder` 则是非线程安全的。

以下是 `StringBuffer` 类的一些重要特征和概述：

1. **可变性：** `StringBuffer` 是可变的，可以通过一系列的方法来修改其内容，如 `append()`、`insert()`、`delete()` 等。

2. **线程安全性：** `StringBuffer` 是线程安全的，因此适用于多线程环境。然而，由于同步机制的存在，相比于 `StringBuilder`，`StringBuffer` 在性能上可能稍显低效。

3. **构造方法：** `StringBuffer` 有多个构造方法，可以通过不同的方式创建对象，例如通过默认构造方法或通过指定初始容量的构造方法。

   ```java
   StringBuffer buffer1 = new StringBuffer(); // 默认构造方法
   StringBuffer buffer2 = new StringBuffer("Hello"); // 使用字符串初始化
   StringBuffer buffer3 = new StringBuffer(20); // 指定初始容量的构造方法
   ```

4. **常用方法：** `StringBuffer` 提供了一系列用于字符串操作的方法，包括拼接、插入、删除、反转等。

   ```java
   StringBuffer buffer = new StringBuffer("Hello");
   
   // 追加字符串
   buffer.append(" World");
   
   // 在指定位置插入字符串
   buffer.insert(5, " Java");
   
   // 删除子串
   buffer.delete(0, 5);
   
   // 反转字符串
   buffer.reverse();
   ```

5. **与 `StringBuilder` 的区别：** `StringBuffer` 和 `StringBuilder` 都是可变的字符串，但 `StringBuilder` 不是线程安全的。在单线程环境下，通常使用 `StringBuilder`，
   而在多线程环境下，使用 `StringBuffer`。

```java
StringBuilder builder = new StringBuilder(); // 非线程安全
StringBuffer buffer = new StringBuffer(); // 线程安全
```

总体而言，`StringBuffer` 类是在处理字符串时非常有用的工具，特别适用于需要频繁修改字符串的情况。在选择使用 `StringBuffer` 还是 `StringBuilder` 时，需要根据具体的线程安全要求
来决定。

### 13.4 StringBuilder
`StringBuilder` 类是 Java 中用于处理可变字符串的类，与 `String` 和 `StringBuffer` 不同，`StringBuilder` 主要用于单线程环境，因为它不是线程安全的。与 `StringBuffer` 
类似，`StringBuilder` 对象的内容可以被修改，而不会创建新的对象，这使得它在字符串拼接和修改的场景中具有高效性能。

以下是 `StringBuilder` 类的一些重要特征和概述：

1. **可变性：** `StringBuilder` 是可变的，可以通过一系列的方法来修改其内容，如 `append()`、`insert()`、`delete()` 等。

2. **非线程安全：** 与 `StringBuffer` 不同，`StringBuilder` 不是线程安全的。因此，在多线程环境下，如果需要可变字符串且线程安全，应该使用 `StringBuffer`。

3. **构造方法：** `StringBuilder` 有多个构造方法，可以通过不同的方式创建对象，例如通过默认构造方法或通过指定初始容量的构造方法。

   ```java
   StringBuilder builder1 = new StringBuilder(); // 默认构造方法
   StringBuilder builder2 = new StringBuilder("Hello"); // 使用字符串初始化
   StringBuilder builder3 = new StringBuilder(20); // 指定初始容量的构造方法
   ```

4. **常用方法：** `StringBuilder` 提供了一系列用于字符串操作的方法，包括拼接、插入、删除等。

   ```java
   StringBuilder builder = new StringBuilder("Hello");
   
   // 追加字符串
   builder.append(" World");
   
   // 在指定位置插入字符串
   builder.insert(5, " Java");
   
   // 删除子串
   builder.delete(0, 5);
   ```

5. **性能优势：** 由于 `StringBuilder` 不是线程安全的，它在性能上通常比 `StringBuffer` 更高效，因为不需要进行同步操作。

```java
StringBuilder builder = new StringBuilder(); // 非线程安全
```

总体而言，`StringBuilder` 类是在处理可变字符串时的一种高效选择，特别适用于单线程环境。在多线程环境下，如果需要可变字符串且线程安全，应该选择 `StringBuffer`。

### 13.5 Math
`Math` 类是 Java 中的一个数学工具类，包含了许多用于执行基本数学运算的静态方法。这些方法提供了一些基本的数学功能，如取绝对值、求平方根、对数、三角函数等。
`Math` 类中的方法都是静态方法，因此不需要创建 `Math` 对象，可以直接通过类名调用这些方法。

以下是 `Math` 类的一些常用方法和概述：

1. **常数：** `Math` 类包含一些常用的数学常数，如 π（`Math.PI`）和自然对数的底数 e（`Math.E`）。

   ```java
   double pi = Math.PI;
   double e = Math.E;
   ```

2. **基本数学运算：** `Math` 类提供了一系列基本的数学运算，如加法、减法、乘法、除法等。

   ```java
   double sum = Math.addExact(10, 20);
   double difference = Math.subtractExact(30, 15);
   double product = Math.multiplyExact(5, 6);
   double quotient = Math.floorDiv(25, 4);
   ```

3. **取整和取余：** `Math` 类提供了取整和取余的方法。

   ```java
   double round = Math.round(3.7);
   double ceil = Math.ceil(4.2);
   double floor = Math.floor(4.8);
   double remainder = Math.IEEEremainder(10, 3);
   ```

4. **指数和对数：** `Math` 类提供了指数和对数运算的方法。

   ```java
   double power = Math.pow(2, 3);
   double squareRoot = Math.sqrt(25);
   double log = Math.log(100);
   ```

5. **三角函数：** `Math` 类提供了三角函数，如正弦、余弦、正切等。

   ```java
   double sinValue = Math.sin(Math.PI / 2);
   double cosValue = Math.cos(Math.PI);
   double tanValue = Math.tan(Math.PI / 4);
   ```

这些只是 `Math` 类中的一部分方法，还有许多其他方法可用于各种数学运算。`Math` 类提供了一些基本的数学工具，方便在 Java 中进行数学计算。

### 13.6 Arrays
在 Java 中，`Arrays` 类是一个包含用于操作数组（例如排序和搜索）的静态方法的工具类。它提供了一系列静态方法，用于处理数组的常见操作，例如排序、搜索、比较等。

以下是 `Arrays` 类的一些常用方法和概述：

1. **数组排序：** `Arrays` 类提供了用于对数组进行排序的方法，其中最常见的是 `sort()` 方法。

   ```java
   int[] array = {5, 2, 9, 1, 5};
   Arrays.sort(array);
   ```

2. **数组搜索：** `Arrays` 类提供了用于在数组中搜索指定值的方法，其中最常见的是 `binarySearch()` 方法。注意，数组在调用二分查找之前必须是有序的。

   ```java
   int[] array = {1, 2, 5, 5, 9};
   int index = Arrays.binarySearch(array, 5);
   ```

3. **数组填充：** `Arrays` 类提供了用于将数组的所有元素填充为指定值的方法。

   ```java
   int[] array = new int[5];
   Arrays.fill(array, 42);
   ```

4. **数组比较：** `Arrays` 类提供了用于比较两个数组是否相等的方法，其中最常见的是 `equals()` 方法。

   ```java
   int[] array1 = {1, 2, 3};
   int[] array2 = {1, 2, 3};
   boolean isEqual = Arrays.equals(array1, array2);
   ```

5. **数组复制：** `Arrays` 类提供了用于复制数组的方法，其中最常见的是 `copyOf()` 方法。

   ```java
   int[] originalArray = {1, 2, 3};
   int[] copiedArray = Arrays.copyOf(originalArray, 5);
   ```

6. **数组转换为字符串：** `Arrays` 类提供了将数组转换为字符串表示形式的方法，其中最常见的是 `toString()` 方法。

   ```java
   int[] array = {1, 2, 3};
   String arrayString = Arrays.toString(array);
   ```

这些方法使得在处理数组时更加方便，可以轻松地执行各种数组操作。`Arrays` 类是 Java 标准库中一个重要的工具类，尤其在处理数组时非常实用。

### 13.7 BigInteger 和 BigDecimal 类
`BigInteger` 和 `BigDecimal` 类是 Java 中用于处理大整数和大浮点数的类，它们属于 `java.math` 包。这两个类提供了对大数进行精确计算的支持，可以用于处理超过 `long` 和 `double` 范围的数值。

#### BigInteger 类概述：

`BigInteger` 类用于表示任意精度的整数。它提供了对整数进行基本的算术操作，如加法、减法、乘法和除法，而且不会发生溢出。

```java
import java.math.BigInteger;

BigInteger bigInteger1 = new BigInteger("1234567890123456789012345678901234567890");
BigInteger bigInteger2 = new BigInteger("9876543210987654321098765432109876543210");

BigInteger sum = bigInteger1.add(bigInteger2);
BigInteger difference = bigInteger1.subtract(bigInteger2);
BigInteger product = bigInteger1.multiply(bigInteger2);
BigInteger quotient = bigInteger1.divide(bigInteger2);
```

#### BigDecimal 类概述：

`BigDecimal` 类用于表示任意精度的浮点数。它提供了对浮点数进行基本的算术运算，并且可以精确处理小数点位数。

```java
import java.math.BigDecimal;

BigDecimal bigDecimal1 = new BigDecimal("123.456");
BigDecimal bigDecimal2 = new BigDecimal("987.654");

BigDecimal sum = bigDecimal1.add(bigDecimal2);
BigDecimal difference = bigDecimal1.subtract(bigDecimal2);
BigDecimal product = bigDecimal1.multiply(bigDecimal2);
BigDecimal quotient = bigDecimal1.divide(bigDecimal2, 10, BigDecimal.ROUND_HALF_UP);
```

在 `BigDecimal` 的 `divide` 方法中，第三个参数指定了小数点后的位数，而 `ROUND_HALF_UP` 则是指定了四舍五入的方式。

这两个类对于需要高精度计算或处理大数值的场景非常有用，比如金融计算、科学计算等。在这些情况下，使用 `BigInteger` 和 `BigDecimal` 类可以避免由于浮点数精度问题引起的计算误差。

### 13.8 日期类
在 Java 中，`java.util` 包提供了用于处理日期和时间的类，其中最常用的是 `Date` 类、`Calendar` 类以及在 Java 8 及更高版本中引入的 `LocalDate`、`LocalTime`、`LocalDateTime`、
`Instant` 等类。下面简要概述一些关键的日期类：

#### Date 类：

`Date` 类是 Java 早期提供的日期和时间类，但它有一些问题，包括不是线程安全的、设计上不够清晰等。在 Java 8 之前，它是处理日期和时间的主要类。

```java
import java.util.Date;

// 获取当前日期和时间
Date currentDate = new Date();

// 在指定时间戳创建日期对象
Date specificDate = new Date(1638274512000L);
```

#### Calendar 类：

`Calendar` 类是 `Date` 类的后继者，提供了更多的操作和更丰富的日期和时间处理功能。

```java
import java.util.Calendar;

// 获取当前日期和时间
Calendar calendar = Calendar.getInstance();

// 设置特定的日期
calendar.set(2023, Calendar.NOVEMBER, 30);
```

#### LocalDate、LocalTime、LocalDateTime、Instant（Java 8及更高版本）：

在 Java 8 及更高版本中，引入了新的日期和时间 API，包括 `LocalDate`、`LocalTime`、`LocalDateTime` 和 `Instant` 等类。这些类解决了旧 API 中的一些问题，
同时提供了更简洁、清晰、安全的方式来处理日期和时间。

```java
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.Instant;

// 获取当前日期
LocalDate currentDate = LocalDate.now();

// 获取当前时间
LocalTime currentTime = LocalTime.now();

// 获取当前日期和时间
LocalDateTime currentDateTime = LocalDateTime.now();

// 获取当前时间戳
Instant instant = Instant.now();
```

这些新的日期类提供了更好的不变性、线程安全性、可读性和精确性。在编写新的代码时，推荐使用这些类来进行日期和时间的处理。
