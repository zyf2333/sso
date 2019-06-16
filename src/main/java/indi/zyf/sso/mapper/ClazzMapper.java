package indi.zyf.sso.mapper;

import indi.zyf.sso.model.Clazz;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClazzMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Clazz record);

	int insertSelective(Clazz record);

	Clazz selectByPrimaryKey(Integer id);

	List<Clazz> selectAll(@Param("sid") Integer sid, @Param("name") String name);

	int selectAllCount(@Param("sId") Integer sid, @Param("name") String name, @Param("endTime") String endTime);
	
	int selectAllClazzCount(@Param("sId") Integer sid, @Param("name") String name);
	
	List<Clazz> all(@Param("endTime") String endTime);
	
	List<Clazz> resportAllAsClazz(@Param("clazzId") Integer clazzId, @Param("endTime") String endTime);

	List<Clazz> resportAllAsSchool(@Param("sId") Integer schoolId, @Param("endTime") String endTime);

	List<Clazz> resportAllBySchool(@Param("sId") Integer schoolId);

	List<Integer> getClazzIdBySId(Integer schoolId);

	int updateByPrimaryKeySelective(Clazz record);

	int chageClazzLeader(String leader);

	int cleanClazzLeader(String leader);

	int updateByPrimaryKey(Clazz record);

	List<Clazz> selectLikeByName(String name);

	List<Clazz> selectLikeByNameSchool(@Param("name") String name, @Param("id") Integer id);

	int deleteClazz(@Param("cId") Integer cId);
}