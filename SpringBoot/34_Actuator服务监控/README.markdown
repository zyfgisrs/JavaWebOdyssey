# Spring Boot Actuator

## 基本概念

Actuator是Spring Boot的一个组件，专为生产环境设计，能够帮助你监控和管理应用程序，例如查看应用的健康状况、查看应用的配置信息、查看JVM、内存、垃圾回收和各种其他统计信息。

## 核心功能

- 健康检查：Actuator提供了一个健康检查端点，你可以用它来检查应用程序及其所依赖的服务的健康状况。
- 应用环境：你可以查看应用的配置属性，包括默认属性、环境属性等。
- 度量：提供关于应用程序的各种度量信息，例如系统属性、垃圾回收、Web请求等。
- HTTP追踪：查看最近的HTTP请求追踪。
- 日志级别管理：动态更改应用中的日志级别。
- 其他：还有其他多种管理和监控功能，如线程堆栈、数据库健康、缓存统计等。

## 配置相关依赖

```groovy
project('microboot-web') { // 子模块
    dependencies { // 配置子模块依赖
        compile(project(':microboot-common')) // 引入其他子模块
        compile(libraries.'fastjson') //FastJSON
        //jackson相关依赖
        compile(libraries.'jackson-dataformat-xml')
        compile(libraries.'jackson-databind')
        compile(libraries.'jackson-annotations')
        compile(libraries.'jackson-core')
        //PDF相关依赖
        compile(libraries.'itextpdf')
        //excel相关依赖
        compile(libraries.'easypoi')
        //邮件服务相关依赖
        compile("org.springframework.boot:spring-boot-starter-mail")
        //服务监控相关依赖
        compile("org.springframework.boot:spring-boot-starter-actuator")
    }
}
```

## 开启端点

`application.yaml`

```yaml
management:
  server:
    port: 9090
  endpoints:
    web:
      exposure:
        include: "*"
      base-path: /actuator
```

## 访问监控路径

```
http://localhost:9090/actuator
```

回显结果：

```json
{
  "_links": {
    "self": {
      "href": "http://localhost:9090/actuator",
      "templated": false
    },
    "beans": {
      "href": "http://localhost:9090/actuator/beans",
      "templated": false
    },
    "caches": {
      "href": "http://localhost:9090/actuator/caches",
      "templated": false
    },
    "caches-cache": {
      "href": "http://localhost:9090/actuator/caches/{cache}",
      "templated": true
    },
    "health": {
      "href": "http://localhost:9090/actuator/health",
      "templated": false
    },
    "health-path": {
      "href": "http://localhost:9090/actuator/health/{*path}",
      "templated": true
    },
    "info": {
      "href": "http://localhost:9090/actuator/info",
      "templated": false
    },
    "conditions": {
      "href": "http://localhost:9090/actuator/conditions",
      "templated": false
    },
    "configprops": {
      "href": "http://localhost:9090/actuator/configprops",
      "templated": false
    },
    "env-toMatch": {
      "href": "http://localhost:9090/actuator/env/{toMatch}",
      "templated": true
    },
    "env": {
      "href": "http://localhost:9090/actuator/env",
      "templated": false
    },
    "loggers": {
      "href": "http://localhost:9090/actuator/loggers",
      "templated": false
    },
    "loggers-name": {
      "href": "http://localhost:9090/actuator/loggers/{name}",
      "templated": true
    },
    "heapdump": {
      "href": "http://localhost:9090/actuator/heapdump",
      "templated": false
    },
    "threaddump": {
      "href": "http://localhost:9090/actuator/threaddump",
      "templated": false
    },
    "metrics-requiredMetricName": {
      "href": "http://localhost:9090/actuator/metrics/{requiredMetricName}",
      "templated": true
    },
    "metrics": {
      "href": "c",
      "templated": false
    },
    "scheduledtasks": {
      "href": "http://localhost:9090/actuator/scheduledtasks",
      "templated": false
    },
    "mappings": {
      "href": "http://localhost:9090/actuator/mappings",
      "templated": false
    }
  }
}
```

由于当前配置已经打开全部监控端点，所以直接通过`/actuator`访问就可以得到全部的SpringBoot原生端点。

