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
<img src="https://github.com/guangying23/java/assets/54796147/364ea8d9-bec0-409d-9e68-dce016085681" alt="示例图片" width="360" height="200">

下一张图片展示了一些哑光边框。创建哑光边框时，你需要指定边框在组件的顶部、左侧、底部和右侧占用多少像素。然后，你可以为哑光边框指定一个颜色或图标进行绘制。在选择图标和确定组件大小时需要小心，否则图标可能会被切断或在组件角落处不匹配。  
<img src="https://github.com/guangying23/java/assets/54796147/2a89b63a-2a75-491a-8fdc-d9f53b0e8b4b" alt="示例图片" width="360" height="200">  

下一张图片展示了带标题的边框。使用带标题的边框，你可以将任何边框转换为显示文本描述的边框。如果你不指定一个边框，将使用一个外观和感觉特定的边框。例如，在 Java 外观和感觉中，默认的带标题边框使用灰色线条，在 Windows 外观和感觉中，默认的带标题边框使用蚀刻边框。默认情况下，标题位于边框的左上方，如下图顶部所示。  
<img src="https://github.com/guangying23/java/assets/54796147/fb2f87e9-4f66-4342-9ec9-03ac763216ef" alt="示例图片" width="360" height="200">

下一张图片展示了复合边框。使用复合边框，你可以组合任何两个边框，这些边框本身可以是复合边框。  
<img src="https://github.com/guangying23/java/assets/54796147/85dab06d-c63e-4484-829d-e91723e70f65" alt="示例图片" width="360" height="200">

