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
}
