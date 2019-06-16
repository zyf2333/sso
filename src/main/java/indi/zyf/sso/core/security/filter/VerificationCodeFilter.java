package indi.zyf.sso.core.security.filter;

import indi.zyf.sso.core.SystemConstants;
import indi.zyf.sso.core.security.exception.VerificationCodeException;
import indi.zyf.sso.core.security.handler.MyFailureHandler;
import indi.zyf.sso.core.security.verification.pojo.VerificationCodeType;
import indi.zyf.sso.core.security.verification.processor.VerificationCodeProcessorHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Function: VerificationCodeFilter
 * @Description: 验证码过滤器
 * @param:
 * @return:
 * @throws: 异常描述
 * @version: v1.0.0
 * @author: zyf
 * @date: 2019/1/17 16:34
 * <p>
 * Modification History:
 * Date                  Author        Version         Description
 * -----------------------------------------------------------------*
 * 2019/1/17 16:34      zyf            v1.0.0           修改原因
 */
@Component
public class VerificationCodeFilter extends OncePerRequestFilter implements InitializingBean {

    @Autowired
    private MyFailureHandler myFailureHandler;

    /**
     * 系统中的校验码处理器
     */
    @Autowired
    private VerificationCodeProcessorHolder verificationCodeProcessorHolder;
    /**
     * 存放所有需要校验验证码的url
     */
    private Map<String, VerificationCodeType> urlMap = new HashMap<>();
    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 初始化要拦截的url配置信息
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        //要图片验证码拦截的url
        urlMap.put(SystemConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, VerificationCodeType.IMAGE);
        addUrlToMap(SystemConstants.PARAMETER_CODE_IMAGE_URLS, VerificationCodeType.IMAGE);
        //邮箱验证码
        urlMap.put(SystemConstants.DEFAULT_LOGIN_PROCESSING_URL_EMAIL, VerificationCodeType.EMAIL);
        addUrlToMap(SystemConstants.PARAMETER_CODE_EMAIL_URLS, VerificationCodeType.EMAIL);
        //手机验证码
        urlMap.put(SystemConstants.DEFAULT_LOGIN_PROCESSING_URL_SMS, VerificationCodeType.SMS);
        addUrlToMap(SystemConstants.PARAMETER_CODE_SMS_URLS, VerificationCodeType.SMS);
    }

    /**
     * 讲系统中配置的需要校验验证码的URL根据校验的类型放入map
     *
     * @param urlString
     * @param type
     */
    protected void addUrlToMap(String urlString, VerificationCodeType type) {
        if (org.apache.commons.lang.StringUtils.isNotBlank(urlString)) {
            String[] urls = org.apache.commons.lang.StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
            for (String url : urls) {
                urlMap.put(url, type);
            }
        }
    }


    /**
     * @Function: VerificationCodeFilter
     * @Description: 判断请求是否要过滤
     * @param:
     * @return:
     * @throws: 异常描述
     * @version: v1.0.0
     * @author: zyf
     * @date: 2019/1/17 16:36
     * <p>
     * Modification History:
     * Date                  Author        Version         Description
     * -----------------------------------------------------------------*
     * 2019/1/17 16:36      zyf            v1.0.0           修改原因
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        VerificationCodeType type = getValidateCodeType(request);
        if (type != null) {
            logger.info("校验请求(" + request.getRequestURI() + ")中的验证码,验证码类型" + type);
            try {
                //找到合适的验证码处理器验证
                verificationCodeProcessorHolder.findValidateCodeProcessor(type)
                        .validate(new ServletWebRequest(request, response));
                logger.info("验证码校验通过");
            } catch (VerificationCodeException exception) {
                myFailureHandler.onAuthenticationFailure(request, response, exception);
                return;
            }
        }
        //调用后面过滤器
        chain.doFilter(request, response);
    }

    /**
     * 获取校验码的类型，如果当前请求不需要校验，则返回null
     *
     * @param request
     * @return
     */
    private VerificationCodeType getValidateCodeType(HttpServletRequest request) {
        VerificationCodeType result = null;
       // if (StringUtils.equalsIgnoreCase(request.getMethod(), "post")) {//post请求
            Set<String> urls = urlMap.keySet();
            for (String url : urls) {
                if (pathMatcher.match(SystemConstants.DEFAULT_PATH+url, request.getRequestURI())) {
                    result = urlMap.get(url);
                }
            }
        //
        // }
        return result;
    }

}
