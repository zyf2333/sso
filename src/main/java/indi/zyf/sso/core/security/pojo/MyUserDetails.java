/**
 * Title: MyUserDetails.java
 * @author zyf
 * @date 2018年11月14日下午4:51:20
 */
package indi.zyf.sso.core.security.pojo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author	zyf
 * @date	2018年11月14日下午4:51:20
 */
public class MyUserDetails extends User {

	private String testPassword;//test密码
	private boolean isTest;//当前用户是否为test用户
	
	public String getTestPassword() {
		return testPassword;
	}

	public void setTestPassword(String testPassword) {
		this.testPassword = testPassword;
	}

	public boolean isTest() {
		return isTest;
	}

	public void setTest(boolean isTest) {
		this.isTest = isTest;
	}

	public MyUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	public MyUserDetails(String username, String password,boolean isTest,String testPassword, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.testPassword=testPassword;
		this.isTest=isTest;
	}
	/** serialVersionUID*/
	private static final long serialVersionUID = 1L;

	
}
