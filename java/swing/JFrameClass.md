`JFrame` 是 `java.awt.Frame` 的扩展版本，添加了对 JFC/Swing 组件架构的支持。你可以在 Java 教程中的 “How to Make Frames” 部分找到关于使用 `JFrame` 的任务导向文档。

`JFrame` 类与 `Frame` 类有一些不兼容之处。像所有其他 JFC/Swing 顶级容器一样，`JFrame` 的唯一子组件是 `JRootPane`。根面板提供的内容面板应包含 `JFrame` 显示的所有非菜单组件。这与 AWT 的 `Frame` 不同。为了方便起见，该类的 `add`、`remove` 和 `setLayout` 方法被重写，以便将调用委托给 `ContentPane` 的相应方法。例如，你可以如下添加一个子组件到框架中：

```java
frame.add(child);
```

子组件将被添加到 `ContentPane`。内容面板始终为非空。尝试将其设置为 null 将导致 `JFrame` 抛出异常。默认的内容面板将有一个 `BorderLayout` 管理器。有关添加、删除和设置 `JFrame` 布局管理器的详细信息，请参阅 `RootPaneContainer`。

与 `Frame` 不同，`JFrame` 对用户尝试关闭窗口时如何响应有一些概念。默认行为是当用户关闭窗口时简单地隐藏 `JFrame`。要更改默认行为，可以调用 `setDefaultCloseOperation` 方法。要使 `JFrame` 的行为与 `Frame` 实例相同，请使用 `setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE)`。

有关内容面板和根面板提供的其他功能的更多信息，请参阅 Java 教程中的 "Using Top-Level Containers"。

在多屏幕环境中，你可以在不同的屏幕设备上创建 `JFrame`。有关详细信息，请参阅 `Frame`。

警告：Swing 不是线程安全的。有关更多信息，请参阅 Swing 的线程策略。

警告：此类的序列化对象将与未来的 Swing 版本不兼容。当前的序列化支持适用于在相同版本的 Swing 运行的应用程序之间进行短期存储或 RMI。从 1.4 版开始，所有 JavaBeans™ 的长期存储支持已添加到 `java.beans` 包中。请参阅 `java.beans.XMLEncoder`。

```java
public class JFrame  extends Frame implements WindowConstants,
                                              Accessible,
                                              RootPaneContainer,
                              TransferHandler.HasGetTransferHandler
```

# 一、属性
## 1.1 `public static final int EXIT_ON_CLOSE = 3;`
  `EXIT_ON_CLOSE` 是默认窗口关闭操作之一。如果一个窗口将此设置为关闭操作并且在小程序（applet）中关闭，则可能会抛出 `SecurityException`。建议仅在应用程序中使用此操作。
## 1.2 `private static final Object defaultLookAndFeelDecoratedKey =new StringBuffer("JFrame.defaultLookAndFeelDecorated");`
键入 AppContext，用于检查是否应默认提供装饰。
## 1.3 `private int defaultCloseOperation = HIDE_ON_CLOSE;`
## 1.4 `private TransferHandler transferHandler;`
此框架的 TransferHandler。TransferHandler 是一个 Swing 类，用于处理拖放（drag and drop）以及剪切、复制、粘贴操作。它允许组件与用户或其他组件之间进行数据传输。
## 1.5 `protected JRootPane rootPane;`
用于管理此框架的 contentPane、可选的 menuBar 以及 glassPane 的 JRootPane 实例。
## 1.6 `protected boolean rootPaneCheckingEnabled = false;`
如果为 true，则对 add 和 setLayout 的调用将转发到 contentPane。初始值为 false，但在构造 JFrame 时设置为 true。

# 二、方法
## 2.1 构造函数
### 2.1.1 JFrame()
构造一个最初不可见的新框架。  
此构造函数将组件的 locale 属性设置为 JComponent.getDefaultLocale 返回的值。  
抛出：  
HeadlessException – 如果 GraphicsEnvironment.isHeadless() 返回 true。
```java
public JFrame() throws HeadlessException {
        super();
        frameInit();
    }
```

