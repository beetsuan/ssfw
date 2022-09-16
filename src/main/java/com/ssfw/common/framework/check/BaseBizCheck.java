package com.ssfw.common.framework.check;

import com.ssfw.common.exception.BusinessException;
import com.ssfw.common.util.StringUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * 共通的业务验证类;
 * 
 * @author a
 * @date 2016-11-7下午02:50:55
 */
public abstract class BaseBizCheck<E> implements ValidStatus<E> {

	/**验证信息*/
	private final List<Object> infos;

	private final List<Object> codes;

	
	public BaseBizCheck() {
		infos = new ArrayList<>();
		codes = new ArrayList<>();
	}

	@Override
	public ValidStatus<E> addable(E entity) {
		return this;
	}

	@Override
	public ValidStatus<E> deletable(E entity) {
		return this;
	}

	@Override
	public ValidStatus<E> updatable(E entity) {
		return this;
	}

	/**
	 * 判断是否有错误;
	 * 
	 * @return true,false;
	 */
	@Override
	public boolean hasError() {
		return !infos.isEmpty() || !codes.isEmpty();
	}


	/**
	 * 有错误的话，就直接抛出错误
	 */
	@Override
	public void throwIfError(){
		if (hasError()){
			if (codes.isEmpty()){
				BusinessException.throwError(getErrorMsg());
			}else{
				BusinessException.throwErrorWithCode(getErrorMsg(),getErrorCode());
			}
		}
	}

	/**
	 * 是否需要进行验证; (如果业务验证不通过，不再验证下一次记录;如果不存在业务验证不通过的情况，则进行验证);
	 * 
	 * @return true：验证; false: 不需要验证;
	 */
	protected boolean needBizCheck() {
		return !hasError();
	}

	/**
	 * @return 业务验证错误的信息内容
	 */
	public String getErrorMsg() {

		if (infos.isEmpty()){
			return null;
		}
		if (infos.size()==1){
			return infos.get(0)+"";
		}
		StringBuilder msg = new StringBuilder();
		for (Object info : infos) {
			msg.append(" [").append(info).append("]");
		}
		return msg.substring(1);
	}

	/**
	 *
	 * @return 业务验证错误的信息编号
	 */
	public String getErrorCode() {

		StringBuilder builder = new StringBuilder();
		codes.forEach(o -> builder.append(",").append(o));
		if(builder.length()>1){
			return builder.substring(1);
		}
		return builder.toString();
	}

	/**
	 * 新增业务验证错误信息
	 * 
	 * @param error 错误信息
	 */
	protected void addError(Object error) {
		infos.add(error);
	}

	/**
	 * 新增业务验证错误信息
	 *
	 * @param error 错误信息
	 */
	protected void addError(Object... error) {

		StringBuilder sb = new StringBuilder();
		for (Object err : error) {
			sb.append(err);
		}
		infos.add(sb.toString());
	}

	/**
	 * 新增业务验证失败，缺少信息
	 * 拼接 “不能为空”
	 * @param error 错误
	 */
	protected void addRequired(Object error) {
		infos.add(error.toString().concat("不能为空"));
	}

	/**
	 * 新增业务验证错误信息
	 *
	 * @param errorCode
	 */
	protected void addErrorCode(Object errorCode) {
		codes.add(errorCode);
	}


	protected BaseBizCheck required(Object fileValue,Object fieldDescription){

		if (StringUtil.isNull(fileValue)){
			addRequired(fieldDescription);
		}
		return this;
	}



	/**
	 * 校验值是否非法
	 * @param value 值
	 * @param valueName 值名称
	 * @param optionValues 可选值
	 */
	protected <V> boolean valueInOptions(V value,String valueName,V... optionValues) {

		if (null==optionValues){
			return true;
		}

		if (!Arrays.asList(optionValues).contains(value)){
			addError(valueName+"取值范围："+ StringUtils.joinWith(",", optionValues));
			return false;
		}
		return true;
	}
}