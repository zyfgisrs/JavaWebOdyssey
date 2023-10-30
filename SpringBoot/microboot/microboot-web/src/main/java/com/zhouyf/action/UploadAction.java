package com.zhouyf.action;

import com.zhouyf.common.action.abs.AbstractBaseAction;
import com.zhouyf.vo.Message;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@RestController
@RequestMapping("/form/*")
public class UploadAction extends AbstractBaseAction {
    @PostMapping("upload")
    public Object upload(Message message, MultipartFile photo) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("photoName", photo.getName());
        map.put("photoOriginalFileName", photo.getOriginalFilename());
        map.put("photoContentType", photo.getContentType());
        map.put("photoSize", photo.getSize());
        return map;
    }
}
