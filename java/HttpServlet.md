# HttpServlet.class
## 一、成员属性
```java
    private static final String METHOD_DELETE = "DELETE";
    private static final String METHOD_HEAD = "HEAD";
    private static final String METHOD_GET = "GET";
    private static final String METHOD_OPTIONS = "OPTIONS";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_PUT = "PUT";
    private static final String METHOD_TRACE = "TRACE";
    private static final String HEADER_IFMODSINCE = "If-Modified-Since";
    private static final String HEADER_LASTMOD = "Last-Modified";
    private static final String LSTRING_FILE = "javax.servlet.http.LocalStrings";
    private static ResourceBundle lStrings = ResourceBundle.getBundle("javax.servlet.http.LocalStrings");
```

## 二、成员方法
### 1. void doGet(HttpServletRequest req, HttpServletResponse resp)
处理HTTP GET请求。然而，在这个实现中，它实际上并不处理GET请求，而是明确地表示不支持GET请求，并根据协议版本返回适当的HTTP错误代码。
```java
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // 获取请求的协议版本（例如，HTTP/1.1或HTTP/1.0）
    String protocol = req.getProtocol();
    
    // 从资源文件中获取错误信息（一般是一个常量字符串，表示GET方法不支持）
    String msg = lStrings.getString("http.method_get_not_supported");
    
    // 如果协议是HTTP/1.1，则返回405 Method Not Allowed错误
    if (protocol.endsWith("1.1")) {
        resp.sendError(405, msg);
    } 
    // 否则（如HTTP/1.0），返回400 Bad Request错误
    else {
        resp.sendError(400, msg);
    }
}
```
用途
这个方法主要用于在Servlet中明确表示不支持GET请求。它可以用于以下几种情况：
限制HTTP方法：当一个Servlet只应该处理特定的HTTP方法（如POST、PUT等）而不允许处理GET请求时，可以使用这个方法来返回错误，避免误用。
安全考虑：在某些情况下，为了安全性，某些操作可能不适合通过GET请求暴露，以防止敏感信息泄露或被未经授权的用户访问。
API设计：在RESTful API设计中，如果某个资源或操作不支持GET方法，可以通过这种方式明确告知客户端。

实际应用示例
```java
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String protocol = req.getProtocol();
        String msg = "GET method is not supported";
        
        if (protocol.endsWith("1.1")) {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, msg);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, msg);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 处理POST请求的逻辑
        resp.setContentType("text/html");
        resp.getWriter().println("<h1>POST request processed</h1>");
    }
}
```
解释示例代码
doGet方法：
获取请求的协议版本。
获取错误消息（在这个例子中，直接使用硬编码的字符串）。
根据协议版本返回适当的HTTP错误代码（405 Method Not Allowed或400 Bad Request）。

doPost方法：
处理POST请求并返回响应内容。
通过这种方式，客户端在尝试使用GET方法访问这个Servlet时，会收到明确的错误提示，表明GET方法不被支持，从而避免不正确的请求操作。

### 2. long getLastModified(HttpServletRequest req)
确定请求资源的最后修改时间，以便在支持的情况下提供缓存机制。这个方法的默认实现返回-1，表示无法确定资源的修改时间。
```java
protected long getLastModified(HttpServletRequest req) {
    return -1L;
}
```
详细解释
返回值：
默认实现返回-1L，表示无法确定资源的最后修改时间。
如果返回的是一个正的时间戳（long类型，单位是毫秒），这个值表示资源的最后修改时间。

使用场景：
getLastModified方法常用于实现条件请求（conditional requests），尤其是处理If-Modified-Since头部。通过使用这个方法，服务器可以告诉客户端资源是否自上次请求以来被修改。
当客户端发送一个包含If-Modified-Since头的请求时，服务器会调用getLastModified方法来获取资源的最后修改时间。然后，服务器将这个时间与If-Modified-Since头的值进行比较。如果资源未修改，服务器可以返回HTTP 304 Not Modified状态码，而不是重新传输整个资源，从而节省带宽和提高性能。

