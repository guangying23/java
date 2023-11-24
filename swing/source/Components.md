Swing 提供了丰富的 GUI 组件，用于构建图形用户界面。以下是一些常见的 Swing 组件：

1. **JButton:**
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

2. **JTextField:**
   - 文本框，用于接收用户输入的文本。
```
...
JTextField textField=new JTextField("hello world");
frame.add(textField);
...
```

3. **JLabel:**
   - 标签，用于显示文本或图像。

4. **JComboBox:**
   - 下拉框，允许用户从预定义的选项中选择一个。
```
        String[] options = {"Option 1", "Option 2", "Option 3", "Option 4"};
        JComboBox<String> comboBox = new JComboBox<>(options);
```

5. **JCheckBox:**
   - 复选框，允许用户选择或取消选择一个或多个选项。
```
JCheckBox checkBox = new JCheckBox("Enable Feature");
```

6. **JRadioButton:**
   - 单选按钮，允许用户从一组选项中选择一个。
```
JRadioButton radioButton = new JRadioButton("Enable Feature");
```

7. **JTextArea:**
   - 文本区域，用于显示多行文本。
```
JTextArea textArea = new JTextArea("Enable Feature");
```

8. **JList:**
   - 列表，显示一个单列的项目列表。
```
// 创建一个 DefaultListModel 作为 JList 的数据模型
DefaultListModel<String> listModel = new DefaultListModel<>();

// 向数据模型中添加数据
listModel.addElement("Item 1");
listModel.addElement("Item 2");
listModel.addElement("Item 3");

// 使用数据模型创建 JList
JList<String> myList = new JList<>(listModel);
```

9. **JTable:**
   - 表格，用于显示和编辑二维数据。
```
// 创建表头
String[] columnNames = {"Name", "Age", "Gender"};

// 创建数据模型
Object[][] data = {
       {"John", 25, "Male"},
       {"Jane", 30, "Female"},
       {"Bob", 22, "Male"}
};

// 使用数据模型创建 JTable
JTable myTable = new JTable(new DefaultTableModel(data, columnNames));
```

10. **JScrollPane:**
    - 滚动窗格，用于处理大量数据的显示，提供滚动功能。
```
// 将 JTable 放入 JScrollPane 中以支持滚动
JScrollPane scrollPane = new JScrollPane(myTable);

// 将 JScrollPane 添加到 JFrame
frame.add(scrollPane);
```

11. **JPanel:**
    - 面板，用于组织和容纳其他组件。
```
// 创建一个 JPanel
        JPanel panel = new JPanel();

        // 添加按钮到 JPanel
        JButton button1 = new JButton("Button 1");
        JButton button2 = new JButton("Button 2");
        panel.add(button1);
        panel.add(button2);

        // 将 JPanel 添加到 JFrame
        frame.add(panel);
```
**设置布局管理器**
默认情况下，JPanel使用FlowLayout布局管理器，但你可以根据需要设置其他布局管理器，例如BorderLayout、GridLayout等。
```
panel.add(button1, BorderLayout.NORTH);
panel.add(button2, BorderLayout.CENTER);
```
```
// 创建一个 JPanel，并设置 GridLayout 布局管理器
JPanel panel = new JPanel(new GridLayout(2, 3)); // 2 行，3 列的网格

// 添加按钮到 JPanel
JButton button1 = new JButton("Button 1");
JButton button2 = new JButton("Button 2");
JButton button3 = new JButton("Button 3");
JButton button4 = new JButton("Button 4");
JButton button5 = new JButton("Button 5");
JButton button6 = new JButton("Button 6");

panel.add(button1);
panel.add(button2);
panel.add(button3);Q
panel.add(button4);
panel.add(button5);
panel.add(button6);
```

12. **JFrame:**
    - 顶层窗口，表示应用程序的主窗口。
```
//初始化并设置窗口
JFrame frame = new JFrame("Panel with Components");
frame.setSize(400, 300);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//设置窗口可见
frame.setVisible(true);
```

13. **JDialog:**
    - 对话框，表示一个弹出式窗口，通常用于显示消息或接收用户输入。
```
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JDialogExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("JDialog Example");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        JButton showDialogButton = new JButton("Show Dialog");

        showDialogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 创建一个 JDialog
                JDialog dialog = new JDialog(frame, "Dialog Example", true);
                
                // 设置对话框的布局
                dialog.setLayout(null);

                // 向对话框中添加组件
                JLabel label = new JLabel("This is a dialog!");
                label.setBounds(20, 20, 150, 30);
                dialog.add(label);

                JButton closeButton = new JButton("Close");
                closeButton.setBounds(20, 60, 80, 30);

                closeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // 关闭对话框
                        dialog.dispose();
                    }
                });

                dialog.add(closeButton);

                // 设置对话框的大小和可见性
                dialog.setSize(200, 150);
                dialog.setVisible(true);
            }
        });

        panel.add(showDialogButton);
        frame.getContentPane().add(panel);

        frame.setVisible(true);
    }
}

```

14. **JSplitPane:**
    - 分隔窗格，可以将容器分为两个可调整大小的区域。
```
// 创建左侧和右侧的面板
JPanel leftPanel = new JPanel();
JPanel rightPanel = new JPanel();

// 向左侧和右侧的面板添加一些组件
leftPanel.add(new JButton("Left Button 1"));
leftPanel.add(new JButton("Left Button 2"));

rightPanel.add(new JButton("Right Button 1"));
rightPanel.add(new JButton("Right Button 2"));

// 创建 JSplitPane，指定方向和左右两侧的组件
JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);

// 设置分隔条的位置（可选）
splitPane.setDividerLocation(150);

// 将 JSplitPane 添加到 JFrame
frame.getContentPane().add(splitPane);
```

15. **JProgressBar:**
    - 进度条，用于显示任务完成的百分比。
   ```
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JProgressBarExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("JProgressBar Example");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();

        // 创建一个 JProgressBar
        JProgressBar progressBar = new JProgressBar();
        progressBar.setStringPainted(true); // 显示进度百分比文本

        // 创建一个按钮，用于启动模拟任务
        JButton startButton = new JButton("Start Task");

        // 添加按钮点击事件监听器
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 模拟一个长时间运行的任务
                Thread taskThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i <= 100; i++) {
                            final int progressValue = i;

                            // 更新进度条的值
                            progressBar.setValue(progressValue);

                            try {
                                // 模拟任务执行时间
                                Thread.sleep(50);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });

                taskThread.start();
            }
        });

        panel.add(progressBar);
        panel.add(startButton);

        frame.getContentPane().add(panel);

        frame.setVisible(true);
    }
}
```

16. **JSlider:**
    - 滑块，允许用户通过拖动选择一个范围内的值。
```
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class JSliderExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("JSlider Example");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();

        // 创建一个 JSlider，设置最小值、最大值和初始值
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        slider.setMajorTickSpacing(10); // 设置主刻度间隔
        slider.setMinorTickSpacing(1); // 设置次刻度间隔
        slider.setPaintTicks(true); // 显示刻度
        slider.setPaintLabels(true); // 显示标签

        // 添加 ChangeListener 监听器，以便在滑块值变化时执行操作
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // 获取滑块的当前值
                int sliderValue = slider.getValue();
                System.out.println("Slider Value: " + sliderValue);
            }
        });

        panel.add(slider);

        frame.getContentPane().add(panel);

        frame.setVisible(true);
    }
}
```

这只是一小部分可用组件的列表，Swing 还提供了许多其他组件和功能，以满足各种 GUI 设计需求。通过组合这些组件，开发人员可以创建丰富而灵活的用户界面。
