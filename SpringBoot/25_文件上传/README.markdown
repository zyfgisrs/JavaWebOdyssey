# 文件上传

## MultipartFile

在Web开发中，我们经常需要实现文件上传功能，例如用户上传头像、文档等。Spring为我们提供了对这一功能的支持，这就是`MultipartFile`接口，它是Spring中处理文件上传的关键部分。

这个接口提供了很多方法：

- **`getOriginalFilename()`**: 返回上传时的原始文件名。
- **`getName()`**: 获取表单中文件控件的字段名称。
- **`isEmpty()`**: 返回文件是否为空。
- **`getSize()`**: 获取文件的大小（以字节为单位）。
- **`getContentType()`**: 返回文件的MIME类型。
- **`getBytes()`**: 返回文件的内容为字节数组。
- **`getInputStream()`**: 获取文件内容的输入流。
- **`transferTo(File dest)`**: 将上传的文件保存到一个目标文件。

当处理完上传文件后，你可以决定如何存储这个文件。你可以选择存储在服务器的文件系统中，或者上传到云存储，或者存入数据库。选择哪种方式取决于你的应用需求。

## 文件上传配置

```yaml
spring:
  servlet:
    multipart:
      enabled: 
      max-file-size: 10MB #设置支持的单个文件的大小限制
      max-request-size: 20MB #设置最大的请求文件大小、总体文件大小
      file-size-threshold: 512KB #当上传的文件大小达到指定阈值时，将文件内容写入磁盘中。
      location: / #设置上传文件临时保存目录
```

- **`spring.servlet.multipart.enabled`**:
  - **含义**：这个属性决定是否启用multipart文件上传。
  - **默认值**：`true`
  
- **`spring.servlet.multipart.max-file-size`**:
  - **含义**：这个属性设置单个文件上传的最大大小。超出此大小的文件将不被接受。
  - **值**：`10MB`，表示每个上传文件的最大大小是10MB。

- **`spring.servlet.multipart.max-request-size`**:
  - **含义**：设置整个请求的最大大小，这包括文件和其他请求数据。
  - **值**：`20MB`，表示整个上传请求的大小不超过20MB。
  
- **`spring.servlet.multipart.file-size-threshold`**:
  - **含义**：文件开始被写入到磁盘的临时文件夹的大小阈值。如果文件大小超过这个值，系统将不会把它存储在内存中，而是写入磁盘的临时文件夹。
  - **值**：`512KB`，表示超过512KB的文件将被写入到磁盘的临时文件夹中。

- **`spring.servlet.multipart.location`**:
  - **含义**：定义文件被写入的临时文件夹的路径。
  - **值**：`/`，表示文件将被写入到根目录下的临时文件夹。这通常不是一个好的选择，因为根目录可能没有足够的权限或者空间。最好为这个属性提供一个明确的路径。

注意事项：

1. **资源限制**：`max-file-size`和`max-request-size`可以帮助你预防资源耗尽攻击，例如用户上传过大的文件导致服务器崩溃。
2. **临时文件处理**：当文件大小超过`file-size-threshold`，系统会将它写入磁盘的临时文件夹。确保你的服务器有足够的磁盘空间和正确的权限，以便文件可以被成功写入。
4. **异常处理**：在实际的文件上传代码中，应该处理可能的异常，例如文件过大、文件类型不匹配等，并给用户提供清晰的反馈。

## 接受请求上传的文件

```java
package com.zhouyf.action;

import com.zhouyf.vo.Message;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@RestController
@RequestMapping("/form/*")
public class UploadAction {
    @PostMapping("upload")
    public Object upload(Message message, MultipartFile photo){
        HashMap<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("photoName", photo.getName());
        map.put("photoOriginalFileName", photo.getOriginalFilename());
        map.put("photoContentType", photo.getContentType());
        map.put("photoSize", photo.getSize());
        return map;
    }
}
```

## 通过curl上传

此程序的curl命令采用了POST方式进行请求发送，随后利用”F-“设置了上传的所需要的参数内容。

```
curl -X POST -F "photo=@G:\zhouyf.png" -F "title=zhouyf" -F "content=www.zhouyf" -F "pubdate=2021-12-23" http://localhost:8080/form/upload
```

服务端成功实现文件上传后，可以直接看见相应的请求信息。

```java
{
  "photoOriginalFileName": "zhouyf.png",
  "photoSize": 2474,
  "message": {
    "content": "www.zhouyf",
    "pubdate": "2021-12-23 00:00:00",
    "title": "zhouyf"
  },
  "photoName": "photo",
  "photoContentType": "image/png"
}
```

