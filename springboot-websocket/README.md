## springboot整合websocket
websocket最伟大之处在于服务器和客户端可以在给定的时间范围内的任意时刻，相互推送信息。 浏览器和服务器只需要要做一个握手的动作，在建立连接之后，服务器可以主动传送数据给客户端，客户端也可以随时向服务器发送数据。

实现功能：springboot整合websocket实现一对一，多对多聊天系统

**支持作者就Star Mua~**

## 依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-websocket</artifactId>
</dependency>

其他的还用到了thymeleaf，lombok，fastjson，自己加一下
```
## 配置类
```java
/**
 * websocket的配置
 */
@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
```
## WebSocket
```java
@Slf4j
@Component
@ServerEndpoint("/websocket/{username}")
public class WebSocket {

    /**
     * 在线人数
     */
    public static int onlineNumber = 0;
    /**
     * 以用户的姓名为key，WebSocket为对象保存起来
     */
    private static Map<String, WebSocket> clients = new ConcurrentHashMap<String, WebSocket>();
    /**
     * 会话
     */
    private Session session;
    /**
     * 用户名称
     */
    private String username;

    /**
     * OnOpen 表示有浏览器链接过来的时候被调用
     * OnClose 表示浏览器发出关闭请求的时候被调用
     * OnMessage 表示浏览器发消息的时候被调用
     * OnError 表示有错误发生，比如网络断开了等等
     */

