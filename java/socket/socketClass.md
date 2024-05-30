# 一、属性
//socket的状态  
`private boolean created = false;`     
表示 Socket 是否已经创建。初始化为 false，一旦 Socket 被成功创建，这个值会被设置为 true  
`private boolean bound = false;`            
表示 Socket 是否已经绑定到一个本地地址和端口。初始化为 false，一旦 Socket 绑定成功，这个值会被设置为 true。  
`private boolean connected = false;   `     
表示 Socket 是否已经连接到远程地址和端口。初始化为 false，一旦连接成功，这个值会被设置为 true。  
`private boolean closed = false;  CLOSED： `
表示 Socket 是否已经关闭。初始化为 false，一旦 Socket 被关闭，这个值会被设置为 true。  
`private Object closeLock = new Object();  `
这是一个锁对象，用于同步关闭操作，确保 Socket 的关闭过程是线程安全的。这个锁可以防止多个线程同时尝试关闭 Socket  
`private boolean shutIn = false;   `        
表示 Socket 的输入流是否已经被关闭。初始化为 false，一旦输入流被关闭，这个值会被设置为 true。  
`private boolean shutOut = false; `         
表示 Socket 的输出流是否已经被关闭。初始化为 false，一旦输出流被关闭，这个值会被设置为 true  

`SocketImpl impl;   `  
SocketImpl 是 Java 中 Socket 类的一个抽象基类，它为具体的 Socket 实现提供了基本的框架。它定义了 Socket 的底层操作，如连接、绑定、发送和接收数据等。
具体的 Socket 实现类需要继承 SocketImpl 并实现这些底层操作。  
`private boolean oldImpl = false;  `        
用于指示是否使用旧的套接字实现方式。这有助于在旧版和新版实现之间进行兼容性处理。  



# 二、方法
## 2.1.构造函数
- 2.1.1 `public Socket() {setImpl();}`
   创建一个未连接的套接字，使用系统默认类型的 SocketImpl。
- 2.1.2 `public Socket(Proxy proxy)` 
  创建一个未连接的套接字，指定应使用的代理类型（如果有），无论其他设置如何。如果存在安全管理器，则其 checkConnect 方法会以代理主机地址和端口号作为参数被调用。这可能会导致 SecurityException 异常。  
  示例：  
  - `Socket s = new Socket(Proxy.NO_PROXY);`  
    将创建一个忽略任何其他代理配置的普通套接字。  
  - `Socket s = new Socket(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("socks.mydom.com", 1080)));`  
    将创建一个通过指定的 SOCKS 代理服务器连接的套接字。
    
  参数：  
    - proxy：一个 Proxy 对象，指定应使用的代理类型。
      
  抛出：  
  - IllegalArgumentException：如果代理类型无效或为 null。  
  - SecurityException：如果存在安全管理器并且拒绝连接到代理的权限。
  
  源码：
  ```
  public Socket(Proxy proxy) {
        // 作为一种安全措施，创建 Proxy 的副本。
        if (proxy == null) {
            throw new IllegalArgumentException("Invalid Proxy");
        }
        Proxy p = proxy == Proxy.NO_PROXY ? Proxy.NO_PROXY
                                          : sun.net.ApplicationProxy.create(proxy);
        Proxy.Type type = p.type();
        if (type == Proxy.Type.SOCKS || type == Proxy.Type.HTTP) {
            SecurityManager security = System.getSecurityManager();
            InetSocketAddress epoint = (InetSocketAddress) p.address();
            if (epoint.getAddress() != null) {
                checkAddress (epoint.getAddress(), "Socket");
            }
            if (security != null) {
                if (epoint.isUnresolved())
                    epoint = new InetSocketAddress(epoint.getHostName(), epoint.getPort());
                if (epoint.isUnresolved())
                    security.checkConnect(epoint.getHostName(), epoint.getPort());
                else
                    security.checkConnect(epoint.getAddress().getHostAddress(),
                                  epoint.getPort());
            }
            impl = type == Proxy.Type.SOCKS ? new SocksSocketImpl(p)
                                            : new HttpConnectSocketImpl(p);
            impl.setSocket(this);
        } else {
            if (p == Proxy.NO_PROXY) {
                if (factory == null) {
                    impl = new PlainSocketImpl(false);
                    impl.setSocket(this);
                } else
                    setImpl();
            } else
                throw new IllegalArgumentException("Invalid Proxy");
        }
    }
  ```
- 2.1.3 `protected Socket(SocketImpl impl) ` 
  创建一个未连接的套接字，使用用户指定的 SocketImpl。

  参数：  
  - impl：一个 SocketImpl 实例，子类希望在该套接字上使用。
  
  抛出：
  - SocketException：如果底层协议（如 TCP 协议）中出现错误。

  源码:
  ```
  protected Socket(SocketImpl impl) throws SocketException {
        this(checkPermission(impl), impl);
    }
  ```
  checkPermission源码:
  ```
  private static Void checkPermission(SocketImpl impl) {
        if (impl == null) {
            return null;
        }
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(SecurityConstants.SET_SOCKETIMPL_PERMISSION);
        }
        return null;
    }
  ```

- 2.1.4 `private Socket(Void ignore, SocketImpl impl)`  
  用于实际的内部初始化，并确保 Socket 对象正确配置。  
  源码:
  ```
  private Socket(Void ignore, SocketImpl impl) {
        if (impl != null) {
            this.impl = impl;
            checkOldImpl();
            impl.setSocket(this);
        }
    }
  ```

- 2.1.5 `public Socket(String host, int port)`
  创建一个流套接字并将其连接到指定主机上的指定端口号。  
  如果指定的主机为 null，则相当于指定地址为 InetAddress.getByName(null)。换句话说，这相当于指定环回接口的地址。  
  如果应用程序指定了服务器套接字工厂，则调用该工厂的 createSocketImpl 方法来创建实际的套接字实现。否则，将创建一个“普通”套接字。  
  如果存在安全管理器，则其 checkConnect 方法会以主机地址和端口号作为参数被调用。这可能会导致 SecurityException 异常。  
  参数：
  - host：主机名，或为环回地址的 null。
  - port：端口号。
 
  抛出：
  - UnknownHostException：如果无法确定主机的 IP 地址。
  - IOException：如果在创建套接字时发生 I/O 错误。
  - SecurityException：如果存在安全管理器并且其 checkConnect 方法不允许此操作。
  - IllegalArgumentException：如果端口参数超出有效端口值的指定范围（0 到 65535 之间，包括这两个值）。
 
  源码:
  ```
  public Socket(String host, int port)
        throws UnknownHostException, IOException
    {
        this(host != null ? new InetSocketAddress(host, port) :
             new InetSocketAddress(InetAddress.getByName(null), port),
             (SocketAddress) null, true);
    }
  ```

