---
typora-copy-images-to: ./
---

## **Gradle 概述**

Gradle 是一个开源的构建自动化工具，专门设计用于支持多项目构建。它基于Groovy编程语言，允许用户使用更加灵活的方式来描述构建逻辑。Gradle 不仅可以用于Java项目，还支持多种语言和平台，如 C/C++, Groovy, Kotlin, Android, 和 Scala。

## **Gradle 相较于 Maven 的优势**

1. **灵活性**: 
    - **DSL**: Gradle 使用领域特定语言(Domain-Specific Language, DSL)，允许用户以更自然的方式描述构建逻辑，而 Maven 使用基于 XML 的 pom.xml 文件，可能导致过于繁琐和冗长的配置。
    - **可定制性**: 由于其编程性质，Gradle 允许更高程度的自定义。

2. **性能**: 
    - **增量构建**: Gradle 采用增量构建，意味着只重新构建改变的部分，而不是每次都构建整个项目，从而加快构建速度。
    - **并行构建**: Gradle 可以并行地处理不相互依赖的任务，进一步提高构建速度。

3. **缓存**: 
    - Gradle 提供了更先进的缓存机制，包括远程缓存，这可以极大地提高在持续集成环境中的构建速度。

4. **深度API**:
    - Gradle 提供了一套丰富的API，允许插件开发者访问和扩展构建和任务的内部逻辑。

5. **多项目构建**: 
    - Gradle 在多项目构建方面提供了更好的支持和更强大的依赖管理。

6. **跨语言和平台支持**: 
    - 除了 Java, Gradle 还支持多种其他语言和平台，包括 Android，使其成为 Android 官方建议的构建工具。

7. **Kotlin DSL**: 
    - 除了基于 Groovy 的 DSL，Gradle 还提供了基于 Kotlin 的 DSL，为用户提供了更多的选择。

## Gradle安装与配置

- 下载二进制安装包https://gradle.org/releases/，并解压缩
- 将bin目录添加到环境变量中
- 在命令行中输入`gradle -v`查看是否安装成功

## Gradle项目创建并构建

构建Gradle项目，构建系统选择Gradle

<img src="F:\GITHUB\JavaWebOdyssey\Gradle\01_Gradle概述\image-20231005165923580.png" alt="image-20231005165923580" style="zoom: 67%;" />

项目目录：

```
F:.
│  .gitignore
│  build.gradle
│  gradlew
│  gradlew.bat
│  settings.gradle
│
├─.gradle
│  ├─8.2
│  │  │  gc.properties
│  │  │
│  │  ├─checksums
│  │  │      checksums.lock
│  │  │
│  │  ├─dependencies-accessors
│  │  │      dependencies-accessors.lock
│  │  │      gc.properties
│  │  │
│  │  ├─executionHistory
│  │  │      executionHistory.lock
│  │  │
│  │  ├─fileChanges
│  │  │      last-build.bin
│  │  │
│  │  ├─fileHashes
│  │  │      fileHashes.lock
│  │  │
│  │  └─vcsMetadata
│  ├─buildOutputCleanup
│  │      buildOutputCleanup.lock
│  │      cache.properties
│  │
│  └─vcs-1
│          gc.properties
│
├─.idea
│      .gitignore
│      compiler.xml
│      gradle.xml
│      jarRepositories.xml
│      misc.xml
│      vcs.xml
│      workspace.xml
│
├─gradle
│  └─wrapper
│          gradle-wrapper.jar
│          gradle-wrapper.properties
│
└─src
    ├─main
    │  ├─java
    │  │  └─org
    │  │      └─example
    │  │              Main.java
    │  │
    │  └─resources
    └─test
        ├─java
        └─resources
```

1. **根目录**（F:.）: 这是整个项目的根目录。

   - `.gitignore`: 用于定义要在版本控制中忽略的文件和目录的规则。
   - `build.gradle`: 项目的 Gradle 构建脚本，定义了项目的依赖和构建任务。
   - `gradlew` 和 `gradlew.bat`: Gradle Wrapper 脚本，允许在不安装 Gradle 的情况下构建项目。
   - `settings.gradle`: Gradle 项目设置文件，用于配置项目的结构和模块。

2. **.gradle**: 这是 Gradle 构建工具的工作目录，包含了与构建相关的文件和信息。

   - `.gradle/8.2`: 存储与 Gradle 版本 8.2 相关的构建数据和缓存。
   - `.gradle/buildOutputCleanup`: 存储构建输出清理相关的信息。
   - `.gradle/vcs-1`: 存储与版本控制系统（VCS）相关的信息。

