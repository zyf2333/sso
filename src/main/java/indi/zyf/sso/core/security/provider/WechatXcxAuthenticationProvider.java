package indi.zyf.sso.core.security.provider;

import com.fasterxml.jackson.annotation.JsonProperty;
import indi.zyf.sso.core.ErrorCode;
import indi.zyf.sso.core.security.pojo.WechatUserDetails;
import indi.zyf.sso.core.security.service.MyUserDetailsService;
import indi.zyf.sso.core.security.service.WeChatXcxDetailsService;
import indi.zyf.sso.core.security.token.WechatXcxAuthenticationToken;
import indi.zyf.sso.util.WeixinUtil;
import indi.zyf.sso.vo.ApiResult;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class WechatXcxAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(WechatXcxAuthenticationProvider.class);

    @Autowired
    private WeChatXcxDetailsService weChatXcxDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;//密码工具

    /**
    * @Function: WechatXcxAuthenticationProvider
    * @Description: 验证登录
    *
    * @param:
    * @return:
    * @throws: 异常描述
    *
    * @version: v1.0.0
    * @author: zyf
    * @date: 2019/1/15 17:17
    *
    * Modification History:
    * Date                  Author        Version         Description
    *-----------------------------------------------------------------*
    * 2019/1/15 17:17      zyf            v1.0.0           修改原因
    */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String code = (String) authentication.getPrincipal();//code 微信code
        String type = (String) authentication.getCredentials();//type 登录类型 school，parent

        // 查询数据库中的用户信息
        UserDetails user =weChatXcxDetailsService.loadUserByCode(code, type);
        String opendId = null;
        if(user instanceof WechatUserDetails){
            WechatUserDetails muser = (WechatUserDetails) user;
            opendId = muser.getOpenId();
            if(!muser.isEnabled()){//用户禁用
                throw new DisabledException(ErrorCode.ERR_USER_DIS_ABLE.getErrMsg());
            }
        }
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new WechatXcxAuthenticationToken(user,opendId,type,authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (WechatXcxAuthenticationToken.class
                .isAssignableFrom(authentication));
    }
}
