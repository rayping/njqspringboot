package org.njqspringboot.service;

import java.util.List;

import org.njqspringboot.model.member.MemberBean;
import org.njqspringboot.model.member.MemberInfo;

public interface MemberService {

	List<MemberInfo> getMemberList();

	List<MemberBean> getPageMemberList();

	MemberInfo getMemberInfoById(Long id);

	void updateMemberInfo(MemberInfo memberInfo);

}
