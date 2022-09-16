package com.ssfw.common.framework.dto;

import com.ssfw.common.exception.BusinessException;

import java.io.Serializable;
import java.util.List;

/**
 * service的返回对象
 * @author a
 */
public class ResultDto<T> implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 4115304246181800146L;
	/**提示信息*/
	private String errMsg;
	/**提示信息CODE*/
	private String errCode;
	/**实体*/
	private T entity;
	/**实体集合*/
	private List<T> entities;
	/**是否需要中断操作*/
	private boolean interrupted = false;

	public ResultDto() {
		super();
	}

	public ResultDto(T entity) {
		super();
		this.setEntity(entity);
	}

	public ResultDto(List<T> entities) {
		super();
		this.setEntities(entities);
	}

	public ResultDto(String errMsg) {
		super();
		this.setErrMsg(errMsg);
		this.setInterrupted(true);
	}

	public ResultDto(String errMsg, String errCode) {
		super();
		this.setErrMsg(errMsg);
		this.setErrCode(errCode);
	}

	public ResultDto(String errMsg, boolean interrupted) {
		super();
		this.setErrMsg(errMsg);
		this.setInterrupted(interrupted);
	}

	public static <T> ResultDto<T> of(T entity) {

		return new ResultDto<>(entity);
	}

	public static <T> ResultDto<T> of(List<T> entities) {

		return new ResultDto<>(entities);
	}

	public static <T> ResultDto<T> of(String errMsg) {

		return new ResultDto<>(errMsg);
	}

	public static <T> ResultDto<T> ofEntity(T entity) {

		ResultDto<T> model = new ResultDto<>();
		model.setEntity(entity);
		return model;
	}

	public static <T> ResultDto<T> empty() {

		return new ResultDto<>();
	}



	public String getErrMsg() {
		return errMsg;
	}
	
	public boolean hasError() {

        return null != errMsg;
    }

	public ResultDto<T> setErrMsg(String errMsg) {
		this.errMsg = errMsg;
		return this;
	}

	public T getEntity() {
		return entity;
	}

	public ResultDto<T> setEntity(T entity) {
		this.entity = entity;
		return this;
	}

	public List<T> getEntities() {
		return entities;
	}

	public ResultDto<T> setEntities(List<T> entities) {
		this.entities = entities;
		return this;
	}

	public boolean isInterrupted() {
		return interrupted;
	}

	public void setInterrupted(boolean interrupted) {
		this.interrupted = interrupted;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	/**
	 * 抛出错误，中断进程进行执行
	 * 前提 this.hasError()==true ，否则不抛错
	 * 
	 * @date 2016年10月14日 下午5:09:15
	 */
	public ResultDto<T> throwIfError() {
		if (hasError()) {
			throw new BusinessException(getErrMsg());
		}
		return this;
	}

	/**
	 * 抛出错误，中断进程进行执行
	 * 前提 this.hasError()==true ，否则不抛错
	 *
	 * @date 2016年10月14日 下午5:09:15
	 */
	public ResultDto<T> throwIfInterrupted() {
		if (isInterrupted()) {
			throw new BusinessException(getErrMsg());
		}
		return this;
	}
	
	/**
	 * 抛出错误，中断进程进行执行
	 * 
	 * @date 2016年10月14日 下午5:09:15
	 * @param errMsg
	 */
	public void throwError(String errMsg) {
		
			throw new BusinessException(errMsg);
	}
	
	/**
	 * 抛出错误，中断进程进行执行
	 * 
	 * @date 2016年10月14日 下午5:09:15
	 */
	public void throwError(Exception exception) {
		throw new RuntimeException(exception);
	}


}
