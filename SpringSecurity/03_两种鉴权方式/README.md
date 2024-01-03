# 授权

在Spring相关的安全框架中，用户认证后进行权限存储以及资源权限设置通常涉及以下三个方面：

1. **基于请求的授权 (Request-based Authorization)**: 这种方式关注于对HTTP请求进行授权。通常使用Spring Security来定义特定的HTTP路径（URLs）应该由具备特定权限的用户才能访问。例如，你可以设定只有管理员能访问"/admin"路径。这种授权方式是通过配置文件或使用类似于`@EnableWebSecurity`的注解来实现的。

2. **基于方法的授权 (Method-based Authorization)**: 在这种方式中，授权是在方法级别上进行的。这意味着你可以控制对特定方法的访问权限。Spring Security支持使用注解如`@PreAuthorize`，`@PostAuthorize`，`@Secured`等来在方法上定义安全约束。例如，使用`@PreAuthorize("hasRole('ADMIN')")`可以确保只有拥有ADMIN角色的用户才能调用该方法。

3. **动态权限 (Dynamic Permissions)**: 动态权限允许在运行时基于不同的条件（如用户属性、输入参数等）动态地确定用户的访问权限。这通常涉及到编写复杂的逻辑来评估用户是否有权访问特定资源。在Spring Security中，你可以通过自定义方法安全表达式或者访问决策管理器（Access Decision Manager）来实现动态权限。

这三种授权方式可以单独使用，也可以结合使用，以构建一个全面且灵活的安全体系。在实际的应用程序中，选择哪种方式取决于应用程序的具体需求和安全需求。