package com.wezaam.withdrawal.handler;

import java.io.IOException;

import com.wezaam.withdrawal.exception.CommunicationException;
import com.wezaam.withdrawal.exception.NotAvailableException;
import com.wezaam.withdrawal.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class RestExceptionHandler
{
    @ExceptionHandler(value = { IOException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequest(Exception ex)
    {
        return new ErrorResponse.Builder()
                .withStatus(400)
                .withMessage("Bad Request")
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse validationError(Exception ex) {
        return new ErrorResponse.Builder()
                .withStatus(400)
                .withMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler(value = { UserNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse unKnownException(Exception ex)
    {
        return new ErrorResponse.Builder()
                .withStatus(404)
                .withMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler(value = { NotAvailableException.class })
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorResponse notAvailableException(Exception ex)
    {
        return new ErrorResponse.Builder()
                .withStatus(503)
                .withMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler(value = { CommunicationException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse communicationException(Exception ex)
    {
        return new ErrorResponse.Builder()
                .withStatus(500)
                .withMessage(ex.getMessage())
                .build();
    }

}