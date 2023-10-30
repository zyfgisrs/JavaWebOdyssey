#             1. html

```html
<!DOCTYPE html>
<html lang="en">   
  <!-- html表示html的开始，lang="zh_CN" 表示中文 html标签一般分为两个部分：head和body -->
<head> 
  <!--表示头部信息 一般包含三个部分的内容，title标签, css样式, js代码 -->
  <meta charset="UTF-8"> 
<!--  表示当前页面使用UTF-8字符集-->
  <title>标题</title>
<!--  表示标题-->
</head>
<body>
<!--  body标签是整个html页面的主体内容-->
  hallo
</body>
</html>
```

- 标签名大小写不敏感
- 标签拥有自己的属性
  1. 基本属性：bgcolor = "red"    可以修改简单的样式效果。
  2. 事件属性：onclick = "alert('你好！');"
- 标签又分为，单标签和双标签
  1. 单标签`<br/>` 自结束  br换行   hr水平线分割线  input输入框
  2. 双标签`<p>   </p>`

- 标签必须正确关闭。

- 标签不能嵌套。

- 属性必须有值，属性值必须加引号。

- 


```html
<!DOCTYPE html>
<html lang="en">   <!-- html表示html的开始，lang="zh_CN" 表示中文 html标签一般分为两个部分：head和body -->
<head> <!--表示头部信息 一般包含三个部分的内容，title标签, css样式, js代码 -->
  <meta charset="UTF-8">
  <!--  表示当前页面使用UTF-8字符集-->
  <title>标题</title>
  <!--  表示标题-->
</head>
<!--bgcolor是背景颜色属性-->
<body bgcolor="#7fffd4">
<!--  body标签是整个html页面的主体内容-->
hallo
<!--alert是javascript语言提供的一个警告框函数-->
<button onclick="alert('周哥好帅!')">按钮</button>
<br/>周哥好帅<hr/>
<!--font标签是字体标签,并修改字体的颜色 字体 尺寸-->
<font color="#8a2be2" face="黑体" size="6">我喜欢java</font>
</body>
</html>
```

## 1. **字体**

### 1.1 加粗、斜体、下划线

- 加粗bold、斜体（italic）、下划线（Underline）
- strong（和加粗b一样）、big（字体不会加粗、但会加大）、small（缩小）、em（着重字、斜体）

```html
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Title</title>
</head>
<body>
  <b>东北师范大学</b>
<!--  加粗-->
  <i>东北师范大学</i>
<!--  斜体-->
  <u>东北师范大学</u>
<!--下划线-->
</body>
</html>
```

### 1.2 **上下标**

```html
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Title</title>
</head>
<body>
  水分子的化学式 ： H<sub>2</sub>O <br/>
  氧分子的化学式：O<sup>2</sup>
</body>
</html>
```



### 1.3 **常用的特殊字符**

| 显示结果 |       |
| -------- | ----- |
| <        | &lt   |
| >        | &gt   |
| 空格     | &nbsp |

------

## 2. **标题标签**

### 2.1 标题标签

- 标题标签（head）
- align（排列）属性可以指定标题的位置
  1. center中间
  2. left左侧
  3. right右侧

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Document</title>
    </head>

    <body>
        <h1 align="center">hello</h1>
        <h1 align="left">hello</h1>
        <h1 align="right">hello</h1>
    </body>

</html>
```

![image-20230228092928845](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230228092928845.png)

## 3. **段落**

- 段落（paragraph）
- *段落与段落之间的换行,间距比换行符大*

```html
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Title</title>
</head>
<body>
  你好<br/>
  你好
  <p>第一段</p>
  <p>第二段</p>
</body>
</html>
```

## ![image-20221215205817922](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20221215205817922.png)

## 4. 引用

### 4.1 q元素

- q元素为引用。
- 浏览器通常会为 <q> 元素包围引号。

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=<device-width>, initial-scale=1.0">
        <title>Document</title>
    </head>

    <body>
        <p>他的昵称是<q>疯驴子</q></p>
    </body>

</html>
```

![image-20230228095725268](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230228095725268.png)

### 4.2 blockquote块引用

- 浏览器通常会对 <blockquote> 元素进行*缩进*处理。

### 4.3 abbr缩略词引用

- 对缩写进行标记能够为浏览器、翻译系统以及搜索引擎提供有用的信息。

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=<device-width>, initial-scale=1.0">
        <title>Document</title>
    </head>

    <body>
        <p><abbr title="zhouyangfan">ZYF</abbr>中文名是周杨凡</p>
    </body>

</html>
```

![image-20230228100512628](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230228100512628.png)



------

## 5. span和div

- 大多数 HTML 元素被定义为块级元素（block level element）或行内元素（inline element）。
- **块级元素**在浏览器显示时，通常会以新行来开始（和结束）。
- **行内元素**在显示时通常不会以新行开始。（<b>, <td>, <a>, <img>）

### 5.1 div元素

- HTML  div 元素是块级元素，它是可用于组合其他 HTML 元素的容器。
- div 元素没有特定的含义。除此之外，由于它属于块级元素，浏览器会在其前后显示折行。
- 如果与 CSS 一同使用，div 元素可用于对大的内容块设置样式属性。

### 5.2 span

- HTML  span 元素是内联元素，可用作**文本的容器**。
- **当与 CSS 一同使用时**，span元素可用于为部分文本设置样式属性。

------

## 6. 超链接

- 在网页中所有点击之后可以跳转的内容都是超链接


```html
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Title</title>
</head>
<body>
  <a href="http://www.baidu.com">百度</a>
  <a href="http://www.baidu.com" target="_self">百度</a>
<!--  _self是默认值-->
  <a href="http://www.baidu.com" target="_blank">百度</a>
<!--href属性设置连接的地址
target属性设置那哪个目标进行跳转  _self表示当前页面  _blank表示打开新页面进行跳转-->
</body>
</html>
```

## 7. 列表标签

1. 有序列表

   ```java
   <!DOCTYPE html>
   <html lang="en">
   <head>
     <meta charset="UTF-8">
     <title>Title</title>
   </head>
   <body>
     <ol>
       <li>java</li>
       <li>c++</li>
       <li>python</li>
     </ol>
   
     <ol type="A" start="3">
       <li>java</li>
       <li>c++</li>
       <li>python</li>
     </ol>
   </body>
   </html>
   ```

   

2. 无序列表

   ```html
   <!DOCTYPE html>
   <html lang="en">
   <head>
     <meta charset="UTF-8">
     <title>Title</title>
   </head>
   <body>
     <ul>
       <li>赵四</li>
       <li>刘能</li>
       <li>小沈阳</li>
       <li>宋小宝</li>
     </ul>
   
     <ul type="none">
       <li>赵四</li>
       <li>刘能</li>
       <li>小沈阳</li>
       <li>宋小宝</li>
     </ul>
   <!--  type可以修改列表前面的符号-->
   
     <ol>
       <li>赵四</li>
       <li>刘能</li>
       <li>小沈阳</li>
       <li>宋小宝</li>
     </ol>
   </body>
   </html>
   ```

   

## 8. **img标签**

- src 图片的地址
- alt 图片定义，当图片加载失败，显示名字

```html
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Title</title>
</head>
<body>
<!--img标签是图片标签，用来显示图片，src属性可以设置图片的路径
  在web中路径分为相对路径和绝对路径
  相对路径：
  .          表示当前文件所在的目录
  ..          表示当前文件所在的上一级目录
  文件名       表示当前文件所在目录的文件，相当于./文件名     ./可以省略

  绝对路径:
  格式是： http://ip:port/工程名/资源路径



  javase路径下 相对路径：从工程名开始算
               绝对路径：盘符:/目录/文件名-->

  <img src="./qm.jpg" width="400" height="460" border="3" alt="签名">
  <img src="./qm.jpg" width="400" height="460" border="3">
  <img src="./qm.jpg" width="400" height="460" border="5">
</body>
</html>
```

## 9. **表格**

```
<table> - <tr> - (<th>,<td>)
第一层表示表格，第二层指定行，第三层指定哪一行是表头和表内容
```

```html
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Title</title>
</head>
<body>
  <table border="1" width="200" height="300" align="center" cellspacing="0">
    <tr>
     <td align="center"><b>1.1</b></td>
     <th>1.2</th>
     <th>1.3</th>
    </tr>
<!--table标签是表格标签 tr是行标签 th是表头标签 td是单元格标签
border 边框标签
b标签是加粗标签-->
    <tr>
      <td>2.1</td>
      <td>2.2</td>
      <td>2.3</td>
    </tr>

    <tr>
      <td>3.1</td>
      <td>3.2</td>
      <td>3.3</td>
    </tr>
  </table>
</body>
</html>
```

**表格跨行跨列**

```html
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Title</title>
</head>
<body>
  <table border="1" width="400" height="400" cellspacing="0">
    <tr>
      <td colspan="2">1.1</td>
      <td>1.3</td>
      <td>1.4</td>
      <td>1.5</td>
    </tr>
    <tr>
      <td rowspan="2">2.1</td>
      <td>2.2</td>
      <td>2.3</td>
      <td>2.4</td>
      <td>2.5</td>
    </tr>
    <tr>
      <td>3.2</td>
      <td>3.3</td>
      <td>3.4</td>
      <td>3.5</td>
    </tr>
    <tr>
      <td>4.1</td>
      <td>4.2</td>
      <td>4.3</td>
      <td colspan="2" rowspan="2">4.4</td>
    </tr>
    <tr>
      <td>5.1</td>
      <td>5.2</td>
      <td>5.3</td>
    </tr>
  </table>
</body>
</html>
```

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20221213205217081.png" alt="image-20221213205217081" style="zoom:50%;" />

## 10. 表单form



```html
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Title</title>
</head>
<body>
  <form action="demo02.html" method="post">
<!--    action 是发送的目的地， 跳转的目的地-->
    昵称：<input type="text" name="nickName"/><br/>
    密码：<input type="password" name = "pwd"/> <br/>
    性别：<input type="radio" name="gender" value = "male"> 男
          <input type="radio" name="gender" value= "female" checked> 女<br/>
<!-- checked默认选择   -->
    爱好：<input type="checkbox" name = "hobby" value = "basketball" checked>篮球
    <input type="checkbox" name = "hobby" value = "football" checked>足球
    <input type="checkbox" name = "hobby" value = "earth">地球<br/>
    星座：<select name="star">
            <option value="1">白羊座</option>
            <option value="2" selected>金牛座</option>
            <option value="3">天蝎座</option>
            <option value="4">射手座</option>
          </select><br/>
