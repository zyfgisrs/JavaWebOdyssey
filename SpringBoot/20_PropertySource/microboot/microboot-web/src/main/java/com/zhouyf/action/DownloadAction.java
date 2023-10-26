package com.zhouyf.action;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/data/*")
public class DownloadAction {
    @GetMapping("download")
    public void fileDownload(HttpServletResponse response) throws IOException {
        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition", "attachement;filename=zhouyf.zip");
        ClassPathResource resource = new ClassPathResource("/file/zhouyf.zip");
        InputStream inputStream = resource.getInputStream();
        byte[] data = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(data)) != -1) {
            response.getOutputStream().write(data, 0, len);
        }
    }
}
