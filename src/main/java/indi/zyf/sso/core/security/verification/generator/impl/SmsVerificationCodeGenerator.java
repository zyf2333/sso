package indi.zyf.sso.core.security.verification.generator.impl;

import indi.zyf.sso.core.security.verification.generator.VerificationCodeGenerator;
import indi.zyf.sso.core.security.verification.pojo.VerificationCode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Date;

/**
* @Function: SMSVerificationCodeGenerator
* @Description: 短信验证码生成器
*
* @param:
* @return:
* @throws: 异常描述
*
* @version: v1.0.0
* @author: zyf
* @date: 2019/1/23 10:17
*
* Modification History:
* Date                  Author        Version         Description
*-----------------------------------------------------------------*
* 2019/1/23 10:17      zyf            v1.0.0           修改原因
*/
@Component
public class SmsVerificationCodeGenerator extends VerificationCodeGenerator {

    /**
    * @Function: SMSVerificationCodeGenerator
    * @Description: 生成验证码
    *
    * @param:
    * @return:
    * @throws: 异常描述
    *
    * @version: v1.0.0
    * @author: zyf
    * @date: 2019/1/23 10:18
    *
    * Modification History:
    * Date                  Author        Version         Description
    *-----------------------------------------------------------------*
    * 2019/1/23 10:18      zyf            v1.0.0           修改原因
    */
    @Override
    public VerificationCode generate(ServletWebRequest request) {
        int length = 6;//验证码长度
        int expireTime = 5*60*1000;//超时时间 毫秒
        String code = getRandomNum(length);

        Date time = new Date();//当前时间
        time.setTime(time.getTime()+expireTime);
        return  new VerificationCode(code,time);
    }
}
