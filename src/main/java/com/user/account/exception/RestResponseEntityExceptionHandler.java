package com.user.account.exception;

import com.user.account.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidOtpExecption.class)
    public ResponseEntity<ApiResponse> handleInvalidOtp(){
        ApiResponse ar = new ApiResponse("Invalid Otp",false);
        return new ResponseEntity<ApiResponse>(ar, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OtpExpiredException.class)
    public ResponseEntity<ApiResponse> handleOtpExpiration(){
        ApiResponse ar = new ApiResponse("Otp has been expired",false);
        return new ResponseEntity<ApiResponse>(ar, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConsumedOtpException.class)
    public ResponseEntity<ApiResponse> handleConsumedOtp(){
        ApiResponse ar = new ApiResponse("Otp has been already consumed",false);
        return new ResponseEntity<ApiResponse>(ar, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidOtpIdException.class)
    public ResponseEntity<ApiResponse> handleInvalidOtpId(){
        ApiResponse ar = new ApiResponse("Don't refresh the browser when email and amount is submitted",false);
        return new ResponseEntity<ApiResponse>(ar, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse> handleUserNotFoundRequest(){
        ApiResponse apiResponse = new ApiResponse("User not found", false);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
    }
//
//    @ExceptionHandler(InsufficientBalanceException.class)
//    public ResponseEntity<ApiResponse> handleInsufficientBalance(){
//        ApiResponse ar = new ApiResponse("Insufficient balance",false);
//        return new ResponseEntity<ApiResponse>(ar, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(TicketWithZeroAmountException.class)
//    public ResponseEntity<ApiResponse> handleTicketWithZeroAmount(){
//        ApiResponse ar = new ApiResponse("Cannot create ticket with 0 amount",false);
//        return new ResponseEntity<ApiResponse>(ar, HttpStatus.BAD_REQUEST);
//    }
}
