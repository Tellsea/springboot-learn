package cn.tellsea.service.impl;

import cn.tellsea.service.ThymeleafService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ThymeleafServiceImpl implements ThymeleafService {

    public static final String destPath = "D:/temp/static";

    @Autowired
    private TemplateEngine templateEngine;

    public Map<String, Object> loadModel(Long id) {
        // 讲道理，这里加载的数据是根据id从数据库查询出来的，我这里就写固定了
        Map<String, Object> map = new HashMap<>();
        map.put("name", "tellsea");
        map.put("age", 20);
        map.put("email", "3210054449@qq.com");
        return map;
    }

    /**
     * 创建html页面
     *
     * @param spuId
     * @throws Exception
     */
    public void createHtml(Long spuId) {
        // 上下文
        Context context = new Context();
        context.setVariables(loadModel(spuId));
        // 输出流
        File dest = new File(destPath, spuId + ".html");
        if (dest.exists()) {
            dest.delete();
        }
        try (PrintWriter writer = new PrintWriter(dest, "UTF-8")) {
            // 生成html
            templateEngine.process("id", context, writer);
        } catch (Exception e) {
            log.error("[静态页服务]：生成静态页异常", e);
        }
    }

    public void deleteHtml(Long id) {
        // 输出流
        File dest = new File(destPath, id + ".html");
        if (dest.exists()) {
            dest.delete();
        }
    }
}
