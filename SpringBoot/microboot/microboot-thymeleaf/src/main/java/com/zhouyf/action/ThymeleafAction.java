package com.zhouyf.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/thymeleaf/*")
public class ThymeleafAction {
    @RequestMapping("view")
    public ModelAndView view(String message){
        ModelAndView modelAndView = new ModelAndView("message/message_show");
        modelAndView.addObject("message", message);
        modelAndView.addObject("title", "zhouyf");
        modelAndView.addObject("content", "zyf0503");
        return modelAndView;
    }
}
