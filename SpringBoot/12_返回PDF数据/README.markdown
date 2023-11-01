# 响应PDF数据

## 添加依赖

```
implementation group: 'com.itextpdf', name: 'itextpdf', version: '5.5.13.3'
```

- 添加版本信息

```groovy
//定义版本号
ext.versions = [
        itextpdf             : '5.5.13.3'
]

//定义所有的依赖库
ext.libraries = [
        //PDF生成库
        'itextpdf'                 : "com.itextpdf:itextpdf:${versions.itextpdf}"
]
```

- `build.gradle`配置

![image-20231020203540222](assets/image-20231020203540222.png)

## 配置PDF的字体库

创建`src.main.resources.fonts`文件夹，将字体放到这个目录中：

<img src="assets/image-20231020212040484.png" alt="image-20231020212040484" style="zoom:67%;" />

创建创建`src.main.resources.images`文件夹，将图片放到这个目录中：

<img src="assets/image-20231020204309032.png" alt="image-20231020204309032" style="zoom:67%;" />

## 生成PDF

- 创建专属的生成PDF的控制器

```java
package com.zhouyf.action;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/data/*")
//@RequestMapping("/data/*")定义了此控制器的顶级路由
public class PDFAction {
    @GetMapping("pdf")
    public void createPDFData(HttpServletResponse response) throws Exception{
        //此方法接受一个HttpServletResponse对象，用于构建HTTP响应。
        //设置HTTP响应头，表明响应内容的类型为PDF。
        response.setHeader("Content-Type", "application/pdf");
        //设置HTTP响应头，指示浏览器处理响应为文件下载，文件名为"zhouyf.pdf"。
        response.setHeader("Content-Disposition", "attachement;filename=zhouyf.pdf");
        //创建一个新的Document对象，页面大小为A4，四周的边距分别为10, 10, 50, 20。
        Document document = new Document(PageSize.A4, 10,10,50,20);
        //获取PdfWriter实例，它将把document对象写入到HTTP响应的输出流中。
        PdfWriter.getInstance(document, response.getOutputStream());
        //打开document以准备写入内容。
        document.open();
        //从类路径的/images目录下加载名为“zhou.png”的图片，并创建一个Image对象。
        Resource imageResource = new ClassPathResource("/images/zhou.png");
        Image image = Image.getInstance(imageResource.getFile().getAbsolutePath());
        //设置图片的缩放，使其适应A4纸张宽度的一半和A4纸张的高度。
        image.scaleToFit(PageSize.A4.getWidth()/2, PageSize.A4.getHeight());
        //计算图片在页面上的位置，然后设置图片的绝对位置。
        float pointX = (PageSize.A4.getWidth() - image.getScaledWidth()) / 2;
        float pointY = PageSize.A4.getHeight() - image.getHeight()/2;
        image.setAbsolutePosition(pointX, pointY);
        document.add(image); //将图片添加到PDF文档中。
        //在文档中添加一些空行，作为图片和接下来内容之间的间隔。
        document.add(new Paragraph("\n\n\n"));

        //加载字体资源，创建基本字体BaseFont对象。
        Resource fontResource = new ClassPathResource("/fonts/simhei.ttf");
        BaseFont baseFont = BaseFont.createFont(fontResource.getFile().getAbsolutePath(),
                BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

        //使用基本字体创建新的Font对象，字体大小为20，样式为正常。
        Font font = new Font(baseFont, 20, Font.NORMAL);
        //定义表格的标题和内容，并通过循环添加到PDF文档中。
        String[] titles = new String[]{"姓名","内容","注释"};
        String[] contents = new String[]{"zhouyangfan","earth","JAVA"};
        for(int x = 0; x < titles.length; x++){
            PdfPTable pdfPTable = new PdfPTable(2);
            PdfPCell pdfPCell = new PdfPCell();
            pdfPCell.setPhrase(new Paragraph(titles[x], font));
            pdfPTable.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph(contents[x], font));
            pdfPTable.addCell(pdfPCell);
            document.add(pdfPTable);
        }
        //所有内容添加完成后，关闭document对象，完成PDF文档的生成。
        document.close();
    }
}
```

`PDFAction`类是一个Spring Web控制器，用于处理生成PDF文件并使用户能够通过Web浏览器下载的请求。它使用了iText库来生成PDF文件。

## 测试

请求：

```
http://localhost:8080/data/pdf
```

![image-20231020215631356](assets/image-20231020215631356.png)

成功下载PDF