package com.fallensakura.exception;

import com.fallensakura.constant.MessageConstant;

public class PasswordErrorException extends RuntimeException {
    public PasswordErrorException() {
        super(MessageConstant.PASSWORD_ERROR);
    }
    public PasswordErrorException(String message) {
        super(message);
    }
}
