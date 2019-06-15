# SpringBoot 整合 JDBC Templete

今天是 2019/6/15，写这个案例的主要目的是，因为我是应届生，第一天上班拿到的项目采用的就是 SpringMVC + JDBC Templete + Bootstrap + easyUI ，通过前面的学习，我们知道还有更多，更好的框架来进行持久层的开发，例如Hibernate，SpringData JPA ，Mybatis 等，但是因为工作需要，我还是决定自己动手写一个SpringBoot整合JDBC Templete的案例。俗话说的好，技多不压身！

## 解惑

- 为什么不是采用SSM进行整合？

你可以这么理解，其实SpringBoot和SSM是同一个东西，表达的意思也是相同的，例如写一篇文章，采用白话文的方式和文言文的方式，结果都能完成文章，但是文言文更加的简洁。

- 体验一下SSM与SpringBoot的区别有多大？

SSM 客户管理系统：这个项目采用的是传统的SSM的方式，写的一个客户关系管理系统，仔细观察可以发现配置文件多，而且偏复杂，[SSM 客户管理系统源码示例](https://github.com/tellsea/ssm)

SpringBoot入门案例：这个项目就是采用SpringBoot的方式，输出Hello World的案例，仔细观察可以发现只有一个配置文件，而且还是空的，其实全部都由SpringBoot帮我们配置好了，[SpringBoot入门案例Hello World](https://github.com/Tellsea/springboot-learn/tree/master/springboot-hello)

下面正式准备我们本次实现的案例，觉得项目有用的话，顺便 Star 和 Fork 一下噢 🎉🎉


##  准备工作
### 数据库
找到项目的`doc/sql/springboot-jdbc-templete.sql`文件，导入到自己的数据库中，也可以根据下面的sql进行创建
```sql
CREATE TABLE `tb_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

### 依赖
刚开始搭建项目的话，很容易迷，所以这里我贴一个项目目录结构

![项目目录结构](https://github.com/Tellsea/springboot-learn/blob/master/springboot-jdbc-templete/doc/images/0.png)

创建SpringBoot项目，完整的依赖我这里粘贴一份
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.3.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>cn.tellsea</groupId>
    <artifactId>springboot-jdbc-templete</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>springboot-jdbc-templete</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <!-- spring boot -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <!-- jdbc -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>

        <!-- 数据库驱动，我是MySQL5.7，所以使用5.1.x的驱动，如果你是MySQL8，则改成8.0.x的版本 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.46</version>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.8</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

```

### 配置文件
注意改成自己的相关属性值
```yml
server:
  port: 8080
spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://localhost:3306/springboot-jdbc-templete
    username: root
    password: 123456
```
### SpringBoot启动类
创建SpringBoot项目的话，这个应该是自动生成的，这里给通过Maven创建项目的同学粘贴一个启动类
```java
@SpringBootApplication
public class SpringbootJdbcTempleteApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootJdbcTempleteApplication.class, args);
    }
}
```

## 编写代码
- 实体类，采用了Lombok表达式，不清楚的同学可以看我 [SSM的项目文档](https://github.com/Tellsea/ssm) 有简单的说明
```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;

    private String username;

    private String password;

    private Date ctime;
}
```
- dao接口
```java
public interface UserDao {
    
    User getUserById(Integer id);

    List<User> getUserList();

    int add(User user);

    int update(User user);

