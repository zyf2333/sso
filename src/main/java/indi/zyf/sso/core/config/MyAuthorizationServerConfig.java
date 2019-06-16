package indi.zyf.sso.core.config;

import indi.zyf.sso.core.webapp.MyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer//这个注解实现了认证服务器
public class MyAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{
	@Autowired
	private TokenStore tokenStore;
	@Autowired
	private JwtAccessTokenConverter jwtAccessTokenConverter;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService myUserDetailsService;
	@Autowired
	private MyFilter myFilter;
		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
	}
		 /*@Bean
		  public CorsFilter corsFilter() {
		        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		        final CorsConfiguration corsConfiguration = new CorsConfiguration();
		        corsConfiguration.setAllowCredentials(true);
		        corsConfiguration.addAllowedOrigin("*");
		        corsConfiguration.addAllowedHeader("*");
		        corsConfiguration.addAllowedMethod("*");
		        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		        return new CorsFilter(urlBasedCorsConfigurationSource);
		    }*/
		
		@Override//配入口点
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints
			.tokenStore(tokenStore)
			.authenticationManager(authenticationManager)
			.userDetailsService(myUserDetailsService)
			.accessTokenConverter(jwtAccessTokenConverter)
			;
			
		}
		
		
		/**
		 * /oauth/token
		这个如果配置支持allowFormAuthenticationForClients的，且url中有client_id和client_secret的会走ClientCredentialsTokenEndpointFilter来保护
		如果没有支持allowFormAuthenticationForClients或者有支持但是url中没有client_id和client_secret的，走basic认证保护
		 */
		@Override
		public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
			security.tokenKeyAccess("permitAll()").checkTokenAccess(
                    "isAuthenticated()");
			//enable client to get the authenticated when using the /oauth/token to get a access token
		    //there is a 401 authentication is required if it doesn't allow form authentication for clients when access /oauth/token
			security.allowFormAuthenticationForClients();

		}
		@Override//客户端 配置文件的clientId和cilentSecret
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
			builder.withClient("edu")
				.secret("eduSecret")
				.accessTokenValiditySeconds(60*60*2)//单位是秒
				.authorizedGrantTypes("password","refresh_token")//指定授权模式
				.scopes("all")//这里指定了，发请求可以不带，或者在集合内
				;
		}
}
	