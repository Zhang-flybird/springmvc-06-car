package com.etoak.commons;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etoak.exception.ParamException;
import com.etoak.vo.ResultVo;

import lombok.extern.slf4j.Slf4j;

/**全局异常处理
 * 如果controller方法出现异常 就会被@ControllerAdvice注解拦截到
 *  拦截器拦截到使用@ExceptionnHandler注解方法处理
 * */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ParamException.class)
	@ResponseBody
	public ResultVo handleParamException(ParamException e) {
		log.error(e.getMessage(),e);
		return ResultVo.failed(e.getMessage());
	}

}
