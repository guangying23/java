```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/*
 * FrameDemo2.java 展示了在1.4中添加的窗口装饰功能，
 * 以及一些窗口定位代码和（可选的）setIconImage方法。
 * 它使用了文件 images/FD.jpg。
 */
public class FrameDemo2 extends WindowAdapter
                        implements ActionListener {
    private Point lastLocation = null;
    private int maxX = 500;
    private int maxY = 500;

    //主框架的默认按钮
    private static JButton defaultButton = null;

    //动作命令的常量
    protected final static String NO_DECORATIONS = "no_dec";
    protected final static String LF_DECORATIONS = "laf_dec";
    protected final static String WS_DECORATIONS = "ws_dec";
    protected final static String CREATE_WINDOW = "new_win";
    protected final static String DEFAULT_ICON = "def_icon";
    protected final static String FILE_ICON = "file_icon";
    protected final static String PAINT_ICON = "paint_icon";

    //下一个创建的框架是否应该没有窗口装饰
    protected boolean noDecorations = false;

    //下一个创建的框架是否应该调用 setIconImage
    protected boolean specifyIcon = false;

    //下一个创建的框架是否应该有自定义绘制的图标
    protected boolean createIcon = false;

    //执行一些初始化。
    public FrameDemo2() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        maxX = screenSize.width - 50;
        maxY = screenSize.height - 50;
    }

    //创建一个新的 MyFrame 对象并显示它。
    public void showNewWindow() {
        JFrame frame = new MyFrame();

        //处理没有窗口装饰的情况。
        //注意：除非你真的需要 JFrame 提供的功能，
        //否则你通常会使用 Window 或 JWindow 而不是无装饰的 JFrame。
        if (noDecorations) {
            frame.setUndecorated(true);
        }

        //设置窗口位置。
        if (lastLocation != null) {
            //将窗口向右和向下移动40像素。
            lastLocation.translate(40, 40);
            if ((lastLocation.x > maxX) || (lastLocation.y > maxY)) {
                lastLocation.setLocation(0, 0);
            }
            frame.setLocation(lastLocation);
        } else {
            lastLocation = frame.getLocation();
        }

        //调用 setIconImage 设置在窗口最小化时显示的图标。
        //如果装饰由外观提供，大多数窗口系统（或外观）也会在窗口装饰中使用此图标。
        if (specifyIcon) {
            if (createIcon) {
                frame.setIconImage(createFDImage()); //从头开始创建图标
            } else {
                frame.setIconImage(getFDImage());    //从文件中获取图标
            }
        }

        //显示窗口。
        frame.setSize(new Dimension(170, 100));
        frame.setVisible(true);
    }

    //创建主窗口中的窗口创建控件。
    protected JComponent createOptionControls() {
        JLabel label1 = new JLabel("为后续创建的框架提供装饰选项:");
        ButtonGroup bg1 = new ButtonGroup();
        JLabel label2 = new JLabel("图标选项:");
        ButtonGroup bg2 = new ButtonGroup();

        //创建按钮
        JRadioButton rb1 = new JRadioButton();
        rb1.setText("外观装饰");
        rb1.setActionCommand(LF_DECORATIONS);
        rb1.addActionListener(this);
        rb1.setSelected(true);
        bg1.add(rb1);
        //
        JRadioButton rb2 = new JRadioButton();
        rb2.setText("窗口系统装饰");
        rb2.setActionCommand(WS_DECORATIONS);
        rb2.addActionListener(this);
        bg1.add(rb2);
        //
        JRadioButton rb3 = new JRadioButton();
        rb3.setText("无装饰");
        rb3.setActionCommand(NO_DECORATIONS);
        rb3.addActionListener(this);
        bg1.add(rb3);
        //
        //
        JRadioButton rb4 = new JRadioButton();
        rb4.setText("默认图标");
        rb4.setActionCommand(DEFAULT_ICON);
        rb4.addActionListener(this);
        rb4.setSelected(true);
        bg2.add(rb4);
        //
        JRadioButton rb5 = new JRadioButton();
        rb5.setText("来自 JPEG 文件的图标");
        rb5.setActionCommand(FILE_ICON);
        rb5.addActionListener(this);
        bg2.add(rb5);
        //
        JRadioButton rb6 = new JRadioButton();
        rb6.setText("绘制的图标");
        rb6.setActionCommand(PAINT_ICON);
        rb6.addActionListener(this);
        bg2.add(rb6);

        //将所有内容添加到一个容器中。
        Box box = Box.createVerticalBox();
        box.add(label1);
        box.add(Box.createVerticalStrut(5)); //间隔
        box.add(rb1);
        box.add(rb2);
        box.add(rb3);
        //
        box.add(Box.createVerticalStrut(15)); //间隔
        box.add(label2);
        box.add(Box.createVerticalStrut(5)); //间隔
        box.add(rb4);
        box.add(rb5);
        box.add(rb6);

        //添加一些空隙。
        box.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        return box;
    }

    //创建主窗口中的按钮。
    protected JComponent createButtonPane() {
        JButton button = new JButton("新窗口");
        button.setActionCommand(CREATE_WINDOW);
        button.addActionListener(this);
        defaultButton = button; //稍后用于将其设置为框架的默认按钮。

        //将按钮居中放置在一个带有一些空隙的面板中。
        JPanel pane = new JPanel(); //使用默认的流布局
        pane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        pane.add(button);

        return pane;
    }

    //处理所有按钮的动作事件。
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        //处理新窗口按钮。
        if (CREATE_WINDOW.equals(command)) {
            showNewWindow();

        //处理第一组单选按钮。
        } else if (NO_DECORATIONS.equals(command)) {
            noDecorations = true;
            JFrame.setDefaultLookAndFeelDecorated(false);
        } else if (WS_DECORATIONS.equals(command)) {
            noDecorations = false;
            JFrame.setDefaultLookAndFeelDecorated(false);
        } else if (LF_DECORATIONS.equals(command)) {
            noDecorations = false;
            JFrame.setDefaultLookAndFeelDecorated(true);

        //处理第二组单选按钮。
        } else if (DEFAULT_ICON.equals(command)) {
            specifyIcon = false;
        } else if (FILE_ICON.equals(command)) {
            specifyIcon = true;
            createIcon = false;
        } else if (PAINT_ICON.equals(command)) {
            specifyIcon = true;
            createIcon = true;
        }
    }

    //从头开始创建一个图标合适的图像。
    protected static Image createFDImage() {
        //创建一个16x16像素的图像。
        BufferedImage bi = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);

        //在其中绘制。
        Graphics g = bi.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 15, 15);
        g.setColor(Color.RED);
        g.fillOval(5, 3, 6, 6);

        //清理。
        g.dispose();

        //返回图像。
        return bi;
    }

    //返回一个图像或空值。
    protected static Image getFDImage() {
        java.net.URL imgURL = FrameDemo2.class.getResource("images/FD.jpg");
        if (imgURL != null) {
            return new ImageIcon(imgURL).getImage();
        } else {
            return null;
        }
    }

    /**
     * 创建GUI并显示它。为了线程安全，
     * 此方法应该在事件调度线程中调用。
     */
    private static void createAndShowGUI() {
        //使用Java外观。
        try {
            UIManager.setLookAndFeel(
                UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) { }

        //确保我们有漂亮的窗口装饰。
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);

        //实例化控制类。
        JFrame frame = new JFrame("FrameDemo2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //创建并设置内容窗格。
        FrameDemo2 demo = new FrameDemo2();

        //将组件添加到其中。
        Container contentPane = frame.getContentPane();
        contentPane.add(demo.createOptionControls(),
                        BorderLayout.CENTER);
        contentPane.add(demo.createButtonPane(),
                        BorderLayout.PAGE_END);
        frame.getRootPane().setDefaultButton(defaultButton);

        //显示窗口。
        frame.pack();
        frame.setLocationRelativeTo(null); //居中
        frame.setVisible(true);
    }

    //启动演示。
    ```java
    public static void main(String[] args) {
        // 为事件调度线程安排一个任务：
        // 创建并显示该应用程序的 GUI。
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    class MyFrame extends JFrame implements ActionListener {
    
        // 创建一个带按钮的框架。
        public MyFrame() {
            super("A window");
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    
            // 这个按钮可以让你关闭一个没有装饰的窗口。
            JButton button = new JButton("Close window");
            button.addActionListener(this);
    
            // 将按钮放置在窗口底部附近。
            Container contentPane = getContentPane();
            contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
            contentPane.add(Box.createVerticalGlue()); // 占用所有额外的空间
            contentPane.add(button);
            button.setAlignmentX(Component.CENTER_ALIGNMENT); // 水平居中
            contentPane.add(Box.createVerticalStrut(5)); // 间隔
        }
    
        // 让按钮执行与默认关闭操作（DISPOSE_ON_CLOSE）相同的操作。
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            dispose();
        }
}
```