- 2.1.6 `public Socket(InetAddress address, int port)`  
  创建一个流套接字并将其连接到指定 IP 地址的指定端口号。  
  如果应用程序指定了套接字工厂，则调用该工厂的 createSocketImpl 方法来创建实际的套接字实现。否则，将创建一个“普通”套接字。  
  如果存在安全管理器，则其 checkConnect 方法会以主机地址和端口号作为参数被调用。这可能会导致 SecurityException 异常。  
  参数：  
    - address：IP 地址。
    - port：端口号。
   
  抛出：
    - IOException：如果在创建套接字时发生 I/O 错误。
    - SecurityException：如果存在安全管理器并且其 checkConnect 方法不允许此操作。
    - IllegalArgumentException：如果端口参数超出有效端口值的指定范围（0 到 65535 之间，包括这两个值）。
    - NullPointerException：如果地址为 null。
 
  源码:
  ```
  public Socket(InetAddress address, int port) throws IOException {
        this(address != null ? new InetSocketAddress(address, port) : null,
             (SocketAddress) null, true);
    }
  ```

- 2.1.7 `public Socket(String host, int port, InetAddress localAddr,int localPort)`  
  创建一个套接字并将其连接到指定远程主机上的指定远程端口。套接字还将绑定（bind）到提供的本地地址和端口。  
  如果指定的主机为 null，则相当于指定地址为 InetAddress.getByName(null)。换句话说，这相当于指定环回接口的地址。  
  本地端口号为零将使系统在绑定操作中选择一个空闲端口。  
  如果存在安全管理器，则其 checkConnect 方法会以主机地址和端口号作为参数被调用。这可能会导致 SecurityException 异常。  
  参数：
    - host：远程主机的名称，或为环回地址的 null。
    - port：远程端口。
    - localAddr：套接字绑定的本地地址，或为 anyLocal 地址的 null。
    - localPort：套接字绑定的本地端口，或为系统选择空闲端口的零。
 
  抛出：
    - IOException：如果在创建套接字时发生 I/O 错误。
    - SecurityException：如果存在安全管理器并且其 checkConnect 方法不允许连接到目标地址，或者其 checkListen 方法不允许绑定到本地端口。
    - IllegalArgumentException：如果端口参数或本地端口参数超出有效端口值的指定范围（0 到 65535 之间，包括这两个值）。
 
  源码:
  ```
  public Socket(String host, int port, InetAddress localAddr,
                  int localPort) throws IOException {
        this(host != null ? new InetSocketAddress(host, port) :
               new InetSocketAddress(InetAddress.getByName(null), port),
             new InetSocketAddress(localAddr, localPort), true);
    }
  ```

- 2.1.8 `public Socket(InetAddress address, int port, InetAddress localAddr,int localPort)`  
  创建一个套接字并将其连接到指定远程地址的指定远程端口。套接字还将绑定（bind）到提供的本地地址和端口。  
  如果指定的本地地址为 null，则相当于指定地址为 AnyLocal 地址（参见 InetAddress.isAnyLocalAddress()）。  
  本地端口号为零将使系统在绑定操作中选择一个空闲端口。  
  如果存在安全管理器，则其 checkConnect 方法会以主机地址和端口号作为参数被调用。这可能会导致 SecurityException 异常。  
  参数：
    - address：远程地址。
    - port：远程端口。
    - localAddr：套接字绑定的本地地址，或为 anyLocal 地址的 null。
    - localPort：套接字绑定的本地端口，或为系统选择空闲端口的零。
  
  抛出：
    - IOException：如果在创建套接字时发生 I/O 错误。
    - SecurityException：如果存在安全管理器并且其 checkConnect 方法不允许连接到目标地址，或者其 checkListen 方法不允许绑定到本地端口。
    - IllegalArgumentException：如果端口参数或本地端口参数超出有效端口值的指定范围（0 到 65535 之间，包括这两个值）。
    - NullPointerException：如果地址为 null。
 
- 2.1.9 `public Socket(String host, int port, boolean stream)`  已弃用。  
  创建一个流套接字并将其连接到指定主机上的指定端口号。  
  如果指定的主机为 null，则相当于指定地址为 InetAddress.getByName(null)。换句话说，这相当于指定环回接口的地址。  
  如果流参数为 true，则创建一个流套接字。如果流参数为 false，则创建一个数据报套接字。  
  如果应用程序指定了服务器套接字工厂，则调用该工厂的 createSocketImpl 方法来创建实际的套接字实现。否则，将创建一个“普通”套接字。  
  如果存在安全管理器，则其 checkConnect 方法会以主机地址和端口号作为参数被调用。这可能会导致 SecurityException 异常。  
  如果使用 UDP 套接字，则与 TCP/IP 相关的套接字选项将不适用。  
  已过时。请改用 DatagramSocket 用于 UDP 传输。  
  参数：
    - host：主机名，或为环回地址的 null。
    - port：端口号。
    - stream：一个布尔值，指示这是一个流套接字还是数据报套接字。
  
  抛出：
    - IOException：如果在创建套接字时发生 I/O 错误。
    - SecurityException：如果存在安全管理器并且其 checkConnect 方法不允许此操作。
    - IllegalArgumentException：如果端口参数超出有效端口值的指定范围（0 到 65535 之间，包括这两个值）。

  源码:
  ```
  public Socket(String host, int port, boolean stream) throws IOException {
        this(host != null ? new InetSocketAddress(host, port) :
               new InetSocketAddress(InetAddress.getByName(null), port),
             (SocketAddress) null, stream);
    }
  ```

