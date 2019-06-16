package indi.zyf.sso.core.config;

import indi.zyf.sso.core.security.filter.VerificationCodeFilter;
import indi.zyf.sso.core.security.handler.MyAuthenticationSuccessHandler;
import indi.zyf.sso.core.security.handler.MyFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.cors.CorsUtils;

import javax.servlet.Filter;

@Configuration
@EnableResourceServer
public class MyResourceServerConfig extends ResourceServerConfigurerAdapter implements WebSecurityConfigurer<WebSecurity> {
	@Autowired
	private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
	@Autowired
	private MyFailureHandler myAuthenticationFailureHandler;
	@Autowired
	private Filter myFilter;

	@Autowired
	private MyUsernamePasswordAuthenticationConfig myUsernamePasswordAuthenticationConfig;

	@Autowired
	private WechatXcxAuthenticationConfig wechatXcxAuthenticationConfig;

	@Autowired
	private VerificationCodeFilter verificationCodeFilter;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
				.addFilterBefore(myFilter,ChannelProcessingFilter.class)
				.formLogin() //httpBasic()
				.loginPage("/authentication/require")//配置跳转
				.loginProcessingUrl("/authentication/form")//from 表单的url
				.successHandler(myAuthenticationSuccessHandler)
				.failureHandler(myAuthenticationFailureHandler)
				.and()
				.authorizeRequests()
				.antMatchers("/eval/getExcel**").permitAll()
				.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
				.antMatchers(HttpMethod.OPTIONS).permitAll()
				.antMatchers("/authentication/require",
						"/authentication/form",
						"/authentication/check",
						"/authentication/saveImage",
						"/oauth/token",
						"/xcx_login",
						"/login.html",
						"/demo.html",
						"/sysuser/registByEmail",
						"/sysuser/sendSMS",
						"/code/**",
						"/core/templet/*",
						"/swagger-ui.html",
						"/talk/**",
						"/topic/getResponse",
						"/images/*",
						"/webjars/*",
						"/swagger-resources",
						"/v2/api-docs",
						"/configuration/*")
				.permitAll()
				.and()
				.csrf().disable()
				.apply(myUsernamePasswordAuthenticationConfig)
				.and()
				.apply(wechatXcxAuthenticationConfig)
				.and()
				.addFilterBefore(verificationCodeFilter, AbstractPreAuthenticatedProcessingFilter.class);
	}

	@Override
	public void init(WebSecurity builder) throws Exception {

	}

	@Override
	public void configure(WebSecurity builder) throws Exception {
		builder.ignoring()
		.antMatchers(HttpMethod.OPTIONS);
	}
	

}