示例代码
```java
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyServlet extends HttpServlet {

    // 模拟资源的最后修改时间
    private long lastModified = System.currentTimeMillis();

    @Override
    protected long getLastModified(HttpServletRequest req) {
        // 返回资源的最后修改时间
        return lastModified;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        long ifModifiedSince = req.getDateHeader("If-Modified-Since");
        if (ifModifiedSince != -1 && ifModifiedSince >= lastModified) {
            // 如果资源未修改，返回304 Not Modified
            resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return;
        }

        // 如果资源已修改或没有If-Modified-Since头，返回资源内容
        resp.setContentType("text/html");
        resp.getWriter().println("<h1>Hello, World!</h1>");
        // 设置Last-Modified头
        resp.setDateHeader("Last-Modified", lastModified);
    }

    // 用于更新资源内容和修改时间
    public void updateContent() {
        // 更新内容
        lastModified = System.currentTimeMillis();
    }
}
```

### 3.void doHead(HttpServletRequest req, HttpServletResponse resp)
处理HTTP HEAD请求。HEAD请求和GET请求类似，但服务器只返回响应头，不返回响应体。这对于检查资源的元信息（如内容长度、类型等）而不下载资源本身是有用的。
这个特定的doHead方法实现通过调用doGet方法来生成响应头信息，然后使用自定义的NoBodyResponse类来确保响应体为空，并设置适当的内容长度。
```
protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    NoBodyResponse response = new NoBodyResponse(resp);
    this.doGet(req, response);
    response.setContentLength();
}
```
作用
创建NoBodyResponse对象：
NoBodyResponse response = new NoBodyResponse(resp);
NoBodyResponse是一个包装器类，用来拦截和丢弃通过输出流写入的内容，确保响应体为空。
它包装了原始的HttpServletResponse对象。

调用doGet方法：
this.doGet(req, response);
调用当前Servlet的doGet方法来处理请求。
使用NoBodyResponse对象替代原始的响应对象，这样所有写入的响应内容都会被丢弃，只保留响应头。

设置内容长度：
response.setContentLength();
调用NoBodyResponse的setContentLength方法来设置内容长度头。
确保响应头中包含正确的Content-Length字段，尽管实际的响应体是空的。

### 4.void doPost(HttpServletRequest req, HttpServletResponse resp)
处理HTTP POST请求，并明确表示不支持POST请求。根据HTTP协议的版本，返回适当的HTTP错误状态码来通知客户端，POST方法在这个Servlet中不被允许。
```
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // 获取请求的协议版本（例如，HTTP/1.1或HTTP/1.0）
    String protocol = req.getProtocol();
    
    // 从资源文件中获取错误信息（一般是一个常量字符串，表示POST方法不支持）
    String msg = lStrings.getString("http.method_post_not_supported");
    
    // 如果协议是HTTP/1.1，则返回405 Method Not Allowed错误
    if (protocol.endsWith("1.1")) {
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, msg);
    } 
    // 否则（如HTTP/1.0），返回400 Bad Request错误
    else {
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, msg);
    }
}
```
用途
这个方法主要用于在Servlet中明确表示不支持POST请求。它可以用于以下几种情况：
限制HTTP方法：当一个Servlet只应该处理特定的HTTP方法（如GET、PUT等）而不允许处理POST请求时，可以使用这个方法来返回错误，避免误用。
安全考虑：在某些情况下，为了安全性，某些操作可能不适合通过POST请求暴露，以防止未经授权的操作。
API设计：在RESTful API设计中，如果某个资源或操作不支持POST方法，可以通过这种方式明确告知客户端。

### 5.void doPut(HttpServletRequest req, HttpServletResponse resp)
处理HTTP PUT请求，并明确表示不支持PUT请求。根据HTTP协议的版本，返回适当的HTTP错误状态码来通知客户端，put方法在这个Servlet中不被允许。
```
protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String protocol = req.getProtocol();
        String msg = lStrings.getString("http.method_put_not_supported");
        if (protocol.endsWith("1.1")) {
            resp.sendError(405, msg);
        } else {
            resp.sendError(400, msg);
        }

    }
```
用途
这个方法主要用于在Servlet中明确表示不支持put请求。它可以用于以下几种情况：
限制HTTP方法：当一个Servlet只应该处理特定的HTTP方法（如GET、POST等）而不允许处理PuT请求时，可以使用这个方法来返回错误，避免误用。
安全考虑：在某些情况下，为了安全性，某些操作可能不适合通过PUT请求暴露，以防止未经授权的操作。
API设计：在RESTful API设计中，如果某个资源或操作不支持PUT方法，可以通过这种方式明确告知客户端。

