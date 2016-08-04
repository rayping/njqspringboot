package org.njqspringboot.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.njqspringboot.model.member.MemberInfo;
import org.njqspringboot.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return memberList;
	}
}
