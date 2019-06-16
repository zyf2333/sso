package indi.zyf.sso.mapper;


import indi.zyf.sso.model.LoginStatistics;

public interface LoginStatisticsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LoginStatistics record);

    int insertSelective(LoginStatistics record);

    LoginStatistics selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LoginStatistics record);

    int updateByPrimaryKey(LoginStatistics record);
    
    int getSchoolLoginStatistics(Integer sId);
}