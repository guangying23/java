## Class StringBuffer
java.lang.Object
  java.lang.StringBuffer

public final class StringBuffer  
extends Object  
implements Appendable, Serializable, Comparable<StringBuffer>, CharSequence  

线程安全的可变字符序列。StringBuffer 类似于 String，但可以被修改。在任何时刻，它都包含某个特定的字符序列，但序列的长度和内容可以通过某些方法调用进行更改。
StringBuffer 对多线程是安全的。方法在必要时进行同步，以便对任何特定实例的所有操作都表现得好像它们以与各个线程涉及的方法调用顺序一致的某种串行顺序发生。

StringBuffer 的主要操作是 append（追加）和 insert（插入）方法，这些方法被重载以接受任何类型的数据。每个方法实际上都将给定数据转换为字符串，然后将该字符串的字符追加或插入到字符串缓冲区中。append 方法总是在缓冲区的末尾添加这些字符；insert 方法在指定点添加字符。

例如，如果 z 引用一个当前内容为 "start" 的字符串缓冲区对象，则方法调用 z.append("le") 将使字符串缓冲区包含 "startle"，而 z.insert(4, "le") 将改变字符串缓冲区为 "starlet"。

通常情况下，如果 sb 引用一个 StringBuffer 的实例，则 sb.append(x) 的效果与 sb.insert(sb.length(), x) 相同。

每当发生涉及源序列的操作（例如从源序列追加或插入）时，该类仅在执行操作的字符串缓冲区上同步，而不是在源上。请注意，虽然 StringBuffer 设计为可以由多个线程同时安全使用，但如果构造函数或 append 或 insert 操作传递了一个跨线程共享的源序列，则调用代码必须确保操作期间源序列的视图是一致且不变的。这可以通过调用者在操作调用期间持有锁，使用不可变的源序列，或者不跨线程共享源序列来满足。

每个字符串缓冲区都有一个容量。只要字符串缓冲区中包含的字符序列的长度不超过容量，就不需要分配一个新的内部缓冲数组。如果内部缓冲区溢出，它会自动变大。

除非另有说明，否则向此类中的构造函数或方法传递 null 参数将导致抛出 NullPointerException。

从 JDK 5 版本开始，这个类已经被增加了一个等效的类，旨在由单个线程使用，即 StringBuilder。StringBuilder 类通常应该优先于这个类使用，因为它支持所有相同的操作，但它更快，因为它不执行同步。

API Note:  
StringBuffer 实现了 Comparable 接口，但没有重写 equals 方法。因此，StringBuffer 的自然排序与 equals 方法不一致。如果在 SortedMap 或 SortedSet 中使用 StringBuffer 对象作为键或元素，应当谨慎处理。更多信息请参阅 Comparable、SortedMap 或 SortedSet 相关文档。  
Since:  1.0  
 
## Constructor Summary 
| 构造函数 | 描述 |
| --- | --- |
| StringBuffer() | 构造一个没有字符且初始容量为16个字符的字符串缓冲区。 |
| StringBuffer(int capacity) | 构造一个没有字符且指定初始容量的字符串缓冲区。 |
| StringBuffer(CharSequence seq) | 构造一个包含与指定 CharSequence 相同字符的字符串缓冲区。 |
| StringBuffer(String str) | 构造一个初始化为指定字符串内容的字符串缓冲区。 |

