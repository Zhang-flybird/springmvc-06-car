package com.etoak.exception;
  
/*忽略序列化接口警告*/
@SuppressWarnings("serial")
public class ParamException extends RuntimeException{

		public ParamException (String message) {
			super(message);
		}
}
