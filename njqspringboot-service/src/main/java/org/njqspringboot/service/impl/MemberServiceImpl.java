package org.njqspringboot.service.impl;

import java.util.List;

import org.njqspringboot.dao.MemberDao;
import org.njqspringboot.model.member.MemberBean;
import org.njqspringboot.model.member.MemberInfo;
import org.njqspringboot.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{
	@Autowired
	private MemberDao merberDao;

	@Override
	public List<MemberInfo> getMemberList() {
		return merberDao.getMemberList();
	}

	@Override
	public List<MemberBean> getPageMemberList() {
		return merberDaogetPageMemberList();
	}

	private List<MemberBean> merberDaogetPageMemberList() {
		return merberDao.merberDaogetPageMemberList();
	}

	@Override
	public MemberInfo getMemberInfoById(Long id) {
		return merberDao.getMemberInfoById(id);
	}

	@Override
	public void updateMemberInfo(MemberInfo memberInfo) {
		merberDao.updateMemberInfo(memberInfo);
	}
	
}
