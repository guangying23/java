Swing 提供了丰富的 GUI 组件，用于构建图形用户界面。以下是一些常见的 Swing 组件：

1. JButton:
   - 按钮，用于触发动作或事件。
  
```
import javax.swing.JButton;
import javax.swing.JFrame;

public class Jbutton01 {
    public static void main(String[] args){
        JFrame frame=new JFrame("Jbutton");
        frame.setBounds(400,300,400,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton button=new JButton("button");
        frame.add(button);

        frame.setVisible(true);
    }
}
```

2. JTextField:
   - 文本框，用于接收用户输入的文本。

3. **JLabel:**
   - 标签，用于显示文本或图像。

4. **JComboBox:**
   - 下拉框，允许用户从预定义的选项中选择一个。

5. **JCheckBox:**
   - 复选框，允许用户选择或取消选择一个或多个选项。

6. **JRadioButton:**
   - 单选按钮，允许用户从一组选项中选择一个。

7. **JTextArea:**
   - 文本区域，用于显示多行文本。

8. **JList:**
   - 列表，显示一个单列的项目列表。

9. **JTable:**
   - 表格，用于显示和编辑二维数据。

10. **JScrollPane:**
    - 滚动窗格，用于处理大量数据的显示，提供滚动功能。

11. **JPanel:**
    - 面板，用于组织和容纳其他组件。

12. **JFrame:**
    - 顶层窗口，表示应用程序的主窗口。

13. **JDialog:**
    - 对话框，表示一个弹出式窗口，通常用于显示消息或接收用户输入。

14. **JSplitPane:**
    - 分隔窗格，可以将容器分为两个可调整大小的区域。

15. **JProgressBar:**
    - 进度条，用于显示任务完成的百分比。

16. **JSlider:**
    - 滑块，允许用户通过拖动选择一个范围内的值。

这只是一小部分可用组件的列表，Swing 还提供了许多其他组件和功能，以满足各种 GUI 设计需求。通过组合这些组件，开发人员可以创建丰富而灵活的用户界面。
