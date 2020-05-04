package com.etoak.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultVo {

	private static final Integer SUCCESS_CODE =200;
	private static final String SUCCESS_MSG ="success";
	
	private static final Integer FAILED_CODE =100;
	private static final String FAILED_MSG ="FAILED";
	
	private Integer code;
	private String msg;
	private Object data;
	
	public static ResultVo success(Object data){
		return new ResultVo(SUCCESS_CODE,SUCCESS_MSG,data);
	}
	
	public static ResultVo failed(Object data){
		return new ResultVo(FAILED_CODE,FAILED_MSG,data);
	}
	public static ResultVo failed(){
		return new ResultVo(FAILED_CODE,FAILED_MSG,"");
	}
	
}
