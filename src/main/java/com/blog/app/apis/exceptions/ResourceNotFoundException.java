package com.blog.app.apis.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{

	String resourceName;
	String feildName;
	Integer fieldValue;
	
	
	public ResourceNotFoundException(String resourceName, String feildName, Integer fieldValue ){
		super(String.format("%s not found with %s : %s",resourceName,feildName,fieldValue));
		this.resourceName = resourceName;
		this.feildName = feildName;
		this.fieldValue = fieldValue;
	}


	
}
