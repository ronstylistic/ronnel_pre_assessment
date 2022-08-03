package com.rcjsolutions.SpringAppTest.exception;

public class EmailAlreadyExistException extends Exception {

    public EmailAlreadyExistException(){
        super("User with this Email already exists");
    }
}
