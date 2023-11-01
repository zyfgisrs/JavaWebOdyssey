# 返回图片流

- 添加控制器`com.zhouyf.action.ImageAction`

```java
package com.zhouyf.action;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping("/image/*")
public class ImageAction {
    @GetMapping(value = "png", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public BufferedImage createImageData() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("/images/zhou.png");
        return ImageIO.read(classPathResource.getInputStream());
    }
}
```

- `com.zhouyf.config.WebConfig`添加图片流转换器

![image-20231021162202072](assets/image-20231021162202072.png)

- 访问测试

```
http://localhost:8080/image/png
```

![image-20231021162245394](assets/image-20231021162245394.png)