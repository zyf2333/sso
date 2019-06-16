package indi.zyf.sso.core.security.verification.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.awt.image.BufferedImage;
import java.util.Date;

/**
 * @Function: ImageCode
 * @Description: 图片验证码
 * @param:
 * @return:
 * @throws: 异常描述
 * @version: v1.0.0
 * @author: zyf
 * @date: 2019/1/17 10:35
 * <p>
 * Modification History:
 * Date                  Author        Version         Description
 * -----------------------------------------------------------------*
 * 2019/1/17 10:35      zyf            v1.0.0           修改原因
 */
public class ImageCode extends VerificationCode  {

    @JsonIgnore
    private BufferedImage bufferedImage;

    public ImageCode() {
        super();
    }
    public ImageCode(String code, BufferedImage bufferedImage, Date expireTime) {
        super(code, expireTime);
        this.bufferedImage = bufferedImage;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }
}