- 2.1.10 `public Socket(InetAddress host, int port, boolean stream)` 已弃用。 
- 2.1.11 `private Socket(SocketAddress address, SocketAddress localAddr,boolean stream)`
  初始化和配置 Socket 对象，使其能够与指定的远程地址进行通信，同时允许指定本地绑定地址。它处理了所有必要的步骤，包括设置实现、检查参数、创建底层实现、绑定和连接，并在出现异常时进行清理和错误处理。  
  
  1.调用 setImpl() 方法：  
    &nbsp;&nbsp;这一步设置 Socket 的 SocketImpl，即底层的实际实现。它可能选择默认的实现或用户自定义的实现。这个方法是确保 Socket 对象具有一个正确配置的 SocketImpl。  
  2.检查地址是否为空：  
    &nbsp;&nbsp;如果 address 参数为 null，抛出 NullPointerException。这确保了必须提供一个有效的远程地址。  
  3.创建底层实现：  
    &nbsp;&nbsp;调用 createImpl(stream) 方法，基于 stream 参数创建底层实现。stream 参数指示是否创建一个流（TCP）Socket 或数据报（UDP）Socket。  
  4.绑定本地地址（如果提供）：  
    &nbsp;&nbsp;如果 localAddr 参数不为 null，调用 bind(localAddr) 方法将 Socket 绑定到指定的本地地址。这允许用户指定从哪个本地网络接口和端口发起连接。  
  5.连接远程地址：  
    &nbsp;&nbsp;调用 connect(address) 方法将 Socket 连接到指定的远程地址。这是实际发起连接的步骤。  
  6.错误处理：  
    &nbsp;&nbsp;捕获 IOException、IllegalArgumentException 和 SecurityException 异常。  
    &nbsp;&nbsp;如果捕获到这些异常，尝试关闭 Socket。  
    &nbsp;&nbsp;如果在关闭过程中再次遇到 IOException，将其添加为抑制异常。  
    &nbsp;&nbsp;重新抛出原始异常。

## 2.2 `createImpl(boolean stream)`
  创建套接字实现。
  参数：
  - stream：一个布尔值，true 表示 TCP 套接字，false 表示 UDP 套接字。
  
  抛出：
  - IOException：如果创建失败。
 
  ```
  void createImpl(boolean stream) throws SocketException {
        if (impl == null)
            setImpl();
        try {
            impl.create(stream);
            created = true;
        } catch (IOException e) {
            throw new SocketException(e.getMessage());
        }
    }
  ```

## 2.3 `private void checkOldImpl()`
```
private void checkOldImpl() {
        if (impl == null)
            return;
        // SocketImpl.connect() is a protected method, therefore we need to use
        // getDeclaredMethod, therefore we need permission to access the member

        oldImpl = AccessController.doPrivileged
                                (new PrivilegedAction<Boolean>() {
            public Boolean run() {
                Class<?> clazz = impl.getClass();
                while (true) {
                    try {
                        clazz.getDeclaredMethod("connect", SocketAddress.class, int.class);
                        return Boolean.FALSE;
                    } catch (NoSuchMethodException e) {
                        clazz = clazz.getSuperclass();
                        // java.net.SocketImpl class will always have this abstract method.
                        // If we have not found it by now in the hierarchy then it does not
                        // exist, we are an old style impl.
                        if (clazz.equals(java.net.SocketImpl.class)) {
                            return Boolean.TRUE;
                        }
                    }
                }
            }
        });
    }
```

## 2.4 `setImpl()`
将 `impl` 设置为系统默认类型的 `SocketImpl`。
```
void setImpl() {
        if (factory != null) {
            impl = factory.createSocketImpl();
            checkOldImpl();
        } else {
            // No need to do a checkOldImpl() here, we know it's an up to date
            // SocketImpl!
            impl = new SocksSocketImpl();
        }
        if (impl != null)
            impl.setSocket(this);
    }
```

## 2.5 `SocketImpl getImpl()`
  获取与此套接字关联的 `SocketImpl`，如果需要的话则创建它。  
  返回：
  - 与该 ServerSocket 关联的 SocketImpl。
  
  抛出：
  - `SocketException`：如果创建失败。
  
  ```
  SocketImpl getImpl() throws SocketException {
        if (!created)
            createImpl(true);
        return impl;
    }
  ```

## 2.6 `public void connect(SocketAddress endpoint)`  
  将此套接字连接到服务器。  
  参数：
  - `endpoint`：SocketAddress
  
  抛出：
  - `IOException`：如果在连接过程中发生错误。
  - `IllegalBlockingModeException`：如果此套接字具有关联的通道，并且该通道处于非阻塞模式。
  - `IllegalArgumentException`：如果 `endpoint` 为 null 或是此套接字不支持的 SocketAddress 子类。

  ```
  public void connect(SocketAddress endpoint) throws IOException {
        connect(endpoint, 0);
    }
  ```

## 2.7 `public void connect(SocketAddress endpoint, int timeout) `  
  将此套接字连接到具有指定超时值的服务器。超时值为零被解释为无限超时。连接将会阻塞，直到建立连接或发生错误。
  
  参数：
  - `endpoint`：SocketAddress
  - `timeout`：超时值，以毫秒为单位。
  
  抛出：
  - `IOException`：如果在连接过程中发生错误。
  - `SocketTimeoutException`：如果超时在连接之前到期。
  - `IllegalBlockingModeException`：如果此套接字具有关联的通道，并且该通道处于非阻塞模式。
  - `IllegalArgumentException`：如果 `endpoint` 为 null 或是此套接字不支持的 SocketAddress 子类。
  ```
  public void connect(SocketAddress endpoint, int timeout) throws IOException {
        if (endpoint == null)
            throw new IllegalArgumentException("connect: The address can't be null");

        if (timeout < 0)
          throw new IllegalArgumentException("connect: timeout can't be negative");

        if (isClosed())
            throw new SocketException("Socket is closed");

        if (!oldImpl && isConnected())
            throw new SocketException("already connected");

        if (!(endpoint instanceof InetSocketAddress))
            throw new IllegalArgumentException("Unsupported address type");

        InetSocketAddress epoint = (InetSocketAddress) endpoint;
        InetAddress addr = epoint.getAddress ();
        int port = epoint.getPort();
        checkAddress(addr, "connect");

        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            if (epoint.isUnresolved())
                security.checkConnect(epoint.getHostName(), port);
            else
                security.checkConnect(addr.getHostAddress(), port);
        }
        if (!created)
            createImpl(true);
        if (!oldImpl)
            impl.connect(epoint, timeout);
        else if (timeout == 0) {
            if (epoint.isUnresolved())
                impl.connect(addr.getHostName(), port);
            else
                impl.connect(addr, port);
        } else
            throw new UnsupportedOperationException("SocketImpl.connect(addr, timeout)");
        connected = true;
        /*
         * If the socket was not bound before the connect, it is now because
         * the kernel will have picked an ephemeral port & a local address
         */
        bound = true;
    }
  ```