### 2.1.2 JFrame(GraphicsConfiguration gc) 
在指定屏幕设备的 GraphicsConfiguration 中创建一个 Frame，并带有一个空白标题。  
此构造函数将组件的 locale 属性设置为 JComponent.getDefaultLocale 返回的值。  
参数：  
gc – 用于构造新 Frame 的 GraphicsConfiguration；如果 gc 为 null，则假定为系统默认的 GraphicsConfiguration。  
抛出：  
IllegalArgumentException – 如果 gc 不是来自屏幕设备。当 GraphicsEnvironment.isHeadless() 返回 true 时，总是会抛出此异常。
```java
public JFrame(GraphicsConfiguration gc) {
        super(gc);
        frameInit();
    }
```

### 2.1.3 JFrame(String title)
创建一个带有指定标题的新 Frame，初始时不可见。  
此构造函数将组件的 locale 属性设置为 JComponent.getDefaultLocale 返回的值。  
参数：  
title – Frame 的标题  
抛出：  
HeadlessException – 如果 GraphicsEnvironment.isHeadless() 返回 true。  
```java
public JFrame(String title) throws HeadlessException {
        super(title);
        frameInit();
    }
```

### 2.1.4 JFrame(String title, GraphicsConfiguration gc) 
创建一个带有指定标题和屏幕设备指定 GraphicsConfiguration 的 JFrame。  
此构造函数将组件的 locale 属性设置为 JComponent.getDefaultLocale 返回的值。  
参数：  
- title – 要在框架边框中显示的标题。null 值被视为空字符串 ""。  
- gc – 用于构建新 JFrame 的 GraphicsConfiguration；如果 gc 为 null，则假定为系统默认的 GraphicsConfiguration。  
抛出：  
- IllegalArgumentException – 如果 gc 不来自屏幕设备。当 GraphicsEnvironment.isHeadless() 返回 true 时，总是抛出此异常。  
```java
public JFrame(String title, GraphicsConfiguration gc) {
        super(title, gc);
        frameInit();
    }
```

## 2.2 frameInit()
由构造函数调用，以正确初始化 JFrame。
```java
protected void frameInit() {
        enableEvents(AWTEvent.KEY_EVENT_MASK | AWTEvent.WINDOW_EVENT_MASK);
        setLocale( JComponent.getDefaultLocale() );
        setRootPane(createRootPane());
        setBackground(UIManager.getColor("control"));
        setRootPaneCheckingEnabled(true);
        if (JFrame.isDefaultLookAndFeelDecorated()) {
            boolean supportsWindowDecorations =
            UIManager.getLookAndFeel().getSupportsWindowDecorations();
            if (supportsWindowDecorations) {
                setUndecorated(true);
                getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
            }
        }
        sun.awt.SunToolkit.checkAndSetPolicy(this);
    }
```

## 2.3 createRootPane()
由构造方法调用以创建默认的 rootPane。
```java
protected JRootPane createRootPane() {
        JRootPane rp = new JRootPane();
        // 注意：这里使用 setOpaque 而不是 LookAndFeel.installProperty，因为
        // 没有理由使 RootPane 不透明。为了使绘画工作正常，contentPane 必须是不透明的，因此 RootPane 也可以是不透明的。
        rp.setOpaque(true);
        return rp;
    }
```

## 2.4 processWindowEvent(final WindowEvent e)
处理发生在此组件上的窗口事件。根据 defaultCloseOperation 属性的设置，隐藏窗口或将其释放。  
参数：  
e – 窗口事件  
```java
protected void processWindowEvent(final WindowEvent e) {
        super.processWindowEvent(e);

        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            switch (defaultCloseOperation) {
                case HIDE_ON_CLOSE:
                    setVisible(false);
                    break;
                case DISPOSE_ON_CLOSE:
                    dispose();
                    break;
                case EXIT_ON_CLOSE:
                    // This needs to match the checkExit call in
                    // setDefaultCloseOperation
                    System.exit(0);
                    break;
                case DO_NOTHING_ON_CLOSE:
                default:
            }
        }
    }
```

