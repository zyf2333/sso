package indi.zyf.sso.mapper;

import indi.zyf.sso.model.SysUserOpenIdRel;
import org.apache.ibatis.annotations.Param;

public interface SysUserOpenIdRelMapper {
    int insert(SysUserOpenIdRel record);

    int insertSelective(SysUserOpenIdRel record);

	SysUserOpenIdRel selectByOpenId(String openId);
	
	SysUserOpenIdRel selectByUsername(String username);
	
	int deleteByOpenId(@Param("openId") String openId);

	int deleteByUsername(@Param("username") String username);
}