    /**
     * 建立连接
     *
     * @param session
     */
    @OnOpen
    public void onOpen(@PathParam("username") String username, Session session) {
        onlineNumber++;
        log.info("现在来连接的客户id：" + session.getId() + "用户名：" + username);
        this.username = username;
        this.session = session;
        log.info("有新连接加入！ 当前在线人数" + onlineNumber);
        try {
            //messageType 1代表上线 2代表下线 3代表在线名单 4代表普通消息
            //先给所有人发送通知，说我上线了
            Map<String, Object> map1 = new HashMap<>();
            map1.put("messageType", 1);
            map1.put("username", username);
            sendMessageAll(JSON.toJSONString(map1), username);

            //把自己的信息加入到map当中去
            clients.put(username, this);
            //给自己发一条消息：告诉自己现在都有谁在线
            Map<String, Object> map2 = new HashMap<>();
            map2.put("messageType", 3);
            //移除掉自己
            Set<String> set = clients.keySet();
            map2.put("onlineUsers", set);
            sendMessageTo(JSON.toJSONString(map2), username);
        } catch (IOException e) {
            log.info(username + "上线的时候通知所有人发生了错误");
        }


    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.info("服务端发生了错误" + error.getMessage());
        //error.printStackTrace();
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose() {
        onlineNumber--;
        //webSockets.remove(this);
        clients.remove(username);
        try {
            //messageType 1代表上线 2代表下线 3代表在线名单  4代表普通消息
            Map<String, Object> map1 = new HashMap<>();
            map1.put("messageType", 2);
            map1.put("onlineUsers", clients.keySet());
            map1.put("username", username);
            sendMessageAll(JSON.toJSONString(map1), username);
        } catch (IOException e) {
            log.info(username + "下线的时候通知所有人发生了错误");
        }
        log.info("有连接关闭！ 当前在线人数" + onlineNumber);
    }

    /**
     * 收到客户端的消息
     *
     * @param message 消息
     * @param session 会话
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            log.info("来自客户端消息：" + message + "客户端的id是：" + session.getId());
            JSONObject jsonObject = JSON.parseObject(message);
            String textMessage = jsonObject.getString("message");
            String fromusername = jsonObject.getString("username");
            String tousername = jsonObject.getString("to");
            //如果不是发给所有，那么就发给某一个人
            //messageType 1代表上线 2代表下线 3代表在线名单  4代表普通消息
            Map<String, Object> map1 = new HashMap<>();
            map1.put("messageType", 4);
            map1.put("textMessage", textMessage);
            map1.put("fromusername", fromusername);
            if (tousername.equals("All")) {
                map1.put("tousername", "所有人");
                sendMessageAll(JSON.toJSONString(map1), fromusername);
            } else {
                map1.put("tousername", tousername);
                sendMessageTo(JSON.toJSONString(map1), tousername);
            }
        } catch (Exception e) {
            log.info("发生了错误了");
        }
    }

    public void sendMessageTo(String message, String ToUserName) throws IOException {
        for (WebSocket item : clients.values()) {
            if (item.username.equals(ToUserName)) {
                item.session.getAsyncRemote().sendText(message);
                break;
            }
        }
    }

    public void sendMessageAll(String message, String FromUserName) throws IOException {
        for (WebSocket item : clients.values()) {
            item.session.getAsyncRemote().sendText(message);
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineNumber;
    }

}
```

## 控制器

```java
@Slf4j
@Controller
public class WebSocketController {

    @RequestMapping("/websocket/{name}")
    public String webSocket(@PathVariable String name, Model model) {
        try {
            log.info("跳转到websocket的页面上");
            model.addAttribute("username", name);
            return "websocket";
        } catch (Exception e) {
            log.info("跳转到websocket的页面上发生异常，异常信息是：" + e.getMessage());
            return "error";
        }
    }
}
```
## html页面
```html
<!DOCTYPE html>
<html xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <title>websocket</title>
    <script type="text/javascript" src="http://ajax.microsoft.com/ajax/jquery/jquery-1.4.min.js"></script>
    <script src="http://cdn.bootcss.com/stomp.js/2.3.3/stomp.min.js"></script>
    <script src="https://cdn.bootcss.com/sockjs-client/1.1.4/sockjs.min.js"></script>
</head>

<body>
<div style="margin: auto;text-align: center">
    <h1>Welcome to websocket</h1>
</div>
<br/>
<div style="margin: auto;text-align: center">
    <select id="onLineUser">
        <option>--所有--</option>
    </select>
    <input id="text" type="text"/>
    <button onclick="send()">发送消息</button>
</div>
<br>
<div style="margin-right: 10px;text-align: right">
    <button onclick="closeWebSocket()">关闭连接</button>
</div>
<hr/>
<div id="message" style="text-align: center;"></div>
<input type="text" th:value="${username}" id="username" style="display: none"/>
</body>


<script type="text/javascript">
    var webSocket;
    var commWebSocket;
    if ("WebSocket" in window) {
        webSocket = new WebSocket("ws://localhost:8080/websocket/" + document.getElementById('username').value);

        //连通之后的回调事件
        webSocket.onopen = function () {
            //webSocket.send( document.getElementById('username').value+"已经上线了");
            console.log("已经连通了websocket");
            setMessageInnerHTML("已经连通了websocket");
        };

        //接收后台服务端的消息
        webSocket.onmessage = function (evt) {
            var received_msg = evt.data;
            console.log("数据已接收:" + received_msg);
            var obj = JSON.parse(received_msg);
            console.log("可以解析成json:" + obj.messageType);
            //1代表上线 2代表下线 3代表在线名单 4代表普通消息
            if (obj.messageType == 1) {
                //把名称放入到selection当中供选择
                var onlineName = obj.username;
                var option = "<option>" + onlineName + "</option>";
                $("#onLineUser").append(option);
                setMessageInnerHTML(onlineName + "上线了");
            } else if (obj.messageType == 2) {
                $("#onLineUser").empty();
                var onlineName = obj.onlineUsers;
                var offlineName = obj.username;
                var option = "<option>" + "--所有--" + "</option>";
                for (var i = 0; i < onlineName.length; i++) {
                    if (!(onlineName[i] == document.getElementById('username').value)) {
                        option += "<option>" + onlineName[i] + "</option>"
                    }
                }
                $("#onLineUser").append(option);

                setMessageInnerHTML(offlineName + "下线了");
            } else if (obj.messageType == 3) {
                var onlineName = obj.onlineUsers;
                var option = null;
                for (var i = 0; i < onlineName.length; i++) {
                    if (!(onlineName[i] == document.getElementById('username').value)) {
                        option += "<option>" + onlineName[i] + "</option>"
                    }
                }
                $("#onLineUser").append(option);
                console.log("获取了在线的名单" + onlineName.toString());
            } else {
                setMessageInnerHTML(obj.fromusername + "对" + obj.tousername + "说：" + obj.textMessage);
            }
        };

        //连接关闭的回调事件
        webSocket.onclose = function () {
            console.log("连接已关闭...");
            setMessageInnerHTML("连接已经关闭....");
        };
    } else {
        // 浏览器不支持 WebSocket
        alert("您的浏览器不支持 WebSocket!");
    }

    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML) {
        document.getElementById('message').innerHTML += innerHTML + '<br/>';
    }

    function closeWebSocket() {
        //直接关闭websocket的连接
        webSocket.close();
    }

    function send() {
        var selectText = $("#onLineUser").find("option:selected").text();
        if (selectText == "--所有--") {
            selectText = "All";
        } else {
            setMessageInnerHTML(document.getElementById('username').value + "对" + selectText + "说：" + $("#text").val());
        }
        var message = {
            "message": document.getElementById('text').value,
            "username": document.getElementById('username').value,
            "to": selectText
        };
        webSocket.send(JSON.stringify(message));
        $("#text").val("");

    }
</script>

</html>

```
## 效果图
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190419190241179.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM4NzYyMjM3,size_16,color_FFFFFF,t_70)
