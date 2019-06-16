package indi.zyf.sso.controller;

import indi.zyf.sso.core.ErrorCode;
import indi.zyf.sso.model.SysUserOpenIdRel;
import indi.zyf.sso.service.ISysUserService;
import indi.zyf.sso.util.SMSUtil;
import indi.zyf.sso.vo.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(value = "/sysuser", description = "系统用户")
@RestController
@RequestMapping("/sysuser")
public class SysUserController {

	@Autowired
	private ISysUserService sysuserService;

	@Autowired
	private SMSUtil smsUtil;

	// 解除绑定用户
	@ApiOperation(value = "解除绑定用户", notes = "（校园版）")
	@GetMapping("/loginOut/{openId}")
	public ApiResult loginOut(@PathVariable String openId) {
		return sysuserService.loginOut(openId);
	}

	// 绑定用户
	@ApiOperation(value = "绑定用户", notes = "（校园版）")
	@PostMapping("/loginAndRegister")
	public ApiResult loginandregister(@RequestBody SysUserOpenIdRel rel, HttpServletRequest request) {
		return sysuserService.loginandregister(rel,request);
	}

	/**
	* @Function: SysUserController
	* @Description: 通过邮箱注册
	*
	* @param:
	* @return:
	* @throws: 异常描述
	*
	* @version: v1.0.0
	* @author: zyf
	* @date: 2019/1/18 21:57
	*
	* Modification History:
	* Date                  Author        Version         Description
	*-----------------------------------------------------------------*
	* 2019/1/18 21:57      zyf            v1.0.0           修改原因
	*/
	@ApiOperation(value = "通过邮箱注册", notes = "")
	@PostMapping("/registByEmail")
	public ApiResult registByEmail(){
		ApiResult result = new ApiResult(ErrorCode.SUCCESS);
		result.setErrMsg("注册成功");
		return result;
	}


	@ApiOperation(value = "通过手机号注册", notes = "")
	@PostMapping("/registBySms")
	public ApiResult registBySms(){
		ApiResult result = new ApiResult(ErrorCode.SUCCESS);
		result.setErrMsg("注册成功");
		return result;
	}

}
