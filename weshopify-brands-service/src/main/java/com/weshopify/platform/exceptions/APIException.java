package com.weshopify.platform.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
//@Builder
@AllArgsConstructor
public class APIException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String errorMsg;
	private int errorCode;
	
	/*
	 * public APIException(String errorMsg,int errorCode) { this.errorMsg =
	 * errorMsg; this.errorCode = errorCode; }
	 */
	
	

}
