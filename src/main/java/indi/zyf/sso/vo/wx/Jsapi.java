package indi.zyf.sso.vo.wx;

public class Jsapi {
	public String errcode;
	public String errmsg;
	public String ticket;
	public int expires_in;
	public String getErrcode() {
		return errcode;
	}
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public int getExpiresIn() {
		return expires_in;
	}
	public void setExpiresIn(int expiresIn) {
		this.expires_in = expiresIn;
	}
	
}
