# 1. IO

## 1.1 初识IO

- IO，即in和out，也就是输入和输出，指应用程序和外部设备之间的数据传递，常见的外部设备包括文件、管道、网络连接。

- 流（Stream），是一个抽象的概念，是指一连串的数据（字符或字节），是以先进先出的方式发送信息的通道，流的补充：

  - 先进先出：最先写入输出流的数据最先被输入流读取到。
  - 顺序存取：可以一个接一个地往流中写入一串字节，读出时也将按写入顺序读取一串字节，不能随机访问中间的数据。（RandomAccessFile除外）
  - 只读或只写：每个流只能是输入流或输出流的一种，不能同时具备两个功能，输入流只能进行读操作，对输出流只能进行写操作。在一个数据传输通道中，如果既要写入数据，又要读取数据，则要分别提供两个流。

- 传输方式划分：

  - 字节（byte）是计算机中用来表示存储容量的一个计量单位，通常情况下，一个字节有 **8 位（bit）**。
    - **InputStream 类**
    - **OutputStream 类**
  - 字符（char）可以是计算机中使用的字母、数字、和符号，比如说 `A 1 $` 这些。
    - **Reader 类**
    - **Writer 类**

- 按操作对象划分：

  <img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230419133308095.png" alt="image-20230419133308095" style="zoom:50%;" />

- 虽然 IO 类的方法也很多，但核心的也就 2 个：read 和 write。

## 1.2 文件流：IO流的起点与终点

# Java File类

## 1. 简介


File类是Java中用于处理文件和目录的类。
- File类是Java中用于处理文件和目录的类。
  - 创建一个File对象：可以通过指定文件路径来创建一个File对象，例如：
  
    ```java
    File file = new File("/Users/username/Documents/example.txt");
    ```
    
  - 获取文件名：可以使用getName()方法来获取文件名，例如：
  
    ```java
    String fileName = file.getName();
    ```
    
  - 判断文件是否存在：可以使用exists()方法来判断文件是否存在，例如：
  
    ```java
    boolean isExist = file.exists();
    ```
    
  - 创建新文件：可以使用createNewFile()方法来创建新文件，例如：
  
    ```java
    boolean isSuccess = file.createNewFile();
    ```
    
  - 删除文件：可以使用delete()方法来删除文件，例如：
  
    ```java
    boolean isDelete = file.delete();
    ```
    
  - 判断是否是文件夹：可以使用isDirectory()方法来判断是否是文件夹，例如：
  
    ```java
    boolean isDirectory = file.isDirectory();
    ```
    
  - 获取文件大小：可以使用length()方法来获取文件大小，例如：
  
    ```java
    long fileSize = file.length();
    ```
    
  - 获取文件最后修改时间：可以使用lastModified()方法来获取文件最后修改时间，例如：
  
    ```java
    long lastModifiedTime = file.lastModified();
    ```

File类可以用来创建、删除、重命名文件或目录，以及获取文件或目录的信息等。- File类可以用来创建、删除、重命名文件或目录，以及获取文件或目录的信息等。

实例：

```java
// 创建一个文件
File file = new File("example.txt");
try {
    if (file.createNewFile()) {
        System.out.println("文件创建成功！");
    } else {
        System.out.println("文件已存在！");
    }
} catch (IOException e) {
    e.printStackTrace();
}

// 创建一个目录
File dir = new File("exampleDir");
if (dir.mkdir()) {
    System.out.println("目录创建成功！");
} else {
    System.out.println("目录已存在！");
}

// 获取文件信息
System.out.println("文件名：" + file.getName());
System.out.println("文件路径：" + file.getPath());
System.out.println("文件绝对路径：" + file.getAbsolutePath());
System.out.println("文件父目录：" + file.getParent());
System.out.println("文件大小：" + file.length());

// 获取目录信息
System.out.println("目录名：" + dir.getName());
System.out.println("目录路径：" + dir.getPath());
System.out.println("目录绝对路径：" + dir.getAbsolutePath());
System.out.println("目录父目录：" + dir.getParent());
System.out.println("目录是否为空：" + dir.isEmpty());
```

## 2. 常用方法
1. 创建文件或目录
  

`public boolean createNewFile()`
   ```
- public boolean createNewFile()
  作用：创建一个新的空文件
  示例：
  File file = new File("D:\\test.txt");
  try {
      boolean success = file.createNewFile();
      if (success) {
          System.out.println("文件创建成功");
      } else {
          System.out.println("文件创建失败");
      }
  } catch (IOException e) {
      e.printStackTrace();
  }
   ```
注意：示例中的路径为Windows系统下的路径，如果是其他操作系统需要修改路径格式。

`public boolean mkdir()`
   ```
- public boolean mkdir()
   ```

示例：

```java
import java.io.File;

public class Test {
    public static void main(String[] args) {
        File dir = new File("D:\\test");
        boolean result = dir.mkdir();
        if (result) {
            System.out.println("目录创建成功！");
        } else {
            System.out.println("目录创建失败！");
        }
    }
}
```

输出：

```
目录创建成功！
```