## 2.8 `public void bind(SocketAddress bindpoint)`
将套接字绑定到本地地址。

如果地址为 null，则系统将选择一个临时端口和一个有效的本地地址来绑定套接字。

参数：
- `bindpoint`：要绑定到的 SocketAddress。

抛出：
- `IOException`：如果绑定操作失败，或者套接字已经绑定。
- `IllegalArgumentException`：如果 bindpoint 是此套接字不支持的 SocketAddress 子类。
- `SecurityException`：如果存在安全管理器并且其 checkListen 方法不允许绑定到本地端口。

```
public void bind(SocketAddress bindpoint) throws IOException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        if (!oldImpl && isBound())
            throw new SocketException("Already bound");

        if (bindpoint != null && (!(bindpoint instanceof InetSocketAddress)))
            throw new IllegalArgumentException("Unsupported address type");
        InetSocketAddress epoint = (InetSocketAddress) bindpoint;
        if (epoint != null && epoint.isUnresolved())
            throw new SocketException("Unresolved address");
        if (epoint == null) {
            epoint = new InetSocketAddress(0);
        }
        InetAddress addr = epoint.getAddress();
        int port = epoint.getPort();
        checkAddress (addr, "bind");
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkListen(port);
        }
        getImpl().bind (addr, port);
        bound = true;
    }
```

## 2.9 `private void checkAddress (InetAddress addr, String op)`
参数:

- `InetAddress addr` 是要检查的地址对象。
- `String op` 是一个操作字符串，用于在抛出异常时提供更具体的错误信息。

这个方法的作用是检查传入的 `InetAddress` 对象是否为空，以及它的类型是否为 IPv4 或 IPv6。具体来说：

1. 如果 `addr` 为 `null`，则直接返回，不进行后续的检查。这是为了避免在传入空地址时继续执行后续的检查。

2. 如果 `addr` 不是 `Inet4Address` 或 `Inet6Address` 的实例，即不是 IPv4 或 IPv6 地址类型，那么就会抛出 `IllegalArgumentException` 异常，异常的消息是 `op + ": invalid address type"`。
   这个异常信息中的 `op` 是传入的操作字符串，用于指明发生错误的操作是什么。

这个方法的目的是确保传入的地址对象是有效的 IPv4 或 IPv6 地址类型。如果不是这两种类型，则抛出异常，表示参数错误。
```
private void checkAddress (InetAddress addr, String op) {
        if (addr == null) {
            return;
        }
        if (!(addr instanceof Inet4Address || addr instanceof Inet6Address)) {
            throw new IllegalArgumentException(op + ": invalid address type");
        }
    }
```

## 2.10 `final void postAccept() `
在 accept() 调用之后设置标志。
```
final void postAccept() {
        connected = true;
        created = true;
        bound = true;
    }
```

## 2.11 ` void setCreated() {created = true;}`
## 2.12 `void setBound() {bound = true;}`
## 2.13 `void setConnected() {connected = true;}`
## 2.14 `public InetAddress getInetAddress()`
返回套接字连接的地址。

如果套接字在关闭之前已连接，则此方法将在套接字关闭后继续返回连接的地址。

返回：
- 此套接字连接的远程 IP 地址，如果套接字未连接则返回 null。

```
public InetAddress getInetAddress() {
        if (!isConnected())
            return null;
        try {
            return getImpl().getInetAddress();
        } catch (SocketException e) {
        }
        return null;
    }
```

## 2.15 `public InetAddress getLocalAddress()`
获取套接字绑定的本地地址。

如果设置了安全管理器，则会调用其 checkConnect 方法，将本地地址和 -1 作为其参数，以查看是否允许该操作。如果不允许该操作，则返回环回地址。

返回：
- 套接字绑定的本地地址，如果被安全管理器拒绝则返回环回地址，如果套接字已关闭或尚未绑定则返回通配符地址。

```
public InetAddress getLocalAddress() {
        // This is for backward compatibility
        if (!isBound())
            return InetAddress.anyLocalAddress();
        InetAddress in = null;
        try {
            in = (InetAddress) getImpl().getOption(SocketOptions.SO_BINDADDR);
            SecurityManager sm = System.getSecurityManager();
            if (sm != null)
                sm.checkConnect(in.getHostAddress(), -1);
            if (in.isAnyLocalAddress()) {
                in = InetAddress.anyLocalAddress();
            }
        } catch (SecurityException e) {
            in = InetAddress.getLoopbackAddress();
        } catch (Exception e) {
            in = InetAddress.anyLocalAddress(); // "0.0.0.0"
        }
        return in;
    }
```

## 2.16 `public int getPort()`
返回套接字连接的远程端口号。

如果套接字在关闭之前已连接，则此方法将在套接字关闭后继续返回连接的端口号。

返回：
- 套接字连接的远程端口号，如果套接字尚未连接则返回 0。

```
public int getPort() {
        if (!isConnected())
            return 0;
        try {
            return getImpl().getPort();
        } catch (SocketException e) {
            // Shouldn't happen as we're connected
        }
        return -1;
    }
```

## 2.17 `public int getLocalPort()`
返回套接字绑定的本地端口号。

如果套接字在关闭之前已绑定，则此方法将在套接字关闭后继续返回绑定的本地端口号。

返回：
- 套接字绑定的本地端口号，如果套接字尚未绑定则返回 -1。

```
public int getLocalPort() {
        if (!isBound())
            return -1;
        try {
            return getImpl().getLocalPort();
        } catch(SocketException e) {
            // shouldn't happen as we're bound
        }
        return -1;
    }
```

## 2.18 `public SocketAddress getRemoteSocketAddress()`
返回此套接字连接的端点地址，如果未连接则返回 null。

如果套接字在关闭之前已连接，则此方法将在套接字关闭后继续返回连接的地址。

