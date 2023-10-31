package com.zhouyf.action;

import com.zhouyf.vo.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping("/member/*")
public class MemberAction {
    @RequestMapping("show")
    public String showMember(Model model) throws ParseException {
        Member member = new Member("周杨凡", "zhouyf", 19, 8999.8,
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").
                        parse("2021-02-24 21:23:32"));

        model.addAttribute("member", member);
        return "member/member_switch";
    }

    @RequestMapping("list")
    public String MemberList(Model model) throws ParseException {
        HashMap<String, Member> map = new HashMap<>();
        ArrayList<Member> list = new ArrayList<>();
        for (int l = 0; l < 5; l++) {
            Member member = new Member("周杨凡", "zhouyf" + l, 12, 888.8,
                    new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2001-02-23 12:12:12"));
            map.put("member" + l, member);
            list.add(member);
        }
        model.addAttribute("memberList", list);
        model.addAttribute("memberMap", map);
        return "member/member_list";
    }


    @RequestMapping("map")
    public String MemberMap(Model model) throws ParseException {
        HashMap<String, Member> map = new HashMap<>();
        for (int l = 0; l < 5; l++) {
            Member member = new Member("周杨凡", "zhouyf" + l, 12, 888.8,
                    new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2001-02-23 12:12:12"));
            map.put("member" + l, member);
        }
        model.addAttribute("memberMap", map);
        return "member/member_map";
    }
}


