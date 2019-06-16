package indi.zyf.sso.model;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class School {
    private Integer id;
    @ApiModelProperty(value="编号",allowableValues="12345678")

    private String schoolName;
    @ApiModelProperty(value="幼儿园名称",allowableValues="测试幼儿园")

    private String province;
    @ApiModelProperty(value="省",allowableValues="山西省")

    private String city;
    @ApiModelProperty(value="市",allowableValues="太原市")

    private String region;
    @ApiModelProperty(value="区、县",allowableValues="小店区")

    private String phone;
    @ApiModelProperty(value="电话",allowableValues="8122222")

    private String address;
    @ApiModelProperty(value="地址",allowableValues="平阳路学府街22号")

    private String lalo;
    @ApiModelProperty(value="经纬度",allowableValues="")

    private String leader;
    @ApiModelProperty(value="联系人",allowableValues="张三")

    private String title;

    private Integer state;

    private Date createDate;

    private Date updateDate;

    private String ext1;
    @ApiModelProperty(value="公办 1  民办2   公有民办3 全民事业4   企事业单位办园5   部队办园6  普惠性幼儿园7  其他10",allowableValues="1")

    private String ext2;
    @ApiModelProperty(value="院长",allowableValues="张三")

    private String ext3;
    @ApiModelProperty(value="院长电话",allowableValues="13811111111")

    private String ext4;

    private String ext5;
    
    private Object other;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName == null ? null : schoolName.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region == null ? null : region.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getLalo() {
        return lalo;
    }

    public void setLalo(String lalo) {
        this.lalo = lalo == null ? null : lalo.trim();
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader == null ? null : leader.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
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

	public Object getOther() {
		return other;
	}

	public void setOther(Object other) {
		this.other = other;
	}
    
    
    
}