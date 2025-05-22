package com.gevernova.leavemanagment;
public class LeaveLimitExceededException extends Exception {
    public LeaveLimitExceededException(String msg) {
        super(msg);
    }
}
