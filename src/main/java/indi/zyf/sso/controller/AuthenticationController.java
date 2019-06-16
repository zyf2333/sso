package indi.zyf.sso.controller;

import indi.zyf.sso.service.impl.AuthenticationServiceImpl;
import indi.zyf.sso.vo.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
* @Function: AuthenticationController
* @Description: 身份验证
*
* @param:
* @return:
* @throws: 异常描述
*
* @version: v1.0.0
* @author: zyf
* @date: 2019/1/23 15:19
*
* Modification History:
* Date                  Author        Version         Description
*-----------------------------------------------------------------*
* 2019/1/23 15:19      zyf            v1.0.0           修改原因
*/
@Api(value = "身份验证", description = "")
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationServiceImpl authenticationService;

    /**
    * @Function: AuthenticationController
    * @Description: 检查用户权限
    *
    * @param:
    * @return:
    * @throws: 异常描述
    *
    * @version: v1.0.0
    * @author: zyf
    * @date: 2019/1/25 15:51
    *
    * Modification History:
    * Date                  Author        Version         Description
    *-----------------------------------------------------------------*
    * 2019/1/25 15:51      zyf            v1.0.0           修改原因
    */
    @ApiOperation(value = "检查用户权限", notes = "")
    @PostMapping("/check")
    public ApiResult checkCertificate(@RequestParam String token, @RequestParam String url) {
//        //获取token
//        String token = ServletRequestUtils.getStringParameter(request, "token");
//        //获取需要检查权限的url
//        String apiUrl = ServletRequestUtils.getStringParameter(request, "apiUrl");
        ApiResult result =authenticationService.check(token, url);
        return result;
    }

}
