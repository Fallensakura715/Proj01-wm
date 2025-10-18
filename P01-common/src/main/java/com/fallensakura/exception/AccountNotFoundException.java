package com.fallensakura.exception;

import com.fallensakura.constant.MessageConstant;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException() {
        super(MessageConstant.ACCOUNT_NOT_FOUND);
    }
    public AccountNotFoundException(String message) {
        super(message);
    }
}
