package com.ssfw.common.framework.check;


/**
 * 业务验证对象接口
 * 
 * @author a
 * @date 2016-11-7下午02:51:18
 */
public interface ValidStatus<E> {
	
	
	/**
	 * 增加 业务验证
	 * @author a
	 * @date 2013-6-29下午3:47:10
	 * @param entity 实体
	 * @return this
	 */
	ValidStatus<E> addable(E entity) ;
	
	
	/**
	 * 删除 业务验证
	 * @author a
	 * @date 2013-6-29下午3:47:36
	 * @param entity 实体
	 * @return this
	 */
	ValidStatus<E> deletable(E entity) ;
	
	
	/**
	 * 修改业务验证
	 * 
	 * @author a
	 * @date 2013-6-29下午3:47:45
	 * @param entity 实体
	 * @return this
	 */
	ValidStatus<E> updatable(E entity);


	/**
	 * 判断是否有错误;
	 *
	 * @return true,false;
	 */
	boolean hasError();
	/**
	 * 验证不通过的话，抛错方式中断程序
	 */
	void throwIfError();
}
