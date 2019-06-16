package indi.zyf.sso.core.security.verification.generator.impl;

import indi.zyf.sso.core.security.verification.generator.VerificationCodeGenerator;
import indi.zyf.sso.core.security.verification.pojo.ImageCode;
import indi.zyf.sso.core.security.verification.pojo.VerificationCode;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Random;

/**
* @Function: EmailVerificationCodeGenerator
* @Description: 邮箱验证码生成器
*
* @param:
* @return:
* @throws: 异常描述
*
* @version: v1.0.0
* @author: zyf
* @date: 2019/1/18 9:43
*
* Modification History:
* Date                  Author        Version         Description
*-----------------------------------------------------------------*
* 2019/1/18 9:43      zyf            v1.0.0           修改原因
*/
@Component
public class EmailVerificationCodeGenerator extends VerificationCodeGenerator {

    @Override
    public VerificationCode generate(ServletWebRequest request) {
        int length = 6;//验证码长度
        int expireTime = 5*60*1000;//超时时间 毫秒
        String code = getRandomStr(length);

        Date time = new Date();//当前时间
        time.setTime(time.getTime()+expireTime);
        return  new VerificationCode(code,time);
    }

}
