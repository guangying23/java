许多 Swing 组件，如标签、按钮和标签页窗格，可以用图标来装饰——一种固定大小的图片。图标是一个遵循 Icon 接口的对象。Swing 提供了一个特别有用的 Icon 接口实现：ImageIcon，它可以从 GIF、JPEG 或 PNG 图像中绘制图标。

这是一个应用程序的快照，其中有三个标签，两个装饰了图标：  
![image](https://github.com/guangying23/java/assets/54796147/938631d2-fc93-4d79-9ff4-220d23841fc6)

程序使用一个图像图标来包含和绘制黄色的飞溅图案。一个语句创建了图像图标，另外两个语句将图像图标包含在两个标签上：

```java
ImageIcon icon = createImageIcon("images/middle.gif",
                                 "a pretty but meaningless splat");
label1 = new JLabel("Image and Text", icon, JLabel.CENTER);
...
label3 = new JLabel(icon);
```

createImageIcon 方法（在前面的片段中使用）是我们在许多代码示例中使用的方法。它找到指定的文件，并为该文件返回一个 ImageIcon，如果找不到该文件，则返回 null。以下是一个典型的实现：

```java
/** 返回一个 ImageIcon，如果路径无效则返回 null。 */
protected ImageIcon createImageIcon(String path,
                                           String description) {
    java.net.URL imgURL = getClass().getResource(path);
    if (imgURL != null) {
        return new ImageIcon(imgURL, description);
    } else {
        System.err.println("Couldn't find file: " + path);
        return null;
    }
}
```

在前面的片段中，ImageIcon 构造函数的第一个参数相对于当前类的位置，并将被解析为一个绝对 URL。描述参数是一个字符串，允许辅助技术帮助视力受损的用户理解图标传达的信息。

通常，应用程序提供自己的一套作为应用程序一部分的图像，如我们许多演示中使用的图像。你应该使用 Class 的 getResource 方法来获取图像的路径。这允许应用程序验证图像是否可用，并在图像不可用时提供合理的错误处理。当图像不是应用程序的一部分时，不应使用 getResource，而是直接使用 ImageIcon 构造函数。例如：

```java
ImageIcon icon = new ImageIcon("images/middle.gif",
                               "a pretty but meaningless splat");
```

当你向 ImageIcon 构造函数指定一个文件名或 URL 时，处理将被阻塞，直到图像数据完全加载完毕或数据位置被证明无效。如果数据位置无效（但非空），则仍会成功创建 ImageIcon；它只是没有大小，因此不会绘制任何内容。如 createImageIcon 方法所示，建议在将 URL 传递给 ImageIcon 构造函数之前，先验证 URL 指向的文件是否存在。这允许在文件不存在时优雅地处理错误。如果你想在加载图像时获取更多信息，你可以通过调用其 setImageObserver 方法在图像图标上注册一个观察者。

在底层，每个图像图标使用一个 Image 对象来持有图像数据。

本节其余部分涵盖以下主题：

- 更复杂的图像图标示例
- 使用 getResource 加载图像
- 将图像加载到小应用程序中
- 改善加载图像图标时的感知性能
- 创建自定义图标实现
- 图像图标 API
- 使用图标的示例

## A More Complex Image Icon Example
这是一个使用六个图像图标的应用程序。其中五个显示缩略图，第六个显示全尺寸照片。  
![image](https://github.com/guangying23/java/assets/54796147/bd0c65d2-b998-4d28-a80b-5066077f491a)

IconDemoApp 展示了图标在以下几种方式中的使用：

1. 作为附加到按钮的 GUI 元素（按钮上的缩略图像）。
2. 用于显示图像（五幅照片）。

这些照片是通过 `loadImages.execute` 在一个单独的线程中加载的。稍后在本节中会展示 `loadImages` 的代码。

`ThumbnailAction` 类，一个在 `IconDemoApp.java` 中的内部类，是 `AbstractAction` 的一个子类，它管理我们的全尺寸图像图标、一个缩略图版本及其描述。当调用 `actionPerformed` 方法时，全尺寸图像将被加载到主显示区域。每个按钮都有自己的 `ThumbnailAction` 实例，指定要显示的不同图像。

```java
/**
 * 行为类，显示其构造函数中指定的图像。
 */
private class ThumbnailAction extends AbstractAction {

    /**
     * 我们想要显示的全尺寸图像的图标。
     */
    private Icon displayPhoto;

    /**
     * @param Icon - 按钮中显示的全尺寸照片。
     * @param Icon - 按钮中显示的缩略图。
     * @param String - 图标的描述。
     */
    public ThumbnailAction(Icon photo, Icon thumb, String desc){
        displayPhoto = photo;

        // 短描述变成按钮的工具提示。
        putValue(SHORT_DESCRIPTION, desc);

        // LARGE_ICON_KEY 实际上是设置按钮中图标的键
        // 当 Action 应用于按钮时。
        putValue(LARGE_ICON_KEY, thumb);
    }

    /**
     * 在主区域显示全尺寸图像并设置应用程序标题。
     */
    public void actionPerformed(ActionEvent e) {
        photographLabel.setIcon(displayPhoto);
        setTitle("Icon Demo: " + getValue(SHORT_DESCRIPTION).toString());
    }
}
```

这个 `ThumbnailAction` 类通过在按钮上设置缩略图和全尺寸图像，以及响应按钮点击事件来显示全尺寸图像，展示了如何有效地使用 `AbstractAction` 来处理事件和更新 UI。

## Loading Images Using getResource
通常情况下，图像图标的数据来源于一个图像文件。你的应用程序的类文件和图像文件在文件服务器上可能有多种有效的配置方式。你可能将类文件放在一个 JAR 文件中，或者将图像文件放在一个 JAR 文件中；它们可能在同一个 JAR 文件中，也可能在不同的 JAR 文件中。以下几种方式展示了这些文件可以如何配置：
![image](https://github.com/guangying23/java/assets/54796147/1e5ec0c0-96d8-439d-80ce-ea30522c87bd)
![image](https://github.com/guangying23/java/assets/54796147/df71c672-6a2f-4147-80ad-68e5842162d6)  
类文件位于一个包含 PNG 格式图像文件的图像目录旁边。      类文件与 JAR 文件位于同一目录中。JAR 文件是通过将所有图像文件放在一个名为 images 的目录中创建的。

![image](https://github.com/guangying23/java/assets/54796147/d1d95655-ffaa-4ad7-b880-cd282ef9c119)
![image](https://github.com/guangying23/java/assets/54796147/0f4b2a22-4e75-411d-84db-265448bfb099)  
类文件在一个 JAR 文件中，而图像文件在另一个 JAR 文件中。        类文件和图像文件在同一个 JAR 文件中。

如果您正在编写一个实际应用程序，那么将文件放入包中是可能的（也是推荐的）。有关包的更多信息，请参见Java语言学习路径中的“创建和使用包”。这里有一些使用名为 "omega" 的包的可能配置：  
![image](https://github.com/guangying23/java/assets/54796147/ba319fac-24dd-4598-93ff-99cc7b9d8808)
![image](https://github.com/guangying23/java/assets/54796147/7fe4ad91-e525-41df-8134-b79c2018eb7f)  
类文件位于名为“omega”的目录中。图片位于“omega/images”目录中。         类文件位于omega目录中。图片在JAR文件中，而不在omega目录内，但按照omega/images层次结构创建。

![image](https://github.com/guangying23/java/assets/54796147/b3e4a62d-6188-459b-baaf-be707616d1a5)  
一个大的JAR文件，其中类文件位于omega目录下，图片文件位于omega/images目录下。

所有显示的七种配置都是有效的，并且相同的代码读取图像：

```java
java.net.URL imageURL = myDemo.class.getResource("images/myImage.gif");
...
if (imageURL != null) {
    ImageIcon icon = new ImageIcon(imageURL);
}
```

getResource方法会使类加载器遍历程序类路径中的目录和JAR文件，一旦找到所需的文件就返回一个URL。在这个例子中，MyDemo程序尝试从omega类中加载images/myImage.png文件。类加载器在程序类路径中的目录和JAR文件中查找/omega/images/myImage.png。如果类加载器找到了文件，它会返回包含该文件的JAR文件或目录的URL。如果类路径中的另一个JAR文件或目录包含images/myImage.png文件，类加载器会返回包含该文件的第一个实例。

以下是指定类路径的三种方式：  
- 使用-cp或-classpath命令行参数。例如，在图像位于名为images.jar的JAR文件中，类文件位于当前目录的情况下：

  ```shell
  java -cp .;images.jar MyDemo  [Microsoft Windows]
  java -cp ".;images.jar" MyDemo  [UNIX仿真Shell在Microsoft Windows上——您必须引用路径]
  java -cp .:images.jar MyDemo  [UNIX]
  ```
  
  如果您的图像和类文件在不同的JAR文件中，您的命令行将如下所示：
  
  ```shell
  java -cp .;MyDemo.jar;images.jar MyDemo  [Microsoft Windows]
  ```
  
  在所有文件都在一个JAR文件中的情况下，您可以使用以下任一命令：
  
  ```shell
  java -jar MyAppPlusImages.jar
  java -cp .;MyAppPlusImages.jar MyApp  [Microsoft Windows]
  ```
  
  有关更多信息，请参见JAR文件路径。

- 在程序的JNLP文件中（由Java Web Start使用）。例如，这是DragPictureDemo使用的JNLP文件：

  ```xml
  <?xml version="1.0" encoding="utf-8"?>
  <!-- JNLP File for DragPictureDemo -->
  <jnlp
    spec="1.0+"
    codebase="https://docs.oracle.com/javase/tutorialJWS/src/uiswing/misc/examples"
    href="DragPictureDemo.jnlp">
    <information>
      <title>DragPictureDemo</title>
      <vendor>The Java(tm) Tutorial: Sun Microsystems, Inc.</vendor>
      <homepage href="https://docs.oracle.com/javase/tutorial/uiswing/misc/examples/index.html#DragPictureDemo"/>
      <description>DragPictureDemo</description>
      <description kind="short">A demo showing how to install
          data transfer on a custom component.</description>
      <offline-allowed/>
    </information>
    <resources>
      <j2se version="1.6+"/>
      <jar href="allClasses.jar"/>
      <jar href="images.jar"/>
    </resources>
    <application-desc main-class="DragPictureDemo"/>
  </jnlp>
  ```
  
  在这个例子中，类文件和图像文件位于不同的JAR文件中。JAR文件使用XML的jar标签指定。

- 设置CLASSPATH环境变量。这种方法最后一种方法不推荐。如果未设置CLASSPATH，则默认使用当前目录（“.”）及JRE附带的系统类的位置。

大多数Swing教程示例将图像放在包含示例类文件的目录下的images目录中。当我们为示例创建JAR文件时，我们保持相同的相对位置，尽管我们通常将类文件放在不同于图像JAR文件的JAR文件中。无论类文件和图像文件在文件系统中的位置如何——在一个JAR文件中，或在多个JAR文件中，在命名包中，或在默认包中——相同的代码都可以使用getResource找到图像文件。

有关更多信息，请参见以与位置无关的方式访问资源和应用程序开发注意事项。

## Loading Images Into Applets
Applet通常从提供Applet的计算机加载图像数据。APPLET标签是您指定Applet中使用的图像信息的位置。有关APPLET标签的更多信息，请参见使用APPLET标签。

## 提高加载图像图标时的感知性能
由于照片图像的访问速度较慢，IconDemoApp.java使用SwingWorker来提高用户感知的程序性能。

背景图像加载——程序使用javax.swing.SwingWorker对象在后台线程中加载每张照片图像并计算其缩略图。使用SwingWorker可防止程序在加载和缩放图像时看起来冻结。

以下是处理每张图像的代码：

```java
/**
 * SwingWorker类在后台线程中加载图像并在新图像准备好显示时调用publish。
 *
 * 我们使用Void作为第一个SwingWorker参数，因为我们不需要从doInBackground()返回任何内容。
 */
private SwingWorker<Void, ThumbnailAction> loadimages = new SwingWorker<Void, ThumbnailAction>() {

    /**
     * 创建目标图像文件的全尺寸和缩略图版本。
     */
    @Override
    protected Void doInBackground() throws Exception {
        for (int i = 0; i < imageCaptions.length; i++) {
            ImageIcon icon;
            icon = createImageIcon(imagedir + imageFileNames[i], imageCaptions[i]);

            ThumbnailAction thumbAction;
            if (icon != null) {
                ImageIcon thumbnailIcon = new ImageIcon(getScaledImage(icon.getImage(), 32, 32));

                thumbAction = new ThumbnailAction(icon, thumbnailIcon, imageCaptions[i]);

            } else {
                // 图像因某种原因未能加载
                // 因此加载一个占位符
                thumbAction = new ThumbnailAction(placeholderIcon, placeholderIcon, imageCaptions[i]);
            }
            publish(thumbAction);
        }
        // 不幸的是，我们必须返回一些内容，当返回类型为void时，只有null是有效的返回值。
        return null;
    }

    /**
     * 处理所有加载的图像。
     */
    @Override
    protected void process(List<ThumbnailAction> chunks) {
        for (ThumbnailAction thumbAction : chunks) {
            JButton thumbButton = new JButton(thumbAction);
            // 在最后一个glue之前添加新按钮
            // 这会将按钮居中到工具栏中
            buttonBar.add(thumbButton, buttonBar.getComponentCount() - 1);
        }
    }
};
```

SwingWorker在后台线程中调用doInBackground方法。该方法将全尺寸图像、缩略图和标题放入一个ThumbnailAction对象中。然后，SwingWorker将ThumbnailAction传递给process方法。process方法在事件分派线程上执行，并通过向工具栏添加按钮来更新GUI。JButton有一个构造函数接受一个action对象。action对象决定了按钮的多个属性。在我们的例子中，按钮图标、标题和按下按钮时执行的操作都由ThumbnailAction决定。

开销——该程序最终将所有源图像加载到内存中。这在某些情况下可能不理想。加载许多非常大的文件可能会导致程序分配大量内存。应注意管理加载的图像数量和大小。

与所有性能相关的问题一样，这种技术在某些情况下适用，而在其他情况下不适用。此外，这里描述的技术旨在提高程序的感知性能，但不一定影响其实际性能。

## Creating a Custom Icon Implementation  创建自定义图标实现
createImageIcon方法在找不到图像时返回null，但程序应该怎么做呢？一种可能性是忽略该图像并继续。另一种选择是在无法加载真实图像时提供某种默认图标。再次调用createImageIcon可能会再次返回null，因此使用它不是一个好主意。相反，让我们创建一个自定义图标实现。
![image](https://github.com/guangying23/java/assets/54796147/e1ff801e-3005-4147-9cdc-d08f21aa2891)

您可以在MissingIcon.java中找到自定义图标类的实现。以下是其代码的关键部分：

```java
/**
 * "缺失图标"是一个带有黑色边框和红色X的白色框。
 * 当从外部位置加载图标时出现问题时使用它来显示一些内容。
 *
 * 作者：Collin Fagan
 */
public class MissingIcon implements Icon {

    private int width = 32;
    private int height = 32;

    private BasicStroke stroke = new BasicStroke(4);

    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(x + 1, y + 1, width - 2, height - 2);

        g2d.setColor(Color.BLACK);
        g2d.drawRect(x + 1, y + 1, width - 2, height - 2);

        g2d.setColor(Color.RED);

        g2d.setStroke(stroke);
        g2d.drawLine(x + 10, y + 10, x + width - 10, y + height - 10);
        g2d.drawLine(x + 10, y + height - 10, x + width - 10, y + 10);

        g2d.dispose();
    }

    public int getIconWidth() {
        return width;
    }

    public int getIconHeight() {
        return height;
    }
}
```

paintIcon方法传递了一个Graphics对象。Graphics对象使paintIcon方法能够访问整个Java2D API。有关绘画和Java2D的更多信息，请参见执行自定义绘画。

以下代码演示了MissingIcon类在SwingWorker的doInBackground方法中的使用：

```java
private MissingIcon placeholderIcon = new MissingIcon();

...
if(icon != null) {
    ...

} else {
    // 图像因某种原因未能加载
    // 因此加载一个占位符
    thumbAction = new ThumbnailAction(placeholderIcon, placeholderIcon, imageCaptions[i]);
}
```

使用自定义图标有一些影响：

由于图标的外观是动态确定的，图标绘制代码可以使用任何信息——例如组件和应用程序状态——来确定要绘制的内容。

根据平台和图像类型，使用自定义图标可能会提高性能，因为绘制简单形状有时比复制图像更快。

由于MissingIcon不进行任何文件I/O，因此不需要单独的线程来加载图像。

## The Image Icon API
下表列出了常用的ImageIcon构造函数和方法。请注意，ImageIcon不是JComponent甚至不是Component的后代。

使用图像图标的API分为以下几类：
- 设置、获取和绘制图像图标的图像
- 设置或获取有关图像图标的信息
- 监视图像图标的图像加载

| 方法或构造函数 | 目的 |
| --- | --- |
| ImageIcon() | 创建一个 ImageIcon 实例。 |
| ImageIcon(byte[]) | 创建一个 ImageIcon 实例，初始化为包含指定的图片，图片来源是字节数组。 |
| ImageIcon(byte[], String) | 创建一个 ImageIcon 实例，初始化为包含指定的图片，图片来源是字节数组，并提供图片描述。 |
| ImageIcon(Image) | 创建一个 ImageIcon 实例，初始化为包含指定的图片，图片来源是 Image 对象。 |
| ImageIcon(Image, String) | 创建一个 ImageIcon 实例，初始化为包含指定的图片，图片来源是 Image 对象，并提供图片描述。 |
| ImageIcon(String) | 创建一个 ImageIcon 实例，初始化为包含指定的图片，图片来源是文件名。 |
| ImageIcon(String, String) | 创建一个 ImageIcon 实例，初始化为包含指定的图片，图片来源是文件名，并提供图片描述。 |
| ImageIcon(URL) | 创建一个 ImageIcon 实例，初始化为包含指定的图片，图片来源是 URL。 |
| ImageIcon(URL, String) | 创建一个 ImageIcon 实例，初始化为包含指定的图片，图片来源是 URL，并提供图片描述。 |
| void setImage(Image) | 设置或获取由图像图标显示的图像。 |
| Image getImage() | 获取由图像图标显示的图像。 |
| void paintIcon(Component, Graphics, int, int) | 在指定的图形上下文中绘制图像图标的图像。如果您正在实现自己绘制的自定义图标，您可能需要重写此方法。Component 对象用作图像观察器。您可以依赖 Component 类提供的默认行为，并传入任何组件。两个整数参数指定图标绘制的左上角位置。 |
| URL getResource(String) | 在（java.lang.ClassLoader）中找到具有给定名称的资源。有关更多信息，请参见使用 getResource 加载图像。 |
| InputStream getResourceAsStream(String) | 在（java.lang.ClassLoader）中找到具有给定名称的资源，并返回一个输入流以读取资源。有关更多信息，请参见在小程序中加载图像的讨论。 |

| 方法 | 目的 |
| --- | --- |
| void setDescription(String) | 设置图像的描述。此描述旨在供辅助技术使用。 |
| String getDescription() | 获取图像的描述。此描述旨在供辅助技术使用。 |
| int getIconWidth() | 获取图像图标的宽度（以像素为单位）。 |
| int getIconHeight() | 获取图像图标的高度（以像素为单位）。 |

| 方法 | 目的 |
| --- | --- |
| void setImageObserver(ImageObserver) | 设置或获取图像图标的图像观察者。 |
| ImageObserver getImageObserver() | 获取图像图标的图像观察者。 |
| int getImageLoadStatus() | 获取图像图标的图像加载状态。此方法返回的值由 MediaTracker 定义。 |
