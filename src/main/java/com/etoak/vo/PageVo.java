package com.etoak.vo;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVo<T> {
	//页码
	private int pageNum;
	//每页显示的记录数
	private int PageSize;
	//数据
	private List<T> rows;
	//数据总量
	private long total;
	//总页数
	private int pageCount;
}