    int delete(Integer id);
}
```
- dao 实现类，jdbcTemplate 是注入的对象
```java
@Repository // 用于标注数据访问组件
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User getUserById(Long id) {
        List<User> list = jdbcTemplate.query("select * from tb_user where id = ?", new Object[]{id}, new BeanPropertyRowMapper(User.class));
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<User> getUserList() {
        List<User> list = jdbcTemplate.query("select * from tb_user", new Object[]{}, new BeanPropertyRowMapper(User.class));
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    @Override
    public int add(User user) {
        return jdbcTemplate.update("insert into tb_user(username, password, ctime) values(?, ?, ?)",
                user.getUsername(), user.getPassword(), new Date());
    }

    @Override
    public int update(User user) {
        return jdbcTemplate.update("update tb_user SET username = ? , password = ? WHERE id=?",
                user.getUsername(), user.getPassword(), user.getId());
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update("delete from tb_user where id = ? ", id);
    }

}
```
- service 层接口
```java
public interface UserService {

    User getUserById(Integer id);

    List<User> getUserList();

    int add(User user);

    int update(User user);

    int delete(Integer id);
}
```
- service 层实现类，很简单，直接调用dao层就行了
```java
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer id) {
        return userDao.getUserById(id);
    }

    @Override
    public List<User> getUserList() {
        return userDao.getUserList();
    }

    @Override
    public int add(User user) {
        return userDao.add(user);
    }

    @Override
    public int update(User user) {
        return userDao.update(user);
    }

    @Override
    public int delete(Integer id) {
        return userDao.delete(id);
    }
}
```
- 下面这个是一个简单的公共数据返回类，通常项目中都是必须存在的，用于指定统一的响应数据格式，更复杂的写法在SpringBoot的基础模块学习，[SpringBoot 搭建全局异常处理](https://github.com/Tellsea/springboot-learn/tree/master/springboot-global)
```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult {

    private Integer code;

    private String msg;

    private Object data;
}
```
- 控制层
```java
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public ResponseResult getUserById(@PathVariable(value = "id") Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return new ResponseResult(200, "查询结果为空", null);
        }
        return new ResponseResult(200, "查询成功", user);
    }

    /**
     * 查询用户列表
     *
     * @return
     */
    @GetMapping("/list")
    public ResponseResult getUserList() {
        List<User> list = userService.getUserList();
        if (list == null || list.isEmpty()) {
            return new ResponseResult(200, "查询结果为空", null);
        }
        return new ResponseResult(200, "查询成功", list);
    }

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @PostMapping("/add")
    public ResponseResult add(@RequestBody User user) {
        int count = userService.add(user);
        if (count == 0) {
            return new ResponseResult(500, "新增失败", null);
        }
        return new ResponseResult(200, "新增成功", null);
    }

    /**
     * 根据id删除用户
     *
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable(value = "id") Long id) {
        int count = userService.delete(id);
        if (count == 0) {
            return new ResponseResult(500, "删除失败", null);
        }
        return new ResponseResult(200, "删除成功", null);
    }

    /**
     * 根据id修改用户信息
     *
     * @param user
     * @return
     */
    @PostMapping("/update")
    public ResponseResult update(@RequestBody User user) {
        int count = userService.update(user);
        if (count == 0) {
            return new ResponseResult(500, "更新失败", null);
        }
        return new ResponseResult(200, "更新成功", null);
    }
}
```

## 测试
我这里直接使用Postman测试工具进行测试，不会使用的话你也可以直接使用SpringBoot带的单元测试

- 新增用户

![新增用户](https://github.com/Tellsea/springboot-learn/blob/master/springboot-jdbc-templete/doc/images/1.png)

- 根据ID查询

![根据ID查询](https://github.com/Tellsea/springboot-learn/blob/master/springboot-jdbc-templete/doc/images/2.png)

- 查询列表

![查询列表](https://github.com/Tellsea/springboot-learn/blob/master/springboot-jdbc-templete/doc/images/3.png)

- 更新用户

![更新用户](https://github.com/Tellsea/springboot-learn/blob/master/springboot-jdbc-templete/doc/images/4.png)

- 删除用户

![删除用户](https://github.com/Tellsea/springboot-learn/blob/master/springboot-jdbc-templete/doc/images/5.png)

# 交流学习
![在这里插入图片描述](https://github.com/Tellsea/springboot-learn/blob/master/doc/images/emoticon1.jpg)
![交流学习](https://github.com/Tellsea/springboot-learn/blob/master/doc/images/qq-group.png)
