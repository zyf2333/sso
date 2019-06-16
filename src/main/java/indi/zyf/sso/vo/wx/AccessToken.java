package indi.zyf.sso.vo.wx;

public class AccessToken {
	// 获取到的凭证  
    private String access_token;
    // 凭证有效时间，单位：s 
    private int expires_in;
	public String getAccessToken() {
		return access_token;
	}
	public void setAccessToken(String accessToken) {
		this.access_token = accessToken;
	}
	public int getExpiresIn() {
		return expires_in;
	}
	public void setExpiresIn(int expiresIn) {
		this.expires_in = expiresIn;
	}
    
}
