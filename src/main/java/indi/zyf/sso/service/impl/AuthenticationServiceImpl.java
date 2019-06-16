package indi.zyf.sso.service.impl;

import indi.zyf.sso.core.ErrorCode;
import indi.zyf.sso.service.AuthenticationService;
import indi.zyf.sso.util.AuthenticationUtil;
import indi.zyf.sso.vo.ApiResult;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
* @Function: AuthenticationServiceImpl
* @Description: 身份验证
*
* @param:
* @return:
* @throws: 异常描述
*
* @version: v1.0.0
* @author: zyf
* @date: 2019/2/18 8:52
*
* Modification History:
* Date                  Author        Version         Description
*-----------------------------------------------------------------*
* 2019/2/18 8:52      zyf            v1.0.0           修改原因
*/
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    Logger logger = LoggerFactory.getLogger(getClass());
    /**
    * @Function: AuthenticationServiceImpl
    * @Description: 检查token权限
    *
    * @param:
    * @return:
    * @throws: 异常描述
    *
    * @version: v1.0.0
    * @author: zyf
    * @date: 2019/2/18 8:52
    *
    * Modification History:
    * Date                  Author        Version         Description
    *-----------------------------------------------------------------*
    * 2019/2/18 8:52      zyf            v1.0.0           修改原因
    */
    @Override
    public ApiResult check(String token, String apiUrl) {

        ApiResult result = new ApiResult(ErrorCode.SUCCESS);
        try {
            Claims user = AuthenticationUtil.getUser(token);
            //查询用户名
            String username = (String) user.get("user_name");
            logger.info(username);
            //查询该用户是否有接口url的访问权限

        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            result = new ApiResult(ErrorCode.ERR_TOKEN_Expired);
        }
        return result;
    }
}
