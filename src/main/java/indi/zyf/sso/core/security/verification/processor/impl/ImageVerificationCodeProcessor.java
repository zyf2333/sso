package indi.zyf.sso.core.security.verification.processor.impl;

import indi.zyf.sso.core.security.verification.pojo.ImageCode;
import indi.zyf.sso.core.security.verification.processor.AbstractVerificationCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

/**
* @Function: ImageVerificationCodeProcessor
* @Description: 图片验证码
*
* @param:
* @return:
* @throws: 异常描述
*
* @version: v1.0.0
* @author: zyf
* @date: 2019/1/18 9:39
*
* Modification History:
* Date                  Author        Version         Description
*-----------------------------------------------------------------*
* 2019/1/18 9:39      zyf            v1.0.0           修改原因
*/
@Component("imageVerificationCodeProcessor")
public class ImageVerificationCodeProcessor extends AbstractVerificationCodeProcessor<ImageCode> {
    /**
    * @Function: ImageVerificationCodeProcessor
    * @Description: 发送验证码
    *
    * @param:
    * @return:
    * @throws: 异常描述
    *
    * @version: v1.0.0
    * @author: zyf
    * @date: 2019/1/18 10:17
    *
    * Modification History:
    * Date                  Author        Version         Description
    *-----------------------------------------------------------------*
    * 2019/1/18 10:17      zyf            v1.0.0           修改原因
    */
    @Override
    protected void send(ServletWebRequest request, ImageCode verificationCode) throws Exception {
        String username = ServletRequestUtils.getStringParameter(request.getRequest(), "username");
        logger.info(username+" 的图片验证码:"+verificationCode.getCode());
        ImageIO.write(verificationCode.getBufferedImage(), "JPEG", request.getResponse().getOutputStream());
    }

}
