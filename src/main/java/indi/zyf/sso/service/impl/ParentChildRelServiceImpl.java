package indi.zyf.sso.service.impl;

import indi.zyf.sso.core.ErrorCode;
import indi.zyf.sso.mapper.ChildrenMapper;
import indi.zyf.sso.mapper.ParentChildRelMapper;
import indi.zyf.sso.model.Children;
import indi.zyf.sso.model.ParentChildRel;
import indi.zyf.sso.service.IParentChildRelService;
import indi.zyf.sso.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParentChildRelServiceImpl implements IParentChildRelService {
	
	@Autowired
	private ChildrenMapper childrenMapper;
	
	@Autowired
	private ParentChildRelMapper parentChildRelMapper;

	@Override
	public ApiResult bindChild(String openId, List<String> childIds) {
		for (String string : childIds) {
			Children children = childrenMapper.selectByPrimaryKey(Integer.valueOf(string));
			if(children == null){
				return new ApiResult(ErrorCode.ERROR_CHILD_IS_NOT_EXIST);
			}
		}
		for (String string : childIds) {
			ParentChildRel rel2 = parentChildRelMapper.selectByOpenIdAndChildId(openId,string);
			if(rel2==null){
				ParentChildRel rel = new ParentChildRel();
				rel.setChildId(string);
				rel.setOpenId(openId);
				rel.setState(1);
				parentChildRelMapper.insertSelective(rel);
			}else{
				return new ApiResult(ErrorCode.ERROR_NOT_AGAIN_BIND);
			}
		}
		return new ApiResult(ErrorCode.SUCCESS);
	}

	@Override
	public ApiResult unbindChild(String openId, String childId) {
		int i = parentChildRelMapper.update(openId,childId);
		if(i>0){
			return new ApiResult(ErrorCode.SUCCESS);
		}
		return new ApiResult(ErrorCode.ERR_SYS_INTERNAL_ERROR);
	}

}
