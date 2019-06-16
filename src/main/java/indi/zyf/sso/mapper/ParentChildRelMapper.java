package indi.zyf.sso.mapper;

import indi.zyf.sso.model.ParentChildRel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ParentChildRelMapper {
    int insert(ParentChildRel record);

    int insertSelective(ParentChildRel record);
    
    List<ParentChildRel> selectByOpenid(@Param("openId") String openId);

	int update(@Param("openId") String openId, @Param("childId") String childId);

	ParentChildRel selectByOpenIdAndChildId(@Param("openId") String openId, @Param("childId") String childId);

}