package indi.zyf.sso.mapper;

import indi.zyf.sso.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(SysUser record);

	int insertSelective(SysUser record);

	SysUser selectByPrimaryKey(Integer id);

	List<SysUser> selectByState(Integer state);

	SysUser selectByUsername(String username);

	int updateByPrimaryKeySelective(SysUser record);

	int updateByPrimaryKey(SysUser record);

	int count(Integer state);

	/**
	 *
	 * @Function: SysUserMapper.java
	 * @Description: 不根据状态查询用户数据
	 *
	 * @param:用户名
	 * @return：用户 @throws：
	 *
	 * @version: v1.0.0
	 * @author: LCJA
	 * @date: 2018年10月31日 下午3:56:00
	 *
	 *        Modification History: Date Author Version Description
	 *        ---------------------------------------------------------*
	 *        2018年10月31日 LCJA v1.0.0 不根据状态查询用户数据
	 */
	SysUser selectByUsernameDisable(String username);

	// 平台级查询
	List<SysUser> queryPlatformSchool(@Param("ext1") String ext1);

	// 幼儿园级查询
	List<SysUser> querySchoolTeacher(@Param("sid") Integer sid, @Param("id") Integer id);

	// 平台级的账号
	List<SysUser> selectLikeByName(String name);

	// 院级
	List<SysUser> selectLikeByNameSchool(@Param("name") String name, @Param("sId") Integer sId);

	// 教师列表
	List<SysUser> selectBySchoolId(@Param("ext1") String ext1);

	int teacherListCount(@Param("ext1") String schoolId);

	List<SysUser> selectByClazzId(@Param("ext2") String clazzId);

	int queryClazzAllTeacher(@Param("cid") Integer cid);

	// 查询所有老师
	List<SysUser> selectTeacherByCid(@Param("cid") Integer cid);

	// 查询所有老师
	List<SysUser> queryAllSchoolTeacher(@Param("sid") Integer sid);

	// 查询用户
	SysUser queryByUsername(String username);

	// 查询用户（不是政府平台的用户）
	SysUser queryGovUser(String username);

	List<SysUser> queryClazzAllTeacherList(@Param("cId") Integer cid, @Param("endTime") String endTime);

	// 查询学校所有老师包括园所管理员
	int querySchoolAllTeacher(@Param("sId") Integer sid, @Param("endTime") String endTime);
}