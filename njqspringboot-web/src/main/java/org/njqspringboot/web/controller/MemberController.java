package org.njqspringboot.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.njqspringboot.model.member.MemberBean;
import org.njqspringboot.model.member.MemberInfo;
import org.njqspringboot.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;

@RestController
@RequestMapping("/member")
public class MemberController {
	private static Logger logger = Logger.getLogger(MemberController.class);
	
	@Autowired
	private MemberService memberService;

	@RequestMapping(value="/getMemberInfo",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public List<MemberInfo> getMemberInfo() {
		List<MemberInfo> memberList = new ArrayList<MemberInfo>();
		try {
			memberList = memberService.getMemberList();
			System.out.println("memberList.size====:"+memberList.size());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return memberList;
	}
	
	
	@RequestMapping(value="/getPageMemberList",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<MemberBean> getPageMemberList(ModelMap modelMap, HttpServletRequest request) {
		List<MemberBean> memberList = new ArrayList<MemberBean>();
		try {
			String pageNum = request.getParameter("pageNum");
			String pageSize = request.getParameter("pageSize");
			String orderBy = request.getParameter("orderBy");
			if(StringUtil.isEmpty(pageNum)){
				pageNum = "1";
			}
			
			if(StringUtil.isEmpty(pageSize)){
				pageSize ="10";
			}
			
			if(StringUtil.isEmpty(orderBy)){
				orderBy ="update_time desc  ,id desc";
			}
			
			PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize), orderBy);
			memberList = memberService.getPageMemberList();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return new PageInfo<MemberBean>(memberList);
	}
}
