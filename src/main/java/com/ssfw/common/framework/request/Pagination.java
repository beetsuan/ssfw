package com.ssfw.common.framework.request;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssfw.common.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 分页排序对象
 *
 * 内部控制了最大记录不超过10000条
 *
 * @author ssfw
 */
public class Pagination<T> implements Serializable{

	private final Logger log = LoggerFactory.getLogger(Pagination.class);

	private static final long serialVersionUID = -1287711769714680737L;

	public final static String ASC = "asc";
	public final static String DESC = "desc";

	private final Page<T> page = new Page<>();


	/**
	 * 默认分页大小
	 */
	public final static int DEFAULT_PAGE_SIZE = 10;

	/** 分页大小 */
	@NotNull
	protected int pageSize;

	/** 当前页码 */
	@NotNull
	protected int pageIndex;

	/**查询起始行*/
	protected int startIndex;

	/**查询结束行*/
	protected int endIndex;
	/**查询总行数*/
	protected long total;

	/**查询总分页数*/
	protected long totalPage;

	/**查询字段排序 getSortFieldUnderlineCase 返回下划线命名方式的字符串*/
	protected String sortField;
	protected String sortOrder;

	/**是否忽略最大分页限制*/
	protected boolean ignoreMaxLimit;

	private void initRowIndex(){

		startIndex = pageIndex*pageSize;
		endIndex = startIndex+pageSize;

		page.setSize(getPageSize());
		page.setCurrent(getPageIndex());
		if (null != sortOrder){
			page.addOrder(ASC.equals(sortOrder)? OrderItem.asc(sortOrder) : OrderItem.desc(sortOrder));
		}
	}

	private Pagination() {
	}


	public Pagination(HttpServletRequest request){

		pageIndex = 0;
		pageSize = DEFAULT_PAGE_SIZE;
		setPagination(request);
	}

	public Pagination<T> setPagination(HttpServletRequest request){

		try {
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			//分页
			String page = request.getParameter("page");
			String limit = request.getParameter("limit");
			if(null!=limit){
				//layui
                if (StringUtil.isNotNull(page)){
                    pageIndex = Integer.parseInt(page)-1;
                }
                if (StringUtil.isNotNull(limit)){
                    pageSize = Integer.parseInt(limit);
                }
			}
			else {
				//miniui and bootstrap-table
                page = request.getParameter("pageIndex");
                limit = request.getParameter("pageSize");
			}

			if (StringUtil.isNotNull(page)){
				pageIndex = Integer.parseInt(page);
			}
			if (StringUtil.isNotNull(limit)){
				pageSize = Integer.parseInt(limit);
			}

			if (StringUtil.isNull(sortField)){
				sortField = request.getParameter("pagination.sortField");
			}
			if (StringUtil.isNull(sortOrder)){
				sortOrder = request.getParameter("pagination.sortOrder");
			}

			if(StringUtils.isNotBlank(sortField)){
				setSortField(sortField);
			}
			if(StringUtils.isNotBlank(sortOrder)){
				setSortOrder(sortOrder);
			}
			initRowIndex();
		} catch (NumberFormatException e) {
			log.info("分页解析出错{}",e.getMessage());
		}
		return this;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setPageSize(int pageSize,boolean ignoreMaxLimit) {
		this.pageSize = pageSize;
		this.ignoreMaxLimit = ignoreMaxLimit;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getStartIndex() {
		if (0 != pageIndex){
			initRowIndex();
		}
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getEndIndex() {
		if (0 == endIndex){
			initRowIndex();
		}
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public String getSortField() {
		return StringUtils.isBlank(sortField)? null : sortField ;
	}

	public void setSortField(String sortField) {
		//避免SQL注入
		if (StringUtil.hasSqlInjection(sortField)){
			return;
		}
		this.sortField = sortField;
	}

	public String getSortOrder() {
		return StringUtils.isBlank(sortOrder)? null : sortOrder ;
	}

	public void setSortOrder(String sortOrder) {
		//避免SQL注入
		if (StringUtils.isNotEmpty(sortOrder) && !ASC.equals(sortOrder.toLowerCase()) && !DESC.equals(sortOrder.toLowerCase())){
			return;
		}
		this.sortOrder = sortOrder;
	}

	/**
	 * 设置排序信息
	 * @param sortField 排序字段 必填
	 * @param sortOrder 排序类型，非必填 默认asc
	 */
	public void setSort(@NotNull String sortField,String sortOrder){

		this.setSortField(sortField);
		this.setSortOrder(null == sortOrder?ASC:sortOrder);
	}


	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = (int)total;
	}

	public long getTotalPage() {
		if (0 == totalPage){
			totalPage = (int) Math.ceil((total));
		}
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}

	public Page<T> getPage() {
		return page;
	}
}
