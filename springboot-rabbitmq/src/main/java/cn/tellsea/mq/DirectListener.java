package cn.tellsea.mq;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Direct：定向，把消息交给符合指定routing key 的队列
 */
@Component
public class DirectListener {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "spring.direct.queue", durable = "true"),
            exchange = @Exchange(
                    value = "spring.direct.exchange",
                    ignoreDeclarationExceptions = "true"
            ),
            key = {"direct"}
    ))
    public void listen(String msg) {
        System.out.println("DirectListener listen 接收到消息：" + msg);
    }

    // 队列2（第二个人），key值不同，接收不到消息
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "spring.direct2.queue", durable = "true"),
            exchange = @Exchange(
                    value = "spring.direct.exchange",
                    ignoreDeclarationExceptions = "true"
            ),
            key = {"direct-test"}
    ))
    public void listen2(String msg) {
        System.out.println("DirectListener listen2 接收到消息：" + msg);
    }
}