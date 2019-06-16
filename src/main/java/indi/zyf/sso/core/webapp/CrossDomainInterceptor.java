package indi.zyf.sso.core.webapp;

import org.jboss.logging.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CrossDomainInterceptor extends HandlerInterceptorAdapter {
	private static Logger logger = Logger.getLogger(CrossDomainInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		String domain = request.getHeader("ORIGIN");
		logger.debug("跨域:"+domain);
		logger.debug("Authorization:"+request.getHeader("Authorization"));
		response.setHeader("Access-Control-Allow-Origin", domain);
		response.setHeader("Access-Control-Allow-Methods","*");
		response.setHeader("Access-Control-Allow-Credentials", "true");		
		response.setHeader("Access-Control-Allow-Headers", "Content-Type,Authorization,Access-Control-Expose-Headers");
		//response.setHeader("Access-Control-Request-Headers", "Content-Type");	
		response.setHeader("Access-Control-Expose-Headers", "Authorization");	
		return true;
	}
}
