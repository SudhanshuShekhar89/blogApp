package com.blog.app.apis.exceptions;

import com.blog.app.apis.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		String  message=ex.getMessage();
		ApiResponse apiResponse=new ApiResponse(message,false);
		return new ResponseEntity<ApiResponse> (apiResponse,HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String ,String>>handelMethodArgsNotValidException(MethodArgumentNotValidException ex){
     Map<String  ,String> errorResponse=new HashMap<>();
     ex.getBindingResult().getAllErrors().forEach((error)->{
	String  feildNmae=((FieldError)error).getField();
	String message= error.getDefaultMessage();
	errorResponse.put(feildNmae,message);
});
		return  new ResponseEntity<Map<String,String>>(errorResponse,HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> handelApiException(ApiException ex){
		String  message=ex.getMessage();
		ApiResponse apiResponse=new ApiResponse(message,true);
		return new ResponseEntity<ApiResponse> (apiResponse,HttpStatus.BAD_REQUEST);
	}

}
