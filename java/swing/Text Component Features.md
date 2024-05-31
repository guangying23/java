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


## 四、概念：关于文档
像其他 Swing 组件一样，文本组件将其数据（称为模型）与其数据视图分开。如果您还不熟悉 Swing 组件使用的模型-视图分离，请参阅《使用模型》。

文本组件的模型称为文档，是实现 Document 接口的类的实例。文档为文本组件提供以下服务：

- 包含文本。文档将文本内容存储在 Element 对象中，Element 对象可以表示任何逻辑文本结构，例如段落或共享样式的文本块。这里我们不描述 Element 对象。
- 通过 remove 和 insertString 方法提供对文本编辑的支持。
- 通知文档监听器和可撤销编辑监听器文本的更改。
- 管理 Position 对象，这些对象即使在文本被修改时也会跟踪文本中的特定位置。
- 允许您获取有关文本的信息，例如其长度，以及文本段落作为字符串的内容。

Swing 文本包包含 Document 的子接口 StyledDocument，它增加了对使用样式标记文本的支持。一个 JTextComponent 的子类 JTextPane 要求其文档必须是 StyledDocument 而不仅仅是 Document。

javax.swing.text 包提供了以下文档类的层次结构，这些类实现了各种 JTextComponent 子类的专门文档：  
![image](https://github.com/guangying23/java/assets/54796147/1c4454e1-2688-4aa2-adb8-72568739af4d)

PlainDocument 是文本字段、密码字段和文本区域的默认文档。PlainDocument 提供了一个基本的文本容器，所有文本都以相同的字体显示。即使编辑器窗格是一个带样式的文本组件，它默认使用 PlainDocument 实例。标准 JTextPane 的默认文档是 DefaultStyledDocument 的实例——一个不特定格式的带样式文本容器。然而，任何特定编辑器窗格或文本窗格使用的文档实例取决于绑定的内容类型。如果使用 setPage 方法将文本加载到编辑器窗格或文本窗格中，窗格使用的文档实例可能会更改。详情请参阅《如何使用编辑器窗格和文本窗格》。

尽管您可以设置文本组件的文档，但通常更容易允许其自动设置，并在必要时使用文档过滤器更改文本组件数据的设置方式。您可以通过安装文档过滤器或替换文本组件的文档为自定义文档来实现某些定制。例如，TextComponentDemo 示例中的文本窗格具有一个文档过滤器，该过滤器限制文本窗格可以包含的字符数。

## 五、实现文档过滤器
要实现文档过滤器，创建一个 DocumentFilter 的子类，然后使用 AbstractDocument 类中定义的 setDocumentFilter 方法将其附加到文档上。尽管可能存在不从 AbstractDocument 派生的文档，但默认情况下，Swing 文本组件使用 AbstractDocument 的子类作为其文档。

TextComponentDemo 应用程序有一个文档过滤器 DocumentSizeFilter，它限制文本窗格可以包含的字符数。以下是创建过滤器并将其附加到文本窗格文档的代码：

```java
// 在成员变量声明处:
JTextPane textPane;
AbstractDocument doc;
static final int MAX_CHARACTERS = 300;
...
textPane = new JTextPane();
...
StyledDocument styledDoc = textPane.getStyledDocument();
if (styledDoc instanceof AbstractDocument) {
    doc = (AbstractDocument) styledDoc;
    doc.setDocumentFilter(new DocumentSizeFilter(MAX_CHARACTERS));
}
```

为了限制文档中允许的字符数，DocumentSizeFilter 重写了 DocumentFilter 类的 insertString 方法，该方法在每次将文本插入到文档时调用。它还重写了 replace 方法，该方法最有可能在用户粘贴新文本时调用。一般来说，文本插入可以在用户键入或粘贴新文本时发生，或者在调用 setText 方法时发生。以下是 DocumentSizeFilter 类的 insertString 方法的实现：

```java
public void insertString(FilterBypass fb, int offs,
                         String str, AttributeSet a)
    throws BadLocationException {

    if ((fb.getDocument().getLength() + str.length()) <= maxCharacters)
        super.insertString(fb, offs, str, a);
    else
        Toolkit.getDefaultToolkit().beep();
}
```

replace 方法的代码类似。DocumentFilter 类定义的方法中的 FilterBypass 参数只是一个对象，它使文档能够以线程安全的方式更新。

因为上述文档过滤器关注的是文档数据的添加，所以它只重写了 insertString 和 replace 方法。大多数文档过滤器还会重写 DocumentFilter 的 remove 方法。

## 六、监听文档的更改
您可以在文档上注册两种不同类型的监听器：文档监听器和可撤销编辑监听器。本小节描述了文档监听器。有关可撤销编辑监听器的信息，请参阅实现撤销和重做。

文档通知已注册的文档监听器文档的更改。当文本插入或从文档中删除时，或当文本样式发生变化时，使用文档监听器创建反应。

TextComponentDemo 程序使用文档监听器在文本窗格更改时更新更改日志。以下代码行将 MyDocumentListener 类的一个实例注册为文本窗格文档的监听器：

```java
doc.addDocumentListener(new MyDocumentListener());
```

以下是 MyDocumentListener 类的实现：

```java
protected class MyDocumentListener implements DocumentListener {
    public void insertUpdate(DocumentEvent e) {
        displayEditInfo(e);
    }
    public void removeUpdate(DocumentEvent e) {
        displayEditInfo(e);
    }
    public void changedUpdate(DocumentEvent e) {
        displayEditInfo(e);
    }
    private void displayEditInfo(DocumentEvent e) {
        Document document = (Document) e.getDocument();
        int changeLength = e.getLength();
        changeLog.append(e.getType().toString() + ": "
            + changeLength + " character"
            + ((changeLength == 1) ? ". " : "s. ")
            + " Text length = " + document.getLength()
            + "." + newline);
    }
}
```

该监听器实现了三个方法来处理三种不同类型的文档事件：插入、删除和样式更改。StyledDocument 实例可以触发所有三种类型的事件。PlainDocument 实例仅触发插入和删除事件。有关文档监听器和文档事件的一般信息，请参阅《如何编写文档监听器》。

请记住，此文本窗格的文档过滤器限制了文档中允许的字符数。如果您尝试添加超过文档过滤器允许的文本，文档过滤器将阻止更改，并且监听器的 insertUpdate 方法不会被调用。只有当更改已经发生时，文档监听器才会被通知。

您可能想在文档监听器中更改文档的文本。然而，您不应该从文档监听器内部修改文本组件的内容。如果这样做，程序可能会死锁。相反，您可以使用格式化文本字段或提供文档过滤器。

## 七、监听插入符号和选择更改
TextComponentDemo 程序使用插入符号监听器显示插入符号的当前位置，或者在选中文本时显示选择范围。

此示例中的插入符号监听器类是 JLabel 的子类。以下代码创建插入符号监听器标签并将其设置为文本窗格的插入符号监听器：

```java
// 创建状态区域
CaretListenerLabel caretListenerLabel = new CaretListenerLabel("Caret Status");
...
textPane.addCaretListener(caretListenerLabel);
```

插入符号监听器必须实现一个方法 caretUpdate，该方法在每次插入符号移动或选择更改时调用。以下是 CaretListenerLabel 的 caretUpdate 实现：

```java
public void caretUpdate(CaretEvent e) {
    // 获取文本中的位置
    int dot = e.getDot();
    int mark = e.getMark();
    if (dot == mark) {  // 没有选择
        try {
            Rectangle caretCoords = textPane.modelToView(dot);
            // 将其转换为视图坐标
            setText("caret: text position: " + dot +
                    ", view location = [" +
                    caretCoords.x + ", " + caretCoords.y + "]" +
                    newline);
        } catch (BadLocationException ble) {
            setText("caret: text position: " + dot + newline);
        }
     } else if (dot < mark) {
        setText("selection from: " + dot + " to " + mark + newline);
     } else {
        setText("selection from: " + mark + " to " + dot + newline);
     }
}
```

如您所见，此监听器更新其文本标签以反映插入符号或选择的当前状态。监听器从插入符号事件对象中获取要显示的信息。有关插入符号监听器和插入符号事件的一般信息，请参阅《如何编写插入符号监听器》。

与文档监听器一样，插入符号监听器是被动的。它对插入符号或选择的更改做出反应，但不会更改插入符号或选择本身。如果您想更改插入符号或选择，请使用导航过滤器或自定义插入符号。

实现导航过滤器类似于实现文档过滤器。首先，编写 NavigationFilter 的子类。然后使用 setNavigationFilter 方法将子类的实例附加到文本组件上。

您可能会创建自定义插入符号来自定义插入符号的外观。要创建自定义插入符号，请编写一个实现 Caret 接口的类——也许通过扩展 DefaultCaret 类。然后在文本组件上调用 setCaret 方法时提供该类的实例作为参数。

## 八、概念：关于编辑器工具包

文本组件使用 EditorKit 将文本组件的各个部分联系在一起。编辑器工具包提供视图工厂、文档、插入符号和操作。编辑器工具包还可以读取和写入特定格式的文档。尽管所有文本组件都使用编辑器工具包，但某些组件隐藏了它们。您无法设置或获取文本字段或文本区域使用的编辑器工具包。编辑器窗格和文本窗格提供 getEditorKit 方法来获取当前的编辑器工具包，并提供 setEditorKit 方法来更改它。

对于所有组件，JTextComponent 类提供了 API，您可以间接调用或自定义某些编辑器工具包功能。例如，JTextComponent 提供 read 和 write 方法，这些方法调用编辑器工具包的 read 和 write 方法。JTextComponent 还提供一个方法 getActions，返回组件支持的所有操作。

Swing 文本包提供以下编辑器工具包：

- DefaultEditorKit：读取和写入纯文本，并提供基本的编辑命令集。关于文本系统如何处理换行符的详细信息可以在 DefaultEditorKit API 文档中找到。简而言之，内部使用 '\n' 字符，但在写入文件时使用文档或平台的行分隔符。所有其他编辑器工具包都是 DefaultEditorKit 类的子类。
- StyledEditorKit：读取和写入样式文本，并为样式文本提供最少的操作集。此类是 DefaultEditorKit 的子类，默认情况下由 JTextPane 使用。
- HTMLEditorKit：读取、写入和编辑 HTML。此类是 StyledEditorKit 的子类。

上面列出的每个编辑器工具包都已在 JEditorPane 类中注册，并与工具包读取、写入和编辑的文本格式相关联。当文件加载到编辑器窗格时，窗格会根据其注册的工具包检查文件的格式。如果找到支持该文件格式的注册工具包，窗格将使用该工具包读取文件、显示并编辑文件。因此，编辑器窗格有效地将自身转换为该文本格式的编辑器。您可以通过为自己的文本格式创建编辑器工具包，然后使用 JEditorPane 的 registerEditorKitForContentType 方法将您的工具包与您的文本格式相关联，从而扩展 JEditorPane 以支持自己的文本格式。
