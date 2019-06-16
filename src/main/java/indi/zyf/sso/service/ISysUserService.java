package indi.zyf.sso.service;


import indi.zyf.sso.model.SysUser;
import indi.zyf.sso.model.SysUserOpenIdRel;
import indi.zyf.sso.vo.ApiResult;

import javax.servlet.http.HttpServletRequest;

public interface ISysUserService {

	public ApiResult login(SysUser user);

	public ApiResult username(String username);

	public ApiResult register(SysUser user);

	public ApiResult update(SysUser user);

	public ApiResult query();

	public ApiResult password(Integer id);

	public ApiResult levelQuery(Integer id, Integer nums, Integer pages, SysUser sysUser);

	public ApiResult levelCount(Integer id, SysUser sysUser);

	public ApiResult del(Integer id);

	public ApiResult count(SysUser user);

	public ApiResult pages(SysUser user, Integer nums, Integer sizes);

	public ApiResult loginandregister(SysUserOpenIdRel rel, HttpServletRequest request);

	public ApiResult teacherList(String schoolId, Integer page, Integer sizes);

	public ApiResult teacherListCount(String schoolId);

	public ApiResult clazzTeacherList(String clazzId);

	public ApiResult loginOut(String openId);

}
