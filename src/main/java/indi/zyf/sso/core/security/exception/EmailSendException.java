package indi.zyf.sso.core.security.exception;
/**
* @Function: EmailSendException
* @Description: 邮件发送异常
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
public class EmailSendException extends RuntimeException {
    public EmailSendException(String message) {
        super(message);
    }

    public EmailSendException(String message, Throwable cause) {
        super(message, cause);
    }
}
