package org.njqspringboot.dao;

import java.util.List;

import org.njqspringboot.common.MyBatisRepository;
import org.njqspringboot.model.member.MemberBean;
import org.njqspringboot.model.member.MemberInfo;
import org.njqspringboot.support.mybatis.locker.annotation.VersionLocker;

@MyBatisRepository
public interface MemberDao {

	List<MemberInfo> getMemberList();

	List<MemberBean> merberDaogetPageMemberList();

	MemberInfo getMemberInfoById(Long id);

	@VersionLocker(true)
	void updateMemberInfo(MemberInfo memberInfo);

}
