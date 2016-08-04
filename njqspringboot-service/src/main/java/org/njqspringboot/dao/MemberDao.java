package org.njqspringboot.dao;

import java.util.List;

import org.njqspringboot.common.MyBatisRepository;
import org.njqspringboot.model.member.MemberInfo;

@MyBatisRepository
public interface MemberDao {

	List<MemberInfo> getMemberList();

}
