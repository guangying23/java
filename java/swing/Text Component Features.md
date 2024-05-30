JTextComponent 类是 Swing 文本组件的基础。该类为其所有后代提供以下可定制的功能：
 
一个称为文档的模型，用于管理组件的内容。  
一个视图，用于在屏幕上显示组件。  
一个称为编辑器工具包的控制器，用于读取和写入文本，并实现具有动作的编辑功能。  
支持无限撤销和重做。  
可插拔的插入符号以及插入符号更改侦听器和导航过滤器的支持。  
查看名为 TextComponentDemo 的示例以探索这些功能。尽管 TextComponentDemo 示例包含一个自定义的 JTextPane 实例，但本节讨论的功能被所有 JTextComponent 子类继承。 

[TextComponentDemo](https://docs.oracle.com/javase/tutorial/uiswing/examples/components/TextComponentDemoProject/src/components/TextComponentDemo.java)

![image](https://github.com/guangying23/java/assets/54796147/8ec8181a-5fe6-41ea-92a5-f70fe9a422e6)

上方的文本组件是定制的文本面板。下方的文本组件是JTextArea的一个实例，用作报告对文本面板内容所做的所有更改的日志。窗口底部的状态行报告选择的位置或插入符的位置，具体取决于是否选择了文本。

使用TextComponentDemo示例作为参考点，本节涵盖以下主题：

- 将文本操作关联到菜单和按钮
- 将文本操作关联到键盘快捷键
- 实现撤销和重做功能
- 文档相关概念
- 实现文档过滤器
- 监听文档变化
- 监听插入符和选择变化
- 编辑器工具包相关概念

## 一、将文本操作与菜单和按钮关联
所有 Swing 文本组件都支持标准的编辑命令，例如剪切、复制、粘贴和插入字符。每个编辑命令都由一个 Action 对象表示和实现。（要了解更多关于 actions 的信息，请参阅《如何使用 Actions》。）Actions 允许您将命令与 GUI 组件（如菜单项或按钮）关联起来，从而围绕文本组件构建 GUI。

您可以在任何文本组件上调用 getActions 方法，以接收包含此组件支持的所有 actions 的数组。还可以将 actions 数组加载到 HashMap 中，这样您的程序就可以通过名称检索一个 action。以下是从 TextComponentDemo 示例中获取文本窗格中的 actions 并将其加载到 HashMap 的代码。

```java
private HashMap<Object, Action> createActionTable(JTextComponent textComponent) {
    HashMap<Object, Action> actions = new HashMap<Object, Action>();
    Action[] actionsArray = textComponent.getActions();
    for (int i = 0; i < actionsArray.length; i++) {
        Action a = actionsArray[i];
        actions.put(a.getValue(Action.NAME), a);
    }
    return actions;
}
```

以下是从哈希映射中通过名称检索 action 的方法：

```java
private Action getActionByName(String name) {
    return actions.get(name);
}
```

您可以在您的程序中直接使用这两种方法。

下面的代码显示了如何创建剪切菜单项并将其与从文本组件中移除文本的 action 关联起来。

```java
protected JMenu createEditMenu() {
    JMenu menu = new JMenu("Edit");
    ...
    menu.add(getActionByName(DefaultEditorKit.cutAction));
    ...
```

这段代码使用了先前展示的方便方法通过名称获取 action。然后将 action 添加到菜单中。这就是您需要做的全部。菜单和 action 将处理其他所有事务。请注意，action 的名称来自于 DefaultEditorKit。该工具包提供了用于基本文本编辑的 actions，并且是 Swing 提供的所有编辑工具包的超类。因此，除非被定制覆盖，否则其功能对所有文本组件都可用。

为了效率，文本组件共享 actions。getActionByName(DefaultEditorKit.cutAction) 返回的 Action 对象由窗口底部的不可编辑 JTextArea 共享。这种共享特性有两个重要的影响：

通常情况下，不应修改从编辑工具包中获取的 Action 对象。如果这样做，更改会影响程序中的所有文本组件。
Action 对象可以在程序中操作其他文本组件，有时甚至超出您的意图。在本示例中，即使 JTextArea 是不可编辑的，它也与 JTextPane 共享 actions。（在文本区域中选择一些文本，然后选择剪切到剪贴板菜单项。您将听到蜂鸣声，因为文本区域是不可编辑的。）如果不想共享，可以自己实例化 Action 对象。DefaultEditorKit 定义了许多有用的 Action 子类。

以下是创建 Style 菜单并在其中放置 Bold 菜单项的代码：

```java
protected JMenu createStyleMenu() {
    JMenu menu = new JMenu("Style");

    Action action = new StyledEditorKit.BoldAction();
    action.putValue(Action.NAME, "Bold");
    menu.add(action);
    ...
```

StyledEditorKit 提供了用于实现样式文本编辑命令的 Action 子类。您会注意到，这段代码不是从编辑工具包中获取 action，而是创建了 BoldAction 类的一个实例。因此，这个 action 不会与任何其他文本组件共享，更改其名称也不会影响其他文本组件。

## 二、关联文本操作与按键。
除了将操作与 GUI 组件关联之外，您还可以使用文本组件的输入映射将操作与按键关联起来。输入映射在《如何使用键绑定》中有描述。

TextComponentDemo 示例中的文本窗格支持四个默认未提供的键绑定。

- 按下 Ctrl-B 可以将插入符号向后移动一个字符
- 按下 Ctrl-F 可以将插入符号向前移动一个字符
- 按下 Ctrl-N 可以将插入符号向下移动一行
- 按下 Ctrl-P 可以将插入符号向上移动一行

以下代码将 Ctrl-B 键绑定添加到文本窗格中。添加其他三个列出的键绑定的代码类似。

```java
InputMap inputMap = textPane.getInputMap();

KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_B,
                                       Event.CTRL_MASK);
inputMap.put(key, DefaultEditorKit.backwardAction);
```

首先，代码获取文本组件的输入映射。接下来，它找到表示 Ctrl-B 键序列的 KeyStroke 对象。最后，代码将按键绑定到将光标向后移动的操作。

## 三、实现撤销和重做有两个部分：

1. 记录可撤销的编辑。
2. 实现撤销和重做命令，并为其提供用户界面。

第一部分：记录可撤销的编辑
为了支持撤销和重做，文本组件必须记住每个编辑操作的发生顺序以及撤销每个编辑所需的步骤。示例程序使用 UndoManager 类的一个实例来管理其可撤销编辑的列表。在声明成员变量的地方创建撤销管理器：

```java
protected UndoManager undo = new UndoManager();
```

现在，让我们看看程序如何发现可撤销的编辑并将它们添加到撤销管理器中。

当文档内容发生可撤销的编辑时，文档会通知感兴趣的监听器。实现撤销和重做的一个重要步骤是在文本组件的文档上注册一个可撤销编辑监听器。以下代码将 MyUndoableEditListener 的一个实例添加到文本窗格的文档中：

```java
doc.addUndoableEditListener(new MyUndoableEditListener());
```

我们示例中使用的可撤销编辑监听器将编辑添加到撤销管理器的列表中：

```java
protected class MyUndoableEditListener implements UndoableEditListener {
    public void undoableEditHappened(UndoableEditEvent e) {
        // 记录编辑并更新菜单
        undo.addEdit(e.getEdit());
        undoAction.updateUndoState();
        redoAction.updateRedoState();
    }
}
```

请注意，此方法更新两个对象：undoAction 和 redoAction。它们分别是附加到“撤销”和“重做”菜单项的操作对象。接下来的步骤将向您展示如何创建菜单项以及如何实现这两个操作。有关可撤销编辑监听器和可撤销编辑事件的一般信息，请参阅《如何编写可撤销编辑监听器》。

注意：
默认情况下，每次可撤销的编辑都撤销一个字符输入。通过一些努力，可以将编辑分组，使一系列按键组合成一个可撤销的编辑。以这种方式分组编辑需要您定义一个类，拦截文档中的可撤销编辑事件，如果适当则将它们合并，并将结果转发给您的可撤销编辑监听器。

第二部分：实现撤销和重做命令
实现撤销和重做的第一步是创建要放入“编辑”菜单中的操作。

```java
JMenu menu = new JMenu("Edit");

// 撤销和重做是我们自己创建的操作
undoAction = new UndoAction();
menu.add(undoAction);

redoAction = new RedoAction();
menu.add(redoAction);
```

撤销和重做操作由自定义的 AbstractAction 子类实现：UndoAction 和 RedoAction。这些类是示例主类的内部类。

当用户调用撤销命令时，会调用 UndoAction 类的 actionPerformed 方法：

```java
public void actionPerformed(ActionEvent e) {
    try {
        undo.undo();
    } catch (CannotUndoException ex) {
        System.out.println("Unable to undo: " + ex);
        ex.printStackTrace();
    }
    updateUndoState();
    redoAction.updateRedoState();
}
```

该方法调用撤销管理器的 undo 方法，并更新菜单项以反映新的撤销/重做状态。

类似地，当用户调用重做命令时，会调用 RedoAction 类的 actionPerformed 方法：

```java
public void actionPerformed(ActionEvent e) {
    try {
        undo.redo();
    } catch (CannotRedoException ex) {
        System.out.println("Unable to redo: " + ex);
        ex.printStackTrace();
    }
    updateRedoState();
    undoAction.updateUndoState();
}
```

该方法与撤销类似，只是调用撤销管理器的 redo 方法。

UndoAction 和 RedoAction 类中的大部分代码都用于根据当前状态启用和禁用操作，并更改菜单项的名称以反映要撤销或重做的编辑。

注意：
TextComponentDemo 示例中撤销和重做的实现来自 JDK 软件附带的 Notepad 演示。许多程序员也可以在不修改的情况下复制此撤销/重做的实现。


