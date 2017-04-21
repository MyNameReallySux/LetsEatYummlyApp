package com.sux.exceptions;

/**
 * Created by Shorty on 4/14/14.
 */
public class AsyncTaskException extends RuntimeException {
    public AsyncTaskException(){
        super();

    }
    public AsyncTaskException(String message){
        super(message);

    }
    public AsyncTaskException(String message, Throwable throwable){
        super(message, throwable);

    }
}
