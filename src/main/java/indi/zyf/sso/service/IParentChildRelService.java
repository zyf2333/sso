package indi.zyf.sso.service;

import indi.zyf.sso.vo.ApiResult;

import java.util.List;

public interface IParentChildRelService {

	ApiResult bindChild(String openId, List<String> childId);

	ApiResult unbindChild(String openId, String childId);

}