返回：
- 代表此套接字远程端点的 SocketAddress，如果尚未连接则返回 null。

```
public SocketAddress getRemoteSocketAddress() {
        if (!isConnected())
            return null;
        return new InetSocketAddress(getInetAddress(), getPort());
    }
```

## 2.19 ` public SocketAddress getLocalSocketAddress()`
返回此套接字绑定到的端点地址。

如果绑定到由 InetSocketAddress 表示的端点的套接字关闭，则此方法在套接字关闭后继续返回一个 InetSocketAddress。在这种情况下，返回的 InetSocketAddress 的地址是通配符地址，其端口是绑定到的本地端口。

如果设置了安全管理器，则会调用其 checkConnect 方法，将本地地址和 -1 作为其参数，以查看是否允许该操作。如果不允许该操作，则返回代表环回地址和套接字绑定到的本地端口的 SocketAddress。

返回：
- 代表此套接字本地端点的 SocketAddress，如果被安全管理器拒绝则返回代表环回地址的 SocketAddress，如果套接字尚未绑定则返回 null。

```
public SocketAddress getLocalSocketAddress() {
        if (!isBound())
            return null;
        return new InetSocketAddress(getLocalAddress(), getLocalPort());
    }
```

## 2.20 `public SocketChannel getChannel() {return null;}`
返回与此套接字关联的唯一 SocketChannel 对象（如果有的话）。

只有当通道本身是通过 SocketChannel.open 或 ServerSocketChannel.accept 方法创建时，套接字才会具有通道。

返回：

与此套接字关联的套接字通道，如果此套接字不是为通道创建的，则返回 null。

## 2.21 `public InputStream getInputStream()`
返回此套接字的输入流。

如果此套接字具有关联的通道，则生成的输入流将所有操作委托给该通道。如果通道处于非阻塞模式，则输入流的读取操作将抛出 `java.nio.channels.IllegalBlockingModeException`。

在异常情况下，远程主机或网络软件可能会中断底层连接（例如在 TCP 连接情况下的连接重置）。当网络软件检测到连接中断时，返回的输入流将应用以下规则：
- 网络软件可能会丢弃由套接字缓冲的字节。未被网络软件丢弃的字节可以使用 `read` 读取。
- 如果套接字上没有缓冲的字节，或者所有缓冲的字节已被 `read` 消费，则所有后续对 `read` 的调用将抛出 `IOException`。
- 如果套接字上没有缓冲的字节，并且套接字未通过 `close` 关闭，则 `available` 将返回 0。
- 关闭返回的 InputStream 将关闭关联的套接字。

返回：
- 用于从此套接字读取字节的输入流。

抛出：
- `IOException`：如果在创建输入流时发生 I/O 错误，套接字已关闭，套接字未连接，或套接字输入已使用 `shutdownInput()` 关闭。

```
public InputStream getInputStream() throws IOException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        if (!isConnected())
            throw new SocketException("Socket is not connected");
        if (isInputShutdown())
            throw new SocketException("Socket input is shutdown");
        final Socket s = this;
        InputStream is = null;
        try {
            is = AccessController.doPrivileged(
                new PrivilegedExceptionAction<InputStream>() {
                    public InputStream run() throws IOException {
                        return impl.getInputStream();
                    }
                });
        } catch (java.security.PrivilegedActionException e) {
            throw (IOException) e.getException();
        }
        return is;
    }
```

## 2.21 `public OutputStream getOutputStream()`
返回此套接字的输出流。

如果此套接字具有关联的通道，则生成的输出流将所有操作委托给该通道。如果通道处于非阻塞模式，则输出流的写操作将抛出 `java.nio.channels.IllegalBlockingModeException`。

关闭返回的 OutputStream 将关闭关联的套接字。

返回：
- 用于向此套接字写入字节的输出流。

抛出：
- `IOException`：如果在创建输出流时发生 I/O 错误或套接字未连接。
```
public OutputStream getOutputStream() throws IOException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        if (!isConnected())
            throw new SocketException("Socket is not connected");
        if (isOutputShutdown())
            throw new SocketException("Socket output is shutdown");
        final Socket s = this;
        OutputStream os = null;
        try {
            os = AccessController.doPrivileged(
                new PrivilegedExceptionAction<OutputStream>() {
                    public OutputStream run() throws IOException {
                        return impl.getOutputStream();
                    }
                });
        } catch (java.security.PrivilegedActionException e) {
            throw (IOException) e.getException();
        }
        return os;
    }
```

## 2.22 `public void setTcpNoDelay(boolean on)`
启用/禁用 TCP_NODELAY（禁用/启用 Nagle 算法）。

参数：

on：true 表示启用 TCP_NODELAY，false 表示禁用。  
抛出：

SocketException：如果底层协议（例如 TCP 错误）存在错误。

```
   public void setTcpNoDelay(boolean on) throws SocketException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        getImpl().setOption(SocketOptions.TCP_NODELAY, Boolean.valueOf(on));
    }
```

## 2.23 `public boolean getTcpNoDelay()`
测试是否启用了 TCP_NODELAY。

返回：
- 一个布尔值，指示是否启用了 TCP_NODELAY。

抛出：
- `SocketException`：如果底层协议（例如 TCP 错误）存在错误。

```
public boolean getTcpNoDelay() throws SocketException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        return ((Boolean) getImpl().getOption(SocketOptions.TCP_NODELAY)).booleanValue();
    }
```

## 2.24 `public void setSoLinger(boolean on, int linger)`
启用/禁用 SO_LINGER 并指定延迟时间（以秒为单位）。最大超时值取决于平台。该设置仅影响套接字关闭。

参数：
- `on`：是否启用延迟。
- `linger`：如果 `on` 为 true，延迟多长时间。

抛出：
- `SocketException`：如果底层协议（例如 TCP 错误）存在错误。
- `IllegalArgumentException`：如果延迟值为负数。

```
public void setSoLinger(boolean on, int linger) throws SocketException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        if (!on) {
            getImpl().setOption(SocketOptions.SO_LINGER, new Boolean(on));
        } else {
            if (linger < 0) {
                throw new IllegalArgumentException("invalid value for SO_LINGER");
            }
            if (linger > 65535)
                linger = 65535;
            getImpl().setOption(SocketOptions.SO_LINGER, new Integer(linger));
        }
    }
```

