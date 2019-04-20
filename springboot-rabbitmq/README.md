## SpringBoot整合RabbitMQ

RabbitMQ是基于AMQP的一款消息管理系统，RabbitMQ基于Erlang语言开发，安装之前需要先安装Erlang的相关依赖。

RabbitMQ提供了6种消息模型，但是第6种其实是RPC，并不是MQ，因此不予学习。那么也就剩下5种。

但是其实3、4、5这三种都属于订阅模型，只不过进行路由的方式不同。

[RabbitMQ官网](http://www.rabbitmq.com/#)
[RabbitMQ官方教程](http://www.rabbitmq.com/getstarted.html#)
[RabbitMQ下载地址](http://www.rabbitmq.com/download.html#)
[Erlang下载地址](http://www.erlang.org/download.html#)
[RabbitMQ相关知识讲解](https://blog.csdn.net/qq_38762237/article/details/89416444#)
[RabbitMQ在Windows的安装教程](https://blog.csdn.net/weixin_39735923/article/details/79288578#)

## 准备

参照上面安装教程的讲解，安装好以下三个软件：

- Erlang，由于是基于Erlang的中间件，所以必须安装，且配置环境变量
- RabbitMQ，双击安装，默认的即可
- sbin目录下安装图形界面：使用rabbitmq-plugins enable rabbitmq_management命令进行安装

运行sbin/rabbitmq-server

使用浏览器访问：http://localhost:15672，默认登录用户名：guest，密码为：guest

**添加用户**

admin模块下添加一个用户，建议和我使用一样的，避免之后产生混淆:

|属性|值|
|:--|:--|
|Username|tellsea|
|password|123456|
Tags|administrator|

**创建虚拟主机**

Virtual Hosts名称设置为：/tellsea-host，点击主机名称授权用户tellsea