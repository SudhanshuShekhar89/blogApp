package com.blog.app.apis.payloads;


import lombok.Data;

@Data
public class JwtAuthResponse {

    private String token;
    private UserDto user;
}
