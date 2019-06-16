package indi.zyf.sso.core.security.filter;

import indi.zyf.sso.core.security.token.WechatXcxAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @Function: WechatXcxAuthenticationFilter
* @Description: 微信小程序登录过滤器
*
* @param:
* @return:
* @throws: 异常描述
*
* @version: v1.0.0
* @author: zyf
* @date: 2019/1/10 17:05
*
* Modification History:
* Date                  Author        Version         Description
*-----------------------------------------------------------------*
* 2019/1/10 17:05      zyf            v1.0.0           修改原因
*/
//@Component
public class WechatXcxAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public static final String SPRING_SECURITY_FORM_code_KEY = "code";
    public static final String SPRING_SECURITY_FORM_type_KEY = "type";
    private String codeParameter = "code";//微信code，用来获取openid
    private String typeParameter = "type";//登录类型,园所版school/家长版parent
    private boolean postOnly = true;

    /**
    * @Function: WechatXcxAuthenticationFilter
    * @Description: 匹配过滤的请求
    *
    * @param:
    * @return:
    * @throws: 异常描述
    *
    * @version: v1.0.0
    * @author: zyf
    * @date: 2019/1/10 17:29
    *
    * Modification History:
    * Date                  Author        Version         Description
    *-----------------------------------------------------------------*
    * 2019/1/10 17:29      zyf            v1.0.0           修改原因
    */
    public WechatXcxAuthenticationFilter() {
        super(new AntPathRequestMatcher("/xcx_login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String code = this.obtainCode(request);
            String type = this.obtainType(request);
            if (code == null) {
                code = "";
            }

            if (type == null) {
                type = "";
            }

            code = code.trim();
            WechatXcxAuthenticationToken authRequest = new WechatXcxAuthenticationToken(code, type);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    protected String obtainCode(HttpServletRequest request) {
        return request.getParameter(this.codeParameter);
    }

    protected String obtainType(HttpServletRequest request) {
        return request.getParameter(this.typeParameter);
    }

    protected void setDetails(HttpServletRequest request, WechatXcxAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setCodeParameter(String codeParameter) {
        Assert.hasText(codeParameter, "code不可为空");
        this.codeParameter = codeParameter;
    }

    public void setTypeParameter(String passwordParameter) {
        Assert.hasText(typeParameter, "type不可为空");
        this.typeParameter = typeParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getCodeParameter() {
        return this.codeParameter;
    }

    public final String getTypeParameter() {
        return this.typeParameter;
    }
}
