- 创建Gradle项目

<img src="assets/image-20231008195801949.png" alt="image-20231008195801949" style="zoom:67%;" />

- `build.gradle`->定义源代码与目标字节码的 Java 版本兼容性

  ```
  sourceCompatibility = 11
  targetCompatibility = 11
  ```

- 引入SpringBoot入门级依赖，并手工进行刷新

  ```groovy
  implementation group: 'org.springframework.boot', name: 'spring-boot-starter-web', version: '2.4.3'
  ```

  ![image-20231008200231160](assets/image-20231008200231160.png)

`spring-boot-starter-web` 是用于构建 Web 与 RESTful 应用的入门级依赖。当你包含这个 starter 时，它会提供用于构建 web 层的多种功能，例如：

- 嵌入的 Tomcat 作为默认的 Servlet 容器
- Spring MVC 作为默认的 web 框架
- JSON 的自动序列化/反序列化（使用 Jackson）
- 对 RESTful Web 服务的支持
- 以及更多...

