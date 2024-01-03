# SecurityFilterChain接口

SecurityFilterChain代表了一系列安全过滤器的集合，这些过滤器按特定的顺序执行，负责处理传入的HTTP请求，并对这些请求进行认证和授权。

- 当HTTP请求到达应用程序时，它会被`SecurityFilterChain`中的过滤器依次处理。每个过滤器对请求执行特定的任务，例如验证用户身份、检查用户权限、管理会话等。
- `SecurityFilterChain`允许开发者根据应用程序的安全需求自定义过滤器链。通过配置不同的过滤器，可以实现不同的安全策略和要求。

实现表单验证：

```java
package com.zhouyf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth ->
                auth.requestMatchers("to_login").
                        permitAll().
                        anyRequest().
                        authenticated());

        http.formLogin(form -> form.loginPage("/to_login")
                .loginProcessingUrl("/doLogin")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/index")
        );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user = User.withUsername("zhouyf").password("{noop}111").roles("admin").build();

        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();

        inMemoryUserDetailsManager.createUser(user);

        return inMemoryUserDetailsManager;
    }
}
```

# HttpSecurity相关api

`HttpSecurity`是Spring Security中用于配置安全性详细信息的核心类。通过使用`HttpSecurity`，你可以定制Web安全性的各个方面。以下是一些常用的`HttpSecurity`相关API和它们的功能：

## `authorizeRequests()`
- 用于定义哪些URL路径应该被保护，哪些不应该。
- 可以结合使用`antMatchers()`, `regexMatchers()`, `anyRequest()`等方法来定义具体的访问控制规则。

## `formLogin()`
- 启用表单登录，并允许自定义登录页面、默认成功登录后的跳转路径等。
- 可以结合`loginPage()`, `defaultSuccessUrl()`, `failureUrl()`等方法来自定义登录行为。

## `httpBasic()`
- 启用HTTP基本认证。
- 通常用于API的认证。

##  `logout()`
- 定义注销行为。
- 可以使用`logoutUrl()`, `logoutSuccessUrl()`, `deleteCookies()`等方法来自定义注销行为。

##  `csrf()`
- 启用或禁用跨站请求伪造（CSRF）保护。
- 对于REST API，通常会禁用CSRF保护。

## `cors()`
- 配置跨源资源共享（CORS）策略。
- 对于需要处理来自不同域的请求的应用程序很有用。

##  `exceptionHandling()`
- 定义异常处理策略，如自定义访问拒绝页面。
- 可以使用`accessDeniedPage()`或`accessDeniedHandler()`等方法。

##  `sessionManagement()`
- 定义会话管理策略，如会话固定保护、会话超时处理等。
- 可以使用`sessionCreationPolicy()`, `invalidSessionUrl()`等方法。

## `rememberMe()`
- 启用“记住我”功能，允许用户在关闭浏览器后依然保持登录状态。
- 可以定制记住我的参数，如有效期、数据源等。

## `oauth2Login()`
- 配置OAuth 2.0登录。
- 可以自定义客户端注册信息、用户信息端点等。