<!--    select 下拉框默认选择-->
    备注:<textarea name="remark" cols="50" rows="10"></textarea><br/>

    <input type="submit" value="注 册"/>
    <input type="reset" value="重置"/>
    <input type="button" value="普通按钮"/>
  </form>
</body>
</html>

<!--input type = "text" 表示文本框-->
<!--name属性必须要指定，否则这个文本框的数据将来不会发送给服务器-->
<!--input type = "password" 表示密码框-->
<!--input type="radio" 表示单选按钮 需要注意的是 name属性值需要保持一致才会有互斥的效果
    可以通过checked设置默认选择-->
<!--input type="checkbox" 表示复选框 默认值保持一致 这样服务器获取值的时候获取的是一个数组-->
<!--select表示下拉列表。每个选项是option， value属性是发送给服务器的值， selected表示默认选择的项-->
<!--textarea 表示多行文本框，他的value就是开始结束标签之间的值-->
```

**frameset（淘汰）**

```html
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Title</title>
</head>
<frameset rows="20%,*">
  <frame src="frames/Top.html">
  <frameset cols="15%,*">
     <frame src="frames/left.html">
    <frameset rows="80%,*">
      <frame src = "frames/right.html">
      <frame src = "frames/bottom.html">
    </frameset>
  </frameset>
</frameset>
</html>
```

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20221214140142524.png" alt="image-20221214140142524" style="zoom:33%;" />

**iframe**

```html
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Title</title>
</head>
  <body>
    <iframe src="frames/bottom.html"></iframe>
  </body>
</html>
```

总结：

- HTML是解释型的标记语言，不区分大小写
- html，head，titile，meta，body，br，p，hr，div，table，form，u，i，b，sup，sub

# 2. CSS

- 选择器：告诉浏览器要设置什么样的元素
- 声明块：用于设置样式
- 层叠样式表
- Viewport：将网页的视口设置为完美视口，开发移动端页面时一定要加上

```HTML
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
        <style>
            div {
                width: 100px;
                height: 100px;
                background-color: aqua;
            }
        </style>
    </head>

    <body>
        <div></div>
    </body>

</html>
```

## 2.1 CSS几种推荐写法

### 2.1.1 内部样式表

**写在元素的style标签里面的**

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
        <style>
            div {
                color: red;
            }
        </style>
    </head>

    <body>
        <div>
            hello,world
        </div>
    </body>

</html>
```

### 2.1.2 内联样式表

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
    </head>

    <body>
        <div style="background-color: aquamarine;">
            hello,world
        </div>
    </body>

</html>
```

### 2.1.3 外部样式表

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
        <link rel="stylesheet" href="./style.css">
    </head>

    <body>
        <div>
            hello,world
        </div>
    </body>

</html>
```

```css
style.css文件
div {
    border: 1px solid green;
}
```

优点：

- 解决页面当中样式重复的问题。
- 代码分离，利于代码维护和阅读。
- 浏览器会缓存起来，提高页面的响应速度。

## 2.2 常见的选择器及使用场景

### 2.2.1 元素标签选择器

- 单一选择器

- 组合选择器

  ```html
  <!DOCTYPE html>
  <html lang="en">
  
      <head>
          <meta charset="UTF-8">
          <meta http-equiv="X-UA-Compatible" content="IE=edge">
          <meta name="viewport" content="width=<device-width>, initial-scale=1.0">
          <title>Document</title>
          <style>
              div,
              p {
                  color: red;
              }
          </style>
      </head>
  
      <body>
          <div>hello,world</div>
          <p>欢迎来到html</p>
      </body>
  
  </html>
  ```

### 2.2.2 类选择器

```HTML
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=<device-width>, initial-scale=1.0">
        <title>Document</title>
        <style>
            .zyf {
                color: green;
            }
        </style>
    </head>

    <body>
        <div class="zyf">hello,world</div>
    </body>

</html>
```

- 结合标签选择器

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="Google Chrome">
        <meta name="viewport" content="width=<device-width>, initial-scale=1.0">
        <title>Document</title>
        <style>
            div.css01 {
                color: green;
            }
        </style>
    </head>

    <body>
        <div class="css01">hello,world</div>
        <p class="css01">html</p>
    </body>

</html>
```

符号`.`前面是标签名字，后面是类名

- 多类选择器

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="Google Chrome">
        <meta name="viewport" content="width=<device-width>, initial-scale=1.0">
        <title>Document</title>
        <style>
            .color01 {
                color: aquamarine;
            }

            .background01 {
                background-color: blue;
            }
        </style>
    </head>

    <body>
        <div class="color01 background01">hello,world</div>
    </body>

</html>
```

- 链接多个类选择器

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="Google Chrome">
        <meta name="viewport" content="width=<device-width>, initial-scale=1.0">
        <title>Document</title>
        <style>
            .color01.background01 {
                color: aquamarine;
                background-color: blue;
            }
        </style>
    </head>

    <body>
        <div class="color01 background01">hello,world</div>
    </body>

</html>
```

### 2.2.3 id选择器

- id选择器使用#
- 一个id选择器属性值在html文档中只能出现一次，避免写js获取id时出现混淆。

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=<device-width>, initial-scale=1.0">
        <title>Document</title>
        <style>
            #css01 {
                color: aquamarine;
                background-color: blue;
            }
        </style>
    </head>

    <body>
        <div id="css01">hello,world</div>
    </body>

</html>
```

### 2.2.4 通配符选择器*

- 通配符选择器可以设置全局的属性，但是优先级较低，容易被覆盖。
- 通配符最多用于页边距的设置。

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=<device-width>, initial-scale=1.0">
        <title>Document</title>
        <style>
            * {
                color: aqua;
            }
						/* 将边距设置为0 */
            * {
                margin: 0;
                padding: 0;
            }
        </style>
    </head>

    <body>
        <div>hello,world</div>
    </body>

</html>
```

### 2.2.5 派生选择器

- 后代选择器（中间使用空格）

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=<device-width>, initial-scale=1.0">
        <title>Document</title>
        <style>
            h1 p {
                color: sandybrown;
            }
        </style>
    </head>

    <body>
        <h1>
            <p>hello, world</p>
        </h1>
    </body>

</html>
```

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=<device-width>, initial-scale=1.0">
        <title>Document</title>
        <style>
            h1 p i {
                color: sandybrown;
            }
        </style>
    </head>

    <body>
        <h1>
            <p>
                <i>hello, world</i>
                你好
            </p>
        </h1>
    </body>

</html>
```

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230304104638092.png" alt="image-20230304104638092" style="zoom:33%;" />

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=<device-width>, initial-scale=1.0">
        <title>Document</title>
        <style>
            h1 p {
                color: sandybrown;
            }
        </style>
    </head>

    <body>
        <h1>
            <p>
                <i>hello, world</i>
                你好
            </p>
        </h1>
    </body>

</html>
```

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230304104737083.png" alt="image-20230304104737083" style="zoom:33%;" />



- 子元素选择器

  h1里面的p标签才能生效

  ```html
  <!DOCTYPE html>
  <html lang="en">
  
      <head>
          <meta charset="UTF-8">
          <meta name="viewport" content="width=<device-width>, initial-scale=1.0">
          <title>Document</title>
          <style>
              h1>p {
                  color: sandybrown;
              }
          </style>
      </head>
  
      <body>
          <h1>
              <p>hello, world01</p>
          </h1>
          <h1>
              <p>hello, world02</p>
          </h1>
          <h2>
              <p>hello, world03</p>
          </h2>
      </body>
  
  </html>
  ```

  <img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230304105106755.png" alt="image-20230304105106755" style="zoom:50%;" />

  

- 相邻选择器

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=<device-width>, initial-scale=1.0">
        <title>Document</title>
        <style>
            li+li {
                color: sandybrown;
            }
        </style>
    </head>

    <body>
        <ul>
            <li>前端</li>
            <li>后端</li>
            <li>测试</li>
        </ul>

        <ol>
            <li>电脑</li>
            <li>键盘</li>
            <li>鼠标</li>
        </ol>
    </body>

</html>
```

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230304105652139.png" alt="image-20230304105652139" style="zoom:50%;" />



## 2.3 特殊的选择器及使用场景

### 2.3.1 伪类选择器

不改变任何DOM的内容,只是插入了一些修饰类的元素

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=<device-width>, initial-scale=1.0">
        <title>Document</title>
        <style>
            /* 第一项设置为aquamarine */
            li:first-child {
                color: aquamarine;
            }
            /* 最后一项设置为red */
            li:last-child {
                color: red;
            }
            /* 第三项设置 */
            li:nth-child(3) {
                color: red;
            }
            
            
        </style>
    </head>

    <body>
        <ul>
            <li>tom</li>
            <li>jack</li>
            <li>james</li>
            <li>lucy</li>
            <li>durant</li>
        </ul>
    </body>

</html>
```

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=<device-width>, initial-scale=1.0">
        <title>Document</title>
        <style>
            /* 偶数项颜色设置为aquamarine */
            li:nth-child(2n) {
                color: aquamarine;
            }

            /* 奇数项颜色设置为royalblue */
            li:nth-child(2n+1) {
                color: royalblue;
            }
        </style>
    </head>

    <body>
        <ul>
            <li>tom</li>
            <li>jack</li>
            <li>james</li>
            <li>lucy</li>
            <li>durant</li>
        </ul>
    </body>

</html>
```

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=<device-width>, initial-scale=1.0">
        <title>Document</title>
        <style>
            /* 除了第二个其他元素设置元素 */
            li:not(:nth-child(2)) {
                color: salmon;
            }
        </style>
    </head>

    <body>
        <ul>
            <li>tom</li>
            <li>jack</li>
            <li>james</li>
            <li>lucy</li>
            <li>durant</li>
        </ul>
    </body>

</html>
```

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=<device-width>, initial-scale=1.0">
        <title>Document</title>
        <style>
            /* 除了第二个其他元素设置元素 */
            li:not(:nth-child(2)) {
                color: salmon;
            }

            a:visited {
                color: seagreen;
            }

            a:link {
                color: sienna;
            }

            a:active {
                color: red;
            }

            a:hover {
                color: black;
            }
        </style>
    </head>

    <body>
        <ul>
            <li>tom</li>
            <li>jack</li>
            <li>james</li>
            <li>lucy</li>
            <li>durant</li>

            <a href="https://xdclass.net">小滴课堂</a>
        </ul>
    </body>