## 2.5 setDefaultCloseOperation(int operation)
设置用户对该框架发起“关闭”操作时默认发生的操作。必须指定以下选项之一：  
- DO_NOTHING_ON_CLOSE（在 WindowConstants 中定义）：不执行任何操作；需要程序在已注册的 WindowListener 对象的 windowClosing 方法中处理该操作。  
- HIDE_ON_CLOSE（在 WindowConstants 中定义）：在调用任何已注册的 WindowListener 对象后自动隐藏框架。  
- DISPOSE_ON_CLOSE（在 WindowConstants 中定义）：在调用任何已注册的 WindowListener 对象后自动隐藏并释放框架。  
- EXIT_ON_CLOSE（在 JFrame 中定义）：使用 System exit 方法退出应用程序。仅在应用程序中使用此选项。  

默认情况下，该值设置为 HIDE_ON_CLOSE。对该属性值的更改将触发一个属性更改事件，属性名称为“defaultCloseOperation”。  
注意：当 Java 虚拟机 (VM) 内的最后一个可显示窗口被释放时，VM 可能会终止。有关更多信息，请参见 AWT 线程问题。  

参数：  
operation – 用户关闭框架时应执行的操作  

抛出：  
SecurityException – 如果指定了 EXIT_ON_CLOSE 并且 SecurityManager 不允许调用者调用 System.exit  
IllegalArgumentException – 如果 defaultCloseOperation 值不是上述有效选项之一
```java
public void setDefaultCloseOperation(int operation) {
        if (operation != DO_NOTHING_ON_CLOSE &&
            operation != HIDE_ON_CLOSE &&
            operation != DISPOSE_ON_CLOSE &&
            operation != EXIT_ON_CLOSE) {
            throw new IllegalArgumentException("defaultCloseOperation must be one of: DO_NOTHING_ON_CLOSE, HIDE_ON_CLOSE, DISPOSE_ON_CLOSE, or EXIT_ON_CLOSE");
        }

        if (operation == EXIT_ON_CLOSE) {
            SecurityManager security = System.getSecurityManager();
            if (security != null) {
                security.checkExit(0);
            }
        }
        if (this.defaultCloseOperation != operation) {
            int oldValue = this.defaultCloseOperation;
            this.defaultCloseOperation = operation;
            firePropertyChange("defaultCloseOperation", oldValue, operation);
        }
    }
```

## 2.6 getDefaultCloseOperation()
返回用户对该框架发起“关闭”操作时发生的操作。  
返回：  
一个整数，指示窗口关闭操作。
```java
public int getDefaultCloseOperation() {
        return defaultCloseOperation;
    }
```

## 2.7 setTransferHandler(TransferHandler newHandler)
设置 transferHandler 属性，这是一个支持将数据传输到此组件的机制。如果组件不支持数据传输操作，请使用 null。  
如果系统属性 suppressSwingDropSupport 为 false（默认值），并且此组件上的当前拖放目标为空或不是用户设置的拖放目标，则此方法将按以下方式更改拖放目标：如果 newHandler 为 null，它将清除拖放目标。如果不为 null，它将安装一个新的 DropTarget。  
注意：当与 JFrame 一起使用时，TransferHandler 仅提供数据导入功能，因为数据导出相关的方法目前类型化为 JComponent。  
有关更多信息，请参阅《如何使用拖放和数据传输》，Java 教程中的一节。  
参数：  
newHandler – 新的 TransferHandler
```java
public void setTransferHandler(TransferHandler newHandler) {
        TransferHandler oldHandler = transferHandler;
        transferHandler = newHandler;
        SwingUtilities.installSwingDropTargetAsNecessary(this, transferHandler);
        firePropertyChange("transferHandler", oldHandler, newHandler);
    }
```

## 2.8 TransferHandler getTransferHandler()
获取 transferHandler 属性。  
返回值：  
transferHandler 属性的值。
```java
public TransferHandler getTransferHandler() {
        return transferHandler;
    }
```

## 2.9  update(Graphics g) 
仅调用 paint(g)。重写此方法是为了避免不必要的背景清除操作。  
参数：  
g – 用于绘制的 Graphics 上下文
```java
public void update(Graphics g) {
        paint(g);
    }
```

