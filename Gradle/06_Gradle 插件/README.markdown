# 插件

在 Gradle 构建系统中，插件（Plugins）扮演着至关重要的角色，因为它们负责向项目添加特定的功能和任务。插件通常提供了构建项目所需的一组相关功能，它们可以配置项目对象、添加任务和扩展功能，以提供新的或自定义的构建逻辑。

### Gradle 插件的主要作用

1. **添加任务（Tasks）**

插件可以添加一个或多个任务到你的项目中。这些任务完成具体的构建过程，比如编译代码、打包应用、运行测试等。

2. **提供和扩展构建功能**

插件可能会提供并扩展构建逻辑和功能。例如，Java插件会添加用于编译Java代码的任务和配置，Android插件会添加用于构建Android应用的任务和配置。

3. **配置项目**

插件可以自动配置项目的某些属性，以便更加便捷的构建和测试项目。例如，Java插件会为你的项目配置一些常用的目录结构（如`src/main/java`作为源代码目录）。

4. **依赖管理**

一些插件（例如 Java 插件或 Maven Publish 插件）允许开发者更方便地管理依赖，包括声明依赖、配置仓库和发布产物等。

### 自定义插件

- 创建新项目

  <img src="assets/image-20231008173111272.png" alt="image-20231008173111272" style="zoom:67%;" />

- 在b`uild.gradle`中编写自定义插件

  ```groovy
  class Myplugin1 implements Plugin<Project>{
      @Override
      void apply(Project project) {
          project.task("myPluginTask1"){
              doLast {
                  println('myPluginTask1 success!!!')
              }
          }
      }
  }
  
  apply plugin: Myplugin1
  ```

  - `MyPlugin1` 是一个自定义插件，它实现了 `Plugin<Project>` 接口。
  - `apply` 方法是插件的入口点。当插件被应用到项目时，这个方法被调用。
  - `project.task('myplugintask1')` 创建了一个新的任务，并命名为 `myplugintask1`。
  - `doLast` 定义了一个动作，当任务 `myplugintask1` 被执行时，这个动作将打印一条消息到控制台。

![image-20231008181742154](assets/image-20231008181742154.png)

- 执行任务

  ```bash
  > Task :myPluginTask1
  myPluginTask2 success!!!
  
  BUILD SUCCESSFUL in 10s
  1 actionable task: 1 executed
  ```

### buildSrc中定义插件

- 新建文件夹：`buildSrc/src/main/groovy/MyPlugin2.groovy`，并在`buildSrc/src`下新建`build.gradle`

  <img src="assets/image-20231008181909762.png" alt="image-20231008181909762" style="zoom:67%;" />

- `MyPlugin2.groovy`中编写自定义插件代码

  ```
  import org.gradle.api.Plugin
  import org.gradle.api.Project
  
  
  class MyPlugin2 implements Plugin<Project>{
      @Override
      void apply(Project project) {
          project.task('myPluginTask2'){
              doLast {
                  println('myPluginTask2 success!!!')
              }
          }
      }
  }
  ```

- 在全局`build.gradle`文件中应用插件，并刷新项目

  ![image-20231008182243991](assets/image-20231008182243991.png)

- 执行任务

  ```bash
  > Task :myPluginTask2
  myPluginTask2 success!!!
  
  BUILD SUCCESSFUL in 82ms
  3 actionable tasks: 1 executed, 2 up-to-date
  18:23:29: Execution finished 'myPluginTask2'.
  ```

### 两种自定义插件的区别

在 Gradle 中，你可以选择在 `build.gradle` 文件中直接定义插件，或者在 `buildSrc` 目录中定义插件。两种方法主要的区别体现在插件的作用范围和代码组织上。

#### 直接在 `build.gradle` 中定义插件

当你直接在 `build.gradle` 中定义插件时：
- **作用范围限制**：这个插件只在定义它的 `build.gradle` 文件中可用。
- **便利性**：对于一些简单和特定项目的自定义逻辑非常方便。
- **可维护性问题**：如果你有多个 `build.gradle` 文件或多模块项目，并且需要在多个地方复用插件逻辑，这种方式就会产生代码重复。

```groovy
// build.gradle
class MyPlugin implements Plugin<Project>{
    void apply(Project project) {
        project.task('myTask') {
            doLast {
                println 'Hello from MyPlugin'
            }
        }
    }
}

apply plugin: MyPlugin
```

#### 在 `buildSrc` 中定义插件

使用 `buildSrc` 目录定义插件：
- **全局可用**：在 `buildSrc` 中定义的插件和其他构建逻辑对整个构建脚本全局可用。
- **代码组织**：你可以将构建逻辑组织得更加结构化，并且可以利用多文件组织你的代码。
- **编译检查**：`buildSrc` 中的代码会在主构建脚本之前被编译，这意味着你可以得到编译时的类型检查和自动完成功能。
- **依赖管理**：你可以为 `buildSrc` 中的构建逻辑声明依赖关系。

```groovy
// buildSrc/src/main/groovy/MyPlugin.groovy
import org.gradle.api.Plugin
import org.gradle.api.Project

class MyPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.task('myTask') {
            doLast {
                println 'Hello from MyPlugin'
            }
        }
    }
}

// build.gradle
apply plugin: MyPlugin
```