</html>
```

- a:visited{}设置访问后a标签的样式
- a:link{}设置a标签显示的样式
- a:active{}已选中的链接的样式
- a:hover{}鼠标悬停后的链接

### 2.3.2 伪元素选择器

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=<device-width>, initial-scale=1.0">
        <title>Document</title>
        <style>
            /* 第一个字设置颜色 */
            .text::first-letter {
                color: red;

            }

            /* 第一行设置属性，只能用于块级元素 span无效 */
            .text::first-line {
                color: aqua;
            }

            /* 选中 */
            .text::selection {
                color: aquamarine;
            }


            /* 在开始位置 */
            .text::before {
                content: 'hello，world！';
                color: blue;
            }

            /* 在结束位置 */
            .text::after {
                content: 'hello，world！';
                color: blue;
            }
        </style>
    </head>

    <body>
        <div class="text">
            Lorem, ipsum dolor sit amet consectetur adipisicing elit. Iure sequi illo voluptatum cupiditate eligendi
            illum sit natus ducimus harum iste obcaecati sint fuga deleniti nobis, distinctio repellat iusto eaque.
            Tenetur.
            Vero odio quam, labore sint rem voluptate harum ipsa. Perspiciatis, perferendis a! A ea velit commodi magnam
            culpa, repudiandae similique facere blanditiis cum quaerat, modi nostrum debitis non, aliquid qui.
            Sequi rem necessitatibus maiores deserunt? Quisquam suscipit culpa nemo iure consequuntur beatae dignissimos
            saepe. Enim deleniti quas aliquam voluptatum doloremque mollitia minima unde, cumque soluta placeat
            doloribus maiores eius quaerat?
            Eveniet voluptatum soluta ipsam maxime dolorem. Cum, obcaecati neque qui delectus quam saepe dolor! Iste sit
            officiis delectus, animi quae vitae quam architecto sapiente numquam libero corrupti ea similique natus.
            Enim ipsum earum, culpa ratione nobis iure soluta rerum molestias iste qui eum explicabo delectus minima
            vitae aspernatur laudantium ea pariatur. Quibusdam iusto odio voluptatibus quia quos sunt doloremque
            aspernatur?

        </div>
    </body>

</html>
```

## 2.4 CSS盒子模型

- 在CSS里面，所有元素的HTML元素都可以看成一个盒子。

- <img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230310093207425.png" alt="image-20230310093207425" style="zoom: 67%;" />

- margin、border、padding

- content盒子内容（元素大小）

- paddy盒子的内边距

  ```java
   padding:25px 50px 75px 100px; 上填充为25px  右填充为50px    下填充为75px 左填充为100px
   padding:25px 50px 75px;       上填充为25px  左右填充为50px  下填充为75px
   padding:25px 50px;            上下填充为25px   左右填充为50px
  ```

- border盒子的边框样式

  ```java
  /* 边框样式 */
  border: solid;
  
  /* 边框宽度 | 边框样式 */
  border: 2px dotted;
  
  /* 边框样式 | 边框颜色 */
  border: outset #f33;
  
  /* 边框宽度 | 边框样式 | 边框颜色 */
  border: medium dashed green;
  
  /* 全局值 */
  border: inherit;
  border: initial;
  border: unset;
  ```

  ```java
  //分别指定属性
  border-color: blue;
  border-style: solid;
  border-width: 5px;
  
  border: 10px solid red;
  //属性之间不加逗号
  ```

  

- **WC3标准的盒子模型（义的盒子大小 = content）**

  ```css
  .box {
      width: 200px;
      height: 200px;
      background-color: chocolate;
      border: 5px solid black;
      padding: 10px;
  }
  ```

- **IE标准的盒子模型（定义的盒子大小=content+border+padding）**

  ```css
  .box {
      /* ie标准的盒子模型 */
      box-sizing: border-box;
      width: 200px;
      height: 200px;
      background-color: chocolate;
      border: 5px solid black;
      padding: 10px;
  }
  ```

- 外边距的折叠

  - 上下两个兄弟盒子的margin都为正取大，都为负取小，一正一负相加。下面两个div间距为30

    ```html
    <!DOCTYPE html>
    <html lang="en">
    
        <head>
            <meta charset="UTF-8">
            <title>Document</title>
            <style>
                /* 通配符，全局设置 */
                * {
                    margin: 0;
                    padding: 0
                }
    
                .top {
                    width: 100px;
                    height: 100px;
                    background-color: aqua;
                    margin-bottom: 30px;
                }
    
                .bottom {
                    width: 100px;
                    height: 100px;
                    background-color: blueviolet;
                    margin-top: 20px;
                }
            </style>
        </head>
    
        <body>
            <div class="top">
    
            </div>
    
            <div class="bottom">
    
            </div>
        </body>
    
    </html>
    ```

    

  - 父子元素的盒子的margin，假设没有内边距或者边框把外边分隔开，也会合并。

  - 行内框和浮动框或绝对定位的外边距不会合并。

  - 解决父子边距合并

    ```css
    将父类设置为浮动
    .father {
        width: 200px;
        height: 200px;
        background-color: aqua;
        float: left;
    }
    ```

    ```css
    .father {
        width: 200px;
        height: 200px;
        background-color: aqua;
        overflow: auto;
    }
    ```

## 2.5 CSS常用属性

### 2.5.1 盒子的位置和大小

#### 尺寸

```
宽度 width  长度/百分比/auto
高度 height
边界 margin padding 上下左右|左右上下
```

#### 布局

```
浮动： float
定位： position
弹性布局： flex
盒子内容超出部分：overflow：hidden|scroll|auto
```

不添加布局，两个div上下排列

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Document</title>
        <style>
            /* 通配符，全局设置 */
            * {
                margin: 0;
                padding: 0
            }

            .box1 {
                width: 200px;
                height: 200px;
                background-color: aqua;
            }

            .box2 {
                width: 200px;
                height: 200px;
                background-color: blueviolet;
            }
        </style>
    </head>

    <body>
        <div class="box1"></div>
        <div class="box2"></div>
    </body>

</html>
```

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230310142010121.png" alt="image-20230310142010121" style="zoom: 25%;" />

添加浮动布局，box1和box2都向左浮动

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Document</title>
        <style>
            /* 通配符，全局设置 */
            * {
                margin: 0;
                padding: 0
            }

            .box1 {
                width: 200px;
                height: 200px;
                background-color: aqua;
                float: left;
            }

            .box2 {
                width: 200px;
                height: 200px;
                background-color: blueviolet;
                float: left;
            }
        </style>
    </head>

    <body>
        <div class="box1"></div>
        <div class="box2"></div>
    </body>

</html>
```

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230310142132802.png" alt="image-20230310142132802" style="zoom:25%;" />

box1向左浮动，box2向右浮动

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Document</title>
        <style>
            /* 通配符，全局设置 */
            * {
                margin: 0;
                padding: 0
            }

            .box1 {
                width: 200px;
                height: 200px;
                background-color: aqua;
                float: left;
            }

            .box2 {
                width: 200px;
                height: 200px;
                background-color: blueviolet;
                float: right;
            }
        </style>
    </head>

    <body>
        <div class="box1"></div>
        <div class="box2"></div>
    </body>

</html>
```

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230310142236747.png" alt="image-20230310142236747" style="zoom:25%;" />

将box2设置为绝对定位

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Document</title>
        <style>
            /* 通配符，全局设置 */
            * {
                margin: 0;
                padding: 0
            }

            .box1 {
                width: 200px;
                height: 200px;
                background-color: aqua;

            }

            .box2 {
                width: 200px;
                height: 200px;
                background-color: blueviolet;
                position: absolute;
                top: 0px;
                left: 200px;
            }
        </style>
    </head>

    <body>
        <div class="father">
            <div class="box1"></div>
            <div class="box2"></div>
        </div>
    </body>

</html>
```

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230310142858651.png" alt="image-20230310142858651" style="zoom: 33%;" />

设置父级为flex（弹性）布局

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Document</title>
        <style>
            /* 通配符，全局设置 */
            * {
                margin: 0;
                padding: 0
            }

            .box1 {
                width: 200px;
                height: 200px;
                background-color: aqua;

            }

            .box2 {
                width: 200px;
                height: 200px;
                background-color: blueviolet;
            }

            .father {
                display: flex;
            }
        </style>
    </head>

    <body>
        <div class="father">
            <div class="box1"></div>
            <div class="box2"></div>
        </div>
    </body>

</html>
```

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230310143143840.png" alt="image-20230310143143840" style="zoom:33%;" />

#### overflow

在`box2`中添加`lorem`，可以发现文字溢出了

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Document</title>
        <style>
            /* 通配符，全局设置 */
            * {
                margin: 0;
                padding: 0
            }

            .box1 {
                width: 200px;
                height: 200px;
                background-color: aqua;

            }

            .box2 {
                width: 200px;
                height: 200px;
                background-color: blueviolet;
            }

            .father {
                display: flex;
            }
        </style>
    </head>

    <body>
        <div class="father">
            <div class="box1"></div>
            <div class="box2">
                Lorem, ipsum dolor sit amet consectetur adipisicing elit. Repudiandae minus praesentium incidunt totam
                dolores laborum soluta molestias porro quae cumque obcaecati asperiores perspiciatis facere quos,
                consequuntur illum vero neque provident?
            </div>
        </div>
    </body>

</html>
```

![image-20230310143553670](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230310143553670.png)

将溢出的部分隐藏

```css
.box2 {
    width: 200px;
    height: 200px;
    background-color: blueviolet;
    overflow: hidden;
}
```

![image-20230310143729375](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230310143729375.png)

设置为滚动scroll (auto)

```css
.box2 {
    width: 200px;
    height: 200px;
    background-color: blueviolet;
    overflow: scroll;
}
```

![image-20230310143843967](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230310143843967.png)

### 2.5.2 外观风格

#### 背景

```
background-image
background-repeat
background-size
background-position
background-color
```

background-image 将图片作为背景

```css
.image {
    width: 1000px;
    height: 1000px;
    background-color: aqua;
    background-image: url("https://front.cdn.xdclass.net/images/logo.webp");
}
```

![image-20230310145312324](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230310145312324.png)

背景图片不重复background-repeat

```css
.image {
    width: 1000px;
    height: 1000px;
    background-color: aqua;
    background-image: url("https://front.cdn.xdclass.net/images/logo.webp");
    background-repeat: no-repeat;
}
```

![image-20230310145738835](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230310145738835.png)

 background-size调整图片的大小

```css
 .image {
    width: 1000px;
    height: 1000px;
    background-color: aqua;
    background-image: url("https://front.cdn.xdclass.net/images/logo.webp");
    background-repeat: no-repeat;
    background-size: 50%;
}
```

