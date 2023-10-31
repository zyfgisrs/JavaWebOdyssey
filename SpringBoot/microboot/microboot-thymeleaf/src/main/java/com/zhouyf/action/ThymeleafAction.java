package com.zhouyf.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/thymeleaf/*")
public class ThymeleafAction {
    @RequestMapping("view")
    public ModelAndView view(String message) {
        ModelAndView modelAndView = new ModelAndView("message/message_show");
        modelAndView.addObject("message", message);
        modelAndView.addObject("title", "zhouyf");
        modelAndView.addObject("content", "zyf0503");
        return modelAndView;
    }

    @RequestMapping("path")
    public String path() {
        return "message/message_path.html";
    }

    @RequestMapping("i18n")
    public String i18n() {
        return "message/message_i18n.html";
    }


    @RequestMapping("attribute")
    public String showAttribute(HttpServletRequest request, HttpSession session) {
        request.setAttribute("message", "【request】message");
        session.setAttribute("message", "【session】message");
        request.getServletContext().setAttribute("message", "【application】message");
        return "message/message_attribute";
    }
}
