package indi.zyf.sso.core.security.verification.processor;

import indi.zyf.sso.core.security.exception.VerificationCodeException;
import indi.zyf.sso.core.security.verification.pojo.VerificationCodeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
/**
 * @Function: VerificationCodeProcessorHolder
 * @Description: 通过参数type，选择合适的验证码
 *
 * @param:
 * @return:
 * @throws: 异常描述
 *
 * @version: v1.0.0
 * @author: zyf
 * @date: 2019/1/18 9:06
 *
 * Modification History:
 * Date                  Author        Version         Description
 *-----------------------------------------------------------------*
 * 2019/1/18 9:06      zyf            v1.0.0           修改原因
 */
@Component
public class VerificationCodeProcessorHolder {

    @Autowired
    private Map<String, VerificationCodeProcessor> verificationCodeProcessors;

    public VerificationCodeProcessor findValidateCodeProcessor(VerificationCodeType type) {
        return findValidateCodeProcessor(type.toString().toLowerCase());
    }

    /**
    * @Function: VerificationCodeProcessorHolder
    * @Description: 通过type查询对应的验证码处理器
    *
    * @param:
    * @return:
    * @throws: 异常描述
    *
    * @version: v1.0.0
    * @author: zyf
    * @date: 2019/1/18 21:33
    *
    * Modification History:
    * Date                  Author        Version         Description
    *-----------------------------------------------------------------*
    * 2019/1/18 21:33      zyf            v1.0.0           修改原因
    */
    public VerificationCodeProcessor findValidateCodeProcessor(String type) {
        //新建的processor命名必须保证以VerificationCodeProcessor结尾
        String name = type.toLowerCase() + VerificationCodeProcessor.class.getSimpleName();
        VerificationCodeProcessor processor = verificationCodeProcessors.get(name);
        if (processor == null) {
            throw new VerificationCodeException("验证码处理器" + name + "不存在");
        }
        return processor;
    }

}
