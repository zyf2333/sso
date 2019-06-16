package indi.zyf.sso.mapper;


import indi.zyf.sso.model.StaticData;

public interface StaticDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StaticData record);

    int insertSelective(StaticData record);

    StaticData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StaticData record);

    int updateByPrimaryKey(StaticData record);
    
    StaticData selectByKey(String key);
}