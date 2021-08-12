package com.example.exam.exception;

import org.springframework.dao.DuplicateKeyException;

public class FixedSameDataException extends DuplicateKeyException {
    public FixedSameDataException(String msg) {
        super(msg);
    }
}
