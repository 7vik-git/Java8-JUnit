package com.gevernova.library;
public class BookLimitExceededException extends Exception {
    public BookLimitExceededException(String msg) {
        super(msg);
    }
}

