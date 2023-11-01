package com.zhouyf.action;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping("/image/*")
public class ImageAction {
    @GetMapping(value = "png", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public BufferedImage createImageData() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("/images/zhou.png");
        return ImageIO.read(classPathResource.getInputStream());
    }
}
