package cn.tellsea;

import cn.tellsea.utils.PortUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootSmsApplication {

    static {
        PortUtils.checkPort(6379, "Redis 服务端", true);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootSmsApplication.class, args);
    }

}
