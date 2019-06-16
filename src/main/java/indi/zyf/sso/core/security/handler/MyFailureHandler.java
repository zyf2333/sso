package indi.zyf.sso.core.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import indi.zyf.sso.core.ErrorCode;
import indi.zyf.sso.core.security.exception.VerificationCodeException;
import indi.zyf.sso.vo.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyFailureHandler implements AuthenticationFailureHandler{

	private static final Logger logger = LoggerFactory.getLogger(MyFailureHandler.class);
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		logger.info("用户登录失败:"+exception.getMessage());
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setContentType("application/json;charset=UTF-8");
		ApiResult apiResult = null;
		if(exception instanceof DisabledException){
			apiResult = new ApiResult(ErrorCode.ERR_USER_DIS_ABLE);
		}else if(exception instanceof UsernameNotFoundException){
			apiResult = new ApiResult(ErrorCode.ERR_USER_NOT_EXIST);
		}else if(exception instanceof BadCredentialsException){
			apiResult = new ApiResult(ErrorCode.ERROR_USER_NAME_PASSWORD);
		} else if (exception instanceof VerificationCodeException) {
			apiResult = new ApiResult(ErrorCode.ERR_VERLIFICATION);
			apiResult.setErrMsg(exception.getMessage());
		} else {
			apiResult = new ApiResult(ErrorCode.ERR_SYS_INTERNAL_ERROR);
		}
		response.getWriter().write(objectMapper.writeValueAsString(apiResult));
		
	}

}
