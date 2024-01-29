package com.blog.app.apis.security;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JwtAuthRequest {

    private String username;
    private String password;

}
