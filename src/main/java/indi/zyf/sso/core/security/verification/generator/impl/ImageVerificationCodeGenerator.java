package indi.zyf.sso.core.security.verification.generator.impl;

import indi.zyf.sso.core.security.verification.generator.VerificationCodeGenerator;
import indi.zyf.sso.core.security.verification.pojo.ImageCode;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Random;

/**
* @Function: ImageCodeGenerator
* @Description: 图片验证码生成器
*
* @param:
* @return:
* @throws: 异常描述
*
* @version: v1.0.0
* @author: zyf
* @date: 2019/1/18 9:43
*
* Modification History:
* Date                  Author        Version         Description
*-----------------------------------------------------------------*
* 2019/1/18 9:43      zyf            v1.0.0           修改原因
*/
@Component
public class ImageVerificationCodeGenerator extends VerificationCodeGenerator {

    @Override
    public ImageCode generate(ServletWebRequest request) {
        // 宽度
        int width = ServletRequestUtils.getIntParameter(request.getRequest(), "width",
                80);
        // 高度
        int height = ServletRequestUtils.getIntParameter(request.getRequest(), "height",
                40);
        //验证码长度
        int length = 6;
        //超时时间 毫秒
        int expireTime = 5*60*1000;

        Object[] obj = createImage(width,height,length);
        String code = (String) obj[0];
        BufferedImage image = (BufferedImage) obj[1];
        Date time = new Date();//当前时间
        time.setTime(time.getTime()+expireTime);
        return new ImageCode(code,image,time);
    }


    // 验证码字符集
    private static final char[] chars = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * 生成随机验证码及图片
     */
    public static Object[] createImage(int width,int height,int length) {
        // 字符数量
        int size = 4;
        // 干扰线数量
        int lines = 5;
        // 字体大小
        int font_size = 28;

        StringBuffer sb = new StringBuffer();
        // 1.创建空白图片
        BufferedImage image = new BufferedImage(
                width, height, BufferedImage.TYPE_INT_RGB);
        // 2.获取图片画笔
        Graphics graphic = image.getGraphics();
        // 3.设置画笔颜色
        graphic.setColor(Color.white);//LIGHT_GRAY
        // 4.绘制矩形背景
        graphic.fillRect(0, 0, width, height);
        // 5.画随机字符
        Random ran = new Random();
        for (int i = 0; i < size; i++) {
            // 取随机字符索引
            int n = ran.nextInt(chars.length);
            // 设置随机颜色
            graphic.setColor(getRandomColor());
            // 设置字体大小
            graphic.setFont(new Font(
                    null, Font.BOLD + Font.ITALIC, font_size));
            // 画字符，字符位置
            graphic.drawString(
                    chars[n] + "", i * width / size, height );
            // 记录字符
            sb.append(chars[n]);
        }
        // 6.画干扰线
        for (int i = 0; i < lines; i++) {
            // 设置随机颜色
            graphic.setColor(getRandomColor());
            // 随机画线
            graphic.drawLine(ran.nextInt(width), ran.nextInt(height),
                    ran.nextInt(width), ran.nextInt(height));
        }
        // 7.返回验证码和图片
        return new Object[]{sb.toString(), image};
    }

    /**
     * 随机取色
     */
    public static Color getRandomColor() {
        Random ran = new Random();
        Color color = new Color(ran.nextInt(256),
                ran.nextInt(256), ran.nextInt(256));
        return color;
    }



}
