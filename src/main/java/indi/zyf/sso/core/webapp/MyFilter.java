package indi.zyf.sso.core.webapp;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("myFilter")
public class MyFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) srequest;
		HttpServletResponse response = (HttpServletResponse) sresponse;
		String domain = request.getHeader("ORIGIN");
		response.setHeader("Access-Control-Allow-Origin", domain);
		response.setHeader("Access-Control-Allow-Methods","*");
		response.setHeader("Access-Control-Allow-Credentials", "true");		
		response.setHeader("Access-Control-Allow-Headers", "Content-Type,Authorization,Access-Control-Expose-Headers");
		//response.setHeader("Access-Control-Request-Headers", "Content-Type");	
		response.setHeader("Access-Control-Expose-Headers", "Authorization");	
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
