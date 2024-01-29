package com.blog.app.apis.exceptions;

public class ApiException extends RuntimeException{


    public ApiException(String message) {
        super(message);
    }

    public ApiException() {
        super();
    }
}