## 2.25 `public int getSoLinger()`
返回 SO_LINGER 的设置。-1 表示该选项已禁用。该设置仅影响套接字关闭。

返回：
- SO_LINGER 的设置值。

抛出：
- `SocketException`：如果底层协议（例如 TCP 错误）存在错误。

```
public int getSoLinger() throws SocketException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        Object o = getImpl().getOption(SocketOptions.SO_LINGER);
        if (o instanceof Integer) {
            return ((Integer) o).intValue();
        } else {
            return -1;
        }
    }
```

## 2.26 `public void sendUrgentData (int data)`
在套接字上发送一个字节的紧急数据。要发送的字节是 `data` 参数的最低八位。紧急字节将在任何先前写入套接字 OutputStream 的数据之后以及任何将来写入 OutputStream 的数据之前发送。

参数：
- `data`：要发送的数据字节

抛出：
- `IOException`：如果在发送数据时发生错误。

```
public void sendUrgentData (int data) throws IOException  {
        if (!getImpl().supportsUrgentData ()) {
            throw new SocketException ("Urgent data not supported");
        }
        getImpl().sendUrgentData (data);
    }
```

## 2.27 `public void setOOBInline(boolean on) `
启用/禁用 SO_OOBINLINE（接收 TCP 紧急数据）。默认情况下，此选项被禁用，并且在套接字上接收到的 TCP 紧急数据会被静默丢弃。如果用户希望接收紧急数据，则必须启用此选项。启用后，紧急数据将与正常数据内联接收。

请注意，对于处理传入的紧急数据，仅提供有限的支持。特别是，没有传入紧急数据的通知，除非由更高层次的协议提供，否则无法区分正常数据和紧急数据。

参数：
- `on`：true 表示启用 SO_OOBINLINE，false 表示禁用。

抛出：
- `SocketException`：如果底层协议（例如 TCP 错误）存在错误。
```
public void setOOBInline(boolean on) throws SocketException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        getImpl().setOption(SocketOptions.SO_OOBINLINE, Boolean.valueOf(on));
    }
```

## 2.28  `public boolean getOOBInline()`
测试是否启用了 SO_OOBINLINE。

返回：
- 一个布尔值，指示是否启用了 SO_OOBINLINE。

抛出：
- `SocketException`：如果底层协议（例如 TCP 错误）存在错误。

```
public boolean getOOBInline() throws SocketException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        return ((Boolean) getImpl().getOption(SocketOptions.SO_OOBINLINE)).booleanValue();
    }
```

## 2.29 `public synchronized void setSoTimeout(int timeout)`
启用/禁用指定超时时间（以毫秒为单位）的 SO_TIMEOUT。将此选项设置为非零超时时间后，与此 Socket 关联的 InputStream 上的 read() 调用将仅阻塞这段时间。如果超时时间到期，将会引发 `java.net.SocketTimeoutException`，但套接字仍然有效。必须在进入阻塞操作之前启用该选项才能生效。超时时间必须大于 0。超时时间为零被解释为无限超时。

参数：
- `timeout`：指定的超时时间，以毫秒为单位。

抛出：
- `SocketException`：如果底层协议（例如 TCP 错误）存在错误。

```
public synchronized void setSoTimeout(int timeout) throws SocketException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        if (timeout < 0)
          throw new IllegalArgumentException("timeout can't be negative");

        getImpl().setOption(SocketOptions.SO_TIMEOUT, new Integer(timeout));
    }
```

## 2.29 `public synchronized int getSoTimeout()`
返回 SO_TIMEOUT 的设置。返回值为 0 表示该选项已禁用（即无限超时）。

返回：
- SO_TIMEOUT 的设置值。

抛出：
- `SocketException`：如果底层协议（例如 TCP 错误）存在错误。

```
public synchronized int getSoTimeout() throws SocketException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        Object o = getImpl().getOption(SocketOptions.SO_TIMEOUT);
        /* extra type safety */
        if (o instanceof Integer) {
            return ((Integer) o).intValue();
        } else {
            return 0;
        }
    }
```

## 2.30 `public synchronized void setSendBufferSize(int size)`
将此套接字的 SO_SNDBUF 选项设置为指定的值。SO_SNDBUF 选项被平台的网络代码用作对底层网络 I/O 缓冲区大小的提示。

因为 SO_SNDBUF 是一个提示，所以想要验证缓冲区设置为多大的应用程序应调用 getSendBufferSize()。

参数：
- `size`：要设置的发送缓冲区大小。该值必须大于 0。

抛出：
- `SocketException`：如果底层协议（例如 TCP 错误）存在错误。
- `IllegalArgumentException`：如果值为 0 或为负数。

```
public synchronized void setSendBufferSize(int size)
    throws SocketException{
        if (!(size > 0)) {
            throw new IllegalArgumentException("negative send size");
        }
        if (isClosed())
            throw new SocketException("Socket is closed");
        getImpl().setOption(SocketOptions.SO_SNDBUF, new Integer(size));
    }
```

## 2.31 `public synchronized int getSendBufferSize()`
获取此套接字的 SO_SNDBUF 选项值，即平台在此套接字上用于输出的缓冲区大小。

返回：
- 此套接字的 SO_SNDBUF 选项值。

抛出：
- `SocketException`：如果底层协议（例如 TCP 错误）存在错误。
```
public synchronized int getSendBufferSize() throws SocketException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        int result = 0;
        Object o = getImpl().getOption(SocketOptions.SO_SNDBUF);
        if (o instanceof Integer) {
            result = ((Integer)o).intValue();
        }
        return result;
    }
```

## 2.32  `public synchronized void setReceiveBufferSize(int size)`
将此套接字的 SO_RCVBUF 选项设置为指定的值。SO_RCVBUF 选项被平台的网络代码用作对底层网络 I/O 缓冲区大小的提示。

增加接收缓冲区大小可以提高高流量连接的网络 I/O 性能，而减小它可以帮助减少传入数据的积压。

因为 SO_RCVBUF 是一个提示，所以想要验证缓冲区设置为多大的应用程序应调用 getReceiveBufferSize()。

