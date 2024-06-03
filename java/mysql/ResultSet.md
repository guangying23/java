| 修饰符和类型 | 字段 | 描述 |
| --- | --- | --- |
| static final int | CLOSE_CURSORS_AT_COMMIT | 指示具有此保持性（holdability）的打开 ResultSet 对象将在当前事务提交时关闭的常量。 |
| static final int | CONCUR_READ_ONLY | 指示 ResultSet 对象的并发模式为只读的常量。 |
| static final int | CONCUR_UPDATABLE | 指示 ResultSet 对象的并发模式为可更新的常量。 |
| static final int | FETCH_FORWARD | 指示结果集中的行将按正向（从第一行到最后一行）处理的常量。 |
| static final int | FETCH_REVERSE | 指示结果集中的行将按反向（从最后一行到第一行）处理的常量。 |
| static final int | FETCH_UNKNOWN | 指示结果集中行的处理顺序未知的常量。 |
| static final int | HOLD_CURSORS_OVER_COMMIT | 指示具有此保持性（holdability）的打开 ResultSet 对象在当前事务提交时将保持打开状态的常量。 |
| static final int | TYPE_FORWARD_ONLY | 指示 ResultSet 对象的类型为其游标只能向前移动的常量。 |
| static final int | TYPE_SCROLL_INSENSITIVE | 指示 ResultSet 对象的类型为可滚动但通常对 ResultSet 所依据的数据的更改不敏感的常量。 |
| static final int | TYPE_SCROLL_SENSITIVE | 指示 ResultSet 对象的类型为可滚动且通常对 ResultSet 所依据的数据的更改敏感的常量。 |