background-position调整图片的位置

```
background-position: center；
```

#### 文字属性

```
字体大小： font-size
是否加粗： font-weight
是不是斜体 font-style
字体什么  font-family
```

## 2.6 层叠与选择器优先级

- `css`：层叠样式表
- 层叠是一个基本特征
- 优先级是怎么计算
  - 通配符选择器1：
  - 标签选择器2：
  - 类选择器3：
  - id选择器4：
  - 行内样式5：
  - ``!important`6：（尽量不要在公用代码中使用）

同一个元素有两个同级别样式出现在在代码中，下面出现的样式将会叠加在上面出现的样式，因此显示的下面出现的样式。

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Document</title>
        <style>
            .box {
                color: red;
            }

            .box {
                color: black;
            }
        </style>
    </head>

    <body>
        <div class="box">东北师范大学</div>
    </body>

</html>
```

id选择与类选择器，id选择器覆盖类选择器的样式，因此下面代码字体显示黑色。

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Document</title>
        <style>
            .box {
                color: red;
            }

            #boxId {
                color: black;
            }
        </style>
    </head>

    <body>
        <div class="box" id="boxId">东北师范大学</div>
    </body>

</html>
```

行内样式与id选择器、类选择器，行内选择器优先级最高，下面代码显示的是行内选择器的样式

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Document</title>
        <style>
            .box {
                background-color: black;
            }

            #boxId {
                background-color: beige;
            }
        </style>
    </head>

    <body>
        <div class="box" id="boxId" style="background-color: aqua;">东北师范大学</div>
    </body>

</html>
```

`!important`标注的样式优先级最高，下面代码div显示为`!important`标注的蓝色。

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Document</title>
        <style>
            * {
                background-color: beige;
            }

            div {
                background-color: blue !important;
            }

            .box {
                background-color: black;
            }

            #boxId {
                background-color: beige;
            }
        </style>
    </head>

    <body>
        <div class="box" id="boxId" style="background-color: aqua;">东北师范大学</div>
    </body>

</html>
```

## 2.7 常见的可继承的属性

- 什么是继承
  - 继承了父类元素的某些属性
  - 优点：继承可以简化代码，便于维护

- 默认设置继承的属性
  - 文字属性、文本缩进（text-indent）、对齐（text-align）、行高（line-height）

下面代码，父类文字颜色被设置为红色，子类div中的文字颜色继承了父类的文字属性。因此所有字体都显示为红色。

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Document</title>
        <style>
            #box {
                color: red;
            }
        </style>
    </head>

    <body id="box">
        <div>前端
            <div>
                HTML
            </div>
        </div>
        <div>JAVA</div>
        <div>测试</div>

    </body>

</html>
```

## 2.8 CSS布局定位

- 正常元素怎么布局
  - 默认一个块级元素的内容宽度就是其父元素的100%，他的高度和其内容高度一致。
  - 行内元素它的宽度和高度都是根据内容决定的（无法设置行内元素的宽高）

- 元素之间又是如何影响？
  - 正常的文档布局流
    - 每个块级元素会在上个元素下面另起一行。
    - 行内元素不会另起一行。

**显示为行级元素，无法修改其宽高**

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Document</title>

        <style>
            span {
                width: 200px;
                height: 200px;
            }
        </style>
    </head>

    <body>

    </body>
    <div>
        Lorem, ipsum dolor sit <span>aaaaa</span>amet consectetur adipisicing elit. Similique
        maiores voluptatibus enim rem, alias exducimus fuga aliquam cum illo veritatis
        ipsum at debitis. Sed mollitia in
        ea omnis iusto.
    </div>

</html>
```

![image-20230310172708996](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230310172708996.png)

以块级元素显示

```css
span {
    width: 200px;
    height: 200px;
    display: block;
}
```

![image-20230310172801888](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230310172801888.png)



### 2.8.1 float布局

- ```
  float：none； //默认值，元素不浮动
  float:letf;  //元素向左浮动
  float:right; //元素向右浮动
  ```

- 特点
  1. 浮动元素会脱离文档流，不再占据文档流空间。
  2. 浮动元素彼此之间是从左往右排列，宽度超过一行自动换行。
  3. 在浮动元素前面的元素不浮动时，浮动元素无法上移。
  4. 块级元素和行内元素浮动之后都会变成行内块级元素。
  5. 浮动元素不会盖住文字，可以设置文字环绕效果。

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Document</title>

        <style>
            .a {
                width: 100px;
                height: 100px;
                background-color: aquamarine;
                float: left;
            }

            .b {
                width: 200px;
                height: 200px;
                background-color: red;
            }
        </style>
    </head>

    <body>

    </body>
    <div>
        <div class="a"></div>
        <div class="b"></div>
    </div>

</html>
```

![image-20230310211848284](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230310211848284.png)

**浮动的元素脱离文档流，置于文档流上层，不占据文档流的空间。**



```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Document</title>

        <style>
            .a {
                width: 100px;
                height: 100px;
                background-color: aquamarine;
                float: left;
            }

            .b {
                width: 500px;
                height: 200px;
                background-color: red;
                float: left;
            }
        </style>
    </head>

    <body>

    </body>
    <div>
        <div class="a"></div>
        <div class="b"></div>
        <div class="b"></div>
        <div class="b"></div>
        <div class="b"></div>
    </div>

</html>
```

![image-20230310212321941](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230310212321941.png)

**浮动元素彼此之间是从左往右排列，宽度超过一行自动换行。**



```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Document</title>

        <style>
            .a {
                width: 100px;
                height: 100px;
                background-color: aquamarine;
            }

            .b {
                width: 500px;
                height: 200px;
                background-color: red;
                float: left;
            }
        </style>
    </head>

    <body>

    </body>
    <div>
        <div class="a"></div>
        <div class="b"></div>
        <div class="b"></div>
    </div>

</html>
```

![image-20230310213027111](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230310213027111.png)

**在浮动元素前面的元素不浮动时，浮动元素无法上移。**



行内元素浮动后变成行内块元素

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Document</title>

        <style>
            .a {
                width: 100px;
                height: 100px;
                background-color: aquamarine;
                float: left;
            }

            span {
                width: 100px;
                height: 100px;
                background-color: yellow;
            }
        </style>
    </head>

    <body>
        <div>
            <div class="a"></div>
            <span>小滴课堂</span>
        </div>
    </body>

</html>
```

![image-20230311135944800](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230311135944800.png)



```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Document</title>

        <style>
            .a {
                width: 100px;
                height: 100px;
                background-color: aquamarine;
                float: left;
            }

            span {
                width: 100px;
                height: 100px;
                background-color: yellow;
                float: left;
            }
        </style>
    </head>

    <body>
        <div>
            <div class="a"></div>
            <span>小滴课堂</span>
        </div>
    </body>

</html>
```

![image-20230311140044389](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230311140044389.png)



**float实现文字浮动**

当块元素不设置浮动，会占据这一行所有的空间，行内元素会另起一行

![image-20230311142510028](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230311142510028.png)

当div设置浮动，就变成了行内块元素，实现了文字环绕。

![image-20230311142749098](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230311142749098.png)

```css
<style>
    .a {
        width: 100px;
        height: 100px;
        background-color: aquamarine;
        float: left;
    }
</style>
```



清除浮动，使用伪元素清除浮动

```
.box2::after {
    clear: both;
    content: '';
    display: block;
}
```

### 2.8.2 flex布局

#### 父元素容器的属性

在父元素设置flex布局，实现子元素浮动

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230311152646425.png" alt="image-20230311152646425" style="zoom:33%;" />

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Document</title>

        <style>
            .a {
                width: 500px;
                height: 500px;
                background-color: bisque;
                display: flex;
            }

            .b {
                width: 100px;
                height: 100px;
                background-color: aqua;
            }

            .c {
                width: 100px;
                height: 100px;
                background-color: darkgreen;
            }


            .d {
                width: 100px;
                height: 100px;
                background-color: darkblue;
            }
        </style>
    </head>

    <body>
        <div class="a">
            <div class="b">b</div>
            <div class="c">c</div>
            <div class="d">d</div>
        </div>
    </body>

</html>
```



水平方向颠倒

```
flex-direction: row-reverse;
```

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230311152927185.png" alt="image-20230311152927185" style="zoom:33%;" />

```
flex-direction: column;
```

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230311153028722.png" alt="image-20230311153028722" style="zoom:33%;" />

```
flex-direction: column-reverse;
```

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230311153143094.png" alt="image-20230311153143094" style="zoom:33%;" />





父元素设置为flex布局，那么多个子元素宽度总和超过父元素，子元素的宽度将被视为父元素宽度的等分长度。

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Document</title>

        <style>
            .a {
                width: 500px;
                background-color: bisque;
                display: flex;
            }

            .b {
                width: 100px;
                height: 100px;
                background-color: aqua;
            }

            .c {
                width: 100px;
                height: 100px;
                background-color: darkgreen;
            }


            .d {
                width: 100px;
                height: 100px;
                background-color: darkblue;
            }
        </style>
    </head>

    <body>
        <div class="a">
            <div class="b">b</div>
            <div class="c">c</div>
            <div class="d">d</div>
            <div class="d">d</div>
            <div class="d">d</div>
            <div class="d">d</div>
        </div>
    </body>

</html>
```

父元素宽度为500，其中六个子元素宽度设置为100，超出了宽度限制，因此进行等分，每个子元素的宽度为83.34

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230311154015526.png" alt="image-20230311154015526" style="zoom:50%;" />

进行换行，子元素的宽度恢复了设置的宽度

```
flex-wrap: wrap;
nowrap 不换行
```

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230311154217325.png" alt="image-20230311154217325" style="zoom: 50%;" />

换行＋垂直颠倒

```
flex-wrap: wrap-reverse;
```

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230311154447657.png" alt="image-20230311154447657" style="zoom:50%;" />



flex布局中 子元素水平方向排列方式

```
justify-content: flex-start; 子元素从左边开始排列
justify-content: flex-end;   子元素从右边开始排列
justify-content: center;     子元素居中排列
justify-content: space-between; 从左右连边开始排列，子元素之间的间距相等
justify-content: space-around;  最左右子元素与边框留有间距，间距为子元素之间的间距的一半
```



flex布局中 子元素垂直方向排列方式

```
align-items: flex-start;  与默认方式相同
align-items: flex-end;  从下面开始排列
align-items: center;  放置在垂直方向的中间
```

`align-items: flex-start;`

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230311160131077.png" alt="image-20230311160131077" style="zoom:33%;" />

`align-items: flex-end;`

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230311160046360.png" alt="image-20230311160046360" style="zoom:33%;" />

`align-items: center;`

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230311160327768.png" alt="image-20230311160327768" style="zoom:33%;" />

`align-items: baseline;`

沿着交叉轴方向，按照项目内的文字对齐。

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Document</title>

        <style>
            .a {
                width: 500px;
                height: 500px;
                background-color: bisque;
                display: flex;
                align-items: baseline;
            }

            .b {
                width: 100px;
                height: 100px;
                background-color: aqua;
            }

            .c {
                width: 100px;
                height: 100px;
                background-color: darkgreen;
                line-height: 100px;
            }


            .d {
                width: 100px;
                height: 100px;
                background-color: darkblue;
                line-height: 40px;
            }
        </style>
    </head>

    <body>
        <div class="a">
            <div class="b">b</div>
            <div class="c">c</div>
            <div class="d">d</div>
            <div class="d">d</div>
        </div>
    </body>

</html>
```

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230311162919528.png" alt="image-20230311162919528" style="zoom:50%;" />



