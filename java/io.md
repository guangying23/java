I/O（Input/Output）流是Java中处理输入和输出的机制。它提供了一种统一的方式来处理从不同来源读取数据和将数据写入不同目的地。I/O流主要用于处理文件、网络通信、键盘输入等操作。

在Java中，I/O流被分为两种类型：字节流和字符流。

### 字节流（Byte Streams）：

1. **InputStream 和 OutputStream：** 这是字节流的抽象基类。`InputStream` 用于从输入流中读取字节，而 `OutputStream` 用于向输出流中写入字节。

   ```java
   InputStream inputStream = new FileInputStream("example.txt");
   OutputStream outputStream = new FileOutputStream("output.txt");
   ```

2. **FileInputStream 和 FileOutputStream：** 用于从文件读取字节和向文件写入字节的类。

   ```java
   FileInputStream fileInputStream = new FileInputStream("example.txt");
   FileOutputStream fileOutputStream = new FileOutputStream("output.txt");
   ```

3. **BufferedInputStream 和 BufferedOutputStream：** 提供了缓冲功能，可以提高读写的性能。

   ```java
   InputStream inputStream = new BufferedInputStream(new FileInputStream("example.txt"));
   OutputStream outputStream = new BufferedOutputStream(new FileOutputStream("output.txt"));
   ```

### 字符流（Character Streams）：

1. **Reader 和 Writer：** 这是字符流的抽象基类。`Reader` 用于从输入流中读取字符，而 `Writer` 用于向输出流中写入字符。

   ```java
   Reader reader = new FileReader("example.txt");
   Writer writer = new FileWriter("output.txt");
   ```

2. **FileReader 和 FileWriter：** 用于从文件读取字符和向文件写入字符的类。

   ```java
   FileReader fileReader = new FileReader("example.txt");
   FileWriter fileWriter = new FileWriter("output.txt");
   ```

3. **BufferedReader 和 BufferedWriter：** 提供了缓冲功能，可以提高读写的性能。

   ```java
   Reader reader = new BufferedReader(new FileReader("example.txt"));
   Writer writer = new BufferedWriter(new FileWriter("output.txt"));
   ```

### 使用I/O流的基本步骤：

1. **选择合适的流：** 根据操作的数据类型（字节或字符）和来源（文件、网络、键盘等），选择合适的流类。

2. **打开流：** 使用流类的构造方法创建相应的流对象，并关联到数据源或目的地。

3. **读取或写入数据：** 使用流对象提供的方法进行数据的读取或写入操作。

4. **关闭流：** 当操作完成后，及时关闭流，释放相关资源。

```java
// 例子：使用字符流从文件读取内容并输出到控制台
try (Reader reader = new FileReader("example.txt");
     BufferedReader bufferedReader = new BufferedReader(reader)) {
    String line;
    while ((line = bufferedReader.readLine()) != null) {
        System.out.println(line);
    }
} catch (IOException e) {
    e.printStackTrace();
}
```

总体而言，I/O流是Java中进行输入和输出操作的重要工具，提供了丰富的类库用于满足不同场景的需求。在实际应用中，根据需要选择合适的流类和相应的方法，同时注意异常处理和资源的关闭。
