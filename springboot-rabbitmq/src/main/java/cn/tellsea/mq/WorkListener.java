package cn.tellsea.mq;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Work：生产者-->队列-->多个消费者共同消费
 */
@Component
public class WorkListener {

    // 通过注解自动创建 spring.work.queue 队列
    @RabbitListener(queuesToDeclare = @Queue("spring.work.queue"))
    public void listen(String msg) {
        System.out.println("WorkListener listen 接收到消息：" + msg);
    }

    // 创建两个队列共同消费
    @RabbitListener(queuesToDeclare = @Queue("spring.work.queue"))
    public void listen2(String msg) {
        System.out.println("WorkListener listen2 接收到消息：" + msg);
    }
}