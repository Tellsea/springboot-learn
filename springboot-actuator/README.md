# springboot整合actuator

Spring Boot执行器(Actuator)提供安全端点，用于监视和管理Spring Boot应用程序。 默认情况下，所有执行器端点都是安全的。

## 导入依赖

```
<!-- 执行器 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

## application.yml
```
management:
  server:
    port: 9001
  endpoints:
    web:
      base-path: /actuator
      exposure:
        include: "*"
  endpoint:
    health:
      show-details: always
```

官网API：https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html