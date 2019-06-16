package indi.zyf.sso.task;

import indi.zyf.sso.core.SystemConstants;
import indi.zyf.sso.util.WeixinUtil;
import indi.zyf.sso.vo.wx.AccessToken;
import indi.zyf.sso.vo.wx.Jsapi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class GetWechatConfigTask {
    private Logger logger = LoggerFactory.getLogger(GetWechatConfigTask.class);
    // 第三方用户唯一凭证
    public static AccessToken accessToken = null;
    public static Jsapi jsapi = null;
    public static Integer count = 0;//调用计数，防止死循环
    /**
     * @Function: GetWechatConfigTask
     * @Description: 获取微信参数
     * @param:
     * @return:
     * @throws: 异常描述
     * @version: v1.0.0
     * @author: zyf
     * @date: 2019/1/10 9:39
     * <p>
     * Modification History:
     * Date                  Author        Version         Description
     * -----------------------------------------------------------------*
     * 2019/1/10 9:39      zyf            v1.0.0           修改原因
     */
    @Scheduled(initialDelay = 1000, fixedRate = 2 * 55 * 60 * 1000)
    public void execute() {
        logger.debug("获取微信参数");
        count++;
        try {
            logger.debug("获取accessToken");
            accessToken = WeixinUtil.getAccessToken(SystemConstants.APPID_SCHOOL, SystemConstants.APPSECRET_SCHOOL);
            if (null != accessToken) {
                SystemConstants.ACCESS_TOKEN = accessToken.getAccessToken();
                // 保存为常量
                logger.info("获取access_token成功，有效时长{}秒 token:{}", accessToken.getExpiresIn(),
                        accessToken.getAccessToken());
            } else {
                if (count < 5) {
                    execute();
                    return;
                }
            }

            logger.debug("获取jsapi");
            jsapi = WeixinUtil.getJsapi("dphQrj4oAJNHhpdW");
            if (null != jsapi && "" != jsapi.getTicket() && null != jsapi.getTicket()) {
                SystemConstants.TICKET = jsapi.getTicket();
                logger.info("获取jsapi成功，有效时长{}秒 ticket:{}", jsapi.getExpiresIn(), jsapi.getTicket());
                count=0;
            } else {
                if (count < 5) {
                    execute();
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }


    }
}
