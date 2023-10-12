# 任务和生命周期

## 任务

Gradle Task 是 Gradle 中的基本执行单元。

1. **定义**：Task 是一个由 Gradle 执行的独立的工作单元，例如编译类或生成文档。
2. **声明**：在 `build.gradle` 文件中，你可以声明一个任务，使用 Groovy 语言定义它的行为。
3. **依赖管理**：任务可以依赖于其他任务。这意味着在执行一个任务之前，它所依赖的任务会首先被执行。
4. **生命周期**：每个任务都有三个生命周期阶段：初始化、配置和执行。当运行一个 Gradle 构建时，所有的任务都会经过初始化和配置阶段，但只有明确指定的任务才会进入执行阶段。
5. **类型**：除了自定义任务，Gradle 还提供了很多内置的任务类型，这些任务类型针对常见的构建活动，如复制文件和编译 Java 源代码。
6. **执行**：可以通过命令行运行特定的任务，例如 `gradle taskName`。
7. **自定义属性和方法**：可以为任务添加自定义属性和方法，使其更加灵活和强大。

### 自定义任务

- 在build.gradle文件中自定义任务

  ```groovy
  task t1{
      println('我是任务1')
      doFirst {
          println('在任务t1执行前操作的动作代码')
      }
  
      doLast {
          println('在任务t1执行后操作的动作代码')
      }
  }
  ```

- 点击刷新后，Tasks->other中出现了t1任务

  ![image-20231007133047051](F:\GITHUB\JavaWebOdyssey\Gradle\04_Gradle Task\image-20231007133047051.png)

- 将任务放到自定义分组中

  ![image-20231007133629925](F:\GITHUB\JavaWebOdyssey\Gradle\04_Gradle Task\image-20231007133629925.png)

### 配置代码（Configuration code）和动作代码（Action code）

在 Gradle 中，任务（Task）可以包含两种主要的代码：配置代码（Configuration code）和动作代码（Action code）。

1. **配置代码 (Configuration Code)**:
   
   配置代码定义了任务的配置阶段的行为。在构建生命周期中，所有任务的配置代码都会执行，无论这些任务是否最终被执行。配置代码主要用于设置任务的属性和状态。

   例如，假设你有一个名为 `copyDocs` 的复制任务，你可以在配置代码中设置源目录和目标目录：

   ```groovy
   task copyDocs(type: Copy) {
       from 'src/docs'
       into 'build/docs'
   }
   ```

   在这里，`from` 和 `into` 方法是配置代码，它们设置了任务的属性。

2. **动作代码 (Action Code)**:
   
   动作代码定义了当任务被执行时应该进行的实际操作。动作代码只在任务被执行时运行。

   你可以使用 `doLast` 或 `<<` 来添加一个动作到任务中：

   ```groovy
   task hello {
       doLast {
           println 'Hello, World!'
       }
   }
   ```

   或者：

   ```groovy
   task hello << {
       println 'Hello, World!'
   }
   ```

   在这里，`println 'Hello, World!'` 是动作代码，它定义了当 `hello` 任务被执行时应该做什么。

**注意**: 在 Gradle 的构建生命周期中，所有任务首先被配置（执行配置代码），然后只有选定的任务会被执行（执行动作代码）。这意味着，即使你没有执行特定的任务，其配置代码仍会被执行。

理解配置代码和动作代码之间的区别是使用 Gradle 的关键，因为它可以帮助你确保任务的正确配置和执行，以及避免在配置阶段不必要的操作。

### 动态任务定义

```groovy
3.times {index ->
    task("task${index}"){
        group('mytask')
        println("task${index}")
    }
}
```

![image-20231007134734236](F:\GITHUB\JavaWebOdyssey\Gradle\04_Gradle Task\image-20231007134734236.png)

### 任务依赖

Gradle 的任务依赖表示一个任务在执行之前必须先执行另一个或多个任务。换句话说，一个任务依赖于另一个任务意味着它不能在其依赖的任务之前执行。

任务依赖是 Gradle 构建生命周期中的核心概念，因为它确保在执行任务时所有必要的步骤都按正确的顺序执行。

**如何定义任务依赖：**

1. **使用 `dependsOn` 关键字**:

   你可以使用 `dependsOn` 来明确指定一个任务依赖于另一个任务。

   ```groovy
   task taskA {
       doLast {
           println 'Executing task A'
       }
   }

   task taskB {
       dependsOn taskA
       doLast {
           println 'Executing task B'
       }
   }
   ```

   在上述示例中，`taskB` 依赖于 `taskA`。因此，当你执行 `taskB` 时，`taskA` 将首先被执行。