3. **.idea**: 这是 IntelliJ IDEA 集成开发环境的项目文件夹，包含有关项目配置的文件。

4. **gradle**: 这个目录通常包含与 Gradle 构建系统相关的文件。

   - `gradle/wrapper`: 包含 Gradle Wrapper 相关文件，允许项目使用特定版本的 Gradle 进行构建，而无需手动安装 Gradle。

5. **src**: 这是项目源代码和资源文件的根目录。

   - ```
     src/main
     ```

      这是主要的源代码和资源目录，包括应用程序的主要功能代码和资源文件。

     - `src/main/java`: Java 源代码目录，包含项目的主要源代码文件。
     - `src/main/resources`: 项目的主要资源文件目录，如配置文件、模板等。

   - ```
     src/test
     ```

     这是测试代码和资源的目录，用于编写单元测试和集成测试。

     - `src/test/java`: 测试用例的 Java 源代码目录。
     - `src/test/resources`: 测试用例的资源文件目录。

使用`gradle build`命令构建项目：

```
PS F:\GITHUB\JavaWebOdyssey\Gradle\01_Gradle概述\first-gradle-app> gradle build

BUILD SUCCESSFUL in 595ms
2 actionable tasks: 2 executed
```

可以看到项目目录中多了build文件夹。

在 Gradle 项目中，当你运行 `gradle build` 命令时，会在项目根目录下生成一个名为 `build` 的文件夹。这个文件夹的主要作用是存储构建过程中生成的中间和最终输出文件。

- **build/classes**: 这个目录包含了编译后的 Java 类文件。在 Java 项目中，编译源代码后的 `.class` 文件通常存储在这里。
- **build/libs**: 如果你的项目是一个可执行的 JAR 文件或 WAR 文件（例如，Spring Boot 应用程序），那么生成的 JAR 或 WAR 文件将存储在这个目录中。
- **build/resources**: 这个目录包含了项目的资源文件，如配置文件、模板等。这些资源文件通常在构建过程中被复制到相应的输出目录。
- **build/tmp**: 这个目录包含了构建过程中的临时文件和缓存。例如，Gradle 可能会将一些构建缓存存储在这里，以便加快后续构建的速度。
- **build/reports**: 如果你配置了测试任务，测试报告通常会生成在这个目录下。测试报告包括测试结果和代码覆盖率等信息。
- **build/scripts**: 如果你的项目包含自定义脚本或插件，构建过程中生成的脚本文件可能会存储在这个目录中。
- **build/distributions**: 如果你的项目包含分发任务，例如 `distribution` 插件，那么生成的分发包（如 ZIP 或 TAR 文件）通常会存储在这里。

## Gradle其他相关命令

1. **构建项目**:
   - `gradle build`: 构建项目，编译源代码，运行测试，并生成可部署的输出文件。
2. **清理构建**:
   - `gradle clean`: 删除构建过程中生成的临时文件和输出目录。
3. **运行测试**:
   - `gradle test`: 运行项目中的单元测试。
4. **运行特定任务**:
   - `gradle <task-name>`: 运行指定名称的 Gradle 任务。例如，`gradle compileJava` 将只编译 Java 源代码。
5. **列出所有可用任务**:
   - `gradle tasks`: 列出项目中所有可用的 Gradle 任务，包括默认任务和自定义任务。
6. **构建并运行应用程序**:
   - `gradle run`: 构建并运行应用程序，如果 Gradle 构建文件中有配置的话。
7. **生成项目文档**:
   - `gradle javadoc`: 生成 Java 代码的 Javadoc 文档。
8. **依赖管理**:
   - `gradle dependencies`: 显示项目的依赖关系。
9. **查看构建日志**:
   - `gradle build --info`: 显示详细的构建信息，包括任务的执行顺序和各种详细日志。
   - `gradle build --debug`: 显示调试级别的构建信息。
10. **自定义任务**:
    - 你可以在 Gradle 构建文件中定义自己的任务，然后使用 `gradle <task-name>` 命令运行它们。
11. **使用 Wrapper**:
    - `./gradlew`（Linux/macOS）或 `gradlew.bat`（Windows）: Gradle Wrapper 是一个脚本，可以自动下载和使用项目特定版本的 Gradle，而不需要手动安装 Gradle。这是一种推荐的方式来保证构建的一致性。

## Gradle构建过程

1. **初始化阶段（Initialization）**:
   - 在这个阶段，Gradle决定哪些项目将参与构建过程。对于单个项目，这是一个简单的过程。但是，对于多模块的项目（即一个包含多个子项目的项目），Gradle需要确定哪些子项目参与构建，并为它们创建相应的项目实例。
