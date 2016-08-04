package org.njqspringboot.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class PageResultBean {
	/**
	 * 当前页
	 */
	private int pageNo;
	
	/**
	 * 每页条数
	 */
	private int pageSize = 20;
	
	/**
	 * 总条数
	 */
	private int totalCount;
	
	/**
	 * 分页list,适用于简单情况：结果集中只是包含一个list
	 */
	private List list;
	
	
	/**
	 * 分页中的复杂情况，结果集中不止一个list，比如除了一个list,还包括汇总的信息
	 */
	private Map<String, Object> map = new HashMap<>();
	
	public Map<String, Object> getMap() {
		return map;
	}
	
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
	public PageResultBean() {
		super();
	}
	
	public PageResultBean(List list) {
		super();
		this.list = list;
	}
	
	public PageResultBean(List list,Map<String,Object> map) {
		super();
		this.list = list;
		this.map = map;
	}
	
	public PageResultBean(int pageNo, int totalCount) {
		super();
		this.pageNo = pageNo;
		this.totalCount = totalCount;
	}

	public PageResultBean(int pageNo, int totalCount, List list) {
		super();
		this.pageNo = pageNo;
		this.totalCount = totalCount;
		this.list = list;
	}
	
	public PageResultBean(PageBean pageBean, int totalCount, List list) {
		super();
		this.pageNo = pageBean.getPageNo();
		this.pageSize = pageBean.getPageSize();
		this.totalCount = totalCount;
		this.list = list;
	}

	public PageResultBean(int pageNo, int pageSize, int totalCount, List list) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.list = list;
	}
	
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}
	
	/**
	 * 分页中的复杂情况，结果集中不止一个list，比如除了一个list,还包括汇总的信息
	 * 支持链式操作
	 * 
	 * @param key
	 * @param valueObject
	 */
	public Map<String,Object> addToMap(String key, Object valueObject) {
		map.put(key, valueObject);
		return map;
	}
	
	/**
	 * addToMap的便捷方法，key默认为list
	 * @param valueObject
	 */
	public void addListToMap(Object valueObject) {
		map.put("list", valueObject);
	}
	
	/**
	 * addToMap的便捷方法，key默认为extra
	 * @param valueObject
	 */
	public void addExtraToMap(Object valueObject) {
		map.put("extra", valueObject);
	}

}
