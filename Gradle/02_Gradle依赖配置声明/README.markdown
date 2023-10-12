Gradle依赖配置声明

在 Gradle 中，有许多不同的依赖配置（dependencies），每个配置有不同的用途和作用域。以下是一些常见的依赖配置及其用途：

1. **implementation**:
   - `implementation` 是主要的依赖配置，用于指定项目在编译和运行时依赖的库或模块。
   - 依赖声明在 `implementation` 配置下的库将会被编译到项目中，并且会在运行时可用。
2. **api**:
   - `api` 配置类似于 `implementation`，但具有更广泛的可见性。依赖声明在 `api` 配置下的库将被编译到项目中，并且会在运行时可用。此外，`api` 的依赖也会传递给项目的依赖方。
   - 通常，`api` 配置用于发布的库，希望将其 API 暴露给其他项目。
3. **compileOnly**:
   - `compileOnly` 配置用于指定编译时依赖的库。
   - 依赖声明在 `compileOnly` 配置下的库将只在编译时可用，不会被包含在项目的运行时构建输出中。
   - 通常，`compileOnly` 用于声明只在编译时需要的库，如注解处理器或编译时检查工具。
4. **runtimeOnly**:
   - `runtimeOnly` 配置用于指定只在运行时依赖的库。
   - 依赖声明在 `runtimeOnly` 配置下的库只会在运行时可用，不会被编译到项目中。
   - 通常，`runtimeOnly` 用于声明只在运行时需要的库，以减小项目的构建输出大小。
5. **testImplementation**:
   - `testImplementation` 配置用于指定项目的测试代码依赖的库。
   - 依赖声明在 `testImplementation` 配置下的库将在编译测试代码时可用，并且在运行测试时也可用。
6. **testCompileOnly**:
   - `testCompileOnly` 配置用于指定项目的测试代码只在编译时依赖的库，不会在运行测试时可用。
7. **testRuntimeOnly**:
   - `testRuntimeOnly` 配置用于指定项目的测试代码只在运行时依赖的库，不会在编译测试代码时可用。
8. **annotationProcessor**:
   - `annotationProcessor` 配置用于指定注解处理器依赖。这些依赖通常用于处理注解和生成代码。