`align-items: stretch;`

子元素没有设置高度，子元素高度拉伸至与父元素高度一样

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Document</title>

        <style>
            .a {
                width: 500px;
                height: 500px;
                background-color: bisque;
                display: flex;
                align-items: stretch;
            }

            .b {
                width: 100px;
                background-color: aqua;
            }

            .c {
                width: 100px;
                background-color: darkgreen;
                line-height: 100px;
            }


            .d {
                width: 100px;
                background-color: darkblue;
                line-height: 40px;
            }
        </style>
    </head>

    <body>
        <div class="a">
            <div class="b">b</div>
            <div class="c">c</div>
            <div class="d">d</div>
            <div class="d">d</div>
        </div>
    </body>

</html>
```

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230311163200245.png" alt="image-20230311163200245" style="zoom: 50%;" />





`不设置 align-content: flex-start;`

换行后，第二行从中间开始排列

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Document</title>

        <style>
            .a {
                width: 500px;
                height: 500px;
                background-color: bisque;
                display: flex;
                flex-wrap: wrap;
            }

            .b {
                width: 100px;
                height: 100px;
                background-color: aqua;
            }

            .c {
                width: 100px;
                height: 100px;
                background-color: darkgreen;
            }


            .d {
                width: 100px;
                height: 100px;
                background-color: darkblue;
            }
        </style>
    </head>

    <body>
        <div class="a">
            <div class="b">b</div>
            <div class="c">c</div>
            <div class="d">d</div>
            <div class="d">d</div>
            <div class="d">d</div>
            <div class="d">d</div>
        </div>
    </body>

</html>
```

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230311165149788.png" alt="image-20230311165149788" style="zoom:50%;" />





`设置 align-content: flex-start;`

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Document</title>

        <style>
            .a {
                width: 500px;
                height: 500px;
                background-color: bisque;
                display: flex;
                flex-wrap: wrap;
                align-content: flex-start;
            }

            .b {
                width: 100px;
                height: 100px;
                background-color: aqua;
            }

            .c {
                width: 100px;
                height: 100px;
                background-color: darkgreen;
            }


            .d {
                width: 100px;
                height: 100px;
                background-color: darkblue;
            }
        </style>
    </head>

    <body>
        <div class="a">
            <div class="b">b</div>
            <div class="c">c</div>
            <div class="d">d</div>
            <div class="d">d</div>
            <div class="d">d</div>
            <div class="d">d</div>
        </div>
    </body>

</html>
```

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230311165308333.png" alt="image-20230311165308333" style="zoom:50%;" />



`align-content: flex-end;`

内容从下往上排列

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230311165617990.png" alt="image-20230311165617990" style="zoom:50%;" />



`align-content: center;`

内容从中间开始排列

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230311165808263.png" alt="image-20230311165808263" style="zoom:50%;" />



`lign-content: space-between;`

子元素分布在上下两端，再平分剩余的空间

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230311171826554.png" alt="image-20230311171826554" style="zoom:50%;" />

`align-content: space-around;`

元素间距是元素与边框间距的两倍

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230311172327144.png" alt="image-20230311172327144" style="zoom:50%;" />

#### 子元素容器属性

`order`

默认值为0，属性值越大，排列顺序越后

`flex-grow`

设置放大比例，可以简写为flex：x

`flex-shrink`

设置缩小比例，可以简写为flex：0

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Document</title>

        <style>
            .a {
                width: 200px;
                height: 500px;
                background-color: bisque;
                display: flex;
            }

            .b {
                width: 100px;
                height: 100px;
                background-color: aqua;
                flex-shrink: 0;
            }

            .c {
                width: 100px;
                height: 100px;
                background-color: darkgreen;
            }


            .d {
                width: 100px;
                height: 100px;
                background-color: darkblue;
            }
        </style>
    </head>

    <body>
        <div class="a">
            <div class="b">b</div>
            <div class="c">c</div>
            <div class="d">d</div>
        </div>
    </body>

</html>
```

上面代码中设置b类div不缩小，b保持原来的大小，剩余的空间被c和d评分。

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230311174223087.png" alt="image-20230311174223087" style="zoom:50%;" />

### 2.8.3 position

子元素设定绝对定位，right为0，在设置相对定位的父级元素中，距离父级元素最右边的边框为0。

```java
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Document</title>

        <style>
            .a {
                width: 300px;
                height: 500px;
                background-color: bisque;
            }

            .b {
                width: 300px;
                height: 500px;
                background-color: aqua;
                position: relative;
            }

            .c {
                width: 300px;
                height: 500px;
                background-color: darkgreen;
            }


            .aa {
                width: 50px;
                height: 50px;
                background-color: darkblue;
                position: absolute;
                right: 0;
            }
        </style>
    </head>

    <body>
        <div class="a"></div>
        <div class="b">
            <div class="aa"></div>
        </div>
        <div class="c"></div>
    </body>

</html>
```

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230311191251972.png" alt="image-20230311191251972" style="zoom:33%;" />

当子元素设定绝对定位，各级父级元素没有设定相对定位，只能按照整个浏览器定位

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Document</title>

        <style>
            .a {
                width: 300px;
                height: 500px;
                background-color: bisque;
            }

            .b {
                width: 300px;
                height: 500px;
                background-color: aqua;
            }

            .c {
                width: 300px;
                height: 500px;
                background-color: darkgreen;
            }


            .aa {
                width: 50px;
                height: 50px;
                background-color: darkblue;
                position: absolute;
                right: 0;
            }
        </style>
    </head>

    <body>
        <div class="a"></div>
        <div class="b">
            <div class="aa"></div>
        </div>
        <div class="c"></div>
    </body>

</html>
```

![image-20230311191334156](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230311191334156.png)

**总结：**

- **`absolute`用于需要定位的元素，`relative`用于需要定位子类元素的参照父级元素。**
- `position`：默认为静态定位`static`
- `position：fixed`：固定定位，无论怎么滚动，元素都固定在浏览器特定位置。
- `position：sticky`：粘性定位，固定元素在顶部

- `z-index`：设置元素的层级，属性值越高，越优先展示。

### 2.8.4 三栏布局

- 问题：高度固定，左右两侧是盒子宽度为200px，中间自适应
- 解决：
  - float实现
  - position实现
  - flex实现

float实现

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Document</title>

        <style>
            * {
                margin: 0;
                padding: 0;
            }

            .left {
                width: 200px;
                height: 300px;
                background-color: red;
                float: left;
            }

            .right {
                width: 200px;
                height: 300px;
                background-color: blue;
                float: right;
            }

            .center {
                height: 300px;
                background-color: green;
                margin: 0 200px;
            }
        </style>
    </head>

    <body>
        <div class="father">
            <div class="left"></div>
            <div class="right"></div>
            <div class="center"></div>
        </div>
    </body>

</html>
```

position实现

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Document</title>

        <style>
            * {
                margin: 0;
                padding: 0;
            }

            .left {
                width: 200px;
                height: 300px;
                background-color: red;
                position: absolute;
                left: 0;
            }

            .right {
                width: 200px;
                height: 300px;
                background-color: blue;
                position: absolute;
                right: 0;
            }

            .center {
                height: 300px;
                background-color: green;
                margin: 0 200px;
            }
        </style>
    </head>

    <body>
        <div class="father">
            <div class="left"></div>
            <div class="right"></div>
            <div class="center"></div>
        </div>
    </body>

</html>
```

flex布局实现

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Document</title>

        <style>
            * {
                margin: 0;
                padding: 0;
            }

            .left {
                width: 200px;
                height: 300px;
                background-color: red;
            }

            .right {
                width: 200px;
                height: 300px;
                background-color: blue;
            }

            .center {
                flex: 1;
                height: 300px;
                background-color: green;
            }

            .father {
                display: flex;
            }
        </style>
    </head>

    <body>
        <div class="father">
            <div class="left"></div>
            <div class="center"></div>
            <div class="right"></div>
        </div>
    </body>

</html>
```

### 2.8.5 水平垂直居中的常用方法

- 行内块元素设置水平垂直居中

  ```
  line-height: 100px; 将行高度设置为行内块元素的高度
  text-align: center;
  
  display: flex;
  justify-content: center;
  align-items: center;
  ```

- 块级元素

  ```
  position + margin
  position + transform
  flex
  ```

  `position + margin`已知子元素的宽高

  ```css
  .subbox {
      width: 100px;
      height: 100px;
      background-color: blue;
      position: absolute;
      top: 50%;
      left: 50%;
      margin-left: -50px;
      margin-top: -50px;
  }
  ```

  `position + transform`子元素宽高位置

  ```css
  .subbox {
      width: 100px;
      height: 100px;
      background-color: blue;
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
  }
  ```

  `flex`

  ```css
  .box {
      width: 500px;
      height: 500px;
      background-color: palegoldenrod;
      display: flex;
      justify-content: center;
      align-items: center;
  }
  
  .subbox {
      width: 100px;
      height: 100px;
      background-color: blue;
  }
  ```

  

### 2.8.6 BFC格式化上下文

- 定义：
  - 格式化上下文是web页面的可视化CSS渲染的一部分，是块盒子的布局过程发生的区域，也是浮动元素与其他元素交互的区域。
  - 即形成了一块封闭的区域，能检测到区域内脱离文档流的元素。
- 开启：
  - 设置元素浮动`float：left`；
  - 设置行内块元素：`display：inline-block`；
  - `overflow：hidden`
- 作用：
  - 清除浮动带来的影响。
  - 解决边距坍塌问题。