## Using the Borders Provided by Swing
接下来的代码展示了如何创建和设置你在前面几幅图中看到的边框。你可以在 [`BorderDemo.java` ](https://github.com/guangying23/java/blob/8fe8d444c492f7bce9b1ef39f4d51a933e806513/java/swing/source/BorderDemo.java)文件中找到程序的代码。

```java
// 保留对接下来几个边框的引用，
// 用于标题和复合边框。
Border blackline, raisedetched, loweredetched,
       raisedbevel, loweredbevel, empty;

blackline = BorderFactory.createLineBorder(Color.black);
raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
raisedbevel = BorderFactory.createRaisedBevelBorder();
loweredbevel = BorderFactory.createLoweredBevelBorder();
empty = BorderFactory.createEmptyBorder();

// 简单边框
jComp1.setBorder(blackline);
jComp2.setBorder(raisedbevel);
jComp3.setBorder(loweredbevel);
jComp4.setBorder(empty);

// 哑光边框
ImageIcon icon = createImageIcon("images/wavy.gif",
                                 "波纹线边框图标"); // 20x22

jComp5.setBorder(BorderFactory.createMatteBorder(
                                   -1, -1, -1, -1, icon));
jComp6.setBorder(BorderFactory.createMatteBorder(
                                    1, 5, 1, 1, Color.red));
jComp7.setBorder(BorderFactory.createMatteBorder(
                                    0, 20, 0, 0, icon));

// 带标题的边框
TitledBorder title;
title = BorderFactory.createTitledBorder("标题");
jComp8.setBorder(title);

title = BorderFactory.createTitledBorder(
                       blackline, "标题");
title.setTitleJustification(TitledBorder.CENTER);
jComp9.setBorder(title);

title = BorderFactory.createTitledBorder(
                       loweredetched, "标题");
title.setTitleJustification(TitledBorder.RIGHT);
jComp10.setBorder(title);

title = BorderFactory.createTitledBorder(
                       loweredbevel, "标题");
title.setTitlePosition(TitledBorder.ABOVE_TOP);
jComp11.setBorder(title);

title = BorderFactory.createTitledBorder(
                       empty, "标题");
title.setTitlePosition(TitledBorder.BOTTOM);
jComp12.setBorder(title);

// 复合边框
Border compound;
Border redline = BorderFactory.createLineBorder(Color.red);

// 这创建了一个漂亮的框架。
compound = BorderFactory.createCompoundBorder(
                          raisedbevel, loweredbevel);
jComp13.setBorder(compound);

// 在框架上添加一个红色轮廓。
compound = BorderFactory.createCompoundBorder(
                          redline, compound);
jComp14.setBorder(compound);

// 在红色轮廓的框架上添加标题。
compound = BorderFactory.createTitledBorder(
                          compound, "标题",
                          TitledBorder.CENTER,
                          TitledBorder.BELOW_BOTTOM);
jComp15.setBorder(compound);
```

这段代码详细说明了如何使用多种边框类型为 Swing 组件设置视觉样式。

正如你可能已经注意到的，代码使用了 `BorderFactory` 类来创建每个边框。`BorderFactory` 类位于 `javax.swing` 包中，它返回实现 `Border` 接口的对象。

`Border` 接口及其 Swing 提供的实现都位于 `javax.swing.border` 包中。通常，你不需要直接使用 `border` 包中的任何内容，除非在指定特定边框类的常量或引用 `Border` 类型时。这样的设计使得 Swing 的边框系统既灵活又易于使用，允许开发者快速地为 UI 组件添加视觉边界。

## Creating Custom Borders
如果 `BorderFactory` 没有提供足够的控制来定制边框的形式，那么你可能需要直接使用 `border` 包中的 API，甚至定义自己的边框。除了包含 `Border` 接口外，`border` 包还包含了实现你已经看到的边框的类：`LineBorder`、`EtchedBorder`、`BevelBorder`、`EmptyBorder`、`MatteBorder`、`TitledBorder` 和 `CompoundBorder`。`border` 包还包含一个名为 `SoftBevelBorder` 的类，其效果类似于 `BevelBorder`，但边缘更柔和。

如果 Swing 提供的边框不适合你的需求，你可以实现自己的边框。通常，你可以通过创建 `AbstractBorder` 类的子类来实现这一点。在你的子类中，你必须实现至少一个构造函数和以下两个方法：

- `paintBorder`，包含 `JComponent` 执行以绘制边框的绘图代码。
- `getBorderInsets`，指定边框绘制自身所需的空间。

如果自定义边框有内边距（它们通常有内边距），你需要重写 `AbstractBorder.getBorderInsets(Component c)` 和 `AbstractBorder.getBorderInsets(Component c, Insets insets)` 来提供正确的内边距。

要查看实现边框的示例，请参阅 `javax.swing.border` 包中类的源代码。这些示例将为你提供如何自定义和扩展边框功能的实际指导。       

## The Border API
下面的表格列出了常用的边框方法。使用边框的 API 分为两类：
- 使用 BorderFactory 创建边框
- 设置或获取组件的边框

| Method | Purpose |
| --- | --- |
| **Border createLineBorder(Color)** <br> **Border createLineBorder(Color, int)** | 创建线条边框。第一个参数是一个 java.awt.Color 对象，指定线条的颜色。可选的第二个参数指定线条的像素宽度。 |
| **Border createEtchedBorder()** <br> **Border createEtchedBorder(Color, Color)** <br> **Border createEtchedBorder(int)** <br> **Border createEtchedBorder(int, Color, Color)** | 创建蚀刻边框。可选的 Color 参数指定使用的高光和阴影颜色。带有 int 参数的方法允许边框的方式被指定为 EtchedBorder.RAISED 或 EtchedBorder.LOWERED。不带 int 参数的方法创建一个下降的蚀刻边框。 |
| **Border createLoweredBevelBorder()** | 创建一个使组件看起来低于周围区域的边框。 |
| **Border createRaisedBevelBorder()** | 创建一个使组件看起来高于周围区域的边框。 |
| **Border createBevelBorder(int, Color, Color)** <br> **Border createBevelBorder(int, Color, Color, Color, Color)** | 创建一个凸起或下陷的斜面边框，指定使用的颜色。整数参数可以是 BevelBorder.RAISED 或 BevelBorder.LOWERED。三参数构造函数指定高光和阴影颜色。五参数构造函数依次指定外高光、内高光、外阴影和内阴影颜色。 |
| **Border createEmptyBorder()** <br> **Border createEmptyBorder(int, int, int, int)** | 创建一个不可见的边框。如果没有指定参数，则边框不占用空间，这在创建没有可见边界的标题边框时很有用。可选参数指定边框在使用它的组件的顶部、左侧、底部和右侧（按此顺序）占用的像素数。此方法用于在组件周围放置空白空间。 |
| **MatteBorder createMatteBorder(int, int, int, int, Color)** <br> **MatteBorder createMatteBorder(int, int, int, int, Icon)** | 创建一个哑光边框。整数参数指定边框在使用它的组件的顶部、左侧、底部和右侧（按此顺序）占用的像素数。颜色参数指定边框应填充其区域的颜色。图标参数指定边框应该平铺其区域的图标。 |
| **TitledBorder createTitledBorder(String)** <br> **TitledBorder createTitledBorder(Border)** <br> **TitledBorder createTitledBorder(Border, String)** <br> **TitledBorder createTitledBorder(Border, String, int, int)** <br> **TitledBorder createTitledBorder(Border, String, int, int, Font)** <br> **TitledBorder createTitledBorder(Border, String, int, int, Font, Color)** | 创建一个带标题的边框。字符串参数指定要显示的标题。可选的字体和颜色参数指定标题文本的字体和颜色。边框参数指定应该显示的边框。如果没有指定边框，则使用外观和感觉特定的默认边框。默认情况下，标题横跨其伴随边框的顶部并左对齐。可选的整数参数依次指定标题的位置和对齐方式。TitledBorder 定义了以下可能的位置：ABOVE_TOP, TOP（默认）, BELOW_TOP, ABOVE_BOTTOM, BOTTOM, 和 BELOW_BOTTOM。你可以将对齐方式指定为 LEADING（默认），CENTER，或 TRAILING。在使用西方字母表的地区，LEADING 等同于 LEFT 且 TRAILING 等同于 RIGHT。 |
| CompoundBorder createCompoundBorder(Border, Border)|将两个边框组合成一个。第一个参数指定外边框；第二个参数指定内边框。|

| 方法 | 目的 |
| --- | --- |
| void setBorder(Border) | 设置或获取接收 JComponent 的边框。 |
| Border getBorder() | 获取接收 JComponent 的边框。 |
| void setBorderPainted(boolean) | 设置或获取组件的边框是否应显示（适用于 AbstractButton, JMenuBar, JPopupMenu, JProgressBar, 和 JToolBar）。 |
| boolean isBorderPainted() | 获取组件的边框是否应显示（适用于 AbstractButton, JMenuBar, JPopupMenu, JProgressBar, 和 JToolBar）。 |
