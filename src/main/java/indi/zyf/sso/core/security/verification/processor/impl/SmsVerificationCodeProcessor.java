package indi.zyf.sso.core.security.verification.processor.impl;

import indi.zyf.sso.core.ErrorCode;
import indi.zyf.sso.core.security.exception.EmailSendException;
import indi.zyf.sso.core.security.exception.SMSSendException;
import indi.zyf.sso.core.security.exception.VerificationCodeException;
import indi.zyf.sso.core.security.verification.pojo.VerificationCode;
import indi.zyf.sso.core.security.verification.pojo.VerificationCodeType;
import indi.zyf.sso.core.security.verification.processor.AbstractVerificationCodeProcessor;
import indi.zyf.sso.util.SMSUtil;
import indi.zyf.sso.util.StringUtil;
import indi.zyf.sso.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
* @Function: SMSVerificationCodeProcessor
* @Description: 短信验证码处理器
*
* @param:
* @return:
* @throws: 异常描述
*
* @version: v1.0.0
* @author: zyf
* @date: 2019/1/23 10:28
*
* Modification History:
* Date                  Author        Version         Description
*-----------------------------------------------------------------*
* 2019/1/23 10:28      zyf            v1.0.0           修改原因
*/
@Component
public class SmsVerificationCodeProcessor extends AbstractVerificationCodeProcessor<VerificationCode> {

    @Autowired
    private SMSUtil smsUtil;
    @Value("${debug}")
    private String debug;
    @Override
    protected void send(ServletWebRequest request, VerificationCode verificationCode) throws Exception {
        String username = ServletRequestUtils.getStringParameter(request.getRequest(), "username");
        logger.info(username + " 的手机验证码:" + verificationCode.getCode());
        if(!StringUtil.isMobile(username)){
            throw new VerificationCodeException("手机号格式错误");
        }
        try {
            //发送短信
            if(!"true".equals(debug)){//如果不是debug模式才发送验证码
                smsUtil.sendCode(username, verificationCode.getCode());
            }
            //给前端响应
            ApiResult apiResult = new ApiResult(ErrorCode.SUCCESS);
            apiResult.setErrMsg("短信发送成功");
            respond(request.getResponse(), apiResult);
            //添加发送记录
            saveSendRecord(request, verificationCode, VerificationCodeType.SMS);
        } catch (SMSSendException e) {
            logger.error(e);
            throw e;
        }
    }
}
