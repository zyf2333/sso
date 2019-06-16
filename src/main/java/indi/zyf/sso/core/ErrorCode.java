package indi.zyf.sso.core;

/**
 * 返回参数
 * @author LCJA
 *
 */
public enum ErrorCode {
	SUCCESS("0000", ""),
	SUCCESS_LATER("0001", "系统刷新中，稍后尝试"),
	ERR_SYS_USER_EXIST("0002","用户已存在"),
	ERR_SYS_INTERNAL_ERROR("1000", "系统内部错误"),
	ERR_SYS_DATA_REPEAT_ERROR("1001", "数据重复"),
	ERR_SYS_UPDATE_DATA_ERROR("1002", "数据更新失败"),
	ERR_SYS_FILE_EXIST("1003", "文件已存在"),
	ERR_SYS_FILE_ERROR("1004", "文件保存失败"),
	ERR_SYS_FILE_FORMAT_ERROR("1005", "文件格式不正确"),
	ERR_SYS_REQUEST_MISSING_PARAMETER("1007", "缺少输入参数"),
	ERR_SYS_WRONG_PARAMETER("1008", "参数错误"),
	ERR_SYS_WRONG_REPORT("1009", "报表异常"),
	ERR_SYS_WECAT_ERROR("1010", "微信调用失败"),
	ERR_SYS_PAY_ERROR("1011", "微信支付失败"),
	ERR_REPORT_IS_NULL("1012", "Report为空"),
	ERR_DATA_NOT_EXIST("1013", "数据不存在"),
	ERR_DATA_BIRTHDAY("1014", "儿童生日和评估日期冲突"),
	ERR_USER_NOT_EXIST("2001", "用户不存在"),
	ERR_USER_WRONG_PWD("2002", "密码不正确"),
	ERR_SYS_AUTHENTICTION("2003", "认证失败"),
	ERR_SYS_NOT_POWER("2004", "权限不足"),
	ERROR_USER_NAME_PASSWORD("2005","用户名或密码不正确"),
	ERR_USER_DIS_ABLE("2006", "账号被禁用"),
	ERR_USER_NO_LOGIN("2007","该账号还未绑定"),
	ERROR_USERNAME_PASSWORD_IS_NULL("2009","用户名或密码为空"),
	ERROR_CHILD_IS_NOT_EXIST("2010","儿童编号不存在"),
	ERROR_NOT_AGAIN_BIND("2011","儿童重复绑定"),
	ERROR_CHILD_NOT_EXIST("2012","儿童不存在"),
	ERROR_ACCOUNT_NUMBERS_IS_NOT_USED("2013","账号已被停用"),
	ERROR_GOVUSER_NOT_USE("2014","政府平台用户禁止使用小程序"),
	ERR_SYS_LOGIN_REGISTER("2015", "账号已经绑定过openid"),
	ERR_SYS_WECAT_CODE_ERROR("2016", "微信code获取openid失败"),
	ERR_VERLIFICATION("2017","验证码异常"),
	ERR_VERLIFICATION_EXPIRE("2018", "验证码失效"),
	ERR_VERLIFICATION_ERROR("2019", "验证码错误"),
	ERR_VERLIFICATION_NOT_EXIST("2020","验证码不存在"),
	ERR_TOKEN_Expired("2021","token失效");

	private String errCode;
	private String errMsg;
	
	private ErrorCode(String errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	
}