## 2.9 CSS3常用属性

### 边框

```css
border-radius ： 10px; 圆角,顺时针顺序
border-radius ： 50%; 变成圆形

box-shadow : 10px 10px 10px #000;  1.水平方向  2.垂直方向  3.阴影的虚幻度

border-image : url();
```

### 渐变

`background-image:liner-gradient;`背景颜色渐变

默认从上到下渐变

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
        <style>
            div {
                width: 100px;
                height: 100px;
                background-image: linear-gradient(red, blue);
            }
        </style>
    </head>

    <body>
        <div>

        </div>

    </body>

</html>
```

![image-20230313153553954](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230313153553954.png)



`background-image: linear-gradient(to right, red, blue);`

![image-20230313153904602](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230313153904602.png)

`background-image: linear-gradient(green, red, blue);`

![image-20230313154858468](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230313154858468.png)

`background-image: linear-gradient(rgba(0, 0, 0, 0), rgba(0, 0, 0, 1));`

rgba第四个参数为透明度

![image-20230313155113932](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230313155113932.png)

`background-image: repeating-linear-gradient(red, green, blue 10%);`

重复渐变

![image-20230313155346419](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230313155346419.png)

### 文本效果

文本阴影

`text-shadow: 10px 10px 10px royalblue;`

一行展示，当文本溢出使用省略号代替

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
        <style>
            div {
                width: 300px;
                background-color: aqua;
                overflow: hidden;
                white-space: nowrap;
                /* 文本溢出展示的效果，省略号代替 */
                text-overflow: ellipsis;
            }
        </style>
    </head>

    <body>
        <div>
            Lorem ipsum, dolor sit amet consectetur adipisicing elit. Quam repudiandae quas facere ea cumque, debitis
            similique explicabo nihil assumenda recusandae totam fugiat tempora voluptate a ullam amet, dolorum ut cum?
            Lorem ipsum, dolor sit amet consectetur adipisicing elit. Quam repudiandae quas facere ea cumque, debitis
            similique explicabo nihil assumenda recusandae totam fugiat tempora voluptate a ullam amet, dolorum ut cum?
        </div>

    </body>

</html>
```

![image-20230313160125891](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230313160125891.png)



两行展示，当文本溢出使用省略号代替

```css
div {
    width: 300px;
    background-color: aqua;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
}
```

![image-20230313160554316](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230313160554316.png)





文本换行的方式

`word-break: break-all;`截断单词进行换行

```css
div {
    width: 300px;
    background-color: aqua;
    overflow: hidden;
    word-break: break-all;
}
```

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230313161130440.png" alt="image-20230313161130440" style="zoom: 50%;" />



`word-break: break-word;`基于单词换行

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230313161200600.png" alt="image-20230313161200600" style="zoom:50%;" />

### 网格布局

- flex、float布局应用于一维布局，网格布局应用于二维布局

 

```html
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
        <style>
            .father {
                width: 400px;
                height: 800px;
                background-color: aquamarine;
                display: grid;
                grid-template-columns: 50% 50%;
                grid-template-rows: 200px 200px;
            }

            .son {
                width: 200px;
                height: 200px;
                background-color: sandybrown;
                border: 1px solid red;
                box-sizing: border-box;
            }
        </style>
    </head>

    <body>
        <div class="father">
            <div class="son"></div>
            <div class="son"></div>
            <div class="son"></div>
            <div class="son"></div>
            <div class="son"></div>
            <div class="son"></div>
            <div class="son"></div>
            <div class="son"></div>
        </div>

    </body>

</html>
```

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230313163124158.png" alt="image-20230313163124158" style="zoom:50%;" />

## 3 阿里巴巴矢量库使用

```html
<script src="http://at.alicdn.com/t/c/font_3949318_6g1ysbpdkr.js"></script>

<svg class="icon" aria-hidden="true">
    <use xlink:href="#icon-QQ"></use>
</svg>

href中为图片code
```







# HTTP

## HTTP请求格式

**请求行**

请求方法（get，post）+url（/首页）+http协议版本1.1版本

例如：post/mayikt http 1.1

**请求头（Request Headers）**

- 形式为键值对

**请求体**

- get请求请求参数在请求行中，没有请求体
- post请求请求参数（表单中的参数）在请求体中
- get请求请求参数有大小限制，post没有

## HTTP响应格式

**响应行**

Status Code状态码

1. 404---客户端发送请求达到服务端地址填写错误
2. 500---服务器端发生了错误

**响应头**

服务器端响应结果给客户端类型：

1. image/png图片
2. text/javascript
3. 响应体 存放服务器响应给客户端的内容



# Tomcat

目录结构说明：

- bin可执行文件目录
- conf配置文件目录，存放全局配置文件，修改tomcat启动端口号码。
- logs日志文件目录
- webapps项目部署的目录
- work工作目录
- temp临时目录
- lib存放lib目录

![image-20221220160746798](C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20221220160746798.png)

- 如果tomcat启动成功后tomcat控制台界面是不会停止的。
- tomcat启动之后默认端口号码：8080

**修改tomcat启动的端口号**

conf -> server.xml

## Tomcat服务器部署项目

1. 直接在tomcat webapps目录创建一个文件夹。
2. 在tomcat目录/conf/server.xml配置
3. 将项目打成war包，放到tomcat webapps目录中，自动解压
4. webapps目录下/ROOT工程的访问（不需要填写路径v），tomcat欢饮页面就是部署在webapps/ROOT目录下

## idea创建web项目

- idea先创建一个普通的java项目，再修改为web项目
- 右击项目名称->add Frameworks -> 勾选web application
- 添加本地tomcat服务器（edit configurations）
- 将项目添加到tomcat服务其中

# Servlet

servlet定义：servlet是基于java技术的Web组件，由容器管理并产生动态内容。Servlet与客户端通过Servlet容器实现的请求/响应模型进行交互。

### servlet环境搭建

1. 在我们的项目中创建libs目录存放第三方的jar包。
2. 在项目中导入servlet-api.jar libs目录中。
3. 创建servlet包，专门存放就是我们的servlet。
4. 创建indexServlet实现Servlet重写方法。
5. indexServlet类加上注解，定义URL访问路径。
6. 重写Servlet类中service在service中编写动态资源。

```java
package com.zyf.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: 周杨凡
 * @date: 2022-12-26
 */
@WebServlet("/zhouyangfan")
public class IndexServlet implements Servlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("OK");
        String userName = servletRequest.getParameter("userName");
        PrintWriter writer = servletResponse.getWriter();
        if("zyf".equals(userName)){
            writer.println("ok");
        }else {
            writer.println("fail");
        }
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
```

### servlet执行流程

- servlet由tomcat服务器创建，项目部署在tomcat中，并加载servlet.class到内存中

- service方法是由tomcat执行的。

1. 创建servlet对象。（单例模式）

2. 执行servlet类中init---初始化方法。

   当我们的servlet类被创建时，执行servlet类初始化方法init代码初始化（该方法只会执行一次）

3. servlet类---service----执行**每次**请求。

   每次客户端发送请求到达服务端都会执行到servlet类service方法。

4. servlet类---destroy---当我们tomcat容器停止时卸载servlet。

   存放销毁的相关代码

```java
@WebServlet(urlPatterns = "/zhouyangfan", loadOnStartup = -1)
//loadOnStartup为负数的情况下，第一次访问到servlet时才会创建servlet。
//0或者正数，启动tomcat时就会创建servlet，数字越小创建的优先级越高
public class IndexServlet implements Servlet {

    public IndexServlet() {
        System.out.println("1.当前我们的对象被创建了，执行无参构造方法");
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("2.执行到类中init初始化方法 该方法在生命周期中只会执行一次");
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("3.执行到业务逻辑代码service方法，每次请求都会执行service");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {
        System.out.println("4.将tomcat停止时，会卸载servlet，执行destroy销毁");
    }
}
```

### servlet线程安全问题

```java
package com.zyf.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: 周杨凡
 * @date: 2022-12-26
 */
@WebServlet(urlPatterns = "/zhouyf468", loadOnStartup = 1)
public class ZyfServlet implements Servlet {
    private int count = 0;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        synchronized (this) {
            count++;
        }
        servletResponse.setContentType("text/html;charset=utf-8");
        PrintWriter writer = servletResponse.getWriter();
        writer.println("第" + count + "次访问" + this + Thread.currentThread().getName());
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
```

### Servlet五个核心方法

1.  init()

2. service()

3. destory()

4. getServletConfig()  获取初始化参数值

5. getServletInfo() 返回一个String类型的字符串，其中包括了关于Servlet的信息。

   

```java
package com.zyf.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: 周杨凡
 * @date: 2022-12-26
 */
@WebServlet(urlPatterns = "/servletConfig", loadOnStartup = -1, initParams = {
        @WebInitParam(name = "p1", value = "mayikt")
})
public class ServletConfigServlet implements Servlet {

    private ServletConfig servletConfig;

    /**
     * @param servletConfig
     * 初始化方法，只会执行一次
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        String p1 = servletConfig.getInitParameter("p1");
        System.out.println(p1);
        this.servletConfig = servletConfig;
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        String p1 = servletConfig.getInitParameter("p1");
        PrintWriter writer = servletResponse.getWriter();
        writer.println(p1);
        writer.close();
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
```

### httpservlet封装类的使用

```java
package com.zyf.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author: 周杨凡
 * @date: 2022-12-26
 */
@WebServlet("/HttpServletDemo")
public class HttpServletDemo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("执行get请求");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("执行post请求");
    }
}
```

### httpservlet原理

```java
package com.zyf.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

/**
 * @author: 周杨凡
 * @date: 2022-12-26
 */
@WebServlet("/httpServletDemo01")
public class HttpServletDemo01 implements Servlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String method = httpServletRequest.getMethod();
        if("GET".equals(method)){
            doGet(httpServletRequest);
        }else if("POST".equals(method)){
            doPost(httpServletRequest);
        }
    }

    private void doPost(HttpServletRequest httpServletRequest) {
        System.out.println("do POST方法....");
    }

    private void doGet(HttpServletRequest httpServletRequest) {
        System.out.println("do get方法....");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
```

### request与response对象 

- request：获取客户端发送数据给服务器端
- response：返回对应的数据给客户端                                                                                                       

