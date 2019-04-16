package cn.tellsea.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping(value = {"", "/", "/index"})
    public String index() {
        return "index";
    }
}
