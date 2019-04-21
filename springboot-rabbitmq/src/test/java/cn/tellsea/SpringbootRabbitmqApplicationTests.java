package cn.tellsea;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootRabbitmqApplicationTests {

    @Autowired
    private AmqpTemplate amqpTemplate;

    /* simple模型，work模型 */
    @Test
    public void simple() throws InterruptedException {
        String msg = "RabbitMQ simple ...";
        for (int i = 0; i < 10; i++) {
            amqpTemplate.convertAndSend("spring.simple.queue", msg + i);
        }
        Thread.sleep(5000);
    }

    @Test
    public void work() throws InterruptedException {
        String msg = "RabbitMQ simple ...";
        for (int i = 0; i < 10; i++) {
            amqpTemplate.convertAndSend("spring.work.queue", msg + i);
        }
        Thread.sleep(5000);
    }

    /* 三种订阅模型 */
    @Test
    public void fanout() throws InterruptedException {
        String msg = "RabbitMQ fanout ...";
        for (int i = 0; i < 10; i++) {
            // 这里注意细节，第二个参数需要写，不然第一个参数就变成routingKey了
            amqpTemplate.convertAndSend("spring.fanout.exchange", "", msg + i);
        }
        Thread.sleep(5000);
    }

    @Test
    public void direct() throws InterruptedException {
        String msg = "RabbitMQ direct ...";
        for (int i = 0; i < 10; i++) {
            amqpTemplate.convertAndSend("spring.direct.exchange", "direct", msg + i);
        }
        Thread.sleep(5000);
    }

    @Test
    public void topic() throws InterruptedException {
        amqpTemplate.convertAndSend("spring.topic.exchange", "user.insert", "新增用户");
        amqpTemplate.convertAndSend("spring.topic.exchange", "user.delete", "删除用户");
        amqpTemplate.convertAndSend("spring.topic.exchange", "student.insert", "新增学生");
        amqpTemplate.convertAndSend("spring.topic.exchange", "student.delete", "删除学生");
        Thread.sleep(5000);
    }
}
