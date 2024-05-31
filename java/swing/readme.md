以下是 Java Swing 的一些主要类及其继承树。为了简洁起见，只列出部分常用类和它们的直接继承关系：

### 顶层父类
- `java.lang.Object`

### 容器类
- `java.awt.Component`
  - `java.awt.Container`
    - `javax.swing.JComponent`
      - `javax.swing.JPanel`
      - `javax.swing.JScrollPane`
      - `javax.swing.JTabbedPane`
      - `javax.swing.JLayeredPane`
        - `javax.swing.JDesktopPane`
      - `javax.swing.JSplitPane`
      - `javax.swing.JInternalFrame`
      - `javax.swing.JRootPane`
        - `javax.swing.JDialog`
        - `javax.swing.JFrame`
        - `javax.swing.JWindow`

### 控件类
- `javax.swing.JComponent`
  - `javax.swing.AbstractButton`
    - `javax.swing.JButton`
    - `javax.swing.JCheckBox`
    - `javax.swing.JRadioButton`
    - `javax.swing.JMenuItem`
      - `javax.swing.JCheckBoxMenuItem`
      - `javax.swing.JRadioButtonMenuItem`
      - `javax.swing.JMenu`
        - `javax.swing.JPopupMenu`
  - `javax.swing.JLabel`
  - `javax.swing.JTextComponent`
    - `javax.swing.JTextField`
    - `javax.swing.JTextArea`
    - `javax.swing.JEditorPane`
      - `javax.swing.JFormattedTextField`
      - `javax.swing.JPasswordField`
  - `javax.swing.JList`
  - `javax.swing.JComboBox`
  - `javax.swing.JTable`
  - `javax.swing.JTree`
  - `javax.swing.JSlider`
  - `javax.swing.JProgressBar`
  - `javax.swing.JScrollBar`
  - `javax.swing.JSpinner`

### 菜单类
- `javax.swing.JMenuBar`
- `javax.swing.JMenu`
- `javax.swing.JMenuItem`
  - `javax.swing.JCheckBoxMenuItem`
  - `javax.swing.JRadioButtonMenuItem`

### 对话框类
- `javax.swing.JDialog`
  - `javax.swing.JOptionPane`

### 窗口类
- `java.awt.Window`
  - `java.awt.Frame`
    - `javax.swing.JFrame`
  - `java.awt.Dialog`
    - `javax.swing.JDialog`

### 其他重要类
- `javax.swing.JFileChooser`
- `javax.swing.JColorChooser`

