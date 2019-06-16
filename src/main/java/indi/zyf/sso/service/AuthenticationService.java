package indi.zyf.sso.service;

import indi.zyf.sso.vo.ApiResult;

/**
* @Function: AuthenticationService
* @Description: 身份验证服务接口
*
* @param:
* @return:
* @throws: 异常描述
*
* @version: v1.0.0
* @author: zyf
* @date: 2019/2/18 8:50
*
* Modification History:
* Date                  Author        Version         Description
*-----------------------------------------------------------------*
* 2019/2/18 8:50      zyf            v1.0.0           修改原因
*/
public interface AuthenticationService {

    ApiResult check(String token ,String apiUrl);

}