SO_RCVBUF 的值也用于设置发送给远程对等方的 TCP 接收窗口。通常，窗口大小可以在套接字连接时随时修改。但是，如果需要一个大于 64K 的接收窗口，则必须在套接字连接到远程对等方之前进行请求。有两种情况需要注意：
- 对于从 ServerSocket 接受的套接字，必须在将 ServerSocket 绑定到本地地址之前通过调用 ServerSocket.setReceiveBufferSize(int) 来执行此操作。
- 对于客户端套接字，在将套接字连接到其远程对等方之前，必须调用 setReceiveBufferSize()。

参数：
- `size`：要设置的接收缓冲区大小。该值必须大于 0。

抛出：
- `IllegalArgumentException`：如果值为 0 或为负数。
- `SocketException`：如果底层协议（例如 TCP 错误）存在错误。
```
public synchronized void setReceiveBufferSize(int size)
    throws SocketException{
        if (size <= 0) {
            throw new IllegalArgumentException("invalid receive size");
        }
        if (isClosed())
            throw new SocketException("Socket is closed");
        getImpl().setOption(SocketOptions.SO_RCVBUF, new Integer(size));
    }
```

## 2.33 `public synchronized int getReceiveBufferSize()`
获取此套接字的 SO_RCVBUF 选项值，即平台在此套接字上用于输入的缓冲区大小。

返回：
- 此套接字的 SO_RCVBUF 选项值。

抛出：
- `SocketException`：如果底层协议（例如 TCP 错误）存在错误。
```
public synchronized int getReceiveBufferSize()
    throws SocketException{
        if (isClosed())
            throw new SocketException("Socket is closed");
        int result = 0;
        Object o = getImpl().getOption(SocketOptions.SO_RCVBUF);
        if (o instanceof Integer) {
            result = ((Integer)o).intValue();
        }
        return result;
    }
```

## 2.34 `public void setKeepAlive(boolean on)`
启用/禁用 SO_KEEPALIVE。

参数：
- `on`：是否打开套接字的保持活动功能。

抛出：
- `SocketException`：如果底层协议（例如 TCP 错误）存在错误。
```
public void setKeepAlive(boolean on) throws SocketException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        getImpl().setOption(SocketOptions.SO_KEEPALIVE, Boolean.valueOf(on));
    }
```

## 2.35 `public boolean getKeepAlive()`
测试是否启用了 SO_KEEPALIVE。

返回：
- 一个布尔值，指示是否启用了 SO_KEEPALIVE。

抛出：
- `SocketException`：如果底层协议（例如 TCP 错误）存在错误。
```
public boolean getKeepAlive() throws SocketException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        return ((Boolean) getImpl().getOption(SocketOptions.SO_KEEPALIVE)).booleanValue();
    }
```

## 2.36 `public void setTrafficClass(int tc)`
设置从此 Socket 发送的数据包的 IP 头中的流量类别或服务类型字节。由于底层网络实现可能会忽略此值，应用程序应将其视为提示。

`tc` 必须在范围 0 <= tc <= 255 内，否则将抛出 `IllegalArgumentException`。

备注：
- 对于 IPv4，该值是一个整数，其最低有效的 8 位表示套接字发送的 IP 数据包中 TOS 字节的值。RFC 1349 定义了 TOS 值如下：
  - IPTOS_LOWCOST (0x02)
  - IPTOS_RELIABILITY (0x04)
  - IPTOS_THROUGHPUT (0x08)
  - IPTOS_LOWDELAY (0x10)
  最后一个低位总是被忽略，因为这对应于 MBZ（必须为零）位。设置优先级字段中的位可能会导致 `SocketException`，指示不允许该操作。
- 如 RFC 1122 第 4.2.4.2 节所示，符合要求的 TCP 实现应该允许应用程序在连接的生命周期内更改 TOS 字段，但不是必须的。因此，在 TCP 连接建立后是否可以更改服务类型字段取决于底层平台中的实现。应用程序不应假设它们可以在连接后更改 TOS 字段。
- 对于 IPv6，`tc` 是要放入 IP 头的 sin6_flowinfo 字段的值。

参数：
- `tc`：位集的整数值。

抛出：
- `SocketException`：设置流量类别或服务类型时出现错误。
```
public void setTrafficClass(int tc) throws SocketException {
        if (tc < 0 || tc > 255)
            throw new IllegalArgumentException("tc is not in range 0 -- 255");

        if (isClosed())
            throw new SocketException("Socket is closed");
        try {
            getImpl().setOption(SocketOptions.IP_TOS, tc);
        } catch (SocketException se) {
            // not supported if socket already connected
            // Solaris returns error in such cases
            if(!isConnected())
                throw se;
        }
    }
```

## 2.37 `public int getTrafficClass()`
获取从此 Socket 发送的数据包的 IP 头中的流量类别或服务类型。

由于底层网络实现可能会忽略使用 setTrafficClass(int) 设置的流量类别或服务类型，因此此方法可能返回与之前使用 setTrafficClass(int) 方法在此 Socket 上设置的值不同的值。

返回：
- 已设置的流量类别或服务类型。

抛出：
- `SocketException`：获取流量类别或服务类型值时出现错误。
```
public int getTrafficClass() throws SocketException {
        return ((Integer) (getImpl().getOption(SocketOptions.IP_TOS))).intValue();
    }
```

## 2.38 `public void setReuseAddress(boolean on)`
启用/禁用 SO_REUSEADDR 套接字选项。

当 TCP 连接关闭时，该连接可能在关闭后的一段时间内保持在超时状态（通常称为 TIME_WAIT 状态或 2MSL 等待状态）。对于使用已知套接字地址或端口的应用程序，如果涉及该套接字地址或端口的连接处于超时状态，可能无法将套接字绑定到所需的 SocketAddress。

在使用 bind(SocketAddress) 绑定套接字之前启用 SO_REUSEADDR 允许套接字绑定，即使先前的连接处于超时状态。

创建套接字时，SO_REUSEADDR 的初始设置为禁用状态。

在套接字绑定（参见 isBound()）之后启用或禁用 SO_REUSEADDR 的行为未定义。

参数：
- `on` – 是否启用或禁用套接字选项。

抛出：
- `SocketException`：启用或禁用 SO_REUSEADDR 套接字选项时发生错误，或套接字已关闭。
```
public void setReuseAddress(boolean on) throws SocketException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        getImpl().setOption(SocketOptions.SO_REUSEADDR, Boolean.valueOf(on));
    }
```

