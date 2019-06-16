package indi.zyf.sso.core.security.verification.generator;

import indi.zyf.sso.core.security.verification.pojo.VerificationCode;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Random;

/**
* @Function: VerificationCodeGenerator
* @Description: 验证码生成器接口
*
* @param:
* @return:
* @throws: 异常描述
*
* @version: v1.0.0
* @author: zyf
* @date: 2019/1/18 9:27
*
* Modification History:
* Date                  Author        Version         Description
*-----------------------------------------------------------------*
* 2019/1/18 9:27      zyf            v1.0.0           修改原因
*/
public abstract class VerificationCodeGenerator {
    /**
     * 生成验证码
     * @param request
     * @return
     */
    public abstract VerificationCode generate(ServletWebRequest request);

    //生成随机字符时，字符来源
    private static String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static int str_len = str.length();
    /**
     * 获取指定位数的随机数(各种类型字符混合)
     * @param length
     * @return
     */
    public static String getRandomStr(int length) {
        if (length <= 0) {
            length = 1;
        }
        Random random = new Random();
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < length; i++) {
            res.append(str.charAt(random.nextInt(str_len)));
        }
        return res.toString();
    }

    /**
     * 获取指定位数的随机数(纯数字)
     * @param length 随机数的位数
     * @return String
     */
    public static String getRandomNum(int length) {
        if (length <= 0) {
            length = 1;
        }
        StringBuilder res = new StringBuilder();
        Random random = new Random();
        int i = 0;
        while (i < length) {
            res.append(random.nextInt(10));
            i++;
        }
        return res.toString();
    }
}
