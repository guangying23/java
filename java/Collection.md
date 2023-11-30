在Java中，集合（Collection）是一种用于存储和操作对象的数据结构。集合框架是Java标准库的一部分，提供了一组接口和实现类，用于表示和操作不同类型的集合数据。
Java集合框架主要分为两个接口层次结构：`Collection` 和 `Map`。
Java集合框架体系是一个层次结构，由多个接口和实现类组成，提供了一组通用的数据结构和算法。以下是Java集合框架的主要接口和实现类：

### 接口层次结构

1. **Collection 接口：** 表示一组对象，是所有集合类的根接口。它派生了三个主要的子接口：
   - **List 接口：** 有序、可重复的集合。实现类包括 `ArrayList`、`LinkedList`、`Vector` 等。
   - **Set 接口：** 无序、不可重复的集合。实现类包括 `HashSet`、`LinkedHashSet`、`TreeSet` 等。
   - **Queue 接口：** 队列，按照先进先出（FIFO）的顺序处理元素。实现类包括 `LinkedList`、`PriorityQueue` 等。

2. **Map 接口：** 表示键值对映射。实现类包括 `HashMap`、`LinkedHashMap`、`TreeMap`、`Hashtable` 等。

### 实现类

#### Collection 接口的实现类

- **List 接口的实现类：**
  - `ArrayList`：基于动态数组实现，支持快速随机访问。
  - `LinkedList`：基于双向链表实现，支持高效的插入和删除操作。
  - `Vector`：类似于 `ArrayList`，但是是同步的，通常较少使用。

- **Set 接口的实现类：**
  - `HashSet`：基于哈希表实现，无序，允许为 `null`。
  - `LinkedHashSet`：基于哈希表和链表实现，按照插入顺序维护元素。
  - `TreeSet`：基于红黑树实现，按照元素的自然顺序或者通过比较器进行排序。

- **Queue 接口的实现类：**
  - `LinkedList`：实现了 `Queue` 接口，支持队列操作。

#### Map 接口的实现类

- **HashMap：** 基于哈希表实现，键值对无序，允许为 `null`。
- **LinkedHashMap：** 基于哈希表和链表实现，按照插入顺序或者访问顺序维护键值对。
- **TreeMap：** 基于红黑树实现，按照键的自然顺序或者通过比较器进行排序。
- **Hashtable：** 早期的实现类，与 `HashMap` 类似但是同步，通常较少使用。

### 其他重要接口

1. **Iterator 接口：** 用于遍历集合中的元素。
2. **ListIterator 接口：** 扩展了 `Iterator`，允许双向访问列表元素。
3. **Map.Entry 接口：** 表示 `Map` 中的键值对。

Java集合框架提供了丰富而强大的功能，适用于各种场景和需求。选择适当的集合类型取决于具体的应用需求和性能考虑。
![image](https://github.com/guangying23/java/assets/54796147/fa9903dd-be86-4cdf-9e8c-d81754148d42)
