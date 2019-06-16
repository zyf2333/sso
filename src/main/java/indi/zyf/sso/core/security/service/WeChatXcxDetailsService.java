package indi.zyf.sso.core.security.service;

import indi.zyf.sso.core.ErrorCode;
import indi.zyf.sso.core.security.pojo.MyUserDetails;
import indi.zyf.sso.core.security.pojo.WechatUserDetails;
import indi.zyf.sso.mapper.*;
import indi.zyf.sso.model.*;
import indi.zyf.sso.util.MessageUtil;
import indi.zyf.sso.util.StringUtil;
import indi.zyf.sso.util.WeixinUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WeChatXcxDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);
    @Autowired
    private SysUserMapper usermapper;
    @Autowired
    private LoginStatisticsMapper loginStatisticsMapper;
    @Autowired
    private StaticDataMapper staticDataMapper;

    @Autowired
    private SysUserOpenIdRelMapper sysUserOpenIdRelMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private ParentChildRelMapper parentChildRel;

    @Autowired
    private ChildrenMapper childrenMapper;

    @Autowired
    private SchoolMapper schoolMapper;

    @Autowired
    private ClazzMapper clazzMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    /**
     * @Function: WeChatXcxDetailsService
     * @Description: 通过微信code查询用户信息
     * @param:
     * @return:
     * @throws: 异常描述
     * @version: v1.0.0
     * @author: zyf
     * @date: 2019/1/16 14:52
     * <p>
     * Modification History:
     * Date                  Author        Version         Description
     * -----------------------------------------------------------------*
     * 2019/1/16 14:52      zyf            v1.0.0           修改原因
     */
    public UserDetails loadUserByCode(String code, String type) throws UsernameNotFoundException {
        //通过code和type查询openid
        String rtstr = WeixinUtil.getOpenidUseType(code, type);
        if (rtstr == null) {
            throw new RuntimeException(ErrorCode.ERR_SYS_WECAT_ERROR.getErrMsg());
        }
        JSONObject json = JSONObject.fromObject(rtstr);
        String username = " "; //用户名
        boolean enabled = true; //是否启用
        String power = "";//权限
        String openId = "";
        //通过openid查询user
        if (!StringUtil.isEmpty((String) json.get("openid"))) {
            openId = json.get("openid").toString();
            logger.debug("openid=======" + json.get("openid").toString());
            logger.debug("session_key=======" + json.get("session_key").toString());
            if ("parent".equals(type)) {//家长版
                List<ParentChildRel> list = parentChildRel.selectByOpenid(openId);
                if (list.size() > 0) {
                    //幼儿id
                    for (ParentChildRel childRel : list) {
                        Integer childId = Integer.valueOf(childRel.getChildId());
                        Children children = childrenMapper.selectByPrimaryKey(childId);
                        if (children != null) {
                            username += children.getId()+",";//幼儿id
                        }
                        power = "ROLE_PARENT";
                    }
                }
            } else if ("school".equals(type)) {//园所版
                SysUserOpenIdRel rel = sysUserOpenIdRelMapper.selectByOpenId(json.get("openid").toString());
                if (rel != null) {
                    SysUser user = sysUserMapper.selectByUsername(rel.getUsername());
                    if (user == null) {//用户已禁用或删除，删除绑定关系
                        sysUserOpenIdRelMapper.deleteByUsername(rel.getUsername());
                    }
                    username = user.getUsername();
                    power = user.getPower();
                }
            }

        }
        return new WechatUserDetails(username, openId, enabled, AuthorityUtils.commaSeparatedStringToAuthorityList(power));
    }
}
