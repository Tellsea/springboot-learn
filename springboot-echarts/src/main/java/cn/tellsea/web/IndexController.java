package cn.tellsea.web;

import cn.tellsea.bean.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
public class IndexController {

    // 静态的图表页面
    @GetMapping({"", "/", "/index"})
    public String index() {
        return "index";
    }

    // 一条动态数据
    @GetMapping("/echarts")
    public String myECharts(Model model) {
        String skirt = "裙子";
        int nums = 30;
        model.addAttribute("skirt", skirt);
        model.addAttribute("nums", nums);
        return "echarts";
    }

    // 所有数据为动态数据
    @GetMapping("/product")
    public String product() {
        return "product";
    }

    // 提供数据的接口用于显示
    @GetMapping("/list")
    @ResponseBody
    public List<Product> productList() {
        return Arrays.asList(
                new Product("酸奶", 4),
                new Product("大食桶", 5),
                new Product("安慕希", 8),
                new Product("津威", 2),
                new Product("汉堡包", 10)
        );
    }
}