## 2.10 setJMenuBar(JMenuBar menubar)
设置此框架的菜单栏。  
参数：  
menubar – 放置在框架中的菜单栏
```java
public void setJMenuBar(JMenuBar menubar) {
        getRootPane().setMenuBar(menubar);
    }
```

## 2.11 getJMenuBar() 
返回此框架上设置的菜单栏。  
返回：  
此框架的菜单栏  
```java
public JMenuBar getJMenuBar() {
        return getRootPane().getMenuBar();
    }
```

## 2.12 isRootPaneCheckingEnabled() 
返回是否将对 add 和 setLayout 的调用转发到 contentPane。  
返回：  
如果 add 和 setLayout 被转发，则返回 true；否则返回 false。  
```java
protected boolean isRootPaneCheckingEnabled() {
        return rootPaneCheckingEnabled;
    }
```

## 2.13 setRootPaneCheckingEnabled(boolean enabled) 
设置是否将对 add 和 setLayout 的调用转发到 contentPane。  
参数：  
enabled – 如果 add 和 setLayout 被转发，则为 true；如果它们应直接操作 JFrame，则为 false。
```java
protected void setRootPaneCheckingEnabled(boolean enabled) {
        rootPaneCheckingEnabled = enabled;
    }
```

## 2.14 addImpl(Component comp, Object constraints, int index)
添加指定的子组件。此方法被重写以有条件地将调用转发到 contentPane。默认情况下，子组件被添加到 contentPane 而不是框架中，详细信息请参见 RootPaneContainer。  
参数：  
- comp – 要增强的组件
- constraints – 要遵守的约束
- index – 索引

抛出：
- IllegalArgumentException – 如果将窗口添加到容器
```java
protected void addImpl(Component comp, Object constraints, int index)
    {
        if(isRootPaneCheckingEnabled()) {
            getContentPane().add(comp, constraints, index);
        }
        else {
            super.addImpl(comp, constraints, index);
        }
    }
```

## 2.15 remove(Component comp) 
将指定的组件从容器中移除。如果 comp 不是 rootPane，则该方法将转发调用到 contentPane。如果 comp 不是 JFrame 或 contentPane 的子组件，则此操作将不起作用。  
参数：  
- comp：要移除的组件

抛出：
- NullPointerException：如果 comp 为 null
```java
public void remove(Component comp) {
        if (comp == rootPane) {
            super.remove(comp);
        } else {
            getContentPane().remove(comp);
        }
    }
```

## 2.16 setLayout(LayoutManager manager) 
设置布局管理器。根据需要将调用条件性地转发到 contentPane。有关更多信息，请参阅 RootPaneContainer。  
参数：
- manager：要设置的布局管理器
```java
public void setLayout(LayoutManager manager) {
        if(isRootPaneCheckingEnabled()) {
            getContentPane().setLayout(manager);
        }
        else {
            super.setLayout(manager);
        }
    }
```


## 2.17 getRootPane() 
返回此窗体的 rootPane 对象。  
返回值：  
rootPane 属性
```java
public JRootPane getRootPane() {
        return rootPane;
    }
```

## 2.18 setRootPane(JRootPane root)
设置 rootPane 属性。此方法由构造函数调用。  
参数：  
root - 此窗体的 rootPane 对象    
```java
protected void setRootPane(JRootPane root)
    {
        if(rootPane != null) {
            remove(rootPane);
        }
        rootPane = root;
        if(rootPane != null) {
            boolean checkingEnabled = isRootPaneCheckingEnabled();
            try {
                setRootPaneCheckingEnabled(false);
                add(rootPane, BorderLayout.CENTER);
            }
            finally {
                setRootPaneCheckingEnabled(checkingEnabled);
            }
        }
    }
```

## 2.19 setIconImage(Image image) 
设置要显示为窗口图标的图像。

此方法可用于指定单个图像作为窗口的图标，而不是使用 setIconImages()。

下面的语句：
```java
setIconImage(image);
```

等同于：
```java
ArrayList<Image> imageList = new ArrayList<Image>();
imageList.add(image);
setIconImages(imageList);
```

