SWING - 控件
每个用户界面考虑有以下三个主要方面：

UI 元素： 有用户最终看到并且与之交互的核心视觉元素。GWT 提供了一个大量的广泛使用和常见的元素列表。
布局： 他们定义应该如何在屏幕上组织 UI 元素，并且提供一个最终的外观和风格给 GUI（图形用户界面）。
行为： 当用户与 UI 元素交互时，这些事件发生。

Swing是Java中用于构建图形用户界面（GUI）的一组API。它由多个部分组成，其中一些主要的组成部分包括：

Swing 是 Java 中用于构建图形用户界面（GUI）的一组 API。它由多个部分组成，其中一些主要的组成部分包括：

1. **组件（Components）:**
   - Swing 提供了丰富的 GUI 组件，如 `JButton`、`JTextField`、`JLabel`、`JComboBox` 等。这些组件是用于构建用户界面的基本构建块。

2. **容器（Containers）:**
   - 容器是一种特殊类型的组件，它可以包含其他组件。常见的 Swing 容器包括 `JFrame`（顶层窗口）、`JPanel`（面板）、`JScrollPane`（滚动窗格）等。

3. **布局管理器（Layout Managers）:**
   - 布局管理器用于确定容器中组件的排列方式。Swing 提供了多种布局管理器，如 `FlowLayout`、`BorderLayout`、`GridLayout` 等，以便更灵活地控制组件的位置和大小。

4. **事件（Events）:**
   - Swing 使用事件模型来处理用户与界面的交互。事件包括鼠标点击、键盘输入、窗口操作等。通过为组件注册事件监听器，可以在事件发生时执行相应的操作。

5. **图形渲染（Graphics Rendering）:**
   - Swing 提供了绘图和渲染功能，允许在组件上进行自定义绘制。这可以通过扩展 `JComponent` 类并覆盖 `paintComponent` 方法来实现。

6. **模型-视图-控制器（Model-View-Controller，MVC）:**
   - Swing 的设计遵循 MVC 模式，其中模型（Model）表示数据和业务逻辑，视图（View）表示用户界面，而控制器（Controller）负责处理用户输入并更新模型和视图。

7. **轻量级组件（Lightweight Components）:**
   - Swing 组件是轻量级的，它们不依赖于底层平台的窗口系统。这种设计使得 Swing 应用程序在不同的操作系统上具有一致的外观和行为。

8. **可插拔外观和感觉（Pluggable Look and Feel）:**
   - Swing 允许应用程序选择外观（Look）和感觉（Feel），从而使其在不同的平台上具有相似的外观和感觉。Swing 默认提供了跨平台的外观，也支持自定义外观。

这些组成部分共同构成了 Swing 框架，使开发人员能够创建功能丰富、可移植且外观一致的 Java 图形用户界面。
