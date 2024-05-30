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

```
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
### 2.1.1 `public JFrame()`
构造一个最初不可见的新框架。  
此构造函数将组件的 locale 属性设置为 JComponent.getDefaultLocale 返回的值。  
抛出：  
HeadlessException – 如果 GraphicsEnvironment.isHeadless() 返回 true。
```
public JFrame() throws HeadlessException {
        super();
        frameInit();
    }
```

### 2.1.2 `public JFrame(GraphicsConfiguration gc) `
在指定屏幕设备的 GraphicsConfiguration 中创建一个 Frame，并带有一个空白标题。  
此构造函数将组件的 locale 属性设置为 JComponent.getDefaultLocale 返回的值。  
参数：  
gc – 用于构造新 Frame 的 GraphicsConfiguration；如果 gc 为 null，则假定为系统默认的 GraphicsConfiguration。  
抛出：  
IllegalArgumentException – 如果 gc 不是来自屏幕设备。当 GraphicsEnvironment.isHeadless() 返回 true 时，总是会抛出此异常。
```
public JFrame(GraphicsConfiguration gc) {
        super(gc);
        frameInit();
    }
```

### 2.1.3 `public JFrame(String title) throws HeadlessException`
创建一个带有指定标题的新 Frame，初始时不可见。  
此构造函数将组件的 locale 属性设置为 JComponent.getDefaultLocale 返回的值。  
参数：  
title – Frame 的标题  
抛出：  
HeadlessException – 如果 GraphicsEnvironment.isHeadless() 返回 true。  
```
public JFrame(String title) throws HeadlessException {
        super(title);
        frameInit();
    }
```

### 2.1.4 `public JFrame(String title, GraphicsConfiguration gc) `
创建一个带有指定标题和屏幕设备指定 GraphicsConfiguration 的 JFrame。  
此构造函数将组件的 locale 属性设置为 JComponent.getDefaultLocale 返回的值。  
参数：  
- title – 要在框架边框中显示的标题。null 值被视为空字符串 ""。  
- gc – 用于构建新 JFrame 的 GraphicsConfiguration；如果 gc 为 null，则假定为系统默认的 GraphicsConfiguration。  
抛出：  
- IllegalArgumentException – 如果 gc 不来自屏幕设备。当 GraphicsEnvironment.isHeadless() 返回 true 时，总是抛出此异常。  
```
public JFrame(String title, GraphicsConfiguration gc) {
        super(title, gc);
        frameInit();
    }
```

## 2.2 `protected void frameInit()`
由构造函数调用，以正确初始化 JFrame。
```
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

## 2.3 `protected JRootPane createRootPane()`
由构造方法调用以创建默认的 rootPane。
```
protected JRootPane createRootPane() {
        JRootPane rp = new JRootPane();
        // 注意：这里使用 setOpaque 而不是 LookAndFeel.installProperty，因为
        // 没有理由使 RootPane 不透明。为了使绘画工作正常，contentPane 必须是不透明的，因此 RootPane 也可以是不透明的。
        rp.setOpaque(true);
        return rp;
    }
```

## 2.4 `protected void processWindowEvent(final WindowEvent e)`
处理发生在此组件上的窗口事件。根据 defaultCloseOperation 属性的设置，隐藏窗口或将其释放。  
参数：  
e – 窗口事件  
```
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

