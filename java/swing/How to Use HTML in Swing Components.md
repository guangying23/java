## How to Use HTML in Swing Components
许多Swing组件在其GUI中显示一个文本字符串。默认情况下，组件的文本以单一字体和颜色显示在一行上。可以分别通过调用组件的setFont和setForeground方法确定组件文本的字体和颜色。例如，以下代码创建了一个标签，然后设置其字体和颜色：

```java
label = new JLabel("A label");
label.setFont(new Font("Serif", Font.PLAIN, 14));
label.setForeground(new Color(0xffffdd));
```

如果想在文本中混合使用字体或颜色，或者想要多行等格式，可以使用HTML。HTML格式可以在所有Swing按钮、菜单项、标签、工具提示和选项卡面板中使用，也可以在使用标签渲染文本的组件（如树和表格）中使用。

要指定组件的文本具有HTML格式，只需在文本开头添加<html>标签，然后在其余部分使用任何有效的HTML。以下是在按钮文本中使用HTML的示例：

```java
button = new JButton("<html><b><u>T</u>wo</b><br>lines</html>");
```

这是生成的按钮。![image](https://github.com/guangying23/java/assets/54796147/9da24990-089b-4e4a-a5c7-fbf74f4cd0a7)

## An Example: HtmlDemo
一个名为HtmlDemo的应用程序允许您通过设置标签上的文本来使用HTML格式。可以在HtmlDemo.java中找到该程序的完整代码。以下是HtmlDemo示例的图片。
![image](https://github.com/guangying23/java/assets/54796147/6d999b57-724d-4283-938e-65988e43a659)

## Example 2: ButtonHtmlDemo
让我们看另一个使用HTML的示例。ButtonHtmlDemo为三个按钮添加了字体、颜色和其他文本格式。可以在ButtonHtmlDemo.java中找到该程序的完整代码。以下是ButtonHtmlDemo示例的图片。
![image](https://github.com/guangying23/java/assets/54796147/118cef18-3641-46bb-9453-9c560ae77a95)

左右按钮有多行和文本样式，使用HTML实现。中间按钮只使用了一行、字体和颜色，因此不需要HTML。以下是指定这三个按钮文本格式的代码：

```java
b1 = new JButton("<html><center><b><u>D</u>isable</b><br>"
                 + "<font color=#ffffdd>middle button</font>",
                 leftButtonIcon);
Font font = b1.getFont().deriveFont(Font.PLAIN);
b1.setFont(font);
...
b2 = new JButton("middle button", middleButtonIcon);
b2.setFont(font);
b2.setForeground(new Color(0xffffdd));
...
b3 = new JButton("<html><center><b><u>E</u>nable</b><br>"
                 + "<font color=#ffffdd>middle button</font>",
                 rightButtonIcon);
b3.setFont(font);
```

请注意，我们必须使用<u>标签来使使用HTML的按钮中的助记符字符“D”和“E”带下划线。还要注意，当按钮被禁用时，其HTML文本不幸仍然是黑色，而不是变成灰色。（请参阅错误#4783068以查看此情况是否有所变化。）

本节讨论了如何在普通的非文本组件中使用HTML。有关主要目的是格式化文本的组件的信息，请参见使用文本组件。

如果您正在使用JavaFX进行编程，请参阅HTML编辑器。
