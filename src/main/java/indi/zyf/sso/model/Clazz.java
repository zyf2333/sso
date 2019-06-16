package indi.zyf.sso.model;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

public class Clazz {
    private Integer id;
    @ApiModelProperty(value="编号",allowableValues="12345678")

    private String schoolName;
    @ApiModelProperty(value="班级名称",allowableValues="大一班")

    private String sId;
    @ApiModelProperty(value="幼儿园id",allowableValues="17")

    private String phone;
    @ApiModelProperty(value="联系电话",allowableValues="13812341234")

    private String leader;
    @ApiModelProperty(value="负责老师",allowableValues="赵六")

    private String title;

    private Integer state;

    private Date createDate;

    private Date updateDate;

    private String ext1;
    @ApiModelProperty(value="所在年级",allowableValues="中班")

    private String ext2;

    private String ext3;

    private String ext4;

    private String ext5;
    
    private Object other;
    
    private Integer teacherCount;
    
    private Integer childCount;
    
    private List<SysUser> teacherList;

    public Integer getTeacherCount() {
		return teacherCount;
	}

	public void setTeacherCount(Integer teacherCount) {
		this.teacherCount = teacherCount;
	}

	public Integer getChildCount() {
		return childCount;
	}

	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}

	public List<SysUser> getTeacherList() {
		return teacherList;
	}

	public void setTeacherList(List<SysUser> teacherList) {
		this.teacherList = teacherList;
	}

	private Integer[] teachers;

	private String[] teachersStrings;    
	
    private String leaderName;
    
    private String leaderUsername;
    
	public Object getOther() {
		return other;
	}

	public void setOther(Object other) {
		this.other = other;
	}

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

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId == null ? null : sId.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
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
//
//	public List<SysUser> getTeachers() {
//		return teachers;
//	}
//
//	public void setTeachers(List<SysUser> teachers) {
//		this.teachers = teachers;
//	}

    
	public String getLeaderName() {
		return leaderName;
	}

	public Integer[] getTeachers() {
		return teachers;
	}

	public void setTeachers(Integer[] teachers) {
		this.teachers = teachers;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	public String getLeaderUsername() {
		return leaderUsername;
	}

	public void setLeaderUsername(String leaderUsername) {
		this.leaderUsername = leaderUsername;
	}

	public String[] getTeachersStrings() {
		return teachersStrings;
	}

	public void setTeachersStrings(String[] teachersStrings) {
		this.teachersStrings = teachersStrings;
	}


    
}