### 6.void doDelete(HttpServletRequest req, HttpServletResponse resp)
处理HTTP DELETE请求，但它实际上不支持DELETE请求。
```
protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // 获取请求的协议版本（例如，HTTP/1.1或HTTP/1.0）
    String protocol = req.getProtocol();
    
    // 从资源文件中获取错误信息（一般是一个常量字符串，表示DELETE方法不支持）
    String msg = lStrings.getString("http.method_delete_not_supported");
    
    // 如果协议是HTTP/1.1，则返回405 Method Not Allowed错误
    if (protocol.endsWith("1.1")) {
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, msg);
    } 
    // 否则（如HTTP/1.0），返回400 Bad Request错误
    else {
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, msg);
    }
}

```
用途
这个方法主要用于在Servlet中明确表示不支持DELETE请求。它可以用于以下几种情况：
限制HTTP方法：当一个Servlet只应该处理特定的HTTP方法（如GET、POST等）而不允许处理DELETE请求时，可以使用这个方法来返回错误，避免误用。
安全考虑：在某些情况下，为了安全性，某些操作可能不适合通过DELETE请求暴露，以防止未经授权的删除操作。
API设计：在RESTful API设计中，如果某个资源或操作不支持DELETE方法，可以通过这种方式明确告知客户端。

### 7.Method[] getAllDeclaredMethods(Class<?> c)
用于递归获取一个类及其所有父类中声明的所有方法（包括私有方法）
```
private Method[] getAllDeclaredMethods(Class<?> c) {
    if (c.equals(HttpServlet.class)) {
        return null;
    } else {
        Method[] parentMethods = this.getAllDeclaredMethods(c.getSuperclass());
        Method[] thisMethods = c.getDeclaredMethods();
        if (parentMethods != null && parentMethods.length > 0) {
            Method[] allMethods = new Method[parentMethods.length + thisMethods.length];
            System.arraycopy(parentMethods, 0, allMethods, 0, parentMethods.length);
            System.arraycopy(thisMethods, 0, allMethods, parentMethods.length, thisMethods.length);
            thisMethods = allMethods;
        }

        return thisMethods;
    }
}
```

详细解释
基类检查：
```
if (c.equals(HttpServlet.class)) {
    return null;
}
```
如果当前类是HttpServlet，返回null，表示不获取HttpServlet类中的方法。这可能是为了忽略特定的基类方法。

递归获取父类的方法：
```
Method[] parentMethods = this.getAllDeclaredMethods(c.getSuperclass());
递归调用当前方法，获取父类的所有声明的方法。
```

获取当前类的方法：
```
Method[] thisMethods = c.getDeclaredMethods();
获取当前类声明的所有方法，包括私有方法。
```

合并父类方法和当前类方法：
```
if (parentMethods != null && parentMethods.length > 0) {
    Method[] allMethods = new Method[parentMethods.length + thisMethods.length];
    System.arraycopy(parentMethods, 0, allMethods, 0, parentMethods.length);
    System.arraycopy(thisMethods, 0, allMethods, parentMethods.length, thisMethods.length);
    thisMethods = allMethods;
}
```
如果父类的方法不为空，将父类的方法和当前类的方法合并到一个新的数组中。

返回方法数组：
```
return thisMethods;
```
返回包含当前类和所有父类的方法数组。

使用场景
这个方法可以用于以下几种场景：

反射操作：在需要反射获取类及其父类中的所有方法时使用。
框架设计：在设计框架时，可能需要遍历类的继承结构，获取所有方法来进行某些处理。
工具类：用于调试或分析类的结构，获取类及其继承层次中的所有方法。

