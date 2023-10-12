# 项目热部署

在开发过程中，每次修改了代码或资源文件后，为了看到效果，我们通常都需要重启应用。这样的过程往往是耗时的，因为需要等待应用停止及启动。Spring Boot提供了一种"热部署"（或称"热重载"）的功能，称为Spring Boot DevTools（开发者工具），能够使文件改变之后自动重新加载。

### 什么是热部署？

热部署是指在不重启应用的情况下，更新/替换某些部分（例如类文件或资源文件），使之立即生效的技术。在Java开发中，热部署技术能够大大加速开发进度，因为开发人员可以立即看到他们代码修改的效果，无需重启整个应用。

### Spring Boot DevTools

Spring Boot通过提供一个额外的工具集DevTools来支持热部署。要使用DevTools，你需要在你的项目中添加相应的依赖。如果你使用的是Maven，可以在`pom.xml`文件中添加如下依赖：

```xml
<dependencies>
    ...
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <optional>true</optional>
    </dependency>
    ...
</dependencies>
```

若使用Gradle，可以在`build.gradle`文件中添加：

```gradle
dependencies {
    ...
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    ...
}
```

### 特点和功能

- **代码更改即时生效**：只要保存代码，DevTools就会重新加载应用，你几乎可以立即看到修改的效果。

- **自动重启**：每当在类路径上找到文件更改时，应用就会自动重启。它使用了两个类加载器。一个加载不会改变的类（例如第三方JARs），另一个加载会改变的类（即你的项目类）。大多数情况下，这种方式能够提供超快的重启时间。

- **LiveReload**：DevTools集成了一个LiveReload服务器，这意味着你可以在保存文件时自动刷新浏览器。要在浏览器端启用LiveReload功能，你可以安装一个浏览器扩展或在你的HTML中手动添加脚本。

- **全局设置**：你可以在你的HOME目录中添加一个`.spring-boot-devtools.properties`文件来配置全局的DevTools设置（这些设置会应用到你机器上的所有项目）。

- **远程开发**：你也可以使用DevTools进行远程开发，但这通常涉及一些额外的设置工作。

DevTools不仅提高了本地开发的效率，而且通过支持远程更新和其他一些开发时的辅助功能，成为一个相当强大的工具。

# DevTools配置

- 修改build.gradle配置文件，在公共模块依赖处进行依赖配置：

```
compile("org.springframework.boot:spring-boot-devtools")
```

![image-20231009211745432](assets/image-20231009211745432.png)

- IDEA配置：File -> Settings -> Build, Execution, Deployment ->Compiler

<img src="assets/image-20231009212036590.png" alt="image-20231009212036590" style="zoom:67%;" />

- IDEA配置2：File | Settings | Advanced Settings

![image-20231009214256936](assets/image-20231009214256936.png)

- 重启IDEA

# 验证热部署是否生效

- 启动项目

![image-20231009214928299](assets/image-20231009214928299.png)

- 客户端访问

![image-20231009215025328](assets/image-20231009215025328.png)

- 清空控制台，修改代码，后保存

![image-20231009215126490](assets/image-20231009215126490.png)

- 当点击保存，Springboot项目重启

![image-20231009215306768](assets/image-20231009215306768.png)

- 客户端访问

![image-20231009215329306](assets/image-20231009215329306.png)

可以看到SpringBoot自动重启成功，热部署完毕。