package indi.zyf.sso.util;

import indi.zyf.sso.core.SystemConstants;
import indi.zyf.sso.core.security.exception.EmailSendException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

/**
 * @Function: EmailUtil
 * @Description: 邮件发送工具
 * @param:
 * @return:
 * @throws: 异常描述
 * @version: v1.0.0
 * @author: zyf
 * @date: 2019/1/18 17:23
 * <p>
 * Modification History:
 * Date                  Author        Version         Description
 * -----------------------------------------------------------------*
 * 2019/1/18 17:23      zyf            v1.0.0           修改原因
 */
@Component
public class EmailUtil {
    @Autowired
    private JavaMailSender mailSender; //框架自带的

    private Logger logger = LoggerFactory.getLogger(getClass());


    public boolean sendEmail(String from, String to, String title, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from); // 发送人的邮箱
        message.setSubject(title); //标题
        message.setTo(to); //发给谁  对方邮箱
//        message.setCc(from);//抄送
        message.setText(content); //内容
        try {
            mailSender.send(message);
            logger.info(to+" 的邮件<" + title + ">发送成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new EmailSendException("邮件发送失败", e);
        }
        return true;
    }


    public boolean sendVerificationCodeEmail(String to, String code) {
        //默认使用静态配置文件中的发送方邮箱，如果yml文件中配置了username，则使用username
        String from =SystemConstants.DEFAULT_EMAIL_FROM;
        if(mailSender instanceof JavaMailSenderImpl){
            JavaMailSenderImpl jsi = (JavaMailSenderImpl)mailSender;
            if(StringUtils.isEmpty(from)){
                from = jsi.getUsername();
            }
        }
        return sendEmail(from,to,"验证码邮件", StringUtils.replace(
                SystemConstants.DEFAULT_EMAIL_CODE_CONTENT,"CODE",code));
    }
}
