package com.blog.app.apis.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{

	String resourceName;
	String fieldName;
	Integer fieldValue;
	String username;
	
	
	public ResourceNotFoundException(String resourceName, String feildName, Integer fieldValue ){
		super(String.format("%s not found with %s : %s",resourceName,feildName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = feildName;
		this.fieldValue = fieldValue;
	}

	public ResourceNotFoundException(String resourceName,
									 String fieldName,
									 String username){
		super(String.format("%s not found with %s : %s",resourceName,fieldName,username));
		this.resourceName=resourceName;
		this.fieldName=fieldName;
		this.username=username;
	}
	
}
