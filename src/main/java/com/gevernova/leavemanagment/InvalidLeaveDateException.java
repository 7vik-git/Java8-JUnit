package com.gevernova.leavemanagment;

public class InvalidLeaveDateException extends Exception {
    public InvalidLeaveDateException(String msg) {
        super(msg);
    }
}