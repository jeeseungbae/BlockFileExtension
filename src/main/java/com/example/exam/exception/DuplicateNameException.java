package com.example.exam.exception;

import org.springframework.dao.DuplicateKeyException;

public class DuplicateNameException extends DuplicateKeyException {
    public DuplicateNameException(String msg) {
        super(msg);
    }
}
