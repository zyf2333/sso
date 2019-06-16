package indi.zyf.sso.mapper;

import indi.zyf.sso.model.Children;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChildrenMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Children record);

	int insertSelective(Children record);

	Children selectByPrimaryKey(Integer id);

	List<Children> selectLikeByName(String name);
	
	List<Children> selectLikeByNameSchool(@Param("name") String name, @Param("sid") Integer sid);

	List<Children> selectLikeByNameClazz(@Param("name") String name, @Param("cid") Integer cid);

	int updateByPrimaryKeySelective(Children record);

	int updateByPrimaryKey(Children record);

	List<Children> selectAll(@Param("cid") Integer cid, @Param("name") String name);

	int selectAllCount(@Param("cid") Integer cid, @Param("name") String name);

	List<Children> all(@Param("endTime") String endTime);

	Integer graduationUseClazz(@Param("id") Integer id);

	List<Children> reportallAsClazz(@Param("cId") Integer id, @Param("endTime") String endTime);

	List<Children> reportallAsSchool(@Param("sId") Integer id, @Param("endTime") String endTime);

	List<Children> reportallBySchool(@Param("sId") Integer id);

	List<Children> queryByClazz(Integer clazzId);

	List<Children> selectBySidAndCid(@Param("sid") String schoolId, @Param("cid") String clazzId);

	int childrenListCount(@Param("sid") String schoolId, @Param("cid") String clazzId);

	List<Children> selectByCid(@Param("cid") String cId);

	int queryClazzAllChild(@Param("cid") Integer cid);

	List<Children> selectByClazz(Integer cId);

//	List<Children> selectBySchool(Integer sId);

	/**
	* @Function: ChildrenMapper.java
	* @Description: 根据班级ID查询儿童数量
	*
	* @param:班级ID
	* @return：学校儿童数量
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: liutt
	* @date: 2018年11月5日 上午10:25:03
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2018年11月5日     liutt           v1.0.0               修改原因
	*/
	int selectAllCountByClazz(@Param("cId") Integer id, @Param("endTime") String endTime);

	/**
	* @Function: ChildrenMapper.java
	* @Description: 根据学校ID查询儿童数量
	*
	* @param:学校ID
	* @return：学校儿童数量
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: liutt
	* @date: 2018年11月5日 上午10:25:03
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2018年11月5日     liutt           v1.0.0               修改原因
	*/
	int selectAllCountBySchool(@Param("sId") Integer id, @Param("endTime") String endTime);

	List<Children> selectBySchool(@Param("sId") Integer sId,
                                  @Param("startDate") String startDate, @Param("endTime") String endTime);

}