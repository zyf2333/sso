package indi.zyf.sso.core.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import indi.zyf.sso.core.ErrorCode;
import indi.zyf.sso.core.security.pojo.WechatUserDetails;
import indi.zyf.sso.core.security.token.WechatXcxAuthenticationToken;
import indi.zyf.sso.mapper.ClazzMapper;
import indi.zyf.sso.mapper.SchoolMapper;
import indi.zyf.sso.mapper.SysUserMapper;
import indi.zyf.sso.model.Clazz;
import indi.zyf.sso.model.School;
import indi.zyf.sso.model.SysUser;
import indi.zyf.sso.util.StringUtil;
import indi.zyf.sso.vo.ApiResult;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.TextCodec;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * @Function: WechatXcxAuthenticationSuccessHandler
 * @Description: 微信小程序用户登录成功处理
 * @param:
 * @return:
 * @throws: 异常描述
 * @version: v1.0.0
 * @author: zyf
 * @date: 2019/1/16 16:59
 * <p>
 * Modification History:
 * Date                  Author        Version         Description
 * -----------------------------------------------------------------*
 * 2019/1/16 16:59      zyf            v1.0.0           修改原因
 */
@Component
public class WechatXcxAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SchoolMapper schoolMapper;
    @Autowired
    private ClazzMapper clazzMapper;
    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.web.authentication.
     * AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.
     * HttpServletRequest, javax.servlet.http.HttpServletResponse,
     * org.springframework.security.core.Authentication)
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        logger.info("微信小程序用户登录");

        // 获取请求头中的Authorization
        String header = request.getHeader("Authorization");
        // 是否以Basic开头
        if (header == null || !header.startsWith("Basic ")) {
            throw new UnapprovedClientAuthenticationException("请求头中无client信息");
        }

        String[] tokens = extractAndDecodeHeader(header, request);
        assert tokens.length == 2;

        String clientId = tokens[0];
        String clientSecret = tokens[1];

        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在:" + clientId);
        } else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配:" + clientId);
        }

        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP,
                clientId,
                clientDetails.getScope(),
                "custom");

        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request,
                authentication);

        OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
        ApiResult apiResult = new ApiResult(ErrorCode.SUCCESS);
        Map<String, Object> rtMap = new HashMap<>();

        Claims claims = Jwts.parser()////存时默认用utf-8取时需要指定
                .setSigningKey(TextCodec.BASE64.encode("edu"))
                .parseClaimsJws(token.getValue()).getBody();
        String username = (String) claims.get("user_name");
        String openId = "";
        String type = "";
        if (authentication instanceof WechatXcxAuthenticationToken) {
            //取出openid
            openId = ((WechatXcxAuthenticationToken) authentication).getOpenId();
            type = ((WechatXcxAuthenticationToken) authentication).getType();
        }
        //查询用户信息
        SysUser user = null;
        if (!"".equals(username.trim()) && !StringUtil.isEmpty(openId)) {//用户尚未绑定openid
            if ("school".equals(type)) {
                user = sysUserMapper.selectByUsername(username);
                if (user != null) {
                    if (!StringUtil.isEmpty(user.getExt1())) {
                        School school = schoolMapper.selectByPrimaryKey(Integer.valueOf(user.getExt1()));
                        user.setExt4(school.getSchoolName());
                        user.setPassword("");
                    }
                    if (!StringUtil.isEmpty(user.getExt2())) {
                        Clazz clazz = clazzMapper.selectByPrimaryKey(Integer.valueOf(user.getExt2()));
                        user.setExt5(clazz.getSchoolName());
                    }
                }
            } else if ("parent".equals(type)) {
                rtMap.put("childIds", username);//幼儿id列表
            }
            apiResult.setErrMsg("登录成功");
        } else if (StringUtil.isEmpty(openId)) {
            apiResult = new ApiResult(ErrorCode.ERR_SYS_WECAT_CODE_ERROR);
        } else {
            apiResult = new ApiResult(ErrorCode.ERR_USER_NO_LOGIN);
        }


        rtMap.put("token", token);
        rtMap.put("user", user);
        rtMap.put("openid", openId);
        apiResult.setData(rtMap);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(apiResult));
    }

    /**
     * 解析请求头拿到clientid  client secret的数组
     *
     * @param header
     * @param request
     * @return
     * @throws IOException
     */
    private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {

        byte[] base64Token = header.substring(6).getBytes("UTF-8");
        byte[] decoded;
        try {
            decoded = Base64.decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }

        String token = new String(decoded, "UTF-8");

        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        return new String[]{token.substring(0, delim), token.substring(delim + 1)};
    }

    public static void main(String[] args) {
    }
}