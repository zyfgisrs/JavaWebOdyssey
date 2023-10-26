# 返回Excel数据

## 配置依赖

EasyPOI 是基于 Java 的简化版 Office 文档操作库，其在 Apache POI 的基础上进行了封装，旨在简化 POI 的复杂性，并提供一种更加便捷的方式来读写 Excel、Word 等 Office 文件。EasyPOI 主要针对 Excel 操作提供了更加简单方便的 API，支持导出 Excel 文件，支持模板导出，支持导入 Excel 映射为 Java 对象等功能。

```
implementation group: 'cn.afterturn', name: 'easypoi-spring-boot-starter', version: '4.4.0'
```

- 定义依赖库的版本

```
//定义版本号
ext.versions = [
        springboot           : '2.4.3', // SpringBoot版本
        junit                : '5.7.1', //配置junit测试工具的版本编号
        junitPlatformLauncher: '1.7.1',//JUnit测试工具运行平台
        lombok               : '1.18.30',
        fastjson             : '2.0.41',
        jackson              : '2.12.2',
        itextpdf             : '5.5.13.3',
        easypoi              : '4.4.0'
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
        'junit-platform-launcher'  : "org.junit.platform:junit-platform-launcher:${versions.junitPlatformLauncher}",
        //lombok
        'lombok'                   : "org.projectlombok:lombok:${versions.lombok}",
        //FastJSON
        'fastjson'                 : "com.alibaba:fastjson:${versions.fastjson}",
        //jackson依赖
        'jackson-dataformat-xml'   : "com.fasterxml.jackson.dataformat:jackson-dataformat-xml:${versions.jackson}",
        'jackson-databind'         : "com.fasterxml.jackson.core:jackson-databind:${versions.jackson}",
        'jackson-annotations'      : "com.fasterxml.jackson.core:jackson-annotations:${versions.jackson}",
        //PDF生成库
        'itextpdf'                 : "com.itextpdf:itextpdf:${versions.itextpdf}",
        //Office 文档操作库
        'easypoi'                  : "cn.afterturn:easypoi-spring-boot-starter:${versions.easypoi}"
]
```

- 配置`build.gradle`文件

```groovy
project('microboot-web') { // 子模块
    dependencies { // 配置子模块依赖
        compile(project(':microboot-common')) // 引入其他子模块
        compile(libraries.'fastjson') //FastJSON
        //jackson相关依赖
        compile(libraries.'jackson-dataformat-xml')
        compile(libraries.'jackson-databind')
        compile(libraries.'jackson-annotations')
        compile(libraries.'itextpdf')
        compile(libraries.'easypoi')
    }
}
```

- 刷新项目

<img src="assets/image-20231021134831843.png" alt="image-20231021134831843" style="zoom:50%;" />

可以看到，相关依赖已经引入到项目中。

## 返回Excel数据

- `com.zhouyf.vo.Student`类

```java
package com.zhouyf.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class Student {
    @Excel(name = "学生姓名", orderNum = "0", width = 30)
    private String name;

    @Excel(name = "学生年龄", orderNum = "1", width = 10)
    private int age;
    @Excel(name = "学生ID", orderNum = "2", width = 10)
    private String id;
}
```

- `com.zhouyf.action.StudentAction`控制器

编写Spring框架中的一个控制器，它用于创建一个包含学生信息的Excel文件，并通过HTTP响应将其发送给客户端。

```java
package com.zhouyf.action;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.export.ExcelExportService;
import com.zhouyf.vo.Student;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/excel/*")
public class StudentAction {
    @RequestMapping("students")
    public void creatExcelData(HttpServletResponse httpServletResponse) throws IOException {
        //设置HTTP响应的内容类型为Excel格式。
        httpServletResponse.setHeader("Content-type",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        //这行代码设置HTTP头信息，以指示浏览器这是一个需要下载的文件，而不是直接显示，并且文件的名称是“zhou.xlsx”。
        httpServletResponse.setHeader("Content-Disposition", "attachement;filename=zhou.xlsx");
        //这段代码创建了一个学生列表，每个学生都有名字、年龄和ID。
        String[] names = {"tom", "jack", "lucy"};
        int[] ages = {11, 13, 18};
        String[] ids = {"001", "002", "003"};

        List<Student> students = new ArrayList<>();
        for(int i = 0; i < names.length; ++i){
            Student student = new Student();
            student.setName(names[i]);
            student.setAge(ages[i]);
            student.setId(ids[i]);
            students.add(student);
        }

        //ExportParams是EasyPoi库中的一个类，用于定义Excel导出时的一些参数，例如标题、表名和Excel的类型。
        // XSSFWorkbook是Apache POI库中表示一个Excel文档的类。
        ExportParams exportParams = new ExportParams("学生信息", "第一页", ExcelType.XSSF);
        XSSFWorkbook sheets = new XSSFWorkbook();
        //这行代码创建了一个新的ExcelExportService对象，并调用createSheet方法将学生数据写入Excel表中。
        // 它使用了exportParams中定义的参数，Student.class指定了数据类型，students是实际的数据。
        new ExcelExportService().createSheet(sheets, exportParams, Student.class, students);
//       //这行代码将创建的Excel文档写入到HTTP响应的输出流中，这样当请求此方法时，用户会下载包含学生信息的Excel文件。
        sheets.write(httpServletResponse.getOutputStream());
    }
```

- 测试

客户端发送请求：

```
http://localhost:8080/excel/students
```

客户端浏览器将会成功下载一个Excel文件：

![image-20231021154247253](assets/image-20231021154247253.png)