`public boolean mkdirs()`
2. 删除文件或目录
   - `public boolean mkdirs()`

  ```java
  File dir = new File("C:/test");
  boolean result = dir.mkdirs();
  System.out.println(result); // true
  ```

- 删除文件或目录

  ```java
  File file = new File("C:/test/test.txt");
  boolean result = file.delete();
  System.out.println(result); // true
  ```

`public boolean delete()`
3. 重命名文件或目录
   - `public boolean delete()`

  ```java
  File file = new File("example.txt");
  if(file.delete()){
      System.out.println("文件删除成功！");
  }else{
      System.out.println("文件删除失败！");
  }
  ```

- 重命名文件或目录

  ```java
  File oldFile = new File("old.txt");
  File newFile = new File("new.txt");
  if(oldFile.renameTo(newFile)){
      System.out.println("文件重命名成功！");
  }else{
      System.out.println("文件重命名失败！");
  }
  ```

`public boolean renameTo(File dest)`
4. 获取文件或目录信息
   - `public boolean renameTo(File dest)`: 将当前文件或目录重命名为指定的文件或目录。示例代码如下：

  ```java
  File file = new File("oldName.txt");
  File newFile = new File("newName.txt");
  boolean result = file.renameTo(newFile);
  if (result) {
      System.out.println("文件重命名成功！");
  } else {
      System.out.println("文件重命名失败！");
  }
  ```

- 获取文件或目录信息：

  - `public String getName()`: 获取文件或目录的名称。示例代码如下：

    ```java
    File file = new File("test.txt");
    String name = file.getName();
    System.out.println("文件名称为：" + name);
    ```

  - `public String getPath()`: 获取文件或目录的路径。示例代码如下：

    ```java
    File file = new File("test.txt");
    String path = file.getPath();
    System.out.println("文件路径为：" + path);
    ```

  - `public long length()`: 获取文件的大小（字节数）。示例代码如下：

    ```java
    File file = new File("test.txt");
    long size = file.length();
    System.out.println("文件大小为：" + size + "字节");
    ```

  - `public boolean isDirectory()`: 判断当前File对象是否表示一个目录。示例代码如下：

    ```java
    File file = new File("test");
    boolean isDir = file.isDirectory();
    if (isDir) {
        System.out.println("该对象表示一个目录");
    } else {
        System.out.println("该对象不表示一个目录");
    }
    ```

`public String getName()`
   ```
- public String getName()
  返回由此抽象路径名表示的文件或目录的名称。
  
  示例：
  File file = new File("C:\\Users\\Admin\\Desktop\\test.txt");
  String fileName = file.getName();
  System.out.println(fileName); // 输出：test.txt
   ```

`public String getParent()`
   - `public String getParent()`: 返回此File对象的父路径名字符串，如果此File对象表示顶层，则返回null。

实例：

```java
File file = new File("C:\\Users\\username\\Documents\\example.txt");
String parentPath = file.getParent();
System.out.println(parentPath);
// 输出结果为：C:\Users\username\Documents
```

`public boolean isDirectory()`
   ```
- public boolean isDirectory()
  作用：判断当前File对象表示的是否是一个目录。
  示例：
  File file = new File("D:/test");
  System.out.println(file.isDirectory()); // true
   ```
注：以上示例中，假设D盘下存在一个名为test的目录。

`public boolean isFile()`
   `public boolean isFile()`

判断当前File对象所表示的路径是否为文件，如果是文件则返回true，如果是目录则返回false。

示例代码：

```java
File file = new File("D:/test.txt");
if(file.isFile()){
    System.out.println("该路径表示的是一个文件");
}else{
    System.out.println("该路径表示的是一个目录");
}
```

`public long lastModified()`
   ```java
// 获取文件最后修改时间
File file = new File("test.txt");
long lastModifiedTime = file.lastModified();
System.out.println("文件最后修改时间：" + lastModifiedTime);
   ```

- 说明：`lastModified()`方法返回文件最后修改时间，返回值为long类型，表示自1970年1月1日0时起此文件最后一次被修改的时间。

`public long length()`- `public long length()`

  返回文件的长度，单位为字节。

  示例代码：

  ```java
  File file = new File("example.txt");
  long length = file.length();
  System.out.println("文件长度为：" + length + "字节");
  ```

  输出结果：

  ```
  文件长度为：1024字节
  ```

## 3. 示例代码
```java
import java.io.File;

## public class FileDemo {
    public static void main(String[] args) {
        File file = new File("test.txt");
        try {
            if (file.createNewFile()) {
                System.out.println("文件创建成功！");
            } else {
                System.out.println("文件已存在！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

# 2. NIO



```
EPOCHS = 6
BATCH_SIZE = 500

model_1 = Sequential([
    layers.Dense(
        units = 5,
        activation = 'relu',
        input_shape = [bans_lengthw]
    ),

    layers.Dense(1)
])

model_1.compile(
    optimizer = 'adam',
    loss = 'mse',
    metrics = ['mae', 'mse']
)

history_1 = model_1.fit(x_train, y_train, epochs=EPOCHS, batch_size=BATCH_SIZE, validation_data=(x_test, y_test), verbose=1)
请问这段代码gou'jian
```

