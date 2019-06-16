package indi.zyf.sso.model;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class StaticData {
    private Integer id;
    @ApiModelProperty(value="编号",allowableValues="12345678")

    private String staticKey;
    @ApiModelProperty(value="静态数据代号",allowableValues="123")

    private String staticValue;
    @ApiModelProperty(value="静态数据值",allowableValues="123")

    private Integer state;

    private Date createDate;

    private Date updateDate;

    private String ext1;

    private String ext2;

    private String ext3;

    private String ext4;

    private String ext5;
    @ApiModelProperty(value="描述",allowableValues="123")

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStaticKey() {
        return staticKey;
    }

    public void setStaticKey(String staticKey) {
        this.staticKey = staticKey == null ? null : staticKey.trim();
    }

    public String getStaticValue() {
        return staticValue;
    }

    public void setStaticValue(String staticValue) {
        this.staticValue = staticValue == null ? null : staticValue.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1 == null ? null : ext1.trim();
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2 == null ? null : ext2.trim();
    }

    public String getExt3() {
        return ext3;
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3 == null ? null : ext3.trim();
    }

    public String getExt4() {
        return ext4;
    }

    public void setExt4(String ext4) {
        this.ext4 = ext4 == null ? null : ext4.trim();
    }

    public String getExt5() {
        return ext5;
    }

    public void setExt5(String ext5) {
        this.ext5 = ext5 == null ? null : ext5.trim();
    }
}