package com.zhouyf.xml;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DEMO1 {
    public static void main(String[] args) throws IOException {
        //创建文档
        Document document = DocumentHelper.createDocument();
        //添加根元素
        Element users = document.addElement("users");
        //添加子元素
        Element user1 = users.addElement("user");
        Element user2 = users.addElement("user");
        Element people = users.addElement("people");
        //添加子元素的属性
        user1.addAttribute("id", "1");
        user2.addAttribute("id", "2");
        //添加子元素
        user1.addElement("name").addText("Tom");
        user2.addElement("name").addText("Jack");
        user1.addElement("age").addText("18");
        user2.addElement("age").addText("21");
        user1.addElement("address").addText("南京");
        user2.addElement("address").addText("长春");
        people.addElement("name").addText("james");

        LocalDate birthday = LocalDate.of(1999, 5, 6);
        user1.addElement("birthday").addText(birthday.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        user2.addElement("birthday").addText(birthday.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        //创建输出流
        XMLWriter writer = new XMLWriter(new FileWriter("src/main/resources/output.xml"));
        //输出xml文件
        writer.write(document);
        writer.close();
    }
}
