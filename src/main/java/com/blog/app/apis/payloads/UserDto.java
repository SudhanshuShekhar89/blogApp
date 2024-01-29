package com.blog.app.apis.payloads;

import com.blog.app.apis.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@Setter

public class UserDto {
	
	
	private int id;

	@NotNull
	@NotEmpty
	@Size(min = 4,message = "user name must be greater than min of 4 charcter")
	private String name;

	@NotNull
	@Email(message = "Your email address is not valid")
	private String email;



	private String password;


	@NotEmpty
	@Size(min = 4,max = 20,message = "Password must be in range of 4 to 20 charcter")
	private String about;

	private Set<RoleDto> roles=new HashSet<>();

	// isko new comment kiya hai

//	@JsonIgnore
//	public String getPassword() {
//
//		return this.password;
//	}
//
//	@JsonProperty
//	public void setPassword(String password) {
//		this.password=password;
//	}

}
