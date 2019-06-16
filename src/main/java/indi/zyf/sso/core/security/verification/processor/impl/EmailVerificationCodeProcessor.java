package indi.zyf.sso.core.security.verification.processor.impl;

import indi.zyf.sso.core.ErrorCode;
import indi.zyf.sso.core.security.exception.EmailSendException;
import indi.zyf.sso.core.security.exception.VerificationCodeException;
import indi.zyf.sso.core.security.verification.pojo.VerificationCode;
import indi.zyf.sso.core.security.verification.pojo.VerificationCodeType;
import indi.zyf.sso.core.security.verification.processor.AbstractVerificationCodeProcessor;
import indi.zyf.sso.util.EmailUtil;
import indi.zyf.sso.util.StringUtil;
import indi.zyf.sso.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @Function: EmailVerificationCodeProcessor
 * @Description: 邮箱验证码
 * @param:
 * @return:
 * @throws: 异常描述
 * @version: v1.0.0
 * @author: zyf
 * @date: 2019/1/18 9:39
 * <p>
 * Modification History:
 * Date                  Author        Version         Description
 * -----------------------------------------------------------------*
 * 2019/1/18 9:39      zyf            v1.0.0           修改原因
 */
@Component("emailVerificationCodeProcessor")
public class EmailVerificationCodeProcessor extends AbstractVerificationCodeProcessor<VerificationCode> {
    /**
     * 邮件发送工具
     */
    @Autowired
    private EmailUtil emailUtil;

    @Value("${debug}")
    private String debug;

    /**
     * @Function: EmailVerificationCodeProcessor
     * @Description: 发送验证码
     * @param:
     * @return:
     * @throws: 异常描述
     * @version: v1.0.0
     * @author: zyf
     * @date: 2019/1/18 10:17
     * <p>
     * Modification History:
     * Date                  Author        Version         Description
     * -----------------------------------------------------------------*
     * 2019/1/18 10:17      zyf            v1.0.0           修改原因
     */
    @Override
    protected void send(ServletWebRequest request, VerificationCode verificationCode) throws Exception {
        String username = ServletRequestUtils.getStringParameter(request.getRequest(), "username");
        logger.info(username + " 的邮箱验证码:" + verificationCode.getCode());
        if (!StringUtil.isEmail(username)) {
            throw new VerificationCodeException("邮箱格式错误");
        }
        try {
            //发送邮件
            if(!"true".equals(debug)) {//如果不是debug模式才发送验证码
                emailUtil.sendVerificationCodeEmail(username, verificationCode.getCode());
            }
            //给前端响应
            ApiResult apiResult = new ApiResult(ErrorCode.SUCCESS);
            apiResult.setErrMsg("邮件发送成功");
            respond(request.getResponse(), apiResult);
            //添加发送记录
            saveSendRecord(request, verificationCode, VerificationCodeType.EMAIL);
        } catch (EmailSendException e) {
            logger.error(e);
            throw e;
        }
    }

}
