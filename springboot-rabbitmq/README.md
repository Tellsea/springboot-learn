## SpringBoot整合RabbitMQ实现五种消息模型
RabbitMQ提供了6种消息模型，但是第6种其实是RPC，并不是MQ，因此不予学习。那么也就剩下5种。

 - 基本消息模型：生产者-->队列-->消费者
 - work消息模型：生产者-->队列-->多个消费者共同消费
 - 订阅模型-Fanout：广播，将消息交给所有绑定到交换机的队列，每个消费者都会收到同一条消息
 - 订阅模型-Direct：定向，把消息交给符合指定 `rotingKey` 的队列
 - 订阅模型-Topic：通配符，把消息交给符合`routing pattern`（路由模式） 的队列

但是其实3、4、5这三种都属于订阅模型，只不过进行路由的方式不同。

![在这里插入图片描述](https://img-blog.csdnimg.cn/201904211443428.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM4NzYyMjM3,size_16,color_FFFFFF,t_70)

## 准备工作

我已经安装好了RabbitMQ，Erlang，RabbitMQ图形界面插件。创建了用户：tellsea，和虚拟主机：/tellsea-host，并设置了使用权，下面给出下载地址。

**相关软件的安装**

[RabbitMQ官方教程](http://www.rabbitmq.com/getstarted.html)

[RabbitMQ官网下载地址](http://www.rabbitmq.com/download.html)

[Erlang下载地址](http://www.erlang.org/download.html)

[RabbitMQ五种消息模型介绍](https://blog.csdn.net/qq_38762237/article/details/89416444)

或者群文件夹下载，QQ群：957406675

**依赖**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```
**application.yml**
```yml
spring:
  rabbitmq:
    host: 127.0.0.1
    username: tellsea
    password: 123456
    virtual-host: /tellsea-host
```
## simple消息模型

> Spring AMQP提供的‘template’扮演者关键的角色。定义主要操作的接口是AmqpTemplate。
```java
    @Autowired
    private AmqpTemplate amqpTemplate;
```

**发送消息**
```java
    @Test
    public void simple() throws InterruptedException {
        String msg = "RabbitMQ simple ...";
        for (int i = 0; i < 10; i++) {
            amqpTemplate.convertAndSend("spring.simple.queue", msg + i);
        }
        Thread.sleep(5000);
    }
```
**接收消息**
```java
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
```
## work消息模型
在刚才的基本模型中，一个生产者，一个消费者，生产的消息直接被消费者消费。比较简单。

Work queues，也被称为（Task queues），任务模型。

当消息处理比较耗时的时候，可能生产消息的速度会远远大于消息的消费速度。长此以往，消息就会堆积越来越多，无法及时处理。此时就可以使用work 模型：**让多个消费者绑定到一个队列，共同消费队列中的消息**。队列中的消息一旦消费，就会消失，因此任务是不会被重复执行的。

**发送消息**
```java
    @Test
    public void work() throws InterruptedException {
        String msg = "RabbitMQ simple ...";
        for (int i = 0; i < 10; i++) {
            amqpTemplate.convertAndSend("spring.work.queue", msg + i);
        }
        Thread.sleep(5000);
    }
```
**接收消息**
```java
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
```
## 订阅模型-Fanout
Fanout，也称为广播。在广播模式下，消息发送流程是这样的：

- 1）  可以有多个消费者
- 2）  每个**消费者有自己的queue**（队列）
- 3）  每个**队列都要绑定到Exchange**（交换机）
- 4）  **生产者发送的消息，只能发送到交换机**，交换机来决定要发给哪个队列，生产者无法决定。
- 5）  交换机把消息发送给绑定过的所有队列
- 6）  队列的消费者都能拿到消息。实现一条消息被多个消费者消费

**发送消息**
```java
    @Test
    public void fanout() throws InterruptedException {
        String msg = "RabbitMQ fanout ...";
        for (int i = 0; i < 10; i++) {
            // 这里注意细节，第二个参数需要写，不然第一个参数就变成routingKey了
            amqpTemplate.convertAndSend("spring.fanout.exchange", "", msg + i);
        }
        Thread.sleep(5000);
    }
```
**接收消息**
```java
/**
 * Fanout：广播，将消息交给所有绑定到交换机的队列，每个消费者都会收到同一条消息
 */
@Component
public class FanoutListener {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "spring.fanout.queue", durable = "true"),
            exchange = @Exchange(
                    value = "spring.fanout.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.FANOUT
            )
    ))
    public void listen(String msg) {
        System.out.println("FanoutListener listen 接收到消息：" + msg);
    }

    // 队列2（第二个人），同样能接收到消息
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "spring.fanout2.queue", durable = "true"),
            exchange = @Exchange(
                    value = "spring.fanout.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.FANOUT
            )
    ))
    public void listen2(String msg) {
        System.out.println("FanoutListener listen2 接收到消息：" + msg);
    }
}
```
## 订阅模型-Direct
在Fanout模式中，一条消息，会被所有订阅的队列都消费。但是，在某些场景下，我们希望不同的消息被不同的队列消费。这时就要用到Direct类型的Exchange。

 在Direct模型下：

- 队列与交换机的绑定，不能是任意绑定了，而是要指定一个`RoutingKey`（路由key）
- 消息的发送方在 向 Exchange发送消息时，也必须指定消息的 `RoutingKey`。
- Exchange不再把消息交给每一个绑定的队列，而是根据消息的`Routing Key`进行判断，只有队列的`Routingkey`与消息的 `Routing key`完全一致，才会接收到消息

**发送消息**
```java
    @Test
    public void direct() throws InterruptedException {
        String msg = "RabbitMQ direct ...";
        for (int i = 0; i < 10; i++) {
            amqpTemplate.convertAndSend("spring.direct.exchange", "direct", msg + i);
        }
        Thread.sleep(5000);
    }
