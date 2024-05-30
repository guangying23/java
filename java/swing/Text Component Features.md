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

