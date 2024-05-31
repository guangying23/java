import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/* FrameDemo.java 不需要其他文件。 */
public class FrameDemo {
    /**
     * 创建并显示 GUI。为了线程安全，
     * 应该从事件分发线程调用此方法。
     */
    private static void createAndShowGUI() {
        // 创建并设置窗口。
        JFrame frame = new JFrame("FrameDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel emptyLabel = new JLabel("");
        emptyLabel.setPreferredSize(new Dimension(175, 100));
        frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);

        // 显示窗口。
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // 为事件分发线程安排一个任务：
        // 创建并显示此应用程序的 GUI。
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
