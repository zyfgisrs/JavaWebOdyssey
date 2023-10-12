# 整合`JUnit5`

## 环境配置

- 项目想进行`JUnit`测试，则需要引入一些相关的测试组件，本次要引用的组件内容如下：

```
testImplementation group: 'org.springframework.boot', name: 'spring-boot-starter-test', version: '2.4.3'
testImplementation group: 'org.junit.jupiter', name: 'junit-jupiter-api', version: '5.7.1'
testImplementation group: 'org.junit.jupiter', name: 'junit-jupiter-engine', version: '5.7.1'
testImplementation group: 'org.junit.vintage', name: 'junit-vintage-engine', version: '5.7.1'
testImplementation group: 'org.junit.platform', name: 'junit-platform-launcher', version: '1.7.1'
testImplementation group: 'org.junit', name: 'junit-bom', version: '5.7.1', ext: 'pom'
```

- `dependencies.gradle`

```groovy
//定义版本号
ext.versions = [
        springboot           : '2.4.3', // SpringBoot版本
        junit                : '5.7.1', //配置junit测试工具的版本编号
        junitPlatformLauncher: '1.7.1',//JUnit测试工具运行平台
]

//定义所有的依赖库
ext.libraries = [
        //SpringBoot项目所需要的核心依赖:
        'spring-boot-gradle-plugin': "org.springframework.boot:spring-boot-gradle-plugin:${versions.springboot}",
        //以下的配置为与项目用例测试有关的依赖:
        'junit-jupiter-api'        : "org.junit.jupiter:junit-jupiter-api:${versions.junit}",
        'junit-jupiter-engine'     : "org.junit.jupiter:junit-jupiter-engine:${versions.junit}",
        'junit-vintage-engine'     : "org.junit.vintage:junit-vintage-engine:${versions.junit}",
        'junit-bom'                : "org.junit:junit-bom:${versions.junit}",
        'junit-platform-launcher'  : "org.junit.platform:junit-platform-launcher:${versions.junitPlatformLauncher}"
]
```

- 由于后面开发之中可能会到处使用JUnit测试工具，那么为了方便测试，建议将JUnit依赖库定义为公共组件（在subprojects配置之中进行依赖处理）

```groovy
    dependencies {
        //允许项目进行热部署
        developmentOnly("org.springframework.boot:spring-boot-devtools")
        testImplementation('org.springframework.boot:spring-boot-starter-test'){
            exclude group:'junit', module:'junit' //移除旧版本的测试工具
        }
        testImplementation(enforcedPlatform(libraries.'junit-bom')) //将当前项目强制性绑定为JUnit5运行
        testImplementation(libraries.'junit-jupiter-api')
        testImplementation(libraries.'junit-jupiter-engine')
        testImplementation(libraries.'junit-vintage-engine')
        testImplementation(libraries.'junit-platform-launcher')
    }
```

![image-20231010115639721](assets/image-20231010115639721.png)

- 测试任务启用：

```groovy
    gradle.taskGraph.whenReady {    // 在所有的操作准备好后触发
        tasks.each { task ->    // 找出所有的任务
            if (task.name.contains('test')) {   // 如果现在发现有test任务
                // 如果将enabled设置为true表示要执行测试任务，如果设置为false表示不执行测试任务
                task.enabled = true
            }
        }
    }
```

至此，JUnit环境已经配置完成

## 测试`JUnit5`

`microboot-web\src\test\java\com\zhouyf\test\TestMessageAction.java`：编写测试类

```java
package com.zhouyf.test;

import com.zhouyf.MyApplication;
import com.zhouyf.action.MessageAction;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@ExtendWith(SpringExtension.class)//使用JUnit5测试工具
@WebAppConfiguration //启动Web运行环境
@SpringBootTest(classes = MyApplication.class) //配置启动程序类
public class TestMessageAction {
    @Autowired
    private MessageAction messageAction;

    @BeforeAll
    public static void init() {
        System.err.println("【beforeAll】TestMessageAction类开始执行测试操作");
    }

    @AfterAll
    public static void after() {
        System.err.println("【AfterAll】TestMessageAction类测试完成");
    }

    @Test
    public void testEcho() {
        String content = this.messageAction.echo("zhouyf");
        String value = "[ECHO]zhouyf";
        System.err.println("【@Test】测试echo方法的返回值，当前返回结果为：" + content);
        Assertions.assertEquals(content, value);//测试相等
    }
}
```

- 修改IDEA配置，否则将宝找不到测试任务的错误

![image-20231010144442221](assets/image-20231010144442221.png)

- 运行测试类

![image-20231010144701734](assets/image-20231010144701734.png)

可以看到测试JUnit已经成功配置到了SpringBoot中
