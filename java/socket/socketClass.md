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

## 2.21 `
