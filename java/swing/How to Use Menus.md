## How to Use Menus
菜单提供了一种节省空间的方法，让用户在多个选项中进行选择。用户可以通过其他组件（如组合框、列表、单选按钮、微调器和工具栏）进行多项选择。如果您的任何菜单项执行的操作被其他菜单项或工具栏按钮重复，那么除了本节内容之外，您还应该阅读如何使用操作。

菜单的独特之处在于，按照惯例，它们不会与UI中的其他组件放在一起。相反，菜单通常出现在菜单栏中或作为弹出菜单。菜单栏包含一个或多个菜单，并具有习惯性的、与平台相关的位置——通常在窗口的顶部。弹出菜单是在用户对启用弹出的组件执行特定平台的鼠标操作（如按下右键）时才会出现的菜单。然后，弹出菜单会出现在光标下方。

下图显示了许多与菜单相关的组件：菜单栏、菜单、菜单项、单选按钮菜单项、复选框菜单项和分隔符。如您所见，菜单项可以有图像、文本或两者兼有。您还可以指定其他属性，如字体和颜色。  
![image](https://github.com/guangying23/java/assets/54796147/b9c13430-4111-460f-9a29-b74bf433110c)  

本节的其余部分将介绍菜单组件，并教您如何使用各种菜单功能：

- 菜单组件层次结构
- 创建菜单
- 处理菜单项的事件
- 启用键盘操作
- 弹出菜单
- 自定义菜单布局
- 菜单API
- 使用菜单的示例

## The Menu Component Hierarchy
Here is a picture of the inheritance hierarchy for the menu-related classes:
![image](https://github.com/guangying23/java/assets/54796147/cc62ba39-665a-4053-ae38-af77e50c97e4)

如图所示，菜单项（包括菜单）实际上只是按钮。您可能会疑惑，如果菜单只是一个按钮，它是如何显示其菜单项的。答案是，当菜单被激活时，它会自动弹出一个菜单，显示菜单项。

## Creating Menus
以下代码创建了本节开头显示的菜单。加粗的代码行创建并连接菜单对象；其他代码设置或自定义菜单对象。您可以在MenuLookDemo.java中找到整个程序。其他所需文件列在示例索引中。

由于这段代码没有事件处理，因此这些菜单除了显示外没有其他功能。如果运行示例，您会注意到，尽管缺少自定义事件处理，菜单和子菜单在适当的时候仍会出现，并且当用户选择它们时，复选框和单选按钮会适当地响应。

```java
//Where the GUI is created:
JMenuBar menuBar;
JMenu menu, submenu;
JMenuItem menuItem;
JRadioButtonMenuItem rbMenuItem;
JCheckBoxMenuItem cbMenuItem;

//Create the menu bar.
menuBar = new JMenuBar();

//Build the first menu.
menu = new JMenu("A Menu");
menu.setMnemonic(KeyEvent.VK_A);
menu.getAccessibleContext().setAccessibleDescription(
        "The only menu in this program that has menu items");
menuBar.add(menu);

//a group of JMenuItems
menuItem = new JMenuItem("A text-only menu item",
                         KeyEvent.VK_T);
menuItem.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_1, ActionEvent.ALT_MASK));
menuItem.getAccessibleContext().setAccessibleDescription(
        "This doesn't really do anything");
menu.add(menuItem);

menuItem = new JMenuItem("Both text and icon",
                         new ImageIcon("images/middle.gif"));
menuItem.setMnemonic(KeyEvent.VK_B);
menu.add(menuItem);

menuItem = new JMenuItem(new ImageIcon("images/middle.gif"));
menuItem.setMnemonic(KeyEvent.VK_D);
menu.add(menuItem);

//a group of radio button menu items
menu.addSeparator();
ButtonGroup group = new ButtonGroup();
rbMenuItem = new JRadioButtonMenuItem("A radio button menu item");
rbMenuItem.setSelected(true);
rbMenuItem.setMnemonic(KeyEvent.VK_R);
group.add(rbMenuItem);
menu.add(rbMenuItem);

rbMenuItem = new JRadioButtonMenuItem("Another one");
rbMenuItem.setMnemonic(KeyEvent.VK_O);
group.add(rbMenuItem);
menu.add(rbMenuItem);

//a group of check box menu items
menu.addSeparator();
cbMenuItem = new JCheckBoxMenuItem("A check box menu item");
cbMenuItem.setMnemonic(KeyEvent.VK_C);
menu.add(cbMenuItem);

cbMenuItem = new JCheckBoxMenuItem("Another one");
cbMenuItem.setMnemonic(KeyEvent.VK_H);
menu.add(cbMenuItem);

//a submenu
menu.addSeparator();
submenu = new JMenu("A submenu");
submenu.setMnemonic(KeyEvent.VK_S);

menuItem = new JMenuItem("An item in the submenu");
menuItem.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_2, ActionEvent.ALT_MASK));
submenu.add(menuItem);

menuItem = new JMenuItem("Another item");
submenu.add(menuItem);
menu.add(submenu);

//Build second menu in the menu bar.
menu = new JMenu("Another Menu");
menu.setMnemonic(KeyEvent.VK_N);
menu.getAccessibleContext().setAccessibleDescription(
        "This menu does nothing");
menuBar.add(menu);

...
frame.setJMenuBar(theJMenuBar);
```

如代码所示，要为JFrame设置菜单栏，可以使用setJMenuBar方法。要将JMenu添加到JMenuBar，可以使用add(JMenu)方法。要将菜单项和子菜单添加到JMenu，可以使用add(JMenuItem)方法。

注意：
菜单项和其他组件一样，最多只能在一个容器中。如果尝试将一个菜单项添加到第二个菜单，该菜单项将在添加到第二个菜单之前从第一个菜单中移除。有关实现多个执行相同操作的组件的方法，请参见如何使用操作。

前面的代码中还包括setAccelerator和setMnemonic方法，这些方法将在启用键盘操作部分稍后讨论。setAccessibleDescription方法在如何支持辅助技术中讨论。

## Handling Events from Menu Items
要检测用户何时选择JMenuItem，可以监听动作事件（就像监听JButton一样）。要检测用户何时选择JRadioButtonMenuItem，可以监听动作事件或项事件，如如何使用单选按钮中所述。对于JCheckBoxMenuItems，通常监听项事件，如如何使用复选框中所述。

![image](https://github.com/guangying23/java/assets/54796147/1679226f-021e-4e4f-a245-df7dcd91c81d)

```java
public class MenuDemo ... implements ActionListener,
                                     ItemListener {
    ...
    public MenuDemo() {
        //...for each JMenuItem instance:
        menuItem.addActionListener(this);
        ...
        //for each JRadioButtonMenuItem: 
        rbMenuItem.addActionListener(this);
        ...
        //for each JCheckBoxMenuItem: 
        cbMenuItem.addItemListener(this);
        ...
    }

    public void actionPerformed(ActionEvent e) {
        //...Get information from the action event...
        //...Display it in the text area...
    }

    public void itemStateChanged(ItemEvent e) {
        //...Get information from the item event...
        //...Display it in the text area...
    }
```

有关处理动作事件和项事件的示例，请参见按钮、单选按钮和复选框部分，以及本节末尾的示例列表。

## Enabling Keyboard Operation
菜单支持两种键盘替代方法：助记符和快捷键。助记符提供了一种使用键盘导航菜单层次结构的方法，从而提高程序的可访问性。快捷键则提供了绕过菜单层次结构的键盘快捷方式。助记符适用于所有用户；快捷键适用于高级用户。

助记符是使已经可见的菜单项被选中的按键。例如，在MenuDemo中，第一个菜单的助记符是A，第二个菜单项的助记符是B。这意味着，当以Java外观和感觉运行MenuDemo时，按下Alt和A键会使第一个菜单出现。当第一个菜单可见时，按下B键（无论是否按下Alt）会选择第二个菜单项。菜单项通常通过在菜单项文本中助记符字符的第一次出现下划线来显示其助记符，如以下快照所示。

![image](https://github.com/guangying23/java/assets/54796147/ffc74c5d-512d-4ad2-968b-5dc637ad2a98)

快捷键是一个键盘组合，可以使菜单项被选中，无论它是否可见。例如，在MenuDemo中，按下Alt和2键会使第一个菜单的子菜单中的第一项被选中，而不会弹出任何菜单。只有叶子菜单项——不会弹出其他菜单的菜单——可以有快捷键。以下快照展示了Java外观和感觉如何显示具有快捷键的菜单项。

![image](https://github.com/guangying23/java/assets/54796147/61ce796d-f820-40f9-84af-59ecbcbcf9c3)

您可以在构建菜单项时或使用 setMnemonic 方法来指定助记符。要指定快捷键，请使用 setAccelerator 方法。以下是设置助记符和快捷键的示例：

//在构建菜单项时设置助记符：
menuItem = new JMenuItem("仅文本菜单项",
                         KeyEvent.VK_T);

//创建后设置助记符：
menuItem.setMnemonic(KeyEvent.VK_T);

//设置快捷键：
menuItem.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_T, ActionEvent.ALT_MASK));
如您所见，设置助记符需要指定与用户应按下的键对应的 KeyEvent 常量。要指定快捷键，您必须使用 KeyStroke 对象，该对象结合了一个键（由 KeyEvent 常量指定）和一个修饰键掩码（由 ActionEvent 常量指定）。

注意：
因为弹出菜单与常规菜单不同，并不总是由组件包含，除非弹出菜单可见，否则菜单项中的快捷键无效。

## Bringing Up a Popup Menu  弹出菜单的显示
要显示弹出菜单（JPopupMenu），您必须在每个应与弹出菜单关联的组件上注册一个鼠标监听器。鼠标监听器必须检测到用户请求显示弹出菜单的行为。

触发弹出菜单的具体手势因外观和感觉而异。在 Microsoft Windows 中，按照惯例，用户通过在启用弹出的组件上释放右键来触发弹出菜单。在 Java 的外观和感觉中，常见的触发器是按下右键（对于释放按钮时会消失的弹出菜单）或点击它（对于点击后仍然显示的弹出菜单）。


//...在声明实例变量的地方：
JPopupMenu popup;

    //...在构建 GUI 的地方：
    //创建弹出菜单。
    popup = new JPopupMenu();
    JMenuItem menuItem = new JMenuItem("一个弹出菜单项");
    menuItem.addActionListener(this);
    popup.add(menuItem);
    menuItem = new JMenuItem("另一个弹出菜单项");
    menuItem.addActionListener(this);
    popup.add(menuItem);

    //为可能显示弹出菜单的组件添加监听器。
    MouseListener popupListener = new PopupListener();
    output.addMouseListener(popupListener);
    menuBar.addMouseListener(popupListener);
...
class PopupListener extends MouseAdapter {
    public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
    }

    public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
    }

    private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popup.show(e.getComponent(),
                       e.getX(), e.getY());
        }
    }
}
弹出菜单有一些有趣的实现细节。其中一个是每个菜单都有一个关联的弹出菜单。当菜单被激活时，它使用其关联的弹出菜单显示其菜单项。

