package indi.zyf.sso.core.security.pojo;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/*
    微信小程序登录用户信息
 */
public class WechatUserDetails extends MyUserDetails {

    private String openId;//微信openid

    public WechatUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public WechatUserDetails(String username, String password, boolean isTest, String testPassword, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, isTest, testPassword, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public WechatUserDetails(String username, String openId, String password, boolean isTest, String testPassword, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, isTest, testPassword, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.openId = openId;
    }

    public WechatUserDetails(String username,String openId,boolean enabled,Collection<? extends GrantedAuthority> authorities){
        super(username, "", enabled, true, true, true, authorities);
        this.openId = openId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