```java 
package com.zyf.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: 周杨凡
 * @date: 2022-12-26
 */
@WebServlet("/HttpServletDemo02")
public class HttpServletDemo02 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        String userPwd = req.getParameter("userPwd");
        PrintWriter writer = resp.getWriter();
        if ("zyf".equals(userName) && "12345".equals(userPwd)) {
            resp.addHeader("Content-Type", "text/html;charset=UTF-8");
            writer.write("<html><head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Title</title>\n" +
                    "</head><body><h1>恭喜您登录的用户名是" + userName + "</h1></body></html>");
        }else {
            writer.write("<html><head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Title</title>\n" +
                    "</head><body><h1>密码用户名错误</h1></body></html>");
        }
    }
}
```

ServletRequest--------接口  java提供的请求对象根接口

HttpServletRequest -----接口（继承ServletRequest）java提供的对http协议封装请求对象接口。

### request获取请求参数

​       **请求行**

```java
package com.zyf.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author: 周杨凡
 * @date: 2022-12-27
 */
@WebServlet("/httpServletDemo03")
public class HttpServletDemo03 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod(); //方法
        System.out.println("method: " + method);
        String contextPath = req.getContextPath(); //项目名称
        System.out.println("contextPath: " + contextPath);
        StringBuffer requestURL = req.getRequestURL();  //全路径
        String requestURi = requestURL.toString();
        System.out.println("requestURL:" + requestURi);
        String requestURI = req.getRequestURI(); //访问的路径
        System.out.println("requestURI:" + requestURI);
        String queryString = req.getQueryString();
        System.out.println("queryString: " + queryString);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
```

​                               

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20221227111313012.png" alt="image-20221227111313012" style="zoom:50%;" />

**请求头**

```java
package com.zyf.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author: 周杨凡
 * @date: 2022-12-27
 */
@WebServlet("/httpServletDemo03")
public class HttpServletDemo03 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod(); //方法
        System.out.println("method: " + method);
        String contextPath = req.getContextPath(); //项目名称
        System.out.println("contextPath: " + contextPath);
        StringBuffer requestURL = req.getRequestURL();  //全路径
        String requestURi = requestURL.toString();
        System.out.println("requestURL:" + requestURi);
        String requestURI = req.getRequestURI(); //访问的路径
        System.out.println("requestURI:" + requestURI);
        String queryString = req.getQueryString();
        System.out.println("queryString: " + queryString);

        //请求头
        String useAgent = req.getHeader("User-Agent");
        //获取客户端的版本信息 浏览器
        System.out.println(useAgent);
        String language = req.getHeader("Accept-Language");
        System.out.println(language);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
```

**请求体（post）**

```java
package com.zyf.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author: 周杨凡
 * @date: 2022-12-27
 */
@WebServlet("/httpServletDemo03")
public class HttpServletDemo03 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod(); //方法
        System.out.println("method: " + method);
        String contextPath = req.getContextPath(); //项目名称
        System.out.println("contextPath: " + contextPath);
        StringBuffer requestURL = req.getRequestURL();  //全路径
        String requestURi = requestURL.toString();
        System.out.println("requestURL:" + requestURi);
        String requestURI = req.getRequestURI(); //访问的路径
        System.out.println("requestURI:" + requestURI);
        String queryString = req.getQueryString();
        System.out.println("queryString: " + queryString);

        //请求头
        String useAgent = req.getHeader("User-Agent");
        //获取客户端的版本信息 浏览器
        System.out.println(useAgent);
        String language = req.getHeader("Accept-Language");
        System.out.println(language);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        //获取字符输入流
        String s = reader.readLine();
        //获取客户端发送给服务端的数据，设定请求体内容
        System.out.println(s);
        reader.close();
    }
}
```

### 手动封装request参数方法

```java
package com.zyf.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author: 周杨凡
 * @date: 2022-12-27
 */
@WebServlet("/httpServletDemo04")
@SuppressWarnings({"all"})
public class HttpServletDemo04 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求的参数不同
        //获取请求方法的类型
        String method = req.getMethod();
        String parameterStr = null;
        switch (method){
            case "GET":
                parameterStr = req.getQueryString();
                break;
            case "POST":
                BufferedReader reader = req.getReader();
                parameterStr = reader.readLine();
                break;
        }
        HashMap<String, String> paramterMap = new HashMap<>();
        String[] split = parameterStr.split("&");
        for (int i = 0; i < split.length; i++) {
            String[] split1 = split[i].split("=");
            String key = split1[0];
            String value = split1[1];
            paramterMap.put(key, value);
        }
        Set<Map.Entry<String, String>> entries = paramterMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key + " = " + value);
        }
    }
}
```

### request通用获取参数方法

```java
package com.zyf.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * @author: 周杨凡
 * @date: 2022-12-27
 */
@WebServlet("/httpServletDemo05")
public class HttpServletDemo05 extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> parameterMap = req.getParameterMap();
        //key = 参数的名称  value = 该参数对应的值
        //value为字符串数组
        System.out.println(parameterMap);
        Set<String> keys = parameterMap.keySet();
        for (String key : keys) {
            System.out.println(key);
            String[] strings = parameterMap.get(key);
            System.out.println(strings[0]);
        }
        System.out.println("-------");
        String userName = req.getParameter("userName");
        String age = req.getParameter("age");
        System.out.println("userName" +  userName);
        System.out.println("age" + age);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
```

### request请求转发

**一种在服务器内部的资源跳转方式**

- 浏览器地址栏路径不会发生改变。
- 只能转发到当前服务器内部资源。
- 转发是一次请求。

```java
package com.zyf.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author: 周杨凡
 * @date: 2022-12-27
 */
@WebServlet("/HttpServletDemo06")
public class HttpServletDemo06 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //客户端发送请求过来先到达HttpServletDemo06

        System.out.println("HttpServletDemo06....");
        //转发到HttpServletDemo07

        req.setAttribute("name","zyf");
        //共享数据
        req.getRequestDispatcher("/httpServletDemo07").forward(req, resp);
    }
}
```

```java
package com.zyf.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author: 周杨凡
 * @date: 2022-12-27
 */
@WebServlet("/httpServletDemo07")
public class HttpServletDemo07 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("httpServletDemo07....");
        resp.getWriter().println("我是ServletDemo07");
        String name = (String)req.getAttribute("name");
        System.out.println(name);
    }
}
```

### response响应数据

```java
package com.zyf.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: 周杨凡
 * @date: 2022-12-27
 */
@WebServlet("/httpServletDemo08")
public class HttpServletDemo08 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(404);
        //设置正确状态码和不正确状态码、
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.println("周杨凡");
        writer.close();
    }
}
```

### response重定向

- 当我们的客户端发送请求（第一次请求 ）到达服务器端，我们的服务器端状态码302，同时在响应头中设置重定向地址`resp.setHeader("Location","www.mayikt.com")`

- 客户端（浏览器）收到结果之后，在浏览器解析`Location www.mayikt.com`，再直接重定向到`www.mayikt.com`（第二次请求）
- 请求转发产生一次请求，重定向产生两次请求。

**第一种方法**

```java
package com.zyf.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author: 周杨凡
 * @date: 2022-12-27
 */
@WebServlet("/httpServletDemo09")
public class HttpServletDemo09 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(302);
        resp.setHeader("Location","https://www.baidu.com");
    }
}
```

**第二种方法**

```java
package com.zyf.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author: 周杨凡
 * @date: 2022-12-27
 */
@WebServlet("/httpServletDemo10")
public class HttpServletDemo10 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("https://www.baidu.com");
    }
}
```



# <img src="C:\Users\zhouyangfan\Desktop\HttpServlet类.png" alt="HttpServlet类" style="zoom: 33%;" />JSP

- JSP是一种用于开发动态web资源的技术。
- JSP这门技术最大的特点在于写JSP就像在写HTML，但相对于html而言，html只能为用户提供静态数据，而JSP技术允许在页面中镶嵌java代码，为用户提供动态数据。
- 相比Servlet而言，Servlet很难对数据进行排版，而jsp除了可以用java代码产生动态数据的同时，也很容易对数据进行排版。

- JSP转化成servlet。
- index.jsp ---- 底层变成index.java（servlet）---- index.class。

## JSP脚本

在JSP中定义java代码

1. `<%...%>`：内容会直接放到_jspService()方法中。
2. `<%=...%>`：内容会放到out.print()中，作为out.print()参数。
3. `<%!...%>`:内容会放到_jspService()方法之外，被类直接包含。

```jsp
<%--
  Created by IntelliJ IDEA.
  User: zhouyangfan
  Date: 2022/12/29
  Time: 13:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  <%
    System.out.println("我是定义在jspService方法之中");
    int j = 20;
    zyf();
  %>

  <%="直接输出string类型"+j%>
  <%!
    void zyf(){
      System.out.println("我是zyf 定义在jspService方法之外");
    }
    String userName = "zyf";
  %>
  $END$
  </body>
</html>
```

## el表达式

- Expression Language表达式语言，用于简化JSP页面内的java代码。
- 主要功能：获取数据
- 语法：${expression}   例:${users}获取域中存储的key为users的数据
  1. page：当前页面有效
  2. request：当前请求有效
  3. session：当前会话有效
  4. application：当前应用有效

```java
package com.zyf.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author: 周杨凡
 * @date: 2022-12-29
 */
@WebServlet("/userListServlet")
public class UserListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<String> userList = new ArrayList<>();
        userList.add("zyf");
        userList.add("tom");
        userList.add("jack");
        /**
         * key = userList values = userList
         */
        req.setAttribute("userList",userList);
        req.getRequestDispatcher("userList.jsp").forward(req, resp);
    }
}
```

```jsp
<%--
  Created by IntelliJ IDEA.
  User: zhouyangfan
  Date: 2022/12/29
  Time: 15:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
${userList}
</body>
</html>
```



## jstl标签

### if标签

- JSTL是apache对EL表达式的扩展（也就说JSTL依赖EL），JSTL是标签语言。JSTL标签使用起来非常方便，它与JSP动作标签一样，只不过它不是JSP内置的标签，需要我我们自己导入包，以及指定标签库。
- **if标签的test属性必须是一个boolean类型的值，如果test的值为true，那么执行if标签的内容，否则不执行。**

```jsp
<%--
  Created by IntelliJ IDEA.
  User: zhouyangfan
  Date: 2022/12/29
  Time: 16:18
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    ${age}
    <c:if test="${age>18}">
    		<h1>年龄大于18岁</h1>
    </c:if>
    <c:if test="${age<18}">
        <h1>年龄小于18岁</h1>
    </c:if>
</body>
</html>
```

```java
package com.zyf.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author: 周杨凡
 * @date: 2022-12-29
 */
@WebServlet("/userListServlet")
public class UserListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取用户传递的age
        String age = req.getParameter("age");
        //转发值
        req.setAttribute("age",age);
        //转发到userList.jsp
        req.getRequestDispatcher("userList.jsp").forward(req, resp);
    }
}
```

