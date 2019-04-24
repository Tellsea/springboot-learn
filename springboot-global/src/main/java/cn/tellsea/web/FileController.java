package cn.tellsea.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

@Slf4j
@Controller
public class FileController {

    private static final String PREFIX = "http://localhost:8080/images/";

    @GetMapping("/file")
    public String file() {
        return "file";
    }

    /**
     * 单文件上传
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/upload")
    @ResponseBody
    public String upload(@RequestParam(value = "file") MultipartFile file) {
        if (file.isEmpty()) {
            log.error("文件为空空");
            return "文件为空空";
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = "D:/Temp/"; // 上传后的路径
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filename = PREFIX + fileName;
        return filename;
    }

    @PostMapping(value = "/uploadMore")
    @ResponseBody
    public ArrayList<String> uploadMore(@RequestParam(value = "file") MultipartFile[] files) {
        if (files.length == 0) {
            log.error("文件为空空");
            return null;
        }
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            String fileName = files[i].getOriginalFilename();  // 文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
            String filePath = "D:/Temp/"; // 上传后的路径
            fileName = UUID.randomUUID() + suffixName; // 新文件名
            File dest = new File(filePath + fileName);
            try {
                files[i].transferTo(dest);
                list.add(PREFIX + fileName);
                log.info("第" + (i + 1) + "个文件上传成功");
            } catch (IOException e) {
                log.error(e.toString(), e);
                log.error("上传第" + (i++) + "个文件失败");
            }
        }
        return list;
    }
}