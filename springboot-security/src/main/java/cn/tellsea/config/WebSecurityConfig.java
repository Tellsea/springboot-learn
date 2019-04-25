package cn.tellsea.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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
