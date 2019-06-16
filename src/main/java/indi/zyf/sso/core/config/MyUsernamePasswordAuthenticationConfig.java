package indi.zyf.sso.core.config;


import indi.zyf.sso.core.security.filter.MyUsernamePasswordAuthenticationFilter;
import indi.zyf.sso.core.security.handler.MyAuthenticationSuccessHandler;
import indi.zyf.sso.core.security.handler.MyFailureHandler;
import indi.zyf.sso.core.security.provider.MyUsernamePasswordAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;


@Component
public class MyUsernamePasswordAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;//校验成功后的处理器
    @Autowired
    private MyFailureHandler myFailureHandler;//校验失败后的处理器

    @Autowired
    private MyUsernamePasswordAuthenticationProvider myUsernamePasswordAuthenticationProvider;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //微信小程序登录验证过滤器
        MyUsernamePasswordAuthenticationFilter myUsernamePasswordAuthenticationFilter = new MyUsernamePasswordAuthenticationFilter();
        myUsernamePasswordAuthenticationFilter.setAuthenticationManager(
                http.getSharedObject(AuthenticationManager.class));
        myUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        myUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(myFailureHandler);

        http.authenticationProvider(myUsernamePasswordAuthenticationProvider)//添加自定义provider到manager
        .addFilterBefore(myUsernamePasswordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
