package com.weshopify.platform.exceptions;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;




@RestControllerAdvice
@Slf4j
public class BrandsExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(APIException.class)
	public ResponseEntity<Object> handleAPIError(APIException apiException){
		log.error("exception caught is "+apiException.getErrorMsg());
		ApiExptionsPayload userException = ApiExptionsPayload.builder().message(apiException.getErrorMsg())
				.statusCode(apiException.getErrorCode()).timeStamp(new Date()).build();
		
		return ResponseEntity.badRequest().body(userException);
	}

}
