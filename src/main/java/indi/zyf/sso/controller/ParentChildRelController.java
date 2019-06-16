package indi.zyf.sso.controller;

import indi.zyf.sso.service.IParentChildRelService;
import indi.zyf.sso.vo.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "/parentchildrel", description = "儿童家长关联")
@RestController
@RequestMapping("/parentchildrel")
public class ParentChildRelController {

	@Autowired
	private IParentChildRelService parentChildRelService;

	@ApiOperation(value = "绑定儿童", notes = "（家长版）")
	@PostMapping(value = "/bindChild/{openId}")
	public ApiResult bindChild(@PathVariable String openId, @RequestBody List<String> childIds) {
		return parentChildRelService.bindChild(openId, childIds);
	}
	
	@ApiOperation(value = "取消儿童绑定", notes = "（家长版）")
	@PostMapping(value = "/unbindChild/{openId}/{childId}")
	public ApiResult unbindChild(@PathVariable String openId, @PathVariable String childId) {
		return parentChildRelService.unbindChild(openId, childId);
	}

}
