package indi.zyf.sso.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class AuthenticationUtil {

	/*
	 * 获取jwt中的用户信息
	 * {exp=1533000103, user_name=pengyongqiang, authorities=[ROLE_PRINCIPAL], jti=207a7942-fe7e-4df8-9290-0a67f24885af, client_id=edu, scope=[all]}
	 */
	public static Claims getUser(HttpServletRequest request) throws Exception{
		String header = request.getHeader("Authorization");
		return getUser(header);
	}

	public static Claims getUser(String header) throws Exception{
		String token = StringUtils.substringAfter(header, "bearer ");
		Claims claims = Jwts.parser()////存时默认用utf-8取时需要指定
				.setSigningKey("edu".getBytes("UTF-8"))
				.parseClaimsJws(token).getBody();
		return claims ;
	}
}
