package indi.zyf.sso.core.config;


import indi.zyf.sso.core.security.filter.WechatXcxAuthenticationFilter;
import indi.zyf.sso.core.security.handler.MyFailureHandler;
import indi.zyf.sso.core.security.handler.WechatXcxAuthenticationSuccessHandler;
import indi.zyf.sso.core.security.provider.WechatXcxAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * 微信小程序登录配置
 */
@Component
public class WechatXcxAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private WechatXcxAuthenticationSuccessHandler wechatXcxAuthenticationSuccessHandler;//校验成功后的处理器
    @Autowired
    private MyFailureHandler myFailureHandler;//校验失败后的处理器

    @Autowired
    private WechatXcxAuthenticationProvider wechatXcxAuthenticationProvider;//提供微信登录校验

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //微信小程序登录验证过滤器
        WechatXcxAuthenticationFilter wechatXcxAuthenticationFilter = new WechatXcxAuthenticationFilter();
        wechatXcxAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        wechatXcxAuthenticationFilter.setAuthenticationSuccessHandler(wechatXcxAuthenticationSuccessHandler);
        wechatXcxAuthenticationFilter.setAuthenticationFailureHandler(myFailureHandler);

        http.authenticationProvider(wechatXcxAuthenticationProvider)//添加自定义provider到manager
        .addFilterBefore(wechatXcxAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
