package indi.zyf.sso.core.security.verification.pojo;


import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * @Function: VerificationCode
 * @Description: 验证码
 * @param:
 * @return
 * @throws: 异常描述
 * @version: v1.0.0
 * @author: zyf
 * @date: 2019/1/17 10:34
 * <p>
 * Modification History:
 * Date                  Author        Version         Description
 * -----------------------------------------------------------------*
 * 2019/1/17 10:34      zyf            v1.0.0           修改原因
 */
public class VerificationCode implements Serializable{

    private static final long serialVersionUID = 4987537872643180180L;

    private String id;

    private String code;

    private Date expireTime;

    public VerificationCode() {

    }

    public VerificationCode(String code, Date expireTime) {
        this.id = UUID.randomUUID().toString();
        this.code = code;
        this.expireTime = expireTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return "VerificationCode{" +
                "code='" + code + '\'' +
                ", expireTime=" + expireTime +
                '}';
    }

}
