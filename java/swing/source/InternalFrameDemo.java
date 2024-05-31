import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import java.awt.event.*;
import java.awt.*;
/*
 * InternalFrameDemo.java requires:
 *   MyInternalFrame.java
 */
public class InternalFrameDemo extends JFrame
                               implements ActionListener {
    JDesktopPane desktop;

    public InternalFrameDemo() {
        super("InternalFrameDemo");

        // 将大窗口与屏幕的每个边缘缩进50像素。
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                  screenSize.width  - inset*2,
                  screenSize.height - inset*2);

        // 设置GUI。
        desktop = new JDesktopPane(); // 一个专门的分层面板
        createFrame(); // 创建第一个“窗口”
        setContentPane(desktop);
        setJMenuBar(createMenuBar());

        // 使拖拽速度快一些，但可能会更丑陋。
        desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
    }

    protected JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // 设置唯一的菜单。
        JMenu menu = new JMenu("Document");
        menu.setMnemonic(KeyEvent.VK_D);
        menuBar.add(menu);

        // 设置第一个菜单项。
        JMenuItem menuItem = new JMenuItem("New");
        menuItem.setMnemonic(KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("new");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        // 设置第二个菜单项。
        menuItem = new JMenuItem("Quit");
        menuItem.setMnemonic(KeyEvent.VK_Q);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("quit");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        return menuBar;
    }

    // 响应菜单选择。
    public void actionPerformed(ActionEvent e) {
        if ("new".equals(e.getActionCommand())) { // new
            createFrame();
        } else { // quit
            quit();
        }
    }

    // 创建一个新的内部框架。
    protected void createFrame() {
        MyInternalFrame frame = new MyInternalFrame();
        frame.setVisible(true); // 1.3之后必要
        desktop.add(frame);
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}
    }

    // 退出应用程序。
    protected void quit() {
        System.exit(0);
    }

    /**
     * 创建并显示GUI。为了线程安全性，
     * 这个方法应该从事件分派线程中调用。
     */
    private static void createAndShowGUI() {
        // 确保有良好的窗口装饰。
        JFrame.setDefaultLookAndFeelDecorated(true);

        // 创建并设置窗口。
        InternalFrameDemo frame = new InternalFrameDemo();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 显示窗口。
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // 为事件分派线程安排一个任务：
        // 创建并显示此应用程序的GUI。
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
