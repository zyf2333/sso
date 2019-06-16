package indi.zyf.sso.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import indi.zyf.sso.core.ErrorCode;

@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class ApiResult {
	private String errCode;
	private String errMsg;
	private Object data;

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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ApiResult() {
		this.errCode = ErrorCode.SUCCESS.getErrCode();
	}

	public ApiResult(ErrorCode errorCode) {
		this.errCode = errorCode.getErrCode();
		this.errMsg = errorCode.getErrMsg();
	}

	public ApiResult(ErrorCode errorCode, String errMsg) {
		this.errCode = errorCode.getErrCode();
		this.errMsg = errMsg;
	}

	public ApiResult(String errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

}