| 修饰符和类型 | 方法 | 描述 |
| --- | --- | --- |
| boolean | absolute(int row) | 将游标移动到此 ResultSet 对象中的指定行号。 |
| void | afterLast() | 将游标移动到此 ResultSet 对象的末尾，即最后一行之后。 |
| void | beforeFirst() | 将游标移动到此 ResultSet 对象的前面，即第一行之前。 |
| void | cancelRowUpdates() | 取消对当前行所做的更新。 |
| void | clearWarnings() | 清除在此 ResultSet 对象上报告的所有警告。 |
| void | close() | 立即释放此 ResultSet 对象的数据库和 JDBC 资源，而不是等待它自动关闭时发生。 |
| void | deleteRow() | 从此 ResultSet 对象和基础数据库中删除当前行。 |
| int | findColumn(String columnLabel) | 将给定的 ResultSet 列标签映射到其 ResultSet 列索引。 |
| boolean | first() | 将游标移动到此 ResultSet 对象的第一行。 |
| Array | getArray(int columnIndex) | 检索当前行中指定列的值作为 Java 编程语言中的 Array 对象。 |
| Array | getArray(String columnLabel) | 检索当前行中指定列的值作为 Java 编程语言中的 Array 对象。 |
| InputStream | getAsciiStream(int columnIndex) | 检索当前行中指定列的值作为 ASCII 字符流。 |
| InputStream | getAsciiStream(String columnLabel) | 检索当前行中指定列的值作为 ASCII 字符流。 |
| BigDecimal | getBigDecimal(int columnIndex) | 检索当前行中指定列的值，作为具有完全精度的 java.math.BigDecimal。 |
| BigDecimal | getBigDecimal(int columnIndex, int scale) | 已弃用。使用 getBigDecimal(int columnIndex) 或 getBigDecimal(String columnLabel)。 |
| BigDecimal | getBigDecimal(String columnLabel) | 检索当前行中指定列的值，作为具有完全精度的 java.math.BigDecimal。 |
| BigDecimal | getBigDecimal(String columnLabel, int scale) | 已弃用。使用 getBigDecimal(int columnIndex) 或 getBigDecimal(String columnLabel)。 |
| InputStream | getBinaryStream(int columnIndex) | 检索当前行中指定列的值，作为未解释的字节流。 |
| InputStream | getBinaryStream(String columnLabel) | 检索当前行中指定列的值，作为未解释的字节流。 |
| Blob | getBlob(int columnIndex) | 检索当前行中指定列的值，作为 Java 编程语言中的 Blob 对象。 |
| Blob | getBlob(String columnLabel) | 检索当前行中指定列的值，作为 Java 编程语言中的 Blob 对象。 |
| 修饰符和类型 | 方法 | 描述 |
| --- | --- | --- |
| boolean | getBoolean(int columnIndex) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 boolean。 |
| boolean | getBoolean(String columnLabel) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 boolean。 |
| byte | getByte(int columnIndex) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 byte。 |
| byte | getByte(String columnLabel) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 byte。 |
| byte[] | getBytes(int columnIndex) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 byte 数组。 |
| byte[] | getBytes(String columnLabel) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 byte 数组。 |
| Reader | getCharacterStream(int columnIndex) | 检索此 ResultSet 对象中当前行中指定列的值作为 java.io.Reader 对象。 |
| Reader | getCharacterStream(String columnLabel) | 检索此 ResultSet 对象中当前行中指定列的值作为 java.io.Reader 对象。 |
| Clob | getClob(int columnIndex) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 Clob 对象。 |
| Clob | getClob(String columnLabel) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 Clob 对象。 |
| int | getConcurrency() | 检索此 ResultSet 对象的并发模式。 |
| String | getCursorName() | 检索此 ResultSet 对象使用的 SQL 游标的名称。 |
| Date | getDate(int columnIndex) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 java.sql.Date 对象。 |
| Date | getDate(int columnIndex, Calendar cal) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 java.sql.Date 对象。 |
| Date | getDate(String columnLabel) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 java.sql.Date 对象。 |
| Date | getDate(String columnLabel, Calendar cal) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 java.sql.Date 对象。 |
| double | getDouble(int columnIndex) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 double。 |
| double | getDouble(String columnLabel) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 double。 |
| int | getFetchDirection() | 检索此 ResultSet 对象的抓取方向。 |
| int | getFetchSize() | 检索此 ResultSet 对象的抓取大小。 |
| float | getFloat(int columnIndex) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 float。 |
| float | getFloat(String columnLabel) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 float。 |
| int | getHoldability() | 检索此 ResultSet 对象的保持性。 |
| int | getInt(int columnIndex) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 int。 |
| int | getInt(String columnLabel) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 int。 |
| long | getLong(int columnIndex) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 long。 |
| long | getLong(String columnLabel) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 long。 |
| ResultSetMetaData | getMetaData() | 检索此 ResultSet 对象的列的数量、类型和属性。 |
| Reader | getNCharacterStream(int columnIndex) | 检索此 ResultSet 对象中当前行中指定列的值作为 java.io.Reader 对象。 |
| Reader | getNCharacterStream(String columnLabel) | 检索此 ResultSet 对象中当前行中指定列的值作为 java.io.Reader 对象。 |
| NClob | getNClob(int columnIndex) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 NClob 对象。 |
| NClob | getNClob(String columnLabel) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 NClob 对象。 |
| 修饰符和类型 | 方法 | 描述 |
| --- | --- | --- |
| String | getNString(int columnIndex) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 String。 |
| String | getNString(String columnLabel) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 String。 |
| Object | getObject(int columnIndex) | 获取此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 Object。 |
| <T> T | getObject(int columnIndex, Class<T> type) | 检索此 ResultSet 对象中当前行中指定列的值，并将其从 SQL 类型转换为请求的 Java 数据类型（如果支持转换）。 |
| Object | getObject(int columnIndex, Map<String,Class<?>> map) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 Object。 |
| Object | getObject(String columnLabel) | 获取此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 Object。 |
| <T> T | getObject(String columnLabel, Class<T> type) | 检索此 ResultSet 对象中当前行中指定列的值，并将其从 SQL 类型转换为请求的 Java 数据类型（如果支持转换）。 |
| Object | getObject(String columnLabel, Map<String,Class<?>> map) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 Object。 |
| Ref | getRef(int columnIndex) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 Ref 对象。 |
| Ref | getRef(String columnLabel) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 Ref 对象。 |
| int | getRow() | 检索当前行号。 |
| RowId | getRowId(int columnIndex) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 java.sql.RowId 对象。 |
| RowId | getRowId(String columnLabel) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 java.sql.RowId 对象。 |
| short | getShort(int columnIndex) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 short。 |
| short | getShort(String columnLabel) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 short。 |
| SQLXML | getSQLXML(int columnIndex) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 java.sql.SQLXML 对象。 |
| SQLXML | getSQLXML(String columnLabel) | 检索此 ResultSet 对象中当前行中指定列的值作为 Java 编程语言中的 java.sql.SQLXML 对象。 |