注意：本地窗口系统可能会使用不同尺寸的不同图像来表示窗口，具体取决于上下文（例如窗口装饰、窗口列表、任务栏等）。它们也可以在所有上下文中只使用一个图像或不使用图像。
```java
public void setIconImage(Image image) {
        super.setIconImage(image);
    }
```

## 2.20 getContentPane() 
返回此框架的 contentPane 对象。

返回值：  
contentPane 属性
```java
public Container getContentPane() {
        return getRootPane().getContentPane();
    }
```

## 2.21 setContentPane(Container contentPane) 
设置 contentPane 属性。此方法由构造函数调用。  
Swing 的绘图架构需要在容纳层次结构中有一个不透明的 JComponent。通常由 content pane 提供此功能。如果你替换了 content pane，建议你用一个不透明的 JComponent 替换它。

参数：
- contentPane – 此框架的 contentPane 对象

异常：
- IllegalComponentStateException – （运行时异常）如果 content pane 参数为空
```java
public void setContentPane(Container contentPane) {
        getRootPane().setContentPane(contentPane);
    }
```

## 2.22 getLayeredPane() 
返回此框架的 layeredPane 对象。

返回值：
- layeredPane 属性
```java
public JLayeredPane getLayeredPane() {
        return getRootPane().getLayeredPane();
    }
```

## 2.23 setLayeredPane(JLayeredPane layeredPane) 
设置layeredPane属性。此方法由构造函数调用。  
参数：  
layeredPane - 此框架的layeredPane对象  
抛出：  
IllegalComponentStateException -（运行时异常）如果分层窗格参数为null"
```java
public void setLayeredPane(JLayeredPane layeredPane) {
        getRootPane().setLayeredPane(layeredPane);
    }
```

## 2.24 getGlassPane() 
返回此框架的glassPane对象。  
返回：  
glassPane属性
```java
public Component getGlassPane() {
        return getRootPane().getGlassPane();
    }
```

## 2.25 setGlassPane(Component glassPane) 
设置glassPane属性。此方法由构造函数调用。  
参数：  
glassPane – 此框架的glassPane对象
```java
public void setGlassPane(Component glassPane) {
        getRootPane().setGlassPane(glassPane);
    }
```

## 2.26 getGraphics() 
这个方法为指定的组件创建一个图形上下文。然而，如果该组件当前不可显示，这个方法将返回null。
```java
public Graphics getGraphics() {
        JComponent.getGraphicsInvoked(this);
        return super.getGraphics();
    }
```

## 2.27 repaint(long time, int x, int y, int width, int height) 
这个方法在指定的时间（以毫秒为单位）内重绘此组件的指定矩形区域。详细信息请参考 RepaintManager。  
参数：
- time：更新前的最大时间（以毫秒为单位）
- x：x 坐标
- y：y 坐标
- width：宽度
- height：高度
```java
public void repaint(long time, int x, int y, int width, int height) {
        if (RepaintManager.HANDLE_TOP_LEVEL_PAINT) {
            RepaintManager.currentManager(this).addDirtyRegion(
                              this, x, y, width, height);
        }
        else {
            super.repaint(time, x, y, width, height);
        }
    }
```

## 2.28 setDefaultLookAndFeelDecorated(boolean defaultLookAndFeelDecorated) 
这个方法提供了一个提示，指示新创建的 JFrame 是否应该由当前的外观（look and feel）提供其窗口装饰（如边框、关闭窗口的小部件、标题等）。如果 defaultLookAndFeelDecorated 是 true，当前的外观支持提供窗口装饰，并且当前窗口管理器支持无装饰窗口，那么新创建的 JFrame 将由当前外观提供窗口装饰。否则，新创建的 JFrame 将由当前窗口管理器提供窗口装饰。
通过以下方法，您可以在单个 JFrame 上实现相同的效果：
```java
JFrame frame = new JFrame();
frame.setUndecorated(true);
frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
```
参数：
- defaultLookAndFeelDecorated：指示当前外观是否应该提供窗口装饰的提示

