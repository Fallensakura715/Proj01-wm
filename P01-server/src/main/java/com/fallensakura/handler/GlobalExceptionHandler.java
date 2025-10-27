package com.fallensakura.handler;

import com.fallensakura.constant.MessageConstant;
import com.fallensakura.exception.AccountNotFoundException;
import com.fallensakura.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public Result<?> exceptionHandler(SQLIntegrityConstraintViolationException exception) {
        String message = exception.getMessage();
        if (message.contains("Duplicate entry")) {
            String[] split = message.split(" ");
            String username = split[2];
            String msg = username + MessageConstant.ALREADY_EXISTS;
            return Result.error(msg);
        }
        return Result.error(exception.getMessage());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public Result<?> handleAccountNotFound(AccountNotFoundException e) {
        return Result.error(e.getMessage());
    }
}