## 2.39 `public boolean getReuseAddress()`
测试 SO_REUSEADDR 是否启用。

返回：
- 一个 boolean 值，指示 SO_REUSEADDR 是否启用。

抛出：
- `SocketException`：如果底层协议中存在错误，例如 TCP 错误。
```
public boolean getReuseAddress() throws SocketException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        return ((Boolean) (getImpl().getOption(SocketOptions.SO_REUSEADDR))).booleanValue();
    }
```

## 2.40 `public synchronized void close()`
关闭此套接字。

任何当前在此套接字上的 I/O 操作中被阻塞的线程将抛出 `SocketException`。

一旦套接字关闭，它将不可用于进一步的网络使用（即不能重新连接或重新绑定）。需要创建一个新套接字。

关闭此套接字还将关闭套接字的 `InputStream` 和 `OutputStream`。

如果此套接字有相关联的通道，则该通道也将关闭。

抛出：
- `IOException`：如果在关闭此套接字时发生 I/O 错误。
```
public synchronized void close() throws IOException {
        synchronized(closeLock) {
            if (isClosed())
                return;
            if (created)
                impl.close();
            closed = true;
        }
    }
```

## 2.41 `public void shutdownInput()`
将此套接字的输入流置于“流的末尾”。发送到套接字输入流一侧的任何数据将被确认，然后被静默丢弃。

如果在套接字上调用此方法后从套接字输入流读取，流的 `available` 方法将返回 0，其 `read` 方法将返回 -1（流的末尾）。

抛出：
- `IOException`：如果在关闭此套接字时发生 I/O 错误。
```
public void shutdownInput() throws IOException
    {
        if (isClosed())
            throw new SocketException("Socket is closed");
        if (!isConnected())
            throw new SocketException("Socket is not connected");
        if (isInputShutdown())
            throw new SocketException("Socket input is already shutdown");
        getImpl().shutdownInput();
        shutIn = true;
    }
```

## 2.42 `public void shutdownOutput()`
禁用此套接字的输出流。对于 TCP 套接字，任何先前写入的数据都将被发送，然后按照 TCP 的正常连接终止序列进行处理。如果在调用 `shutdownOutput()` 后向套接字输出流写入数据，流将抛出 `IOException`。

抛出：
- `IOException`：如果在关闭此套接字时发生 I/O 错误。
```
public void shutdownOutput() throws IOException
    {
        if (isClosed())
            throw new SocketException("Socket is closed");
        if (!isConnected())
            throw new SocketException("Socket is not connected");
        if (isOutputShutdown())
            throw new SocketException("Socket output is already shutdown");
        getImpl().shutdownOutput();
        shutOut = true;
    }
```

## 2.43 `public String toString()`
```
public String toString() {
        try {
            if (isConnected())
                return "Socket[addr=" + getImpl().getInetAddress() +
                    ",port=" + getImpl().getPort() +
                    ",localport=" + getImpl().getLocalPort() + "]";
        } catch (SocketException e) {
        }
        return "Socket[unconnected]";
    }
```

## 2.44 `public boolean isConnected()`
```
public boolean isConnected() {
        // Before 1.3 Sockets were always connected during creation
        return connected || oldImpl;
    }
```

## 2.45 `public boolean isBound()`
```
public boolean isBound() {
        // Before 1.3 Sockets were always bound during creation
        return bound || oldImpl;
    }
```

## 2.46 `public boolean isClosed()`
```
public boolean isClosed() {
        synchronized(closeLock) {
            return closed;
        }
    }
```

## 2.47 `public boolean isInputShutdown()`
```
public boolean isInputShutdown() {
        return shutIn;
    }
```

## 2.48 `public boolean isOutputShutdown() `
```
public boolean isOutputShutdown() {
        return shutOut;
    }
```

## 2.49 `private static SocketImplFactory factory = null;`

## 2.50 `public static synchronized void setSocketImplFactory(SocketImplFactory fac) `
设置应用程序的客户端套接字实现工厂。工厂只能被指定一次。  
当应用程序创建一个新的客户端套接字时，会调用套接字实现工厂的 `createSocketImpl` 方法来创建实际的套接字实现。  
将 `null` 传递给该方法是无操作，除非工厂已经设置。  
如果存在安全管理器，该方法首先调用安全管理器的 `checkSetFactory` 方法以确保允许操作。这可能会导致抛出 `SecurityException`。  

参数：
- `fac` – 所需的工厂。

抛出：
- `IOException`：如果在设置套接字工厂时发生 I/O 错误。
- `SocketException`：如果工厂已经定义。
- `SecurityException`：如果存在安全管理器并且其 `checkSetFactory` 方法不允许该操作。
```
public static synchronized void setSocketImplFactory(SocketImplFactory fac)
        throws IOException
    {
        if (factory != null) {
            throw new SocketException("factory already defined");
        }
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkSetFactory();
        }
        factory = fac;
    }
```

## 2.51 `public void setPerformancePreferences(int connectionTime, int latency,int bandwidth)`
设置此套接字的性能偏好。  
套接字默认使用 TCP/IP 协议。一些实现可能提供不同于 TCP/IP 的替代协议，这些协议具有不同的性能特征。此方法允许应用程序表达其偏好，以便在实现从可用协议中选择时进行权衡。  
性能偏好由三个整数描述，这些整数值表示短连接时间、低延迟和高带宽的相对重要性。整数的绝对值无关紧要；为了选择协议，只需比较这些值，较大的值表示更强的偏好。负值表示比正值优先级更低。如果应用程序更偏好短连接时间而不是低延迟和高带宽，例如，可以使用值（1，0，0）调用此方法。如果应用程序更偏好高带宽高于低延迟，低延迟高于短连接时间，则可以使用值（0，1，2）调用此方法。  
在此套接字连接之后调用此方法将无效。

参数：
- `connectionTime` – 一个整数，表示短连接时间的重要性
- `latency` – 一个整数，表示低延迟的重要性
- `bandwidth` – 一个整数，表示高带宽的重要性
```
public void setPerformancePreferences(int connectionTime,
                                          int latency,
                                          int bandwidth)
    {
        /* Not implemented yet */
    }
```
