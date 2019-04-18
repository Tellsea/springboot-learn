package cn.tellsea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("cn.tellsea.mapper")
public class SpringbootEhcacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootEhcacheApplication.class, args);
    }

}