2. **使用任务配置的简写语法**:

   你也可以在定义任务时直接指定其依赖关系：

   ```groovy
   task taskA << {
       println 'Executing task A'
   }
   
   task taskB(dependsOn: taskA) << {
       println 'Executing task B'
   }
   ```

   这与前面的示例相同，只是使用了不同的语法。

**任务依赖的类型**：

1. **硬依赖（Hard Dependency）**:

   这是上述示例中展示的类型。任务 B 有一个硬依赖于任务 A，这意味着任务 B 在任何情况下都不会在任务 A 之前执行。

2. **软依赖（Soft Dependency）**:

   如果任务 B 通常应该在任务 A 之后执行，但这不是严格要求的，你可以使用 `shouldRunAfter`。

   ```groovy
   taskB.shouldRunAfter taskA
   ```

3. **顺序依赖（Ordering Dependency）**:

   如果你只想确保两个任务之间的执行顺序，但不想创建真正的依赖关系，你可以使用 `mustRunAfter`。

   ```groovy
   taskB.mustRunAfter taskA
   ```

总的来说，任务依赖确保了 Gradle 构建过程中的正确执行顺序。通过理解和正确使用任务依赖，你可以确保你的构建过程是稳定、可靠和高效的。

演示任务依赖：在build中编写以下代码：

```groovy
task t1{
    group('mytask')
    println('我是任务1')
    doFirst {
        println('在任务t1执行前操作的动作代码')
    }

    doLast {
        println('在任务t1执行后操作的动作代码')
    }
}

task t2{
    group('mytask')
    println('我是任务2')
    dependsOn(t1)
    doFirst {
        println('在任务t2执行前操作的动作代码')
    }

    doLast {
        println('在任务t2执行后操作的动作代码')
    }
}

task t3{
    dependsOn(t2)
    group('mytask')
    println('我是任务3')
    doFirst {
        println('在任务t3执行前操作的动作代码')
    }

    doLast {
        println('在任务t3执行后操作的动作代码')
    }
}
```

执行t3任务

![image-20231007141023675](C:\Users\zhouy\AppData\Roaming\Typora\typora-user-images\image-20231007141023675.png)

### 任务配置

在 Gradle 中，任务可以被配置以在其生命周期的不同阶段执行特定的动作。`doFirst` 和 `doLast` 是两种常用的方法，允许开发者在任务开始前和结束后分别执行代码。这为构建脚本提供了高度的灵活性，使开发者能够根据需要定制和扩展任务的行为。

![image-20231007142019317](C:\Users\zhouy\AppData\Roaming\Typora\typora-user-images\image-20231007142019317.png)

**执行顺序**:

当你运行 `clean` 任务时，动作的执行顺序如下：

1. `'在clean之前执行动作代码'` 会被打印（由于 `doFirst` 配置）。
2. `clean` 任务的主要行为会执行。
3. `'在clean之后执行动作代码'` 会被打印（由于 `doLast` 配置）。

## 生命周期

在 Gradle 中，任务（Task）的执行遵循一个特定的生命周期，这个生命周期可以分为三个主要阶段：初始化（Initialization）、配置（Configuration）和执行（Execution）。以下是这三个阶段的详细描述：

1. **初始化阶段 (Initialization Phase)**:
   
   在此阶段，Gradle 确定哪个项目将参与构建过程，并为每个项目创建一个 `Project` 实例。如果你正在构建一个多项目构建（multi-project build），那么此阶段会为每个子项目创建一个 `Project` 实例。此阶段通常不涉及任务，主要关注项目。

2. **配置阶段 (Configuration Phase)**:
   
   在此阶段，所有的项目都会被配置。这意味着所有的构建脚本（例如 `build.gradle` 文件）都会被执行。此阶段的主要目标是确定哪些任务将会执行以及它们的执行顺序。

   重要的是要理解此阶段中的所有代码都会被执行，无论任务是否最终被执行。这就是为什么我们经常将代码放入 `doLast` 或 `<<` 闭包中的原因，以确保它们只在任务的执行阶段运行，而不是在配置阶段。

3. **执行阶段 (Execution Phase)**:
   
   在此阶段，Gradle 根据之前确定的顺序执行选定的任务。只有在此阶段中明确定义为任务动作的代码才会执行。这包括使用 `doLast` 或 `<<` 闭包定义的代码，以及任务的内置动作。

   例如，考虑以下任务定义：

   ```groovy
   task myTask {
       doLast {
           println "This is executed during the Execution Phase."
       }
   }
   ```

   上面的 `println` 语句只会在执行阶段，当 `myTask` 任务被执行时运行。

在整个生命周期中，一旦确定了要执行的任务及其顺序，Gradle 就会尽可能地并行地执行这些任务，以提高构建性能，除非任务之间存在明确的依赖关系，这可能会限制其并行执行。
