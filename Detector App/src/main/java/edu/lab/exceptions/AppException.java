package edu.lab.exceptions;

public abstract class AppException extends Exception{
    AppException(String msg){
        super(msg);
    }
}
