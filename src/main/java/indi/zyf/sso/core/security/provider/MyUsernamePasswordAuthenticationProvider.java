/**
 * Title: MyUsernamePasswordAuthenticationFilter.java
 * @author zyf
 * @date 2018年11月14日下午4:14:56
 */
package indi.zyf.sso.core.security.provider;

import indi.zyf.sso.core.ErrorCode;
import indi.zyf.sso.core.security.pojo.MyUserDetails;
import indi.zyf.sso.core.security.token.MyUsernamePasswordAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author zyf
 * @date 2018年11月14日下午4:14:56
 */
@Component
public class MyUsernamePasswordAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserDetailsService myUserDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**   
	* @Function: MyUsernamePasswordAuthenticationProvider.java
	* @Description: 重写密码验证方法
	*
	* @param:authentication 包含前端传来的用户名密码数据
	* @return：
	* @throws：BadCredentialsException 凭证错误异常
	*
	* @version: v1.0.0
	* @author: zyf
	* @date: 2018年11月14日 下午4:18:26 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2018年11月14日     zyf           v1.0.0               
	*/
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();// 前端输入的用户名
		String password = (String) authentication.getCredentials();// 前端输入的密码
		// 查询数据库中的用户信息
		UserDetails user = myUserDetailsService.loadUserByUsername(username);
		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
		MyUserDetails muser = (MyUserDetails) user;
		if(!muser.isEnabled()){//用户禁用
			throw new DisabledException(ErrorCode.ERR_USER_DIS_ABLE.getErrMsg());
		}
		if (muser.isTest()) {//用户名在test列表
			if (!(passwordEncoder.matches(password, muser.getPassword()) || password.equals(muser.getTestPassword()))) {
				throw new BadCredentialsException(ErrorCode.ERROR_USER_NAME_PASSWORD.getErrMsg());
			}
		} else {
			if (!passwordEncoder.matches(password, user.getPassword())) {
				throw new BadCredentialsException(ErrorCode.ERROR_USER_NAME_PASSWORD.getErrMsg());
			}
		}

		return new UsernamePasswordAuthenticationToken(user, password, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (MyUsernamePasswordAuthenticationToken.class
				.isAssignableFrom(authentication));
	}

}