2. **配置阶段（Configuration）**:
   - 在此阶段，Gradle解析各个项目的`build.gradle`文件。这意味着它会执行在这些文件中的所有脚本代码。在这个阶段，所有的任务都会被创建并配置，但是这些任务的行为还没有被执行。
3. **执行阶段（Execution）**:
   - 根据用户指定的任务，Gradle在此阶段真正开始执行任务。不过，它并不是盲目地执行所有任务，而是首先确定任务之间的依赖关系，然后按照正确的顺序执行它们。这样确保了在执行某个任务之前，它所依赖的所有其他任务都已经完成。
4. **任务（Task）**:
   - 在Gradle中，几乎所有的构建活动都是由任务来完成的。任务代表了构建过程中的一个原子操作，比如编译源代码、运行测试或生成文档。任务可以依赖其他任务，这意味着它们在执行时需要按照特定的顺序。
5. **插件（Plugins）**:
   - 插件是Gradle的一个重要组成部分。它们为构建脚本提供了代码和约定，这使得开发者可以不用重复编写常见的构建逻辑。例如，Java插件会添加用于编译Java代码、运行测试和生成JAR文件的任务。
6. **生命周期回调和钩子（Lifecycle Callbacks and Hooks）**:
   - Gradle提供了一系列的生命周期回调和钩子，使得开发者可以在特定的时间点插入自己的逻辑，例如在一个任务执行之前或之后。

总的来说，Gradle的构建过程是一个从初始化到配置，再到执行的过程，它通过执行用户定义在`build.gradle`文件中的脚本来完成。同时，它还提供了大量的插件和钩子，使得构建过程既灵活又强大。

## Gradle build

- 在gradle项目src目录中添加`com.zhouyf.test`包

- 在`com.zhouyf.test`包中创建`test01`类并编写测试代码

  ```java
  package com.zhouyf.test;
  
  public class Test01 {
      public static void main(String[] args) {
          System.out.println("Hello, world!!!!");
      }
  }
  ```

- 点击build命令进行构建

  <img src="F:\GITHUB\JavaWebOdyssey\Gradle\01_Gradle概述\image-20231006194547174.png" alt="image-20231006194547174" style="zoom:50%;" />

- 项目目录中生成jar包

  <img src="F:\GITHUB\JavaWebOdyssey\Gradle\01_Gradle概述\image-20231006194737248.png" alt="image-20231006194737248" style="zoom: 67%;" />

- 运行jar包，查看构建效果

  ```bash
  java -classpath .\build\libs\first-gradle-app-1.0-SNAPSHOT.jar com.zhouyf.test.Test01
  ```

- 控制台打印输出

  ![image-20231006195018287](F:\GITHUB\JavaWebOdyssey\Gradle\01_Gradle概述\image-20231006195018287.png)

- 可以看到项目已经构建成功


## Gradle projects

> 在 Gradle 中，`Project` 是构建的基本单位。每个构建至少包含一个项目，但一个构建可以包含多个项目。这些项目可以表示如一个库、一个应用程序、一个Web应用程序等的不同组件。

1. **Project**：在 Gradle 中，一个 `Project` 对应于一个构建文件。这个构建文件定义了一组“任务”（Tasks），这些任务可以是编译、打包、测试、部署等。
2. **多项目构建**：Gradle 支持单项目和多项目构建。在多项目构建中，每个子项目都是一个 `Project`，它们共同组成一个整体的构建。
3. **属性和方法**：`Project` 对象有很多属性和方法，它们允许你配置和操作项目。例如，`project.name` 返回项目的名称，`project.dependencies` 允许你配置项目的依赖等。
4. **插件**：`Project` 可以应用插件，这些插件为项目添加特定的构建逻辑、任务和功能。例如，Java 插件会添加编译、测试和打包的任务。

### projects任务

`projects` 任务是一个内置的任务，当在多项目构建中运行它时，它会列出所有的子项目。这是一个非常有用的任务，可以帮助你了解构建中所有的子项目。

<img src="F:\GITHUB\JavaWebOdyssey\Gradle\01_Gradle概述\image-20231006195958002.png" alt="image-20231006195958002" style="zoom: 67%;" />

- 创建子项目（创建Module）

  <img src="F:\GITHUB\JavaWebOdyssey\Gradle\01_Gradle概述\image-20231006200739052.png" alt="image-20231006200739052" style="zoom:67%;" />

<img src="F:\GITHUB\JavaWebOdyssey\Gradle\01_Gradle概述\image-20231006201130653.png" alt="image-20231006201130653" style="zoom:67%;" />

