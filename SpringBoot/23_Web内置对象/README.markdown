# 获取Web内置对象

## RequestContextHolder

`RequestContextHolder`是Spring框架中的一个工具类，它为当前的请求上下文提供了静态访问方法。该类允许您在应用程序的任何地方，特别是在那些没有直接访问请求和响应对象的地方，访问与当前线程关联的请求属性。

以下是关于`RequestContextHolder`的一些关键点和特性：

1. **线程局部存储**:
   - `RequestContextHolder`内部使用`ThreadLocal`来存储与当前线程关联的`RequestAttributes`对象。这意味着在处理一个特定的请求时，与该请求相关的所有线程都可以访问相同的`RequestAttributes`实例。

2. **获取RequestAttributes**:
   - `getRequestAttributes()`: 这是一个静态方法，返回与当前线程关联的`RequestAttributes`对象（如果存在的话）。

3. **设置RequestAttributes**:
   - `setRequestAttributes(RequestAttributes requestAttributes)`: 这是一个静态方法，允许您为当前线程设置一个新的`RequestAttributes`实例。

4. **清除RequestAttributes**:
   - `resetRequestAttributes()`: 这是一个静态方法，用于从当前线程中删除`RequestAttributes`实例。

5. **与Web应用程序的集成**:
   - 在典型的Web应用程序中，当请求到达时，与该请求关联的`RequestAttributes`实例（通常是`ServletRequestAttributes`实例）被绑定到处理该请求的线程。在请求处理完成后，这个实例被从线程中清除。

6. **用途**:
   - 使用`RequestContextHolder`，您可以在Spring应用程序的任何地方访问当前请求的属性，例如在Service或Repository层，这些层通常没有直接访问Web层组件的能力。
   - 这特别有用，例如，当您需要访问会话数据或特定请求参数，但您正在操作的组件不是一个控制器或其他直接处理请求的组件。

总之，`RequestContextHolder`为Spring应用程序提供了一个方便、线程安全的方式来访问与当前请求关联的属性。这允许开发者在应用程序的各个层中访问请求数据，而不必传递请求和响应对象到每一个方法或组件中。