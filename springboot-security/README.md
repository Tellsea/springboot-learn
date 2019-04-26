# SpringBoot整合SpringSecurity

支持作者就star一下 🎉🎉

Spring Security是一个功能强大且可高度自定义的身份验证和访问控制框架。它是保护基于Spring的应用程序的事实标准。

Spring Security是一个专注于为Java应用程序提供身份验证和授权的框架。与所有Spring项目一样，Spring Security的真正强大之处在于它可以轻松扩展以满足自定义要求。

**特征**

- 对身份验证和授权的全面和可扩展的支持
- 防止会话固定，点击劫持，跨站点请求伪造等攻击
- Servlet API集成
- 可选与Spring Web MVC集成
- [更多可以参考官网](https://spring.io/projects/spring-security#learn) …

# 依赖
spring security整合springboot所使用的依赖
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
```
spring security标签依赖

```xml
        <dependency>
            <groupId>org.thymeleaf.extras</groupId>
            <artifactId>thymeleaf-extras-springsecurity5</artifactId>
        </dependency>
```
Html页面上使用标签库
```html
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity5">
```
Jsp页面上使用标签库
```html
< %@ taglib prefix ="sec" uri ="http://www.springframework.org/security/tags" % >
```
标签使用简单案例：判断是否已经登录
```html
<div sec:authorize="!isAuthenticated()">
    <h4 align="center">游客您好，<a th:href="@{/login}">请登录</a></h4>
</div>
```
# 配置文件
SpringSecurity配置
```java
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 认证请求规则
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("VIP1")
                .antMatchers("/level2/**").hasRole("VIP2")
                .antMatchers("/level3/**").hasRole("VIP3");
        // 注销账号
        http.logout().logoutSuccessUrl("/");
        /****************** 默认的 ****************/
        // 默认登录表单
         http.formLogin();
        // 记住我
         http.rememberMe();

        /****************** 定制的 ****************/
        // 定制页面和参数，默认名称：username，password
        // http.formLogin().loginPage("/login").usernameParameter("userName").passwordParameter("userPwd");
        // 定制记住我
        // http.rememberMe().rememberMeParameter("remember");
    }

    /**
     * 授权
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(new MyPasswordEncoder())
                .withUser("tellsea").password("123456").roles("VIP1", "VIP2")
                .and()
                .withUser("zhangsan").password("123456").roles("VIP2", "VIP3")
                .and()
                .withUser("lisi").password("123456").roles("VIP1", "VIP3");
    }
}
```
密码验证器

> 解决报错：There is no PasswordEncoder mapped for the id “null”

```java
public class MyPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(charSequence.toString());
    }
}
```
报错的原因

因为Spring security 5.0中新增了多种加密方式，也改变了密码的格式。

Spring Security中密码的存储格式是“{id}…………”。前面的id是加密方式，id可以是bcrypt、sha256等，后面跟着的是加密后的密码。

也就是说，程序拿到传过来的密码的时候，会首先查找被“{”和“}”包括起来的id，来确定后面的密码是被怎么样加密的，如果找不到就认为id是null。这也就是为什么我们的程序会报错。

官方文档举的例子中是各种加密方式针对同一密码加密后的存储形式，原始密码都是“password”。

# 控制层测试
```java
@Controller
public class PageController {

    @GetMapping({"/", "", "/index"})
    public String index() {
        return "index";
    }

    // 定制的登录表单
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("level1")
    @ResponseBody
    public String level1() {
        return "level1 拥有角色VIP1";
    }

    @GetMapping("level2")
    @ResponseBody
    public String level2() {
        return "level2 拥有角色VIP2";
    }

    @GetMapping("level3")
    @ResponseBody
    public String level3() {
        return "level3 拥有角色VIP3";
    }
}
```
# 测试页面
index.html
```html
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity5">
<head>
    <meta charset="UTF-8">
    <title>SpringBoot 整合 SpringSecurity</title>
</head>
<body>
<h2 align="center">SpringBoot 整合 SpringSecurity 实现登录、授权案例</h2>
<div sec:authorize="!isAuthenticated()">
    <h4 align="center">游客您好，<a th:href="@{/login}">请登录</a></h4>
</div>
<div sec:authorize="isAuthenticated()">
    <h4><span sec:authentication="name"></span>，您拥有的角色：<span sec:authentication="principal.authorities"></span></h4>
    <form th:action="@{/logout}" method="post">
        <input type="submit" value="注销"/>
    </form>
</div>
<hr>
<ul>
    <div sec:authorize="hasRole('VIP1')">
        <li><a th:href="@{/level1}">VIP1，可以访问</a></li>
    </div>
    <div sec:authorize="hasRole('VIP2')">
        <li><a th:href="@{/level2}">VIP2，可以访问</a></li>
    </div>
    <div sec:authorize="hasRole('VIP3')">
        <li><a th:href="@{/level3}">VIP3，可以访问</a></li>
    </div>
</ul>
</body>
</html>
```
login.html
```html
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>定制的登录表单</title>
</head>
<body>
<h2 align="center">定制登录页面</h2>
<hr>
<form th:action="@{/login}" method="post" style="margin: auto; width: 200px;">
    用户名：<input type="text" name="userName"><br>
    密码：<input type="password" name="userPwd"><br>
    <input type="checkbox" name="remember"> 记住我<br>
    <input type="submit" value="登录">
</form>
</body>
</html>
```