### forEach标签

```jsp
<%--
  Created by IntelliJ IDEA.
  User: zhouyangfan
  Date: 2022/12/29
  Time: 16:18
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table border="1" align="center" style="border-collapse: collapse; width: 30%">
    <tr align="center">
        <th align="center">序号</th>
        <th align="center">名称</th>
        <th align="center">年龄</th>
        <th align="center">冻结</th>
        <th align="center">操作</th>
    </tr>
    <c:forEach items="${userEntities}" var="user" varStatus="m">
        <tr align="center">
            <td align="center">${m.index+1}</td>
            <td align="center">${user.userName}</td>
            <td align="center">${user.age}</td>
            <td align="center"><c:if test="${user.state=='1'}">冻结</c:if><c:if test="${user.state=='0'}">未冻结</c:if></td>
            <td align="center"><a href="#">修改</a>&nbsp;&nbsp;&nbsp;<a href="#">删除</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
```

# 会话技术

- 一种维护浏览器状态的方法，服务器需要识别多次请求是否来自于同一个浏览器，以便于在同一次会话的多次请求间共享数据
- 这是因为http协议是无状态的，每次浏览器向服务器请求时，没有绑定会话信息服务器都会认为该请求是新请求，没有任何记忆功能，所以我们需要会话跟踪技术实现会话内数据共享。
- 会话技术实现方式
  1. 客户端会话跟踪技术：Cookie
  2. 服务端会话跟踪技术：Session
  3. token或者jwt

## cookie技术

- 客户端会话技术，将数据保存到客户端，以后每次请求都携带Cookie数据进行访问
- Cookie数据存放在浏览器端（客户端）
- 重启tomcat服务器，在没有清楚浏览器缓存的情况下，cookie依然在客户端

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20221230213246009.png" alt="image-20221230213246009" style="zoom:50%;" />

### cookie原理

**Cookie实现是基于HTTP协议的**

1. 响应头

   客户端（浏览器端）发送请求到达服务器端，服务器端会创建cookie，会将该cookie返回发客户端，在响应头中设置set -cookie ：cookie数据

2. 请求头

   同一个浏览器发送请求时，在请求中设置该cookie数据存放在请求头中。Cookie:  zyf=zhouyf468

### cookie过期时间

setMaxAge(int seconds):设置Cookie存活时间

1. 正数：将Cookie写入浏览器所在的硬盘，持久化存储，到期自动删除
2. 负数：默认值，Cookie存储在浏览器内存中，当浏览器关闭，内存释放，则Cookie被销毁
3. 零：删除对应Cookie

```java
package com.zyf.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author: 周杨凡
 * @date: 2022-12-30
 */
@WebServlet("/addCookie")
public class AddCookieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //如何创建Cookie
        Cookie cookie = new Cookie("zyf", "zhouyf468");
        //服务端将cookie数据返回给客户端（浏览器端保存Cookie信息）
        cookie.setMaxAge(60);
        //60s秒过期 在有效期内浏览器关闭数据也不会丢失
        resp.addCookie(cookie);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
```

## Session技术

### Session用法

- 服务器端会话跟踪技术：将数据保存在服务器端（底层是基于cookie实现封装的）

```java
package com.zyf.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: 周杨凡
 * @date: 2022-12-31
 */
@WebServlet("/getSessionA")
public class GetSessionA extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        //获取当前的Session
        session.setAttribute("name","zyf");
        //存入值
        PrintWriter writer = resp.getWriter();
        writer.println("ok");
        writer.close();
    }
}
```

```java
package com.zyf.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: 周杨凡
 * @date: 2022-12-31
 */
@WebServlet("/getSessionB")
public class GetSessionB extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String name = (String) session.getAttribute("name");
        PrintWriter writer = resp.getWriter();
        writer.print("sessionValue" + name);
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
```

### Session原理

- 当客户端访问到服务器端getSessionA()，服务器端创建一个Session，Session存放在服务器端，服务器端会响应SessionId给客户端。**客户端的响应头中Set-Cookie就是响应的SessionId。使用cookie来保存服务器端响应的SessionId。**
- 当客户端访问到服务器端getSessionB()，会在请求头中设定sessionId。
- req.getSession(true)从请求头中获取对应的sessionId，查找对应的session。查找不到情况下，则创建一个新的session。
- req.getSession(false)从请求头中获取对应的sessionId，查找不到对应的session则直接报错。

总结

1. 当我们的客户端发送请求到达服务器时创建session，会得到一个sessionId，再将sessionId响应在响应头中。
2. 客户端（浏览器）接受响应头中的sessionId，会将sessionId的值存放在浏览器中。session本质上就是基于cookie实现封装的。
3. 使用同一个浏览器发送请求时，访问同一个服务器端，会在请求头中设定该sessionid的值，服务器端就会从请求头中获取到该sessionid值，查找对应的session。

### Session细节

- 当客户端关闭后，服务器不关闭，获取到的session不是同一个。因为客户端关闭后，cookie对象被销毁，客户端请求服务器会创建新的session。如果需要相同，可以设置cookie的最大存活时间，让cookie持久化保存两次获取session为同一个。

```java
package com.zyf.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: 周杨凡
 * @date: 2022-12-31
 */
@WebServlet("/getSessionA")
public class GetSessionA extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        //获取当前的Session
        session.setAttribute("name","zyf");
        //存入值
        Cookie jsessionid = new Cookie("JSESSIONID", session.getId());
        jsessionid.setMaxAge(60*60);
        resp.addCookie(jsessionid);
        PrintWriter writer = resp.getWriter();
        writer.println("ok");
        writer.close();
    }
}
```

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20221231153416451.png" alt="image-20221231153416451" style="zoom:50%;" />

session什么时候被销毁？

1. 服务器关闭
2. session对象调用invalidate()
3. session默认失效时间30分钟

## session与cookie区别

- session用于存储一次会话的多次请求数据，存在服务器端。
- session可以存储任意类型，任意大小的数据。

1. session存储数据在服务器端，Cookie存储数据在客户端。
2. session没有数据大小限制，Cookie有数据大小限制
3. session数据安全，Cookie数据不安全。

# 过滤器

```java
@WebFilter("/zyf/*")
public class Filter01 implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String name = req.getParameter("name");
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        PrintWriter writer = resp.getWriter();
        resp.setHeader("Content-type", "text/html;utf-8");
        if ("zyf".equals(name)) {
            filterChain.doFilter(req, resp);
            System.out.println("放行");
        }else {
            System.out.println("权限不够");
        }
    }
}
```

**过滤器放行后，后面的代码还会被执行。**

1. 过滤器时客户端与服务器资源文件之间的一道过滤网，在访问资源文件之前，通过一系列的过滤器对请求进行修改、判断等，把不符合规则的请求在中途拦截或修改。也可以对响应进行过滤，拦截或修改。
2. 过滤器可以减少代码的冗余。
3. 应用场景：判断用户是否登录、过滤器请求记录日志、身份验证、权限控制等。

## 过滤器拦截路径配置

1. 所有资源拦截：`@WebFilter("/*")`这里指访问所有资源的时候都会经过过滤器。

   静态资源（css/js/mp4）

2. 具体资源路径拦截：`@WebFilter("/index.jsp")`这里指访问index.jsp的时候会经过过滤器。

3. 具体目录拦截：`@WebFilter("/zyf/*")`这里指访问zyf目录下的所有资源时会经过过滤器

   127.0.0.1:8080/zyf/A

   127.0.0.1:8080/zyf/B

4. 具体后缀名拦截：`@WebFilter("*.jsp")`这里指访问后缀名为.jsp的资源时会经过过滤器。

## 过滤器链

<img src="C:\Users\zhouyangfan\AppData\Roaming\Typora\typora-user-images\image-20230101131602232.png" alt="image-20230101131602232" style="zoom:50%;" />    

默认根据过滤器的首字母进行排序                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   

A过滤器放行后，会执行到B过滤器，B放行后会  执行C过滤器                                                                                                                                                                                                                                                                                                                                             

# maven

**使用maven统一管理维护我们项目中所有的jar包**

原理：

1. 当前我们的项目会引入jar包，先去maven本地仓库中查找该jar包，如果maven本地仓库中有该jar包则直接使用maven本地仓库中的jar包。
2. 如果maven本地仓库中没有该jar包，则会在maven中央仓库中去下载该jar包，再缓存到我们的maven本地仓库中。
3. 如果去中央仓库下载jar包，速度可能会非常慢。
4. 本地仓库→maven私服→中央仓库。
5. 本地仓库适用于在同一台计算机电脑上多个不同的项目共享同一个本地仓库。
6. 私服仓库适用于多个不同的开发者共享使用同一个私服仓库。

## maven常用命令

1. mvn clean 对项目进行清理，删除target目录下编译的内容
2. mvn compile 编译项目源代码
3. mvn test 对项目进行运行测试
4. mvn package 打包文件并存放到项目的target目录下，打包好的文件通常是编译后的class文件
5. mvn install	在本地仓库生成仓库的安装包，可供其他项目引用，同时打包后的文件放到项目的target目录下



**maven项目的目录结构**

```
maven项目
	|--pom.xml                 项目对象模型文件，一个Maven项目必定有pom.xml文件
	|--src
		|--main                项目的主体程序代码目录
		|	|--java            项目的Java程序代码放在这文件夹里
		|	|--resources       项目的配置文件放在这里
		|	|--webapp          项目的web资源放在这里，html，css，js，jsp，音频，图片，视频等等
		|		|--WEB-INF 
		|		|--其它web资源
		|--test                项目的单元测试代码目录
			|--java            项目的单元测试代码
			|--resources       项目单元测试需要的配置文件
```

## maven配置详解

1. 在Maven中坐标是为了定位一个唯一确定的jar包
2. 使用maven中坐标定义项目jar的依赖

maven坐标的构成

groupId：定义当前maven组织名称 com.zyf

artifactId：定义实际项目名称 zyf-web

version：定义当前项目的版本 1.0

```xml
<groupId>com.zyf</groupId>
<artifactId>zyf-maven-web</artifactId>
<version>1.0-SNAPSHOT</version>
```

```xml
<dependencies>
        <!--servlet 自动去远程仓库下载jar ，在缓存到本地仓库中-->
        <dependency>
            <groupId>jakarta.servlet</groupId>
            <artifactId>jakarta.servlet-api</artifactId>
            <version>5.0.0</version>
        </dependency>
</dependencies>
```
