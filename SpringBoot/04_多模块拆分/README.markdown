- 创建Gradle项目：microboot

  ![image-20231009134054823](assets/image-20231009134054823.png)

- 创建gradle.properties资源文件配置相关的项目属性

  ```properties
  project_group=com.zhouyf
  project_version=1.0.0
  project_jdk=11
  ```

- 创建dependencies.gradle，这个文件实现所有项目之中依赖库版本的定义

```groovy
ext.versions = [ //定义版本号
    springboot                          : '2.4.3' // SpringBoot版本号
]


ext.libraries = [ //定义所有的依赖库
    'spring-boot-gradle-plugin': "org.springframework.boot:spring-boot-gradle-plugin:${versions.springboot}"
]
```

- 修改build.gradle配置文件，引入相关的子配置文件

```groovy
buildscript { 						// 定义脚本使用资源
    apply from: 'dependencies.gradle' // 引入所需要的依赖库文件
    repositories { 						// 脚本资源仓库
        maven { url 'https://maven.aliyun.com/repository/public' }
    }
    dependencies { 						// 依赖库
        classpath libraries.'spring-boot-gradle-plugin'
    }
}

group project_group
version project_version
apply from: 'dependencies.gradle' // 引入所需要的依赖库文件
def env = System.getProperty("env") ?: 'dev' // 获取env环境属性

subprojects {   // 子模块
    apply plugin: 'java' // 引入之前的插件
    apply plugin: 'org.springframework.boot' // 引入之前的插件
    apply plugin: 'io.spring.dependency-management' // 引入之前的插件
    sourceCompatibility = project_jdk // 本次项目都是基于JDK-11版本编写的
    targetCompatibility = project_jdk // 本次项目都是基于JDK-11版本编写的
    repositories {  // 配置Gradle仓库
        def ALIYUN_REPOSITORY_URL = 'http://maven.aliyun.com/nexus/content/groups/public'
        def ALIYUN_JCENTER_URL = 'http://maven.aliyun.com/nexus/content/repositories/jcenter'
        all {
            ArtifactRepository repo ->
                if (repo instanceof MavenArtifactRepository) {
                    def url = repo.url.toString()
                    if (url.startsWith('https://repo1.maven.org/maven2')) {
                        project.logger.lifecycle "Repository ${repo.url} replaced by $ALIYUN_REPOSITORY_URL."
                        remove repo
                    }
                    if (url.startsWith('https://jcenter.bintray.com/')) {
                        project.logger.lifecycle "Repository ${repo.url} replaced by $ALIYUN_JCENTER_URL."
                        remove repo
                    }
                }
        }
        maven { url ALIYUN_REPOSITORY_URL } // 设置阿里云仓库
        maven { url ALIYUN_JCENTER_URL } // 设置阿里云仓库
    }
    dependencies {  // 公共依赖库管理

    }
    sourceSets {    // 源代码目录配置
        main { // main及相关子目录配置
            java { srcDirs = ['src/main/java'] }
            resources { srcDirs = ['src/main/resources', "src/main/profiles/$env"] }
        }
        test { // test及相关子目录配置
            java { srcDirs = ['src/test/java'] }
            resources { srcDirs = ['src/test/resources'] }
        }
    }
    test {  // 配置测试任务
        useJUnitPlatform() // 使用JUnit测试平台
    }
    // 最终生成的jar文件名称：baseName-version-classifier.extension
    task sourceJar(type: Jar, dependsOn: classes) { // 源代码的打包任务
        archiveClassifier = 'sources' // 设置文件的后缀
        from sourceSets.main.allSource // 所有源代码的读取路径
    }
    task javadocTask(type: Javadoc) { // JavaDoc文档打包任务
        options.encoding = 'UTF-8' // 设置文件编码
        source = sourceSets.main.allJava // 定义所有的Java源代码
    }
    task javadocJar(type: Jar, dependsOn: javadocTask) { // 先生成JavaDoc再打包
        archiveClassifier = 'javadoc' // 文件标记类型
        from javadocTask.destinationDir // 通过JavadocTask任务中找到目标路径
    }
    tasks.withType(Javadoc) {   // 文档编码配置
        options.encoding = 'UTF-8' // 定义编码
    }
    tasks.withType(JavaCompile) {   // 编译编码配置
        options.encoding = 'UTF-8' // 定义编码
    }
    artifacts { // 最终的打包的操作任务
        archives sourceJar // 源代码打包
        archives javadocJar // javadoc打包
    }
    gradle.taskGraph.whenReady {    // 在所有的操作准备好后触发
        tasks.each { task ->    // 找出所有的任务
            if (task.name.contains('test')) {   // 如果现在发现有test任务
                // 如果将enabled设置为true表示要执行测试任务，如果设置为false表示不执行测试任务
                task.enabled = true
            }
        }
    }
    [compileJava, compileTestJava, javadoc]*.options*.encoding = 'UTF-8' // 编码配置
}
```

- 创建子模块microboot-common

  <img src="assets/image-20231009135824184.png" alt="image-20231009135824184" style="zoom:50%;" />

- 创建子模块microboot-web

  <img src="assets/image-20231009140623219.png" alt="image-20231009140623219" style="zoom:50%;" />

- 项目结构

  <img src="assets/image-20231009140711978.png" alt="image-20231009140711978" style="zoom:50%;" />

- 添加子模块配置

  ```groovy
  project('microboot-web') { // 子模块
      dependencies { // 配置子模块依赖
          compile(project(':microboot-common')) // 引入其他子模块
          compile("org.springframework.boot:spring-boot-starter-web")
      }
  }
  
  project('microboot-common') { // 子模块
      dependencies { // 配置子模块依赖
      }
  }
  ```

- 在microboot-common子项目中编写主类

  ```java
  import org.springframework.boot.SpringApplication;
  import org.springframework.boot.autoconfigure.SpringBootApplication;
  
  @SpringBootApplication
  public class MyApplication {
      public static void main(String[] args) {
          SpringApplication.run(MyApplication.class, args);
      }
  }
  ```

- 在microboot-common子项目中编写一个控制器来测试

  ```java
  package org.zhouyf;
  
  import org.springframework.web.bind.annotation.GetMapping;
  import org.springframework.web.bind.annotation.RestController;
  
  @RestController
  public class MyController {
      @GetMapping("/")
      public String hello() {
          return "Hello, World!";
      }
  }
  ```

- 启动项目，客户端发送请求

  ![image-20231009143623216](assets/image-20231009143623216.png)