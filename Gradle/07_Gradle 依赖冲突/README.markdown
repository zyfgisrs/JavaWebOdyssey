# Gradle解决依赖冲突

Gradle 在处理依赖冲突时采取了一些策略来尽可能自动化地解决这些冲突。以下是 Gradle 在解决依赖冲突时所使用的主要策略：

### 1. **最新版本策略**
默认情况下，Gradle 使用“最新版本策略”来解决依赖冲突。这意味着如果一个项目同时依赖了多个不同版本的相同库，Gradle 会选择这些版本中的最新版本。这个策略基于这样一种信念：较新的库版本通常包含了较旧版本中的所有功能，还可能包括一些修复和改进。

例如：
- 你的项目直接依赖 `LibraryA` 版本 `1.0` 和 `LibraryB` 版本 `1.2`。
- `LibraryA` 又依赖 `LibraryC` 版本 `3.1`。
- `LibraryB` 又依赖 `LibraryC` 版本 `3.3`。

在这种情况下，依据最新版本策略，Gradle 会选择 `LibraryC` 的 `3.3` 版本，因为这是最新的。

### 2. **强制依赖**
在某些情况下，你可能希望覆盖 Gradle 的默认依赖解析策略，强制使用一个特定版本的依赖。你可以在依赖声明中使用 `force` 属性来实现这一点。

```gradle
configurations.all {
    resolutionStrategy {
        force 'com.example:LibraryC:3.1'
    }
}
```
在上述代码片段中，无论任何其他条件如何，`LibraryC` 的 `3.1` 版本总是会被使用。

### 3. **依赖排除**
在一些情况下，你可能希望完全排除一个冲突的依赖。这可以通过 `exclude` 语句来实现。

```gradle
dependencies {
    implementation('com.example:LibraryA:1.0') {
        exclude group: 'com.example', module: 'LibraryC'
    }
}
```
在这个示例中，`LibraryA` 的依赖 `LibraryC` 被完全排除了。

### 4. **依赖解析规则**
你也可以使用依赖解析规则去自定义如何选择和匹配依赖。

```gradle
configurations.all {
    resolutionStrategy.eachDependency { DependencyResolveDetails details ->
        if (details.requested.group == 'com.example' && details.requested.name == 'LibraryC') {
            details.useVersion '3.1'
        }
    }
}
```
在上述例子中，每当解析到 `LibraryC` 这个依赖时，无论请求的版本是什么，都强制使用版本 `3.1`。

## 最新版本策略

- 当项目中只有`spring-jdbc`为5.1.3时，其相关依赖同样为5.1.3版本

  ![image-20231008185011039](assets/image-20231008185011039.png)

- 当项目中只有`spring-webmvc`为5.1.13时，其相关依赖同样为5.1.13版本

![image-20231008185115755](assets/image-20231008185115755.png)

- 当项目中，同时引入`spring-jdbc 5.1.3`和`spring-webmvc 5.1.13`时，会产生依赖冲突，因为最新版本策略，因此`spring-jdbc`中的依赖都变为`5.1.13`版本

  ![image-20231008185300593](assets/image-20231008185300593.png)

## 强制依赖

- 因为最新版本策略，spring-beans的版本为5.1.13，现在使用如下代码强制spring-beans的版本为5.1.3：

  ```groovy
  configurations.all {
      resolutionStrategy {
          force 'org.springframework:spring-beans:5.1.3.RELEASE'
      }
  }
  ```

  ![image-20231008190413417](assets/image-20231008190413417.png)

可以看到项目中的spring-beans版本都为`5.1.3.RELEASE`

## 依赖排除

手动依赖排除：

![image-20231008190837621](assets/image-20231008190837621.png)

## 依赖解析规则

Gradle构建脚本中为特定依赖项设置解析策略。这是通过`configurations.all` 和 `resolutionStrategy.eachDependency` 结构实现的，它允许您为项目中的所有配置设置依赖解析规则。

```groovy
configurations.all {
    resolutionStrategy.eachDependency { DependencyResolveDetails details ->
        if (details.requested.group == 'org.springframework' ) {
            details.useVersion '5.1.3.RELEASE'
        }
    }
}
```

![image-20231008191714357](assets/image-20231008191714357.png)