另一个细节是，弹出菜单本身使用另一个组件来实现包含菜单项的窗口。根据显示弹出菜单的情况，弹出菜单可能使用轻量级组件（如 JPanel）、"中等重量"组件（如 Panel）或重量级窗口（继承自 Window 的某种东西）来实现其“窗口”。

轻量级弹出窗口比重量级窗口更高效，但在 Java SE 平台 6 更新 12 版本之前，如果 GUI 中含有任何重量级组件，它们的功能不佳。具体来说，当轻量级弹出的显示区域与重量级组件的显示区域交叉时，重量级组件会被绘制在顶部。这是我们在 6u12 版本之前不建议混合使用重量级和轻量级组件的原因之一。如果您使用的是较旧的版本，并且绝对需要在 GUI 中使用重量级组件，则可以调用 JPopupMenu.setLightWeightPopupEnabled(false) 来禁用轻量级弹出窗口。有关在 6u12 版本及以后混合组件的信息，请参阅混合重量级和轻量级组件。

## Customizing Menu Layout
由于菜单由普通的 Swing 组件组成，您可以轻松地自定义它们。例如，您可以向 JMenu 或 JMenuBar 添加任何轻量级组件。而且因为 JMenuBar 使用 BoxLayout，您可以通过向菜单栏添加不可见组件来自定义菜单栏的布局。以下是向菜单栏添加粘合组件的示例，使得最后一个菜单位于菜单栏的右边缘：

