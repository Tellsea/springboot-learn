package cn.tellsea.web;

import cn.tellsea.dto.ResponseCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("")
    public ResponseCode test() {
        return ResponseCode.success();
    }
}
