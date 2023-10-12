# dependency-management

当前项目都是基于`Gradle`实现的程序开发，那么既然要基于`Gradle`程序开发，就必须熟悉`Gradle`对`SpringBoot`项目支持（这也是Maven不具备的地方）

```groovy
dependencies {
    testImplementation platform('org.junit:junit-bom:5.9.1')
    testImplementation 'org.junit.jupiter:junit-jupiter'
    implementation group: 'org.springframework.boot', name: 'spring-boot-starter-web', version: '2.4.3'
}
```

如果按照构建你工具的标准配置形式来讲，此时的配置一定是不合理的，原因就在于将版本号编写在依赖库引入之外。这样如果有需要就可以直接修改版本号而实现版本的更新。

## dependency-management插件

```groovy
buildscript {
    repositories {
        maven {url 'https://repo.spring.io/libs-milestone'}
    }
    dependencies {
        classpath 'org.springframework.boot:spring-boot-gradle-plugin:2.4.3'
    }
}

plugins {
    id 'java'
}

apply plugin: 'org.springframework.boot'
apply plugin: 'io.spring.dependency-management'

group = 'com.zhouyf'
version = '1.0-SNAPSHOT'

sourceCompatibility = 11
targetCompatibility = 11

repositories {
    mavenCentral()
}



dependencies {
    testImplementation platform('org.junit:junit-bom:5.9.1')
    testImplementation 'org.junit.jupiter:junit-jupiter'
    implementation 'org.springframework.boot:spring-boot-starter-web'
}

test {
    useJUnitPlatform()
}
```

buildscript块：

```
buildscript {
    repositories {
        maven {url 'https://repo.spring.io/libs-milestone'}
    }
    dependencies {
        classpath 'org.springframework.boot:spring-boot-gradle-plugin:2.4.3'
    }
}
```

- **repositories** 块定义了用于拉取 buildscript 依赖的仓库的位置。在这里，它指定了 Spring Milestone Repository 作为要检索的依赖项的源。
- **dependencies** 块定义了构建脚本自身的依赖关系。这里，它添加了 Spring Boot Gradle 插件 2.4.3 版本到构建脚本的类路径。

`Apply Plugin`块：

```
apply plugin: 'org.springframework.boot'
apply plugin: 'io.spring.dependency-management'
```

**`apply plugin`** 语句用于应用插件到项目中。这里两个插件被应用了：

- `org.springframework.boot` - Spring Boot 的 Gradle 插件，它提供了很多用于简化使用 Spring Boot 的功能，例如创建可执行的 JAR 文件。
- `io.spring.dependency-management` - Spring Dependency Management 插件，它允许你控制项目依赖的版本。它可以与 Spring Boot 插件一起使用，这样 Spring Boot 插件将管理你项目中的所有依赖的版本。

**现在与SpringBoot有关的版本编号都交给了`spring-boot-gradle-plugin`定义**。

![image-20231008212950060](assets/image-20231008212950060.png)