//...创建并添加一些菜单...
menuBar.add(Box.createHorizontalGlue());
//...创建最右边的菜单...
menuBar.add(rightMenu);

Here's the modified menu layout that MenuGlueDemo displays:  
![image](https://github.com/guangying23/java/assets/54796147/e71c96f9-3563-4789-ba4f-6c341a79cdef)

另一种改变菜单外观的方法是更改用于控制它们的布局管理器。例如，您可以将菜单栏的布局管理器从默认的从左到右的 BoxLayout 更改为如 GridLayout 等其他布局。这种更改允许菜单项在菜单栏中均匀分布，无论它们的宽度如何，都能在视觉上提供一种更加统一和对称的外观。

Here's a picture of the menu layout that MenuLayoutDemo creates:

![image](https://github.com/guangying23/java/assets/54796147/173bb9d7-aaee-4e24-9021-d4389ed947ad)

## The Menu API
以下表格列出了常用的菜单构造函数和方法。使用菜单的 API 分为以下几类：
- 创建和设置菜单栏
- 创建和填充菜单
- 创建、填充和控制弹出菜单
- 实现菜单项

| 构造函数或方法 | 目的 |
| --- | --- |
| JMenuBar() | 创建一个菜单栏。 |
| JMenu add(JMenu) | 将菜单添加到菜单栏的末尾。 |
| void setJMenuBar(JMenuBar) | 设置小程序、对话框、框架、内部框架或根窗格的菜单栏。 |
| JMenuBar getJMenuBar() | 获取小程序、对话框、框架、内部框架或根窗格的菜单栏。 |

| 构造函数或方法 | 目的 |
| --- | --- |
| JMenu() | 创建一个菜单。 |
| JMenu(String) | 创建一个菜单，指定要显示的文本。 |
| JMenu(Action) | 创建一个菜单，指定 Action 对象，该对象包含菜单的文本和其他属性（参见如何使用 Action）。 |
| JMenuItem add(JMenuItem) | 将菜单项添加到菜单的当前末尾。 |
| JMenuItem add(String) | 将菜单项添加到菜单的当前末尾。如果参数是字符串，则菜单会自动创建一个显示指定文本的 JMenuItem 对象。 |
| void addSeparator() | 在菜单的当前末尾添加一个分隔符。 |
| JMenuItem insert(JMenuItem, int) | 在指定位置插入一个菜单项。 |
| void insert(String, int) | 在指定位置插入一个显示指定文本的菜单项。 |
| void insertSeparator(int) | 在菜单的指定位置插入一个分隔符。第一个菜单项在位置 0，第二个在位置 1，依此类推。JMenuItem 和 String 参数的处理方式与相应的 add 方法相同。 |
| void remove(JMenuItem) | 移除菜单中指定的菜单项。 |
| void remove(int) | 移除菜单中指定位置的菜单项。 |
| void removeAll() | 移除菜单中的所有菜单项。 |

| 构造函数或方法 | 目的 |
| --- | --- |
| JPopupMenu() | 创建一个弹出菜单。 |
| JPopupMenu(String) | 创建一个弹出菜单。可选的字符串参数指定可能显示为弹出窗口一部分的标题。 |
| JMenuItem add(JMenuItem) | 将菜单项添加到弹出菜单的当前末尾。 |
| JMenuItem add(String) | 将菜单项添加到弹出菜单的当前末尾。如果参数是字符串，则菜单会自动创建一个显示指定文本的 JMenuItem 对象。 |
| void addSeparator() | 在弹出菜单的当前末尾添加一个分隔符。 |
| void insert(Component, int) | 在指定位置插入一个菜单项。第一个菜单项在位置 0，第二个在位置 1，依此类推。Component 参数指定要添加的菜单项。 |
| void remove(int) | 移除菜单中指定位置的菜单项。 |
| void removeAll() | 移除菜单中的所有菜单项。 |
| static void setLightWeightPopupEnabled(boolean) | 默认情况下，Swing 使用轻量级组件实现菜单的窗口。如果在 Swing 程序中使用任何重量级组件，这可能会导致问题。作为一种解决方法，调用 JPopupMenu.setLightWeightPopupEnabled(false)。 |
| void show(Component, int, int) | 在指定组件的坐标系统中，在指定的 x,y 位置（按此顺序由整数参数指定）显示弹出菜单。 |

| 构造函数或方法 | 目的 |
| --- | --- |
| JMenuItem() | 创建一个普通菜单项。 |
| JMenuItem(String) | 创建一个普通菜单项，并指定显示的文本。 |
| JMenuItem(Icon) | 创建一个普通菜单项，并指定显示的图标。 |
| JMenuItem(String, Icon) | 创建一个普通菜单项，并指定显示的文本和图标。 |
| JMenuItem(String, int) | 创建一个普通菜单项，并指定显示的文本和键盘助记符。键盘助记符使用 KeyEvent 类定义的 VK 常量。例如，要指定 A 键，使用 KeyEvent.VK_A。 |
| JMenuItem(Action) | 创建一个普通菜单项，并设置菜单项的 Action，导致菜单项的属性从 Action 中初始化。有关详细信息，请参见如何使用 Action。 |
| JCheckBoxMenuItem() | 创建一个看起来和行为像复选框的菜单项。 |
| JCheckBoxMenuItem(String) | 创建一个看起来和行为像复选框的菜单项，并指定显示的文本。 |
| JCheckBoxMenuItem(Icon) | 创建一个看起来和行为像复选框的菜单项，并指定显示的图标。 |
| JCheckBoxMenuItem(String, Icon) | 创建一个看起来和行为像复选框的菜单项，并指定显示的文本和图标。 |
| JCheckBoxMenuItem(String, boolean) | 创建一个看起来和行为像复选框的菜单项，并指定显示的文本。如果 boolean 参数为 true，则菜单项初始选中（勾选）。否则，菜单项初始未选中。 |
| JCheckBoxMenuItem(String, Icon, boolean) | 创建一个看起来和行为像复选框的菜单项，并指定显示的文本、图标和初始选择状态。 |
| JRadioButtonMenuItem() | 创建一个看起来和行为像单选按钮的菜单项。 |
| JRadioButtonMenuItem(String) | 创建一个看起来和行为像单选按钮的菜单项，并指定显示的文本。 |
| JRadioButtonMenuItem(Icon) | 创建一个看起来和行为像单选按钮的菜单项，并指定显示的图标。 |
| JRadioButtonMenuItem(String, Icon) | 创建一个看起来和行为像单选按钮的菜单项，并指定显示的文本和图标。 |
| JRadioButtonMenuItem(String, boolean) | 创建一个看起来和行为像单选按钮的菜单项，并指定显示的文本。如果 boolean 参数为 true，则菜单项初始选中。否则，菜单项初始未选中。 |
| JRadioButtonMenuItem(Icon, boolean) | 创建一个看起来和行为像单选按钮的菜单项，并指定显示的图标和初始选择状态。 |
| JRadioButtonMenuItem(String, Icon, boolean) | 创建一个看起来和行为像单选按钮的菜单项，并指定显示的文本、图标和初始选择状态。 |
| void setState(boolean) | 设置或获取复选框菜单项的选择状态（在 JCheckBoxMenuItem 中）。 |
| boolean getState() | 获取复选框菜单项的选择状态（在 JCheckBoxMenuItem 中）。 |
| void setEnabled(boolean) | 如果参数为 true，启用菜单项。否则，禁用菜单项。 |
| void setMnemonic(int) | 设置启用通过键盘导航到菜单或菜单项的助记符。使用 KeyEvent 类定义的 VK 常量之一。 |
| void setAccelerator(KeyStroke) | 设置激活菜单项的加速器。 |
| void setActionCommand(String) | 设置菜单项执行的操作名称。 |
| void addActionListener(ActionListener) | 向菜单项添加事件监听器。有关详细信息，请参见处理菜单项的事件。 |
| void addItemListener(ItemListener) | 向菜单项添加事件监听器。 |
| void setAction(Action) | 设置与菜单项关联的 Action。有关详细信息，请参见如何使用 Action。 |
| Many of the preceding methods are inherited from AbstractButton. See The Button API for information about other useful methods that AbstractButton provides. | 许多前述的方法都是从 AbstractButton 继承的。有关 AbstractButton 提供的其他有用方法的信息，请参见按钮 API。 |
