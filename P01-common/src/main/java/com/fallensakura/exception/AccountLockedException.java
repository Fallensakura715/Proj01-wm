package com.fallensakura.exception;

import com.fallensakura.constant.MessageConstant;

public class AccountLockedException extends RuntimeException {
    public AccountLockedException() {
        super(MessageConstant.ACCOUNT_LOCKED);
    }
    public AccountLockedException(String message) {
        super(message);
    }
}