- 执行projects任务，显示已经有了子项目

  

  <img src="F:\GITHUB\JavaWebOdyssey\Gradle\01_Gradle概述\image-20231006201258242.png" alt="image-20231006201258242" style="zoom: 50%;" />

![image-20231006201542688](F:\GITHUB\JavaWebOdyssey\Gradle\01_Gradle概述\image-20231006201542688.png)

### org.gradle.api.Project

在 Gradle 中，`Project` 对象代表一个正在构建的组件，这可以是任何东西，例如一个 JAR 文件、一个 WAR 文件、一个应用程序等。每个 `Project` 都关联一个 `build.gradle` 文件，该文件定义了如何构建该项目。

以下是一些关于 `org.gradle.api.Project` 的详细信息：

1. **实例化**：当你运行 Gradle 构建时，Gradle 会为 `build.gradle` 文件中定义的每个项目实例化一个 `Project` 对象。这个对象为你提供了许多方法和属性，你可以用它们配置和操作你的构建。
2. **属性**：`Project` 对象有很多内置属性，例如 `project.name`（项目的名称）、`project.projectDir`（项目的目录）等。你可以在 `build.gradle` 文件中直接使用这些属性。
3. **方法**：`Project` 对象也提供了一系列的方法来操作和配置构建。例如，`project.dependencies` 方法允许你为项目定义依赖。
4. **任务**：每个 `Project` 对象都有一个任务集合。你可以使用 `project.tasks` 来访问这些任务。例如，`project.tasks.compileJava` 会访问名为 `compileJava` 的任务。
5. **插件**：你可以在 `Project` 对象上应用插件。这些插件为项目添加任务和功能。例如，当你应用 Java 插件时，它会为项目添加编译、测试和打包的任务。
6. **多项目构建**：在一个多项目构建中，每个子项目也都是一个 `Project` 对象。这意味着你可以为每个子项目配置独立的构建逻辑。
7. **评估阶段**：在 Gradle 的生命周期中，有一个评估阶段。在这个阶段，Gradle 会读取和评估所有的 `build.gradle` 文件，并实例化相应的 `Project` 对象。这是你在 `build.gradle` 文件中编写的所有代码被执行的地方。

关键点：

- **隐式的 `project` 变量**：在 `build.gradle` 文件中，你不需要明确地引用 `Project` 对象。Gradle 提供了一个隐式的 `project` 变量，你可以使用它来访问 `Project` 对象的属性和方法。实际上，当你在 `build.gradle` 中写代码时，大多数时候都是在 `Project` 的上下文中。
- **简化的语法**：由于这种隐式的上下文，你通常可以省略 `project` 前缀。例如，`project.name` 和 `name` 在大多数情况下是等价的。
- **任务和插件**：你可以通过 `project` 变量定义新的任务或应用插件。例如，`project.task('myTask')` 定义了一个新任务，而 `project.apply plugin: 'java'` 会应用 Java 插件。
- **依赖管理**：你也可以通过 `project` 变量来配置项目的依赖。例如，`project.dependencies` 允许你定义和管理项目的依赖关系。
- **其他属性和方法**：`Project` 对象有很多其他的属性和方法，你可以在 `build.gradle` 文件中使用它们来配置和定制你的构建过程。

## Gradle 仓库 (Repositories) 

### 声明仓库

在 `build.gradle` 文件中，你可以使用 `repositories` 闭包来声明你的仓库。

```groovy
repositories {
    mavenCentral()
}
```

### 常见的仓库类型

**Maven Central**：这是最常用的 Maven 仓库，包含了大量的开源 Java 库

```
repositories {
    mavenCentral()
}
```

**本地 Maven 仓库**：如果你有一个本地的 Maven 仓库，例如 Nexus 或 Artifactory，你可以这样配置：

```
repositories {
    mavenLocal()  // 这会指向默认的 ~/.m2/repository 或你在 Maven settings.xml 中定义的目录
}
```

### 使用中央仓库添加依赖

- 在build.gradle中添加mybatis依赖

  ![image-20231007130857202](F:\GITHUB\JavaWebOdyssey\Gradle\01_Gradle概述\image-20231007130857202.png)

- 刷新Gradle，将会从中央maven仓库中下载mybatis相关依赖

  ![image-20231007131256228](F:\GITHUB\JavaWebOdyssey\Gradle\01_Gradle概述\image-20231007131256228.png)

- 在`C:\Users\zhouy\.gradle\caches\modules-2\files-2.1`中可以查看mybatis包。

<img src="F:\GITHUB\JavaWebOdyssey\Gradle\01_Gradle概述\image-20231007131450413.png" alt="image-20231007131450413" style="zoom: 67%;" />
