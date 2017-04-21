package com.sux.exceptions;

/**
 * Created by Shorty on 4/15/14.
 */
public class LoginException extends Exception {
    public LoginException(){
        super();

    }
    public LoginException(String message){
        super(message);
    }
}