### 8.void doOptions(HttpServletRequest req, HttpServletResponse resp)
处理HTTP OPTIONS请求。OPTIONS请求是用来获取服务器支持的HTTP方法。客户端可以发送一个OPTIONS请求，以了解服务器允许的HTTP方法。这在设计和调试HTTP服务时非常有用。
```
protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Method[] methods = this.getAllDeclaredMethods(this.getClass());
    boolean ALLOW_GET = false;
    boolean ALLOW_HEAD = false;
    boolean ALLOW_POST = false;
    boolean ALLOW_PUT = false;
    boolean ALLOW_DELETE = false;
    boolean ALLOW_TRACE = true;
    boolean ALLOW_OPTIONS = true;

    // 检查当前类及其父类中声明的方法
    for (int i = 0; i < methods.length; ++i) {
        Method m = methods[i];
        if (m.getName().equals("doGet")) {
            ALLOW_GET = true;
            ALLOW_HEAD = true;
        }
        if (m.getName().equals("doPost")) {
            ALLOW_POST = true;
        }
        if (m.getName().equals("doPut")) {
            ALLOW_PUT = true;
        }
        if (m.getName().equals("doDelete")) {
            ALLOW_DELETE = true;
        }
    }

    // 构建Allow头的值
    String allow = null;
    if (ALLOW_GET) {
        allow = "GET";
    }
    if (ALLOW_HEAD) {
        if (allow == null) {
            allow = "HEAD";
        } else {
            allow = allow + ", HEAD";
        }
    }
    if (ALLOW_POST) {
        if (allow == null) {
            allow = "POST";
        } else {
            allow = allow + ", POST";
        }
    }
    if (ALLOW_PUT) {
        if (allow == null) {
            allow = "PUT";
        } else {
            allow = allow + ", PUT";
        }
    }
    if (ALLOW_DELETE) {
        if (allow == null) {
            allow = "DELETE";
        } else {
            allow = allow + ", DELETE";
        }
    }
    if (ALLOW_TRACE) {
        if (allow == null) {
            allow = "TRACE";
        } else {
            allow = allow + ", TRACE";
        }
    }
    if (ALLOW_OPTIONS) {
        if (allow == null) {
            allow = "OPTIONS";
        } else {
            allow = allow + ", OPTIONS";
        }
    }

    // 设置Allow头
    resp.setHeader("Allow", allow);
}
```
方法的作用
获取所有声明的方法：`Method[] methods = this.getAllDeclaredMethods(this.getClass());`
调用自定义的getAllDeclaredMethods方法，获取当前类及其父类声明的所有方法。

检查支持的HTTP方法：
遍历方法数组，检查当前类是否实现了特定的HTTP方法处理方法（如doGet, doPost, doPut, doDelete）。
如果找到相应的方法，则设置相应的布尔变量为true，表示支持该方法。

构建Allow头的值：
根据检查结果，构建一个字符串，包含所有支持的HTTP方法。
使用字符串拼接的方式，按顺序添加支持的方法。

设置Allow头：`resp.setHeader("Allow", allow);`
设置响应的Allow头，通知客户端服务器支持的HTTP方法。

使用场景
调试和开发：客户端可以发送OPTIONS请求来了解服务器支持哪些HTTP方法，帮助开发者调试和开发。
安全性：明确指定允许的HTTP方法，可以帮助防止不支持的请求类型，提高安全性。
API文档：可以自动生成API文档，说明服务器端点支持的HTTP方法。

### 9.void doTrace(HttpServletRequest req, HttpServletResponse resp)
处理HTTP TRACE请求。TRACE方法主要用于诊断请求路径中的问题。客户端发送TRACE请求以查看服务器在处理请求时所接收到的内容。服务器会在响应中返回原始请求消息。
```
protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String CRLF = "\r\n";
    String responseString = "TRACE " + req.getRequestURI() + " " + req.getProtocol();

    // 遍历请求头并构建响应字符串
    String headerName;
    for (Enumeration<String> reqHeaderEnum = req.getHeaderNames(); reqHeaderEnum.hasMoreElements(); responseString = responseString + CRLF + headerName + ": " + req.getHeader(headerName)) {
        headerName = reqHeaderEnum.nextElement();
    }

    responseString = responseString + CRLF;
    int responseLength = responseString.length();

    // 设置响应的Content-Type和Content-Length
    resp.setContentType("message/http");
    resp.setContentLength(responseLength);

    // 输出响应内容
    ServletOutputStream out = resp.getOutputStream();
    out.print(responseString);
}

```
使用场景
诊断请求路径中的问题：TRACE方法可以用于诊断和调试请求路径中的问题，帮助开发者查看服务器在处理请求时所接收到的内容。
验证代理服务器行为：通过TRACE请求，可以验证中间代理服务器是否正确转发请求，确保请求头没有被篡改。
安全性测试：检查服务器和中间设备（如防火墙、代理）的行为，确保它们没有不正当地修改请求。

