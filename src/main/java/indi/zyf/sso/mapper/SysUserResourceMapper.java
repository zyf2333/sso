package indi.zyf.sso.mapper;

import indi.zyf.sso.model.SysUserResource;

public interface SysUserResourceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUserResource record);

    int insertSelective(SysUserResource record);

    SysUserResource selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUserResource record);

    int updateByPrimaryKey(SysUserResource record);
}