## Method Summary 
| 类型及修饰符 | 方法 | 描述 |
| --- | --- | --- |
| StringBuffer | append(boolean b) | 将布尔参数的字符串表示形式追加到序列中。 |
| StringBuffer | append(char c) | 将字符参数的字符串表示形式追加到这个序列中。 |
| StringBuffer | append(char[] str) | 将字符数组参数的字符串表示形式追加到这个序列中。 |
| StringBuffer | append(char[] str, int offset, int len) | 将字符数组参数的子数组的字符串表示形式追加到这个序列中。 |
| StringBuffer | append(double d) | 将双精度浮点数参数的字符串表示形式追加到这个序列中。 |
| StringBuffer | append(float f) | 将浮点数参数的字符串表示形式追加到这个序列中。 |
| StringBuffer | append(int i) | 将整数参数的字符串表示形式追加到这个序列中。 |
| StringBuffer | append(long lng) | 将长整数参数的字符串表示形式追加到这个序列中。 |
| StringBuffer | append(CharSequence s) | 将指定的CharSequence追加到这个序列中。 |
| StringBuffer | append(CharSequence s, int start, int end) | 将指定的CharSequence的子序列追加到这个序列中。 |
| StringBuffer | append(Object obj) | 将对象参数的字符串表示形式追加到这个序列中。 |
| StringBuffer | append(String str) | 将指定的字符串追加到这个字符序列中。 |
| StringBuffer | append(StringBuffer sb) | 将指定的StringBuffer追加到这个序列中。 |
| StringBuffer | appendCodePoint(int codePoint) | 将代码点参数的字符串表示形式追加到这个序列中。 |
| int | capacity() | 返回当前容量。 |
| char | charAt(int index) | 返回此序列在指定索引处的字符值。 |
| IntStream | chars() | 返回一个int流，这个流扩展了这个序列中的字符值。 |
| int | codePointAt(int index) | 返回指定索引处的字符（Unicode代码点）。 |
| int | codePointBefore(int index) | 返回指定索引前的字符（Unicode代码点）。 |
| int | codePointCount(int beginIndex, int endIndex) | 返回此序列指定文本范围内的Unicode代码点数量。 |
| IntStream | codePoints() | 返回此序列中的代码点值流。 |
| int | compareTo(StringBuffer another) | 字典式比较两个StringBuffer实例。 |
| StringBuffer | delete(int start, int end) | 移除此序列的一个子字符串中的字符。 |
| StringBuffer | deleteCharAt(int index) | 移除此序列中指定位置的字符。 |
| void | ensureCapacity(int minimumCapacity) | 确保容量至少等于指定的最小值。 |
| void | getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) | 将字符从这个序列复制到目标字符数组dst中。 |
| int | indexOf(String str) | 返回这个字符串中指定子字符串第一次出现的索引。 |
| int | indexOf(String str, int fromIndex) | 返回这个字符串中指定子字符串第一次出现的索引，从指定的索引开始。 |
| StringBuffer | insert(int offset, boolean b) | 在这个序列中插入布尔参数的字符串表示形式。 |
| StringBuffer | insert(int offset, char c) | 在这个序列中插入字符参数的字符串表示形式。 |
| StringBuffer | insert(int offset, char[] str) | 在这个序列中插入字符数组参数的字符串表示形式。 |
| StringBuffer | insert(int index, char[] str, int offset, int len) | 在这个序列中插入字符数组参数的子数组的字符串表示形式。 |
| StringBuffer | insert(int offset, double d) | 在这个序列中插入双精度浮点数参数的字符串表示形式。 |
| StringBuffer | insert(int offset, float f) | 在这个序列中插入浮点数参数的字符串表示形式。 |
| StringBuffer | insert(int offset, int i) | 在这个序列中插入整数参数的字符串表示形式。 |
| StringBuffer | insert(int offset, long l) | 在这个序列中插入长整数参数的字符串表示形式。 |
| StringBuffer | insert(int dstOffset, CharSequence s) | 在这个序列中插入指定的CharSequence。 |
| StringBuffer | insert(int dstOffset, CharSequence s, int start, int end) | 在这个序列中插入指定的CharSequence的子序列。 |
| StringBuffer | insert(int offset, Object obj) | 在这个字符序列中插入对象参数的字符串表示形式。 |
| StringBuffer | insert(int offset, String str) | 在这个字符序列中插入字符串。 |
| int | lastIndexOf(String str) | 返回这个字符串中指定子字符串最后一次出现的索引。 |
| int | lastIndexOf(String str, int fromIndex) | 返回这个字符串中指定子字符串最后一次出现的索引，从指定的索引反向搜索。 |
| int | length() | 返回长度（字符数）。 |
| int | offsetByCodePoints(int index, int codePointOffset) | 返回从给定索引偏移codePointOffset代码点后的索引。 |
| StringBuffer | repeat(int codePoint, int count) | 在这个序列中重复count次代码点参数的字符串表示形式。 |
| StringBuffer | repeat(CharSequence cs, int count) | 在这个序列中追加count次指定的CharSequence cs。 |
| StringBuffer | replace(int start, int end, String str) | 用指定的字符串替换这个序列的一个子字符串中的字符。 |
| StringBuffer | reverse() | 使这个字符序列被其序列的反向替换。 |
| void | setCharAt(int index, char ch) | 将指定索引处的字符设置为ch。 |
| void | setLength(int newLength) | 设置字符序列的长度。 |
| CharSequence | subSequence(int start, int end) | 返回这个序列的一个子序列，作为一个新的字符序列。 |
| String | substring(int start) | 返回包含在这个字符序列当前包含的字符的子序列的新String。 |
| String | substring(int start, int end) | 返回包含在这个序列当前包含的字符的子序列的新String。 |
| String | toString() | 返回表示这个序列中数据的字符串。 |
| void | trimToSize() | 尝试减少用于字符序列的存储。

## Methods declared in class java.lang.Object Link icon
clone, equals, finalize, getClass, hashCode, notify, notifyAll, wait, wait, wait

## Methods declared in interface java.lang.CharSequence Link icon
chars, codePoints, isEmpty

