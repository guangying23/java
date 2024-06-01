每个 JComponent 都可以拥有一个或多个边框。边框是非常有用的对象，虽然它们本身不是组件，但知道如何绘制 Swing 组件的边缘。边框不仅用于绘制线条和精美的边缘，还用于提供标题和围绕组件的空白空间。

注意：
我们的示例在 JPanel、JLabel 和 JComponent 的自定义子类上设置边框。尽管从技术上讲，你可以在任何继承自 JComponent 的对象上设置边框，但许多标准 Swing 组件的外观和感觉实现并不适合用户设置的边框。
通常，当你想在 JPanel 或 JLabel 以外的标准 Swing 组件上设置边框时，我们建议你将组件放入 JPanel 中，并在 JPanel 上设置边框。

要在 JComponent 周围放置一个边框，你可以使用其 setBorder 方法。你可以使用 BorderFactory 类来创建 Swing 提供的大多数边框。如果你需要引用一个边框 — 比如说，因为你想在多个组件中使用它 — 你可以将其保存
在一个类型为 Border 的变量中。这里是一个创建带边框容器的代码示例：

```java
JPanel pane = new JPanel();
pane.setBorder(BorderFactory.createLineBorder(Color.black));
```

这是一个容器的图片，其中包含一个标签组件。边框绘制的黑线标记了容器的边缘。  
![image](https://github.com/guangying23/java/assets/54796147/97430631-c2e8-4244-b75a-8638cb4211d3)

本页其余部分讨论以下主题：

- 边框演示示例
- 使用 Swing 提供的边框
- 创建自定义边框
- 边框 API
- 使用边框的示例

## The BorderDemo Example
以下图片展示了一个名为 BorderDemo 的应用程序，该应用程序显示了 Swing 提供的边框。我们稍后将在“使用 Swing 提供的边框”中展示创建这些边框的代码。
