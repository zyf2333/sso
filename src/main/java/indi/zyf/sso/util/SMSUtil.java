package indi.zyf.sso.util;

import indi.zyf.sso.core.security.exception.SMSSendException;
import indi.zyf.sso.util.sms.client.AbsRestClient;
import indi.zyf.sso.util.sms.client.JsonReqClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
* @Function: SMSUtil
* @Description: 短信发送工具类
*
* @param:
* @return:
* @throws: 异常描述
*
* @version: v1.0.0
* @author: zyf
* @date: 2019/1/21 16:27
*
* Modification History:
* Date                  Author        Version         Description
*-----------------------------------------------------------------*
* 2019/1/21 16:27      zyf            v1.0.0           修改原因
*/
@Component
public class SMSUtil {

    /**
     * ==================短信相关配置=========================
     *
     */
    public static final String SMS_APPID = "12fb75fcd73b45c18284dd9de98d2555";
    public static final String SMS_SID = "30f3af21e976f56f2be8009401b1d562";
    public static final String SMS_TOKEN = "4aeb13cf7f3a812dcb8335b584fd5528";
    public static final String SMS_TEMPLATEID = "41926";//默认验证码模版id

    @Autowired
    public AbsRestClient absRestClient;

    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
    * @Function: SMSUtil
    * @Description: 向某个用户发送验证码
    *
    * @param: mobile 手机号
    * @param: code 验证码
    * @param: templateId 模版id
    * @return:
    * @throws: 异常描述
    *
    * @version: v1.0.0
    * @author: zyf
    * @date: 2019/1/21 16:41
    *
    * Modification History:
    * Date                  Author        Version         Description
    *-----------------------------------------------------------------*
    * 2019/1/21 16:41      zyf            v1.0.0           修改原因
    */
    public String sendCode(String mobile,String code,String templateId) {
        String result = null;
        try {
            result = absRestClient.sendSms(SMS_SID, SMS_TOKEN, SMS_APPID, templateId, code, mobile, "");
            logger.debug(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new SMSSendException("短信发送失败",e);
        }
        logger.info("短信验证码发送成功");
        return result;
    }
    /**
     * @Function: SMSUtil
     * @Description: 向某个用户发送验证码
     *
     * @param: mobile 手机号
     * @param: code 验证码
     * @return:
     * @throws: 异常描述
     *
     * @version: v1.0.0
     * @author: zyf
     * @date: 2019/1/21 16:41
     *
     * Modification History:
     * Date                  Author        Version         Description
     *-----------------------------------------------------------------*
     * 2019/1/21 16:41      zyf            v1.0.0           修改原因
     */
    public String sendCode(String mobile,String code) {
        return sendCode(mobile, code, SMS_TEMPLATEID);
    }



}