Got it! Anything else you need help with?
```java
public static void setDefaultLookAndFeelDecorated(boolean defaultLookAndFeelDecorated) {
        if (defaultLookAndFeelDecorated) {
            SwingUtilities.appContextPut(defaultLookAndFeelDecoratedKey, Boolean.TRUE);
        } else {
            SwingUtilities.appContextPut(defaultLookAndFeelDecoratedKey, Boolean.FALSE);
        }
    }
```

## 2.29 isDefaultLookAndFeelDecorated() 
这个方法返回一个布尔值，用来指示新创建的 JFrame 是否应该由当前的外观提供窗口装饰。这只是一个提示，因为某些外观可能不支持这个功能。
```java
public static boolean isDefaultLookAndFeelDecorated() {
        Boolean defaultLookAndFeelDecorated =
            (Boolean) SwingUtilities.appContextGet(defaultLookAndFeelDecoratedKey);
        if (defaultLookAndFeelDecorated == null) {
            defaultLookAndFeelDecorated = Boolean.FALSE;
        }
        return defaultLookAndFeelDecorated.booleanValue();
    }
```

## 2.30 paramString() 
这个方法返回 JFrame 的字符串表示形式。该方法仅用于调试目的，返回的字符串的内容和格式可能因实现而异。返回的字符串可能为空，但不会是 null。
```java
protected String paramString() {
        String defaultCloseOperationString;
        if (defaultCloseOperation == HIDE_ON_CLOSE) {
            defaultCloseOperationString = "HIDE_ON_CLOSE";
        } else if (defaultCloseOperation == DISPOSE_ON_CLOSE) {
            defaultCloseOperationString = "DISPOSE_ON_CLOSE";
        } else if (defaultCloseOperation == DO_NOTHING_ON_CLOSE) {
            defaultCloseOperationString = "DO_NOTHING_ON_CLOSE";
        } else if (defaultCloseOperation == 3) {
            defaultCloseOperationString = "EXIT_ON_CLOSE";
        } else defaultCloseOperationString = "";
        String rootPaneString = (rootPane != null ?
                                 rootPane.toString() : "");
        String rootPaneCheckingEnabledString = (rootPaneCheckingEnabled ?
                                                "true" : "false");

        return super.paramString() +
        ",defaultCloseOperation=" + defaultCloseOperationString +
        ",rootPane=" + rootPaneString +
        ",rootPaneCheckingEnabled=" + rootPaneCheckingEnabledString;
    }
```

## 以下为Accessibility support

## 2.31 `protected AccessibleContext accessibleContext = null;`
`accessibleContext` 属性。

## 2.32 getAccessibleContext() 
与此 `JFrame` 关联的 `AccessibleContext`。对于 `JFrames`，`AccessibleContext` 以 `AccessibleJFrame` 的形式出现。如果有必要，将创建一个新的 `AccessibleJFrame` 实例。  
返回：  
一个作为此 `JFrame` 的 `AccessibleContext` 的 `AccessibleJFrame`。
```java
public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJFrame();
        }
        return accessibleContext;
    }
```

# 三、内部类
该类实现了对 `JFrame` 类的无障碍支持。它提供了适用于框架用户界面元素的 Java 无障碍 API 实现。
```java
protected class AccessibleJFrame extends AccessibleAWTFrame {

        // AccessibleContext methods
        /**
         * 获取该对象的可访问名称。
         *返回：
         *对象的本地化名称 -- 如果该对象没有名称，则可以为 null。
         */
        public String getAccessibleName() {
            if (accessibleName != null) {
                return accessibleName;
            } else {
                if (getTitle() == null) {
                    return super.getAccessibleName();
                } else {
                    return getTitle();
                }
            }
        }

        /**
         * 获取该对象的状态。
          *返回：
          *一个包含对象当前状态集的 AccessibleStateSet 实例。
         */
        public AccessibleStateSet getAccessibleStateSet() {
            AccessibleStateSet states = super.getAccessibleStateSet();

            if (isResizable()) {
                states.add(AccessibleState.RESIZABLE);
            }
            if (getFocusOwner() != null) {
                states.add(AccessibleState.ACTIVE);
            }
            // FIXME:  [[[WDW - should also return ICONIFIED and ICONIFIABLE
            // if we can ever figure these out]]]
            return states;
        }
    } // inner class AccessibleJFrame
```