```
**接收消息**
```java
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
```
## 订阅模型-Topic
`Topic`类型的`Exchange`与`Direct`相比，都是可以根据`RoutingKey`把消息路由到不同的队列。只不过`Topic`类型`Exchange`可以让队列在绑定`Routing key` 的时候使用通配符！

`Routingkey` 一般都是有一个或多个单词组成，多个单词之间以”.”分割，例如： `user.insert`

|通配符规则  |举例  |
|:--|:--|
| `#`：匹配一个或多个词  | `user.#`：能够匹配`user.insert.save` 或者 `user.insert`  |
| `*`：匹配不多不少恰好1个词 | `user.*`：只能匹配`user.insert` |

**发送消息**
```java
    @Test
    public void topic() throws InterruptedException {
        amqpTemplate.convertAndSend("spring.topic.exchange", "user.insert", "新增用户");
        amqpTemplate.convertAndSend("spring.topic.exchange", "user.delete", "删除用户");
        amqpTemplate.convertAndSend("spring.topic.exchange", "student.insert", "新增学生");
        amqpTemplate.convertAndSend("spring.topic.exchange", "student.delete", "删除学生");
        Thread.sleep(5000);
    }
```
**接收消息**
```java
/**
 * Topic：通配符，把消息交给符合routing pattern（路由模式） 的队列
 */
@Component
public class TopicListener {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "spring.topic.queue", durable = "true"),
            exchange = @Exchange(
                    value = "spring.topic.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC
            ),
            key = {"user.*"}
    ))
    public void listen(String msg) {
        System.out.println("TopicListener User 接收到消息：" + msg);
    }

    // 通配规则不同，接收不到消息
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "spring.topic.queue", durable = "true"),
            exchange = @Exchange(
                    value = "spring.topic.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC
            ),
            key = {"student.*"}
    ))
    public void listen2(String msg) {
        System.out.println("TopicListener Student 接收到消息：" + msg);
    }
}
```
