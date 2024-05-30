顶层容器（Top-Level Container）是 Swing 应用程序的核心组成部分之一，它们通常是 JFrame、JDialog、JApplet 和 JWindow 等类。顶层容器是独立于其他容器的，通常由操作系统窗口管理器管理。
每个顶层容器都有一个 JRootPane，JRootPane 包含多个子面板，这些子面板管理顶层容器的不同部分。

### 顶层容器的结构

顶层容器的层次结构如下：

1. **JFrame**、**JDialog**、**JApplet** 或 **JWindow**
   - 这些是顶层容器。
2. **JRootPane**
   - `JRootPane` 是顶层容器的直接子组件，它包含以下部分：
     - **GlassPane**
     - **LayeredPane**
     - **ContentPane**
     - **MenuBar**

### 顶层容器的详细结构

```plaintext
JFrame
 └── JRootPane
      ├── GlassPane
      ├── LayeredPane
      │    ├── ContentPane
      │    └── MenuBar (optional)
      └── MenuBar (optional, older Swing versions)
```

### 组件介绍

1. **JFrame**: 用于创建标准窗口，包含边框、标题栏和关闭按钮。
2. **JRootPane**: 管理顶层容器的所有内容。它包含 `GlassPane`、`LayeredPane`、`ContentPane` 和可选的 `MenuBar`。
3. **GlassPane**: 位于所有组件之上，通常用于临时的覆盖效果或捕获所有鼠标事件。
4. **LayeredPane**: 管理不同层次的组件，可以使得组件在 Z 轴方向上互相叠加。
5. **ContentPane**: 顶层容器的主要内容区域。开发者通常将 UI 组件添加到 `ContentPane` 中。
6. **MenuBar**: 可选的菜单栏，用于显示菜单。

### 访问和使用顶层容器的组件

#### 获取 `ContentPane`

`ContentPane` 是开发者通常与之交互的区域，用于添加和组织用户界面组件。

```java
JFrame frame = new JFrame("Example");
Container contentPane = frame.getContentPane();
contentPane.setLayout(new BorderLayout());
```

#### 设置 `GlassPane`

`GlassPane` 可以用于在所有其他组件之上绘制或捕获事件。

```java
JPanel glassPane = (JPanel) frame.getGlassPane();
glassPane.setOpaque(false);
glassPane.setVisible(true);
```

#### 设置 `LayeredPane`

`LayeredPane` 可以在不同的层次上添加组件。

```java
JLayeredPane layeredPane = frame.getLayeredPane();
JLabel label = new JLabel("Layered Label");
label.setBounds(10, 10, 100, 30);
layeredPane.add(label, JLayeredPane.PALETTE_LAYER);
```

### 示例代码

以下示例展示了如何在顶层容器中设置和使用这些面板：

```java
import javax.swing.*;
import java.awt.*;

public class TopLevelContainerExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Top Level Container Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // 设置 ContentPane
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new JButton("Center Button"), BorderLayout.CENTER);

        // 设置 GlassPane
        JPanel glassPane = (JPanel) frame.getGlassPane();
        glassPane.setOpaque(false);
        glassPane.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                System.out.println("GlassPane clicked");
            }
        });
        glassPane.setVisible(true);

        // 设置 LayeredPane
        JLayeredPane layeredPane = frame.getLayeredPane();
        JLabel label = new JLabel("Layered Label");
        label.setBounds(10, 10, 100, 30);
        layeredPane.add(label, JLayeredPane.PALETTE_LAYER);

        frame.setVisible(true);
    }
}
```

这个示例展示了如何在 `JFrame` 中使用 `ContentPane`、`GlassPane` 和 `LayeredPane`。其中，`GlassPane` 被设置为捕获鼠标点击事件，并在控制台输出点击信息。`LayeredPane` 中添加了一个标签，并设置了其位置和层次。
