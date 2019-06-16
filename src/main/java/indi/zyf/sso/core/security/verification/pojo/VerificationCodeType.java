package indi.zyf.sso.core.security.verification.pojo;

import indi.zyf.sso.core.SystemConstants;
/**
* @Function: VerificationCodeType
* @Description: 验证码类型
 *              enum 要对应VerificationCodeGenerator子类和VerificationCodeProcessor子类的类名前缀
*
* @param:
* @return:
* @throws: 异常描述
*
* @version: v1.0.0
* @author: zyf
* @date: 2019/1/18 21:45
*
* Modification History:
* Date                  Author        Version         Description
*-----------------------------------------------------------------*
* 2019/1/18 21:45      zyf            v1.0.0           修改原因
*/
public enum VerificationCodeType {
    /**
     * 短信验证码
     */
    SMS {
        @Override
        public String getParamNameOnValidate() {
            return SystemConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
        }
    },
    /**
     * 图片验证码
     */
    IMAGE {
        @Override
        public String getParamNameOnValidate() {
            return SystemConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }
    },
    /**
     * 邮箱验证码
     *
     */
    EMAIL {
        @Override
        public String getParamNameOnValidate() {
            return SystemConstants.DEFAULT_PARAMETER_NAME_CODE_EMAIL;
        }
    };

    /**
     * 校验时从请求中获取的参数的名字
     *
     * @return
     */
    public abstract String getParamNameOnValidate();
}
