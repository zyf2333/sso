package indi.zyf.sso.mapper;

import java.util.List;

import indi.zyf.sso.model.School;
import org.apache.ibatis.annotations.Param;


public interface SchoolMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(School record);

	int insertSelective(School record);

	School selectByPrimaryKey(Integer id);

	List<School> selectAll(String endTime);
	
	List<School> selectSchoolAll();
	
	List<School> selectAllAsSchool(@Param("schoolId") Integer schoolId, @Param("endTime") String endTime);

	List<School> selectAllByProvinceCityRegion(@Param("province") String province, @Param("city") String city,
                                               @Param("region") String region);

	int updateByPrimaryKeySelective(School record);

	int updateByPrimaryKey(School record);

	// 地区
	List<String> selectRegion();

	List<String> selectRegionUseCity(String city);

	// 城市
	List<String> selectCity();

	List<String> selectCityUseProvince(String province);

	// 省
	List<String> selectprovince();

	List<School> selectUseRegion(String region);

	int selectUseRegionCount(String region);

	List<School> selectUseCity(String city);

	int selectUseCityCount(String region);

	List<School> selectUseProvince(String province);

	int selectUseProvinceCount(String region);

	int count(@Param("province") String province, @Param("city") String city, @Param("region") String region);

	List<School> selectLikeByName(String name);

}