### 10.void service(HttpServletRequest req, HttpServletResponse resp)
用于处理所有类型的HTTP请求。它根据请求的方法（如GET、POST、PUT等）调用相应的处理方法（如doGet、doPost等）
```
protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String method = req.getMethod();
    long lastModified;
    
    if (method.equals("GET")) {
        lastModified = this.getLastModified(req);
        if (lastModified == -1L) {
            this.doGet(req, resp);
        } else {
            long ifModifiedSince = req.getDateHeader("If-Modified-Since");
            if (ifModifiedSince < lastModified) {
                this.maybeSetLastModified(resp, lastModified);
                this.doGet(req, resp);
            } else {
                resp.setStatus(304);
            }
        }
    } else if (method.equals("HEAD")) {
        lastModified = this.getLastModified(req);
        this.maybeSetLastModified(resp, lastModified);
        this.doHead(req, resp);
    } else if (method.equals("POST")) {
        this.doPost(req, resp);
    } else if (method.equals("PUT")) {
        this.doPut(req, resp);
    } else if (method.equals("DELETE")) {
        this.doDelete(req, resp);
    } else if (method.equals("OPTIONS")) {
        this.doOptions(req, resp);
    } else if (method.equals("TRACE")) {
        this.doTrace(req, resp);
    } else {
        String errMsg = lStrings.getString("http.method_not_implemented");
        Object[] errArgs = new Object[]{method};
        errMsg = MessageFormat.format(errMsg, errArgs);
        resp.sendError(501, errMsg);
    }
}
```
使用场景
通用请求处理：service方法是Servlet容器调用的入口，用于处理所有类型的HTTP请求。
动态方法分派：根据请求的方法动态调用相应的处理方法（如GET、POST等），实现请求的分派和处理。
实现缓存控制：通过处理GET和HEAD请求中的If-Modified-Since头部，支持HTTP缓存控制机制，提高性能。

### 11.void maybeSetLastModified(HttpServletResponse resp, long lastModified)
该方法的作用是检查响应中是否已经包含 Last-Modified 头部，如果没有且 lastModified 参数是有效的（即非负），则将其设置为响应头部。
```
private void maybeSetLastModified(HttpServletResponse resp, long lastModified) {
    if (!resp.containsHeader("Last-Modified")) {
        if (lastModified >= 0L) {
            resp.setDateHeader("Last-Modified", lastModified);
        }
    }
}
```
使用场景
缓存控制：通过设置 Last-Modified 头部，客户端和代理服务器可以使用这个信息进行缓存控制。如果资源自从某个时间点以来没有修改，客户端可以避免重新下载整个资源，从而节省带宽和提高性能。
条件请求：客户端可以使用 If-Modified-Since 请求头来进行条件请求。服务器检查这个头部，如果资源自从指定的时间点以来没有修改，则返回304（未修改）状态，而不返回资源的内容。

### 12.void service(ServletRequest req, ServletResponse res)
service(ServletRequest req, ServletResponse res)方法是一个重载的service方法，它的作用是将通用的ServletRequest和ServletResponse对象转换为特定的HttpServletRequest和HttpServletResponse对象，然后调用处理HTTP请求的重载方法service(HttpServletRequest req, HttpServletResponse res)。如果转换失败，则抛出ServletException。
```
public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
    HttpServletRequest request;
    HttpServletResponse response;
    try {
        request = (HttpServletRequest) req;
        response = (HttpServletResponse) res;
    } catch (ClassCastException e) {
        throw new ServletException("non-HTTP request or response");
    }

    this.service(request, response);
}
```
使用场景
通用Servlet：当一个Servlet既可以处理HTTP请求又可以处理非HTTP请求时，该方法通过类型检查和转换确保只处理HTTP请求。如果请求不是HTTP协议的，则抛出异常，避免错误处理。
HTTP专用Servlet：对于HTTP专用的Servlet，实现了通用的service(ServletRequest, ServletResponse)方法，确保所有请求和响应都转换为HTTP协议特定的对象，然后调用相应的HTTP处理方法。
