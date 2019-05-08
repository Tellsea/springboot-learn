## 什么是静态化

静态化是指把动态生成的HTML页面变为静态内容保存，以后用户的请求到来，直接访问静态页面，不再经过服务的渲染。

而静态的HTML页面可以部署在nginx中，从而大大提高并发能力，减小tomcat压力。

## 如何实现静态化

目前，静态化页面都是通过模板引擎来生成，而后保存到nginx服务器来部署。常用的模板引擎比如：

- Freemarker
- Velocity
- Thymeleaf

我们之前就使用的Thymeleaf，来渲染html返回给用户。Thymeleaf除了可以把渲染结果写入Response，也可以写到本地文件，从而实现静态化。

## Thymeleaf实现静态化

**概念**

先说下Thymeleaf中的几个概念：

- Context：运行上下文
- TemplateResolver：模板解析器
- TemplateEngine：模板引擎

> Context

上下文： 用来保存模型数据，当模板引擎渲染时，可以从Context上下文中获取数据用于渲染。

当与SpringBoot结合使用时，我们放入Model的数据就会被处理到Context，作为模板渲染的数据使用。

> TemplateResolver

模板解析器：用来读取模板相关的配置，例如：模板存放的位置信息，模板文件名称，模板文件的类型等等。

当与SpringBoot结合时，TemplateResolver已经由其创建完成，并且各种配置也都有默认值，比如模板存放位置，其默认值就是：templates。比如模板文件类型，其默认值就是html。

> TemplateEngine

模板引擎：用来解析模板的引擎，需要使用到上下文、模板解析器。分别从两者中获取模板中需要的数据，模板文件。然后利用内置的语法规则解析，从而输出解析后的文件。来看下模板引起进行处理的函数：

```java
templateEngine.process("模板名", context, writer);
```

三个参数：

- 模板名称
- 上下文：里面包含模型数据
- writer：输出目的地的流

在输出时，我们可以指定输出的目的地，如果目的地是Response的流，那就是网络响应。如果目的地是本地文件，那就实现静态化了。

而在SpringBoot中已经自动配置了模板引擎，因此我们不需要关心这个。现在我们做静态化，就是把输出的目的地改成本地文件即可！

## 具体实现步骤
#### 相关依赖
```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>

<dependency>
	<groupId>org.projectlombok</groupId>
	<artifactId>lombok</artifactId>
</dependency>
```
#### 准备静态原型
`thymeleaf`文件夹下面新建一个`id.html`
```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Thymeleaf 静态页面</title>
</head>
<body>
	<h1 th:text="'名字是：' + ${name}"></h1>
	<h1 th:text="'年龄是：' + ${age}"></h1>
	<h1 th:text="'邮箱是：' + ${email}"></h1>
</body>
</html>
```
#### 接口和实现类
```java
public interface ThymeleafService {

    void createHtml(Long id);

    void deleteHtml(Long id);
}
```
实现类相关说明

定义一个存放静态文件的目录，这里你也可以写在`application.yml`文件中，再使用`@Value`注解也行。
```java
public static final String destPath = "D:/temp/static"; // 自己手动创建一个存在的文件路径
```
注入模板引擎`TemplateEngine`
```java
@Autowired
private TemplateEngine templateEngine;
```
准备加载到页面上的数据，讲道理，这里加载的数据是根据id从数据库查询出来的，我这里就写固定了
```java
public Map<String, Object> loadModel(Long id) {
	Map<String, Object> map = new HashMap<>();
	map.put("name", "tellsea");
	map.put("age", 20);
	map.put("email", "3210054449@qq.com");
	return map;
}
```
接下来就是重点，**创建静态页面的方法**
```
/**
 * 创建html页面
 *
 * @param id
 * @throws Exception
 */
public void createHtml(Long id) {
    // 上下文
    Context context = new Context();
    context.setVariables(loadModel(id));
    // 输出流
    File dest = new File(destPath, id + ".html");
    if (dest.exists()) {
        dest.delete();
    }
    try (PrintWriter writer = new PrintWriter(dest, "UTF-8")) {
        // 生成html，第一个参数是thymeleaf页面下的原型名称
        templateEngine.process("id", context, writer);
    } catch (Exception e) {
        log.error("[静态页服务]：生成静态页异常", e);
    }
}
```
这里提供了一个删除静态页面的方法，做戏当然是做全套，删除的接口便于文章后面讲解实战运行
```java
@Override
public void deleteHtml(Long id) {
    // 输出流
    File dest = new File(destPath, id + ".html");
    if (dest.exists()) {
        dest.delete();
    }
}
```
#### 测试效果
执行`createHtmlTest`测试类，然后查看`D:/temp/static`下生成的静态页面，Nice，直接双击生成的`Html`文件，浏览器就可以访问，效果为带数据的页面，Nice
```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class ThymeleafServiceTest {

    @Autowired
    private ThymeleafService thymeleafService;

    @Test
    public void createHtmlTest() {
        thymeleafService.createHtml(1L);
    }
}
```

## 实战分析

上面的实现的功能我们这里把它理解为对于文章详情的静态页，方便下面说明

问题：什么时候调用创建、删除？

#### 新增、更新
我们新增文章、更新文章，完成相应的操作数据库的业务逻辑之后，需要更新静态页面的数据，调用创建的接口，重新创建一个新的即可
```java
thymeleafService.createHtml(1L);
```
怎么覆盖？仔细看实现类的代码，**在创建页面之前，里面有写如果存在，则删除**
```java
if (dest.exists()) {
	dest.delete();
}
```

#### 删除
删除数据库文章之后，直接删除静态页面即可
```java
pageService.deleteHtml(id);
```

## 代理静态页面

我们修改`nginx`，让它对文章详情的请求进行监听，指向本地静态页面，如果本地没找到，才进行反向代理：
```shell
server {
    listen       80;
    server_name  www.tellsea.com;

    proxy_set_header X-Forwarded-Host $host;
    proxy_set_header X-Forwarded-Server $host;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;

    location /item {
        # 先找本地
        root html; # 这里的html是root安装目录的html文件夹，可以自定义
        if (!-f $request_filename) { #请求的文件不存在，就反向代理
            proxy_pass http://127.0.0.1:8080;
            break;
        }
    }

    location / {
        proxy_pass http://127.0.0.1:9002;
        proxy_connect_timeout 600;
        proxy_read_timeout 600;
    }
}
```

重启测试：

发现请求速度得到了极大提升，因为访问的是`HTML`的静态页面了。

## 相关链接
- [CSDN:SpringBoot 使用 Thymeleaf 实现页面静态化](https://blog.csdn.net/qq_38762237/article/details/89948842)
- [示例源码GitHub：springboot-thymeleaf-static](https://github.com/Tellsea/springboot-learn/tree/master/springboot-thymeleaf-static "实例源码")
- [Thymeleaf官方文档-Documentation](https://www.thymeleaf.org/documentation.html "Thymeleaf官方文档-Documentation")
