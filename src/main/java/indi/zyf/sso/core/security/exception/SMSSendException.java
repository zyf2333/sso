package indi.zyf.sso.core.security.exception;
/**
* @Function: SMSSendException
* @Description: 短信发送异常
*
* @param:
* @return:
* @throws: 异常描述
*
* @version: v1.0.0
* @author: zyf
* @date: 2019/1/18 19:00
*
* Modification History:
* Date                  Author        Version         Description
*-----------------------------------------------------------------*
* 2019/1/18 19:00      zyf            v1.0.0           修改原因
*/
public class SMSSendException extends RuntimeException {
    public SMSSendException(String message) {
        super(message);
    }

    public SMSSendException(String message, Throwable cause) {
        super(message, cause);
    }
}
