package com.alanhub.vueadmin.config;

import com.alanhub.vueadmin.security.LoginFailureHandler;
import com.alanhub.vueadmin.security.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    LoginFailureHandler loginFailureHandler;

    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    private static final String[] URL_WHITELIST={
            "/login",
            "/logout",
            "/captcha",
            "/favicon.ico",
    };

    protected void configure(HttpSecurity http) throws  Exception{
        http.cors().and().csrf().disable()

                //登录配置
                .formLogin()
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)

                //禁用session
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                //配置拦截规则
                .and()
                .authorizeRequests()
                .antMatchers(URL_WHITELIST).permitAll()
                .anyRequest().authenticated();

                //异常处理器
    }

}
