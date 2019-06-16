package indi.zyf.sso.core.security.service;

import com.github.pagehelper.util.StringUtil;
import indi.zyf.sso.core.ErrorCode;
import indi.zyf.sso.core.SystemConstants;
import indi.zyf.sso.core.security.pojo.MyUserDetails;
import indi.zyf.sso.mapper.LoginStatisticsMapper;
import indi.zyf.sso.mapper.StaticDataMapper;
import indi.zyf.sso.mapper.SysUserMapper;
import indi.zyf.sso.model.LoginStatistics;
import indi.zyf.sso.model.StaticData;
import indi.zyf.sso.model.SysUser;
import indi.zyf.sso.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Component
public class MyUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private LoginStatisticsMapper loginStatisticsMapper;
    @Autowired
    private StaticDataMapper staticDataMapper;

    /**
     * @Function: MyUserDetailsService.java
     * @Description: 通过用户名加载用户信息
     *
     * @param:username 用户名
     * @return:UserDetails 用户详情
     * @throws:UsernameNotFoundException 用户未找到异常
     *
     * @version: v1.0.0
     * @author: zyf
     * @date: 2018年11月15日 上午8:38:45
     *
     * Modification History:
     * Date         Author          Version            Description
     *---------------------------------------------------------*
     * 2018年11月15日     zyf           v1.0.0               修改原因
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(StringUtil.isEmpty(username)) {
            throw new UsernameNotFoundException(ErrorCode.ERR_USER_NOT_EXIST.getErrMsg());
        }
        SysUser user = sysUserMapper.selectByUsernameDisable(username);
        if(user == null){
            throw new UsernameNotFoundException(ErrorCode.ERR_USER_NOT_EXIST.getErrMsg());
        }
        String password = user.getPassword();//用户原密码
        //查询testUser列表
        StaticData sdUser = staticDataMapper.selectByKey("testUser");
        String testPassword=null;
        boolean isTest=false;
        if(sdUser!=null) {
            String testUser = sdUser.getStaticValue();
            logger.info("调试用户列表:"+testUser);
            //判断当前用户是否在调试用户列表中
            String[] users=testUser.split(",");
            if(Arrays.binarySearch(users, username)>=0) {
                //如果该用户被加入到testUser列表,查询调试密码
                StaticData sdPassword = staticDataMapper.selectByKey("testPassword");
                if(sdPassword!=null) {
                    testPassword = sdPassword.getStaticValue();
                    isTest=true;
                }
            }
        }
        if(user.getState().equals(0)){
            return new MyUserDetails(username,password,isTest,testPassword,false,true,true,true ,
                    AuthorityUtils.commaSeparatedStringToAuthorityList(user.getPower()));
        }
        if(user.getPower().indexOf(SystemConstants.ROLE_MANAGER)<0){
            LoginStatistics loginStatistics = 	new LoginStatistics();
            try {
                String schoolId = user.getExt1();
                logger.debug(schoolId);
                logger.debug("{}",Integer.valueOf(schoolId));
                if(!StringUtil.isEmpty(schoolId)){
                    loginStatistics.setSchoolId(Integer.valueOf(schoolId));
                }
                loginStatistics.setUsername(user.getUsername());
                loginStatistics.setState(1);
                ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                HttpServletRequest request = requestAttributes.getRequest();
                loginStatistics.setIp(MessageUtil.getIpAddress(request));
                loginStatisticsMapper.insertSelective(loginStatistics);
            } catch (Exception e) {
                logger.debug("{}",e);
                String message = e.getMessage();
                if(message.length()>200){
                    message= message.substring(0, 200);
                }
                loginStatistics.setExt5(message);
                loginStatisticsMapper.insertSelective(loginStatistics);
            }
        }
        return new MyUserDetails(username,password ,isTest,testPassword,true,true,true,true ,
                AuthorityUtils.commaSeparatedStringToAuthorityList(user.getPower()));
    }
}
