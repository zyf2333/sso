package indi.zyf.sso.core.security.verification.controller;


import indi.zyf.sso.core.SystemConstants;
import indi.zyf.sso.core.security.verification.processor.VerificationCodeProcessorHolder;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @Function: VerificationCodeController
 * @Description: 验证码
 * @param:
 * @return:
 * @throws: 异常描述
 * @version: v1.0.0
 * @author: zyf
 * @date: 2019/1/17 10:25
 * <p>
 * Modification History:
 * Date                  Author        Version         Description
 * -----------------------------------------------------------------*
 * 2019/1/17 10:25      zyf            v1.0.0           修改原因
 */
@Api(value = "", description = "验证码获取")
@RestController
public class VerificationCodeController {
    @Autowired
    private VerificationCodeProcessorHolder verificationCodeProcessorHolder;

    /**
    * @Function: VerificationCodeController
    * @Description: 通过参数type，发送对应类型的验证码
    *
    * @param:
    * @return:
    * @throws: 异常描述
    *
    * @version: v1.0.0
    * @author: zyf
    * @date: 2019/1/18 9:15
    *
    * Modification History:
    * Date                  Author        Version         Description
    *-----------------------------------------------------------------*
    * 2019/1/18 9:15      zyf            v1.0.0           修改原因
    */
    @GetMapping(SystemConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{type}")
    public void getVerificationCode(ServletWebRequest request,@PathVariable String type) throws Exception {
        verificationCodeProcessorHolder.findValidateCodeProcessor(type).create(request);
    }

}
