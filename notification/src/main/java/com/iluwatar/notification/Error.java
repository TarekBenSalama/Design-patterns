package com.iluwatar.notification;

public class Error {
    private String message;
    public Error(String message) {
        this.message = message;
    }
    public String getErrorMessage(){
        return this.message;
    }
}