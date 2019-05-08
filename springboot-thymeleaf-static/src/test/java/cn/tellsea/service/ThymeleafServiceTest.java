package cn.tellsea.service;

import cn.tellsea.SpringbootThymeleafStaticApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ThymeleafServiceTest extends SpringbootThymeleafStaticApplicationTests {

    @Autowired
    private ThymeleafService thymeleafService;

    @Test
    public void createHtmlTest() {
        thymeleafService.createHtml(1L);
    }

    @Test
    public void delete() {
        thymeleafService.deleteHtml(1L);
    }
}