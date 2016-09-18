package org.njqspringboot.web.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.njqspringboot.common.enums.ResponseCodeEnum;
import org.njqspringboot.model.JsonResponse;
import org.njqspringboot.model.PageBean;
import org.njqspringboot.model.PageResultBean;
import org.njqspringboot.model.member.MemberInfo;
import org.njqspringboot.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/memberApi")
@Api(value="会员相关接口api")
public class MemberApiController {
	private static Logger logger = Logger.getLogger(MemberApiController.class);
	
	@Autowired
	private MemberService memberService;
	
	
	@ApiOperation(value="获取会员记录列表", notes="获取会员记录列表")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "pageNo", value = "当前页（从第1页开始）", required = false, dataType = "java.lang.Integer"),
        @ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "java.lang.Integer"),
    })
	@ApiResponses(value = {
	        @ApiResponse(code=200, message="操作成功", response=JsonResponse.class), 
	        @ApiResponse(code=400, message="失败[异常]", response=JsonResponse.class)
	    })
	@RequestMapping(value="/getMemberList",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public JsonResponse<PageResultBean> getMemberInfo() {
		JsonResponse<PageResultBean> jres = null;
		try {
			PageBean pageBean = new PageBean();
			
			List<MemberInfo> memberList = memberService.getMemberList();
			Integer totalCount =100;
			PageResultBean resultData = new PageResultBean(pageBean, totalCount, memberList);
			jres = new JsonResponse<>(ResponseCodeEnum.SUCCESS, resultData);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			jres = new JsonResponse<>(ResponseCodeEnum.FAIL);
		}
		return jres;
	}
	
	@ApiOperation(value="更新会员记录", notes="更新会员记录")
	@RequestMapping(value="/updateMember",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String,Object> updateMember(){
		Map<String,Object> map = new HashMap<String,Object>();
		MemberInfo memberInfo = memberService.getMemberInfoById(Long.valueOf(14245));
		if(null !=memberInfo){
			memberInfo.setRealname("Leiyongping");
			memberInfo.setAge(30);
			memberInfo.setIdCardNo(System.currentTimeMillis()+"");
			memberService.updateMemberInfo(memberInfo);
		}
		
		return map;
	}

}
