package cn.tellsea.mq;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * simple：生产者-->队列-->消费者
 */
@Component
public class SimpleListener {

    // 通过注解自动创建 spring.simple.queue 队列
    @RabbitListener(queuesToDeclare = @Queue("spring.simple.queue"))
    public void listen(String msg) {
        System.out.println("SimpleListener listen 接收到消息：" + msg);
    }
}