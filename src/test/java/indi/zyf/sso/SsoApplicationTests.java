package indi.zyf.sso;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SsoApplicationTests {

    @Autowired
    private JavaMailSender mailSender; //框架自带的

    @Test
    public void contextLoads() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("zyf69172@163.com"); // 发送人的邮箱
        message.setSubject("验证码邮件"); //标题
        message.setTo("691721078@qq.com"); //发给谁  对方邮箱
        message.setCc("zyf69172@163.com");
        message.setText("您的验证码是453644"); //内容
        mailSender.send(message);
    }

}

