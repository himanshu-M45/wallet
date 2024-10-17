package org.example.wallet.Exceptions;

import org.example.wallet.DTO.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CannotCreateUserException.class)
    public ResponseEntity<ResponseDTO<String>> handleCannotCreateUserException(CannotCreateUserException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
    }

    @ExceptionHandler(UsernameAlreadyRegisteredException.class)
    public ResponseEntity<ResponseDTO<String>> handleUsernameAlreadyRegisteredException(UsernameAlreadyRegisteredException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ResponseDTO<>(HttpStatus.CONFLICT.value(), e.getMessage(), null));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseDTO<String>> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDTO<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
    }

    @ExceptionHandler(UserNotAutorisedException.class)
    public ResponseEntity<ResponseDTO<String>> handleUserNotAuthenticatedException(UserNotAutorisedException e) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ResponseDTO<>(HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage(), null));
    }

    @ExceptionHandler(UserNotAuthorizedException.class)
    public ResponseEntity<ResponseDTO<String>> handleUserNotAuthorizedException(UserNotAuthorizedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ResponseDTO<>(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), null));
    }

    @ExceptionHandler(InvalidAmountEnteredException.class)
    public ResponseEntity<ResponseDTO<String>> handleInvalidAmountEnteredException(InvalidAmountEnteredException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ResponseDTO<String>> handleInsufficientBalanceException(InsufficientBalanceException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
    }

    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<ResponseDTO<String>> handleWalletNotFoundException(WalletNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDTO<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseDTO<String>> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO<String>> handleGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null));
    }
}