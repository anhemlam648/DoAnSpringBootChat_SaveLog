package com.example._VuTrungNghia_SQL.Controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FaviconController {

    @GetMapping(value = "/favicon.ico", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] getFavicon() throws IOException {
        ClassPathResource resource = new ClassPathResource("static/favicon.ico");
        Path path = Paths.get(resource.getURI());
        return Files.readAllBytes(path);
    }
}