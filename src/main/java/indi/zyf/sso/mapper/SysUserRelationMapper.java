package indi.zyf.sso.mapper;

import indi.zyf.sso.model.SysUserRelation;

public interface SysUserRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUserRelation record);

    int insertSelective(SysUserRelation record);

    SysUserRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUserRelation record);

    int updateByPrimaryKey(SysUserRelation record);
}