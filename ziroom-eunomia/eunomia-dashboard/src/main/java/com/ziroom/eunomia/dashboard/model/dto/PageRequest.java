package com.ziroom.eunomia.dashboard.model.dto;

import javax.validation.constraints.Min;

/**
 * <p>分页参数基类</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class PageRequest {

	 /**
     * 当前页
     */
    @Min(value = 1, message = "{commons.page.min}")
    private int page = 1;

    /**
     * 页大小
     */
    @Min(value = 1, message = "{commons.limit.min}")
    private int limit = 50;

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * @param limit the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}
}
