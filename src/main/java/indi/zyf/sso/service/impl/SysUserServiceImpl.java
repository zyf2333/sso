package indi.zyf.sso.service.impl;

import com.github.pagehelper.PageHelper;
import indi.zyf.sso.core.ErrorCode;
import indi.zyf.sso.mapper.*;
import indi.zyf.sso.model.*;
import indi.zyf.sso.service.ISysUserService;
import indi.zyf.sso.util.MessageUtil;
import indi.zyf.sso.util.StringUtil;
import indi.zyf.sso.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SysUserServiceImpl implements ISysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SchoolMapper schoolMapper;
	@Autowired
	private ClazzMapper clazzMapper;
	@Autowired
	private SysUserOpenIdRelMapper sysUserOpenIdRelMapper;
	@Autowired
	private LoginStatisticsMapper loginStatisticsMapper;

	@Override
	public ApiResult login(SysUser user) {
		SysUser selectByUsername = sysUserMapper.selectByUsername(user.getUsername());
		if (selectByUsername != null) {
			if (selectByUsername.getPassword().equals(user.getPassword())) {
				ApiResult apiResult = new ApiResult(ErrorCode.SUCCESS);
				selectByUsername.setPassword(null);
				if (!StringUtil.isEmpty(selectByUsername.getExt1())) {
					selectByUsername.setSchoolName(schoolMapper
							.selectByPrimaryKey(Integer.valueOf(selectByUsername.getExt1())).getSchoolName());
				}
				if (!StringUtil.isEmpty(selectByUsername.getExt2())) {
					selectByUsername.setClazzName(clazzMapper
							.selectByPrimaryKey(Integer.valueOf(selectByUsername.getExt2())).getSchoolName());

				}
				apiResult.setData(selectByUsername);
				return apiResult;
			}
			return new ApiResult(ErrorCode.ERR_USER_WRONG_PWD);
		}
		return new ApiResult(ErrorCode.ERR_USER_NOT_EXIST);
	}

	@Override
	public ApiResult register(SysUser user) {
		if (!"".equals(user.getUsername()) && user.getUsername() != null && !"".equals(user.getPassword())
				&& user.getPassword() != null) {
			SysUser selectByUsername = sysUserMapper.selectByUsername(user.getUsername());
			if (selectByUsername != null) {
				return new ApiResult(ErrorCode.ERR_SYS_USER_EXIST);
			}
			sysUserMapper.insertSelective(user);
			return new ApiResult(ErrorCode.SUCCESS);
		}
		return new ApiResult(ErrorCode.ERROR_USERNAME_PASSWORD_IS_NULL);
	}

	@Override
	public ApiResult update(SysUser user) {
		sysUserMapper.updateByPrimaryKeySelective(user);
		return new ApiResult(ErrorCode.SUCCESS);
	}

	@Override
	public ApiResult query() {
		List<SysUser> selectByState = sysUserMapper.selectByState(1);
		ApiResult apiResult = new ApiResult();
		apiResult.setData(selectByState);
		return apiResult;
	}

	@Override
	public ApiResult del(Integer id) {
		sysUserMapper.deleteByPrimaryKey(id);
		return new ApiResult(ErrorCode.SUCCESS);
	}

	@Override
	public ApiResult count(SysUser user) {
		ApiResult apiResult = new ApiResult(ErrorCode.SUCCESS);
		apiResult.setData(sysUserMapper.count(1));
		return apiResult;
	}

	@Override
	public ApiResult pages(SysUser user, Integer nums, Integer sizes) {
		PageHelper.startPage(nums, sizes);
		List<SysUser> page2 = sysUserMapper.selectByState(1);
		page2.forEach(x -> {
			if (!StringUtil.isEmpty(x.getExt1())) {
				x.setSchoolName(schoolMapper.selectByPrimaryKey(Integer.valueOf(x.getExt1())).getSchoolName());
			}
			if (!StringUtil.isEmpty(x.getExt2())) {
				x.setClazzName(clazzMapper.selectByPrimaryKey(Integer.valueOf(x.getExt2())).getSchoolName());

			}
		});
		ApiResult apiResult = new ApiResult(ErrorCode.SUCCESS);
		apiResult.setData(page2);
		return apiResult;
	}

	@Override
	public ApiResult levelQuery(Integer id, Integer nums, Integer pages, SysUser sysUser) {
		SysUser user = sysUserMapper.selectByPrimaryKey(id);
		String power = user.getPower();
		// int count = 0;
		List<SysUser> arrayList = new ArrayList<SysUser>();
		switch (power) {
		case "1000":
			PageHelper.startPage(nums, pages);
			arrayList = sysUserMapper.queryPlatformSchool(sysUser.getExt1());
			break;

		case "0100":
			PageHelper.startPage(nums, pages);
			arrayList = sysUserMapper.querySchoolTeacher(Integer.valueOf(user.getExt1()), id);
			break;
		case "0010":
			arrayList.add(user);
			break;
		default:
			break;
		}
		arrayList.forEach(x -> {
			if (!StringUtil.isEmpty(x.getExt1())) {
				x.setSchoolName(schoolMapper.selectByPrimaryKey(Integer.valueOf(x.getExt1())).getSchoolName());
			}
			if (!StringUtil.isEmpty(x.getExt2())) {
				x.setClazzName(clazzMapper.selectByPrimaryKey(Integer.valueOf(x.getExt2())).getSchoolName());

			}
		});
		ApiResult apiResult = new ApiResult(ErrorCode.SUCCESS);
		apiResult.setData(arrayList);
		return apiResult;
	}

	@Override
	public ApiResult levelCount(Integer id, SysUser sysUser) {
		SysUser user = sysUserMapper.selectByPrimaryKey(id);
		String power = user.getPower();
		int count = 0;
		switch (power) {
		case "1000":
			List<SysUser> queryPlatformSchool = sysUserMapper.queryPlatformSchool(sysUser.getExt1());
			count += queryPlatformSchool.size();
			break;

		case "0100":
			List<SysUser> querySchoolTeacher = sysUserMapper.querySchoolTeacher(Integer.valueOf(user.getExt1()), id);
			count += querySchoolTeacher.size();
			break;
		case "0010":
			count = 1;
			break;
		default:
			break;
		}

		ApiResult apiResult = new ApiResult(ErrorCode.SUCCESS);
		apiResult.setData(count);
		return apiResult;
	}

	@Override
	public ApiResult password(Integer id) {
		SysUser selectByPrimaryKey = sysUserMapper.selectByPrimaryKey(id);
		ApiResult apiResult = new ApiResult(ErrorCode.SUCCESS);
		apiResult.setData(selectByPrimaryKey);
		return apiResult;
	}

	@Override
	public ApiResult username(String username) {
		SysUser selectByUsername = sysUserMapper.selectByUsername(username);
		if (selectByUsername != null) {
			return new ApiResult(ErrorCode.ERR_SYS_USER_EXIST);
		} else {
			return new ApiResult(ErrorCode.SUCCESS);
		}
	}

	/**
	* @Function: SysUserServiceImpl
	* @Description: 微信用户绑定openid
	*
	* @param:
	* @return:
	* @throws: 异常描述
	*
	* @version: v1.0.0
	* @author: zyf
	* @date: 2019/1/17 10:03
	*
	* Modification History:
	* Date                  Author        Version         Description
	*-----------------------------------------------------------------*
	* 2019/1/17 10:03      zyf            v1.0.0           修改原因
	*/
	@Override
	public ApiResult loginandregister(SysUserOpenIdRel rel, HttpServletRequest request) {
		ApiResult result = new ApiResult(ErrorCode.SUCCESS);
		SysUser user = sysUserMapper.queryByUsername(rel.getUsername());
		if (user != null) {
			if(user.getState()==0){
				return new ApiResult(ErrorCode.ERROR_ACCOUNT_NUMBERS_IS_NOT_USED);
			}
			SysUser sysUser = sysUserMapper.queryGovUser(rel.getUsername());
			if (sysUser != null) {
				String password = rel.getPassword();
				BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
				boolean matches = bCryptPasswordEncoder.matches(password, user.getPassword());
				if (matches) {
					SysUserOpenIdRel rel2 = sysUserOpenIdRelMapper.selectByUsername(rel.getUsername());
					if (rel2 == null) {
						rel.setCreateDate(new Date());
						rel.setState(1);
						int i = sysUserOpenIdRelMapper.insertSelective(rel);
						if (i > 0) {
							return result;
						}
						return new ApiResult(ErrorCode.ERR_SYS_INTERNAL_ERROR);
					}
					return new ApiResult(ErrorCode.ERR_SYS_LOGIN_REGISTER);
				}
				return new ApiResult(ErrorCode.ERR_USER_WRONG_PWD);
			}
			return new ApiResult(ErrorCode.ERROR_GOVUSER_NOT_USE);
		}
		return new ApiResult(ErrorCode.ERR_USER_NOT_EXIST);
	}

	@Override
	public ApiResult teacherList(String schoolId, Integer page, Integer sizes) {
		ApiResult result = new ApiResult(ErrorCode.SUCCESS);
		PageHelper.startPage(page, sizes);
		List<SysUser> list = sysUserMapper.selectBySchoolId(schoolId);
		if (list != null && list.size() > 0) {
			for (SysUser sysUser : list) {
				// 清除密码
				sysUser.setPassword("");
				if (sysUser.getPower().equals("ROLE_TEACHER")) {
					// 添加班级名称
					if (!StringUtil.isEmpty(sysUser.getExt2())) {
						Clazz clazz = clazzMapper.selectByPrimaryKey(Integer.valueOf(sysUser.getExt2()));
						sysUser.setClazzName(clazz.getSchoolName());
					}
				}
			}
			result.setData(list);
			return result;
		}
		return new ApiResult(ErrorCode.ERR_SYS_INTERNAL_ERROR);
	}

	@Override
	public ApiResult teacherListCount(String schoolId) {
		int i = sysUserMapper.teacherListCount(schoolId);
		ApiResult result = new ApiResult(ErrorCode.SUCCESS);
		result.setData(i);
		return result;
	}

	@Override
	public ApiResult clazzTeacherList(String clazzId) {
		ApiResult result = new ApiResult(ErrorCode.SUCCESS);
		List<SysUser> list = sysUserMapper.selectByClazzId(clazzId);
		if (list != null && list.size() > 0) {
			for (SysUser sysUser : list) {
				// 清除密码
				sysUser.setPassword("");
				if (sysUser.getPower().equals("ROLE_TEACHER")) {
					// 添加班级名称
					if (!StringUtil.isEmpty(sysUser.getExt2())) {
						Clazz clazz = clazzMapper.selectByPrimaryKey(Integer.valueOf(sysUser.getExt2()));
						sysUser.setClazzName(clazz.getSchoolName());
					}
				}
			}
			result.setData(list);
			return result;
		}
		return new ApiResult(ErrorCode.ERR_SYS_INTERNAL_ERROR);
	}

	@Override
	public ApiResult loginOut(String openId) {
		int i = sysUserOpenIdRelMapper.deleteByOpenId(openId);
		if (i > 0) {
			return new ApiResult(ErrorCode.SUCCESS);
		}
		return new ApiResult(ErrorCode.ERR_SYS_INTERNAL_ERROR);
	}

}
