package cn.tellsea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("cn.tellsea.mapper")
public class SpringbootEasypoiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootEasypoiApplication.class, args);
    }

}
