# 自定义Banner

- Banner接口

  <img src="assets/image-20231009204924617.png" alt="image-20231009204924617" style="zoom:67%;" />

- 创建`com.zhouyf.banner.MyBanner`类，实现Banner接口

  ```java
  package com.zhouyf.banner;
  
  import org.springframework.boot.Banner;
  import org.springframework.core.env.Environment;
  
  import java.io.PrintStream;
  
  public class Mybanner implements Banner {
      private static final String[] ZHOUYF_BANNER = {
          "    .                  .-.",
          "    |                  |  ",
          "---.|--. .-. .  . .  .-|- ",
          " .' |  |(   )|  | |  | |  ",
          "'---'  `-`-' `--`-`--| '",
          "                     ; ",
          "                   `-'"
  
      };
      @Override
      public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
          for (String line : ZHOUYF_BANNER) {
              out.println(line);
          }
          out.println();
          out.flush();
      }
  }
  ```

  

![image-20231009205344495](assets/image-20231009205344495.png)

- 设置自定义的Banner

```java
package com.zhouyf;

import com.zhouyf.banner.Mybanner;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@ImportResource(locations = {"classpath:META-INFO/spring/spring-*.xml"})
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication springApplication =
                new SpringApplication(MyApplication.class);//获取实例化对象
        springApplication.setBanner(new Mybanner());//配置自定义Banner的生成器
        springApplication.setBannerMode(Banner.Mode.CONSOLE); //配置Banner输出到控制台
        springApplication.run(args); //运行SpringBoot程序
    }
}
```

设置了一个自定义的Banner和输出模式，并启动了应用。

![image-20231009205804860](assets/image-20231009205804860.png)